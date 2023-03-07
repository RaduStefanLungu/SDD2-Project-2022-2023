import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class Main {
    private static ArrayList<Segment> ver = new ArrayList<Segment>();
    private static ArrayList<Segment> hor = new ArrayList<Segment>();

    private static int low, up, left, rigth;

    // NOTE : POUR RUN CE MAIN-CI, FAIT UN <GRADLE RUN> (vu que le main de base est le MainFX
    // ce main-ci sera appelle a la fin du MainFX)

    public static void main() throws Exception
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

    public static void QuickSort(ArrayList<Segment> list, int start, int end){
         if (start<end){
            int j = Partition(list, start, end);
            QuickSort(list, start, j-1);
            QuickSort(list, j+1, end);
         }
    }

    public static int Partition(ArrayList<Segment> list, int start, int end){
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

    public static Segment FindMin(ArrayList<Segment> list){
        Segment min = list.get(0);
        for (int i=1; i<list.size(); i++){
            if(list.get(i).getDif1()<min.getDif1()) {
                min = list.get(i);
            }
        }
        return min;
    }
    
    public static PriorityTree TreeCreate(ArrayList<Segment> list){
        Segment min = FindMin(list);
        list.remove(list.indexOf(min));
        if (list.size()>1){
            int half = (int)list.size()/2;
            ArrayList<Segment> seg1 = new ArrayList<Segment>();
            for (int i=0; i<half; i++){
                seg1.add(list.get(i));
            }
            ArrayList<Segment> seg2 = new ArrayList<Segment>();
            for (int i=half; i<list.size(); i++){
                seg2.add(list.get(i));
            }
            PriorityTree tree = new PriorityTree(min, TreeCreate(seg1), TreeCreate(seg2));
            return tree;
        }
        else{
            if (list.size()==1){
                int half = (int)list.size()/2;
                ArrayList<Segment> seg1 = new ArrayList<Segment>();
                for (int i=0; i<half; i++){
                    seg1.add(list.get(i));
                }
                PriorityTree tree = new PriorityTree(min, TreeCreate(seg1), 0);
                return tree;
            }
            else{
                if (list.size()==0){
                    PriorityTree tree = new PriorityTree(min, 0, 0);
                    return tree;
                }
            }
        }
        return null;
    }
}