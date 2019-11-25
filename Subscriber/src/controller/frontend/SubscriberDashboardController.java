package controller.frontend;

import facade.FacadeFrontend;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
    private Button btnStatus;
    @FXML
    private VBox vBoxCenterContent;

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
        try {
            FacadeFrontend.getInstance().changeDashboardContent(Scenes.ALL_TOPICS);
        } catch (Exception ex) {
        }
    }

    @FXML
    private void changeScreenStatus(ActionEvent event) {
    }

    public void changeContent(Parent content) {
        this.vBoxCenterContent.getChildren().clear();
        this.vBoxCenterContent.getChildren().add(content);
    }
}
