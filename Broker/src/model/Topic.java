package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import model.exceptions.ClientExistException;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Topic {

    private final String id;
    private Map<String, Client> publishers;
    private Map<String, Client> subscripers; //Lista de clientes conectados nesse server

    public Topic(String id) {
        this.id = id;
        this.subscripers = new HashMap();
        this.publishers = new HashMap();
    }

    public String getId() {
        return this.id;
    }

    public void patchPublisher(String publisherID, Client publisher) throws ClientExistException {
        this.patch(publisherID, this.publishers, publisher);
    }

    public void patchSubscriper(String subscriperID, Client subscriper) throws ClientExistException {
        this.patch(subscriperID, this.subscripers, subscriper);
    }

    public void deletePublisher(String publisherID) throws IOException {
        this.delete(publisherID, this.publishers);
    }

    public void deleteSubscriper(String subscriperID) throws IOException {
        this.delete(subscriperID, this.subscripers);
    }

    public void notifyAllPublisher(String response) {
        this.notifyAll(this.publishers.values().iterator(), response);
    }

    public void notifyAllSubscripers(String response) {
        this.notifyAll(this.subscripers.values().iterator(), response);
    }

    private void delete(String id, Map<String, Client> customers) throws IOException {
        Client client = customers.remove(id);
        client.deleteObservers();
        client.close();
    }

    private void patch(String id, Map<String, Client> customers, Client client) throws ClientExistException {
        if (customers.containsKey(id)) {
            throw new ClientExistException();
        }
        customers.put(id, client);
    }

    public void close() throws IOException {
        //this.close(publishers);
        this.close(subscripers);
    }

    private void notifyAll(Iterator<Client> customers, String response) {
        while (customers.hasNext()) {
            Client currentClient = customers.next();
            currentClient.write(response);
        }
    }

    private void close(Map<String, Client> customers) throws IOException {
        for (Client currentClient : customers.values()) {
            currentClient.write("Mensagem para informar que está sendo finalizado");
            currentClient.close();
            /**
             * Método close connection dos subscripers também está avisando que
             * a conexão está sendo fechada.
             *
             * --- Tbm pode ter algo de errado nesse método. será necessario
             * ficar atento a condição de corrida é possivel que estejamos
             * tentando manipular uma estrutura de dados enquanto estamos
             * pecorrendo-a
             *
             */

        }
        customers.clear();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Topic) {
            Topic other = (Topic) obj;
            return (this.hashCode() == other.hashCode());
        }
        return false;
    }
    
    @Override
    public String toString(){
        JSONObject topic = new JSONObject();
        topic.append("id", this.id);
        topic.put("publishers", this.publishers);
        topic.put("subscribers", this.subscripers);
        return topic.toString();
    }
}
