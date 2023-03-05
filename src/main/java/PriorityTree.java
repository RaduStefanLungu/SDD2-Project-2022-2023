import java.awt.*;

public class PriorityTree {

    private Segment root;

    private PriorityTree left, rigth;
    
    public PriorityTree(Segment seg, PriorityTree tree1, PriorityTree tree2){
        root = seg;
        left = tree1;
        rigth = tree2;
    }

    public PriorityTree(Segment seg, PriorityTree tree1, int tree2){
        root = seg;
        left = tree1;
    }

    public PriorityTree(Segment seg, int tree1, int tree2){
        root = seg;
    }

    public Segment getRoot(){
        return root;
    }

    public PriorityTree getLeft(){
        return left;
    }

    public PriorityTree getRigth(){
        return rigth;
    }

}
