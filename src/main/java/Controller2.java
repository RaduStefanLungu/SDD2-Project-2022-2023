import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller2 {


    @FXML
    AnchorPane AnchorPanePlayground;

    @FXML
    TextField WindowH,WindowW,GraphX,GraphY;

    @FXML
    TextArea ErrorsTextArea;

    @FXML
    public void AddWindowHandler(){
        //add default window
        if(!TestMainApp.WINDOW.setupVisualWindow(AnchorPanePlayground)){
            showError("A window already exists inside the Playgound !\nPlease do not click again.");
        }
    }

    @FXML
    public void ClearPromptHandler(){
        ErrorsTextArea.clear();
    }

    @FXML
    public void applyHandler(){

        if(!AnchorPanePlayground.getChildren().contains(TestMainApp.WINDOW.getFxBody())){
            showError("The visual window had not been added.\nPlease press 'Add Window' first.");
            return;
        }

        // change the (x,y) inside the graph and update the segments.
        try{

            var x = Integer.parseInt(GraphX.getText());
            var y = Integer.parseInt(GraphY.getText());
            var w = Integer.parseInt(WindowW.getText());
            var h = Integer.parseInt(WindowH.getText());

            TestMainApp.WINDOW.updateGxGy(x,y);                      // this does update the query by itself
            TestMainApp.WINDOW.updateWH(w,h);                        // this does update the query by itself

        }catch (NumberFormatException e){
            showError("GraphX or/and GraphY input is wrong !");
            GraphX.promptTextProperty().set("Wrong input");
            GraphX.requestFocus();
            GraphY.promptTextProperty().set("Wrong input");
//            e.printStackTrace();
        }


    }


    private void showError(String txt){
        var oldText = ErrorsTextArea.getText();
        ErrorsTextArea.setText(oldText+">"+txt+"\n");
    }


}
