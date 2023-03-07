import java.util.ArrayList;

public class Heap {
    private ArrayList<HeapNode> heap;

    public Heap(){
        this.heap = new ArrayList<HeapNode>();
    }

    /**
     * Adds the value to the heap.
     * @param data value to be added.
     * @return True if the value had been added, false otherwise.
     */
    public void add(int data){
        HeapNode n = new HeapNode(data);
        if(this.heap.size() == 0 ){                     // set the root
            this.heap.add(n);
        }
        else{
            this.heap.add(n);
            correct(this,this.heap.size()-1);
        }
    }

    /**
     * Recursive method used to correct a given Heap , starting with the node at the position 'index'.
     * Note : This algorithm has a complexity of O(index) => O(N).
     * @param A Heap to be corrected.
     * @param index Position of the wrongfully placed HeapNode in the Heap A.
     */
    private void correct(Heap A,int index){
        HeapNode childNode = A.heap.get(index);
        HeapNode fatherNode = A.getFather(childNode);
        if (index == 0){                        // if index = 0 it means we're checking the root node. The root node is always >= the others.
            return;
        }
        //check if child(index) > father:
        else if (childNode.getValue() > fatherNode.getValue()) {
            int fatherIndex = A.heap.indexOf(fatherNode);
            A.heap.set(fatherIndex,childNode);          // A[fatherIndex] = childNode
            A.heap.set(index,fatherNode);               // A[childIndex] = fatherNode
            correct(A,fatherIndex);
        }
    }


    /**
     * Gets the left child of the node.
     * @param hn The parent node.
     * @return Left child Node.
     */
    public HeapNode getLeft(HeapNode hn){
        var index = this.heap.indexOf(hn);
        return this.heap.get(2*index);
    }

    /**
     * Gets the right child of the node at position 'i'.
     * @param hn The parent node.
     * @return The right child Node.
     */
    public HeapNode getRight(HeapNode hn){
        var index = this.heap.indexOf(hn);
        return this.heap.get((2*index) + 1);
    }

    /**
     * Gets the father of the node.
     * @param hn The node child.
     * @return The father Node.
     */
    public HeapNode getFather(HeapNode hn){
        var index = this.heap.indexOf(hn);
        return this.heap.get(index/2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Heap{");
        for(int i = 0; i < this.heap.size();i++){
            stringBuilder.append(this.heap.get(i).getValue()+", ");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
