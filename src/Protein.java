/**
 * Represents a protein produced from a gene, with basic activity simulation.
 * 
 * Assumptions:
 * - Activity score is an arbitrary value between 0.0 and 1.0, representing relative effectiveness.
 * - Proteins are localized either in the cytoplasm or membrane.
 * - Catalytic activity and binding effects are simplified and proportional to activity scores.
 * - This class abstracts real biochemical interactions into a computationally manageable model.
 */
public class Protein {
    private final String name;
    private final String function;
    private final double activityScore; // 0.0 to 1.0
    private final String location; // e.g., "cytoplasm", "membrane"

    /**
     * Constructs a protein from gene information.
     * @param name the name of the protein (gene name)
     * @param function the functional description
     * @param location the cellular location ("cytoplasm" or "membrane")
     */
    public Protein(String name, String function, String location) {
        this.name = name != null ? name : "unknown";
        this.function = function != null ? function : "unknown";
        this.location = location != null ? location : "cytoplasm";
        this.activityScore = Math.random(); // random activity for simplicity
    }

    /**
     * Simulate catalytic activity.
     * @param substrateAmount the amount of substrate
     * @return the produced effect proportional to activity score
     */
    public double catalyzeReaction(double substrateAmount) {
        return substrateAmount * activityScore;
    }

    /**
     * Simulate protein-protein binding effect.
     * @param partner the other protein
     * @return a simple synergy measure
     */
    public double bind(Protein partner) {
        return this.activityScore * partner.activityScore;
    }

    // Getters
    public String getName() { return name; }
    public String getFunction() { return function; }
    public double getActivityScore() { return activityScore; }
    public String getLocation() { return location; }
}
