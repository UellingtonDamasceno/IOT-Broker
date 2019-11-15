package facade;

import controller.backend.DeviceController;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.Device;
import model.Publisher;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend implements Observer {

    private static FacadeBackend facade;

    private DeviceController deviceController;

    private FacadeBackend() {
        this.deviceController = new DeviceController();
    }

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public Device connect(String type, String brand, String model, String ip, int port) throws IOException, NetworkNotConfiguredException {
        //criar método que solicita ao servidor o novo ID do despositivo;

        Device smart = new Publisher(type, brand, model);
        System.out.println(smart);
        this.deviceController.setSmartDevice(smart);        
        this.deviceController.connect(smart, ip, port);
        this.deviceController.trunOn();
        smart.addObserver(this);

        System.out.println("Despositivo criado com sucesso.");
        return smart;
    }
    
    public void disconnect() throws IOException {
        this.deviceController.turnOff();
    }

    public void standBy() {
        this.deviceController.standBy();
    }

    public void restart() throws IOException, NetworkNotConfiguredException {
        this.deviceController.restart();
    }

    public Device getSmartDevice() {
        return this.deviceController.getSmartDevice();
    }

    @Override
    public void update(Observable o, Object o1) {
        String request = (String) o1;
        this.deviceController.processRequest(request);
    }

}
