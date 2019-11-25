package controller.backend;

import java.util.Observable;
import java.util.Observer;
import model.Subscriber;
import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class SubscriberController implements Observer{

    private Subscriber subscriber;
    

    public void setSubscriber(Subscriber subscriber){
        this.subscriber = subscriber;
        this.subscriber.addObserver(this);
    }
    
    private void process(JSONObject response){
        System.out.println(response.toString());
        switch(response.getString("response")){
            case "GET/TOPICS":{
                this.subscriber.updateTopics(response);
                break;
            }
        }
    }
    
    @Override
    public void update(Observable o, Object o1) {
        System.out.println("Recebi mensagem");
        System.out.println((String)o1);
        this.process(new JSONObject(o1));
    }
    
}
