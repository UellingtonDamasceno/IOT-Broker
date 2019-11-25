package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Observable;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Client extends Observable implements Runnable {

    private final String ip;
    private final Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    private boolean online;

    public Client(Socket socket) {
        this.socket = socket;
        this.ip = ((String) socket.getRemoteSocketAddress().toString().replace("/", ""));
        this.online = false;
    }

    public String getIP() {
        return this.ip;
    }

    public boolean isOnline() {
        return online;
    }

    /**
     * Método utilizado para inicializar entrada e saida de um cliente
     *
     * @throws IOException Caso exista problemas em pegar o input ou output do
     * cliente
     */
    public void start() throws IOException {
        InputStreamReader is = new InputStreamReader(this.socket.getInputStream());
        this.reader = new BufferedReader(is);

        OutputStreamWriter ot = new OutputStreamWriter(this.socket.getOutputStream());
        this.writer = new BufferedWriter(ot);

        this.online = true;
        new Thread(this).start();
    }

    /**
     * Responsável por finalizar a conexão do cliente.
     *
     * @throws IOException Caso algum fluxo de dados ou socket não é fechado.
     */
    public void close() throws IOException {
        //this.writer(avisar que a conexão será finalizada);
        this.writer.close();
        this.reader.close();
        this.socket.close();
        this.online = false;
        //Instruções relacionadas observer para remover o objeto
    }

    private String read() throws IOException {
        StringBuilder request = new StringBuilder("");
        int buffer = -1;
        while (buffer != '\n') {
            buffer = reader.read();
            request.append((char) buffer);
        }
        return request.toString();
    }

    public synchronized void write(String response) {
        Runnable send = () -> {
            int uploadAttempets = 5;
            boolean sended = false;
            while (this.online && (uploadAttempets != 0 || !sended)) {
                try {
                    this.writer.write(response + '\n');
                    this.writer.flush();
                    sended = true;
                    uploadAttempets = 0;
                } catch (IOException ex) {
                    System.out.println("Erro ao enviar mensagem::" + response);
                    uploadAttempets--;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        };
        new Thread(send).start();
    }

    @Override
    public void run() {
        String message;
        while (this.online) {
            try {
                message = this.read();
                if (message != null && !(message.isEmpty())) {
                    this.setChanged();
                    this.notifyObservers(message);
                }
            } catch (IOException ex) {
                if (this.online) {
                    this.online = false;
                    JSONObject json = new JSONObject();
                    json.accumulate("request_type", "ERROR");
                    json.accumulate("error_id", "000");
                    this.setChanged();
                    this.notifyObservers(json.toString());
                }
            }
        }
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.accumulate("client", this.ip);
        json.accumulate("connection_status", this.online);
        return json.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Client) {
            Client other = (Client) object;
            return (this.ip == other.ip);
        }
        return false;
    }

}
