import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Window {

    //TODO RAJOUTER UN CAP POUR LES BORNES MAXIMALES  BackEnd.getLow,getUp
    //              --> l'appel a l'infit sera le fixe aux bornes maximales.

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
    private Label labelX1Y1,labelX2Y2;
    private ArrayList<Line> fxBodyBorders;

    private AnchorPane parent;

    public Window(){
        this.width = 250;
        this.height = 250;
        this.gx = 0;
        this.gy = 0;

        this.fxBody = new Pane();
        this.fxBodyBorders = createWindowBorders(Color.BLACK,this.width,this.height);             // window visual borders
        addVisualIndicator(3,3);                                                    // window visual position indicators

//        updateVisual();                                                                           // bring the segments from the back-end to front-end

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
            return true;
        }
    }

    /**
     * Methode utilisée pour mettre à jour le query de segments en fonction des paramètres donnés.
     * @param gx coordonnée x dans le graphique de segments
     * @param gy coordonnée y dans le graphique de segments
     * @param w largeur de la fenêtre de vision
     * @param h hauteur de la fenêtre de vision
     */
    public void update(int gx,int gy,int w,int h,Label NumberOfQueriedSegments,Label NumberOfViewedSegments,Label LabelX1,Label LabelY1,Label LabelX2,Label LabelY2){
        this.updateWH(w,h);
        this.updateGxGy(gx,gy);
        this.updateVisual(NumberOfQueriedSegments,NumberOfViewedSegments,LabelX1,LabelY1,LabelX2,LabelY2);

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

//            updateVisual();
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

//            updateVisual();
        }
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
    private void updateVisual(Label NumberOfQueriedSegments,Label NumberOfViewedSegments,Label LabelX1,Label LabelY1,Label LabelX2,Label LabelY2){
        // update segments from back-end
        System.out.println("Updating visuals");

        // clear screen and add default stuff
        this.fxBody.getChildren().clear();
        this.updateBorders();
        this.addVisualIndicator(3,3);

        ArrayList<Segment> realSegmentsList = TestMainApp.BACKEND.getAnswer();
        NumberOfQueriedSegments.setText(String.valueOf(realSegmentsList.size()));

        //make virtual lines :
        ArrayList<Line> virtualLinesList = createVirtualLines2(extractLinesFromSegments(realSegmentsList),this.gx,this.gy,LabelX1,LabelY1,LabelX2,LabelY2);
        NumberOfViewedSegments.setText(String.valueOf(virtualLinesList.size()));

        // add virtual lines to screen :
        this.fxBody.getChildren().addAll(virtualLinesList);
    }

    private ArrayList<Line> extractLinesFromSegments(ArrayList<Segment> list){
        ArrayList<Line> l = new ArrayList<Line>();

        for(int i = 0; i<list.size();i++){
            l.add(list.get(i).getFxBody());
        }

        return l;
    }

    /**
     * Methode utilisée pour la creation des lines virtuels en rajoutant l'offset necessaire.
     * @return Liste de lignes à rajouter au front-end.
     */
    private ArrayList<Line> createVirtualLines(ArrayList<Line> realSegments ,OffsetDirections direction,int offset){
        ArrayList<Line> list = new ArrayList<Line>();

        for(int i = 0;i < realSegments.size(); i++){
            Line realLine = realSegments.get(i);
            Line virtualLine = new Line();
            //copying the realLine data to the virtualLine
            virtualLine.setStartX(realLine.getStartX());
            virtualLine.setEndX(realLine.getEndY());
            virtualLine.setStartY(realLine.getStartY());
            virtualLine.setEndY(realLine.getEndY());
            //shift the virtualLine
            shift(virtualLine,direction,offset);
            //save the shifted line
            list.add(virtualLine);
        }

        return list;
    }

    /**
     * Methode utilisée pour la creation des lines virtuels en rajoutant l'offset necessaire.
     * @return Liste de lignes à rajouter au front-end.
     */
    private ArrayList<Line> createVirtualLines2(ArrayList<Line> realSegments ,int xOffset, int yOffset,Label LabelX1,Label LabelY1,Label LabelX2,Label LabelY2){
        ArrayList<Line> list = new ArrayList<Line>();

        for(int i = 0;i < realSegments.size(); i++){
            Line realLine = realSegments.get(i);
            Line virtualLine = new Line();
            virtualLine.setStrokeWidth(1.5);
            virtualLine.setStroke(realLine.getStroke());

            // fx behavior :
            virtualLine.setOnMouseEntered(event -> {
                virtualLine.setStrokeWidth(5 * virtualLine.getStrokeWidth());
                System.out.println("Segment at : " + realLine.getStartX()+","+realLine.getStartY() + "-->" + realLine.getEndX()+","+realLine.getEndY());
                LabelX1.setText(String.valueOf(virtualLine.getStartX()));
                LabelY1.setText(String.valueOf(virtualLine.getStartY()));
                LabelX2.setText(String.valueOf(virtualLine.getEndX()));
                LabelY2.setText(String.valueOf(virtualLine.getEndY()));

            });
            virtualLine.setOnMouseExited(event -> {
                virtualLine.setStrokeWidth(virtualLine.getStrokeWidth() / 5);
                LabelX1.setText("0");
                LabelY1.setText("0");
                LabelX2.setText("0");
                LabelY2.setText("0");
            });

            //copying the realLine data to the virtualLine
            virtualLine.setStartX(realLine.getStartX());
            virtualLine.setEndX(realLine.getEndX());
            virtualLine.setStartY( realLine.getStartY());        //reverse the Y axes to fit the stardart screen
            virtualLine.setEndY( realLine.getEndY());            //reverse the Y axes to fit the stardart screen
            //shift the virtualLine
            shift2(virtualLine,xOffset,yOffset);
            //save the shifted line
            list.add(virtualLine);
        }

        return list;
    }

    /**
     * Methode utilisée pour translater une line d'une certaine valeur vers une certaine direction.
     * @param line line a translater
     * @param xOffset valeur à translater sur les axes de X
     * @param yOffset valeur à translater sur les axes de Y
     */
    private void shift2(Line line,int xOffset,int yOffset){
        //default values of begin/end of line
        var x01 = line.getStartX();
        var x11 = line.getEndX();
        var y01 = line.getStartY();
        var y11 = line.getEndY();

        line.setStartX(x01 - xOffset);
        line.setEndX(x11 - xOffset);

        line.setStartY(y01 - yOffset);
        line.setEndY(y11 - yOffset);

    }

    /**
     * Methode utilisée pour translater une line d'une certaine valeur vers une certaine direction.
     * @param line line a translater
     * @param direction direction où on veut translater
     * @param offset la distance de translation
     */
    private void shift(Line line,OffsetDirections direction,int offset){
        //default values of begin/end of line
        var x01 = line.getStartX();
        var x11 = line.getEndX();
        var y01 = line.getStartY();
        var y11 = line.getEndY();

        // shifting :
        switch (direction){
            case DOWN :
                line.setStartX(x01);
                line.setEndX(x11);
                line.setStartY(y01 + offset);
                line.setEndY(y01 + offset);
                break;
            case UP :
                line.setStartX(x01);
                line.setEndX(x11);
                line.setStartY(y01 - offset);
                line.setEndY(y01 - offset);
                break;
            case LEFT:
                line.setStartX(x01 - offset);
                line.setEndX(x11 - offset);
                line.setStartY(y01);
                line.setEndY(y11);
                break;
            case RIGHT:
                line.setStartX(x01 + offset);
                line.setEndX(x11 + offset);
                line.setStartY(y01);
                line.setEndY(y11);
                break;

        }
    }


    /**
     * Methode utilisée pour la mise à jour de l'indicateur visuel pour le point (0,0) du window
     * N.B. : Il faut appeller cette methode à chaque fois qu'on change le paramètre gx,gy
     */
    private void updateVisualIndicator(){
        this.labelX1Y1.setText("("+this.gx+","+this.gy+")");
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
     * Methode utilisée pour créer les indicateurs visuels pour que l'utilisateur sache où se trouve le point (0,0) dans le graphique affiché.
     * @param layoutX distance sur l'axe X dans le parent
     * @param layoutY distance sur l'axe y dans le parent
     */
    private void addVisualIndicator(double layoutX,double layoutY){
        this.labelX1Y1 = new Label("("+this.gx+","+this.gy+")");
        this.labelX1Y1.setLayoutX(layoutX);
        this.labelX1Y1.setLayoutY(layoutY);
        this.labelX1Y1.setTextFill(Color.DARKRED);
        this.labelX1Y1.setFont(new Font(this.labelX1Y1.getFont().getSize()*1.25));

        int x2 = this.width;
        int y2 = this.height;

        this.labelX2Y2 = new Label("("+x2+","+y2+")");
        this.labelX2Y2.setLayoutX(x2 + layoutX);
        this.labelX2Y2.setLayoutY(y2 + layoutY);
        this.labelX2Y2.setTextFill(Color.DARKRED);
        this.labelX2Y2.setFont(new Font(this.labelX2Y2.getFont().getSize()*1.25));

        Line ooDot = new Line();
        ooDot.setStartX(0);
        ooDot.setEndX(0);
        ooDot.setStartY(0);
        ooDot.setEndY(0);
        ooDot.setStroke(Color.DARKRED);
        ooDot.setStrokeWidth(5);

        Line ooDot2 = new Line();
        ooDot2.setStartX(x2);
        ooDot2.setEndX(x2);
        ooDot2.setStartY(y2);
        ooDot2.setEndY(y2);
        ooDot2.setStroke(Color.DARKRED);
        ooDot2.setStrokeWidth(5);

        this.fxBody.getChildren().addAll(labelX1Y1,labelX2Y2,ooDot,ooDot2);
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
