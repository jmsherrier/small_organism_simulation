import java.util.List;

/**
 * Represents a Prochlorococcus cell.
 *
 * Assumptions:
 * - Wet volume, dry fraction, cytoplasm, and nucleoid are defined
 * - Strain name is specified
 */
public class Prochlorococcus extends Organism {
    private String strain;

    public Prochlorococcus(String strain, List<Gene> genes, double wetVolumeMicron3, double dryFraction, String genomeStructure) {
        super(wetVolumeMicron3, dryFraction, new Cytoplasm(wetVolumeMicron3, new Nucleoid(genes, genomeStructure)));
        this.strain = strain;
    }

    public String getStrain() { return strain; }

    public double getGenomeMass() { return cytoplasm.getNucleoid().getGenomeMass(); }

    public double getDryDaltonsWithGenome() {
        double cytoplasmDry = CellConversion.volumeToDryDaltons(wetVolumeMicron3, dryFraction);
        return cytoplasmDry + getGenomeMass();
    }

    public double getWetDaltons() {
        return CellConversion.volumeToWetDaltons(wetVolumeMicron3);
    }
}
