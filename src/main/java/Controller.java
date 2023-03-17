import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class Controller {

//  @FXML objects have to have the java name the same as their fxml id

    @FXML
    Button ApplyButton;

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



    //TODO after completing the desired number of textfields, charge them here and use them in ApplyButtonHandler(){}

    @FXML
    public void ApplyButtonHandler(){
        //code here...
        // get data from TextFileds and send it to the Back-End to change the window propreties.
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
