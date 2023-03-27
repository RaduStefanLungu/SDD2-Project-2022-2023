import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

//  @FXML objects have to have the java name the same as their fxml id

    @FXML
    Stage MainStage;

    @FXML
    Button BChose;
    @FXML
    Button ApplyButton;

    @FXML
    TextField TFFilePath,WindowDataX1,WindowDataX2,WindowDataY1,WindowDataY2;

    @FXML
    CheckBox CBDefaultInput;
    @FXML
    CheckBox CBCustomisedInput;


    @FXML
    VBox ConfigPanel;
    @FXML
    VBox ConfigPanelVHolder;
    @FXML
    VBox DefaultInputPanel;
    @FXML
    VBox CustomisedInputPanel;

    @FXML
    Group GroupDrawing;


    @FXML
    public void LaunchHandler() throws IOException {
        // Send data to back-end
        try{
            MainFX.BACKEND.TreeCreate(new File(TFFilePath.getText()));
        }catch (Exception e){
            System.out.println(e);
        }

        // Set the front-end
        Parent parent = FXMLLoader.load(getClass().getResource("Page_Main.fxml"));
        Scene scene = new Scene(parent,MainFX.WIDTH,MainFX.HEIGHT);

        MainStage.setWidth(MainFX.WIDTH);
        MainStage.setHeight(MainFX.HEIGHT);
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


    //TODO after completing the desired number of textfields, charge them here and use them in ApplyButtonHandler(){}

    @FXML
    public void ApplyButtonHandler(){
        //code here...
        // get data from TextFileds and send it to the Back-End to change the window propreties.

        int x1 = Integer.getInteger(WindowDataX1.getText());
        int y1 = Integer.getInteger(WindowDataY1.getText());

        int x2 = Integer.getInteger(WindowDataX2.getText());
        int y2 = Integer.getInteger(WindowDataY1.getText());


        // Call Query from BackEnd
        MainFX.BACKEND.Query(x1,y1,x2,y2);

        //Apply to Front-End
        ArrayList<Segment> arrayList = MainFX.BACKEND.getAnswer();
        for(int i = 0 ; i<arrayList.size();i++){
            Segment seg = arrayList.get(i);



            Line line = new Line();
            line.setStartX(0);
            line.setStartY(0);

            line.setEndX(0);
            line.setEndY(0);
        }

    }


    @FXML
    public void CheckboxDefaultInputHandler(){
        //check if the checkbox was successfully checked:
        if(CBDefaultInput.isSelected()){
            //uncheck the others checkboxes
            CBCustomisedInput.setSelected(false);
            //show DefaultInputPanel
            ConfigPanelVHolder.getChildren().add(DefaultInputPanel);
            //remove CustomisedInputPanel if present
            ConfigPanelVHolder.getChildren().remove(CustomisedInputPanel);
        }
        else{
            //remove DefaultInputPanel
            ConfigPanelVHolder.getChildren().remove(DefaultInputPanel);
        }
    }

    @FXML
    public void CheckboxCustomisedInputHandler(){
        //check if the checkbox was successfully checked:
        if(CBCustomisedInput.isSelected()){
            //uncheck the others checkboxes
            CBDefaultInput.setSelected(false);
            //show DefaultInputPanel
            ConfigPanelVHolder.getChildren().add(CustomisedInputPanel);
            //remove CustomisedInputPanel if present
            ConfigPanelVHolder.getChildren().remove(DefaultInputPanel);
        }
        else{
            //remove DefaultInputPanel
            ConfigPanelVHolder.getChildren().remove(CustomisedInputPanel);
        }
    }

}
