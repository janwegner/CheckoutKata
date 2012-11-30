package pl.wegner.checkout.rules.description;

import java.math.BigDecimal;

/**
 * 
 * Used for formatting description of calculated chaeckout entry.
 * 
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 *
 */
public interface DescriptionFormatter {

	String formatSimpleItemDescription(BigDecimal price, int amount);

}
