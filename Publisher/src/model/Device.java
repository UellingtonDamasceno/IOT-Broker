package model;

import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import model.exceptions.NetworkNotConfiguredException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public abstract class Device extends Smart {

    protected final String brand;
    protected final String type;
    protected final String model;

    public Device(String type, String brand, String model) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.online = false;
    }

    public boolean isOnline() {
        return this.online;
    }

    /**
     * A mensagem somente será enviada se o despositivo estiver ligado e online;
     *
     * @param response
     * @throws IOException
     * @throws DeviceOfflineException
     * @throws DeviceStandByException
     */
    public void send(String response) throws IOException, DeviceOfflineException, DeviceStandByException {
        if (this.online) {
            this.write(response);
        } else if (!this.online) {
            throw new DeviceOfflineException();
        } else {
            throw new DeviceStandByException();
        }
    }

    /**
     * Inicia a thread de conexão com o servidor
     *
     * @throws IOException
     * @throws model.exceptions.NetworkNotConfiguredException
     */
    public void on() throws IOException, NetworkNotConfiguredException {
        if (this.isConnected()) {
            this.online = true;
            new Thread(this).start();
            System.out.println("Nova thread criada");
        } else {
            System.out.println("Conexão não configurada!");
            throw new NetworkNotConfiguredException();
        }
    }

    public void configureConnection(String ip, int port) {
        //Verificar se o próximo socket será igual ao atual. 
        System.out.println("Começou a configurar a rede");
        try {
            this.configureConnection(new Socket(ip, port));
            this.online = true;
            
            System.out.println("Dispositivo online!");
        } catch (IOException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String status() {
        JSONObject device = new JSONObject();

        device.accumulate("brand", this.brand);
        device.accumulate("type", this.type);
        device.accumulate("model", this.model);
        device.accumulate("online", this.online);
        return device.toString();
    }

}
