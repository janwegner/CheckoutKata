package pl.wegner.checkout.app;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;
import pl.wegner.checkout.engine.Engine;
import pl.wegner.checkout.rules.MultiPricedPayingRule;
import pl.wegner.checkout.rules.SimpleItemPayingRule;

public class AppExample {

	public static void main(String... args) {
		List<SimpleItemPayingRule> singleSimpleRule = getSimpleRules();
		List<MultiPricedPayingRule> singleMultiPriceRule = getMultiPricedRules();
		Engine engine = new Engine(singleSimpleRule, singleMultiPriceRule);

		List<CheckinEntry> checkinEntires = createCheckinEntires(args);
		for (CheckinEntry checkinEntry : checkinEntires) {
			engine.addItem(checkinEntry);
		}

		writeResults(engine);
	}

	private static void writeResults(Engine engine) {
		int i = 0;
		BigDecimal priceSum = BigDecimal.ZERO;
		for (CheckoutEntry checkoutEntry : engine.getAllCheckoutEntries()) {
			priceSum = priceSum.add(checkoutEntry.getPrice());
			System.out.printf("%2d. %-50s (%6.2f£)\n", ++i,
					checkoutEntry.getDescription(), priceSum);
		}
		System.out
				.println("==============================================================");
		System.out
				.printf("%54s  %6.2f£\n", "SUMMARY", engine.getCurrentValue());
	}

	private static List<CheckinEntry> createCheckinEntires(String[] args) {
		List<CheckinEntry> entries = new LinkedList<>();
		entries.add(new CheckinEntry("A"));
		entries.add(new CheckinEntry("B"));
		entries.add(new CheckinEntry("A", 6));
		entries.add(new CheckinEntry("C", 2));
		entries.add(new CheckinEntry("C", 1));
		entries.add(new CheckinEntry("A"));
		entries.add(new CheckinEntry("D", 3));
		entries.add(new CheckinEntry("A"));
		entries.add(new CheckinEntry("B", 4));
		entries.add(new CheckinEntry("C"));
		entries.add(new CheckinEntry("B", 1));
		entries.add(new CheckinEntry("C"));
		return entries;
	}

	private static List<MultiPricedPayingRule> getMultiPricedRules() {
		List<MultiPricedPayingRule> payingRules = new LinkedList<>();
		payingRules.add(new MultiPricedPayingRule("A", new BigDecimal("50.00"),
				3, new BigDecimal("130.00")));
		payingRules.add(new MultiPricedPayingRule("B", new BigDecimal("30.00"),
				2, new BigDecimal("45.00")));
		return payingRules;
	}

	private static List<SimpleItemPayingRule> getSimpleRules() {
		List<SimpleItemPayingRule> payingRules = new LinkedList<>();
		payingRules.add(new SimpleItemPayingRule("A", new BigDecimal("50.00")));
		payingRules.add(new SimpleItemPayingRule("B", new BigDecimal("30.00")));
		payingRules.add(new SimpleItemPayingRule("C", new BigDecimal("20.00")));
		payingRules.add(new SimpleItemPayingRule("D", new BigDecimal("15.00")));
		return payingRules;
	}

}
