package cn.edu.heuet.I.basic;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BitSetTest {
	public static void main(String[] args) {
		// 准备一百万个范围在[0-1百万)的随机数
		final int ONE_MILLION = 100 * 10000;
		Random random = new Random();
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < ONE_MILLION; i++) {
			int randomResult = random.nextInt(ONE_MILLION);
			list.add(randomResult);
		}
		// 进行位图排序
		BitSet bitSet = new BitSet(ONE_MILLION);
		for (int i = 0; i < ONE_MILLION; i++) {
			// 将索引对应位设为true
			bitSet.set(list.get(i));
		}
		// 测试
		int[] test = new int[] {123,456,789,1023,4056,7089};
		for(int i=0; i<test.length; i++) {
			if(!bitSet.get(test[i])) {
				System.out.println(test[i]+"已随机生成");
			} else {
				System.out.println(test[i]+"未生成");
			}
		}
		
		
	}
		
}
