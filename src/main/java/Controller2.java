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
        MainFX.WINDOW.setupVisualWindow(AnchorPanePlayground);

        // change the (x,y) inside the graph and update the segments.
        try{

            var x = Integer.parseInt(GraphX.getText());
            var y = Integer.parseInt(GraphY.getText());
            var w = Integer.parseInt(WindowW.getText());
            var h = Integer.parseInt(WindowH.getText());

            MainFX.WINDOW.updateValues(w,h,x,y);

        }catch (NumberFormatException e){
            GraphX.promptTextProperty().set("Wrong input");
            GraphX.requestFocus();
            GraphY.promptTextProperty().set("Wrong input");
//            e.printStackTrace();
        }
        MainFX.WINDOW.updateVisual();

    }



}
