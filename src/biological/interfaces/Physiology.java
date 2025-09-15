package biological.interfaces;

import java.util.Map;

public interface Physiology {
    double getOptimalTemperature();
    double getOptimalpH();
    double getOptimalSalinity();
    double getMaxGrowthRate();
    Map<String, Double> getNutrientRequirements();
    Map<String, Double> getWasteProductionRates();
    boolean canUtilizeNutrient(String nutrient);
    boolean canTolerateStress(String stressType);
    double calculateEnvironmentalEffect(double temperature, double pH, double salinity, double oxygen, double light);
    String getPrimaryEnergySource();
    double getEnergyProductionRate();
    double getStressTolerance(String stressor);
    boolean canFormSpores();
}
