package pl.wegner.checkout.engine;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;
import pl.wegner.checkout.rules.MultiPricedPayingRule;
import pl.wegner.checkout.rules.PayingRuleBase;
import pl.wegner.checkout.rules.SimpleItemPayingRule;

/**
 * Used to perform actual calculations based on given rules and ckeckin entries.
 * 
 * Must be used for checouts for one klient only and rules recreated after and
 * new instance used next. While addding next items we can check current status.
 * 
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 * 
 */
public class Engine {

	private final Map<String, SimpleItemPayingRule> simpleRulesMap;
	private final Map<String, MultiPricedPayingRule> multiPricedRulesMap;
	private final List<CheckoutEntry> checkoutEntries = new LinkedList<>();
	private BigDecimal result = BigDecimal.ZERO;

	public Engine(List<SimpleItemPayingRule> simpleItemRules,
			List<MultiPricedPayingRule> multiPricedRules) {
		this.simpleRulesMap = createMapFromSimpleRules(simpleItemRules);
		this.multiPricedRulesMap = createMapFromSimpleRules(multiPricedRules);
	}

	public void addItem(CheckinEntry checkinEntry) {
		validateIfProperEntry(checkinEntry);
		calculateItem(simpleRulesMap, checkinEntry);
		calculateItem(multiPricedRulesMap, checkinEntry);
	}

	private void calculateItem(Map<String, ? extends PayingRuleBase> rulesMap,
			CheckinEntry checkinEntry) {
		PayingRuleBase simpleItemPayingRule = rulesMap.get(checkinEntry
				.getSkuCode());
		if (simpleItemPayingRule != null) {
			applyRule(checkinEntry, simpleItemPayingRule);
		}
	}

	private void applyRule(CheckinEntry checkinEntry,
			PayingRuleBase simpleItemPayingRule) {
		CheckoutEntry calculatedSimpleCheckoutValue = simpleItemPayingRule
				.calculateCheckoutValue(checkinEntry);
		if (!calculatedSimpleCheckoutValue.getPrice().equals(BigDecimal.ZERO)) {
			checkoutEntries.add(calculatedSimpleCheckoutValue);
			result = result.add(calculatedSimpleCheckoutValue.getPrice());
		}
	}
	
	private <T extends PayingRuleBase> Map<String, T> createMapFromSimpleRules(
			List<T> simpleItemRules) {
		Map<String, T> rules = new HashMap<>(simpleItemRules.size());
		for (T payingRule : simpleItemRules) {
			rules.put(payingRule.getSkuCode(), payingRule);
		}
		return rules;
	}


	public BigDecimal getCurrentValue() {
		return result;
	}

	public List<CheckoutEntry> getAllCheckoutEntries() {
		return Collections.unmodifiableList(checkoutEntries);
	}

	private void validateIfProperEntry(CheckinEntry checkinEntry) {
		if (!simpleRulesMap.containsKey(checkinEntry.getSkuCode())) {
			throw new IllegalArgumentException("Item added with SKU = "
					+ checkinEntry.getSkuCode()
					+ " when no rules applied fot this item "
					+ simpleRulesMap.keySet());
		}
	}
}
