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

    public Publisher(String type, String brand, String model) {
        super(type, brand, model);
        this.value = new Random().nextInt(0143);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void createTopic() throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("route", "POST/TOPIC");
        request.accumulate("topic_id", this.toString());
        this.write(request.toString());
    }

    public void updateTopic(int value) throws IOException {
        //Pega o value e manda.
        JSONObject request = new JSONObject();
        request.accumulate("route", "UPDATE/TOPIC");
        request.accumulate("topic_id", this.toString());
        request.accumulate("value", value);
        this.write(request.toString());
    }

    public void deleteTopic() throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("route", "DELETE/TOPIC");
        request.accumulate("value", this.toString());
        this.write(request.toString());
    }

    public int read() {
        return new Random().nextInt(99);
    }

    @Override
    public String toString() {
        return "PUB::"+this.type + "::" + this.brand + "::" + this.model;
    }

}
