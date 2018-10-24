import com.alibaba.fastjson.JSON;
import domain.Gambler;
import domain.Statistics;
import method.Dealer;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Title: Main<br>
 * Description: <br>
 * Create DateTime: 2018年10月22日 17:09 <br>
 *
 * @author MoEee
 */
public class Main {

	/**
	 * 抽奖人数
	 */
	private static int GAMBLER_NUMBER = 100;
	/**
	 * 测试抽奖次数
	 */
	private static int GAME_NUMBER = 20000;
	/**
	 * 中奖人数
	 */
	private static int WINNER_NUMBER = 1;
	/**
	 * 当前计算概率支持的最小单位:十万分之一(0.00001)
	 */
	private static int MIN_UNIT = 5;
	/**
	 * 统计用的数组
	 */
	private static List<Statistics> statisticsList;
	/**
	 * 初始的抽奖人员
	 */
	private static List<Gambler> originData;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Long startTime = System.currentTimeMillis();

		init();

		start();

		statistic();

		System.out.println(JSON.toJSONString(statisticsList));
		System.out.println("time cost(ms) :" + (System.currentTimeMillis() - startTime));
	}

	private static void statistic() {
		// 统计实际中奖率
		BigDecimal sumBD = new BigDecimal(GAME_NUMBER);
		for (Statistics statistics : statisticsList) {
			BigDecimal winTime = new BigDecimal(statistics.getWinTime());
			statistics.setActualWinPercent(winTime.divide(sumBD, MIN_UNIT, RoundingMode.HALF_UP).toString());
		}
	}

	private static void start() throws IOException, ClassNotFoundException {
		// 开始抽奖
		for (int i = 0; i < GAME_NUMBER; i++) {
			// 将对象写到流里
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(originData);
			// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			List<Gambler> data = (ArrayList) oi.readObject();
			List<Integer> winnerIds = Dealer.draw(data, WINNER_NUMBER);

			// 加入中奖次数
			for (Integer winnerId : winnerIds) {
				Statistics statistics = statisticsList.get(winnerId - 1);
				statistics.setWinTime(statistics.getWinTime() + 1);
				statisticsList.set(winnerId - 1, statistics);
			}
		}
	}

	private static void init() {
		// 统计数据
		statisticsList = new ArrayList<>(GAMBLER_NUMBER);
		for (int i = 1; i <= GAMBLER_NUMBER; i++) {
			Statistics s = new Statistics();
			s.setWinTime(0);
			statisticsList.add(s);
		}

		// 初始化抽奖人数据
		originData = new ArrayList<>();
		int winPercent = 0;
		// 可能中奖的人
		Gambler possibleWinner = new Gambler();
		possibleWinner.setId(1);
		possibleWinner.setPower(100);
		originData.add(possibleWinner);
		possibleWinner = new Gambler();
		possibleWinner.setId(2);
		possibleWinner.setPower(500);
		originData.add(possibleWinner);
		for (int i = 3; i <= GAMBLER_NUMBER; i++) {
			Gambler gambler = new Gambler();
			gambler.setId(i);
			gambler.setPower(new Random().nextInt(5) + 1);
			originData.add(gambler);
		}
		for (Gambler gambler : originData) {
			winPercent += gambler.getPower();
		}
		BigDecimal winPercentBD = new BigDecimal(winPercent);
		// 预计中奖概率,该中奖概率仅可在中奖人数为1时参考，多中奖人数+权重的中奖概率比较难算
		for (Gambler gambler : originData) {
			BigDecimal power = new BigDecimal(gambler.getPower());
			Statistics statistics = statisticsList.get(gambler.getId() - 1);
			if (WINNER_NUMBER > 1) {
				// TODO
				statistics.setWinPercent("0.00");
			} else {
				statistics.setWinPercent(power.divide(winPercentBD, MIN_UNIT, BigDecimal.ROUND_HALF_UP).toString());
			}
			statisticsList.set(gambler.getId() - 1, statistics);
		}
	}
}
