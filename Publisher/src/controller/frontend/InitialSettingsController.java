package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTypeDevice.getItems().addAll(DevicesTypes.values());
        this.cbModelDevice.getItems().addAll(DevicesModels.values());
        this.cbBrandDevice.getItems().addAll(DevicesBrand.values());
    }

    @FXML
    private void nextScene(MouseEvent event) {
        DevicesTypes type = this.cbTypeDevice.getValue();
        DevicesBrand brand = this.cbBrandDevice.getValue();
        DevicesModels model = this.cbModelDevice.getValue();
        String ip = this.txtIP.getText();
        
        int port = Integer.parseInt(this.txtPort.getText());
        try {
            this.txtStatus.setText("Criando seu Smartdevice.");
            FacadeBackend.getInstance().connect(type, brand, model, ip, port);
            this.txtStatus.setText("Registrando seu dispositivo.");
            FacadeFrontend.getInstance().changeScreean(Scenes.DEVICE_DASHBOARD);
        } catch (IOException ex) {
            Logger.getLogger(InitialSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InitialSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void setDefaultPort(MouseEvent event) {
        if (this.cbPortDefault.isSelected()) {
            this.txtPort.setText(Connection.DEFAULT_PORT.toString());
            this.txtPort.setDisable(true);
        }else{
            this.txtPort.setText("");
            this.txtPort.setDisable(false);
        }
    }

    @FXML
    private void setLocalhost(MouseEvent event) {
        if (this.cbLocalhost.isSelected()) {
            this.txtIP.setText(Connection.DEFAULT_IP.toString());
            this.txtIP.setDisable(true);
        }else{
            this.txtIP.setText("");
            this.txtIP.setDisable(false);
        }
    }

}
