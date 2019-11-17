package controller;

import java.util.Observable;
import java.util.Observer;
import model.Subscriber;

/**
 *
 * @author Uellington Conceição
 */
public class SubscriberController implements Observer{

    private Subscriber subscriber;
    


    public void process(String response){
        
    }
    
    @Override
    public void update(Observable o, Object o1) {
        System.out.println((String) o1);
    }
    
}
