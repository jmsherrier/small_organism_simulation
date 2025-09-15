import java.io.IOException;
import java.util.List;

/**
 * Demonstrates:
 * 1. Downloading MED4 GenBank genome
 * 2. Parsing gene information and genome structure
 * 3. Creating a Prochlorococcus cell
 * 4. Calculating genome mass, dry mass, wet mass
 * 5. Simulating protein activity in cytoplasm
 */
public class Main {
    public static void main(String[] args) {
        String accession = "BX548174";
        String genBankFile = "MED4.gb";

        try {
            System.out.println("=== STARTING GENBANK PARSER DEMO ===");

            GenBankParser.downloadGenBankFile(accession, genBankFile);
            String structure = GenBankParser.getGenomeStructure(genBankFile);
            List<Gene> med4Genes = GenBankParser.parseGenBankFile(genBankFile);

            Prochlorococcus med4 = new Prochlorococcus("MED4", med4Genes, 0.6, 0.3, structure);

            System.out.println("Strain: " + med4.getStrain());
            System.out.println("Genome Structure: " + structure);
            System.out.println("Number of genes: " + med4.getCytoplasm().getNucleoid().getGenes().size());

            System.out.println("\nSimulating protein activity...");
            med4.simulateCellActivity();

        } catch (IOException e) {
        }
    }
}
