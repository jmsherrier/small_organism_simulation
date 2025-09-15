import java.util.*;

/**
 * Represents a gene with its name, function, genomic coordinates, and strand.
 * 
 * Assumptions:
 * - Gene coordinates are 1-based inclusive (start..end both included)
 * - Supports multi-exon/segment genes (join) and complements
 * - Gene names and functions may be unknown but never null
 * - Single-range genes are stored as one range
 */
public class Gene {
    private String geneName;       // Gene's name (e.g., 'psbA')
    private String function;       // Functional description
    private List<int[]> ranges;    // List of [start, end] coordinate pairs
    private char strand;           // '+' for forward, '-' for complement

    /**
     * Constructs a Gene object with a single range on the forward strand.
     */
    public Gene(String geneName, String function, int startBasePair, int endBasePair) {
        this(geneName, function, Collections.singletonList(new int[]{startBasePair, endBasePair}), '+');
    }

    /**
     * Constructs a Gene object with multiple ranges and strand info.
     *
     * @param geneName  gene name or "unknown"
     * @param function  gene function or "unknown"
     * @param ranges    list of [start,end] coordinates
     * @param strand    '+' or '-'
     */
    public Gene(String geneName, String function, List<int[]> ranges, char strand) {
        this.geneName = geneName != null ? geneName : "unknown";
        this.function = function != null ? function : "unknown";
        this.ranges = new ArrayList<>(ranges);
        this.strand = (strand == '+' || strand == '-') ? strand : '+';
    }

    // Getters
    public String getGeneName() { return geneName; }
    public String getFunction() { return function; }
    public List<int[]> getRanges() { return Collections.unmodifiableList(ranges); }
    public char getStrand() { return strand; }

    /** Return start of first range (convenience) */
    public int getStartBasePair() { return ranges.get(0)[0]; }

    /** Return end of last range (convenience) */
    public int getEndBasePair() { return ranges.get(ranges.size() - 1)[1]; }

    /** Total length across all ranges */
    public int getLength() {
        int length = 0;
        for (int[] range : ranges) {
            length += range[1] - range[0] + 1;
        }
        return length;
    }
}
