package facade;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.Device;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend implements Observer {

    private static FacadeBackend facade;

    private FacadeBackend() {}

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public Device connect(String type, String brand, String model, String ip, int port) throws IOException {
        return null;
    }
    
    public void disconnect() throws IOException {
    }

    public void standBy(){
    }    
    
    public void restart() throws IOException, NetworkNotConfiguredException{
    }
    public Device getSmartDevice() {
        return null;
    }

    @Override
    public void update(Observable o, Object o1) {
        String request = (String) o1;
    }

}
