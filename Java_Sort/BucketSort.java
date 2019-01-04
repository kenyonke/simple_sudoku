import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BucketSort {
	
	public static void sort(ArrayList<Integer> list,int bucketNum) {
		if(list.size()<=1) {
			return;
		}
		
		//find out max number and min number
		int max = list.get(0);
		int min = list.get(0);
		for(int i=0;i<list.size();i++) {
			
			int cNum = list.get(i);
			if(cNum<min) {
				min = cNum;
			}else if(cNum>max) {
				max = cNum;
			}	
		}
		
		//the gap between buckets
		double gap = (max-min+1)/bucketNum;
		
		@SuppressWarnings("rawtypes")
		//create a bucket list with buckets inside
		ArrayList<ArrayList> bucketList = new ArrayList<ArrayList>();
		for(int i=0;i<bucketNum;i++) {
			bucketList.add(new ArrayList<Integer>());
		}
		
		//put numbers into buckets
		for(int i=0;i<list.size();i++) {			
			int cNum = list.get(i);
			int bucketIndex = (int) ((cNum-min)/gap); //find out the index of bucket of a number 
			System.out.println(cNum);
			bucketList.get(bucketIndex).add(cNum);			
		}
		
		//sort buckets which is not empty and put numbers back to the list orderly 
		ArrayList<Integer> bucket;
		int indexList = 0;
		for(int i=0;i<bucketNum;i++) {
			bucket = bucketList.get(i);
			if(!bucket.isEmpty()) {
				Collections.sort(bucket);
			}
			for(int j=0;j<bucket.size();j++) {
				list.set(indexList, bucket.get(j));
				indexList ++;
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>(
				Arrays.asList(12, 444, 111, 36, 145, 67, 55, 713, 9, 10, 1110, 438, 2500, 10, 433, 212, 19, 1, 4, 6));
		sort(list, 5);
		System.out.println(list);
	}
}
