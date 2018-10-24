package method;

import domain.Gambler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Title: Dealer<br>
 * Description: <br>
 * Create DateTime: 2018年10月22日 17:09 <br>
 *
 * @author MoEee
 */
public class Dealer {

	public static List<Integer> draw(List<Gambler> gamblers, Integer winnerNumber) {
		List<Integer> winnerIds = new ArrayList<Integer>(winnerNumber);
		for (int i = 0; i < winnerNumber; i++) {
			Gambler gambler = getWinnerGambler(gamblers);
			winnerIds.add(gambler.getId());
		}
		return winnerIds;
	}

	private static Gambler getWinnerGambler(List<Gambler> gamblers) {
		initValue(gamblers);
		int maxIndex = gamblers.get(gamblers.size() - 1).getMaxValue();
		Integer winnerPower = new Random().nextInt(maxIndex);
		Gambler res = findWinner(gamblers, 1, gamblers.size(), winnerPower);
		gamblers.remove(res);
		return res;
	}

	private static Gambler findWinner(List<Gambler> gamblers, int startIndex, int endIndex, Integer winnerPower) {
		int middleIndex = (startIndex + endIndex) / 2;
		Gambler gambler = gamblers.get(middleIndex - 1);
		if (gambler.getMaxValue() < winnerPower) {
			// 查找大的一半
			return findWinner(gamblers, middleIndex + 1, endIndex, winnerPower);
		} else if (gambler.getMinValue() > winnerPower) {
			// 查找小的一半
			return findWinner(gamblers, startIndex, middleIndex - 1, winnerPower);
		} else {
			return gambler;
		}
	}


	private static void initValue(List<Gambler> gamblers) {
		int index = 0;
		for (Gambler gambler : gamblers) {
			int power = gambler.getPower();
			gambler.setMinValue(index);
			index += power - 1;
			gambler.setMaxValue(index);
			index++;
		}
	}
}
