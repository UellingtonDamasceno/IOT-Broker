package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import model.Client;
import model.Topic;
import model.exceptions.ClientExistException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class TopicController {

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

    //Pode ser melhorado, utilizando retornos para indicar que já existe e tals. 
    public synchronized void postTopic(String topicID) {
        if (!this.topics.containsKey(topicID)) {
            Topic topic = new Topic(topicID);
            this.topics.put(topicID, topic);
        }
    }

    /**
     * Apaga a porra toda do tópico
     *
     * @param topicID
     * @throws IOException
     */
    public synchronized void deleteTopic(String topicID) throws IOException {
        if (this.topics.containsKey(topicID)) {
            Topic topic = this.topics.remove(topicID);
            topic.close();
        }
    }

    public synchronized String getTopics() {
        JSONObject topicsJSON = new JSONObject();
        topicsJSON.accumulate("response", "GET/TOPICS");
        topicsJSON.accumulate("qtdTopics", this.topics.size());
        topicsJSON.put("topics", this.topics.values());        
        return topicsJSON.toString();
    }

    /**
     * A solicitação deverá seguir o seguinte padrão {"topicID":
     * Tipo:Marca:Modelo, "Cliente": descrição do cliente}
     *
     * @param topicID
     * @param subscriber
     * @throws ClientExistException
     */
    public synchronized void postSubscriber(String topicID, Client subscriber) throws ClientExistException {
        Topic topic = this.topics.get(topicID);
        topic.patchSubscriper(subscriber.getIP(), subscriber);
    }

    public synchronized void postPublisher(String topicID, Client publisher) {
        Topic topic = this.topics.get(topicID);
        topic.patchPublisher(publisher.getIP(), publisher);
    }

    public synchronized void deleteSubscripe(String topicID, String subscriperID) throws IOException {
        Topic topic = topics.get(topicID);
        topic.deleteSubscriper(subscriperID);
    }

    public synchronized void updatePublishers(String topicID, String response) {
        this.topics.get(topicID).notifyAllPublisher(response);
    }

    public synchronized String updateSubscriper(String topicID, int response) {
        /**
         * Com atualização do publisher todos os publisher poderão receber
         * atualizações
         */
        if (this.topics.containsKey(topicID)) {
            this.topics.get(topicID).notifyAllSubscripers(String.valueOf(response));
            return "200";
        }else{
            return "404";
        }
    }

    /**
     * Fecha a porra toda! desconecta com o mundo! mas avisa ao pessoal.
     *
     * @throws IOException
     */
    public void disconect() throws IOException {
        //Funcionalidade especifica do broker
        for (Topic current : this.topics.values()) {
            current.close();
        }
        this.topics.clear();
    }

    public void deletePublisher(String publisherID) throws IOException {
        Iterator<Topic> iTopics = this.topics.values().iterator();
        Topic currentTopic;
        while(iTopics.hasNext()){
            currentTopic = (Topic) iTopics.next();
            if(currentTopic.containsPublisher(publisherID)){
                currentTopic.deletePublisher(publisherID);
            }
        }
    }

}
