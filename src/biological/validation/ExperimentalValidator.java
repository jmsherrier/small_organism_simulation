package biological.validation;

import biological.cells.Cell;
import java.util.HashMap;
import java.util.Map;

/**
 * Validates simulation results against experimental data
 */
public class ExperimentalValidator {
    private final Map<String, ExperimentalData> validationData;
    private final Map<String, Double> proteinFractions;
    
    public ExperimentalValidator() {
        this.validationData = loadStandardValidationData();
        this.proteinFractions = loadProteinFractions();
    }
    
    public ValidationResult validateCell(Cell cell, String strain) {
        ValidationResult result = new ValidationResult();
        ExperimentalData expected = validationData.get(strain);
        
        if (expected != null) {
            validateGrowthRate(cell, expected, result);
            validateMassCalculations(cell, strain, result); // Pass strain for cell-type specific validation
        }
        
        return result;
    }
    
    private void validateGrowthRate(Cell cell, ExperimentalData expected, ValidationResult result) {
        double simulated = cell.getGrowthRate();
        double experimental = expected.getGrowthRate();
        double error = Math.abs(simulated - experimental) / experimental;
        
        result.addMetric("growth_rate", simulated, experimental, error, 0.3);
    }
    
    private void validateMassCalculations(Cell cell, String strain, ValidationResult result) {
        double dryMass = cell.getDryDaltonsWithGenome();
        double wetMass = cell.getWetDaltons();
        double genomeMass = cell.getGenomeMass();
        
        // Calculate EXPECTED values based on biological reality and cell volume
        double expectedWetMass = calculateExpectedWetMass(cell, strain);
        double expectedDryMass = calculateExpectedDryMass(cell, strain, expectedWetMass);
        double expectedGenomeMass = calculateExpectedGenomeMass(cell, strain);
        
        // Validate with reasonable tolerances
        double wetMassError = Math.abs(wetMass - expectedWetMass) / expectedWetMass;
        double dryMassError = Math.abs(dryMass - expectedDryMass) / expectedDryMass;
        double genomeMassError = Math.abs(genomeMass - expectedGenomeMass) / expectedGenomeMass;
        
        result.addMetric("wet_mass", wetMass, expectedWetMass, wetMassError, 0.1);
        result.addMetric("dry_mass", dryMass, expectedDryMass, dryMassError, 0.2);
        result.addMetric("genome_mass", genomeMass, expectedGenomeMass, genomeMassError, 0.1);
    }
    
    private double calculateExpectedWetMass(Cell cell, String strain) {
        double volume = cell.getVolumeMicron3();
        
        // Wet mass = volume * density (biological cells are ~1.1 g/cm³)
        // 1 um³ = 1e-12 cm³, 1 g = 6.022e23 Da
        double densityDaPerUm3 = 1.1 * 6.022e23 * 1e-12; // ~6.624e11 Da/um³
        
        return volume * densityDaPerUm3;
    }
    
    private double calculateExpectedDryMass(Cell cell, String strain, double wetMass) {
        // Dry mass fraction varies by cell type
        double dryFraction;
        if (strain.contains("Yeast")) {
            dryFraction = 0.28; // Yeast: 28% dry mass
        } else if (strain.contains("E. coli")) {
            dryFraction = 0.25; // E. coli: 25% dry mass
        } else if (strain.contains("MED4")) {
            dryFraction = 0.30; // MED4: 30% dry mass (cyanobacteria have more pigments)
        } else {
            dryFraction = 0.25; // Default
        }
        
        return wetMass * dryFraction;
    }
    
    private double calculateExpectedGenomeMass(Cell cell, String strain) {
        int geneCount = cell.getCytoplasm().getNucleoid().getGenes().size();
        
        // Average gene length and mass calculation
        double avgGeneLength;
        if (strain.contains("Yeast")) {
            avgGeneLength = 1400.0; // Yeast genes are longer
        } else {
            avgGeneLength = 1000.0; // Bacterial genes
        }
        
        // Genome mass = gene count * average gene length * mass per bp (650 Da/bp)
        // Including associated proteins and packing
        return geneCount * avgGeneLength * 650.0 * 1.3; // 1.3 factor for associated proteins
    }
    
    // Biological constants
    private Map<String, Double> loadProteinFractions() {
        Map<String, Double> fractions = new HashMap<>();
        fractions.put("MED4", 0.55);
        fractions.put("E. coli", 0.50);
        fractions.put("Yeast", 0.45);
        return fractions;
    }
    
    public double getProteinFraction(String strain) {
        return proteinFractions.getOrDefault(strain, 0.5);
    }
    
    private Map<String, ExperimentalData> loadStandardValidationData() {
        Map<String, ExperimentalData> data = new HashMap<>();
        data.put("MED4", new ExperimentalData(1.8, 0.55));
        data.put("E. coli", new ExperimentalData(2.0, 0.50));
        data.put("Yeast", new ExperimentalData(0.5, 0.45));
        return data;
    }
}
