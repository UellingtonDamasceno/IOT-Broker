package net;

import facade.Facade;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.Client;
import model.exceptions.ClientExistException;
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
        int value;

        switch (request.getString("route")) {
            case "POST/TOPIC": {
                try {
                    id = request.getString("topic_id");
                    Facade.getInstance().createTopic(id);
                    Facade.getInstance().postPublisher(id, client);
                } catch (ClientExistException ex) {
                    return "100";
                }
                return "201"; //Created
            }
            case "UPDATE/TOPIC": {

                id = request.getString("topic_id");
                value = request.getInt("value");

                return Facade.getInstance().updateTopic(id, value);
            }
            case "POST/SUB": {
                try {
                    id = request.getString("topic_id");
                    Facade.getInstance().postSubscriber(id, client);
                } catch (ClientExistException ex) {
                    return "Sub criado";
                }
                return "200";
            }
            case "GET/TOPICS": {
                return Facade.getInstance().getTopics();
            }
            case "DISCONNECT": {
                try {
                    Facade.getInstance().deletePublisher(client.getIP());
                } catch (IOException ex) {
                    System.out.println("Não desconectou");
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
