package controller.backend;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.Publisher;
import model.exceptions.NetworkNotConfiguredException;

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

    private void processResponse(String request) {
        System.out.println("Recebeu: "+ request);
        switch (request) {
            case "201": {
                System.out.println("Criado com sucesso!");
                break;
            }
            case "202": {
                System.out.println("Aceito com sucesso!");
                break;
            }
            case "SERVER:CLOSE": {
                this.setChanged();
                this.notifyObservers("RECONNECT");
                break;
            }
            case "RECONNECTED": {
                this.setChanged();
                this.notifyObservers(request);
                break;
            }
            default: {
                System.out.println("Erro code: " + request);
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        this.processResponse((String) o1);
    }

}
