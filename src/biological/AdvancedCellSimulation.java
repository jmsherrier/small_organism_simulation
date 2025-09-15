package biological;

import biological.cells.*;
import biological.components.Gene;
import biological.factory.CellFactory;
import biological.sensitivity.SensitivityAnalyzer;
import biological.sensitivity.SensitivityResult;
import biological.util.GenBankParser;
import biological.util.YeastGeneLoader;
import biological.validation.ExperimentalValidator;
import biological.validation.ValidationResult;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Advanced Cellular Simulation with validation and sensitivity analysis
 */
public class AdvancedCellSimulation {
    
    private static final String DATA_DIR = "genbank_data";
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED CELLULAR SIMULATION ===");
        System.out.println("With scientific validation and sensitivity analysis");
        System.out.println();
        
        try {
            java.nio.file.Files.createDirectories(Paths.get(DATA_DIR));
            
            System.out.println("1. DOWNLOADING AND PARSING GENOMIC DATA...");
            
            // MED4
            String med4Accession = "BX548174";
            String med4File = DATA_DIR + "/MED4.gb";
            GenBankParser.downloadGenBankFile(med4Accession, med4File);
            List<Gene> med4Genes = GenBankParser.parseGenBankFile(med4File);
            System.out.println("MED4 genes parsed: " + med4Genes.size() + " genes");
            
            // E. coli
            String ecoliAccession = "U00096";
            String ecoliFile = DATA_DIR + "/ECOLI.gb";
            GenBankParser.downloadGenBankFile(ecoliAccession, ecoliFile);
            List<Gene> ecoliGenes = GenBankParser.parseGenBankFile(ecoliFile);
            System.out.println("E. coli genes parsed: " + ecoliGenes.size() + " genes");
            
            // Yeast - use realistic gene generator instead of broken GenBank parsing
            System.out.println("Generating realistic yeast genes...");
            List<Gene> yeastGenes = YeastGeneLoader.loadYeastGenes();
            System.out.println("Yeast genes generated: " + yeastGenes.size() + " genes");
            
            System.out.println();
            System.out.println("2. CREATING CELL TYPES...");
            
            Cell med4Cell = createMED4WithGenes(med4Genes);
            Cell ecoliCell = createEcoliWithGenes(ecoliGenes);
            Cell yeastCell = createYeastWithGenes(yeastGenes);
            
            System.out.println();
            System.out.println("3. SIMULATION RESULTS:");
            System.out.println();
            
            simulateAndDisplay(med4Cell, "Prochlorococcus MED4");
            simulateAndDisplay(ecoliCell, "E. coli K-12");
            simulateAndDisplay(yeastCell, "Saccharomyces cerevisiae");
            
            System.out.println();
            System.out.println("4. SCIENTIFIC VALIDATION:");
            System.out.println();
            
            ExperimentalValidator validator = new ExperimentalValidator();
            SensitivityAnalyzer sensitivityAnalyzer = new SensitivityAnalyzer();
            
            ValidationResult med4Validation = validator.validateCell(med4Cell, "MED4");
            ValidationResult ecoliValidation = validator.validateCell(ecoliCell, "E. coli");
            ValidationResult yeastValidation = validator.validateCell(yeastCell, "Yeast");
            
            med4Validation.printResults();
            System.out.println();
            ecoliValidation.printResults();
            System.out.println();
            yeastValidation.printResults();
            
            System.out.println();
            System.out.println("5. SENSITIVITY ANALYSIS:");
            System.out.println();
            
            List<String> testParams = Arrays.asList("max_growth_rate", "dry_fraction");
            SensitivityResult sensitivity = sensitivityAnalyzer.analyzeCell(med4Cell, testParams);
            sensitivity.printResults();
            
            System.out.println();
            System.out.println("=== SIMULATION COMPLETED SUCCESSFULLY ===");
            
        } catch (IOException e) {
            System.err.println("Error processing genomic data: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Simulation error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Cell createMED4WithGenes(List<Gene> med4Genes) {
        System.out.println("Creating MED4 cell with photosynthetic genes...");
        return CellFactory.createCell("photosynthetic", "MED4", med4Genes, 0.6, 0.3);
    }
    
    private static Cell createEcoliWithGenes(List<Gene> ecoliGenes) {
        System.out.println("Creating E. coli cell with respiratory genes...");
        return CellFactory.createCell("heterotrophic", "E. coli", ecoliGenes, 1.0, 0.25);
    }
    
    private static Cell createYeastWithGenes(List<Gene> yeastGenes) {
        System.out.println("Creating yeast cell with eukaryotic genes...");
        return CellFactory.createCell("eukaryotic", "Yeast", yeastGenes, 10.0, 0.2);
    }
    
    private static void simulateAndDisplay(Cell cell, String name) {
        System.out.println("--- " + name + " ---");
        System.out.printf("Growth rate: %.2f doublings/hour%n", cell.getGrowthRate());
        System.out.printf("Cell volume: %.2f um3%n", cell.getVolumeMicron3());
        System.out.printf("Dry mass: %.1e Da%n", cell.getDryDaltonsWithGenome());
        System.out.printf("Wet mass: %.1e Da%n", cell.getWetDaltons());
        System.out.printf("Genome mass: %.1e Da%n", cell.getGenomeMass());
        System.out.println();
    }
}
