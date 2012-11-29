package pl.wegner.checkout.rules.description;

import java.math.BigDecimal;

public interface DescriptionFormatter {

	String formatSimpleItemDescription(BigDecimal price, int amount);

}
