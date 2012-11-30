package pl.wegner.checkout.rules.description;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 * 
 */
public class SimpleDescrptionFormatterTest {

	@Test
	public void assertDescriptionForOneItem() {
		SimpleDescriptionFormatter descriptionFormatter = new SimpleDescriptionFormatter(
				"A");

		String formattedDescription = descriptionFormatter
				.formatSimpleItemDescription(new BigDecimal("100.25"), 1);

		assertThat(formattedDescription, equalTo("A(100.25£) x 1 = 100.25£"));
	}

	@Test
	public void assertDescriptionForTwoItem() {
		SimpleDescriptionFormatter descrptionFormatter = new SimpleDescriptionFormatter(
				"B");

		String formattedDescription = descrptionFormatter
				.formatSimpleItemDescription(new BigDecimal("99.25"), 2);

		assertThat(formattedDescription, equalTo("B(99.25£) x 2 = 198.50£"));
	}

}
