package biological;

import biological.cells.*;
import biological.components.Gene;
import biological.factory.CellFactory;
import biological.interfaces.*;
import biological.util.GenBankParser;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Advanced Cellular Simulation with Strain-Specific Genes
 * Simulates different cell types with their relevant genomic content
 */
public class AdvancedCellSimulation {
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED CELLULAR SIMULATION ===");
        System.out.println("Simulating different cell types with strain-specific genes\n");
        
        try {
            // Download and parse reference genomes for different organisms
            System.out.println("1. DOWNLOADING AND PARSING GENOMIC DATA...");
            
            // Prochlorococcus MED4 genome
            String med4Accession = "BX548174";
            String med4File = "MED4.gb";
            GenBankParser.downloadGenBankFile(med4Accession, med4File);
            List<Gene> med4Genes = GenBankParser.parseGenBankFile(med4File);
            System.out.println("✓ MED4 genes parsed: " + med4Genes.size() + " genes");
            
            // E. coli K-12 genome (example heterotrophic bacteria)
            String ecoliAccession = "U00096";
            String ecoliFile = "ECOLI.gb";
            GenBankParser.downloadGenBankFile(ecoliAccession, ecoliFile);
            List<Gene> ecoliGenes = GenBankParser.parseGenBankFile(ecoliFile);
            System.out.println("✓ E. coli genes parsed: " + ecoliGenes.size() + " genes");
            
            // Yeast genome (S. cerevisiae example)
            String yeastAccession = "BK006946";
            String yeastFile = "YEAST.gb";
            GenBankParser.downloadGenBankFile(yeastAccession, yeastFile);
            List<Gene> yeastGenes = GenBankParser.parseGenBankFile(yeastFile);
            System.out.println("✓ Yeast genes parsed: " + yeastGenes.size() + " genes");
            
            System.out.println("\n2. CREATING CELL TYPES WITH STRAIN-SPECIFIC GENES...");
            
            // Create cells with their respective genomes
            Cell med4Cell = createMED4WithGenes(med4Genes);
            Cell ecoliCell = createEcoliWithGenes(ecoliGenes);
            Cell yeastCell = createYeastWithGenes(yeastGenes);
            
            System.out.println("\n3. SIMULATION RESULTS:\n");
            
            // Simulate and display results for each cell type
            simulateAndDisplay(med4Cell, "Prochlorococcus MED4", 200, 24, 7.2, 0.035);
            simulateAndDisplay(ecoliCell, "E. coli K-12", 0, 37, 7.0, 0.15);
            simulateAndDisplay(yeastCell, "Saccharomyces cerevisiae", 0, 30, 7.2, 0.1);
            
            System.out.println("\n4. SPECIALIZED FUNCTION ANALYSIS:\n");
            
            // Specialized function analysis
            analyzeSpecializedFunctions(med4Cell, "MED4");
            analyzeSpecializedFunctions(ecoliCell, "E. coli");
            analyzeSpecializedFunctions(yeastCell, "Yeast");
            
            System.out.println("\n=== SIMULATION COMPLETED ===");
            
        } catch (IOException e) {
            System.err.println("Error processing genomic data: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Simulation error: " + e.getMessage());
        }
    }
    
    /**
     * Create MED4 cell with MED4-specific genes
     */
    private static Cell createMED4WithGenes(List<Gene> med4Genes) {
        System.out.println("Creating MED4 cell with photosynthetic genes...");
        return CellFactory.createCell("photosynthetic", "MED4", med4Genes, 0.6, 0.3);
    }
    
    /**
     * Create E. coli cell with heterotrophic genes
     */
    private static Cell createEcoliWithGenes(List<Gene> ecoliGenes) {
        System.out.println("Creating E. coli cell with respiratory genes...");
        return CellFactory.createCell("heterotrophic", "E. coli K-12", ecoliGenes, 1.0, 0.25);
    }
    
    /**
     * Create yeast cell with eukaryotic genes
     */
    private static Cell createYeastWithGenes(List<Gene> yeastGenes) {
        System.out.println("Creating yeast cell with eukaryotic genes...");
        return CellFactory.createCell("eukaryotic", "S. cerevisiae", yeastGenes, 10.0, 0.2);
    }
    
    /**
     * Simulate and display results for a cell
     */
    private static void simulateAndDisplay(Cell cell, String name, 
                                         double lightIntensity, double temperature,
                                         double pH, double salinity) {
        System.out.println("--- " + name + " ---");
        System.out.printf("Growth rate: %.2f doublings/day\n", cell.getGrowthRate());
        System.out.printf("Cell volume: %.2f µm³\n", cell.getVolumeMicron3());
        System.out.printf("Dry mass: %.1e Da\n", cell.getDryDaltonsWithGenome());
        System.out.printf("Wet mass: %.1e Da\n", cell.getWetDaltons());
        System.out.printf("Genome mass: %.1e Da\n", cell.getGenomeMass());
        
        // Environment-specific simulations
        double envEffect = cell.getPhysiology().calculateEnvironmentalEffect(
            temperature, pH, salinity, 0.21, lightIntensity);
        System.out.printf("Environmental suitability: %.1f%%\n", envEffect * 100);
        
        // Type-specific simulations
        if (cell instanceof Photosynthetic ps) {
            double photosynthesis = ps.simulatePhotosynthesis(lightIntensity);
            System.out.printf("Photosynthesis rate: %.2f mmol O₂/mg/h\n", photosynthesis);
        }
        
        if (cell instanceof HeterotrophicBacterium hb) {
            System.out.printf("Respiration rate: %.1f µmol ATP/min\n", hb.getRespirationRate());
        }
        
        if (cell instanceof EukaryoticCell ec) {
            System.out.printf("Organelles: %d, Total volume: %.2f µm³\n", 
                ec.getOrganelles().size(), ec.getTotalOrganelleVolume());
        }
        
        // Nutrient uptake
        Map<String, Double> uptake = cell.getNutrientUptakeRates();
        System.out.println("Primary nutrient uptake rates:");
        uptake.entrySet().stream()
            .limit(3)
            .forEach(e -> System.out.printf("  %s: %.3f molecules/s\n", e.getKey(), e.getValue()));
        
        System.out.println();
    }
    
    /**
     * Analyze specialized functions for each cell type
     */
    private static void analyzeSpecializedFunctions(Cell cell, String name) {
        System.out.println("--- " + name + " Specialized Functions ---");
        
        // Genome properties
        System.out.printf("Genome size: %.2f Mbp, GC: %.1f%%\n",
            cell.getGenomeProperties().getGenomeSizeMbp(),
            cell.getGenomeProperties().getGCContent() * 100);
        
        // Physiology properties
        System.out.printf("Optimal: %.1f°C, pH %.1f\n",
            cell.getPhysiology().getOptimalTemperature(),
            cell.getPhysiology().getOptimalpH());
        
        System.out.printf("Energy source: %s, Production: %.1f units\n",
            cell.getPhysiology().getPrimaryEnergySource(),
            cell.getPhysiology().getEnergyProductionRate());
        
        // MED4-specific analysis
        if (cell instanceof MED4Strain med4) {
            System.out.printf("Divinyl chlorophyll ratio: %.2f\n", med4.getDivinylChlorophyllRatio());
            System.out.printf("Can use nitrate: %s\n", med4.canUseNitrate());
            System.out.printf("PSBA copies: %d\n", med4.getPsbACopyNumber());
        }
        
        // Heterotrophic bacteria analysis
        if (cell instanceof HeterotrophicBacterium hb) {
            System.out.printf("Aerobic respiration: %s\n", hb.canRespire());
            System.out.printf("Spore formation: %s\n", 
                hb.getPhysiology().canFormSpores());
        }
        
        // Eukaryotic cell analysis
        if (cell instanceof EukaryoticCell ec) {
            System.out.printf("Organelle functional capacity: %.2f\n",
                ec.getOrganelles().stream()
                    .mapToDouble(o -> o.getFunctionalCapacity())
                    .average()
                    .orElse(0));
        }
        
        System.out.println();
    }
    
    /**
     * Additional method to simulate environmental gradients
     */
    public static void simulateEnvironmentalGradient(Cell cell, String name) {
        System.out.println("=== Environmental Gradient Simulation: " + name + " ===");
        
        double[] temperatures = {20, 25, 30, 35, 40};
        double[] lightLevels = {0, 100, 200, 500, 1000};
        
        System.out.println("Temperature effects (°C):");
        for (double temp : temperatures) {
            double effect = cell.getPhysiology().calculateEnvironmentalEffect(
                temp, 7.0, 0.1, 0.21, 200);
            System.out.printf("  %2.0f°C: %.1f%% growth\n", temp, effect * 100);
        }
        
        if (cell instanceof Photosynthetic photosynthetic) {
            System.out.println("Light response (µE/m²/s):");
            for (double light : lightLevels) {
                double photoRate = photosynthetic.simulatePhotosynthesis(light);
                System.out.printf("  %4.0f µE: %.2f mmol O₂\n", light, photoRate);
            }
        }
        
        System.out.println();
    }
}
