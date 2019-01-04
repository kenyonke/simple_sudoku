import java.util.ArrayList;
import java.util.Arrays;

public class StraightSelectSorting {
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(
				Arrays.asList(2, 4, 11, 6, 15, 7, 5, 13, 9, 0, 10, 8, 14 ,1, 3, 12));
		
		for(int i=0;i<list.size();i++) {
			int sortNum=list.get(i);
			int key = i;
			for(int j=i;j<list.size();j++) {
				if(list.get(j)<sortNum) {
					sortNum = list.get(j);
					key = j;
				}
			}
			list.set(key, list.get(i));
			list.set(i, sortNum);
		}
		System.out.println(list);
	}
	
}
