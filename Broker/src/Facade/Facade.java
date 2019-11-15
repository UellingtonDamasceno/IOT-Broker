package facade;

import controller.RequestController;
import net.Server;
import controller.TopicController;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.Client;

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
    public String createTopic(String request) {
        //Método que recebe os parametros e cria um tópico
        //this.topicController.postTopic(topicID, topic);
        return null;
    }

    //post/publisher
    public String createPublisher(String request) {
        return null;
    }

    //post/subscriper
    public String createSubscriper(String request) {
        return null;
    }

    //delete/topic
    public String deleteTopic(String request) {
        return null;
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

}
