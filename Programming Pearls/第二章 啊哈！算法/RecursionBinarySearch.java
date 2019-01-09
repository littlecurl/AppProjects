package cn.edu.heuet.II.algorithm;

public class RecursionBinarySearch {
	public static int binarySearch(int[] array, int leftIndex, int rightIndex, int key) {
		int midIndex;
		// 递归结束条件
		if (leftIndex <= rightIndex) {
			// 定义midIndex
			midIndex = (leftIndex + rightIndex) / 2;
			if (key == array[midIndex])
				return midIndex;
			// 要查找的数小于中间值，往左查找
			if (key < array[midIndex]) {
				return binarySearch(array, leftIndex, midIndex - 1, key);
			}
			// 要查找的数大于中间值，往右查找
			return binarySearch(array, midIndex + 1, rightIndex, key);
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] arr = new int[] { 1, 2, 3, 4, 5 };
		int leftIndex = 0;
		int rightIndex = arr.length - 1;
		int key = 5;
		int result = binarySearch(arr, leftIndex, rightIndex, key);
		if (result == -1) {
			System.out.println(key + "不存在");
		} else {
			System.out.println("找到" + key + "了，它的索引值是：" + result);
		}
	}
}
