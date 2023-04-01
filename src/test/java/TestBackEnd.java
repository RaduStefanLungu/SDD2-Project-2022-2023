import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBackEnd {

    final String FILEPATH = "src/test/resources/10.txt";

    /**
     * Teste si BackEnd borne bien la fenêtre
     * C'est utile pour les cas où les demandes de windowing comportent au moins une borne infinie
     */
    @Test
    void testRead(){
        BackEnd be = new BackEnd(new File(FILEPATH));
        assertEquals(-10, be.getLow());
        assertEquals(10, be.getUp());
        assertEquals(-10, be.getLeft());
        assertEquals(10, be.getRigth());
    }
    
    /**
     * Teste la fonction TreeCreate() qui construit les deux arbres de priorité
     * Cela implique aussi le test de Read(), De QuickSort() et de Tree()
     * On regarde ici les résultats pour les segments verticaux
     */
    @Test
    void testVerTree(){
        BackEnd be = new BackEnd(new File(FILEPATH));
        assertEquals(-9, be.getVerTree().getRoot().getDif1());
        assertEquals(-8, be.getVerTree().getLeft().getRoot().getDif1());
        assertEquals(0, be.getVerTree().getRight().getRoot().getDif1());
    }

    /**
     * Teste la fonction TreeCreate() qui construit les deux arbres de priorité
     * Cela implique aussi le test de Read(), De QuickSort() et de Tree()
     * On regarde ici les résultats pour les segments horizontaux
     */
    @Test
    void testHorTree(){
        BackEnd be = new BackEnd(new File(FILEPATH));
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
        BackEnd be = new BackEnd(new File(FILEPATH));
        be.Query(-4, 4, -4, 4);
        assertEquals(-9, be.getAnswer().get(0).getDif1());
        assertEquals(-8, be.getAnswer().get(1).getDif1());
        assertEquals(-9, be.getAnswer().get(2).getDif1());
        assertEquals(-2, be.getAnswer().get(3).getDif1());
    }
}