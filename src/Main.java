import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String inputPath = "MemoryInfo//set1Copy";
        String outputPath = "MemoryInfo//set1";


        // copy garbage collector
        MemoryRepresentative memoryRepresentative = new MemoryRepresentative(inputPath + "//heap.csv",inputPath + "//pointers.csv", inputPath + "//roots.txt");
        Copy copy = new Copy(memoryRepresentative.heap,memoryRepresentative.pointers,memoryRepresentative.roots);
        copy.collectGarbage();
        writeHeap(copy.getNewHeap(),"MemoryInfo//set4G1//new-heap.csv");


        //G1
        MemoryRepresentative g1Test = new MemoryRepresentative(inputPath + "//heap.csv",inputPath + "//pointers.csv", inputPath + "//roots.txt");
        Marker g1TestMark = new Marker(g1Test);
        G1 g = new G1(g1TestMark, 304, g1Test);
        writeHeap(g.G1Compactor(), "putPath");
        
        
        MemoryRepresentative set1 = new MemoryRepresentative(inputPath + "//heap.csv",inputPath + "//pointers.csv", inputPath + "//roots.txt");
        MemoryRepresentative set1Compact = new MemoryRepresentative(inputPath + "//heap.csv",inputPath + "//pointers.csv", inputPath + "//roots.txt");
        MemoryRepresentative set2Compact = new MemoryRepresentative(inputPath + "//heap.csv",inputPath + "//pointers.csv", inputPath + "//roots.txt");

        Marker markedSet1 = new Marker(set1);
        Sweeper sweptSet1 = new Sweeper(set1.heap, markedSet1.used);
        Marker markedSet1Compact = new Marker(set1Compact);
        Sweeper sweptSet1Compact = new Sweeper(set1Compact.heap, markedSet1Compact.used);
        MarkCompactor set1CompactMarkCompactor = new MarkCompactor();
        set1CompactMarkCompactor.compactHeap(sweptSet1Compact);

        Marker markedSet2Compact = new Marker(set2Compact);
        Sweeper sweptSet2Compact = new Sweeper(set2Compact.heap, markedSet2Compact.used);
        MarkCompactor set2CompactMarkCompactor = new MarkCompactor();
        set2CompactMarkCompactor.compactHeap(sweptSet2Compact);

        writeHeap(sweptSet2Compact.getSweptHeap(), "MemoryInfo//set2Compact//new-heap.csv");
        writeHeap(sweptSet1.getSweptHeap(), "MemoryInfo//set1//new-heap.csv");
        writeHeap(sweptSet1Compact.getSweptHeap(), "MemoryInfo//set1Compact//new-heap.csv");
    }

    private static void writeHeap(Map<Integer, int[]> heap, String path){
        try{
            int[] keys = heap.keySet().stream().mapToInt(j -> j).toArray();
            FileWriter myWriter = new FileWriter(path);
            for (int i = 0; i < keys.length - 1; i++)
                myWriter.write(keys[i] + "," + Arrays.toString(heap.get(keys[i])).replace(" ", "").replace("[", "").replace("]", "") + "\n");
            myWriter.write(keys[keys.length - 1] + "," + Arrays.toString(heap.get(keys[keys.length - 1])).replace(" ", "").replace("[", "").replace("]", ""));
            myWriter.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}