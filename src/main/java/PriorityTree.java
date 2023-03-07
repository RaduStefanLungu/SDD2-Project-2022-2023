import java.awt.*;

public class PriorityTree {

    private Segment root;

    private int mid, type;

    private PriorityTree left, rigth;
    
    public PriorityTree(Segment seg, int m, PriorityTree tree1, PriorityTree tree2){
        root = seg;
        mid = m;
        left = tree1;
        rigth = tree2;
        type = 0;
    }

    public PriorityTree(Segment seg, int m,PriorityTree tree1){
        root = seg;
        mid = m;
        left = tree1;
        type = 1;
    }

    public PriorityTree(Segment seg){
        root = seg;
        type = 2;
    }

    public Segment getRoot(){
        return root;
    }

    public int getMid(){
        return mid;
    }

    public PriorityTree getLeft(){
        return left;
    }

    public PriorityTree getRigth(){
        return rigth;
    }

    public int getType(){
        return type;
    }
}