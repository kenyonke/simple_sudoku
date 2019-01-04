import java.util.ArrayList;
import java.util.Arrays;

public class InsertionSort {
	public static void main(String[] args){
		//int[] b = {1,2,3}; 
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(5,4,6,2,1,3));
		for(int i=1; i<list.size();i++) {
			int sortNum = list.get(i);
			int j = i-1;
			while(j>=0 && list.get(j)>sortNum) {
				list.set(j+1, list.get(j));
				j -= 1;
			}
			list.set(j+1, sortNum);
		}
		System.out.println(list);
		}
	}