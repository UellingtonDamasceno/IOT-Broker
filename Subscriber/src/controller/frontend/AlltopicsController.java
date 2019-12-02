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

    @FXML
    private Button btnVisualize;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnSubscribe.setVisible(false);
        this.btnUnsubscribe.setVisible(false);
        this.btnVisualize.setVisible(false);
        this.tbcName.setCellValueFactory(new PropertyValueFactory("name"));
        this.tbcPubs.setCellValueFactory(new PropertyValueFactory("pubs"));
        this.tbcSubs.setCellValueFactory(new PropertyValueFactory("subs"));
        this.tbcIsSubscriber.setCellValueFactory(new PropertyValueFactory("subscriber"));
        this.tblView.setItems(FacadeBackend.getInstance().getAllTopics());
    }

    @FXML
    private void updateList(ActionEvent event) {
        try {
            FacadeBackend.getInstance().updateListTopics();
        } catch (IOException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceStandByException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeviceOfflineException ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void unsubscribe(ActionEvent event) {
        try {
            Topic selected = this.tblView.getSelectionModel().getSelectedItem();
            FacadeBackend.getInstance().unsubscribe(selected.getName());
            this.btnSubscribe.setVisible(!selected.isSubscriber());
            this.btnUnsubscribe.setVisible(selected.isSubscriber());
            this.btnVisualize.setVisible(selected.isSubscriber());

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
        Topic selected = this.tblView.getSelectionModel().getSelectedItem();
        try {
            FacadeBackend.getInstance().subscribe(selected.getName());
            this.btnSubscribe.setVisible(!selected.isSubscriber());
            this.btnUnsubscribe.setVisible(selected.isSubscriber());
            this.btnVisualize.setVisible(selected.isSubscriber());
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
            this.btnSubscribe.setVisible(!selectedTopic.isSubscriber());
            this.btnUnsubscribe.setVisible(selectedTopic.isSubscriber());
            this.btnVisualize.setVisible(selectedTopic.isSubscriber());
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
            TopicVizualizeController controller;
            controller = (TopicVizualizeController) FacadeFrontend.getInstance().showContentAuxStage(Scenes.TOPIC_VIZUALIZE, selectedTopic.getName());
            controller.setTopic(selectedTopic);
        } catch (Exception ex) {
            Logger.getLogger(AlltopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
