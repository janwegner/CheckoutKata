package pl.wegner.checkout;

import java.math.BigDecimal;

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
