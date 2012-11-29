package pl.wegner.checkout.rules.description;

import java.math.BigDecimal;

public class StandartDescriptionFormatter implements DescriptionFormatter {

	private String skuCode;

	public StandartDescriptionFormatter(String skuCode) {
		this.skuCode = skuCode;

	}

	@Override
	public String formatSimpleItemDescription(BigDecimal price, int amount) {
		return skuCode + "(" + price + "£) x " + amount + " = "
				+ price.multiply(new BigDecimal(amount)) + "£";
	}

}
