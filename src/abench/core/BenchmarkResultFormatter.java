package abench.core;

import java.util.Map;

public interface BenchmarkResultFormatter {
    void printResult(Benchmark benchmark, BenchmarkResult benchmarkResult, Map<Integer, Float> result);
}