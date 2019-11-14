package model;

import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import model.exceptions.NetworkNotConfiguredException;
import java.io.IOException;
import java.net.Socket;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Device extends Smart {
    
    private final String brand;
    private final String type;
    private final String model;
    private boolean online;
    private boolean standBy;

    public Device(String type, String brand, String model) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.online = false;
        this.standBy = false;
    }

    public boolean isOnline() {
        return this.online;
    }

    public String getBrand() {
        return this.brand;
    }

    public boolean inStadby() {
        return this.standBy;
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
        if (this.online && !this.standBy) {
            this.write(response);
        } else if(!this.online){
            throw new DeviceOfflineException();
        }else {
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
//            new Thread(this).start();
            this.online = true;
            this.standBy = false;
        } else {
            throw new NetworkNotConfiguredException();
        }
    }

    public void off() throws IOException {
        //Se der erro finalizar conexão ou coisas relacionadas dê uma olhada nesse método 
        //Talvez tenha problema com o fato da thread ser iterrompida, mas está em um 
        //while true;
        this.close();
//        Tbm deve-se avisar ao server que o despospositivo está desligado.
        System.out.println("Despositivo desligado");
        this.online = false;
    }


    public void standBy() {
        this.standBy = true;
        //Fica em modo de espera, mas sem enviar informações
        //voltará a enviar informações quando estiver tiver ligado
    }

    public void configureConnection(String ip, int port) throws IOException {
        
        //Verificar se o próximo socket será igual ao atual. 
        Socket socket = new Socket(ip, port);
        this.start(socket);
        System.out.println("Está online");
        this.online = true;
    }
    
    @Override
    public String toString(){
        JSONObject device = new JSONObject();
        
        device.accumulate("brand", this.brand);
        device.accumulate("type", this.type);
        device.accumulate("model", this.model);
        device.accumulate("online", this.online);
        device.accumulate("standby", this.standBy);
        return device.toString();
    }

}
