import java.util.ArrayList;
import java.util.Arrays;

//bubble sort�� ѭ������(n-1)�Σ����Ͻ������ֵ�λ�ã�ÿһ�ֶ��ܰ������Ǹ����Ƶ���߻����ұߣ��Ӷ���ɴ�С����
public class BubbleSort {
	
	public static void main(String[] args) {
		//sort a list to be the form from min to max
		ArrayList<Integer> numberList = new ArrayList<Integer>(Arrays.asList(3,2,5,4,1,7,6,8));
		for(int i=0;i<numberList.size()-1;i++) {
			for(int j=0;j<numberList.size()-i-1;j++) {
				int firstNum = numberList.get(j);
				int secondNum = numberList.get(j+1);
				if(firstNum>secondNum) {
					numberList.set(j, secondNum);
					numberList.set(j+1, firstNum);
				}
			}
		}
		System.out.print(numberList);
	}
	
}
