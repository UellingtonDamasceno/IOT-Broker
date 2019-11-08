package view;

import net.ServerBroker;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            ServerBroker.getInstance().initialize(9999);
        } catch (IOException ex) {
            System.out.println("Não inicializado");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
