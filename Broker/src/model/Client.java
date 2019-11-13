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

    private final String id;
    private final Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    private boolean online;

    public Client(Socket socket) {
        this.socket = socket;
        this.id = ((String) socket.getRemoteSocketAddress().toString().replace("/", ""));
        this.online = false;
    }
    
    public String getID(){
        return this.id;
    }
    
    public boolean isOnline() {
        return online;
    }

    public void start() throws IOException {
        InputStreamReader is = new InputStreamReader(this.socket.getInputStream());
        this.reader = new BufferedReader(is);

        OutputStreamWriter ot = new OutputStreamWriter(this.socket.getOutputStream());
        this.writer = new BufferedWriter(ot);

        this.online = true;
    }

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
            System.out.println(request);
        }
        return request.toString();
    }

    public synchronized void write(String response) {
        Runnable send = () -> {
            int uploadAttempets = 5;
            boolean sended = false;
            while (uploadAttempets != 0 || !sended) {
                try {
                    this.writer.write(response);
                    this.writer.flush();
                    sended = true;
                } catch (IOException ex) {
                    System.out.println("Deu merda ao enviar a mensagem");
                    uploadAttempets --;
                }
                //Colocar um sleep;
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
                System.out.println("Falha ao conectar");
                this.online = false;
            }
        }
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.accumulate("client", this.id);
        json.accumulate("connection_status", this.online);
        return json.toString();
    }

}
