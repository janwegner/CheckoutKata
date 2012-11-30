package pl.wegner.checkout.data;

import java.math.BigDecimal;

/**
 * Contains data with calculation of checkin entry.
 * 
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 *
 */
public class CheckoutEntry {

	private String description;
	private BigDecimal price;

	public CheckoutEntry(String description, BigDecimal price) {
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
