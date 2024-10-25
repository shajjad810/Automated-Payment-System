import java.util.Map;

/**
 * Class responsible for processing returns of products.
 */
public class CReturns {

    /**
     * Process a return for a specific product based on its serial number.
     *
     * @param serialNumber Serial number of the product to be returned
     * @param products     Map of available products
     * @return Amount refunded for the returned product
     */
    public double makeReturn(int serialNumber, Map<Integer, CProduct> products) {
        CProduct product = products.get(serialNumber);
        if (product != null) {
            System.out.println("Return processed successfully for " + product.getName());
            return product.getPrice();
        } else {
            System.out.println("Product not found with the given serial number.");
            return 0;
        }
    }
}
