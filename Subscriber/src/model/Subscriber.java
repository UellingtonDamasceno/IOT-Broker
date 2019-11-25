package model;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Subscriber extends Device{
    
    private List <Topic> allTopics; 
    private List <String> subTopics;
    
    public Subscriber(String type, String brand, String model) {
        super(type, brand, model);
        this.allTopics = new LinkedList();
    }
    
    public void updateTopics(JSONObject topics){
        if(this.allTopics.isEmpty()){
            JSONArray jsonArray = topics.getJSONArray("topics");
            Iterator<Object> iterator = jsonArray.iterator();
            System.out.println(jsonArray);
            
            while(iterator.hasNext()){
                Topic a = new Topic((JSONObject) iterator.next());
                System.out.println(a);
            }
        }
    }
    
    public void getTopics() throws IOException, DeviceOfflineException, DeviceStandByException{
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "GET/TOPICS");
        request.accumulate("sub_id", this.toString());
        this.send(request.toString());
    }
    
    public void subscripe(String topic) throws IOException, DeviceOfflineException, DeviceStandByException{
        JSONObject request = new JSONObject();
        request.accumulate("request_type", "HTTP");
        request.accumulate("route", "POST/SUB");
        request.accumulate("topic_id", topic);
        this.send(request.toString());
    }
    
    @Override
    public String toString(){
        return "SUB::" + this.type +"::"+this.brand+ "::" + this.model; 
    }
}
