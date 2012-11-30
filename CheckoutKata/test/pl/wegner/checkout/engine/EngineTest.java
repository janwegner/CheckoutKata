package pl.wegner.checkout.engine;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.rules.MultiPricedPayingRule;
import pl.wegner.checkout.rules.SimpleItemPayingRule;

/**
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 * 
 */
public class EngineTest {

	@Test
	public void shouldCalculateOneItemPrice() {

		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		Engine engine = new Engine(singleSimpleRule,
				Collections.<MultiPricedPayingRule> emptyList());

		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getCurrentValue(), equalTo(new BigDecimal("2.50")));
	}

	@Test
	public void shouldCalculatePriceOfTwoSameItems() {

		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		Engine engine = new Engine(singleSimpleRule,
				Collections.<MultiPricedPayingRule> emptyList());

		engine.addItem(new CheckinEntry("A"));
		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getCurrentValue(), equalTo(new BigDecimal("5.00")));
	}

	@Test
	public void shouldCalculatePriceOfTwoDifferentItems() {

		List<SimpleItemPayingRule> singleSimpleRule = new LinkedList<>();
		singleSimpleRule.add(new SimpleItemPayingRule("A", new BigDecimal(
				"2.50")));
		singleSimpleRule.add(new SimpleItemPayingRule("B", new BigDecimal(
				"1.75")));
		Engine engine = new Engine(singleSimpleRule,
				Collections.<MultiPricedPayingRule> emptyList());

		engine.addItem(new CheckinEntry("B"));
		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getCurrentValue(), equalTo(new BigDecimal("4.25")));
	}

	@Test
	public void shouldCalculatePriceOfTwoDifferentItemsWithBonusIfNotEnoughtItems() {

		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		List<MultiPricedPayingRule> singleMultiPriceRule = Collections
				.singletonList(new MultiPricedPayingRule("A", new BigDecimal(
						"2.50"), 3, new BigDecimal("5.15")));
		Engine engine = new Engine(singleSimpleRule, singleMultiPriceRule);

		engine.addItem(new CheckinEntry("A"));
		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getCurrentValue(), equalTo(new BigDecimal("5.00")));
	}

	@Test
	public void shouldCalculatePriceWithBonus() {

		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		List<MultiPricedPayingRule> singleMultiPriceRule = Collections
				.singletonList(new MultiPricedPayingRule("A", new BigDecimal(
						"2.50"), 2, new BigDecimal("5.15")));
		Engine engine = new Engine(singleSimpleRule, singleMultiPriceRule);

		engine.addItem(new CheckinEntry("A"));
		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getCurrentValue(), equalTo(new BigDecimal("5.15")));
	}

	@Test
	public void shouldContainOneCheckOutValue() {

		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		List<MultiPricedPayingRule> singleMultiPriceRule = Collections
				.singletonList(new MultiPricedPayingRule("A", new BigDecimal(
						"2.50"), 2, new BigDecimal("5.15")));
		Engine engine = new Engine(singleSimpleRule, singleMultiPriceRule);

		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getAllCheckoutEntries().size(), equalTo(1));
	}

	@Test
	public void shouldContain3CheckOutValuesFor2itemsWithBonus() {

		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		List<MultiPricedPayingRule> singleMultiPriceRule = Collections
				.singletonList(new MultiPricedPayingRule("A", new BigDecimal(
						"2.50"), 2, new BigDecimal("4.15")));
		Engine engine = new Engine(singleSimpleRule, singleMultiPriceRule);

		engine.addItem(new CheckinEntry("A"));
		engine.addItem(new CheckinEntry("A"));

		assertThat(engine.getAllCheckoutEntries().size(), equalTo(3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfInvokedForUnknownItem() {
		List<SimpleItemPayingRule> singleSimpleRule = Collections
				.singletonList(new SimpleItemPayingRule("A", new BigDecimal(
						"2.50")));
		Engine engine = new Engine(singleSimpleRule,
				Collections.<MultiPricedPayingRule> emptyList());

		engine.addItem(new CheckinEntry("B"));
	}
}
