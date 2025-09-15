package biological.cells;

import biological.components.Cytoplasm;
import biological.components.PlasmaMembrane;
import biological.interfaces.GenomeProperties;
import biological.interfaces.Physiology;
import biological.organelles.Mitochondrion;
import biological.organelles.Nucleus;
import biological.organelles.Organelle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eukaryotic cell with organelles and energy balance implementation
 */
public class EukaryoticCell extends Cell {
    private final List<Organelle> organelles;
    private final Nucleus nucleus;
    
    public EukaryoticCell(String strain, double volumeMicron3, double dryFraction,
                        Cytoplasm cytoplasm, GenomeProperties genomeProperties,
                        Physiology physiology, PlasmaMembrane membrane,
                        Nucleus nucleus, List<Organelle> organelles) {
        super(strain, volumeMicron3, dryFraction, cytoplasm, genomeProperties, physiology, membrane);
        this.nucleus = nucleus;
        this.organelles = organelles;
    }
    
    @Override
    public double getGrowthRate() {
        double baseRate = physiology.getMaxGrowthRate();
        double energyBalance = calculateEnergyBalance();
        double nutrientLimitation = calculateNutrientLimitation();
        double organelleEfficiency = calculateOrganelleEfficiency();
        double environmentalEffect = physiology.calculateEnvironmentalEffect(25.0, 7.2, 0.1, 0.21, 0.0); // Add realistic environmental parameters
        
        return baseRate * energyBalance * nutrientLimitation * organelleEfficiency * environmentalEffect;
    }
    
    @Override
    public Map<String, Double> getNutrientUptakeRates() {
        Map<String, Double> rates = new HashMap<>();
        Map<String, Double> requirements = physiology.getNutrientRequirements();
        double surfaceArea = getMembrane().getSurfaceArea();
        
        for (String nutrient : requirements.keySet()) {
            double requirement = requirements.get(nutrient);
            double uptake = requirement * surfaceArea * 0.0005;
            rates.put(nutrient, uptake);
        }
        return rates;
    }
    
    @Override
    protected Map<String, Double> calculateATPProduction() {
        Map<String, Double> production = new HashMap<>();
        
        // Mitochondrial ATP production
        double mitochondrialATP = calculateMitochondrialATPProduction();
        production.put("mitochondrial_oxidative_phosphorylation", mitochondrialATP);
        
        // Glycolytic ATP production (in cytoplasm)
        double glycolyticATP = calculateGlycolyticATPProduction();
        production.put("glycolysis", glycolyticATP);
        
        return production;
    }
    
    @Override
    protected Map<String, Double> calculateATPConsumption() {
        Map<String, Double> consumption = new HashMap<>();
        
        // Biosynthesis costs
        double biosynthesisATP = calculateBiosynthesisATPRequirement();
        consumption.put("biosynthesis", biosynthesisATP);
        
        // Maintenance costs
        double maintenanceATP = calculateMaintenanceATPRequirement();
        consumption.put("maintenance", maintenanceATP);
        
        // Transport costs
        double transportATP = calculateTransportATPRequirement();
        consumption.put("transport", transportATP);
        
        return consumption;
    }
    
    private double calculateMitochondrialATPProduction() {
        double totalATP = 0.0;
        for (Organelle organelle : organelles) {
            if (organelle instanceof Mitochondrion mitochondrion) {
                // Assume oxygen concentration and nutrients are available
                double atp = mitochondrion.calculateATPProduction(0.21); // 21% O2
                totalATP += atp;
            }
        }
        return totalATP;
    }
    
    private double calculateGlycolyticATPProduction() {
        // Simplified glycolysis model
        double glucoseUptake = getNutrientUptakeRates().getOrDefault("glucose", 0.0);
        return glucoseUptake * 2.0; // 2 ATP per glucose in glycolysis
    }
    
    private double calculateBiosynthesisATPRequirement() {
        // ATP required for biomass synthesis
        double growthRate = physiology.getMaxGrowthRate();
        double dryMass = getDryDaltonsWithGenome() / 6.022e23 * 1e-3; // grams
        
        // Typical eukaryotic cell: ~30 mmol ATP/g dry weight for biosynthesis
        return growthRate * dryMass * 30.0 * 1000; // Convert to µmol/hour
    }
    
    private double calculateMaintenanceATPRequirement() {
        // Maintenance ATP requirements
        double dryMass = getDryDaltonsWithGenome() / 6.022e23 * 1e-3; // grams
        
        // Typical eukaryotic cell: ~5 mmol ATP/g dry weight/hour for maintenance
        return dryMass * 5.0 * 1000; // Convert to µmol/hour
    }
    
    private double calculateTransportATPRequirement() {
        // ATP for active transport processes
        double surfaceArea = getMembrane().getSurfaceArea();
        
        // Estimate based on membrane area and transport activity
        return surfaceArea * 0.001; // µmol ATP/hour per nm²
    }
    
    private double calculateOrganelleEfficiency() {
        double efficiency = 1.0;
        for (Organelle organelle : organelles) {
            efficiency *= organelle.getFunctionalCapacity();
        }
        return Math.min(efficiency, 1.0);
    }
    
    public List<Organelle> getOrganelles() { return organelles; }
    public Nucleus getNucleus() { return nucleus; }
    
    public double getTotalOrganelleVolume() {
        return organelles.stream().mapToDouble(Organelle::getVolumeMicron3).sum();
    }
    
    public double getMitochondrialVolume() {
        return organelles.stream()
            .filter(o -> o instanceof Mitochondrion)
            .mapToDouble(Organelle::getVolumeMicron3)
            .sum();
    }
}
