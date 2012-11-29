package pl.wegner.checkout.rules;

import java.math.BigDecimal;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;
import pl.wegner.checkout.rules.description.DescriptionFormatter;
import pl.wegner.checkout.rules.description.MultiPricedDescriptionFormatter;

public class MultiPricedPayingRule extends PayingRuleBase {

	private static final CheckoutEntry EMPTY_ENTRY = new CheckoutEntry("",
			new BigDecimal(0));
	private final int amountOfItemsForBonus;
	private final BigDecimal mountOfBonusPerPack;
	private final DescriptionFormatter descriptionFormatter;
	private int currentItemsPending;

	public MultiPricedPayingRule(String skuCode,
			BigDecimal normalPriceForSingleItem, int amountOfItemsForBonus,
			BigDecimal bonusPriceForPack) {
		this(skuCode, normalPriceForSingleItem, amountOfItemsForBonus,
				bonusPriceForPack, new MultiPricedDescriptionFormatter(skuCode,
						amountOfItemsForBonus, bonusPriceForPack));
	}

	public MultiPricedPayingRule(String skuCode,
			BigDecimal normalPriceForSingleItem, int amountOfItemsForBonus,
			BigDecimal bonusPriceForPack,
			DescriptionFormatter descriptionFormatter) {
		super(skuCode);
		this.amountOfItemsForBonus = amountOfItemsForBonus;
		this.descriptionFormatter = descriptionFormatter;
		this.mountOfBonusPerPack = calculateAmountOfBonusPerPack(
				normalPriceForSingleItem, bonusPriceForPack);
	}

	private BigDecimal calculateAmountOfBonusPerPack(BigDecimal normalPrice,
			BigDecimal bonusPrice) {
		return bonusPrice.subtract(normalPrice.multiply(new BigDecimal(
				amountOfItemsForBonus)));
	}

	@Override
	public CheckoutEntry calculateCheckoutValue(CheckinEntry checkinEntry) {
		validateProperItem(checkinEntry);

		int numberOfBonuses = caluclateNumberOfBonuses(checkinEntry);
		if (numberOfBonuses > 0) {
			updateNumberOfItemsPendingForBonus();

			return createCheckoutEntry(numberOfBonuses);
		} else {
			return EMPTY_ENTRY;
		}
	}

	private CheckoutEntry createCheckoutEntry(int numberOfBonuses) {
		BigDecimal summaryAmountOfBonus = calculateSummaryAmountOfBonus(numberOfBonuses);
		String description = descriptionFormatter.formatSimpleItemDescription(
				summaryAmountOfBonus, numberOfBonuses);
		return new CheckoutEntry(description, summaryAmountOfBonus);
	}

	private BigDecimal calculateSummaryAmountOfBonus(int numberOfBonuses) {
		return mountOfBonusPerPack.multiply(new BigDecimal(numberOfBonuses));
	}

	private void updateNumberOfItemsPendingForBonus() {
		currentItemsPending = currentItemsPending % amountOfItemsForBonus;
	}

	private int caluclateNumberOfBonuses(CheckinEntry checkinEntry) {
		currentItemsPending += checkinEntry.getAmount();
		int numberOfBonuses = currentItemsPending / amountOfItemsForBonus;
		return numberOfBonuses;
	}

}
