import java.util.ArrayList;
import java.util.Arrays;

public class ShellSort {
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(4,2,11,6,15,7,5,13,9,0,10,8,1,3,12));
		int length = list.size();
		int gap = length/2;
		while(gap!=0) {
			//start group insertion sort
			for(int i=0;i<gap;i++) {
				//start insertion sort
				for(int j=i+gap; j<length; j+=gap) {
					int sortNum = list.get(j);
					int k = j-gap;
					while(k>=0 && list.get(k)>sortNum) {
						list.set(k+gap, list.get(k));
						k -= gap;
					}
					list.set(k+gap, sortNum);
				}
			}
			//update the gap
			gap = gap/2;
			System.out.println(list);
		}
	}
	
}
