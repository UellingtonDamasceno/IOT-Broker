package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import net.Server;

/**
 *
 * @author Uellington Damasceno
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            new Server().start(9999);
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
