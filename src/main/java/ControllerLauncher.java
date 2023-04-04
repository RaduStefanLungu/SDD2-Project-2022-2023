import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;


public class ControllerLauncher {

    //  @FXML objects have to have the java name the same as their fxml id

    @FXML
    Stage MainStage;
    @FXML
    Button BLaunch,BChose;
    @FXML
    TextField TFFilePath;

    @FXML
    public void LaunchHandler() throws IOException  {
        // Set the back-end
        try{
            TestMainApp.BACKEND = new BackEnd(new File(TFFilePath.getText()));
            TestMainApp.WINDOW = new Window();
        }catch (Exception e){
            System.out.println(e);
        }

        // Set the front-end
        Parent parent = FXMLLoader.load(getClass().getResource("Page_Main.fxml"));
        Scene scene = new Scene(parent, TestMainApp.WIDTH, TestMainApp.HEIGHT);

        MainStage.resizableProperty().set(true);
        MainStage.setTitle(TestMainApp.TITLE);
        MainStage.setWidth(TestMainApp.WIDTH);
        MainStage.setHeight(TestMainApp.HEIGHT);
        MainStage.setScene(scene);
        MainStage.show();
    }

    @FXML
    public void ChoseFile(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            //set the absolute path
            TFFilePath.setText(file.getAbsolutePath());
        }
    }



    @FXML
    public void focusLaunchButton(){
        BLaunch.requestFocus();
    }

}
