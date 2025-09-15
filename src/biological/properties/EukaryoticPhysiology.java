package biological.properties;

import biological.interfaces.Physiology;
import java.util.*;

/**
 * Physiology implementation for eukaryotic cells
 */
public class EukaryoticPhysiology implements Physiology {
    private final Map<String, Double> nutrientQuotas;
    
    public EukaryoticPhysiology() {
        nutrientQuotas = new HashMap<>();
        nutrientQuotas.put("carbon", 0.45);
        nutrientQuotas.put("nitrogen", 0.1);
        nutrientQuotas.put("phosphorus", 0.02);
        nutrientQuotas.put("oxygen", 0.0);
    }
    
    @Override
    public double getOptimalTemperature() { return 25.0; }
    @Override
    public double getOptimalpH() { return 7.2; }
    @Override
    public double getOptimalSalinity() { return 0.1; }
    @Override
    public double getMaxGrowthRate() { return 0.5; }
    
    @Override
    public Map<String, Double> getNutrientRequirements() { 
        return Collections.unmodifiableMap(nutrientQuotas); 
    }
    
    @Override
    public Map<String, Double> getWasteProductionRates() {
        Map<String, Double> waste = new HashMap<>();
        waste.put("CO2", 0.15);
        waste.put("urea", 0.03);
        return waste;
    }
    
    @Override
    public boolean canUtilizeNutrient(String nutrient) {
        return nutrientQuotas.containsKey(nutrient);
    }
    
    @Override
    public boolean canTolerateStress(String stressType) {
        return stressType.equals("osmotic") || stressType.equals("oxidative");
    }
    
    @Override
    public double calculateEnvironmentalEffect(double temperature, double pH, double salinity, 
                                             double oxygen, double light) {
        double tempEffect = 1.0 - Math.abs(temperature - 25.0) / 15.0;
        double pHEffect = 1.0 - Math.abs(pH - 7.2) / 2.0;
        double salinityEffect = 1.0 - Math.abs(salinity - 0.1) / 0.05;
        return Math.max(0, Math.min(1, tempEffect * pHEffect * salinityEffect));
    }
    
    @Override
    public String getPrimaryEnergySource() { return "glucose"; }
    @Override
    public double getEnergyProductionRate() { return 80.0; }
    @Override
    public double getStressTolerance(String stressor) { return 0.6; }
    @Override
    public boolean canFormSpores() { return false; }
}
