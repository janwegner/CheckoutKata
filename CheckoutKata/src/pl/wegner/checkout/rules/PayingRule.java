package pl.wegner.checkout.rules;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;

public interface PayingRule {

	/**
	 * Calculate value of entry
	 * 
	 * @param checkinEntry
	 *            Checkin entry to be calculated
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 *             thrown when checkinEntry is given for different SKU item as
	 *             rule is
	 */
	CheckoutEntry calculateCheckoutValue(CheckinEntry checkinEntry);

}
