package facade;

import controller.backend.SubscriberController;
import java.io.IOException;
import java.util.Observer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import model.Topic;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Conceição
 */
public class FacadeBackend {

    private static FacadeBackend facade;
    private SubscriberController sub;
    
    private FacadeBackend() {
        this.sub = new SubscriberController();
    }

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public void connect(String type, String brand, String model, String ip, int port) throws IOException, NetworkNotConfiguredException{
        this.sub.createSubscriber(type, brand, model, ip, port);
        this.sub.connect();
    }

    public void addSubscriberControllerObserver(Observer observer){
        this.sub.addObserver(observer);
    }
    
    public ObservableList getAllTopics() {
        return this.sub.getAllTopics();
    }

    public void updateListTopics() throws IOException, DeviceStandByException, DeviceOfflineException {
        this.sub.getListTopic();
    }

    public void subscribe(String topicID) throws IOException, DeviceStandByException, DeviceOfflineException {
        this.sub.subscribe(topicID);
    }

    public void unsubscribe(String topicID) throws DeviceStandByException, IOException, DeviceOfflineException {
        this.sub.unsubscribe(topicID);
    }

    public void disconnect() throws IOException {
        this.sub.disconnect();
    }

    public void addTopicsVisualize(Topic topic) {
        this.sub.addTopicVisualize(topic);
    }
   
}
