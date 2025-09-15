package biological.interfaces;

/**
 * Interface for strain-specific genome properties.
 */
public interface GenomeProperties {
    int getExpectedGeneCount();
    double getGenomeSizeMbp();
    String[] getMissingGenes();
    String[] getUniqueGenes();
    boolean hasGene(String geneName);
    double getGCContent();
    String getGenomeStructure();
    double getOptimalTemperature();
    double getOptimalLight();
}
