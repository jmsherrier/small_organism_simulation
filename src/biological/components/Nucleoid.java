package biological.components;

import biological.interfaces.GenomeProperties;
import java.util.List;

/**
 * Represents the nucleoid of a cell, containing a list of genes and genome structure.
 */
public class Nucleoid {
    private List<Gene> genes;
    private String structure;
    private GenomeProperties genomeProperties;

    public Nucleoid(List<Gene> genes, String structure, GenomeProperties genomeProperties) {
        this.genes = genes != null ? genes : List.of();
        this.structure = structure != null ? structure : "unknown";
        this.genomeProperties = genomeProperties != null ? genomeProperties : new DefaultGenomeProperties();
    }

    public Nucleoid(List<Gene> genes, String structure) {
        this(genes, structure, new DefaultGenomeProperties());
    }

    public List<Gene> getGenes() { return genes; }
    public String getStructure() { return structure; }
    public GenomeProperties getGenomeProperties() { return genomeProperties; }

    public double getGenomeMass() {
        return biological.util.CellConversion.genomeToDaltons(genes);
    }
}

/**
 * Default genome properties implementation
 */
class DefaultGenomeProperties implements biological.interfaces.GenomeProperties {
    @Override public int getExpectedGeneCount() { return 0; }
    @Override public double getGenomeSizeMbp() { return 0; }
    @Override public String[] getMissingGenes() { return new String[0]; }
    @Override public String[] getUniqueGenes() { return new String[0]; }
    @Override public boolean hasGene(String geneName) { return false; }
    @Override public double getGCContent() { return 0; }
    @Override public String getGenomeStructure() { return "unknown"; }
    @Override public double getOptimalTemperature() { return 0; }
    @Override public double getOptimalLight() { return 0; }
}
