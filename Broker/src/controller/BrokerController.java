package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.Client;

/**
 *
 * @author Uellington Damasceno
 */
public class BrokerController implements Runnable {

    private ServerSocket broker;    
//  Pode-se implementar uma fila de espera. como se fosse um pré cadastro. 
    private boolean online;
    
    public BrokerController() {
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
                System.out.println("Cliente conectado: " + client);
            } catch (IOException ex) {
            }
        }
    }
}
