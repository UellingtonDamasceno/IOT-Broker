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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Topic;
import model.exceptions.DeviceOfflineException;
import model.exceptions.DeviceStandByException;
import util.Settings.Scenes;

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

    private boolean isUpdating;
    @FXML
    private Button btnVisualize;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnSubscribe.setVisible(false);
        this.btnUnsubscribe.setVisible(false);
        this.tbcName.setCellValueFactory(new PropertyValueFactory("name"));
        this.tbcPubs.setCellValueFactory(new PropertyValueFactory("pubs"));
        this.tbcSubs.setCellValueFactory(new PropertyValueFactory("subs"));
        this.tblView.setItems(FacadeBackend.getInstance().getAllTopics());
    }

    @FXML
    private void updateList(ActionEvent event) {
        FacadeBackend.getInstance().addTopic();
        System.out.println("Add novo topico");
    }

    @FXML
    private void unsubscribe(ActionEvent event) {
        try {
            String id = this.tblView.getSelectionModel().getSelectedItem().getName();
            FacadeBackend.getInstance().unsubscribe(id);
            this.btnSubscribe.setVisible(true);
            this.btnUnsubscribe.setVisible(false);
        } catch (DeviceStandByException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceOfflineException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void subscribe(ActionEvent event) {
        String id = this.tblView.getSelectionModel().getSelectedItem().getName();
        try {
            FacadeBackend.getInstance().subscribe(id);
        } catch (IOException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceStandByException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceOfflineException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnSubscribe.setVisible(false);
        this.btnUnsubscribe.setVisible(true);
    }

    @FXML
    private void itemSelected(MouseEvent event) {
        if (!tblView.getSelectionModel().isEmpty()) {
            Topic selectedTopic = this.tblView.getSelectionModel().getSelectedItem();
            this.btnSubscribe.setVisible(true);
            System.out.println(selectedTopic);
        } else {
            this.btnSubscribe.setVisible(false);
            this.btnUnsubscribe.setVisible(false);
            event.consume();
        }
    }

    @FXML
    private void changeVisualizeScreen(ActionEvent event) {
        try {
            Topic selectedTopic = this.tblView.getSelectionModel().getSelectedItem();
            FacadeFrontend.getInstance().changeScreean(Scenes.TOPIC_VIZUALIZE);
            FacadeBackend.getInstance().setCurrentTopic(selectedTopic);
        } catch (Exception ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
