package abench.sample;

import abench.core.Benchmark;

public class Sum extends Benchmark {
    @Override
    protected Object run(int n) {
        int v = 17;
        for (int i = 0; i < n; i++) {
            v = v * 31 + 17;
            incrementInstructionCount();
        }
        return v;
    }
}
