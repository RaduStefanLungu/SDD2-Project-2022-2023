import java.awt.*;

public class Segment {

    private int dif1,dif2,cons;

    public Segment(int x, int y, int z){
        dif1 = x;
        dif2 = y;
        cons = z;
    }

    public int[] getCoord(){
        return new int[] {this.dif1,this.dif2,this.cons};
    }

    public int getDif1(){
        return dif1;
    }

    public int getDif2(){
        return dif2;
    }

    public int getCons(){
        return cons;
    }

}
