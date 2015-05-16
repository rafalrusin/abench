package abench.core;

import java.io.PrintWriter;
import java.util.Map;

public class TextResultFormatter implements BenchmarkResultFormatter {
    private PrintWriter out;

    public TextResultFormatter(PrintWriter out) {
        this.out = out;
    }

    public void printResult(Benchmark benchmark, BenchmarkResult benchmarkResult, Map<Integer, Float> result) {
        out.println("Benchmark: " + benchmark.getClass().getSimpleName());
        out.println("result: " + result);
        out.println("benchmarkResult: " + benchmarkResult);
        out.println();
        out.flush();
    }

}
