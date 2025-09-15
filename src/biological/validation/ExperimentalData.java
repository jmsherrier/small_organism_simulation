package biological.validation;

/**
 * Container for experimental validation data
 */
public class ExperimentalData {
    private final double growthRate; // doublings/hour
    private final double proteinFraction;
    
    public ExperimentalData(double growthRate, double proteinFraction) {
        this.growthRate = growthRate;
        this.proteinFraction = proteinFraction;
    }
    
    public double getGrowthRate() { return growthRate; }
    public double getProteinFraction() { return proteinFraction; }
}
