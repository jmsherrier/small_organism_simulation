package biological.validation;

/**
 * Individual validation metric with tolerance checking
 */
public class ValidationMetric {
    private final String name;
    private final double simulated;
    private final double expected;
    private final double error;
    private final double tolerance;
    
    public ValidationMetric(String name, double simulated, double expected, 
                           double error, double tolerance) {
        this.name = name;
        this.simulated = simulated;
        this.expected = expected;
        this.error = error;
        this.tolerance = tolerance;
    }
    
    public boolean isWithinTolerance() { return error <= tolerance; }
    public String getName() { return name; }
    public double getSimulated() { return simulated; }
    public double getExpected() { return expected; }
    public double getError() { return error; }
    public double getTolerance() { return tolerance; }
}
