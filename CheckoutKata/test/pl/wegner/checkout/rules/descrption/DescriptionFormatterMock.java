package pl.wegner.checkout.rules.descrption;

import java.math.BigDecimal;

/**
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 * 
 */
import pl.wegner.checkout.rules.description.DescriptionFormatter;

public class DescriptionFormatterMock implements DescriptionFormatter {

	public static String DEFAULT_DESCRIPTION = "Test for simple item description";

	@Override
	public String formatSimpleItemDescription(BigDecimal price, int amount) {
		return DEFAULT_DESCRIPTION;
	}

}
