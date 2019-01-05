package cn.edu.heuet.I.basic;

public class QuickSort {
	
	public static void qSort(int[] arr, int left, int right) {
		if (left >= right || arr == null || arr.length <= 1) {
			return;
		}
		int i = left, j = right, mid = arr[(left + right) / 2];
		while (i <= j) {
			while (arr[i] < mid) {
				++i;
			}
			while (arr[j] > mid) {
				--j;
			}
			if (i < j) {
				int t = arr[i];
				arr[i] = arr[j];
				arr[j] = t;
				++i;
				--j;
			} else if (i == j) {
				++i;
			}
		}
		qSort(arr, left, j);
		qSort(arr, i, right);
	}
	// 测试
	public static void main(String[] args) {
		int[] arr = new int[] { 1, 4, 8, 2, 3, 3 };
		qSort(arr, 0, arr.length - 1);
		String out = "";
		for (int digit : arr) {
			out += (digit + ",");
		}
		System.out.println(out);
	}
}