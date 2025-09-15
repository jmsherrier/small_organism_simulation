import java.util.*;

/**
 * Represents the cytoplasm of a cell, including volume, nucleoid, and expressed proteins.
 * 
 * Assumptions:
 * - Cytoplasm volume is in cubic micrometers.
 * - Nucleoid is always present (may contain empty gene list).
 * - All genes in nucleoid are expressed into proteins in the cytoplasm.
 * - Protein activity is simulated using simplified catalytic or binding effects.
 */
public class Cytoplasm {
    private final double volumeMicron3;
    private final Nucleoid nucleoid;
    private final List<Protein> proteins;

    public Cytoplasm(double volumeMicron3, Nucleoid nucleoid) {
        this.volumeMicron3 = volumeMicron3;
        this.nucleoid = nucleoid;
        this.proteins = new ArrayList<>();
        expressAllGenes();
    }

    private void expressAllGenes() {
        for (Gene g : nucleoid.getGenes()) {
            proteins.add(g.expressProtein("cytoplasm"));
        }
    }

    /**
     * Simulate a single step of protein activity in the cytoplasm.
     * Outputs the total cumulative effect.
     */
    public void simulateActivity() {
        double totalEffect = 0;
        for (Protein p : proteins) {
            totalEffect += p.catalyzeReaction(1.0); // substrate = 1.0
        }
        System.out.println("Total cytoplasmic protein activity: " + totalEffect);
    }

    // Getters
    public Nucleoid getNucleoid() { return nucleoid; }
    public List<Protein> getProteins() { return Collections.unmodifiableList(proteins); }
    public double getVolume() { return volumeMicron3; }
}
