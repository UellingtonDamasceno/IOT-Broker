package model;

import java.io.IOException;
import java.util.Iterator;
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

    public Subscriber(String type, String brand, String model, String ip, int port) {
        super(type, brand, model);
        this.allTopics = FXCollections.observableArrayList();
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
        int index;
        Topic newTopic, topicAux;
        JSONObject currentTopic;
        JSONArray jsonArray = topics.getJSONArray("topics");
        Iterator<Object> iterator = jsonArray.iterator();

        while (iterator.hasNext()) {
            currentTopic = (JSONObject) iterator.next();
            newTopic = new Topic(currentTopic);
            index = allTopics.indexOf(newTopic);
            if (index >= 0) {
                topicAux = allTopics.get(index);
                topicAux.setPubs(newTopic.getPubs());
                topicAux.setSubs(newTopic.getSubs());
            } else {
                allTopics.add(newTopic);
            }
        }
    }

    public void getTopics() throws IOException, DeviceOfflineException, DeviceStandByException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "GET/TOPICS");
        request.accumulate("sub_id", this.toString());
        this.send(request.toString());
    }

    public void subscribe(String topicID) throws IOException, DeviceOfflineException, DeviceStandByException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "POST/SUB");
        request.accumulate("sub_id", this.toString());
        request.accumulate("topic_id", topicID);
        this.send(request.toString());
        this.setSubscribe(topicID, true);
    }

    public void unsubscribe(String topicID) throws IOException, DeviceStandByException, DeviceOfflineException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "DELETE/SUB");
        request.accumulate("topic_id", topicID);
        request.accumulate("sub_id", this.toString());
        this.send(request.toString());
        this.setSubscribe(topicID, false);
    }

    private void setSubscribe(String topicID, boolean status){
        for (Topic currentTopic : allTopics) {
            if(currentTopic.getName().equals(topicID)){
                currentTopic.setSubscriber(status);
            }
        }
    }
    
    @Override
    public String toString() {
        return "SUB:" + this.type + ":" + this.brand + ":" + this.model;
    }

    public void offPublisher(Topic topic) throws IOException {
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "PUBS/OFF");
        request.accumulate("topic_id", topic.getName());
        this.write(request.toString());
    }

}
