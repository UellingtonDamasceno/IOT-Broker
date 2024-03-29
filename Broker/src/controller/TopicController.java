package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import model.Client;
import model.Topic;
import model.exceptions.ClientExistException;
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
        topicsJSON.accumulate("route", "GET/TOPICS");
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
    public synchronized void postSubscriber(String topicID, Client subscriber) {
        if (this.topics.containsKey(topicID)) {
            Topic topic = this.topics.get(topicID);
            if (!topic.containsPublisher(subscriber.getIP())) {
                topic.patchSubscriper(subscriber.getIP(), subscriber);
            }
        }
    }

    public synchronized void postPublisher(String topicID, Client publisher) {
        Topic topic = this.topics.get(topicID);
        topic.patchPublisher(publisher.getIP(), publisher);
    }

    public synchronized void deleteSubscripe(String topicID, String subscriperID) throws IOException {
        Topic topic = topics.get(topicID);
        if (topic != null) {
            topic.deleteSubscriper(subscriperID);
        }
    }

    public synchronized void updatePublishers(String topicID, String response) {
        this.topics.get(topicID).notifyAllPublisher(response);
    }

    public synchronized void finalizeTopicsWithoutPublishers() {
        Iterator<Topic> iterator = this.topics.values().iterator();
        JSONObject response = new JSONObject();
        response.accumulate("route", "TOPIC/CLOSE");
        while (iterator.hasNext()) {
            Topic currentTopic = iterator.next();
            if (currentTopic.getPublishers() == 0) {
                response.accumulate("topic_id", currentTopic.getTopicName());
                currentTopic.notifyAllSubscripers(response);
            }
        }
    }

    public synchronized String updateSubscribers(JSONObject response) {
        String topicID = response.getString("topic_id");
        if (this.topics.containsKey(response.getString("topic_id"))) {
            this.topics.get(topicID).notifyAllSubscripers(response);
            return "200";
        } else {
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
        while (iTopics.hasNext()) {
            currentTopic = (Topic) iTopics.next();
            if (currentTopic.containsPublisher(publisherID)) {
                currentTopic.deletePublisher(publisherID);
            }
        }
    }

}
