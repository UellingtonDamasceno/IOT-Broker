package net;

import facade.Facade;
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

    private int processing;

    private static Router router;

    private Router() {
        this.processing = 0;
    }

    public static synchronized Router getInstance() {
        return (router == null) ? router = new Router() : router;
    }

    public synchronized String process(Client client, JSONObject request) {

        /*
            Validar de alguma forma se a solicitação deve ser processada. 
        Ideia: Usar o buffer para gerenciar a fila de requisições e usuarios;
            Como? 
         */
        switch (request.getString("route")) {
            case "POST/TOPIC": {
                try {
                    Facade.getInstance().createTopic(request.getString("topic_id"));
                    Facade.getInstance().postPublisher(request.getString("topic_id"), client);
                } catch (ClientExistException ex) {
                    //Facade.getInstance().deleteTopic(request.getString("topic_id"));
                    return "100";
                }
                return "200";
            }
            case "UPDATE/TOPIC": {
                Facade.getInstance().updateTopic(request.getString("topic_id"), request.getInt("value"));
                return "Atualizado!";
            }
            case "POST/SUB": {
                return "Sub criado";
            }
            case "GET/TOPICS": {
                return Facade.getInstance().getTopics();
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
        String response = this.process(client, message);
        client.write(response);

        System.out.println("O cliente::" + client);
        System.out.println("Solicitação::" + request);
    }

}
