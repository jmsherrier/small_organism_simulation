import java.util.List;

/**
 * Represents the nucleoid of a cell, containing genes and structure info.
 *
 * Assumptions:
 * - Nucleoid contains all genes from the parsed GenBank file
 * - Genome structure is either "circular", "linear", or "unknown"
 * - Genes list is never null (may be empty if parsing fails)
 */
public class Nucleoid {
    private final List<Gene> genes;
    private final String structure; // "circular", "linear", or "unknown"

    public Nucleoid(List<Gene> genes, String structure) {
        this.genes = genes;
        this.structure = structure != null ? structure : "unknown";
    }

    public List<Gene> getGenes() { return genes; }
    public String getStructure() { return structure; }

    /** Returns total genome mass in Daltons */
    public double getGenomeMass() {
        return CellConversion.genomeToDaltons(genes);
    }
}
