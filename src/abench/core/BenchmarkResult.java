package abench.core;

public class BenchmarkResult {
    public float constant;
    public float errorSqr;
    public Function function;

    @Override
    public String toString() {
        return "BenchmarkResult{" +
                "constant=" + constant +
                ", errorSqr=" + errorSqr +
                ", function='" + function.getClass().getSimpleName() + '\'' +
                '}';
    }
}
