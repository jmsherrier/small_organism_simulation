import java.io.IOException;
import java.util.List;

/**
 * Main demo program.
 *
 * Steps:
 * 1. Download MED4 GenBank genome
 * 2. Detect genome structure
 * 3. Parse genes
 * 4. Create Prochlorococcus object
 * 5. Output genome and mass info
 */
public class Main {
    public static void main(String[] args) {
        String accession = "BX548174";
        String genBankFile = "MED4.gb";

        try {
            System.out.println("=== STARTING GENBANK PARSER DEMO ===");

            // Step 1: Download
            System.out.println("Step 1: Downloading GenBank file...");
            GenBankParser.downloadGenBankFile(accession, genBankFile);
            System.out.println("Downloaded GenBank file for " + accession);

            // Step 2: Structure
            System.out.println("\nStep 2: Detecting genome structure...");
            String structure = GenBankParser.getGenomeStructure(genBankFile);
            System.out.println("Genome structure: " + structure);

            // Step 3: Parse genes
            System.out.println("\nStep 3: Parsing GenBank file...");
            List<Gene> med4Genes = GenBankParser.parseGenBankFile(genBankFile);
            System.out.println("Parsed " + med4Genes.size() + " genes");

            if (med4Genes.isEmpty()) {
                System.out.println("WARNING: No genes were parsed!");
                return;
            }

            // Step 4: Create cell
            System.out.println("\nStep 4: Creating Prochlorococcus object...");
            Prochlorococcus med4 = new Prochlorococcus("MED4", med4Genes, 0.6, 0.3, structure);

            // Step 5: Output
            System.out.println("\nStep 5: Outputting results...");
            System.out.println("Strain: " + med4.getStrain());
            System.out.println("Wet Volume (µm³): " + med4.getWetVolumeMicron3());
            System.out.println("Dry Fraction: " + med4.getDryFraction());
            System.out.println("Genome Structure: " + med4.getCytoplasm().getNucleoid().getStructure());
            System.out.println("Number of genes: " + med4.getCytoplasm().getNucleoid().getGenes().size());
            System.out.println("Genome Mass (Daltons): " + med4.getGenomeMass());
            System.out.println("Dry Daltons with Genome: " + med4.getDryDaltonsWithGenome());
            System.out.println("Wet Daltons: " + med4.getWetDaltons());

            System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");

        } catch (IOException e) {
            System.err.println("Error downloading or parsing GenBank file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
