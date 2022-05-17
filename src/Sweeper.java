import java.util.*;

public class Sweeper {

    private Map<Integer, int[]> sweptHeap = new LinkedHashMap<>();

    public Sweeper(Map<Integer, int[]> oldHeap, Set<Integer> used) {
        for (int object : oldHeap.keySet()){
            if (used.contains(object))
                sweptHeap.put(object, oldHeap.get(object));
        }
    }

    public Map<Integer, int[]> getSweptHeap(){
        return this.sweptHeap;
    }

}
