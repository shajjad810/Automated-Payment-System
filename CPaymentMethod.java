/**
 * Class representing a payment method in the Wolfville store.
 */
public class CPaymentMethod {

    /**
     * Constant representing a successful payment.
     */
    public static final int PAYMENT_SUCCESS = 0;

    /**
     * Process a payment using the specified payment method.
     *
     * @param paymentMethod The payment method to be processed
     * @return An integer representing the payment status (0 for success, others for failure)
     */
    public int processPayment(String paymentMethod) {
        System.out.println("Processing " + paymentMethod + " payment...");
        return PAYMENT_SUCCESS;
    }
}
