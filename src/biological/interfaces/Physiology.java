package biological.interfaces;

import java.util.Map;

/**
 * Comprehensive physiology interface for all cell types
 */
public interface Physiology {
    // Basic properties
    double getOptimalTemperature();
    double getOptimalpH();
    double getOptimalSalinity();
    double getMaxGrowthRate();
    
    // Metabolic capabilities
    Map<String, Double> getNutrientRequirements();
    Map<String, Double> getWasteProductionRates();
    boolean canUtilizeNutrient(String nutrient);
    boolean canTolerateStress(String stressType);
    
    // Environmental responses
    double calculateEnvironmentalEffect(double temperature, double pH, double salinity, 
                                      double oxygen, double light);
    
    // Energy metabolism
    String getPrimaryEnergySource();
    double getEnergyProductionRate();
    
    // Stress responses
    double getStressTolerance(String stressor);
    boolean canFormSpores();
}
