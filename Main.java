import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * Main class to run the Wolfville store application.
 */
public class Main {
   
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome to the Wolfville store");

    int choice = -1;
    boolean displayMenu = true;

    while (choice < 1 || choice > 3) {
        try {
            if (displayMenu) {
                System.out.println("Please select between our 3 options");
                System.out.println("1 -- to buy and checkout");
                System.out.println("2 -- to return");
                System.out.println("3 -- to join our special program");
            }

            System.out.print("Enter your choice (1, 2, or 3): ");
            choice = input.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please enter a valid choice (1, 2, or 3).");
                displayMenu = false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid choice (1, 2, or 3).");
            input.next(); // Consume the invalid input
            displayMenu = false;
        }
    }

    Map<Integer, CProduct> products = CProduct.getAllCProducts();
    CLoyaltyCardDetails loyaltyCardDetails = new CLoyaltyCardDetails();

    switch (choice) {
        case 1:
            CCheckoutSystem checkoutSystem = new CCheckoutSystem(products);
            checkoutSystem.checkout();
            break;
        case 2:
            processReturns(input, products);
            break;
        case 3:
            joinLoyaltyProgram(input, loyaltyCardDetails);
            break;
    }

    // Close the scanner to prevent resource leaks
    input.close();
}
    

    /**
     * Process returns for purchased items.
     *
     * @param input    Scanner for user input
     * @param products Map of available products
     */
    private static void processReturns(Scanner input, Map<Integer, CProduct> products) {
        DecimalFormat currencyFormat = new DecimalFormat("$#.00");
        System.out.println("Available Products:");
        for (Map.Entry<Integer, CProduct> entry : products.entrySet()) {
            System.out.println("Serial Number: " + entry.getKey() + ", Name: " +
                    entry.getValue().getName() + ", Price: " +
                    currencyFormat.format(entry.getValue().getPrice()));
        }
        System.out.println("Enter the serial number of the item you want to return (-1 to finish):");
        CReturns returns = new CReturns();
        double totalAmtReturned = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        while (true) {
            try {
                int serialNumber = input.nextInt();
                if (serialNumber == -1) {
                    break;
                }
                double returnedAmount = returns.makeReturn(serialNumber, products);
                totalAmtReturned += returnedAmount;
                System.out.println("Amount returned: $" + decimalFormat.format(returnedAmount));
            } catch (InputMismatchException inputException) {
                System.out.println("Invalid input. Please enter a valid serial number.");
                input.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        System.out.println("Total Amount Returned: $" + decimalFormat.format(totalAmtReturned));
    }

    /**
     * Join the loyalty program by entering a unique card number and code.
     *
     * @param input              Scanner for user input
     * @param loyaltyCardDetails Loyalty card details manager
     */
    private static void joinLoyaltyProgram(Scanner input, CLoyaltyCardDetails loyaltyCardDetails) {
        try {
            System.out.println("Please enter a unique card number (4 digits):");
            int cardNumber = input.nextInt();

            if (String.valueOf(cardNumber).length() != 4) {
                System.out.println("Invalid card number length. Please enter a 4-digit number.");
                return;
            }

            System.out.println("Please enter a unique code (4 digits):");
            int code = input.nextInt();

            if (String.valueOf(code).length() != 4) {
                System.out.println("Invalid code length. Please enter a 4-digit number.");
                return;
            }

            loyaltyCardDetails.addCard(String.valueOf(cardNumber), code);
            loyaltyCardDetails.writeCardDetailsToFile("loyaltyMembers.txt");
            System.out.println("You have successfully joined the loyalty program.");
        } catch (InputMismatchException inputexception) {
            System.out.println("Invalid input. Please enter valid integers.");
            input.next();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
