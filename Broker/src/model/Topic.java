package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Topic {
    
    private String topicName;

    private int publisherSize;
    private int subscripersSize;

    private Map<String, Client> publishers;
    private Map<String, Client> subscripers; //Lista de clientes conectados nesse server

    public Topic(String topicName) {
        this.topicName = topicName;
        this.subscripers = new HashMap();
        this.publishers = new HashMap();
    }
    public String getTopicName(){
        return this.topicName;
    }
    
    public int getPublishers(){
        return this.publisherSize;
    }
    
    public int getSubscribers(){
        return this.subscripersSize;
    }
        
    public boolean containsPublisher(String clientID){
        return this.publishers.containsKey(clientID);
    }
    
    
    /**
     * Adciona um novo publisher no tópico
     * @param publisherIP ip e porta do publisher.
     * @param publisher publisher deverá ser adcionado.
     */
    public void patchPublisher(String publisherIP, Client publisher) {
        this.publishers.put(publisherIP, publisher);
        this.publisherSize++;
    }

    public void patchSubscriper(String subscriperIP, Client subscriper) {
        this.subscripers.put(subscriperIP, subscriper);
        this.subscripersSize++;
    }

    public void deletePublisher(String publisherIP) throws IOException {
        this.delete(publisherIP, this.publishers);
        this.publisherSize--;
    }

    public void deleteSubscriper(String subscriperIP) throws IOException {
        this.delete(subscriperIP, this.subscripers);
        this.subscripersSize--;
    }

    public void notifyAllPublisher(String response) {
        this.notifyAll(this.publishers.values().iterator(), response);
    }

    public void notifyAllSubscripers(JSONObject response) {
        this.notifyAll(this.subscripers.values().iterator(), response.toString());
    }

    private void delete(String ip, Map<String, Client> customers) throws IOException {
        Client client = customers.remove(ip);
        client.deleteObservers();
        client.close();
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
    public String toString() {
        JSONObject topic = new JSONObject();
        topic.accumulate("publisher_size", this.publisherSize);
        topic.accumulate("subscriber_size", this.subscripersSize);
        return topic.toString();
    }
}
