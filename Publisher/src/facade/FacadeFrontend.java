package facade;

import controller.frontend.ScreensController;
import controller.frontend.StageController;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeFrontend {
    private static FacadeFrontend facade;
    
    private ScreensController sc;
    private StageController stageController;
    
    private FacadeFrontend(){
        this.sc = new ScreensController();
    }
    
    public static synchronized FacadeFrontend getInstance(){
        return (facade == null) ? facade = new FacadeFrontend() : facade;
    }
    
    public void initialize(Stage stage, Scenes scene) throws Exception{        
        this.stageController = new StageController(stage);
        Parent loadedScreen = this.sc.loadScreen(scene);
        this.stageController.changeStageContent(loadedScreen);
    }
    
    public void changeScreean(Scenes scene) throws Exception{
        Parent loadedScreen = this.sc.loadScreen(scene);
        this.stageController.changeStageContent(loadedScreen);
    }

    public void showAlert(AlertType alertType, String title, String message) {
        this.stageController.newAlert(alertType, title, message);
    }

}
