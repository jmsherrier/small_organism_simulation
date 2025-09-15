package biological.cells;

import biological.components.Cytoplasm;
import biological.components.PlasmaMembrane;
import biological.interfaces.GenomeProperties;
import biological.interfaces.Physiology;
import biological.properties.RespirationProperties;
import java.util.*;

/**
 * Heterotrophic bacterial cell implementation
 */
public class HeterotrophicBacterium extends Cell {
    private final RespirationProperties respiration;
    
    public HeterotrophicBacterium(String strain, double volumeMicron3, double dryFraction,
                                Cytoplasm cytoplasm, GenomeProperties genomeProperties,
                                Physiology physiology, PlasmaMembrane membrane,
                                RespirationProperties respiration) {
        super(strain, volumeMicron3, dryFraction, cytoplasm, genomeProperties, physiology, membrane);
        this.respiration = respiration;
    }
    
    @Override
    public double getGrowthRate() {
        return physiology.getMaxGrowthRate() * respiration.getRespirationEfficiency();
    }
    
    @Override
    public Map<String, Double> getNutrientUptakeRates() {
        Map<String, Double> rates = new HashMap<>();
        Map<String, Double> requirements = physiology.getNutrientRequirements();
        
        for (String nutrient : requirements.keySet()) {
            if (physiology.canUtilizeNutrient(nutrient)) {
                double requirement = requirements.get(nutrient);
                double uptake = requirement * membrane.getSurfaceArea() * 0.0001;
                rates.put(nutrient, uptake);
            }
        }
        return rates;
    }
    
    public double getRespirationRate() {
        return respiration.calculateRespirationRate();
    }
    
    public boolean canRespire() {
        return respiration.canPerformAerobicRespiration();
    }
}
