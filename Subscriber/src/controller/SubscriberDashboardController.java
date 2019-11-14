package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
    private Button btnStatus;
    @FXML
    private Button btnRemote;
    @FXML
    private VBox vBoxCenterContent;
    @FXML
    private VBox vBoxSide;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onOff(ActionEvent event) {
    }

    @FXML
    private void changeScreenSubs(ActionEvent event) {
    }

    @FXML
    private void changeScreenAllTopics(ActionEvent event) {
    }

    @FXML
    private void changeScreenStatus(ActionEvent event) {
    }

    @FXML
    private void changeScreenRemote(ActionEvent event) {
    }
    
}
