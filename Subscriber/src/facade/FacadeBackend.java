package facade;

import controller.backend.SubscriberController;
import java.io.IOException;
import javafx.collections.ObservableList;
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
    
    public ObservableList getAllTopics() {
        return this.sub.getAllTopics();
    }

    public void updateListTopics() throws IOException, DeviceStandByException, DeviceOfflineException {
        this.sub.getListTopic();
    }
}
