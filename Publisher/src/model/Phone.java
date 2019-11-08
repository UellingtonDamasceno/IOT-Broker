package model;

import java.io.IOException;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Damasceno
 */
public class Phone extends SmartDevice{

    public Phone(String type, String brand, String model) {
        super(type, brand, model);
    }


    public void starting() throws IOException, NetworkNotConfiguredException {
        System.out.println("Ligando");
        this.on();
        System.out.println("Ligado");
    }

    public void turningOff() throws IOException {
        System.out.println("Desligando");
        this.off();
        System.out.println("Desligado");
    }

    public void restarting() throws IOException, NetworkNotConfiguredException {
        System.out.println("Reinicando");
        System.out.println("Reiniciado");
    }

    public String status() {
        return " :: B "+this.getBrand() +
               " :: O "+this.isOnline() +
               " :: S "+this.inStadby();
    }

    public void connectWifi(String ip, int port) throws IOException {
        System.out.println("Conecatando");
        this.configureConnection(ip, port);
        System.out.println("Conectado");
    }

    public void suspend() {

    }
    
}
