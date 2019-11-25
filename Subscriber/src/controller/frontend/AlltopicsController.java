package controller.frontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Topic;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class AlltopicsController implements Initializable {

    @FXML
    private TableColumn<Topic, String> tbcName;
    @FXML
    private TableColumn<Topic, Integer> tbcPubs;
    @FXML
    private TableColumn<Topic, Integer> tbcSubs;
    @FXML
    private TableColumn<Topic, Boolean> tbcIsSubscriber;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnUnsubscribe;
    @FXML
    private Button btnSubscribe;
    @FXML
    private TableView<Topic> tblView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnSubscribe.setVisible(false);
        this.btnUnsubscribe.setVisible(false);
        this.tbcName.setCellValueFactory(new PropertyValueFactory("name"));
        this.tbcPubs.setCellValueFactory(new PropertyValueFactory("pubs"));
        this.tbcSubs.setCellValueFactory(new PropertyValueFactory("subs"));
        
    }

    @FXML
    private void updateList(ActionEvent event) {
    }

    @FXML
    private void unsubscribe(ActionEvent event) {
    }

    @FXML
    private void subscribe(ActionEvent event) {
    }

}
