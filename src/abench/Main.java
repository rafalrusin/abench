package abench;

import abench.core.Benchmark;
import abench.core.BenchmarkRunner;
import abench.core.HtmlResultFormatter;
import abench.core.TextResultFormatter;
import abench.sample.*;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        BenchmarkRunner benchmarkRunner = new BenchmarkRunner();
        benchmarkRunner.addFormatter(new TextResultFormatter(new PrintWriter(System.out)));
        benchmarkRunner.addFormatter(new HtmlResultFormatter(new File("out")));

        for (Benchmark benchmark : new Benchmark[]{
                new Sum(),
                new BinaryCounter(),
                new NestedLoop(),
                new MergeSort(),
                new SumIntervals(),
                new SumLog(),
                new SumLog2()}) {
            benchmarkRunner.run(benchmark, 1);
        }
    }
}
