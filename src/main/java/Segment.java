import java.awt.*;

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
}
