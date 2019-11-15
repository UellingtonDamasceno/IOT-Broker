/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.frontend;

import facade.FacadeBackend;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class PublisherDashboardController implements Initializable {

    @FXML
    private Text txtName;
    @FXML
    private Button btnRandom;
    @FXML
    private Button btnOnOff;
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtNewValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void randomize(ActionEvent event) {

    }

    @FXML
    private void onOff(ActionEvent event) {

    }

    @FXML
    private void exit(ActionEvent event) {
        //Necessario finalizar as coisas no broker. avisando que foi desconectdo.
        System.exit(0);
    }

    @FXML
    private void update(ActionEvent event) {
        try {
            FacadeBackend.getInstance().getSmartDevice().send(this.txtNewValue.getText());
        } catch (IOException ex) {
            Logger.getLogger(PublisherDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceOfflineException ex) {
            Logger.getLogger(PublisherDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceStandByException ex) {
            Logger.getLogger(PublisherDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
