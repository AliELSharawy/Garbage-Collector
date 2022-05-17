import java.util.*;

public class Marker {

    Set<Integer> used = new HashSet<>();
    Set<Integer> unUsed = new HashSet<>();

    public Marker(MemoryRepresentative memoryRepresentative) {
        extractUsed(memoryRepresentative.roots, memoryRepresentative.pointers);
        extractUnUsed(memoryRepresentative.heap);
    }

    private void extractUsed(Set<Integer> roots, Map<Integer, Set<Integer>> pointers){
        roots.forEach(root -> {
            if (!used.contains(root)) {
                used.add(root);
                if (pointers.containsKey(root))
                    extractUsed(pointers.get(root), pointers);
            }
        });
    }

    private void extractUnUsed(Map<Integer, int[]> heap){
       for (int object : heap.keySet())
           if(!used.contains(object))
               unUsed.add(object);
    }

}
