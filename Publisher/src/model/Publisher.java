package model;

import java.io.IOException;
import java.util.Random;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Publisher extends Device {

    private int value;
    private String ip;
    private int port;

    public Publisher(String type, String brand, String model, String ip, int port) {
        super(type, brand, model);
        this.ip = ip;
        this.port = port;
        this.value = 0;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void createTopic() throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "POST/TOPIC");
        request.accumulate("topic_id", this.toString());
        this.write(request.toString());
    }

    public void updateTopic(int value) throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "UPDATE/TOPIC");
        request.accumulate("topic_id", this.toString());
        request.accumulate("value", value);
        this.write(request.toString());
    }

    public void deleteTopic() throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "DELETE/TOPIC");
        request.accumulate("value", this.toString());
        this.write(request.toString());
    }

    public void disconnect() throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "DISCONNECT");
        this.write(request.toString());
    }

    public boolean reconnect() throws InterruptedException, IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "RECONNECT");
        
        this.configureConnection(this.ip, this.port);
        try {
            this.write(request.toString());
            this.online = true;
            System.out.println("Sucesso ao enviar");
            return true;
        } catch (IOException ex) {
            System.out.println("Falha ao enviar");
            return false;
        }
    }

    public int read() {
        return new Random().nextInt(99);
    }

    @Override
    public String toString() {
        return "PUB:" + this.type + ":" + this.brand + ":" + this.model;
    }

    public void connect() throws IOException {
        this.configureConnection(this.ip, this.port);
    }

}
