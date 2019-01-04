import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort {

	// 找到list中最大的数，并返回它的最大数位
	public static int findMax(ArrayList<Integer> list) {
		int num = 0;
		int maxNum = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) > maxNum) {
				maxNum = list.get(i);
			}
		}

		int j = 1;
		while (maxNum / j != 0) {
			j *= 10;
			num += 1;
		}

		return num;
	}

	public static void radixSort(ArrayList<Integer> list, int num) {
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();

		// 初始化bucket
		for (int i = 0; i < 10; i++) {
			buckets.add(new ArrayList<Integer>());
		}

		int cNum;
		for (int i = 1; i <= num; i++) {

			// 先将所有数放进buckets中
			for (int index = 0; index < list.size(); index++) {
				cNum = list.get(index);
				System.out.println((int) (cNum % Math.pow(10, i) / Math.pow(10, i - 1)));
				buckets.get((int) (cNum % Math.pow(10, i) / Math.pow(10, i - 1))).add(cNum);
			}

			// 将buckets里的数字返回到list中,第i次排列
			int n = 0; // n为list中的index，下面按照顺序将数字返回list中
			for (int j = 0; j < buckets.size(); j++) {
				ArrayList<Integer> bucket = buckets.get(j);
				for (int k = 0; k < bucket.size(); k++) {
					list.set(n, bucket.get(k));
					n += 1; // 返回一个数，n就往后移一位
				}
				// 一个bucket返还完毕之后，马上将bucket里面的内容清空
				bucket.clear();
			}

		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(
				Arrays.asList(12, 444, 111, 36, 145, 67, 55, 713, 9, 10, 1110, 438, 2500, 10, 433, 212, 19, 1, 4, 6));
		radixSort(list, findMax(list));
		System.out.println(list);
	}

}
