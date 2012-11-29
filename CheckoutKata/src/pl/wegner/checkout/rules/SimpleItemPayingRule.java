package pl.wegner.checkout.rules;

import java.math.BigDecimal;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;
import pl.wegner.checkout.rules.description.DescriptionFormatter;
import pl.wegner.checkout.rules.description.StandartDescriptionFormatter;

public class SimpleItemPayingRule extends PayingRuleBase {

	private final BigDecimal costOfOneAItem;
	private final DescriptionFormatter descriptionFormatter;

	public SimpleItemPayingRule(String skuCode, BigDecimal costOfOneAItem) {
		this(skuCode, costOfOneAItem, new StandartDescriptionFormatter(skuCode));
	}

	public SimpleItemPayingRule(String skuCode, BigDecimal costOfOneAItem,
			DescriptionFormatter descriptionFormatter) {
		super(skuCode);
		this.costOfOneAItem = costOfOneAItem;
		this.descriptionFormatter = descriptionFormatter;
	}

	@Override
	public CheckoutEntry calculateCheckoutValue(CheckinEntry checkinEntry) {
		validateProperItem(checkinEntry);

		BigDecimal price = costOfOneAItem.multiply(new BigDecimal(checkinEntry
				.getAmount()));
		String description = descriptionFormatter.formatSimpleItemDescription(
				costOfOneAItem, checkinEntry.getAmount());
		return new CheckoutEntry(description, price);
	}

}
