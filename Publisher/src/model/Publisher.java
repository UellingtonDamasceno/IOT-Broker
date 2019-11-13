package model;

import java.io.IOException;

/**
 *
 * @author Uellington Damasceno
 */
public class Publisher extends Device{

    public Publisher(String type, String brand, String model) {
        super(type, brand, model);
    }
    

    public void createTopic(String request) throws IOException{
        this.write(request);
    }
    
    public void updateTopic(String mesage) throws IOException{
        this.write(mesage);
    }
    
    public void deleteTopic() throws IOException{
        this.write("");
    }
}
