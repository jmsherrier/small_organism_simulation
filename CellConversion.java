import java.util.List;

/**
 * Utility class for converting cell volumes and genome to Daltons.
 *
 * Assumptions:
 * - Average base pair mass: 650 Daltons
 * - 1 µm³ wet volume = 6.022e11 Daltons
 * - Dry mass calculations include genome mass if specified
 */
public class CellConversion {
    private static final double BASE_PAIR_MASS_DA = 650;
    private static final double VOLUME_TO_DALTONS = 6.022e11;

    public static double genomeToDaltons(List<Gene> genes) {
        double total = 0;
        for (Gene gene : genes) total += gene.getLength() * BASE_PAIR_MASS_DA;
        return total;
    }

    public static double volumeToWetDaltons(double volumeMicron3) { return volumeMicron3 * VOLUME_TO_DALTONS; }

    public static double volumeToDryDaltons(double volumeMicron3, double dryFraction) {
        return volumeMicron3 * dryFraction * VOLUME_TO_DALTONS;
    }

    public static double volumeToDryDaltonsWithGenome(double volumeMicron3, double dryFraction, List<Gene> genes) {
        return volumeToDryDaltons(volumeMicron3, dryFraction) + genomeToDaltons(genes);
    }
}
