package model;

import model.exceptions.PublisherNotExistException;
import model.exceptions.UnintializedObjectException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Uellington Damasceno
 */
public class Publisher implements Runnable {
    private final String id;
    
    private Socket socket;
    
    private BufferedReader reader;
    private BufferedWriter writer;
    
    private boolean online;
    
    public Publisher(String id){
        this.id = id;
        this.online = false;
    }
    
    public boolean exist(){
        return online;
    }   
       
    public void startPublisher(Socket socket) throws IOException, PublisherNotExistException{
        if(!this.exist()){
            this.socket = socket;
            
            InputStreamReader isr = new InputStreamReader(this.socket.getInputStream());
            this.reader = new BufferedReader(isr);
            
            OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream());
            this.writer = new BufferedWriter(osw);
            
            this.online = true;
        }
        throw new PublisherNotExistException();
    }

    public String read() throws IOException, UnintializedObjectException{
        if(this.online){
            StringBuilder message = new StringBuilder("");
            int buffer = -1;
            
            while(buffer != '\n'){
                buffer = this.reader.read();
                message.append(((char)buffer));
            }
            return message.toString();
        }
        throw new UnintializedObjectException();
    }
    
    public void write(String response) throws IOException, UnintializedObjectException{
        if(this.online){
            this.writer.write(response);
            this.writer.flush();
        }
        throw new UnintializedObjectException();
    }
    
    
    public void closeConnection() throws IOException{
        this.writer.close();
        this.reader.close();
        this.socket.close();
        this.online = false;
    }
    
    @Override
    public void run() {
        String message;
        while(true){
            try {
                message = this.read();
            } catch (IOException ex) {
                System.out.println("Desconectado");
            } catch (UnintializedObjectException ex) {
                System.out.println("Inicialize o objeto!");
            }
        }
    }
}
