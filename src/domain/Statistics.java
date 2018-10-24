package domain;

/**
 * Title: Statistics<br>
 * Description: <br>
 * Create DateTime: 2018年10月23日 15:51 <br>
 *
 * @author MoEee
 */
public class Statistics {
	private Integer winTime;

	private String winPercent;

	private String actualWinPercent;

	public Integer getWinTime() {
		return winTime;
	}

	public void setWinTime(Integer winTime) {
		this.winTime = winTime;
	}

	public String getWinPercent() {
		return winPercent;
	}

	public void setWinPercent(String winPercent) {
		this.winPercent = winPercent;
	}

	public String getActualWinPercent() {
		return actualWinPercent;
	}

	public void setActualWinPercent(String actualWinPercent) {
		this.actualWinPercent = actualWinPercent;
	}
}
