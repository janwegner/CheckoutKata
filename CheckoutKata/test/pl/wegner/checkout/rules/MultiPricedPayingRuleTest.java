package pl.wegner.checkout.rules;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;
import pl.wegner.checkout.rules.descrption.DescriptionFormatterMock;

public class MultiPricedPayingRuleTest {

	@Test
	public void shouldReturnEmptyCheckoutCodeIfNotEnoughtProductsForRule() {
		MultiPricedPayingRule multiPricedRule = new MultiPricedPayingRule("A",
				new BigDecimal("50.0"), 3, new BigDecimal("130.0"));

		CheckinEntry checkinEntry = new CheckinEntry("A");
		CheckoutEntry checkoutEntry = multiPricedRule
				.calculateCheckoutValue(checkinEntry);

		assertThat(checkoutEntry.getPrice(), equalTo(new BigDecimal("0")));
	}

	@Test
	public void shouldReturnBonusValueForProperItemAmount() {
		MultiPricedPayingRule multiPricedRule = new MultiPricedPayingRule("A",
				new BigDecimal("50.0"), 3, new BigDecimal("130.0"));

		CheckinEntry checkinEntry = new CheckinEntry("A", 3);
		CheckoutEntry checkoutEntry = multiPricedRule
				.calculateCheckoutValue(checkinEntry);

		assertThat(checkoutEntry.getPrice(), equalTo(new BigDecimal("-20.0")));
	}

	@Test
	public void shouldReturnBonusValueForProperItemAmountIfGainedInTwoSteps() {
		MultiPricedPayingRule multiPricedRule = new MultiPricedPayingRule("A",
				new BigDecimal("50.0"), 2, new BigDecimal("90.0"));

		CheckinEntry checkinEntry = new CheckinEntry("A", 1);
		multiPricedRule.calculateCheckoutValue(checkinEntry);
		CheckoutEntry checkoutEntry = multiPricedRule
				.calculateCheckoutValue(checkinEntry);

		assertThat(checkoutEntry.getPrice(), equalTo(new BigDecimal("-10.0")));
	}

	@Test
	public void shouldReturnBonusValueIfAmountIsBiggerThanSizeButLessThanTwice() {
		MultiPricedPayingRule multiPricedRule = new MultiPricedPayingRule("A",
				new BigDecimal("60.0"), 3, new BigDecimal("130.0"));

		CheckinEntry checkinEntry = new CheckinEntry("A", 4);
		CheckoutEntry checkoutEntry = multiPricedRule
				.calculateCheckoutValue(checkinEntry);

		assertThat(checkoutEntry.getPrice(), equalTo(new BigDecimal("-50.0")));
	}

	@Test
	public void shouldReturnBonusTwiceValueIfAmountIsTwiceSizeAsNeeded() {
		MultiPricedPayingRule multiPricedRule = new MultiPricedPayingRule("A",
				new BigDecimal("60.0"), 2, new BigDecimal("90.0"));

		CheckinEntry checkinEntry = new CheckinEntry("A", 4);
		CheckoutEntry checkoutEntry = multiPricedRule
				.calculateCheckoutValue(checkinEntry);

		assertThat(checkoutEntry.getPrice(), equalTo(new BigDecimal("-60.0")));
	}

	@Test
	public void shouldReturnSingeBonusInSecondRunIfAmountIsTwiceSizeAsNeededInTwoSteps() {
		MultiPricedPayingRule multiPricedRule = new MultiPricedPayingRule("A",
				new BigDecimal("60.0"), 2, new BigDecimal("90.0"));

		CheckinEntry firstCheckinEntry = new CheckinEntry("A", 3);
		multiPricedRule.calculateCheckoutValue(firstCheckinEntry);
		CheckinEntry secondCheckinEntry = new CheckinEntry("A", 1);
		CheckoutEntry secondCheckoutEntry = multiPricedRule
				.calculateCheckoutValue(secondCheckinEntry);

		assertThat(secondCheckoutEntry.getPrice(), equalTo(new BigDecimal(
				"-30.0")));
	}

	@Test
	public void shouldContainProperDescription() {
		PayingRuleBase rule = new MultiPricedPayingRule("A", new BigDecimal(
				"60.0"), 3, new BigDecimal("130.0"),
				new DescriptionFormatterMock());

		CheckinEntry checkinEntry = new CheckinEntry("A", 4);
		CheckoutEntry result = rule.calculateCheckoutValue(checkinEntry);

		assertThat(result.getDescription(),
				equalTo(DescriptionFormatterMock.DEFAULT_DESCRIPTION));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfInvokedForWrongItem() {
		MultiPricedPayingRule multiPricedRuleForB = new MultiPricedPayingRule(
				"B", new BigDecimal("60.0"), 3, new BigDecimal("130.0"));
		CheckinEntry checkinEntryForA = new CheckinEntry("A", 4);

		multiPricedRuleForB.calculateCheckoutValue(checkinEntryForA);
	}
}
