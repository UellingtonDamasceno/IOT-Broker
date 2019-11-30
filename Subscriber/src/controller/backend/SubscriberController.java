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
    private Topic currentTopic;

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        this.subscriber.addObserver(this);
    }

    public void createSubscriber(String type, String brand, String model, String ip, int port) {
        this.subscriber = new Subscriber(type, brand, model, ip, port);
        this.subscriber.addObserver(this);
    }

    public void setCurrentTopic(Topic selectedTopic) {
        this.currentTopic = selectedTopic;
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
                System.out.println("Atualizando...");
                //this.subscriber.updateTopic(response);
                if (currentTopic.getName().equals(response.getString("topic_id"))) {
                    System.out.println(currentTopic.getName());
                    System.out.println(response.getString("topic_id"));
                    this.setChanged();
                    this.notifyObservers(response);
                }
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        System.out.println("Processando");
        try {
            System.out.println("String: "+ (String)o1);
            System.out.println(((String)o1).replace("\n", ":::"));
            JSONObject request = new JSONObject((String) o1);
            this.process(request);

        } catch (Exception e) {
            System.out.println("Deu merda");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    public void addTopic() {
        this.subscriber.addTopic();
    }

    public void disconnect() throws IOException {
        this.subscriber.disconnect();
    }

}
