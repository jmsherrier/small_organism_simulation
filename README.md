# Prochlorococcus Genome Parser & Mass Calculator

## Overview
This project provides a Java-based tool to:

1. Download a Prochlorococcus MED4 GenBank genome from NCBI.
2. Parse gene information, including names, functions, genomic coordinates, and strand orientation.
3. Detect genome structure (circular or linear).
4. Represent the organism as a hierarchy of objects: `Prochlorococcus → Cytoplasm → Nucleoid → Gene`.
5. Calculate genome mass, dry mass, and wet mass in Daltons.

The tool is generalizable for other prokaryotic or eukaryotic genomes.

---

## Assumptions

### Biological
- All genes have 1-based inclusive coordinates (start..end both included).
- Gene names or functions may be unknown; unknown values are labeled `"unknown"`.
- Genes may be single- or multi-exon (represented by multiple coordinate ranges).
- Strand is `+` for forward, `-` for complement.
- Genome structure is either `circular`, `linear`, or `"unknown"` if not specified.
- Cytoplasm occupies the majority of cell volume; nucleoid always exists.

### Physical
- Average base pair mass: 650 Daltons.
- Conversion factor from cytoplasm volume to mass: `1 µm³ = 6.022e11 Daltons` (wet weight).
- Dry mass fraction is provided (e.g., 0.3 = 30% dry mass).

### Software
- Java 17+ recommended.
- Internet connection required for NCBI GenBank download.
- Input GenBank files must follow standard NCBI flat file formatting.

---

## Project Structure

```
project/
│
├─ Main.java                  # Entry point; downloads, parses, creates organism, outputs masses
├─ GenBankParser.java         # Downloads GenBank files, parses genes, detects genome structure
├─ Gene.java                  # Represents individual genes with ranges, function, strand
├─ Nucleoid.java              # Contains list of genes and genome structure
├─ Cytoplasm.java             # Contains volume and nucleoid
├─ Organism.java              # Abstract class for general organisms
├─ Prochlorococcus.java       # Concrete organism class extending Organism
├─ CellConversion.java        # Utility class to calculate genome, dry, and wet masses
├─ README.md                  # Project documentation (this file)
└─ MED4.gb                    # Optional: local copy of downloaded GenBank file
```

---

## Class Summaries

### 1. `Gene`
- Stores: `geneName`, `function`, `List<int[]> ranges`, `strand`.
- Methods:
  - `getStartBasePair()`, `getEndBasePair()` → single- or multi-range positions.
  - `getLength()` → total coding length across all ranges.

### 2. `Nucleoid`
- Stores all genes for the organism.
- Stores genome structure (`circular` or `linear`).

### 3. `Cytoplasm`
- Stores cytoplasm volume (µm³) and associated nucleoid.

### 4. `Organism` (abstract)
- Stores wet volume, dry fraction, and cytoplasm.
- Provides generalizable mass calculation methods.

### 5. `Prochlorococcus`
- Extends `Organism`.
- Calculates genome mass, dry Daltons with genome, and wet Daltons.

### 6. `CellConversion`
- Utility class for mass conversions:
  - `genomeToDaltons(List<Gene>)`
  - `volumeToWetDaltons(double volume)`
  - `volumeToDryDaltons(double volume, double dryFraction)`
  - `volumeToDryDaltonsWithGenome(double volume, double dryFraction, List<Gene>)`

### 7. `GenBankParser`
- Downloads GenBank files from NCBI.
- Parses genes and their coordinates (handles joins, complements, multi-exon).
- Detects genome structure (`circular` or `linear`).

---

## Usage Instructions

1. Compile all Java files:
```bash
javac *.java
```

2. Run the main program:
```bash
java Main
```

3. Program workflow:
- Downloads MED4 GenBank genome from NCBI.
- Parses genes and genome structure.
- Creates a `Prochlorococcus` object with wet volume and dry fraction.
- Outputs:
  - Strain name
  - Wet volume (µm³)
  - Dry fraction
  - Genome structure (circular/linear)
  - Number of genes
  - Genome mass in Daltons
  - Dry mass including genome
  - Wet mass

---

## Example Output
```
=== STARTING GENBANK PARSER DEMO ===
Step 1: Downloading GenBank file...
Downloaded GenBank file for BX548174

Step 2: Detecting genome structure...
Genome structure: circular

Step 3: Parsing GenBank file...
Parsed 1700 genes from GenBank file

Step 4: Creating Prochlorococcus object...

Step 5: Outputting results...
Strain: MED4
Wet Volume (µm³): 0.6
Dry Fraction: 0.3
Genome Structure: circular
Number of genes: 1700
Genome Mass (Daltons): 1.1E9
Dry Daltons with Genome: 2.5E11
Wet Daltons: 3.6E11

=== DEMO COMPLETED SUCCESSFULLY ===
```

---

## Notes & Future Improvements
- The parser is generalizable for eukaryotic multi-exon genes.
- Can extend `Organism` for other microbes.
- Could add sequence retrieval, GC content calculation, or visualization.