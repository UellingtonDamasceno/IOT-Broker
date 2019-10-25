package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import model.exceptions.TopicExistException;

/**
 *
 * @author Uellington Damasceno
 */
public class Topic {

    private final String id;
    private final String name;
    private int subscriptions;

    private ServerSocket topic; //Coração do server
    private Map<String, Subscriper> subscripers; //Lista de clientes conectados nesse server

    public Topic(String id, String name) {
        this.id = id;
        this.name = name;
        this.subscriptions = 0;
        this.subscripers = new HashMap(31);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean thisServerExists() {
        return (this.topic != null);
    }

    public void addSubscriper(String subscriperID, Subscriper subscriper) throws TopicExistException {
        if (this.subscripers.containsKey(subscriperID)) {
            throw new TopicExistException();
        }
        this.subscripers.put(subscriperID, subscriper);
        this.subscriptions++;
    }

    public boolean createTopic(int port) throws IOException {
        boolean started = false;
        if (this.topic == null) {
            this.topic = new ServerSocket(port);
            started = true;
        }
        return started;
    }

    public boolean createTopic(int port, int ip) throws IOException {
        boolean started = false;
        if (this.topic == null) {
            this.topic = new ServerSocket(port, ip);
            started = true;
        }
        return started;
    }

    /**
     * Questão: Existe algum tratamento especial ao para fechar um server? Close
     * the server.
     *
     * @throws java.io.IOException
     */
    public void closeTopic() throws IOException {
        if (!this.topic.isClosed()) {
            this.topic.close();
        }
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
            return (this.topic.hashCode() == other.hashCode());
        }
        return false;
    }

    public void run() {
        try {
            Socket socket = this.topic.accept();
            Subscriper client = new Subscriper(socket);
            client.startClient();

            this.addSubscriper("Client" + this.subscriptions, client);
            new Thread(client).start();

            System.out.println("Novo tópico cadastrado");

        } catch (IOException ex) {
            System.out.println("Erro de IO");
        } catch (TopicExistException ex) {
            System.out.println("Topic já existe");
        }
    }

    public void notifyAllSubscripers(String response) {
        Iterator allSubscripers = this.subscripers.values().iterator();
        
        while (allSubscripers.hasNext()) {
            Subscriper currentSubscriper = (Subscriper) allSubscripers.next();

            try {
                currentSubscriper.write(response + '\n');
            } catch (IOException ex) {
                System.out.println("Erro ao enviar mensagem");
            }
        }
    }
}
