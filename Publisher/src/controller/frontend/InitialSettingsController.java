package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.exceptions.NetworkNotConfiguredException;
import util.Settings.Connection;
import util.Settings.DevicesBrand;
import util.Settings.DevicesModels;
import util.Settings.DevicesTypes;
import util.Settings.Scenes;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class InitialSettingsController implements Initializable {

    @FXML
    private TextField txtIP;
    @FXML
    private TextField txtPort;
    @FXML
    private CheckBox cbPortDefault;
    @FXML
    private CheckBox cbLocalhost;
    @FXML
    private Button btnExit;
    @FXML
    private ComboBox<DevicesTypes> cbTypeDevice;
    @FXML
    private ComboBox<DevicesBrand> cbBrandDevice;
    @FXML
    private ComboBox<DevicesModels> cbModelDevice;
    @FXML
    private Text txtStatus;
    @FXML
    private Button btnConnect;

    private boolean sucess;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTypeDevice.getItems().addAll(DevicesTypes.values());
        this.cbModelDevice.getItems().addAll(DevicesModels.values());
        this.cbBrandDevice.getItems().addAll(DevicesBrand.values());
        this.cbTypeDevice.getSelectionModel().select(0);
        this.cbModelDevice.getSelectionModel().select(0);
        this.cbBrandDevice.getSelectionModel().select(0);
        this.sucess = true;
    }

    @FXML
    private void nextScene(MouseEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                btnConnect.setDisable(true);
                btnExit.setDisable(true);
            }
        });
        String type = this.cbTypeDevice.getValue().toString();
        String brand = this.cbBrandDevice.getValue().toString();
        String model = this.cbModelDevice.getValue().toString();
        String ip = this.txtIP.getText();
        int port = Integer.parseInt(this.txtPort.getText());

        Task<Void> task = new Task<Void>() {
            protected Void call() throws Exception {

                updateMessage("Criando seu despositivo.");
                Thread.sleep(1500);
                updateMessage("Despositivo criado!");
                Thread.sleep(500);
                updateMessage("Registrando seu dispositivo.");
                Thread.sleep(2000);
                try {
                    FacadeBackend.getInstance().connect(type, brand, model, ip, port);
                    FacadeBackend.getInstance().createTopic();
                } catch (IOException ex) {
                    updateMessage("Erro ao conectar com servidor!");
                    sucess = false;
                } catch (NetworkNotConfiguredException ex) {
                    sucess = false;
                    updateMessage("IP ou Porta invalidos");
                } catch (InterruptedException ex) {
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (sucess) {
                                FacadeFrontend.getInstance().changeScreean(Scenes.PUBLISHER_DASHBOARD);
                            } else {
                                btnConnect.setDisable(false);
                                btnExit.setDisable(false);
                            }
                        } catch (Exception ex) {
                        }
                    }
                });

                return null;
            }
        };

        this.txtStatus.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }

    @FXML
    private void exit(MouseEvent event) {
        try {
            FacadeBackend.getInstance().disconnect();
        } catch (IOException ex) {
            FacadeFrontend.getInstance().showAlert(AlertType.CONFIRMATION, "Erro", "Não foi "
                    + "Possivel desconectar");
        }
        System.exit(0);

    }

    @FXML
    private void setDefaultPort(MouseEvent event) {
        if (this.cbPortDefault.isSelected()) {
            this.txtPort.setText(Connection.DEFAULT_PORT.toString());
            this.txtPort.setDisable(true);
        } else {
            this.txtPort.setText("");
            this.txtPort.setDisable(false);
        }
    }

    @FXML
    private void setLocalhost(MouseEvent event) {
        if (this.cbLocalhost.isSelected()) {
            this.txtIP.setText(Connection.DEFAULT_IP.toString());
            this.txtIP.setDisable(true);
        } else {
            this.txtIP.setText("");
            this.txtIP.setDisable(false);
        }
    }

}
