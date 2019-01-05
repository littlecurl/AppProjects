package cn.edu.heuet.I.basic;

/**
  * 归并排序Java语言 对<a href= "https://blog.csdn.net/morewindows/article/details/6678165">《白话经典算法系列之五归并排序的实现》</a> 
 * C 代码的改编版
 * @author 刘同学
 *
 */
public class MergeSort {
	// 因为要在main方法中进行测试，
	// 所以merge()、recursion()和mergeSort()方法都加了static

	// merge()方法作用:
	// 合并两个<b>有序</b>的数列
	// 归并排序是在“并”的时候进行排序的
	private static void merge(int[] a, int left, int mid, int right, int[] temp) {
		int i = left;
		int m = mid;
		int j = mid + 1;
		int n = right;
		int k = 0;

		while (i <= m && j <= n) {
			// 依次找最小，将其放入temp数组
			if (a[i] <= a[j]) {
				temp[k++] = a[i++];
			} else {
				temp[k++] = a[j++];
			}
		}
		// 如果左右不对称，比较之后还剩余
		// 那么把剩余的依次放入temp数组
		while (i <= m) {
			temp[k++] = a[i++];
		}

		while (j <= n) {
			temp[k++] = a[j++];
		}
		// 将temp数组中的数据拷贝回去a数组
		for (i = 0; i < k; i++) {
			a[left + i] = temp[i];
		}

	}

	// 递归recursion，合并merge
	private static void recursion(int[] a, int left, int right, int[] temp) {
		if (left < right) {
			int mid = (left + right) / 2;
			
			recursion(a, left, mid, temp);
			/**
			  *  上面的递归最深层是
			 * recursion(a, 0, 0, temp);
			 * recursion(a, 1, 1, temp);
			 * merge(a, 0, 0, 1, temp);
			 * 
			  * 将a[0]和a[1]作比较后按大小放入temp数组
			 */
			recursion(a, mid + 1, right, temp);
			/**
			  *  上面的递归最深层是
			 * recursion(a, mid+1, mid+1, temp);
			 * recursion(a, mid+2, mid+2, temp);
			 * merge(a, mid+1, mid+1, mid+2, temp);
			 * 
			  * 将a[mid+1]和a[mid+2]作比较后按大小放入temp数组
			 */
			merge(a, left, mid, right, temp);
		}
	}

	public static boolean mergeSort(int[] a, int length) {
		int[] temp = new int[length];
		if (temp == null)
			return false;
		// 因为left传入0，所以right需要传入length-1
		// 又因为merge()方法需要temp数组，所以需要加上temp
		recursion(a, 0, length - 1, temp);
		return true;
	}

	// 测试
	public static void main(String[] args) {
		int[] a = new int[] { 4, 3, 6, 1, 2, 5 };
		mergeSort(a, a.length);
		for (int i = 0; i < a.length; ++i) {
			System.out.print(a[i] + " ");
		}
	}
}
