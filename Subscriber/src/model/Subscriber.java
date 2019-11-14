package model;

import java.io.IOException;

/**
 *
 * @author Uellington Damasceno
 */
public class Subscriber extends Device{
    
    public Subscriber(String type, String brand, String model) {
        super(type, brand, model);
    }
    
    public void getTopics() throws IOException{
        this.write("");
    }
    
    public void subscripe(String topic) throws IOException{
        this.write("");
    }
}
