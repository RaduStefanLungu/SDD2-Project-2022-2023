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

    private AnchorPane parent;

    public Window(){
        this.width = 1250;
        this.height = 250;
        this.fxBody = new Pane();
        this.gx = 0;
        this.gy = 0;
    }

    public boolean setupVisualWindow(AnchorPane parent){
        //check if this already exists inside the given parent
        if(parent.getChildren().contains(this.fxBody)){
            return false;
        }
        else{
            parent.getChildren().add(this.fxBody);
            this.parent = parent;
            //visual customisation
            updateVisual();



        }

        return true;
    }

    public void updateVisual(){
        System.out.println("Updating visuals");
        this.fxBody.prefHeight(this.height);
        this.fxBody.prefWidth(this.width);
        this.fxBody.setLayoutX(this.parent.getWidth()/2 - (this.width/2) );
        this.fxBody.setLayoutY(this.parent.getHeight()/2 - (this.height/2) );

        this.fxBody.getChildren().addAll(createWindowBorders(Color.BLACK,this.width,this.height));
    }

    public void show(){

    }

    private ArrayList<Line> createWindowBorders(Color color, int w, int h){

        Line w_top = new Line();
        w_top.setStartX(0);
        w_top.setEndX(w);
        w_top.setStartY(0);
        w_top.setEndY(0);

        w_top.setStrokeWidth(3);
        w_top.setStroke(color);

        Line w_bot = new Line();
        w_bot.setStartX(0);
        w_bot.setEndX(w);
        w_bot.setStartY(h);
        w_bot.setEndY(h);

        w_bot.setStrokeWidth(3);
        w_bot.setStroke(color);

        Line w_left = new Line();
        w_left.setStartX(0);
        w_left.setEndX(0);
        w_left.setStartY(0);
        w_left.setEndY(h);

        w_left.setStrokeWidth(3);
        w_left.setStroke(color);

        Line w_right = new Line();
        w_right.setStartX(w);
        w_right.setEndX(w);
        w_right.setStartY(0);
        w_right.setEndY(h);

        w_right.setStrokeWidth(3);
        w_right.setStroke(color);

        return new ArrayList<Line>(List.of(w_bot,w_top,w_left,w_right));
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

    public void setDx(int dx) {
        this.gx = dx;
    }

    public void setDy(int dy) {
        this.gy = dy;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
