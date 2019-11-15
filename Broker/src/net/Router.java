package net;

import controller.RequestController;
import facade.Facade;
import java.util.Observable;
import java.util.Observer;
import model.Client;

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
public class Router implements Observer{

    private final int MAX_PROCESSES = 5;
    private int processing;
    
    private static Router router;

    private Router() {
        this.processing = 0;
    }

    public static synchronized Router getInstance() {
        return (router == null) ? router = new Router() : router;
    }

    
    public synchronized String process(Client client, String request) {
        
        /*
            Validar de alguma forma se a solicitação deve ser processada. 
        Ideia: Usar o buffer para gerenciar a fila de requisições e usuarios;
            Como? 
         */
        switch (request) {
            case "POST/TOPIC": {
               //Facade.getInstance().createTopic(request);
                return "200";
            }case "POST/SUB":{
                return "Sub criado";
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
        String response = this.process(client, request);
        client.write(response);
    }
    
}
