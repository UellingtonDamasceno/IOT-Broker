package controller.frontend;

import controller.backend.SubscriberController;
import facade.FacadeBackend;
import facade.FacadeFrontend;
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
import model.Subscriber;
import util.Settings;
import util.Settings.Connection;
import util.Settings.Brand;
import util.Settings.Models;
import util.Settings.Scenes;
import util.Settings.Types;

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
    private ComboBox<Types> cbTypeDevice;
    @FXML
    private ComboBox<Brand> cbBrandDevice;
    @FXML
    private ComboBox<Models> cbModelDevice;
    @FXML
    private Text txtStatus;
    @FXML
    private Button btnConnect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTypeDevice.getItems().addAll(Types.values());
        this.cbModelDevice.getItems().addAll(Models.values());
        this.cbBrandDevice.getItems().addAll(Brand.values());
    }

    @FXML
    private void nextScene(MouseEvent event) {
        String type = this.cbTypeDevice.getValue().toString();
        String brand = this.cbBrandDevice.getValue().toString();
        String model = this.cbModelDevice.getValue().toString();
        String ip = this.txtIP.getText();

        int port = Integer.parseInt(this.txtPort.getText());

        try {
            FacadeBackend.getInstance().connect(type, brand, model, ip, port);
            FacadeBackend.getInstance().updateListTopics();
            Thread.sleep(500);
            FacadeFrontend.getInstance().setSubscriberDashBoardController(Scenes.SUBSCRIBER_DASHBOARD);
            FacadeFrontend.getInstance().changeScreean(Scenes.SUBSCRIBER_DASHBOARD);
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
