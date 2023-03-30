import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Controller2 {


    @FXML
    AnchorPane AnchorPanePlayground;

    @FXML
    TextField WindowH,WindowW,GraphX,GraphY;


    @FXML
    public void applyHandler(){
       //set up the window:
        if(!MainFX.WINDOW.setupVisualWindow(AnchorPanePlayground)){
            MainFX.WINDOW.updateVisual();
        }

        // change the (x,y) inside the graph and update the segments.
        try{
            MainFX.WINDOW.setDx(Integer.parseInt(GraphX.getText()));
            MainFX.WINDOW.setDy(Integer.parseInt(GraphY.getText()));
        }catch (NumberFormatException e){
            GraphX.promptTextProperty().set("Wrong input");
            GraphX.requestFocus();
            GraphY.promptTextProperty().set("Wrong input");
//            e.printStackTrace();
        }
        MainFX.WINDOW.show();

    }



}
