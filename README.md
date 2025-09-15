# Prochlorococcus Cellular Simulation Framework

## Overview

A comprehensive Java-based biological simulation framework that models cellular systems with genomic, physiological, and metabolic accuracy. The system integrates real genomic data from NCBI and simulates diverse cell types including photosynthetic prokaryotes, heterotrophic bacteria, and eukaryotic cells.

## Features

### Core Capabilities
- **Multi-Cell Type Support**: Photosynthetic (Prochlorococcus), Heterotrophic bacteria, and Eukaryotic cells
- **Real Genomic Data Integration**: Downloads and parses NCBI GenBank files (accession: BX548174)
- **Accurate Mass Calculations**: Biological density-based conversions to Daltons
- **Organelle Simulation**: Functional mitochondria, chloroplasts, nuclei
- **Metabolic Modeling**: Photosynthesis, respiration, nutrient uptake kinetics

### Biological Accuracy
- **Realistic Cellular Composition**: Lipid membranes, protein distributions, genomic mass
- **Environmental Responses**: Multi-factor temperature, pH, salinity, and light effects
- **Molecular Crowding**: Density-dependent reaction rate modulation
- **Species-Specific Adaptations**: MED4 high-light adaptations, nutrient utilization profiles

## Project Structure

```
src/
├── biological/
│   ├── cells/                 # Cell type implementations
│   │   ├── Cell.java          # Abstract base class
│   │   ├── Prochlorococcus.java
│   │   ├── MED4Strain.java    # MED4-specific implementation
│   │   ├── HeterotrophicBacterium.java
│   │   └── EukaryoticCell.java
│   ├── organelles/            # Organelle implementations
│   │   ├── Organelle.java
│   │   ├── Nucleus.java
│   │   ├── Mitochondrion.java
│   │   ├── Chloroplast.java
│   │   └── Thylakoid.java
│   ├── components/            # Cellular components
│   │   ├── Cytoplasm.java
│   │   ├── Nucleoid.java
│   │   ├── PlasmaMembrane.java
│   │   ├── Gene.java
│   │   └── Protein.java
│   ├── interfaces/            # Behavior interfaces
│   │   ├── Photosynthetic.java
│   │   ├── PhotosyntheticOrganelle.java
│   │   ├── SpecializedMetabolism.java
│   │   ├── Motile.java
│   │   ├── GenomeProperties.java
│   │   └── Physiology.java
│   ├── properties/            # Strain-specific properties
│   │   ├── BacterialGenomeProperties.java
│   │   ├── EukaryoticGenomeProperties.java
│   │   ├── BacterialPhysiology.java
│   │   ├── EukaryoticPhysiology.java
│   │   └── RespirationProperties.java
│   ├── factory/               # Object creation
│   │   └── CellFactory.java
│   ├── utils/                 # Utilities
│   │   ├── CellConversion.java
│   │   └── GenBankParser.java
│   └── Main.java              # Entry point
└── resources/                 # Configuration files
```

## Supported Cell Types

### 1. Prochlorococcus MED4
- **Characteristics**: High-light adapted marine cyanobacterium (Rocap et al., 2003)
- **Genome**: 1.66 Mbp, 1716 genes, circular
- **Pigments**: Divinyl chlorophyll a/b, zeaxanthin (Partensky et al., 1999)
- **Metabolism**: Photosynthetic, lacks nitrate reductase (Moore et al., 2002)
- **Optimal Conditions**: 24°C, 250 µE/m²/s light

### 2. Heterotrophic Bacteria
- **Characteristics**: Generic bacterial model (Neidhardt et al., 1990)
- **Genome**: 4.0 Mbp, 4000 genes, circular  
- **Metabolism**: Aerobic respiration, spore-forming
- **Optimal Conditions**: 37°C, pH 7.0

### 3. Eukaryotic Cells
- **Characteristics**: Yeast-like model (Goffeau et al., 1996)
- **Genome**: 100 Mbp, 20,000 genes, linear
- **Organelles**: Nucleus, mitochondria
- **Metabolism**: Oxidative phosphorylation

## Key Components

### Cell Conversion Utilities
- **Volume to Mass**: 6.7e11 Da/µm³ (wet), 8.1e11 Da/µm³ (dry) based on cellular densities (Stock et al., 2019)
- **Genome Mass**: 650 Da/bp including associated proteins (BNID 101938)
- **Membrane Calculations**: Lipid area 0.7 nm²/molecule, bilayer structure (Lodish et al., 2000)

### GenBank Parser
- **Download Capability**: Fetches GenBank files from NCBI Entrez
- **Gene Extraction**: Parses CDS features with coordinates, products, and multi-exon structures
- **Genome Structure**: Detects circular/linear organization from LOCUS line

### Physiology Models
- **Environmental Effects**: Multi-factor response curves (Follows et al., 2007)
- **Nutrient Requirements**: Redfield-like ratios with species variations (Geider & La Roche, 2002)
- **Stress Tolerance**: Heat, acid, osmotic, oxidative stress responses

## Quick Start

```java
// Download and parse GenBank data
List<Gene> genes = GenBankParser.parseGenBankFile("MED4.gb");

// Create cell instances
Cell photosyntheticCell = CellFactory.createCell("photosynthetic", "MED4", genes, 0.6, 0.3);
Cell heterotrophicCell = CellFactory.createCell("heterotrophic", "E. coli", genes, 1.0, 0.25);

// Access functionality
double growthRate = cell.getGrowthRate();
Map<String, Double> uptakeRates = cell.getNutrientUptakeRates();

// Type-specific features
if (cell instanceof Photosynthetic) {
    double photosynthesis = ((Photosynthetic)cell).simulatePhotosynthesis(200);
}
```

## Output Metrics

- **Growth Rates**: Species-specific doubling rates (hr⁻¹)
- **Mass Calculations**: Wet/dry mass (Da), genome mass, membrane mass
- **Photosynthesis**: Light-dependent O₂ production (mmol/mg protein/h)
- **Respiration**: ATP production rates (µmol/min)
- **Nutrient Uptake**: Surface area-limited transport (molecules/s)
- **Environmental Effects**: Multi-factor growth limitations (0-1 scale)

## Biological Parameters

### Cellular Composition (Alberts et al., 2002)
- **Cytoplasm**: 85% of cell volume, pH 7.2, 0.15 M ionic strength
- **Membrane**: 15% volume, typical bacterial lipid composition
- **Dry Fraction**: 20-30% of wet mass
- **Protein Mass**: 40,000 Da average (BNID 101627)
- **Molecular Crowding**: 300 mg/mL reduces reaction rates (BNID 101640)

### Environmental Optima
- **pH**: 7.0-7.2 (bacterial), 7.2 (eukaryotic)
- **Temperature**: 24-37°C species-dependent
- **Salinity**: 0.1-0.15 M for most organisms
- **Light Saturation**: 250 µE/m²/s for MED4 (Moore & Chisholm, 1999)

### Metabolic Parameters
- **Photosynthesis**: Michaelis-Menten kinetics with photoinhibition (Platt et al., 1980)
- **Respiration**: Oxygen-dependent efficiency (P/O ratios)
- **Nutrient Uptake**: Surface area limited diffusion (Button, 1998)

## Requirements

- **Java**: JDK 21 or later
- **Internet Connection**: For GenBank file downloads
- **Memory**: 2GB RAM minimum, 4GB recommended for large genomes
- **Storage**: 50MB for GenBank files and output

## Example Output

```
=== CELLULAR SIMULATION FRAMEWORK ===
✓ Downloaded GenBank file for accession: BX548174
✓ Parsed 1790 genes from GenBank file

✓ Created photosynthetic cell: MED4
✓ Created heterotrophic cell: E. coli  
✓ Created eukaryotic cell: Yeast

Growth Rates:
- MED4: 1.2 doublings/day
- E. coli: 1.8 doublings/day  
- Yeast: 0.4 doublings/day

Specialized Functions:
- Photosynthesis: 156.7 mmol O₂/mg/h
- Respiration: 120.0 µmol ATP/min
- Organelles: 2 functional units

=== SIMULATION COMPLETED ===
```

## Future Enhancements

- **Additional Cell Types**: Archaea, pathogenic bacteria, algal cells
- **Metabolic Networks**: Genome-scale metabolic modeling (Orth et al., 2010)
- **Spatial Modeling**: Intracellular compartmentalization (Karr et al., 2012)
- **Evolutionary Simulations**: Mutation and selection dynamics
- **Graphical Visualization**: 3D cellular component rendering

## References

### Primary Literature
1. **Rocap, G., et al. (2003)**. Genome divergence in two Prochlorococcus ecotypes reflects oceanic niche differentiation. *Nature*, 424(6952), 1042-1047.
2. **Partensky, F., Hess, W. R., & Vaulot, D. (1999)**. Prochlorococcus, a marine photosynthetic prokaryote of global significance. *Microbiology and Molecular Biology Reviews*, 63(1), 106-127.
3. **Moore, L. R., et al. (2002)**. Utilization of different nitrogen sources by the marine cyanobacteria Prochlorococcus and Synechococcus. *Limnology and Oceanography*, 47(4), 989-996.

### Methods & Theory
4. **Neidhardt, F. C., et al. (1990)**. *Physiology of the Bacterial Cell*. Sinauer Associates.
5. **Goffeau, A., et al. (1996)**. Life with 6000 genes. *Science*, 274(5287), 546-567.
6. **Follows, M. J., et al. (2007)**. Emergent biogeography of microbial communities in a model ocean. *Science*, 315(5820), 1843-1846.

### Biological Constants
7. **BNID Database**: Biological Numbers Database (http://bionumbers.hms.harvard.edu)
8. **Alberts, B., et al. (2002)**. *Molecular Biology of the Cell*. 4th edition. Garland Science.
9. **Lodish, H., et al. (2000)**. *Molecular Cell Biology*. 4th edition. W. H. Freeman.

### Environmental Physiology
10. **Geider, R. J., & La Roche, J. (2002)**. Redfield revisited: variability of C:N:P in marine microalgae and its biochemical basis. *European Journal of Phycology*, 37(1), 1-17.
11. **Moore, L. R., & Chisholm, S. W. (1999)**. Photophysiology of the marine cyanobacterium Prochlorococcus: ecotypic differences among cultured isolates. *Limnology and Oceanography*, 44(3), 628-638.
12. **Platt, T., et al. (1980)**. Photoinhibition of photosynthesis in natural assemblages of marine phytoplankton. *Journal of Marine Research*, 38(4), 687-701.

### Systems Biology
13. **Orth, J. D., et al. (2010)**. What is flux balance analysis? *Nature Biotechnology*, 28(3), 245-248.
14. **Karr, J. R., et al. (2012)**. A whole-cell computational model predicts phenotype from genotype. *Cell*, 150(2), 389-401.
15. **Stock, D., et al. (2019)**. Cellular density and cytoplasmic transport. *Current Opinion in Cell Biology*, 56, 87-94.

## Contributing

This framework is designed for extensibility. New cell types, organelles, and metabolic processes can be added by implementing the appropriate interfaces and extending base classes.

## License

This project is available for academic and research use. Please cite relevant literature when using biological parameters or models from this framework.

---

*Last updated: December 2023*  
*Framework version: 1.0*  
*Compatible with Java 21+*