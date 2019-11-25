package controller.frontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class StageController {

    private final Stage mainStage;

    public StageController(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setResizable(false);
        this.mainStage.setTitle("Initial settings");
//        this.mainStage.getIcons().add(new Image("/resources/icons8-bus-64.png"));
    }

    public void changeStageContent(Parent content) {
        this.mainStage.close();
        this.mainStage.setScene(new Scene(content));
        this.mainStage.centerOnScreen();
        this.mainStage.show();
    }

    public void newAlert(String title, String mensege) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(mensege);
        a.show();
    }

}
