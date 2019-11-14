package controller;

import facade.FacadeBackend;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Device;
import model.exceptions.NetworkNotConfiguredException;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class DeviceDashboardController implements Initializable, Observer {

    @FXML
    private Button btnInfo;
    @FXML
    private Button btnStandby;
    @FXML
    private Button btnRestart;
    @FXML
    private Button btnOnOff;
    @FXML
    private VBox vboxContent;

    private Device smart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.smart = FacadeBackend.getInstance().getSmartDevice();
        smart.addObserver(this);
    }

    @FXML
    private void showInfo(MouseEvent event) {
    }

    @FXML
    private void standByMode(MouseEvent event) {
        if (!this.smart.inStadby()) {
            this.smart.standBy();
            this.btnInfo.setDisable(true);
            this.btnRestart.setDisable(true);
            this.btnStandby.setDisable(true);
            this.btnStandby.setText("StandBy: On");
            this.btnOnOff.setText("Ligar");
        }
    }

    @FXML
    private void restart(MouseEvent event) {
        try {
            FacadeBackend.getInstance().restart();
        } catch (IOException ex) {
            Logger.getLogger(DeviceDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NetworkNotConfiguredException ex) {
            Logger.getLogger(DeviceDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onoff(MouseEvent event) {
        try {
            if (this.smart.isOnline() && !this.smart.inStadby()) {
                this.btnInfo.setDisable(true);
                this.btnRestart.setDisable(true);
                this.btnStandby.setDisable(true);
                this.btnOnOff.setText("Ligar");
                this.smart.off();

            } else {
                this.btnInfo.setDisable(false);
                this.btnRestart.setDisable(false);
                this.btnStandby.setDisable(false);
                this.btnStandby.setText("StandBy: Off");
                this.btnOnOff.setText("Desligar");
                this.smart.on();
            }
        } catch (IOException ex) {
            Logger.getLogger(DeviceDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NetworkNotConfiguredException ex) {
            Logger.getLogger(DeviceDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object o1) {
    }

}
