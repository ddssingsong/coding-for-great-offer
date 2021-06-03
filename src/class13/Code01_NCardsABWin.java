package class13;

import java.math.BigDecimal;

public class Code01_NCardsABWin {

	// 谷歌面试题
	// 1~10的牌数无限，每次你可以从1~10中等概率抽一张牌
	// 当累加和<17时，你将一直抽牌
	// 当累加和>=17且<21时，你将获胜
	// 当累加和>=21时，你将失败
	// 返回获胜的概率
	// 改动态规划意义不大，因为数据量太小了
	public static double f1() {
		return p1(0);
	}

	public static double p1(int cur) {
		if (cur >= 17 && cur < 21) {
			return 1.0;
		}
		if (cur >= 21) {
			return 0.0;
		}
		double w = 0.0;
		for (int i = 1; i <= 10; i++) {
			w += p1(cur + i);
		}
		return w / 10;
	}

	// 扩展问题
	// 1~N的牌数无限，每次你可以从1~N中等概率抽一张牌
	// 当累加和<a时，你将一直抽牌
	// 当累加和>=a且<b时，你将获胜
	// 当累加和>=b时，你将失败
	// 返回获胜的概率
	public static double f2(int N, int a, int b) {
		if (N < 1 || a >= b || a < 0 || b < 0) {
			return 0.0;
		}
		if (b - a >= N) {
			return 1.0;
		}
		return p2(0, N, a, b);
	}

	public static double p2(int cur, int N, int a, int b) {
		if (cur >= a && cur < b) {
			return 1.0;
		}
		if (cur >= b) {
			return 0.0;
		}
		double w = 0.0;
		for (int i = 1; i <= N; i++) {
			w += p2(cur + i, N, a, b);
		}
		return w / N;
	}

	// f2的改进版本，用到了观察位置优化枚举的技巧
	// 可以课上讲一下
	public static double f3(int N, int a, int b) {
		if (N < 1 || a >= b || a < 0 || b < 0) {
			return 0.0;
		}
		if (b - a >= N) {
			return 1.0;
		}
		return p3(0, N, a, b);
	}

	public static double p3(int cur, int N, int a, int b) {
		if (cur >= a && cur < b) {
			return 1.0;
		}
		if (cur >= b) {
			return 0.0;
		}
		if (cur == a - 1) {
			return 1.0 * (b - a) / N;
		}
		double w = p3(cur + 1, N, a, b) + p3(cur + 1, N, a, b) * N;
		if (cur + 1 + N < b) {
			w -= p3(cur + 1 + N, N, a, b);
		}
		return w / N;
	}

	// f3的改进版本的动态规划
	// 可以课上讲一下
	public static double f4(int N, int a, int b) {
		if (N < 1 || a >= b || a < 0 || b < 0) {
			return 0.0;
		}
		if (b - a >= N) {
			return 1.0;
		}
		double[] dp = new double[b];
		for (int i = a; i < b; i++) {
			dp[i] = 1.0;
		}
		if (a - 1 >= 0) {
			dp[a - 1] = 1.0 * (b - a) / N;
		}
		for (int cur = a - 2; cur >= 0; cur--) {
			double w = dp[cur + 1] + dp[cur + 1] * N;
			if (cur + 1 + N < b) {
				w -= dp[cur + 1 + N];
			}
			dp[cur] = w / N;
		}
		return dp[0];
	}

	public static void main(String[] args) {
		System.out.println("N = 10, a = 17, b = 21");
		System.out.println(f1());
		System.out.println(f2(10, 17, 21));
		System.out.println(f3(10, 17, 21));
		System.out.println(f4(10, 17, 21));

		int maxN = 15;
		int maxM = 20;
		int testTime = 100000;
		System.out.println("测试开始");
		System.out.println("比对double类型答案可能会有精度对不准的问题");
		System.out.println("所以只保留小数点后四位");
		System.out.println("如果没有错误打印，说明验证通过");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxN);
			int a = (int) (Math.random() * maxM);
			int b = (int) (Math.random() * maxM);
			double ans2 = new BigDecimal(f2(N, a, b)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			double ans3 = new BigDecimal(f3(N, a, b)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			double ans4 = new BigDecimal(f4(N, a, b)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (ans2 != ans3 || ans2 != ans4) {
				System.out.println("Oops!");
				System.out.println(N + " , " + a + " , " + b);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
			}
		}
		System.out.println("测试结束");

		System.out.println("当N = 10000, a = 67834, b = 72315时，除了方法4之外都超时");
		System.out.print("方法4答案: ");
		System.out.println(f4(10000, 67834, 72315));

	}

}
