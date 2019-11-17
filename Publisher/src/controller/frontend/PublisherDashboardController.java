package controller.frontend;

import facade.FacadeBackend;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class PublisherDashboardController implements Initializable {

    @FXML
    private Text txtName;
    @FXML
    private TextField txtNewValue;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblValue;

    @FXML
    private Button btnRandom;
    @FXML
    private Button btnUpdate;
    @FXML
    private HBox hboxInput;
    @FXML
    private Button btnConfig;
    @FXML
    private VBox vboxConfig;
    @FXML
    private TextField txtRate;
    @FXML
    private TextField txtComp;

    private boolean isReading;
    private Task<Void> task;
    private int rate;
    private String complement;

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

}
