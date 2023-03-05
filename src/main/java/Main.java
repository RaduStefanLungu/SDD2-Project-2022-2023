import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class Main {
    private static ArrayList<Segment> ver = new ArrayList<Segment>();
    private static ArrayList<Segment> hor = new ArrayList<Segment>();

    private static int low, up, left, rigth;

    public static void main(String[] args) throws Exception
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
    /*
        public static Segment[] CreateTree(ArrayList<Segment> list){
        double l = Math.log(list.size())/Math.log(2);
        l= Math.ceil(l);
        l = Math.pow(2, l);
        Segment[] tree = new Segment[(int)l];
        FillInTree(list, tree, 0);
    }

    public static void FillInTree(ArrayList<Segment> list, Segment[] tree, int index){

    }
    */
}