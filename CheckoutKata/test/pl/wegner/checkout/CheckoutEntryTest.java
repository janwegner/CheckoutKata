package pl.wegner.checkout;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class CheckoutEntryTest {

	@Test
	public void shouldReturnGivenDescription() {
		BigDecimal price = new BigDecimal( 100.20 );
		String description = "description";
		
		CheckoutEntry entry = new CheckoutEntry(description, price);
		
		assertEquals( description, entry.getDescription() );
	}
}
