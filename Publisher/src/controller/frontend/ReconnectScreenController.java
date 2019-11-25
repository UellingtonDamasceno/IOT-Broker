package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import util.Settings;
import util.Settings.Scenes;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class ReconnectScreenController implements Initializable, Observer {

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
        FacadeBackend.getInstance().setPublisherControllerObserver(this);
        this.progressBar.setVisible(false);
        this.isReconnect = false;
        this.task = this.getTask();
        this.lblStatus.textProperty().bind(this.task.messageProperty());
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
                System.out.println("Saiu do laço");
                if (response) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FacadeFrontend.getInstance().changeScreean(Scenes.PUBLISHER_DASHBOARD);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                FacadeFrontend.getInstance().showAlert(Alert.AlertType.ERROR, "Erro", "Erro carregar nova tela");
                            }
                        }
                    });
                }
                return null;
            }
        };
    }

    @Override
    public void update(Observable o, Object o1) {
        System.out.println("Recebeu uma mensagem");
        if (o1 instanceof String) {
            String request = (String) o1;
            if (request.equals("RECONNECTED")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FacadeFrontend.getInstance().changeScreean(Settings.Scenes.PUBLISHER_DASHBOARD);
                        } catch (Exception ex) {
                            FacadeFrontend.getInstance().showAlert(Alert.AlertType.ERROR, "Erro", "Erro carregar nova tela");
                        }
                    }
                });
            }
        }
    }
}
