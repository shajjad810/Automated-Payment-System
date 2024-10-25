import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class representing the checkout system in the Wolfville store.
 */
public class CCheckoutSystem {
    private final DecimalFormat currencyFormat = new DecimalFormat("$#.00");
    private final Map<Integer, CProduct> products;

    /**
     * Constructor for CCheckoutSystem.
     *
     * @param products The map of available products
     */
    public CCheckoutSystem(Map<Integer, CProduct> products) {
        this.products = products;
    }

    /**
     * Initiates the checkout process.
     */
    public void checkout() {
        Scanner scanner = new Scanner(System.in);
    
        // Display available products to the user
        displayAvailableProducts();
    
        while (true) {
            List<CProduct> purchasedProducts = new ArrayList<>();
            double subtotal = 0.0;
    
            try {
                // Prompt the user to select items for purchase
                System.out.println("Select items to add to the cart (enter serial number, -1 to finish): ");
    
                // Flag to check if at least one item is added to the cart
                boolean hasItemsInCart = false;
    
                while (true) {
                    try {
                        // Get the serial number of the selected item
                        int serialNumber = scanner.nextInt();
    
                        // Check if the user wants to finish the selection
                        if (serialNumber == -1) {
                            break;
                        }
    
                        // Retrieve the product using the serial number
                        CProduct product = products.get(serialNumber);
    
                        // Check if the product exists
                        if (product != null) {
                            // Add the selected product to the cart
                            purchasedProducts.add(product);
                            // Update the subtotal
                            subtotal += product.getPrice();
                            System.out.println("Added: " + product.getName());
                            hasItemsInCart = true; // Set the flag to true
                        } else {
                            System.out.println("Invalid serial number.");
                        }
                    } catch (InputMismatchException e) {
                        // Handle invalid input (non-integer) and consume the invalid input
                        System.out.println("Invalid input. Please enter a valid serial number.");
                        scanner.next(); // Consume the invalid input
                    }
                }
    
                // Check if at least one item is added to the cart
                if (!hasItemsInCart) {
                    System.out.println("Your cart is empty. Please add at least one item to the cart.");
                    continue; // Continue to the next iteration of the loop
                }
    
                // Calculate tax and total
                double tax = subtotal * 0.15;
                double total = subtotal + tax;
    
                // Display order details to the user
                displayOrderDetails(subtotal, tax, total);
    
                // Check if the user has a loyalty card
                boolean hasLoyaltyCard = handleLoyaltyCard(scanner);
    
                // Process payment for the purchase
                processPayment(scanner, new CPaymentMethod(), hasLoyaltyCard);
    
                // Generate and display the receipt
                generateReceipt(purchasedProducts, subtotal, tax, total);
    
                // Exit the loop after completing the checkout
                break;
    
            } catch (Exception e) {
                // Handle general exceptions and display an error message
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    
        // Close the scanner outside the loop to prevent resource leaks
        scanner.close();
    }
    


    private void displayAvailableProducts() {
        System.out.println("Available Products:");
        for (Map.Entry<Integer, CProduct> entry : products.entrySet()) {
            System.out.println("Serial Number: " + entry.getKey() + ", Name: " + entry.getValue().getName()
                    + ", Price: " + currencyFormat.format(entry.getValue().getPrice()));
        }
    }

    private void displayOrderDetails(double subtotal, double tax, double total) {
        System.out.println("------ YOUR ORDER ------");
        System.out.println("Subtotal: " + currencyFormat.format(subtotal));
        System.out.println("Tax: " + currencyFormat.format(tax));
        System.out.println("Total: " + currencyFormat.format(total));
    }

    private void processPayment(Scanner scanner, CPaymentMethod paymentMethod, boolean hasLoyaltyCard) {
        while (true) {
            System.out.print("Enter your payment method (Debit, Credit, Cash" + (hasLoyaltyCard ? ", Loyalty Card" : "") + "): ");
            String chosenPaymentMethod = scanner.next();

            if (chosenPaymentMethod.equalsIgnoreCase("Loyalty Card") && !hasLoyaltyCard) {
                System.out.println("Loyalty card not present or not verified. Please choose another payment method.");
                continue;
            }

            int paymentResult = paymentMethod.processPayment(chosenPaymentMethod);

            if (paymentResult == CPaymentMethod.PAYMENT_SUCCESS) {
                System.out.println("Payment successful using " + chosenPaymentMethod + ".");
                break;
            } else {
                System.out.println("Payment failed or invalid payment method. Please try again.");
            }
        }
    }

    private void generateReceipt(List<CProduct> purchasedProducts, double subtotal, double tax, double total) {
        System.out.println("------ RECEIPT ------");
        for (CProduct product : purchasedProducts) {
            System.out.println(product.getName() + ": " + currencyFormat.format(product.getPrice()));
        }
        System.out.println("Subtotal: " + currencyFormat.format(subtotal));
        System.out.println("Tax: " + currencyFormat.format(tax));
        System.out.println("Total: " + currencyFormat.format(total));
        System.out.println("Thank you for shopping with us!");
    }

    private boolean handleLoyaltyCard(Scanner scanner) {
        System.out.print("Do you have a loyalty card? (yes/no): ");
        String response = scanner.next();

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Enter your loyalty card number: ");
            String cardNumber = scanner.next();

            System.out.print("Enter your unique code: ");
            int uniqueCode = scanner.nextInt();

            if (CLoyaltyCardDetails.isValidMember(cardNumber, uniqueCode)) {
                System.out.println("Loyalty card verified. Points added to your card.");
                return true;
            } else {
                System.out.println("Invalid loyalty card details.");
            }
        }
        return false;
    }
}
