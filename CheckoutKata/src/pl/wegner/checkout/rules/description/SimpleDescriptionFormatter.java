package pl.wegner.checkout.rules.description;

import java.math.BigDecimal;

/**
 * 
 * Used to format description of simple rule
 * 
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 *
 */
public class SimpleDescriptionFormatter implements DescriptionFormatter {

	private String skuCode;

	public SimpleDescriptionFormatter(String skuCode) {
		this.skuCode = skuCode;

	}

	@Override
	public String formatSimpleItemDescription(BigDecimal price, int amount) {
		return skuCode + "(" + price + "£) x " + amount + " = "
				+ price.multiply(new BigDecimal(amount)) + "£";
	}

}
