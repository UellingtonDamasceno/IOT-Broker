package controller.backend;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Publisher;
import model.exceptions.NetworkNotConfiguredException;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class PublisherController extends Observable implements Observer {

    private Publisher publisher;

    public void updateValue(int value) throws IOException {
        this.publisher.setValue(value);
        this.publisher.updateTopic(value);
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void createPublisher(String type, String brand, String model, String ip, int port) {
        this.publisher = new Publisher(type, brand, model, ip, port);
        this.publisher.addObserver(this);
    }

    public boolean reconnect() throws IOException, InterruptedException {
        this.publisher.close();
        return this.publisher.reconnect();
    }

    public void connect() throws IOException, NetworkNotConfiguredException {
        this.publisher.connect();
        this.publisher.on();
    }

    public void turnOff() throws IOException {
        this.publisher.disconnect();
        this.publisher.setOnline(false);
        this.publisher.close();
    }

    public void createTopic() throws IOException {
        this.publisher.createTopic();
    }

    private void processResponse(JSONObject response) {
        System.out.println("Recebeu: " + response);
        String route = response.getString("route");
        route = route.trim();
        System.out.println("Rota desiginada:" + route);
        switch (route) {
            case "ON": {
                this.setChanged();
                this.notifyObservers(response);
                break;
            }
            case "PUBS/OFF": {
                this.setChanged();
                this.notifyObservers("OFF");
                break;
            }
            case "SERVER:CLOSE": {
                this.setChanged();
                this.notifyObservers("RECONNECT");
                break;
            }
            case "RECONNECTED": {
                this.setChanged();
                this.notifyObservers(response);
                break;
            }
            default: {
                System.out.println("Erro code: " + response);
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof JSONObject) {
            System.out.println(((JSONObject) o1).toString());
            this.processResponse((JSONObject) o1);
        }
    }

}
