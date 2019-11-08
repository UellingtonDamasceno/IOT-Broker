package controller.backend;

import java.io.IOException;
import model.SmartDevice;
import model.exceptions.NetworkNotConfiguredException;

/**
 *
 * @author Uellington Damasceno
 */
public class DeviceController{
    private String ip;
    private int port;
    
    private SmartDevice smart;
    
    public SmartDevice getSmartDevice(){
        return this.smart;
    }
    
    public void setSmartDevice(SmartDevice smart){
        this.smart = smart;
    }
    
    public void connectDevice(SmartDevice smartDevice, String ip, int port) throws IOException{
        smartDevice.configureConnection(ip, port);
        this.ip = ip;
        this.port = port;
    }
   
    public void trunOn() throws IOException, NetworkNotConfiguredException{
        this.smart.on();
    }
    
    public void turnOff() throws IOException{
        //Avisar ao server que ele será desconectado!
        this.smart.off();
    }
        
    public void restartDevice() throws IOException, NetworkNotConfiguredException{
        this.turnOff();
        this.connectDevice(smart, ip, port);
        this.trunOn();
    }
    
    public void standBy(){
        this.smart.standBy();
    }
    
    public void processRequest(String request){
        /**
         * Criar função que verifica se o responsavel pelo request pode 
         * manipular o publisher.
         */
    }
    
}
