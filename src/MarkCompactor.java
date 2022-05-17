import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MarkCompactor {



    public void compactHeap(Sweeper sweeper){

        Map<Integer, int[]> sweptHeap = sweeper.getSweptHeap();
        int lastIndex = 0;
        for(int[] vals : sweptHeap.values()) {
            if (vals[0] > lastIndex - 1) {
                int diff = vals[0] - lastIndex;
                vals[0] = lastIndex;
                vals[1] = vals[1] - diff;
            }
            lastIndex = vals[1] + 1;


        }
    }
}
