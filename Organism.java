/**
 * Abstract class representing a biological organism.
 *
 * Assumptions:
 * - All organisms have wet volume, dry fraction, and cytoplasm
 * - Subclasses provide specific implementations
 */
public abstract class Organism {
    protected double wetVolumeMicron3;
    protected double dryFraction;
    protected Cytoplasm cytoplasm;

    public Organism(double wetVolumeMicron3, double dryFraction, Cytoplasm cytoplasm) {
        this.wetVolumeMicron3 = wetVolumeMicron3;
        this.dryFraction = dryFraction;
        this.cytoplasm = cytoplasm;
    }

    public Cytoplasm getCytoplasm() { return cytoplasm; }
    public double getWetVolumeMicron3() { return wetVolumeMicron3; }
    public double getDryFraction() { return dryFraction; }
}
