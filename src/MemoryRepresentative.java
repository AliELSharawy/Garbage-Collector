import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryRepresentative {

    Set<Integer> roots = new HashSet<>();
    Map<Integer, Set<Integer>> pointers = new LinkedHashMap<>();
    Map<Integer, int[]> heap = new LinkedHashMap<>();

    public MemoryRepresentative(String heapPath, String pointersPath, String rootsPath) {

        representHeap(heapPath);
        representPointers(pointersPath);
        readRoots(rootsPath);
        this.heap = sortHeap(this.heap);
    }
    private static Map<Integer, int[]> sortHeap(Map<Integer, int[]> heap){
        Map<Integer, int[]> sortedHeap = heap.entrySet().stream().sorted((x, y)->(Integer.compare(x.getValue()[0], y.getValue()[0]))).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));
        return sortedHeap;
    }

    private void representHeap(String path){
        try{
            File fileToRead = new File(path);
            Scanner fileReader = new Scanner(fileToRead);
            while(fileReader.hasNextLine()) {
                int[] lineData = Arrays.stream(fileReader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                int[] startEndArray = {lineData[1], lineData[2]};
                heap.put(lineData[0], startEndArray);
            }
            fileReader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void representPointers(String path){
        try{
            File fileToRead = new File(path);
            Scanner fileReader = new Scanner(fileToRead);
            while(fileReader.hasNextLine()) {
                int[] lineData = Arrays.stream(fileReader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                if (pointers.containsKey(lineData[0])) {
                    pointers.get(lineData[0]).add(lineData[1]);
                }else{
                    Set<Integer> temp = new HashSet<>();
                    temp.add(lineData[1]);
                    pointers.put(lineData[0], temp);
                }
            }
            fileReader.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void readRoots(String path){
        try{
            File fileToRead = new File(path);
            Scanner fileReader = new Scanner(fileToRead);
            while(fileReader.hasNextLine())
                roots.add(Integer.valueOf(fileReader.nextLine()));
            fileReader.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
