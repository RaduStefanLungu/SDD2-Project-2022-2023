import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Window {

    /**
     * Coordonnées définissant la taille du window.
     */
    private int width,height;

    /**
     * Coordonnées définissant le point de départ dans le graph des segments où
     * le window va commencer son affichage.
     */
    private int gx,gy;

    private Pane fxBody;
    private Label labelXY;
    private ArrayList<Line> fxBodyBorders;

    private AnchorPane parent;

    public Window(){
        this.width = 250;
        this.height = 250;
        this.gx = 0;
        this.gy = 0;

        this.fxBody = new Pane();
        addVisualIndicator(3,3);

        this.fxBodyBorders = createWindowBorders(Color.BLACK,this.width,this.height);

    }

    /**
     * Methode utilisée pour créer les indicateurs visuels pour que l'utilisateur sache où se trouve le point (0,0) dans le graphique affiché.
     * @param layoutX distance sur l'axe X dans le parent
     * @param layoutY distance sur l'axe y dans le parent
     */
    private void addVisualIndicator(double layoutX,double layoutY){
        this.labelXY = new Label("("+this.gx+","+this.gy+")");
        this.labelXY.setLayoutX(layoutX);
        this.labelXY.setLayoutY(layoutY);
        this.labelXY.setTextFill(Color.DARKRED);

        Line ooDot = new Line();
        ooDot.setStartX(0);
        ooDot.setEndX(0);
        ooDot.setStartY(0);
        ooDot.setEndY(0);
        ooDot.setStroke(Color.DARKRED);
        ooDot.setStrokeWidth(5);

        this.fxBody.getChildren().addAll(labelXY,ooDot);
    }

    /**
     * Methode utilisée pour la mise à jour de l'indicateur visuel pour le point (0,0) du window
     * N.B. : Il faut appeller cette methode à chaque fois qu'on change le paramètre gx,gy
     */
    public void updateVisualIndicator(){
        this.labelXY.setText("("+this.gx+","+this.gy+")");
    }


    /**
     * Methode utilisée pour rajouter le window dans un JavaFX.AnchorPane .
     * Cette methode vérifie si le window existe déjà dans ce parent.
     * @param parent l'AnchorPane dont on veut rajouter le window
     * @return true si le window a pu être rajouté. False si le window existe déjà.
     */
    public boolean setupVisualWindow(AnchorPane parent){
        //check if this already exists inside the given parent
        if(parent.getChildren().contains(this.fxBody)){
            return false;
        }
        //the parent doesn't have a Window:
        else{
            parent.getChildren().add(this.fxBody);
            this.parent = parent;
            //visual customisation
            updateResolution();
            updateBorders();
            //add segments from the back-end to the front-end
            updateVisual();
            return true;
        }
    }

    private void updateBorders(){
        //remove old borders
        removeBorders(this.fxBodyBorders);
        //create new borders
        this.fxBodyBorders = createWindowBorders(Color.BLACK,this.width,this.height);
        //apply new borders to screen
        this.fxBody.getChildren().addAll(fxBodyBorders);
    }

    /**
     * Methode utilisée pour mettre le centre du fxBody(JavaFX.Pane) au centre du parent(habituellement un JavaFX.AnchorPane).
     * N.B. : Il faut appeller cette methode à chaque fois qu'on change de paramètre (x, y ou width/height)
     */
    private void updateResolution(){
        this.fxBody.prefHeight(this.height);
        this.fxBody.prefWidth(this.width);
        this.fxBody.setLayoutX(this.parent.getWidth()/2 - (this.width/2) );
        this.fxBody.setLayoutY(this.parent.getHeight()/2 - (this.height/2) );

    }

    /**
     * Methode utilisée pour afficher les segments désirées du back-end au front-end.
     * Cette methode utilise un offset et des segments 'virtuels' pour eviter que le corps fx bouge sans control
     * lorsque l'utilisateur veut, par example, visualiser les segments dans un window (50,50)-(200,400).
     * L'offset est calculé de la manière suivante :
     * Le Window visuel aura comme bords : (0,0)-(width, height) ;
     * L'utilisateur peut choisir de placer le coin gauche du window (le (0,0)) dans une place bien précise dans le vrai graphique des segments données dans le fichier
     * en indiquant le GraphX(=x) et GraphY(=y) ;
     * Le window sera calculé automatiquement grâce au width et height introduit pour le window visuel (window dans le back-end : (GraphX,GraphY)-(GraphX+width,GraphY+height)) .
     * L'offset pour visualiser d'une manière correcte sera calculé tel que : offsetX = GraphX - 0 , offsetY = GraphY - 0 ;
     * On sur base de l'offset on va créer des segments virtuels à l'écran avec les mêmes composantes que le segment du back-end mais en retirant l'offset des axes.
     * Visuelement le window sera de la même taille mais il aura comme point le définissant (offsetX,offsetY)-(offsetX+width,offsetY+height) .
     */
    public void updateVisual(){
        // update segments from back-end
        System.out.println("Updating visuals");


    }

    /**
     * Methode utilisée pour mettre à jour le Width et Height du window.
     * @param w width
     * @param h height
     */
    public void updateWH(int w, int h){
        boolean changedWH = false;
        if(w != this.width){
            this.width = w;
            changedWH = true;
        }
        if(h != this.height){
            this.height = h;
            changedWH = true;
        }
        if(changedWH){
            updateBorders();
            updateResolution();

            updateVisual();
        }
    }

    /**
     * Methode utilisée pour mettre à jour le point x,y dans le graphique du back-end où l'utilisateur désire de commoncer la visualisation.
     * @param gx x dans le graphique des segments
     * @param gy y dans le graphique des segments
     */
    public void updateGxGy(int gx,int gy){
        boolean changedXY = false;

        if(gx != this.gx){
            this.gx = gx;
            changedXY = true;
        }
        if(gy != this.gy){
            this.gy = gy;
            changedXY = true;
        }
        if(changedXY){
            updateVisualIndicator();

            updateVisual();
        }
    }


    private ArrayList<Line> createWindowBorders(Color color, int w, int h){

        Line w_top = new Line();
        w_top.setStartX(0);
        w_top.setEndX(w);
        w_top.setStartY(0);
        w_top.setEndY(0);

        w_top.setStrokeWidth(1.5);
        w_top.setStroke(color);

        Line w_bot = new Line();
        w_bot.setStartX(0);
        w_bot.setEndX(w);
        w_bot.setStartY(h);
        w_bot.setEndY(h);

        w_bot.setStrokeWidth(1.5);
        w_bot.setStroke(color);

        Line w_left = new Line();
        w_left.setStartX(0);
        w_left.setEndX(0);
        w_left.setStartY(0);
        w_left.setEndY(h);

        w_left.setStrokeWidth(1.5);
        w_left.setStroke(color);

        Line w_right = new Line();
        w_right.setStartX(w);
        w_right.setEndX(w);
        w_right.setStartY(0);
        w_right.setEndY(h);

        w_right.setStrokeWidth(1.5);
        w_right.setStroke(color);

        return new ArrayList<Line>(List.of(w_bot,w_top,w_left,w_right));
    }

    private void removeBorders(ArrayList<Line> oldBorders){
        for(int i = 0; i < oldBorders.size();i++){
            this.fxBody.getChildren().remove(oldBorders.get(i));
        }
    }

    public Pane getFxBody() {
        return fxBody;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGx() {
        return gx;
    }

    public int getGy() {
        return gy;
    }

}
