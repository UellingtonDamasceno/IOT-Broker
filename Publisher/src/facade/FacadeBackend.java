package facade;

import controller.backend.PublisherController;
import controller.frontend.PublisherDashboardController;
import java.io.IOException;
import java.util.Observer;
import model.Device;
import model.Publisher;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend {

    private static FacadeBackend facade;

    private PublisherController publisherController;

    private FacadeBackend() {
        this.publisherController = new PublisherController();
    }

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public void connect(String type, String brand, String model, String ip, int port) throws IOException, NetworkNotConfiguredException, InterruptedException {
        //criar método que solicita ao servidor o novo ID do despositivo;  
        this.publisherController.createPublisher(type, brand, model, ip, port);
        this.publisherController.connect();
    }

    public void disconnect() throws IOException {
        this.publisherController.turnOff();
    }

    public boolean restart() throws InterruptedException, IOException {
        return this.publisherController.reconnect();
    }

    public void updateValue(int value) throws IOException {
        this.publisherController.updateValue(value);
    }

    public Publisher getSmartDevice() {
        return this.publisherController.getPublisher();
    }

    public void createTopic() throws IOException {
        this.publisherController.createTopic();
    }

    public void setPublisherControllerObserver(Observer observer) {
        this.publisherController.addObserver(observer);
    }

}
