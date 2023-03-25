import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestBackEnd {

    /**
     * Teste si Read() répartit bien les segments
     */
    @Test
    void testRead(){
        BackEnd be = new BackEnd();
        be.Read("resources/10.txt");
        assertEquals(-10, be.getLow());
        assertEquals(10, be.getUp());
        assertEquals(-10, be.getLeft());
        assertEquals(10, be.getRight());
        assertEquals(3, be.getVer().size());
        assertEquals(4, be.getHor().size());
    }

    /**
     * Teste si Quicksort() trie bien les segments
     */
    @Test
    void testQuicksort(){
        BackEnd be = new BackEnd();
        be.Read("resources/10.txt");
        be.getVer().QuickSort();
        be.getHor().QuickSort();
        int[] verTest = new int[3];
        int[] horTest = new int[4];
        for(int i=0; i<3; i++){
            verTest.add(be.getVer().get(i).getCons());
        }
        for(int i=0; i<4; i++){
            horTest.add(be.getHor().get(i).getCons());
        }
        assertEquals(0, verTest[0]);
        assertEquals(3, verTest[1]);
        assertEquals(9, verTest[2]);
        assertEquals(-6, horTest[0]);
        assertEquals(-1, horTest[1]);
        assertEquals(1, horTest[2]);
        assertEquals(4, horTest[3]);
    }

    /**
     * Teste la fonction TreeCreate() qui construit les deux arbres de priorité
     * et teste si Tree() construit les bons arbres de priorité
     */
    @Test
    void testTree(){
        BackEnd be = new BackEnd();
        be.TreeCreate("resources/10.txt");
        assertEquals(-9, be.getVerTree().getRoot().getDif1());
        assertEquals(-8, be.getVerTree().getLeft().getRoot().getDif1());
        assertEquals(0, be.getVerTree().getRight().getRoot().getDif1());
        assertEquals(-9, be.getHorTree().getRoot().getDif1());
        assertEquals(1, be.getHorTree().getLeft().getRoot().getDif1());
        assertEquals(5, be.getHorTree().getLeft().getLeft().getRoot().getDif1());
        assertEquals(-2, be.getHorTree().getRight().getRoot().getDif1());
    }

    /**
     * Teste les fonctions Query() et QueryAnswer() qui répondent aux demandes de windowing
     */
    @Test
    void testQuery(){
        BackEnd be = new BackEnd();
        be.TreeCreate("resources/10.txt");
        be.Query(-4, 4, -4, 4);
        assertEquals(-9, be.getAnswer().get(0).getDif1());
        assertEquals(-8, be.getAnswer().get(1).getDif1());
        assertEquals(-9, be.getAnswer().get(2).getDif1());
        assertEquals(-2, be.getAnswer().get(3).getDif1());
    }
}