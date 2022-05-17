import java.util.LinkedHashMap;
import java.util.Map;

public class G1 {
	
	private Marker marker;
	private Map<Integer,int[]> newHeap = new LinkedHashMap<>();
	private int blockSize;
	private int blocks[];
	private int remainingSize[];
	private int currentIndex[];
	private Map<Integer, int[]> heap = new LinkedHashMap<>();
	
	public G1 (Marker marker, int heapSize, MemoryRepresentative memoryRepresentative) {
		this.heap = memoryRepresentative.heap;
		this.marker = marker;	
		
		this.blockSize = heapSize/16;
		this.blocks = new int[16];
		this.remainingSize = new int[16];
		for (int i=0; i<16; i++) {
			this.remainingSize[i] = this.blockSize;
		}
		this.currentIndex = new int[16];
	}
	
	public Map<Integer, int[]> G1Compactor () {
		
		for (Integer i: marker.used) {
			blocks[getBlockNumber(heap.get(i)[0])]++;
		}
		
		int startBlock = 0;
		for (int i=0; i<16; i++) {
			if (blocks[i] == 0) {
				startBlock = i;
				break;
			}
		}
		
		for (Integer i:marker.used) {
			 
			if (remainingSize[startBlock] > (heap.get(i)[1] - heap.get(i)[0])) {
				int temp[] = new int[2];
				temp[0] = startBlock * blockSize + currentIndex[startBlock];
				temp[1] = startBlock * blockSize + currentIndex[startBlock] + (heap.get(i)[1] - heap.get(i)[0]);
				newHeap.put(i, temp);
				remainingSize[startBlock] -= (heap.get(i)[1] - heap.get(i)[0]);
				currentIndex[startBlock] += (heap.get(i)[1] - heap.get(i)[0]) + 1;
			}
			else {
				for (int j=startBlock; j<16; j++) {
					if (blocks[j] != 0) {
						continue;
					}
					if (remainingSize[j] > (heap.get(i)[1] - heap.get(i)[0])) {
						int temp[] = new int[2];
						temp[0] = j * blockSize + currentIndex[j];
						temp[1] = j * blockSize + currentIndex[j] + (heap.get(i)[1] - heap.get(i)[0]);
						newHeap.put(i, temp);
						remainingSize[j] -= (heap.get(i)[1] - heap.get(i)[0]);
						currentIndex[j] += (heap.get(i)[1] - heap.get(i)[0]) + 1;
						break;
					}
				}
			}
		}
		return newHeap;
	}
	
	private int getBlockNumber (int startIndex) {
		System.out.println(startIndex);
		return (int) Math.ceil(startIndex/blockSize);
	}
	
	
}
