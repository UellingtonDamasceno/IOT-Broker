package controller.backend;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.ObservableList;
import model.Subscriber;
import model.Topic;
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

    public void unsubscribe(String topicID) throws DeviceStandByException, IOException, DeviceOfflineException {
        this.subscriber.unsubscribe(topicID);
    }

    public void subscribe(String topicID) throws IOException, DeviceOfflineException, DeviceStandByException {
        this.subscriber.subscribe(topicID);
    }

    public void connect() throws IOException, NetworkNotConfiguredException {
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
        String route = response.getString("route");
        System.out.println(route);
        System.out.println(response);
        switch (route) {
            case "GET/TOPICS": {
                this.subscriber.updateTopics(response);
                if (this.countObservers() > 0) {
                    this.setChanged();
                    this.notifyObservers(route);
                }
                break;
            }
            case "UPDATE/TOPIC": {
                this.setChanged();
                this.notifyObservers(response);
                break;
            }
            case "PUB/DISCONNECT": {
                this.setChanged();
                this.notifyObservers(response);
                break;
            }
            case "SERVER:CLOSE": {
                this.setChanged();
                this.notifyObservers(response);
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        try {
            JSONObject request = new JSONObject((String) o1);
            this.process(request);
        } catch (Exception e) {
            System.out.println("Deu merda!");
            System.out.println((String) o1);
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() throws IOException {
        this.subscriber.disconnect();
    }

    public boolean restart() throws InterruptedException, IOException {
        this.subscriber.close();
        return this.subscriber.reconnect();
    }

    public void offPublisher(Topic topic) throws IOException {
        this.subscriber.offPublisher(topic);
    }

}
