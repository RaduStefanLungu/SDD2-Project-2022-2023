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
        int[3] verTest;
        int[4] HorTest;
        for(int i=0; i<3; i++){
            verTest.add(be.getVer().get(i).getCons())
        }
        for(int i=0; i<4; i++){
            horTest.add(be.getHor().get(i).getCons())
        }
        assertEquals([0, 3, 9], verTest);
        assertEquals([-6, -1, 1, 4], horTest);
    }

    /**
     * Teste si Tree() construit les bons arbres de priorités
     */
    void testTree(){
        BackEnd be = new BackEnd();
        be.Read("resources/10.txt");
        be.getVer().QuickSort();
        be.getHor().QuickSort();
        be.getVerTree() = be.Tree(be.getVer());
        be.getHorTree() = be.Tree(be.getHor());
        assertEquals(-9, be.getVerTree().getRoot());
        assertEquals(-8, be.getVerTree().getLeft().getRoot());
        assertEquals(0, be.getVerTree().getRight().getRoot());
        assertEquals(-9, be.getHorTree().getRoot());
        assertEquals(1, be.getHorTree().getLeft().getRoot());
        assertEquals(5, be.getHorTree().getLeft().getLeft().getRoot());
        assertEquals(-2, be.getHorTree().getRight().getRoot());
    }
}