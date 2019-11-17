package facade;

import controller.RequestController;
import net.Server;
import controller.TopicController;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.Client;
import model.exceptions.ClientExistException;

/**
 *
 * @author Uellington Damasceno
 */
public class Facade{

    private static Facade facade;

    private TopicController topicController;
    private RequestController bufferController;

    private Facade() {
        this.topicController = new TopicController();
        this.bufferController = new RequestController();
    }

    public static synchronized Facade getInstance() {
        return (facade == null) ? facade = new Facade() : facade;
    }
    
    //post/topic
    public void createTopic(String request) {
        //Método que recebe os parametros e cria um tópico
        //this.topicController.postTopic(topicID, topic);
        this.topicController.postTopic(request);
    }

    public void postPublisher(String topicID, Client client) throws ClientExistException {
        this.topicController.postPublisher(topicID, client);
    }

    //post/subscriper
    public void postSubscriber(String request, Client client) throws ClientExistException {
        this.topicController.postSubscriber(request, client);
    }

    //delete/topic
    public void deleteTopic(String request) {
       // this.topicController.deleteTopic(request);
    }

    //delete/publisher
    public String deletePublisher() {
        return null;
    }

    //delete/subscriper
    public String deleteSubscriper() {
        return null;
    }

    //get/topic
    public String getTopics() {
        return this.topicController.getTopics();
    }

    public void updateTopic(String topicID, int value) {
        this.topicController.updateSubscriper(topicID, value);;
    }

}
