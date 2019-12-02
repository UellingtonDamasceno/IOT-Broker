package facade;

import controller.frontend.ScreensController;
import controller.frontend.StageController;
import controller.frontend.SubscriberDashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeFrontend {
    private static FacadeFrontend facade;
    
    private ScreensController screensController;
    private StageController stageController;
    private SubscriberDashboardController sdc;
    private FacadeFrontend(){
        this.screensController = new ScreensController();
    }
    
    public static synchronized FacadeFrontend getInstance(){
        return (facade == null) ? facade = new FacadeFrontend() : facade;
    }
    
    public void setSubscriberDashBoardController(Scenes scene) throws Exception{
        if(this.sdc == null){
            FXMLLoader loader = screensController.getLoaderFXML(scene);
            Parent dashboard = loader.load();
            this.sdc = loader.getController();
            this.screensController.addScreen(scene, dashboard);
        }
    }
    
    public void initialize(Stage stage, Scenes scene) throws Exception{        
        this.stageController = new StageController(stage);
        Parent loadedScreen = this.screensController.loadScreen(scene);
        this.stageController.changeMainStage(loadedScreen);
    }
    
    public void changeScreean(Scenes scene) throws Exception{
        Parent loadedScreen = this.screensController.loadScreen(scene);
        this.stageController.changeMainStage(loadedScreen);
    }
    
    public void changeDashboardContent(Scenes scene) throws Exception{
        Parent content = this.screensController.loadScreen(scene);
        sdc.changeContent(content);
    }

    public void showContentAuxStage(Scenes scenes, String name) throws Exception {
        Parent content = this.screensController.loadScreen(scenes);
        this.stageController.changeStageContent(name, content);
    }
}
