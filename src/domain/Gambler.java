package domain;

import java.io.Serializable;

/**
 * Title: Gambler<br>
 * Description: <br>
 * Create DateTime: 2018年10月22日 17:06 <br>
 *
 * @author MoEee
 */
public class Gambler implements Serializable {


	private static final long serialVersionUID = 6022820531798575303L;

	private Integer id;

	private Integer power;

	private Integer minValue;

	private Integer MaxValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return MaxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.MaxValue = maxValue;
	}
}
