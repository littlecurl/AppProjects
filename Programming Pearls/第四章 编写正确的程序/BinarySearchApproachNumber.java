package cn.edu.heuet.IV.rightprogram;
 
public class BinarySearchApproachNumber {
	public static int[] binarySearchApproachNumber(int[] array, int targetNum) {
		int leftIndex = 0;
		int rightIndex = array.length - 1;
		while (leftIndex <= rightIndex) {
			
			// 剪绳子求中值算法
			int midIndex = leftIndex + ((rightIndex - leftIndex) >> 1);

			// 如果找到了，有三种情况
			if (array[midIndex] == targetNum) {
				// 如果在数组最左边
				if (midIndex == 0) {
					// 返回2个数
					int[] result = new int[2];
					result[0] = array[midIndex];
					result[1] = array[midIndex + 1];
					return result;
				}
				// 如果在数组最右边
				if (midIndex == array.length - 1) {
					// 返回2个数
					int[] result = new int[2];
					result[0] = array[midIndex - 1];
					result[1] = array[midIndex];
					return result;
				}
				// 在数组中间，返回三个数
				int[] result = new int[3];
				result[0] = array[midIndex - 1];
				result[1] = array[midIndex];
				result[2] = array[midIndex + 1];
				return result;
			}
			// 暂时没找到，向右继续找
			if (array[midIndex] < targetNum) {
				leftIndex = midIndex + 1;
			}
			// 暂时没找到，向左继续找
			if (array[midIndex] > targetNum) {
				rightIndex = midIndex - 1;
			}
		}

		// 找不到目标数，有三种情况
		if (leftIndex > rightIndex) {
			// 目标数比数组最小的数还小
			if (rightIndex == -1) {
				// 返回2个数
				int[] result = new int[2];
				result[0] = targetNum;
				result[1] = array[0];
				return result;
			}
			// 目标数比数组最大的数还大
			if (leftIndex == array.length) {
				// 返回2个数
				int[] result = new int[2];
				result[0] = array[array.length - 1];
				result[1] = targetNum;
				return result;
			}
		}
		// 最后，目标数在数组范围内，但不与任何数相等，返回三个数
		int[] result = new int[3];
		result[0] = array[rightIndex];
		result[1] = targetNum;
		result[2] = array[leftIndex];
		return result;
	}

	// 测试
	public static void main(String[] args) {
		int[] arr = new int[] { 1, 2, 4, 5 };
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < 7; i++) {
			int targetNum = i;
			System.out.println("targetNum:" + targetNum);
			int[] res = binarySearchApproachNumber(arr, targetNum);
			for (int r : res) {
				System.out.print(r + " ");
			}
			System.out.println();
		}
	}

}
