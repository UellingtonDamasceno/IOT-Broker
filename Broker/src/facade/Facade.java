package facade;

import controller.TopicController;
import java.io.IOException;
import model.Client;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Facade{

    private static Facade facade;

    private TopicController topicController;

    private Facade() {
        this.topicController = new TopicController();
    }

    public static synchronized Facade getInstance() {
        return (facade == null) ? facade = new Facade() : facade;
    }
    
    //post/topic
    public void createTopic(String request) {
        this.topicController.postTopic(request);
    }

    public void postPublisher(String topicID, Client client) {
        this.topicController.postPublisher(topicID, client);
    }

    //post/subscriper
    public void postSubscriber(String topicID, Client client) {
        this.topicController.postSubscriber(topicID, client);
    }

    //delete/topic
    public void deleteTopic(String request) {
       // this.topicController.deleteTopic(request);
    }

    //delete/publisher
    public void deletePublisher(String publisherID) throws IOException {
        this.topicController.deletePublisher(publisherID);
    }

    //delete/subscriper
    public void deleteSubscriper(String topicID, String subscriperID) throws IOException {
        this.topicController.deleteSubscripe(topicID, subscriperID);
    }

    //get/topic
    public String getTopics() {
        return this.topicController.getTopics();
    }

    public String updateTopic(JSONObject request) {
        return this.topicController.updateSubscriper(request);
    }

}
