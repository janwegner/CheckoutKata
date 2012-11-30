package pl.wegner.checkout.rules;

import pl.wegner.checkout.data.CheckinEntry;
import pl.wegner.checkout.data.CheckoutEntry;

/**
 * 
 * @author Jan Wegner (jan.s.wegner[at]gmail.com)
 *
 */
public interface PayingRule {

	/**
	 * Calculate value of entry
	 * 
	 * @param checkinEntry
	 *            Checkin entry to be calculated
	 * @return checkoutEntry with result of calculation. Can be priced as zero
	 *         level, if no calculations needed
	 * 
	 * @throws IllegalArgumentException
	 *             thrown when checkinEntry is given for different SKU item as
	 *             rule is
	 */
	CheckoutEntry calculateCheckoutValue(CheckinEntry checkinEntry);

}
