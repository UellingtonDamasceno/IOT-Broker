package model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Subscriber extends Device{
    
    private List <String> topics; 
    
    public Subscriber(String type, String brand, String model) {
        super(type, brand, model);
        this.topics = new LinkedList();
    }
    
    public void getTopics() throws IOException{
        JSONObject request = new JSONObject();
        request.accumulate("route", "GET/TOPICS");
        request.accumulate("sub_id", this.toString());
        this.write(request.toString());
    }
    
    public void subscripe(String topic) throws IOException{
        this.write("");
    }
    
    @Override
    public String toString(){
        return "SUB::" + this.type +"::"+this.brand+ "::" + this.model; 
    }
}
