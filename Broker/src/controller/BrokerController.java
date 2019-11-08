package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Uellington Damasceno
 */
public class BrokerController {
    private ServerSocket broker;

    
    public void startBroker(int port) throws IOException{
        this.broker = new ServerSocket(port);
    }
    
    public Socket accept() throws IOException{
        return broker.accept();
    }
    
    public void disconnect() throws IOException{
        this.broker.close();
    }
    
}
