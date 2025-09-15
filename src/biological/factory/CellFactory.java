package biological.factory;

import biological.cells.*;
import biological.components.*;
import biological.organelles.*;
import biological.properties.*;
import biological.util.CellConversion;
import java.util.*;

/**
 * Factory for creating different cell types
 */
public class CellFactory {
    
    public static Cell createCell(String cellType, String strain, List<Gene> genes, 
                                double volume, double dryFraction) {
        switch (cellType.toLowerCase()) {
            case "photosynthetic" -> {
                return createPhotosyntheticCell(strain, genes, volume, dryFraction);
            }
            case "heterotrophic" -> {
                return createHeterotrophicCell(strain, genes, volume, dryFraction);
            }
            case "eukaryotic" -> {
                return createEukaryoticCell(strain, genes, volume, dryFraction);
            }
            default -> throw new IllegalArgumentException("Unknown cell type: " + cellType);
        }
    }
    
    private static Cell createPhotosyntheticCell(String strain, List<Gene> genes, 
                                               double volume, double dryFraction) {
        double cytoplasmVol = volume * 0.85;
        double membraneVol = volume * 0.15;
        double surfaceArea = CellConversion.volumeToSurfaceArea(volume);
        
        Nucleoid nucleoid = new Nucleoid(genes, "circular", new biological.cells.MED4Strain.MED4GenomeProperties());
        PlasmaMembrane membrane = new PlasmaMembrane(membraneVol, surfaceArea);
        Cytoplasm cytoplasm = new Cytoplasm(cytoplasmVol, nucleoid, membrane);
        Thylakoid thylakoid = createThylakoid();
        
        return MED4Strain.createFromGenes(genes, volume, dryFraction);
    }
    
    private static Cell createHeterotrophicCell(String strain, List<Gene> genes,
                                              double volume, double dryFraction) {
        double cytoplasmVol = volume * 0.85;
        double membraneVol = volume * 0.15;
        double surfaceArea = CellConversion.volumeToSurfaceArea(volume);
        
        Nucleoid nucleoid = new Nucleoid(genes, "circular", new BacterialGenomeProperties());
        PlasmaMembrane membrane = new PlasmaMembrane(membraneVol, surfaceArea);
        Cytoplasm cytoplasm = new Cytoplasm(cytoplasmVol, nucleoid, membrane);
        RespirationProperties respiration = new RespirationProperties(true, false, 0.8, "oxygen");
        
        return new HeterotrophicBacterium(strain, volume, dryFraction, cytoplasm,
                                        new BacterialGenomeProperties(), new BacterialPhysiology(),
                                        membrane, respiration);
    }
    
    private static Cell createEukaryoticCell(String strain, List<Gene> genes,
                                           double volume, double dryFraction) {
        double cytoplasmVol = volume * 0.7;
        double membraneVol = volume * 0.1;
        double surfaceArea = CellConversion.volumeToSurfaceArea(volume);
        
        Nucleoid nucleoid = new Nucleoid(genes, "linear", new EukaryoticGenomeProperties());
        PlasmaMembrane membrane = new PlasmaMembrane(membraneVol, surfaceArea);
        Cytoplasm cytoplasm = new Cytoplasm(cytoplasmVol, nucleoid, membrane);
        
        Nucleus nucleus = new Nucleus(volume * 0.1, nucleoid);
        Mitochondrion mitochondrion = new Mitochondrion(volume * 0.05, 2.5);
        
        List<Organelle> organelles = Arrays.asList(nucleus, mitochondrion);
        
        return new EukaryoticCell(strain, volume, dryFraction, cytoplasm,
                                new EukaryoticGenomeProperties(), new EukaryoticPhysiology(),
                                membrane, nucleus, organelles);
    }
    
    private static Thylakoid createThylakoid() {
        Thylakoid thylakoid = new Thylakoid(2);
        thylakoid.addPigment("divinyl chlorophyll a");
        thylakoid.addPigment("divinyl chlorophyll b");
        thylakoid.addPigment("zeaxanthin");
        thylakoid.addProtein("Photosystem I");
        thylakoid.addProtein("Photosystem II");
        thylakoid.addProtein("ATP synthase");
        return thylakoid;
    }
}
