package net;

import controller.BrokerController;
import controller.TopicController;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import model.Client;
import model.Topic;

/**
 *
 * @author Uellington Damasceno
 */
public class ServerBroker implements Runnable, Observer {

    private static ServerBroker server;

    private final BrokerController brokerController;
    private TopicController topicController;
    
    private ServerBroker() {
        this.brokerController = new BrokerController();
        this.topicController = new TopicController();
    }

    public static synchronized ServerBroker getInstance() {
        return (server == null) ? server = new ServerBroker() : server;
    }

    public void initialize(int port) throws IOException {
        this.brokerController.startBroker(port);
        new Thread(this).start();
    }
    
    //post/topic
    public String createTopic(Socket client){
        //Método que recebe os parametros e cria um tópico
        //this.topicController.postTopic(topicID, topic);
        return null;
    }
    
    //post/publisher
    public String createPublisher(){
        return null;
    }
    
    //post/subscriper
    public String createSubscriper(){
        return null;
    }
    
    //delete/topic
    public String deleteTopic(){
        return null;
    }
    
    //delete/publisher
    public String deletePublisher(){
        return null;
    }
    
    //delete/subscriper
    public String deleteSubscriper(){
        return null;
    }
    
    //get/topic
    public String getTopics(){
        Iterator<Topic> topics = this.topicController.getTopics();
        return null;
    }
    
    
    @Override
    public void run() {
        while(true){
            try {
                Socket socket = this.brokerController.accept();
                Client client = new Client(socket);
                client.addObserver(this);
            } catch (IOException ex) {
            }
        }
    }

    
    /**
     * Outra opção seria fazer o controller de tópico 
     */
    @Override
    public void update(Observable o, Object o1) {
        
    }
}
