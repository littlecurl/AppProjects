package cn.edu.heuet.II.algorithm;

public class BinarySearch {
	// 如果存在，返回索引下标，如果不存在，返回-1
	public static int binarySearch(int[] array, int key) {
		int leftIndex = 0;
		int rightIndex = array.length - 1;
		int midIndex;
		// 这里一定得是 <= ，否则无法找到边界的数
		while(leftIndex <= rightIndex) {
			midIndex = (leftIndex + rightIndex)/2;
			if(key == array[midIndex]) {
				return midIndex;
			}
			if(key < array[midIndex]) {
				//这里一定需要-1，否则会陷入死循环
				rightIndex = midIndex - 1;
			} else {
				//这里一定需要+1，否则会陷入死循环
				leftIndex = midIndex + 1;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] arr = new int[] { 1, 2, 3, 4 };
		int key = 3;
		int result = binarySearch(arr, key);
		if(result == -1) {
			System.out.println(key+"不存在");
		} else {
			System.out.println("找到"+key+"了，它的索引值是："+result);
		}
	}
}
