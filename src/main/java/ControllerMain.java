import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControllerMain {


    @FXML
    AnchorPane AnchorPanePlayground;

    @FXML
    TextField WindowH,WindowW,GraphX,GraphY,MovementDistanceInput;

    @FXML
    TextArea ErrorsTextArea;

    @FXML
    Label NumberOfQueriedSegments,NumberOfViewedSegments,LabelX1,LabelY1,LabelX2,LabelY2;

    @FXML
    public void AddWindowHandler(){
        //add default window
        Label[] StageLabels = {LabelX1,LabelY1,LabelX2,LabelY2,NumberOfQueriedSegments,NumberOfViewedSegments};     // DO NOT CHANGE THIS ORDER !
        TextField[] UserGraphXYInput = {GraphX,GraphY};

        if(!TestMainApp.WINDOW.setupVisualWindow(AnchorPanePlayground,StageLabels,UserGraphXYInput)){
            showError("A window already exists inside the Playground !\nPlease do not click again.");
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

            // Call Query from Back-End
            int x1 = x;
            int x2 = x+w;
            int y1 = y;
            int y2 = y+h;

            if(x2 > TestMainApp.BACKEND.getRight())
                x2 = TestMainApp.BACKEND.getRight();
            if(y2 < TestMainApp.BACKEND.getLow())
                y2 = TestMainApp.BACKEND.getLow();

            TestMainApp.BACKEND.Query(x1,x2,y1,y2);

            // Apply Query to Front-End
            TestMainApp.WINDOW.update(x,y,w,h);                      // this does update the query by itself
            TestMainApp.WINDOW.updateMovementDistance(Integer.parseInt(MovementDistanceInput.getText()));

            TestMainApp.WINDOW.getFxBody().requestFocus();

        }catch (NumberFormatException e){
            showError("GraphX or/and GraphY input is wrong !");
            GraphX.promptTextProperty().set("Wrong input");
            GraphX.requestFocus();
            GraphY.promptTextProperty().set("Wrong input");
//            e.printStackTrace();
        }


    }


    @FXML
    public void CheckMinMaxX(){
        String sInput = GraphX.getText();
        if(sInput.equals("-")){
            return;
        }
        else if(!sInput.isEmpty() && !sInput.isBlank()){
            int input = Integer.parseInt(sInput);
            int max = TestMainApp.BACKEND.getRight();
            int min = TestMainApp.BACKEND.getLeft();

            if(input > max){
                GraphX.setText(String.valueOf(max));
            }
            else if (input < min){
                GraphX.setText(String.valueOf(min));
            }
        }
        CheckWindowWInput();
    }


    @FXML
    public void CheckMinMaxY(){
        String sInput = GraphY.getText();
        if(sInput.equals("-")){
            return;
        }
        else if(!sInput.isBlank() && !sInput.isEmpty()){
            int input = Integer.parseInt(sInput);
            int max = TestMainApp.BACKEND.getUp();
            int min = TestMainApp.BACKEND.getLow();

            if(input > max){
                GraphY.setText(String.valueOf(max));
            }
            else if (input < min){
                GraphY.setText(String.valueOf(min));
            }
        }

        CheckWindowHInput();
    }

    /**
     * Methode utilisée pour vérifier l'input de l'utilisateur et le corriger.
     * Appellée par WindowW.
     */
    @FXML
    public void CheckWindowWInput(){
        CheckWindowWidthMax();
    }
    /**
     * Methode utilisée pour vérifier l'input de l'utilisateur et le corriger.
     * Appellée par WindowH.
     */
    @FXML
    public void CheckWindowHInput(){
        CheckWindowHeightMax();
    }

    /**
     * Methode utilisée pour limiter l'input dans le TextField WindowW.
     * Cela a comme effet de forcer l'utilisateur de ne pas utiliser une largeur pour la taille du Window
     * plus grande que ce que nous avons besoin pour afficher tous les segments à partir du point GraphX
     */
    private void CheckWindowWidthMax(){
        String sInput = WindowW.getText();
        if(!sInput.isBlank() && !sInput.isEmpty()){
            int input = Integer.parseInt(sInput);
            int rightBorder = TestMainApp.BACKEND.getRight();
            int leftBorder = TestMainApp.BACKEND.getLeft();
            int GraphXValue = Integer.parseInt(GraphX.getText());

            int Wmax = rightBorder - GraphXValue;
            if(GraphXValue == leftBorder)
                Wmax = rightBorder-leftBorder;
            else if (GraphXValue == rightBorder)
                Wmax = 0;

            if(input > Wmax){
                WindowW.setText(String.valueOf(Wmax));
            }
            else if(input < 0){
                WindowW.setText(String.valueOf(0));
            }
        }

    }

    /**
     * Methode utilisée pour limiter l'input dans le TextField WindowH.
     * Cela a comme effet de forcer l'utilisateur de ne pas utiliser une hauteur pour la taille du Window
     * plus grande que ce que nous avons besoin pour afficher tous les segments à partir du point GraphY
     */
    private void CheckWindowHeightMax(){
        String sInput = WindowH.getText();
        if(!sInput.isBlank() && !sInput.isEmpty()){
            int input = Integer.parseInt(sInput);
            int upBorder = TestMainApp.BACKEND.getUp();
            int lowBorder = TestMainApp.BACKEND.getLow();
            int GraphYValue = Integer.parseInt(GraphY.getText());

            int Hmax = upBorder - GraphYValue;
            if(GraphYValue == lowBorder)
                Hmax = upBorder-lowBorder;
            else if (GraphYValue == upBorder)
                Hmax = 0;

            if(input > Hmax){
                WindowH.setText(String.valueOf(Hmax));
            }
            else if(input < 0){
                WindowH.setText(String.valueOf(0));
            }
        }

    }

    private void showError(String txt){
        var oldText = ErrorsTextArea.getText();
        ErrorsTextArea.setText(oldText+">"+txt+"\n");
    }


}
