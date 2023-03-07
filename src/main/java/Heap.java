import java.util.ArrayList;

public class Heap {
    // representation arborescente de Heap

    private ArrayList<Integer> heap;

    public Heap(){
        this.heap = new ArrayList<Integer>();
    }

    /**
     * Adds the value to the heap.
     * @param data value to be added.
     * @return True if the value had been added, false otherwise.
     */
    public boolean add(int data){
        if(data <= this.heap.get(0)){       // make sur the root is >= the others.
            this.heap.add(data);
            return true;
        }
        else{                               // if root is < the data, we have do to something.
            //TODO Heapify
            return false;
        }
    }

    /**
     * Gets the left child of the node at position 'i'.
     * @param i Position of the parent node.
     * @return Value of the left child.
     */
    public int getLeft(int i){
        return this.heap.get(2*i);
    }

    /**
     * Gets the right child of the node at position 'i'.
     * @param i Position of the parent node.
     * @return Value of the right child.
     */
    public int getRight(int i){
        return this.heap.get((2*i) + 1);
    }

    /**
     * Gets the father of the node at position 'i'.
     * @param i Position of child.
     * @return Value of the father.
     */
    public int getFather(int i){
        return this.heap.get(i/2);
    }

}
