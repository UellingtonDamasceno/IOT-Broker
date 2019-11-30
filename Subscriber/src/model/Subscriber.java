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

    private ObservableList<Topic> allTopics;
    private List<String> subTopics;

    public Subscriber(String type, String brand, String model, String ip, int port) {
        super(type, brand, model);
        this.allTopics = FXCollections.observableArrayList();
        this.subTopics = new LinkedList();
        this.ip = ip;
        this.port = port;
    }

    public ObservableList getAllTopics() {
        return allTopics;
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

    public void subscribe(String topic) throws IOException, DeviceOfflineException, DeviceStandByException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "POST/SUB");
        request.accumulate("sub_id", this.toString());
        request.accumulate("topic_id", topic);
        this.send(request.toString());
    }

    public void unsubscribe(String topicID) throws IOException, DeviceStandByException, DeviceOfflineException{
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "DELETE/SUB");
        request.accumulate("topic_id", topicID);
        this.send(request.toString());
    }
    
    @Override
    public String toString() {
        return "SUB:" + this.type + ":" + this.brand + ":" + this.model;
    }

    public void addTopic() {
        this.allTopics.add(new Topic("Nome exemplo", 10, 25));
    }

    public void updateTopic(JSONObject response) {
        Topic topic = this.getTopic(response.getString("topic_id"));
        topic.addValue(response.getInt("value"));
        System.out.println("Topic atualizado");
    }

    public Topic getTopic(String topicID) throws NullPointerException{
        Iterator<Topic> iterator = this.allTopics.iterator();
        while(iterator.hasNext()){
            Topic currentTopic = iterator.next();
            if(currentTopic.getName().equals(topicID)){
                return currentTopic;
            }
        }
        throw new NullPointerException();
    }
}
