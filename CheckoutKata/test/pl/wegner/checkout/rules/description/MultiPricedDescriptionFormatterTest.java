package pl.wegner.checkout.rules.description;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class MultiPricedDescriptionFormatterTest {

	@Test
	public void assertDescriptionForOneItem() {
		MultiPricedDescriptionFormatter descriptionFormatter = new MultiPricedDescriptionFormatter(
				"C", 2, new BigDecimal("10.5"));

		String formattedDescription = descriptionFormatter
				.formatSimpleItemDescription(new BigDecimal("-1.36"), 1);

		assertThat(formattedDescription,
				equalTo("Bonus(2 x C for 10.5£) x 1 = -1.36£"));
	}

	@Test
	public void assertDescriptionForTwoItem() {
		MultiPricedDescriptionFormatter descriptionFormatter = new MultiPricedDescriptionFormatter(
				"D", 4, new BigDecimal("20.55"));

		String formattedDescription = descriptionFormatter
				.formatSimpleItemDescription(new BigDecimal("-10.04"), 2);

		assertThat(formattedDescription,
				equalTo("Bonus(4 x D for 20.55£) x 2 = -10.04£"));
	}

}
