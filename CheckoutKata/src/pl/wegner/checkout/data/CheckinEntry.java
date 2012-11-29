package pl.wegner.checkout.data;

public class CheckinEntry {

	private String skuCode;
	private int amount;

	/**
	 * Constructor that create entry with single product
	 * 
	 * @param skuCode
	 *            Code of product
	 */
	public CheckinEntry(String skuCode) {
		this(skuCode, 1);
	}

	/**
	 * Constructor that create entry with specified amount of specified product
	 * 
	 * @param skuCode
	 *            Code of product
	 */
	public CheckinEntry(String skuCode, int amount) {
		validateIfAmountIsPositive(amount);

		this.skuCode = skuCode;
		this.amount = amount;
	}

	private void validateIfAmountIsPositive(int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"Parameter specifying amount of products must be grater than zero. It was: "
							+ amount);
		}
	}

	public String getSkuCode() {
		return skuCode;
	}

	public int getAmount() {
		return amount;
	}

}
