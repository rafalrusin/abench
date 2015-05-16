package abench.sample;

import abench.core.Benchmark;

public class SumIntervals extends Benchmark {
    @Override
    protected Object run(int n) {
        int v = sum(0, n - 1);
        return v;
    }

    private int sum(int a, int b) {
        incrementInstructionCount();

        if (a >= b) {
            return 17 + a;
        }

        int m = (a + b) / 2;
        return sum(a, m) + sum(m + 1, b);
    }
}
