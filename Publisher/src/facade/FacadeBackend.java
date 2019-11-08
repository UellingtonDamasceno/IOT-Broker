package facade;

import controller.backend.DeviceController;
import controller.backend.FactoryController;
import controller.backend.ProtcolController;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import model.SmartDevice;
import model.exceptions.NetworkNotConfiguredException;
import util.Settings.DevicesBrand;
import util.Settings.DevicesModels;
import util.Settings.DevicesTypes;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend implements Observer {

    private static FacadeBackend facade;

    private DeviceController deviceController;
    private FactoryController facatoryController;
    private ProtcolController protocolController;

    private FacadeBackend() {
        this.deviceController = new DeviceController();
        this.facatoryController = new FactoryController();
        this.protocolController = new ProtcolController();
    }

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public SmartDevice connect(DevicesTypes type, DevicesBrand brand, DevicesModels model, String ip, int port) throws IOException {
        //criar método que solicita ao servidor o novo ID do despositivo;

        SmartDevice smart = this.facatoryController.createSmartDevice(type, ip, brand, model);
        System.out.println(smart);
        this.protocolController.createDevice(smart.toString());
        this.deviceController.connectDevice(smart, ip, port);
        this.deviceController.setSmartDevice(smart);
        smart.addObserver(this);

        System.out.println("Despositivo criado com sucesso.");
        return smart;
    }
    
    public void disconnect() throws IOException {
        this.deviceController.turnOff();
    }

    public void standBy(){

    }    
    
    public void restart() throws IOException, NetworkNotConfiguredException{
        this.deviceController.restartDevice();
    }
    public SmartDevice getSmartDevice() {
        return this.deviceController.getSmartDevice();
    }

    @Override
    public void update(Observable o, Object o1) {
        String message = (String) o1;
//this.deviceController.processRequest();
    }

}
