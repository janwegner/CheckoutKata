package pl.wegner.checkout.rules;

import pl.wegner.checkout.data.CheckinEntry;

/**
 * Abstract class for elements that will be reused in child classes.
 * 
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 * 
 */
public abstract class PayingRuleBase implements PayingRule {

	private String skuCode;

	public PayingRuleBase(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	protected void validateProperItem(CheckinEntry checkinEntry) {
		if (!skuCode.equals(checkinEntry.getSkuCode())) {
			throw new IllegalArgumentException("Item with SKU "
					+ checkinEntry.getSkuCode()
					+ " passed into rule that calculate cost for " + skuCode
					+ ".");
		}
	}
}
