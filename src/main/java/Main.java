import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

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
                Segment seg = new Segment(x, y, z);
                ver.add(seg);
            }
            else{
                Segment seg = new Segment(x, y, z);
                hor.add(seg);
            }
        }
    }
    public static void sort(ArrayList<Segment>){
         
    }
}