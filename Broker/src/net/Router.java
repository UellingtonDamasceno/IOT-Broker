package net;

import facade.Facade;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import org.json.JSONObject;

/**
 * Classe responsável por receber as solicitações e: - Adcionar novo cliente na
 * lista de espera (buffer). - Direcionar cada solicitação para devido
 * processamento.
 *
 * - Precisa-se do facade da aplicação e um buffer para funcionar como espera. -
 * Talvez seja interessante criar um controller de protocolo ... Para padronizar
 * as solicitações.
 *
 * @author Uellington Damasceno
 */
public class Router implements Observer {

    private static Router router;

    private Router() {
    }

    public static synchronized Router getInstance() {
        return (router == null) ? router = new Router() : router;
    }

    public synchronized void process(Client client, JSONObject request) {
        switch (request.getString("request_type")) {
            case "HTTP": {
                String response = this.httpRequest(client, request);
                client.write(response);

                System.out.println("======================================");
                System.out.println("Client: " + client);
                System.out.println("Request: " + request);
                System.out.println("Response: " + response);
                System.out.println("=======================================");
                break;
            }
            case "ERROR": {
                this.erroRequest(client, request);
                break;
            }
        }
    }

    private void erroRequest(Client client, JSONObject request) {
        switch (request.getString("error_id")) {
            case "000": {
                System.out.println("Cliente desconectado com sucesso!");
                break;
            }
        }
    }

    private String httpRequest(Client client, JSONObject request) {
        String id;

        switch (request.getString("route")) {
            case "POST/TOPIC": {

                id = request.getString("topic_id");
                Facade.getInstance().createTopic(id);
                Facade.getInstance().postPublisher(id, client);

                return "201"; //Created
            }
            case "UPDATE/TOPIC": {
                return Facade.getInstance().updateTopic(request);
            }
            case "POST/SUB": {
                id = request.getString("topic_id");
                Facade.getInstance().postSubscriber(id, client);
                return "200";
            }
            case "DELETE/SUB": {
                String subID = request.getString("sub_id");
                id = request.getString("topic_id");
                try {
                    Facade.getInstance().deleteSubscriper(id, subID);
                } catch (IOException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "200";
            }
            case "GET/TOPICS": {
                return Facade.getInstance().getTopics();
            }
            case "PUB/DISCONNECT": {
                try {
                    Facade.getInstance().deletePublisher(client.getIP());
                    //Facade.getInstance().updateTopic(request);
                } catch (IOException ex) {
                    return "120";
                }
                return "200";
            }
            case "RECONNECT": {
                return "RECONNECTED";
            }
            default: {
                return "404";
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        Client client = (Client) o;
        String request = (String) o1;
        JSONObject message = new JSONObject(request);
        this.process(client, message);
    }

}
