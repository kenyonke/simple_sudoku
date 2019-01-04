import java.util.ArrayList;
import java.util.Arrays;

public class HeapSort {

	public static void main(String[] args) {

		ArrayList<Integer> list = new ArrayList<Integer>(
				Arrays.asList(2, 4, 11, 6, 15, 7, 5, 13, 9, 0, 10, 8, 14, 1, 3, 12, 19));
		int length = list.size();
		int t = 0; // times in loop

		while (true) {
			// the number of node is one bigger than index at the beginning
			// -t means we start from a new tree after one loop which had found the biggest
			// number
			for (int i = (length - t) / 2; i > 0; i--) {
				// +t means skip sorted numbers in the list and only sort unsorted numbers in
				// the list
				int parentNum = list.get(i - 1 + t);

				// compare parent node with its left node and right node(if it has)
				for (int j = 2 * i; j < 2 * (i + 1) && j <= length - t; j++) {
					int childNum = list.get(j - 1 + t);
					if (childNum > parentNum) {
						list.set(i - 1 + t, childNum);
						list.set(j - 1 + t, parentNum);
						parentNum = list.get(i - 1 + t); // change parentNum immediately after a exchange
					}
				}
			}
			t += 1; //times record

			// break when length times loop
			if (t == length - 1) {break;}
		}

		System.out.println(list);
	}

}
