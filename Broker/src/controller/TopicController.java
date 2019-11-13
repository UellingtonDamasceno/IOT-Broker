package controller;

import model.exceptions.InexistentKeyException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import model.Client;
import model.Topic;
import model.exceptions.ClientExistException;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class TopicController implements Observer{

    /**
     * permitir que o publisher fique offline (Avisando aos subscripers quando
     * desconectar e quando voltar a ficar online)
     *
     * Remover um publisher e todo seu topico
     *
     * Criar um método que gera um id para novos despositivos
     *
     * Criar uma sala de espera
     */
    private Map<String, Topic> topics;

    public TopicController() {
        this.topics = new HashMap();
    }

    //
    public synchronized void postTopic(String topicID) {
        Topic topic = new Topic(topicID);
        this.topics.put(topicID, topic);
    }

    /**
     * Apaga a porra toda do tópico
     *
     * @param topicID
     * @throws IOException
     * @throws InexistentKeyException
     */
    public synchronized void deleteTopic(String topicID) throws IOException, InexistentKeyException {
        if (this.topics.containsKey(topicID)) {
            Topic topic = this.topics.remove(topicID);
            topic.close();
        }
    }

    public synchronized String getTopics() {
        JSONObject topicsJSON = new JSONObject();
        topicsJSON.accumulate("hasTopic", !this.topics.isEmpty());
        topicsJSON.put("topics", this.topics);
        return topicsJSON.toString();
    }

    public synchronized void postSubscripe(String topicID, Client subscriber) throws ClientExistException {
        Topic topic = this.topics.get(topicID);
        topic.patchSubscriper(subscriber.getID(), subscriber);
    }
     
    public synchronized void postPublisher( String topicID, Client publisher) throws ClientExistException{
        Topic topic = this.topics.get(topicID);
        topic.patchPublisher(publisher.getID(), publisher);
    }
    
    public synchronized void deleteSubscripe(String topicID, String subscriperID) throws IOException {
        Topic topic = topics.get(topicID);
        topic.deleteSubscriper(subscriperID);
    }
    
    public synchronized void updatePublishers(String topicID, String response){
        this.topics.get(topicID).notifyAllPublisher(response);
    }
    
    public synchronized void updateSubscriper(String topicID, String response) {
        /**
         * Com atualização do publisher todos os publisher (Não bloqueados)
         * poderão receber atualizações
         */
        this.topics.get(topicID).notifyAllSubscripers(response);
    }
    
    /**
     * Fecha a porra toda! desconecta com o mundo! mas avisa ao pessoal. 
     * @throws IOException 
     */
    public void disconect() throws IOException {
        //Funcionalidade especifica do broker
        for (Topic current : this.topics.values()) {
            current.close();
        }
        this.topics.clear();
    }
    
        /**
     * Outra opção seria fazer o controller de tópico
     */
    @Override
    public void update(Observable observable, Object object) {
        String request = (String) object;
        String response;

        switch (request) {
            case "POST/TOPIC": {
                
                //response = this.createTopic(request);
                break;
            }case "POST/PUBLISHER":{
                //response = this.createPublisher(request);
            }case "POST/SUBSCRIBER":{
                //response = this.createSubscriper(request);
            }case "GET/TOPICS":{
                
            }case "DELETE/TOPIC":{
            }
            default: {
                response = "404";
            }
        }
        //((Client)observable).write(response);
    }
}
