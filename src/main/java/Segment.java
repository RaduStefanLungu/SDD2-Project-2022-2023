import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Segment {

    /**
     * Coordonnées du segment
     */
    private int dif1,dif2,cons;

    /**
     * Définit si ce segment est horizontal.
     * Si faux, le segment est vertical.
     */
    private final boolean isHorizontal;

    /**
     * Définit le corps FX de cet objet.
     */
    private Line fxBody;

    private double segmentWidth = 1.5;
    private Color segmentColor = Color.color(Math.random(),Math.random(),Math.random());

    /**
     * Création d'un segment
     * @param x première coordonnée qui varie dans son axe
     * @param y deuxième coordonnée qui varie dans son axe
     * @param z coordonnée invariable dans son axe
     */
    public Segment(int x, int y, int z,boolean isHorizontal){
        dif1 = x;
        dif2 = y;
        cons = z;
        this.isHorizontal = isHorizontal;
        this.fxBody = new Line();
        lineCustomiser(this,this.fxBody);
    }

    /**
     * Methode utilisée pour customiser le fxBody du segment.
     * Cela consiste en définissant les (x1,y1) et (x2,y2) du fxBody.
     * @param segment Objet utilisé pour le windowing.
     * @param line Objet JavaFX.
     */
    private void lineCustomiser(Segment segment,Line line){
        line.setStrokeWidth(this.segmentWidth);
        line.setStroke(this.segmentColor);

        if(segment.isHorizontal()) {
            line.setStartX(segment.getDif1());
            line.setStartY(segment.getCons());

            line.setEndX(segment.getDif2());
            line.setEndY(segment.getCons());
        }
        else{
            line.setStartX(segment.getCons());
            line.setStartY(segment.getDif1());

            line.setEndX(segment.getCons());
            line.setEndY(segment.getDif2());
        }
    }

    /**
     * Getteur pour la première coordonnée qui varie dans son axe
     * @return la première coordonnée qui varie dans son axe
     */
    public int getDif1(){
        return dif1;
    }

    /**
     * Getteur pour la deuxième coordonnée qui varie dans son axe
     * @return la deuxième coordonnée qui varie dans son axe
     */
    public int getDif2(){
        return dif2;
    }

    /**
     * Getteur pour la coordonnée invariable dans son axe
     * @return la coordonnée invariable dans son axe
     */
    public int getCons(){
        return cons;
    }

    public boolean isHorizontal(){
        return this.isHorizontal;
    }

    /**
     * Getteur pour le corps FX (JavaFX) de l'objet.
     * @return l'objet JavaFX correspondant.
     */
    public Line getFxBody() {
        return fxBody;
    }

    public void setSegmentColor(Color segmentColor) {
        this.segmentColor = segmentColor;
    }

    public void setSegmentWidth(double segmentWidth) {
        this.segmentWidth = segmentWidth;
    }

    @Override
    public String toString() {
        if(this.isHorizontal){
            return "("+dif1+","+cons+")"+"-->("+dif2+","+cons+")";
        }
        else{
            return "("+cons+","+dif1+")"+"-->("+cons+","+dif2+")";
        }
    }
}
