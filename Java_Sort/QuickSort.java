import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort {

	public static void quickSort(ArrayList<Integer> list, int listLeft, int listRight) {
		int left = listLeft;
		int right = listRight;
		int key = listLeft; // initial key as the first number in the list
		if (left < right) {
			while (left != right) {
				// from right to left in the list, exchange when a number was found smaller than
				// the key number
				while (right > left) {
					if (list.get(right) < list.get(key)) {
						int firNum = list.get(right);
						list.set(right, list.get(key));
						list.set(key, firNum);
						// update index of the key number
						key = right;
						break;
					}
					right -= 1;
				}
				// from left to right in the list, exchange when a number was found bigger than
				// the key number
				while (left < right) {
					if (list.get(left) > list.get(key)) {
						int firNum = list.get(left);
						list.set(left, list.get(key));
						list.set(key, firNum);
						// update index of the key number
						key = left;
						break;
					}
					left += 1;
				}
			}
			//recursion for sorting lists on the left side and right side of key number
			quickSort(list, listLeft, key-1);
			quickSort(list, key+1, listRight);
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(
				Arrays.asList(2, 4, 11, 6, 15, 7, 5, 13, 9, 0, 10, 8, 14 ,1, 3, 12));
		quickSort(list,0,list.size()-1);
		System.out.println(list);
	}
}
