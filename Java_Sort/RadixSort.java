import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort {

	// �ҵ�list�������������������������λ
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

		// ��ʼ��bucket
		for (int i = 0; i < 10; i++) {
			buckets.add(new ArrayList<Integer>());
		}

		int cNum;
		for (int i = 1; i <= num; i++) {

			// �Ƚ��������Ž�buckets��
			for (int index = 0; index < list.size(); index++) {
				cNum = list.get(index);
				System.out.println((int) (cNum % Math.pow(10, i) / Math.pow(10, i - 1)));
				buckets.get((int) (cNum % Math.pow(10, i) / Math.pow(10, i - 1))).add(cNum);
			}

			// ��buckets������ַ��ص�list��,��i������
			int n = 0; // nΪlist�е�index�����水��˳�����ַ���list��
			for (int j = 0; j < buckets.size(); j++) {
				ArrayList<Integer> bucket = buckets.get(j);
				for (int k = 0; k < bucket.size(); k++) {
					list.set(n, bucket.get(k));
					n += 1; // ����һ������n��������һλ
				}
				// һ��bucket�������֮�����Ͻ�bucket������������
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
