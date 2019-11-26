package controller.backend;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.ObservableList;
import model.Subscriber;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import model.exceptions.NetworkNotConfiguredException;
import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class SubscriberController extends Observable implements Observer {

    private Subscriber subscriber;

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        this.subscriber.addObserver(this);
    }

    public void createSubscriber(String type, String brand, String model, String ip, int port) {
        this.subscriber = new Subscriber(type, brand, model, ip, port);
        this.subscriber.addObserver(this);
    }

    public void connect() throws IOException, NetworkNotConfiguredException{
        this.subscriber.connect();
        this.subscriber.on();
    }
    
    public void getListTopic() throws IOException, DeviceOfflineException, DeviceStandByException {
        this.subscriber.getTopics();
    }
    
    public ObservableList getAllTopics() {
        return this.subscriber.getAllTopics();
    }

    private void process(JSONObject response) {
        String route = response.getString("response");
        switch (route) {
            case "GET/TOPICS": {
                this.subscriber.updateTopics(response);
                if(this.countObservers() > 0){
                    this.setChanged();
                    this.notifyObservers(route);
                }
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        this.process(new JSONObject((String) o1));
    }

}
