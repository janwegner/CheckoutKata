package pl.wegner.checkout.data;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import pl.wegner.checkout.data.CheckinEntry;

public class CheckinEntryTest {

	@Test
	public void shouldReturnGivenDescription() {
		String skuCode = "A";

		CheckinEntry entry = new CheckinEntry(skuCode);

		assertThat(entry.getSkuCode(), equalTo(skuCode));
	}

	@Test
	public void shouldReturnGivenAmount() {
		int amount = 2;

		CheckinEntry entry = new CheckinEntry("A", amount);

		assertThat(entry.getAmount(), equalTo(amount));
	}

	@Test
	public void shouldReturnOneItemIfNotSpecified() {
		CheckinEntry entry = new CheckinEntry("A");

		assertThat(entry.getAmount(), equalTo(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownExceptionIfNotPositiveAmountSpecified() {
		new CheckinEntry("A", 0);
	}
}
