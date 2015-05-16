package abench.sample;

import abench.core.Benchmark;

public class SumLog extends Benchmark {
    @Override
    protected Object run(int n) {
        int v = 17;

        for (int i = 0; i < n; i++) {
            incrementInstructionCount();
            int j = i;
            while (j > 0) {
                incrementInstructionCount();
                v += 31 * v + 9;
                j = j / 2;
            }
        }
        return v;
    }
}
