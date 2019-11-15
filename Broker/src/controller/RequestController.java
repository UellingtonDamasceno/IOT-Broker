package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import model.Client;

/**
 *
 * @author Uellington Damasceno
 */
public class RequestController extends Observable implements Observer{

    private Map<String, Client> lobby;
    private Map<String, String> requests;

    public RequestController() {
        this.lobby = new HashMap();
        this.requests = new HashMap();
    }

    public void addClient(Client client) {
        this.lobby.put(client.getIP(), client);
    }

    public Client removeClient(String clientID) {
        return this.lobby.remove(clientID);
    }

    public void addRequest(String requester, String request) {
        this.requests.put(requester, request);
    }

    public String removeRequest(String request) {
        return this.requests.remove(request);
    }

    @Override
    public void update(Observable o, Object o1){
        
    }
}
