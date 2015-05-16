package abench.core;

import abench.function.*;

import java.util.*;

public class BenchmarkRunner {
    private List<BenchmarkResultFormatter> formatterList = new ArrayList<BenchmarkResultFormatter>();

    public void addFormatter(BenchmarkResultFormatter formatter) {
        formatterList.add(formatter);
    }

    public void run(Benchmark benchmark, int repeats) {
        for (int i = 0; i < repeats; i++) {
            Map<Integer, Float> map = runInternal(benchmark);

            BenchmarkResult result = new BenchmarkResult();
            float resultConstant = Float.MAX_VALUE;

            for (Function function : new Function[]{new LinearFunction(), new NLogNFunction(),
                    new SqrFunction(), new NLogSqrNFunction(), new NLogCubicNFunction()}) {
                BenchmarkResult benchmarkResult = compare(map, function);

                //StdDev based comparison
                float benchmarkResultConstant = benchmarkResult.errorSqr;

                if (resultConstant > benchmarkResultConstant) {
                    result = benchmarkResult;
                    resultConstant = benchmarkResultConstant;
                }
            }

            for (BenchmarkResultFormatter benchmarkResultFormatter : formatterList) {
                benchmarkResultFormatter.printResult(benchmark, result, map);
            }
        }
    }

    private BenchmarkResult compare(Map<Integer, Float> benchmark, Function function) {
        float constant = 1.f;

        BenchmarkResult benchmarkResult = compareForConstant(benchmark, function, constant);

        for (float factor : new float[]{1.1f, 1.f / 1.1f}) {
            while (true) {
                constant *= factor;
                BenchmarkResult benchmarkResult2 = compareForConstant(benchmark, function, constant);
                if (benchmarkResult2.errorSqr <= benchmarkResult.errorSqr) {
                    benchmarkResult = benchmarkResult2;
                } else {
                    break;
                }
            }
        }

        return benchmarkResult;
    }

    private BenchmarkResult compareForConstant(Map<Integer, Float> benchmark, Function function, float constant) {
        BenchmarkResult benchmarkResult = new BenchmarkResult();
        benchmarkResult.constant = constant;

        float count = 0f;
        float errorSumSqr = 0.f;

        for (Map.Entry<Integer, Float> integerFloatEntry : benchmark.entrySet()) {
            float functionValue = function.eval(integerFloatEntry.getKey()) * benchmarkResult.constant;
            float benchmarkValue = integerFloatEntry.getValue();
            float diff = functionValue - benchmarkValue;
            errorSumSqr += diff * diff;
            count += 1;
        }

        benchmarkResult.errorSqr = errorSumSqr / count;
        benchmarkResult.function = function;
        return benchmarkResult;
    }

    private static Map<Integer, Float> runInternal(Benchmark benchmark) {
        Map<Integer, Float> resultMap = new TreeMap<Integer, Float>();

        float minTime = 0.003f;
        float maxTime = 0.02f;
//        float minTime = 0.000f;
//        float maxTime = 0.2f;
        float factor = 1.1f;

        float n = 1f;

        while (true) {
            if (!resultMap.containsKey((int) n)) {
                int instructionCount = 0;
                int probes = 1;
                float[] deltaTab = new float[probes];
                for (int i = 0; i < probes; i++) {
                    long ts = System.nanoTime();
                    System.identityHashCode(new Object());
                    benchmark.resetInstructionCount();
                    Object result = benchmark.run((int) n);
                    instructionCount = benchmark.getInstructionCount();
                    if (instructionCount == 0) {
                        throw new IllegalStateException("Instuction count must be greater than 0 for "
                                + benchmark.getClass().getSimpleName());
                    }
                    System.identityHashCode(result);
                    long ts2 = System.nanoTime();
                    deltaTab[i] = ((float)(ts2 - ts)) / 1000000000.f ;
                }
                Arrays.sort(deltaTab);
                float delta = deltaTab[0];

                if (delta > maxTime) {
                    break;
                }

                if (delta > minTime) {
                    resultMap.put((int) n, (float) instructionCount);
                }
            }

            n = n * factor;
        }

        return resultMap;
    }
}
