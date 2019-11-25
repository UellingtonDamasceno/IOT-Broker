package model;

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
    
    public Topic(String name, int pubs, int subs){
        this.name = new SimpleStringProperty(name);
        this.pubs = new SimpleIntegerProperty(pubs);
        this.subs = new SimpleIntegerProperty(subs);
    }

    public Topic (JSONObject object){
        this(object.getString("id"), object.getInt("publishers"), object.getInt("subscribers"));
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
    
    public String toString(){
        return "nome: "+ this.getName() + "pubs: "+this.getPubs() + "subs"+this.getSubs();
    }
    
}
