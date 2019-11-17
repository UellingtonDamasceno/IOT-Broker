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
public class PublisherController implements Observer{

    private String ip;
    private int port;
    private Publisher publisher;
    
    
    public void updateValue(int value) throws IOException {
        this.publisher.setValue(value);
        this.publisher.updateTopic(value);
    }
    
    public Publisher getPublisher(){
        return this.publisher;
    }

    
    public void createPublisher(String type, String brand, String model) {
        this.publisher = new Publisher(type, brand, model);
        this.publisher.addObserver(this);
    }

    public void connect(String ip, int port) throws IOException {
        this.publisher.configureConnection(ip, port);
        this.ip = ip;
        this.port = port;
    }
    
    public void trunOn() throws IOException, NetworkNotConfiguredException {
        this.publisher.on();
    }

    public void turnOff() throws IOException {
        //Avisar ao server que ele será desconectado!
        this.publisher.off();
    }

    public void restart() throws IOException, NetworkNotConfiguredException {
        this.publisher.off();
        this.publisher.configureConnection(ip, port);
        this.publisher.on();
    }

    public void standBy() {
        this.publisher.standBy();
    }

    public void createTopic() throws IOException{
        this.publisher.createTopic();
    }
    
    private void processRequest(String request) {
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
                case "404": {
                    this.restart();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao conectar");
        } catch (NetworkNotConfiguredException ex) {
            System.out.println("Conexão não configurada");
        }
    }

    
    
    @Override
    public void update(Observable o, Object o1) {
        this.processRequest((String) o1);
    }
    
}
