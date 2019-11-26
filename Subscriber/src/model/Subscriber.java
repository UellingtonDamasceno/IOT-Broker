package model;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Subscriber extends Device {

    private String ip;
    private int port;

    private List<Topic> allTopics;
    private List<String> subTopics;

    public Subscriber(String type, String brand, String model, String ip, int port) {
        super(type, brand, model);
        this.allTopics = new LinkedList();
        this.subTopics = new LinkedList();
        this.ip = ip;
        this.port = port;
    }

    public ObservableList getAllTopics() {
        return FXCollections.observableArrayList(allTopics);
    }

    public void connect() throws IOException {
        this.configureConnection(ip, port);
    }

    public void disconnect() throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "DISCONNECT");
        this.write(request.toString());
    }

    public boolean reconnect() throws InterruptedException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "RECONNECT");

        try {
            this.configureConnection(this.ip, this.port);
            this.write(request.toString());
            this.online = true;
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    public void updateTopics(JSONObject topics) {
        JSONArray jsonArray = topics.getJSONArray("topics");
        Iterator<Object> iterator = jsonArray.iterator();
        JSONObject currentTopic;
        while (iterator.hasNext()) {
            currentTopic = (JSONObject) iterator.next();
            allTopics.add(new Topic(currentTopic));
        }
    }

    public void getTopics() throws IOException, DeviceOfflineException, DeviceStandByException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "GET/TOPICS");
        request.accumulate("sub_id", this.toString());
        this.send(request.toString());
    }

    public void subscripe(String topic) throws IOException, DeviceOfflineException, DeviceStandByException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "POST/SUB");
        request.accumulate("topic_id", topic);
        this.send(request.toString());
    }

    @Override
    public String toString() {
        return "SUB::" + this.type + "::" + this.brand + "::" + this.model;
    }
}
