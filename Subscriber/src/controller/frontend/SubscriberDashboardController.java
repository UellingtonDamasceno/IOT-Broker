package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import util.Settings.Scenes;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class SubscriberDashboardController implements Initializable {

    @FXML
    private Button btnOnOff;
    @FXML
    private Button btnSubs;
    @FXML
    private Button btnAllTopics;
    @FXML
    private VBox vBoxCenterContent;

    private boolean oneTime;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.oneTime = true;
    }

    @FXML
    private void onOff(ActionEvent event) {
        try {
            FacadeBackend.getInstance().disconnect();
        } catch (IOException ex) {
            Logger.getLogger(SubscriberDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    @FXML
    private void changeScreenSubs(ActionEvent event) {
    }

    @FXML
    private void changeScreenAllTopics(ActionEvent event) {
        if(oneTime){
            try {
                FacadeBackend.getInstance().updateListTopics();
            } catch (IOException ex) {
                Logger.getLogger(SubscriberDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DeviceStandByException ex) {
                Logger.getLogger(SubscriberDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DeviceOfflineException ex) {
                Logger.getLogger(SubscriberDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            FacadeFrontend.getInstance().changeDashboardContent(Scenes.ALL_TOPICS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void changeContent(Parent content) {
        this.vBoxCenterContent.getChildren().clear();
        this.vBoxCenterContent.getChildren().add(content);
    }
}
