package controller.frontend;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class StageController {

    private Map<String, Stage> stages;

    public StageController(Stage mainStage) {
        this.stages = new HashMap();
        this.stages.put("mainStage", mainStage);
        mainStage.setResizable(false);
        mainStage.setTitle("Initial settings");
    }
    
    public void changeMainStage(Parent content) {
        this.changeStageContent("mainStage", content);
    }

    public void changeStageContent(String stageName, Parent content) {
        Stage stage;
        if (this.stages.containsKey(stageName)) {
            stage = stages.get(stageName);
        } else {
            stage = new Stage();
            stages.put(stageName, stage);
        }
        stage.close();
        stage.setScene(new Scene(content));
        stage.centerOnScreen();
        stage.show();
    }

}
