import java.awt.*;

public class PriorityTree {

    /**
     * Segment dans la racine de l'arbre
     */
    private Segment root;

    /**
     * Entier qui sépare les coordonnées invariables restantes en deux
     */
    private int mid;
    /**
     * Le type permet de définir si l'arbre a un fils gauche et/ou un fils droit
     */
    private int type;

    /**
     * Arbres des fils gauche et droit
     */
    private PriorityTree left, rigth;
    
    /**
     * Création d'un arbre de priorité qui a un fils gauche et un fils droit
     * @param seg segment dans la racine
     * @param m entier qui sépare les coordonnées invariables restantes en deux
     * @param tree1 arbre gauche
     * @param tree2 arbre droit
     */
    public PriorityTree(Segment seg, int m, PriorityTree tree1, PriorityTree tree2){
        root = seg;
        mid = m;
        left = tree1;
        rigth = tree2;
        type = 0;
    }

    /**
     * Création d'un arbre de priorité qui n'a qu'un fils gauche
     * @param seg segment dans la racine
     * @param m entier plus grand ou égal à la coordonnée restante
     * @param tree1 arbre gauche
     */
    public PriorityTree(Segment seg, int m,PriorityTree tree1){
        root = seg;
        mid = m;
        left = tree1;
        type = 1;
    }

    /**
     * Création d'un arbre de priorité qui n'a pas de fils
     * @param seg segment dans la racine
     */
    public PriorityTree(Segment seg){
        root = seg;
        type = 2;
    }

    /**
     * Création d'un arbre vide pour l'initialisation du backend
     */
    public PriorityTree(){}

    /**
     * Getteur pour le segment dans la racine
     * @return le segment dans la racine
     */
    public Segment getRoot(){
        return root;
    }

    /**
     * Getteur pour l'entier qui sépare les coordonnées invariables restantes en deux
     * @return l'entier qui sépare les coordonnées invariables restantes en deux
     */
    public int getMid(){
        return mid;
    }

    /**
     * Getteur pour l'arbre du fils gauche
     * @return l'arbre du fils gauche
     */
    public PriorityTree getLeft(){
        return left;
    }

    /**
     * Getteur pour l'arbre du fils droit
     * @return l'arbre du fils droit
     */
    public PriorityTree getRight(){
        return rigth;
    }

    /**
     * Getteur pour le type d'arbre qui donne le nombre de fils
     * @return le type d'arbre qui donne le nombre de fils
     */
    public int getType(){
        return type;
    }
}