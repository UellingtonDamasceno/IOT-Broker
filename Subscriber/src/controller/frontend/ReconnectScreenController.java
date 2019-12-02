package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import util.Settings.Scenes;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class ReconnectScreenController implements Initializable{

    @FXML
    private Button btnExit;
    @FXML
    private Button btnReconnect;
    @FXML
    private Label lblStatus;
    @FXML
    private ProgressIndicator progressBar;
    private boolean isReconnect;
    private Task<Void> task;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.progressBar.setVisible(false);
        this.isReconnect = false;
        this.task = this.getTask();
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void reconnect(ActionEvent event) {
        if (!this.isReconnect) {
            this.isReconnect = true;
            this.progressBar.setVisible(true);
            this.btnReconnect.setText("Cancelar");
            this.btnExit.setDisable(true);
            this.lblStatus.textProperty().bind(this.task.messageProperty());
            new Thread(task).start();
        } else {
            this.isReconnect = false;
            this.btnReconnect.setText("Reconectar");
            this.progressBar.setVisible(false);
            this.btnExit.setDisable(false);
            this.lblStatus.textProperty().unbind();
            this.lblStatus.setText("Falha na conexão!");
            this.task = getTask();
        }
    }

    private Task<Void> getTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                boolean response = false;
                while (isReconnect && !response) {
                    updateMessage("Reconectando.");
                    Thread.sleep(500);
                    updateMessage("Reconectando..");
                    Thread.sleep(500);
                    updateMessage("Reconectando...");
                    Thread.sleep(500);
                    response = FacadeBackend.getInstance().restart();
                }
                if (response) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FacadeFrontend.getInstance().changeScreean(Scenes.SUBSCRIBER_DASHBOARD);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
                return null;
            }
        };
    }

}
