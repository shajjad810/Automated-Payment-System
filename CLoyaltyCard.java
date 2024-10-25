/**
 * Class representing a loyalty card in the Wolfville store.
 */
public class CLoyaltyCard {
    private int points;

    /**
     * Constructor for CLoyaltyCard. Initializes the loyalty card with zero points.
     */
    public CLoyaltyCard() {
        this.points = 0;
    }

    /**
     * Gets the current points on the loyalty card.
     *
     * @return The current points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points to the loyalty card.
     *
     * @param points The points to add
     */
    public void addPoints(int points) {
        this.points += points;
    }
}
