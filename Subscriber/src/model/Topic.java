package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class Topic {
    
    private SimpleStringProperty name;
    private SimpleIntegerProperty pubs;
    private SimpleIntegerProperty subs;
    private ObservableList<Integer> values;
    
    public Topic(String name, int pubs, int subs){
        this.name = new SimpleStringProperty(name);
        this.pubs = new SimpleIntegerProperty(pubs);
        this.subs = new SimpleIntegerProperty(subs);
        this.values = FXCollections.observableArrayList();
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
    
    public void addValue(int value){
        this.values.add(value);
        System.out.println(this.values);
    }
    
    @Override
    public String toString(){
        return "\nnome: "+ this.getName() + "\npubs: "+this.getPubs() + "\nsubs: "+this.getSubs();
    }
    
}
