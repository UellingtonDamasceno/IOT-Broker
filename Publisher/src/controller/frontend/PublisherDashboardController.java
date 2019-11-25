package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.Settings.Scenes;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class PublisherDashboardController implements Initializable, Observer {

    @FXML
    private Text txtName;
    @FXML
    private TextField txtNewValue;
    @FXML
    private Label lblValue;
    @FXML
    private Button btnRandom;
    @FXML
    private HBox hboxInput;
    @FXML
    private VBox vboxConfig;
    @FXML
    private TextField txtRate;
    @FXML
    private TextField txtComp;

    private Task<Void> task;
    private int rate;
    private String complement;
    private boolean isReading;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rate = 100;
        this.complement = "º";
        this.isReading = false;

        this.vboxConfig.setVisible(false);

        this.task = this.getTask();
        this.lblValue.textProperty().bind(task.messageProperty());
        this.txtName.setText(FacadeBackend.getInstance().getSmartDevice().toString());
        FacadeBackend.getInstance().setPublisherControllerObserver(this);
    }

    private Task getTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (isReading) {
                    int value = FacadeBackend.getInstance().getSmartDevice().read();
                    FacadeBackend.getInstance().getSmartDevice().updateTopic(value);
                    updateMessage(value + complement);
                    try {
                        Thread.sleep(10 * rate);
                    } catch (InterruptedException ex) {

                    }
                }
                return null;
            }
        };
    }

    @FXML
    private void randomize(ActionEvent event) {
        if (!isReading) {
            isReading = true;
            this.btnRandom.setText("Manual");
            this.hboxInput.setVisible(false);

            this.lblValue.textProperty().bind(this.task.messageProperty());
            new Thread(task).start();

        } else {
            this.btnRandom.setText("Automático");

            this.hboxInput.setVisible(true);

            this.task = this.getTask();
            isReading = false;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        try {
            //Necessario finalizar as coisas no broker. avisando que foi desconectdo.
            FacadeBackend.getInstance().disconnect();
        } catch (IOException ex) {
            System.exit(0);
        }
        System.exit(0);
    }

    @FXML
    private void update(ActionEvent event) {
        try {
            int value = Integer.parseInt(txtNewValue.getText());
            FacadeBackend.getInstance().updateValue(value);
            this.lblValue.textProperty().unbind();
            this.lblValue.setText(value + this.complement);
        } catch (IOException ex) {
            Logger.getLogger(PublisherDashboardController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showConfig(ActionEvent event) {
        if (!this.vboxConfig.isVisible()) {
            this.vboxConfig.setVisible(true);
        } else {
            this.vboxConfig.setVisible(false);
            event.consume();
        }
    }

    @FXML
    private void changeConfig(ActionEvent event) {
        if (!txtRate.getText().isEmpty() && !txtComp.getText().isEmpty()) {
            this.rate = Integer.parseInt(txtRate.getText());
            this.complement = txtComp.getText();
        }
        vboxConfig.setVisible(false);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            String request = (String) o1;
            if (request.equals("RECONNECT")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FacadeFrontend.getInstance().changeScreean(Scenes.RECONNECT_SCREEN);
                        } catch (Exception ex) {
                            FacadeFrontend.getInstance().showAlert(AlertType.ERROR, "Erro", "Erro carregar nova tela");
                        }
                    }
                });
            }
        }
    }
}
