package biological.organelles;

/**
 * Mitochondrion organelle implementation
 */
public class Mitochondrion extends Organelle {
    private final double cristaeDensity;
    
    public Mitochondrion(double volumeMicron3, double cristaeDensity) {
        super("Mitochondrion", volumeMicron3);
        this.cristaeDensity = cristaeDensity;
    }
    
    @Override
    public double getFunctionalCapacity() {
        return volumeMicron3 * cristaeDensity;
    }
    
    public double calculateATPProduction(double oxygenConcentration) {
        return getFunctionalCapacity() * oxygenConcentration * 50.0;
    }
    
    public double getCristaeDensity() { return cristaeDensity; }
}
