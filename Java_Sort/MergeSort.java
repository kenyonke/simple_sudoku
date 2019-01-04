import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {

	public static void mergesort(ArrayList<Integer> list) {
		int len = list.size();
		ArrayList<Integer> updatedList = new ArrayList<Integer>();
		subSort(list, 0, len - 1, updatedList);
	}

	public static void subSort(ArrayList<Integer> list, int left, int right, ArrayList<Integer> updatedList) {
		if (left < right) {
			int middle = (left + right) / 2;
			subSort(list, left, middle, updatedList);
			subSort(list, middle + 1, right, updatedList);
			merge(list, left, middle, middle + 1, right, updatedList);
		}
	}

	public static void merge(ArrayList<Integer> list, int left1, int right1, int left2, int right2,
			ArrayList<Integer> updatedList) {
		int left = left1;
		int right = right2;
		int t = 0;
		updatedList.clear();
		
		//start sorting
		while (true) {
			if (list.get(left1) < list.get(left2)) {
				updatedList.add(t, list.get(left1));
				left1 += 1;
				t += 1;
				
				if (left1 > right1) {
					while (left2 <= right2) {
						updatedList.add(t, list.get(left2));
						left2 += 1;
						t += 1;
					}
					break;
				}

			} else {
				updatedList.add(t, list.get(left2));
				left2 += 1;
				t += 1;

				if (left2 > right2) {
					while (left1 <= right1) {
						updatedList.add(t, list.get(left1));
						left1 += 1;
						t += 1;
					}
					break;
				}

			}
		}
		
		int range = right - left; //range of updated list
		for (int i = 0; i <= range; i++) {
			list.set(left, updatedList.get(i));
			left += 1;
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(
				Arrays.asList(2, 4, 11, 6, 15, 7, 5, 13, 9, 0, 10, 8, 14, 1, 3, 12, 19));
		mergesort(list);
		System.out.println(list);
	}
}
