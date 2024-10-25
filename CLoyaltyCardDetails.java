import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing loyalty card details in the Wolfville store.
 */
public class CLoyaltyCardDetails {

    private static Map<String, Integer> cardDetails;

    /**
     * Constructor for CLoyaltyCardDetails. Initializes cardDetails and loads details from a file.
     */
    public CLoyaltyCardDetails() {
        CLoyaltyCardDetails.cardDetails = new HashMap<>();
        loadCardDetailsFromFile("loyaltyMembers.txt");
    }

    /**
     * Adds a new loyalty card with the given card number and unique code.
     *
     * @param cardNumber The card number
     * @param uniqueCode The unique code associated with the card
     */
    public void addCard(String cardNumber, int uniqueCode) {
        cardDetails.put(cardNumber, uniqueCode);
    }

    /**
     * Loads loyalty card details from a file and populates the cardDetails map.
     *
     * @param fileName The name of the file containing loyalty card details
     */
    private void loadCardDetailsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    cardDetails.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading loyalty card details file: " + e.getMessage());
        }
    }

    /**
     * Writes loyalty card details to a file.
     *
     * @param fileName The name of the file to write loyalty card details
     */
    public void writeCardDetailsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Map.Entry<String, Integer> entry : cardDetails.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing loyalty card details to file: " + e.getMessage());
        }
    }

    /**
     * Checks if a loyalty card with the given card number and code is a valid member.
     *
     * @param cardNumber The card number
     * @param code       The code associated with the card
     * @return True if the card is a valid member, false otherwise
     */
    public static boolean isValidMember(String cardNumber, int code) {
        Integer storedCode = cardDetails.get(cardNumber);
        return storedCode != null && storedCode == code;
    }
}
