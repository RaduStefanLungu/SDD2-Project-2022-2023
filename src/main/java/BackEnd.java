import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class BackEnd {

    /**
   * La liste de segments verticaux qui sera triée en x
   */
    private ArrayList<Segment> ver = new ArrayList<Segment>();
    /**
   * La liste de segments horizontaux qui sera triée en y
   */
    private ArrayList<Segment> hor = new ArrayList<Segment>();
    /**
   * La liste de segments renvoyés en réponse à une requête
   */
    private ArrayList<Segment> answer = new ArrayList<Segment>();

    /**
   * L'arbre de priorité des segments verticaux
   */
    PriorityTree verTree = new PriorityTree();
    /**
   * L'arbre de priorité des segments horizontaux
   */
    PriorityTree horTree = new PriorityTree();

    /**
   * Les bornes maximales
   */
    private int low, up, left, rigth;

    // NOTE : POUR RUN CE MAIN-CI, FAIT UN <GRADLE RUN> (vu que le main de base est le MainFX
    // ce main-ci sera appelle a la fin du MainFX)

    /**
     * Function starting calculations and everything the back-end does.
     * @throws Exception
     */

    /**
     * Fonction principale pour l'initialisation des arbres de priorité
     * @param file fichier texte avec les bornes maximales et les segments verticaux et horizontaux
     */
    public void TreeCreate(File file){
        ver.clear();
        hor.clear();
        Read(file);
        QuickSort(ver, 0, ver.size());
        QuickSort(hor, 0, hor.size());
        verTree = Tree(ver);
        horTree = Tree(hor);
    }

    /**
     * Lit et initialise les bornes maximales et trie les segments en deux tableaux
     * @param file fichier texte avec les bornes maximales et les segments verticaux et horizontaux
     */
    public void Read(File file){
        Scanner scanner;
        try {
            scanner = new Scanner(file);
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
                    if (y<=z){
                        Segment seg = new Segment(y, z, x);
                        ver.add(seg);
                    }
                    else {
                        Segment seg = new Segment(z, y, x);
                        ver.add(seg);
                    }
                }
                else{
                    if (x<=z){
                        Segment seg = new Segment(x, z, y);
                        hor.add(seg);
                    }
                    else {
                        Segment seg = new Segment(z, x, y);
                        hor.add(seg);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Trie un tableau de segment en fonction de la valeur constante
     * @param list liste à trier
     * @param start indice du premier élément
     * @param end indice du dernier élément
     */
    private void QuickSort(ArrayList<Segment> list, int start, int end){
         if (start<end){
            int j = Partition(list, start, end);
            QuickSort(list, start, j-1);
            QuickSort(list, j+1, end);
         }
    }

    /**
     * Trouve l'indice qui sert à diviser le tableau en deux partie dont une plus petite que l'élément en question
     * et l'autre plus grande
     * @param list liste à trier
     * @param start indice du premier élément
     * @param end indice du dernier élément
     * @return indice de l'élément qui divise le tableau
     */
    private int Partition(ArrayList<Segment> list, int start, int end){
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

    /**
     * Crée un arbre de priorité en fonction de l'axe qui possède deux valeurs différentes
     * et maintien un équilibre en fonction de la troisième valeur
     * @param list liste triée selon la valeur constante du segment
     * @return arbre de priorité
     */
    private PriorityTree Tree(ArrayList<Segment> list){
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
            PriorityTree tree = new PriorityTree(min, mid, Tree(seg1), Tree(seg2));
            return tree;
        }
        else{
            if (list.size()==1){
                int mid = list.get(0).getCons();
                ArrayList<Segment> seg = new ArrayList<Segment>();
                seg.add(list.get(0));
                PriorityTree tree = new PriorityTree(min, mid, Tree(seg));
                return tree;
            }
            else{
                    PriorityTree tree = new PriorityTree(min);
                    return tree;
                }
            }
    }

    /**
     * Renvoie le segment donc la valeur gauche ou basse (en fonction du tpe de segment) est la plus petite
     * @param list liste triée selon la valeur constante du segment
     * @return segment minimum
     */
    private Segment FindMin(ArrayList<Segment> list){
        Segment min = list.get(0);
        for (int i=1; i<list.size(); i++){
            if(list.get(i).getDif1()<min.getDif1()) {
                min = list.get(i);
            }
        }
        return min;
    }
    
    /**
     * Fonction principale pour les requêtes de windowing
     * @param x1 borne gauche
     * @param x2 borne droite
     * @param y1 borne basse
     * @param y2 borne haute
     */
    public void Query(int x1, int x2, int y1, int y2){
        answer.clear();
        QueryAnswer(verTree, y1, y2, x1, x2);
        QueryAnswer(horTree, x1, x2, y1, y2);
    }

    /**
     * Parcours un arbre de priorité et sélectionne les segments qui répondent à la requête
     * @param tree arbre de priorité de segments horizontaux ou verticaux
     * @param dif1 borne gauche pour les segments horizontaux et basse pour les segments verticaux
     * @param dif2 borne droite pour les segments horizontaux et haute pour les segments verticaux
     * @param cons1 borne basse pour les segments horizontaux et gauche pour les segments verticaux
     * @param cons2 borne haute pour les segments horizontaux et droite pour les segments verticaux
     */
    public void QueryAnswer(PriorityTree tree, int dif1, int dif2, int cons1, int cons2){
        if (tree.getRoot().getDif1()<=dif2){
            if(tree.getRoot().getDif2()>=dif1 && tree.getRoot().getCons()>=cons1 && tree.getRoot().getCons()<=cons2){
                answer.add(tree.getRoot());
            }
            if(tree.getType()!=2){
                if(tree.getMid()>=cons1){
                    QueryAnswer(tree.getLeft(), dif1, dif2, cons1, cons2);
                }
                if(tree.getMid()<=cons2 && tree.getType()!=1){
                    QueryAnswer(tree.getRigth(), dif1, dif2, cons1, cons2);
                }
            }
        }
    }

    /**
     * Getteur pour la borne basse maximale
     * @return la borne basse maximale
     */
    public int getLow(){
        return low;
    }

    /**
     * Getteur pour la borne haute maximale
     * @return la borne haute maximale
     */
    public int getUp(){
        return up;
    }

    /**
     * Getteur pour la borne gauche maximale
     * @return la borne gauche maximale
     */
    public int getLeft(){
        return left;
    }

    /**
     * Getteur pour la borne droite maximale
     * @return la borne droite maximale
     */
    public int getRigth(){
        return rigth;
    }

    /**
     * Getteur pour l'arbre de priorité des segments verticaux
     * @return l'arbre de priorité des segments verticaux
     */
    public PriorityTree getVerTree(){
        return verTree;
    }

    /**
     * Getteur pour l'arbre de priorité des segments horizontaux
     * @return l'arbre de priorité des segments horizontaux
     */
    public PriorityTree getHorTree(){
        return horTree;
    }

    /**
     * Getteur pour le tableau de segments de réponse à la requête de windowing
     * @return le tableau de segments de réponse à la requête de windowing
     */
    public ArrayList<Segment> getAnswer(){
        return answer;
    }
}