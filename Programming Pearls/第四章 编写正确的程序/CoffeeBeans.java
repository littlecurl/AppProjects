package cn.edu.heuet.IV.rightprogram;
 
import java.util.Random;

public class CoffeeBeans {
	private int blackBeanNum;
	private int whiteBeanNum;
	private int totalBeanNum;
	private Random random = new Random();

	public CoffeeBeans() {
		// 初始化白色豆子及黑色豆子数量
		blackBeanNum = this.random.nextInt(100);
		whiteBeanNum = this.random.nextInt(100);
		totalBeanNum = blackBeanNum + whiteBeanNum;
		while (totalBeanNum < 1) {
			blackBeanNum = this.random.nextInt(100);
			whiteBeanNum = this.random.nextInt(100);
			totalBeanNum = blackBeanNum + whiteBeanNum;
		}
	}

	private void algorithmStart() {
		System.out.println("ALGORITHM START :: " + blackBeanNum + " , " + whiteBeanNum);
		// 随机生成参数不合法！
		if (totalBeanNum < 1) {
			System.out.println("参数不合法！！");
			return;
		}
		// 生成参数为1，结束！
		if (totalBeanNum == 1) {
			System.out.println("totalBeanNum 初始化为  1 ! ");
			return;
		}
		// 参数正常，运行！
		while (totalBeanNum > 1) {
			selectBeans();
		}
		System.out.println(
				"ALGORITHM END :: " + (blackBeanNum == 1 ? "黑色豆子" : 0) + " , " + (whiteBeanNum == 1 ? "白色豆子" : 0));
	}

	private void selectBeans() {
		int randomNum = this.random.nextInt(1001);
		// 随机数不合法，重新获取
		while (randomNum == 0 || (whiteBeanNum <= 1 && (randomNum > 0 && randomNum <= 250))
				|| ((whiteBeanNum == 0 || blackBeanNum == 0) && (randomNum > 250 && randomNum <= 750))
				|| ((blackBeanNum <= 1 && (randomNum > 750 && randomNum <= 1000)))) {
			randomNum = this.random.nextInt(1001);
		}
		if ((whiteBeanNum > 1) && (randomNum > 0 && randomNum <= 250)) { // 白白
			whiteBeanNum -= 2; // 颜色相同，都扔掉
			blackBeanNum += 1; // 放入一粒黑色豆子
		} else if ((blackBeanNum > 1) && (randomNum > 750 && randomNum <= 1000)) { // 黑黑
			blackBeanNum -= 2; // 颜色相同，都扔掉
			blackBeanNum += 1; // 放入一粒黑色豆子
		} else if ((whiteBeanNum > 0 && blackBeanNum > 0) && (randomNum > 250 && randomNum <= 750)) { // 黑白 或 白黑
			blackBeanNum -= 1; // 颜色不同，将黑色豆子扔掉，将白色豆子放回
		} else {
			System.out.println("异常情况！ randomNum = " + randomNum + " ；whiteBeanNum = " + whiteBeanNum
					+ " ； blackBeanNum = " + blackBeanNum);
		}
		totalBeanNum = blackBeanNum + whiteBeanNum;
	}

	public void setBlackBeanNum(int blackBeanNum) {
		this.blackBeanNum = blackBeanNum;
	}

	public int getBlackBeanNum() {
		return blackBeanNum;
	}

	public void setWhiteBeanNum(int whiteBeanNum) {
		this.whiteBeanNum = whiteBeanNum;
	}

	public int getWhiteBeanNum() {
		return whiteBeanNum;
	}

	public void setTotalBeanNum(int totalBeanNum) {
		this.totalBeanNum = totalBeanNum;
	}

	public int getTotalBeanNum() {
		return totalBeanNum;
	}
	
	// 测试
	public static void main(String[] args) {
		CoffeeBeans beans = new CoffeeBeans();
		beans.algorithmStart();
	}
}
