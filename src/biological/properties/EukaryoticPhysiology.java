package biological.properties;

import biological.interfaces.Physiology;
import java.util.HashMap;
import java.util.Map;

/**
 * Physiology implementation for eukaryotic cells with energy metabolism
 */
public class EukaryoticPhysiology implements Physiology {
    private final Map<String, Double> nutrientQuotas;
    private final Map<String, Double> energyYields;
    
    public EukaryoticPhysiology() {
        nutrientQuotas = new HashMap<>();
        nutrientQuotas.put("glucose", 0.15);
        nutrientQuotas.put("oxygen", 0.02);
        nutrientQuotas.put("nitrogen", 0.1);
        nutrientQuotas.put("phosphorus", 0.02);
        nutrientQuotas.put("carbon", 0.45);
        
        energyYields = new HashMap<>();
        energyYields.put("glucose_glycolysis", 2.0); // ATP per glucose
        energyYields.put("glucose_oxidative", 36.0); // ATP per glucose
        energyYields.put("fatty_acid_oxidation", 120.0); // ATP per palmitate
    }
    
    @Override
    public double getOptimalTemperature() { return 25.0; }
    
    @Override
    public double getOptimalpH() { return 7.2; }
    
    @Override
    public double getOptimalSalinity() { return 0.1; }
    
    private double maxGrowthRate = 0.5;
    public void setMaxGrowthRate(double maxGrowthRate) {
        this.maxGrowthRate = maxGrowthRate;
    }

    @Override
    public double getMaxGrowthRate() {
        return maxGrowthRate;
    }
    
    @Override
    public Map<String, Double> getNutrientRequirements() { 
        return new HashMap<>(nutrientQuotas);
    }
    
    @Override
    public Map<String, Double> getWasteProductionRates() {
        Map<String, Double> waste = new HashMap<>();
        waste.put("CO2", 0.15);
        waste.put("urea", 0.03);
        waste.put("lactate", 0.01);
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
        double oxygenEffect = oxygen / (oxygen + 0.01); // O2 saturation
        
        return Math.max(0, Math.min(1, tempEffect * pHEffect * salinityEffect * oxygenEffect));
    }
    
    @Override
    public String getPrimaryEnergySource() { return "glucose"; }
    
    @Override
    public double getEnergyProductionRate() { 
        // Maximum theoretical energy production rate
        return 80.0; // µmol ATP/hour
    }
    
    @Override
    public double getStressTolerance(String stressor) { 
        return switch (stressor) {
            case "heat" -> 0.6;
            case "acid" -> 0.4;
            case "osmotic" -> 0.7;
            case "oxidative" -> 0.5;
            default -> 0.5;
        };
    }
    
    @Override
    public boolean canFormSpores() { return false; }
    
    public double getEnergyYield(String substrate, String pathway) {
        String key = substrate + "_" + pathway;
        return energyYields.getOrDefault(key, 0.0);
    }
    
    public Map<String, Double> getEnergyYields() {
        return new HashMap<>(energyYields);
    }
}
