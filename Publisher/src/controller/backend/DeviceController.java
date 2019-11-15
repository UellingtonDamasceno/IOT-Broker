package controller.backend;

import java.io.IOException;
import model.Device;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Damasceno
 */
public class DeviceController {

    private String ip;
    private int port;

    private Device smart;

    public Device getSmartDevice() {
        return this.smart;
    }

    public void setSmartDevice(Device smart) {
        this.smart = smart;
    }

    public void connect(Device smartDevice, String ip, int port) throws IOException {
        smartDevice.configureConnection(ip, port);
        this.ip = ip;
        this.port = port;
    }

    public void trunOn() throws IOException, NetworkNotConfiguredException {
        this.smart.on();
    }

    public void turnOff() throws IOException {
        //Avisar ao server que ele será desconectado!
        this.smart.off();
    }

    public void restart() throws IOException, NetworkNotConfiguredException {
        this.turnOff();
        this.connect(smart, ip, port);
        this.trunOn();
    }

    public void standBy() {
        this.smart.standBy();
    }

    public void processRequest(String request) {
        try {
            switch (request) {
                case "200":{
                    System.out.println("Cadastrado com sucesso!");
                    break;
                }
                case "ON": {
                    this.trunOn();
                    break;
                }
                case "OFF": {
                    this.turnOff();
                    break;
                }
                case "RESTART": {
                    this.restart();
                    break;
                }
                case "STANDBY": {
                    this.standBy();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao conectar");
        } catch (NetworkNotConfiguredException ex) {
            System.out.println("Conexão não configurada");
        }
    }

}
