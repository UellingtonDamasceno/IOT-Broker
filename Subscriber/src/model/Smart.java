package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.lang.Runnable;
import java.util.Observable;

/**
 *
 * @author Uellington Damasceno
 */
abstract public class Smart extends Observable implements Runnable {

    private Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    protected boolean online;

    protected boolean isConnected() {
        return (this.socket != null);
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    protected void configureConnection(Socket socket) throws IOException {
        this.socket = socket;

        InputStreamReader isr = new InputStreamReader(this.socket.getInputStream());
        this.reader = new BufferedReader(isr);

        OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream());
        this.writer = new BufferedWriter(osw);
    }

    private String read() throws IOException {
        StringBuilder message = new StringBuilder("");
        int buffer = -1;

        while (buffer != '\n') {
            buffer = this.reader.read();
            message.append(((char) buffer));
        }
        return message.toString().trim();
    }

    protected void write(String request) throws IOException {
        this.writer.write(request + '\n');
        this.writer.flush();
    }

    public void close() throws IOException {
        /**
         * Avisou ao server que ele será desconectado?
         */
        this.writer.close();
        this.reader.close();
        this.socket.close();
    }

    @Override
    public void run() {
        String message;
        boolean solving = false;
        while (this.online) {
            try {
                message = this.read();
                this.setChanged();
                this.notifyObservers(message);
                solving = false;
            } catch (IOException ex) {
                try {
                    if (!solving) {
                        solving = true;
                        this.setChanged();
                        this.notifyObservers("SERVER:CLOSE");
                    }
                    Thread.sleep(500);
                } catch (InterruptedException ex1) {
                }
            }
        }
    }
}
