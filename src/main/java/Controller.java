import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

//  @FXML objects have to have the java name the same as their fxml id

    @FXML
    Stage MainStage;

    @FXML
    Button BChose,ApplyButton;

    @FXML
    TextField TFFilePath,WindowDataX1,WindowDataX2,WindowDataY1,WindowDataY2;

    @FXML
    CheckBox CBDefaultInput,CBCustomisedInput;

    @FXML
    VBox ConfigPanel,ConfigPanelVHolder,DefaultInputPanel,CustomisedInputPanel;

    @FXML
    Group GroupDrawing;

    @FXML
    Pane PaneDrawing;


    @FXML
    public void LaunchHandler() throws IOException  {
        // Set the back-end
        try{
            MainFX.BACKEND = new BackEnd(new File(TFFilePath.getText()));
        }catch (Exception e){
            System.out.println(e);
        }

        // Set the front-end
        Parent parent = FXMLLoader.load(getClass().getResource("Page_Main.fxml"));
        Scene scene = new Scene(parent,MainFX.WIDTH,MainFX.HEIGHT);

        MainStage.setTitle(MainFX.TITLE);
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
        int x1 = Integer.parseInt(WindowDataX1.getText());
        int y1 = Integer.parseInt(WindowDataY1.getText());

        int x2 = Integer.parseInt(WindowDataX2.getText());
        int y2 = Integer.parseInt(WindowDataY2.getText());


        // empty the Group
//        GroupDrawing.getChildren().clear();
        PaneDrawing.getChildren().clear();

        // customize the Group

        ArrayList<Line> windowBorders = createWindowBorders(Color.BLACK,x1,x2,y1,y2);
//        GroupDrawing.getChildren().addAll(windowBorders);

        PaneDrawing.getChildren().addAll(windowBorders);


        // Call Query from BackEnd
        MainFX.BACKEND.Query(x1,x2,y1,y2);

        //Apply to Front-End

//        showLinesToGroup(MainFX.BACKEND.getAnswer(),GroupDrawing);

        showLinesToPane(MainFX.BACKEND.getAnswer(),PaneDrawing);

    }


    /**
     * Methode utilisée pour la creation visuelle des limites du 'window'.
     * @param color La couleur du bord.
     * @param x1 Coordonnée x1 du 'window'.
     * @param x2 Coordonnée x2 du 'window'.
     * @param y1 Coordonnée y1 du 'window'.
     * @param y2 Coordonnée y2 du 'window'.
     * @return ArrayList d'objets définissant les bords du window.
     */
    private ArrayList<Line> createWindowBorders(Color color,int x1,int x2,int y1,int y2){

        Line w_top = new Line();
        w_top.setStartX(x1);
        w_top.setEndX(x2);
        w_top.setStartY(y1);
        w_top.setEndY(y1);

        w_top.setStrokeWidth(3);
        w_top.setStroke(color);

        Line w_bot = new Line();
        w_bot.setStartX(x1);
        w_bot.setEndX(x2);
        w_bot.setStartY(y2);
        w_bot.setEndY(y2);

        w_bot.setStrokeWidth(3);
        w_bot.setStroke(color);

        Line w_left = new Line();
        w_left.setStartX(x1);
        w_left.setEndX(x1);
        w_left.setStartY(y1);
        w_left.setEndY(y2);

        w_left.setStrokeWidth(3);
        w_left.setStroke(color);

        Line w_right = new Line();
        w_right.setStartX(x2);
        w_right.setEndX(x2);
        w_right.setStartY(y1);
        w_right.setEndY(y2);

        w_right.setStrokeWidth(3);
        w_right.setStroke(color);

        return new ArrayList<Line>(List.of(w_bot,w_top,w_left,w_right));
    }

    /**
     * Methode qui applique à l'écran les segments données en paramètres.
     * @param segmentsList Liste de segments désirée.
     * @param viewer Le plateau de visualisation.
     */
    private void showLinesToGroup(ArrayList<Segment> segmentsList,Group viewer){
        for(int i = 0 ; i < segmentsList.size();i++){
            var segmentFxBody = segmentsList.get(i).getFxBody();
            viewer.getChildren().add(segmentFxBody);
        }
    }

    private void showLinesToPane(ArrayList<Segment> segmentsList,Pane viewer){
        for(int i = 0 ; i < segmentsList.size();i++){
            var segmentFxBody = segmentsList.get(i).getFxBody();
            viewer.getChildren().add(segmentFxBody);
        }
    }


    @FXML
    public void LeftMoveButtonHandler(){
        GroupDrawing.translateXProperty().add(-1*10);
        System.out.println("CLICK LeftMoveButtonHandler");
    }

    @FXML
    public void RightMoveButtonHandler(){
        GroupDrawing.translateXProperty().add(10);
        System.out.println("CLICK RightMoveButtonHandler");
    }

}
