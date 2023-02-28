import java.awt.*;

public class Segment {

    private int X,Y,Z;

    public Segment(int x, int y, int z){
        X = x;
        Y = y;
        Z = z;
    }

    public int[] getCoord(){
        return new int[] {this.X,this.Y,this.Z};
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    public int getZ(){
        return Z;
    }

}
