import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class BackEnd {
    private ArrayList<Segment> ver = new ArrayList<Segment>();
    private ArrayList<Segment> hor = new ArrayList<Segment>();

    private int low, up, left, rigth;

    // NOTE : POUR RUN CE MAIN-CI, FAIT UN <GRADLE RUN> (vu que le main de base est le MainFX
    // ce main-ci sera appelle a la fin du MainFX)

    /**
     * Function starting calculations and everything the back-end does.
     * @throws Exception
     */
    public void run() throws Exception
    {
        File file = new File("ressources/1000.txt");
        Scanner scanner = new Scanner(file);
        String[] first = scanner.nextLine().split(" ");
        low = (int) Float.parseFloat(first[0]);
        up = (int) Float.parseFloat(first[1]);
        left = (int) Float.parseFloat(first[2]);
        rigth = (int) Float.parseFloat(first[3]);
        while (scanner.hasNextLine()){
            String[] line = scanner.nextLine().split(" ");
            int x = (int) Float.parseFloat(line[0]);
            int y = (int) Float.parseFloat(line[1]);
            int z = (int) Float.parseFloat(line[2]);
            if (x == z){
                z = (int) Float.parseFloat(line[3]);
                Segment seg = new Segment(y, z, x);
                ver.add(seg);
            }
            else{
                Segment seg = new Segment(x, z, y);
                hor.add(seg);
            }
        }
        QuickSort(ver, 0, ver.size());
        QuickSort(hor, 0, hor.size());
        PriorityTree verTree = TreeCreate(ver);
        PriorityTree horTree = TreeCreate(hor);
    }

    public void QuickSort(ArrayList<Segment> list, int start, int end){
         if (start<end){
            int j = Partition(list, start, end);
            QuickSort(list, start, j-1);
            QuickSort(list, j+1, end);
         }
    }

    public int Partition(ArrayList<Segment> list, int start, int end){
        int j = start;
        for (int i = start; i < end-1; i++){
            if (list.get(i).getCons() <= list.get(end-1).getCons()){
                Segment temp = new Segment(list.get(j).getDif1(), list.get(j).getDif2(), list.get(j).getCons());
                list.set(j, list.get(i));
                list.set(i, temp);
                j++;
            }
        }
        Segment temp = new Segment(list.get(j).getDif1(), list.get(j).getDif2(), list.get(j).getCons());
        list.set(j, list.get(end-1));
        list.set(end-1, temp);
        return j;
    }

    public Segment FindMin(ArrayList<Segment> list){
        Segment min = list.get(0);
        for (int i=1; i<list.size(); i++){
            if(list.get(i).getDif1()<min.getDif1()) {
                min = list.get(i);
            }
        }
        return min;
    }
    
    public PriorityTree TreeCreate(ArrayList<Segment> list){
        Segment min = FindMin(list);
        list.remove(list.indexOf(min));
        if (list.size()>1){
            int half = (int)list.size()/2;
            int mid = list.get(half).getCons();
            ArrayList<Segment> seg1 = new ArrayList<Segment>();
            for (int i=0; i<half; i++){
                seg1.add(list.get(i));
            }
            ArrayList<Segment> seg2 = new ArrayList<Segment>();
            for (int i=half; i<list.size(); i++){
                seg2.add(list.get(i));
            }
            PriorityTree tree = new PriorityTree(min, mid, TreeCreate(seg1), TreeCreate(seg2));
            return tree;
        }
        else{
            if (list.size()==1){
                int mid = list.get(0).getCons();
                ArrayList<Segment> seg1 = new ArrayList<Segment>();
                seg1.add(list.get(0));
                PriorityTree tree = new PriorityTree(min, mid, TreeCreate(seg1));
                return tree;
            }
            else{
                if (list.size()==0){
                    PriorityTree tree = new PriorityTree(min);
                    return tree;
                }
            }
        }
        return null;
    }
    /*
    ArrayList<Segment> answer = new ArrayList<Segment>();

    public void QueryAnswer(PriorityTree tree, int dif1, int dif2, int cons1, int cons2){
        if(tree.getRoot().getDif1()>=dif1 && tree.getRoot().getDif2()<=dif2 && tree.getRoot().getCons()>=cons1 && tree.getRoot().getCons()<=cons2){
            answer.add(tree.getRoot());
        }
        if(tree.getRoot().getDif2()>=cons1 && tree.getType()!=2){
            if(tree.getMid()>=cons1){
                QueryAnswer(tree.getLeft(), dif1, dif2, cons1, cons2);
            }
            if(tree.getMid()<=cons2 && tree.getType()!=1){
                QueryAnswer(tree.getRigth(), dif1, dif2, cons1, cons2);
            }
        }
    }
    */
}