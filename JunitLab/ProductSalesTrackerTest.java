import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductSalesTrackerTest {

    private ProductSalesTracker tracker1;
    private ProductSalesTracker tracker2;

    @BeforeEach
    public void setUp() {
        tracker1 = new ProductSalesTracker(5);
        tracker1.addSale(15.5);
        tracker1.addSale(30.0);
        tracker1.addSale(8.25);

        tracker2 = new ProductSalesTracker(2);
        tracker2.addSale(50.75);
    }

    @AfterEach
    public void tearDown() {
        tracker1 = null;
        tracker2 = null;
    }

    @Test
    public void testAddSale() {
        assertTrue(tracker1.addSale(12.0));
        assertTrue(tracker2.addSale(20.0));
        assertFalse(tracker2.addSale(5.0)); 
    }

    @Test
    public void testTotalSales() {
        // 15.5 + 30.0 + 8.25 = 53.75
        assertEquals(53.75, tracker1.totalSales(), 0.0001);
        assertEquals(50.75, tracker2.totalSales(), 0.0001);
    }

    @Test
    public void testLowestSale() {
        assertEquals(8.25, tracker1.lowestSale(), 0.0001);

        ProductSalesTracker empty = new ProductSalesTracker(3);
        assertEquals(0.0, empty.lowestSale(), 0.0001);
    }

    @Test
    public void testFinalSalesTotal() {
        // tracker1: 15.5 + 30.0 + 8.25 = 53.75, lowest = 8.25, so final = 45.5
        assertEquals(45.5, tracker1.finalSalesTotal(), 0.0001);

        // tracker2 has only one number so result is 0
        assertEquals(0.0, tracker2.finalSalesTotal(), 0.0001);

        tracker2.addSale(20.0); // 50.75 + 20.0 = 70.75, drop 20.0 = 50.75
        assertEquals(50.75, tracker2.finalSalesTotal(), 0.0001);
    }

    @Test
    public void testToString() {
        assertEquals("15.5 30.0 8.25", tracker1.toString());

        ProductSalesTracker empty = new ProductSalesTracker(2);
        assertEquals("", empty.toString());
    }

}
