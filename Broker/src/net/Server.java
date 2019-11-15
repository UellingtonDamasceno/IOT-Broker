package net;

import controller.RequestController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.Client;

/**
 *
 * @author Uellington Damasceno
 */
public class Server implements Runnable {

    private ServerSocket broker;    
    private boolean online;
    
    public Server() {
        this.online = false;
    }

    public void start(int port) throws IOException {
        if (!this.online) {
            this.broker = new ServerSocket(port);
            this.online = true;
            new Thread(this).start();
        }
    }

    public void close() throws IOException {
        if (this.online) {
            this.broker.close();
            this.online = false;
        }
    }

    @Override
    public void run() {
        while (online) {
            try {
                Socket socket = this.broker.accept();
                Client client = new Client(socket);
                client.addObserver(Router.getInstance());
                client.start();
            } catch (IOException ex) {
                System.out.println("Deu merda: "+ex.getMessage());
            }
        }
    }
}