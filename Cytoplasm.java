/**
 * Represents the cytoplasm of a cell, including volume and nucleoid.
 *
 * Assumptions:
 * - Cytoplasm volume is in cubic micrometers
 * - Nucleoid is always present (may contain empty gene list)
 */
public class Cytoplasm {
    private double volumeMicron3; // Volume in µm³
    private Nucleoid nucleoid;

    public Cytoplasm(double volumeMicron3, Nucleoid nucleoid) {
        this.volumeMicron3 = volumeMicron3;
        this.nucleoid = nucleoid;
    }

    public double getVolume() { return volumeMicron3; }
    public Nucleoid getNucleoid() { return nucleoid; }
}
