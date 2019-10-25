package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 *
 * @author Uellington Damasceno
 */
public class Subscriper extends Observable implements Runnable {

    private final int MAX_REQUEST = 5;

    private final Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    private Queue<String> requests;

    private boolean online;

    public Subscriper(Socket socket) {
        this.socket = socket;
        this.requests = new LinkedList();
        this.online = false;
    }

    public boolean isOnline() {
        return online;
    }

    public void startClient() throws IOException {
        InputStreamReader is = new InputStreamReader(this.socket.getInputStream());
        this.reader = new BufferedReader(is);

        OutputStreamWriter ot = new OutputStreamWriter(this.socket.getOutputStream());
        this.writer = new BufferedWriter(ot);
        
        this.online = true;
    }

    public void closeConnection() {
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
        //requests.add(str.toString().trim());
        return request.toString();//requests.peek();
    }

    public void write(String response) throws IOException {
        System.out.println("Enviando...");
        this.writer.write(response);
        this.writer.flush();
        System.out.println("Enviada");
    }

    public boolean canAcceptRequest() {
        return (requests.size() < MAX_REQUEST);
    }

    public void addNewObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void run() {
        String message;
        while (this.online) {
            if (this.canAcceptRequest()) {
                try {
                    System.out.println("Estou aguardando mensagem");
                    message = this.read();
                    
                    if (message != null && !(message.isEmpty())) {
                        //this.write(message);
                        this.setChanged();
                        this.notifyObservers(message);
                    }
                } catch (IOException ex) {
                    System.out.println("Falha ao conectar");
                    this.online = false;
                }
            } else {

            }
        }
    }
    
    @Override
    public String toString(){
        return this.socket.getLocalAddress().toString();
    }

}
