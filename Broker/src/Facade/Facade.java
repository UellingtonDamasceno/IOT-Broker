package facade;

import controller.BrokerController;
import controller.TopicController;
import java.io.IOException;

/**
 *
 * @author Uellington Damasceno
 */
public class Facade{

    private static Facade facade;

    private final BrokerController brokerController;
    private TopicController topicController;

    private Facade() {
        this.brokerController = new BrokerController();
        this.topicController = new TopicController();
    }

    public static synchronized Facade getInstance() {
        return (facade == null) ? facade = new Facade() : facade;
    }

    public void initialize(int port) throws IOException {
        this.brokerController.start(port);
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
