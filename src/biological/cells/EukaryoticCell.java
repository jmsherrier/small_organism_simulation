package biological.cells;

import biological.components.Cytoplasm;
import biological.components.PlasmaMembrane;
import biological.interfaces.GenomeProperties;
import biological.interfaces.Physiology;
import biological.organelles.Organelle;
import biological.organelles.Nucleus;
import java.util.*;

/**
 * Eukaryotic cell with organelles
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
        this.organelles = new ArrayList<>(organelles);
    }
    
    @Override
    public double getGrowthRate() {
        double baseRate = physiology.getMaxGrowthRate();
        double organelleEffect = calculateOrganelleEfficiency();
        return baseRate * organelleEffect;
    }
    
    @Override
    public Map<String, Double> getNutrientUptakeRates() {
        Map<String, Double> rates = new HashMap<>();
        Map<String, Double> requirements = physiology.getNutrientRequirements();
        
        for (String nutrient : requirements.keySet()) {
            double requirement = requirements.get(nutrient);
            double uptake = requirement * membrane.getSurfaceArea() * 0.0005;
            rates.put(nutrient, uptake);
        }
        return rates;
    }
    
    private double calculateOrganelleEfficiency() {
        double efficiency = 1.0;
        for (Organelle organelle : organelles) {
            efficiency *= organelle.getFunctionalCapacity();
        }
        return Math.min(efficiency, 1.0);
    }
    
    public List<Organelle> getOrganelles() { return Collections.unmodifiableList(organelles); }
    public Nucleus getNucleus() { return nucleus; }
    
    public double getTotalOrganelleVolume() {
        return organelles.stream().mapToDouble(Organelle::getVolumeMicron3).sum();
    }
}
