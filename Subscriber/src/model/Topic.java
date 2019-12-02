package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class Topic {
    
    private SimpleStringProperty name;
    private SimpleIntegerProperty pubs;
    private SimpleIntegerProperty subs;
    private SimpleBooleanProperty subscriber;
    
    public Topic(String name, int pubs, int subs){
        this.name = new SimpleStringProperty(name);
        this.pubs = new SimpleIntegerProperty(pubs);
        this.subs = new SimpleIntegerProperty(subs);
        this.subscriber = new SimpleBooleanProperty(false);
    }
    
    public Topic (JSONObject object){
        this(object.getString("topicName"), object.getInt("publishers"), object.getInt("subscribers"));
    }
       
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPubs() {
        return pubs.get();
    }

    public void setPubs(int pubs) {
        this.pubs.set(pubs);
    }

    public int getSubs() {
        return subs.get();
    }

    public void setSubs(int subs) {
        this.subs.set(subs);
    }
    
    public void setSubscriber(boolean subscriber){
        this.subscriber.set(subscriber);
    }
    
    public boolean isSubscriber(){
        return this.subscriber.get();
    }
    
    @Override
    public String toString(){
        return "nome: "+ this.getName() + "::pubs: "+this.getPubs() + "::subs: "+this.getSubs();
    }
    
    @Override
    public boolean equals(Object obj){ 
        if(obj instanceof Topic){
            Topic other = (Topic) obj;
            return this.hashCode() == other.hashCode();
        }
        return false;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.getName().hashCode();
        return hash;
    }
}
