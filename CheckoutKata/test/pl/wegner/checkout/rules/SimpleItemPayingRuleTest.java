package pl.wegner.checkout.rules;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;
import pl.wegner.checkout.rules.descrption.DescriptionFormatterMock;

/**
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 * 
 */
public class SimpleItemPayingRuleTest {

	private BigDecimal costOfOneAItem = new BigDecimal("99.0");
	CheckinEntry singleACheckinEntry = new CheckinEntry("A");

	@Test
	public void shouldCalculateProperValueForSingleItem() {
		PayingRuleBase rule = new SimpleItemPayingRule("A", costOfOneAItem);

		CheckoutEntry result = rule.calculateCheckoutValue(singleACheckinEntry);

		assertThat(result.getPrice(), equalTo(costOfOneAItem));
	}

	@Test
	public void shouldCalculateProperValueForMultipleItems() {
		int amountOfProductsInEntry = 2;
		CheckinEntry doubleACheckinEntry = new CheckinEntry("A",
				amountOfProductsInEntry);

		PayingRuleBase rule = new SimpleItemPayingRule("A", costOfOneAItem);

		CheckoutEntry result = rule.calculateCheckoutValue(doubleACheckinEntry);

		BigDecimal costOf2AItems = costOfOneAItem.multiply(new BigDecimal(
				amountOfProductsInEntry));
		assertThat(result.getPrice(), equalTo(costOf2AItems));
	}

	@Test
	public void shouldContainProperDescription() {
		PayingRuleBase rule = new SimpleItemPayingRule("A", costOfOneAItem,
				new DescriptionFormatterMock());

		CheckoutEntry result = rule.calculateCheckoutValue(singleACheckinEntry);

		assertThat(result.getDescription(),
				equalTo(DescriptionFormatterMock.DEFAULT_DESCRIPTION));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfInvokedForWrongItem() {
		PayingRuleBase rule = new SimpleItemPayingRule("B", costOfOneAItem);
		rule.calculateCheckoutValue(singleACheckinEntry);
	}

}
