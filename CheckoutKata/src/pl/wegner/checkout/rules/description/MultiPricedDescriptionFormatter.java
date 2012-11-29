package pl.wegner.checkout.rules.description;

import java.math.BigDecimal;

public class MultiPricedDescriptionFormatter implements DescriptionFormatter {

	private final String skuCode;
	private final int amountForBonus;
	private final BigDecimal bonusPriceForPack;

	public MultiPricedDescriptionFormatter(String skuCode, int amountForBonus,
			BigDecimal bonusPriceForPack) {
		this.skuCode = skuCode;
		this.amountForBonus = amountForBonus;
		this.bonusPriceForPack = bonusPriceForPack;

	}

	@Override
	public String formatSimpleItemDescription(BigDecimal price, int amount) {
		return "Bonus(" + amountForBonus + " x " + skuCode + " for "
				+ bonusPriceForPack + "£) x " + amount + " = " + price + "£";
	}

}
