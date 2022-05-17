import java.util.*;

public class Copy {

    private Map<Integer, int[]> heap;
    private Map<Integer, Set<Integer>> pointers;
    private Set<Integer> roots;

    private Map<Integer,int[]> newHeap = new LinkedHashMap<>();
    private Queue<Integer> objQueue = new LinkedList<>();
    private int endHeapIndex;

    public Copy (Map<Integer, int[]> heap , Map<Integer, Set<Integer>> pointers , Set<Integer> roots){
        this.heap = heap;
        this.pointers = pointers;
        this.roots = roots;
        this.endHeapIndex = 0;
    }

    public void collectGarbage(){
        objQueue.addAll(roots);
        while(!objQueue.isEmpty()){
            Integer usedObj = objQueue.poll();
            if(!this.newHeap.containsKey(usedObj)) {

                int[] index = heap.get(usedObj);
                index[1] = (index[1] - index[0]) + this.endHeapIndex;
                index[0] = this.endHeapIndex;
                this.endHeapIndex = index[1] + 1;

                this.newHeap.put(usedObj, index);

                if (this.pointers.get(usedObj) != null) {
                    for (Integer childObj : this.pointers.get(usedObj)) {
                        if (!this.newHeap.containsKey(childObj))
                            objQueue.add(childObj);
                    }
                }
            }
        }
    }

    public Map<Integer,int[]> getNewHeap(){
        return this.newHeap;
    }

}