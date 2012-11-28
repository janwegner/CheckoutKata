package pl.wegner.checkout;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import static org.hamcrest.Matchers.*;

/**
 * @author wegner
 *
 */
public class CheckoutEntryTest {

	@Test
	public void shouldReturnGivenDescription() {
		String description = "description";
		
		CheckoutEntry entry = new CheckoutEntry(description, new BigDecimal( 100.20 ));
		
		assertThat( entry.getDescription(), equalTo(description) );
	}
	
	@Test
	public void shouldReturnGivenPrice() {
		BigDecimal price = new BigDecimal( 100.20 );
		
		CheckoutEntry entry = new CheckoutEntry("description", price);
		
		assertThat( entry.getPrice(), equalTo(price ) );
	}
}
