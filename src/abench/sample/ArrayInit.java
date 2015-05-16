package abench.sample;

import abench.core.Benchmark;

public class ArrayInit extends Benchmark {
    @Override
    public Object run(int n) {
        int[] tab = new int[n];
        int v = 17;
        for (int i = 0; i < n; i++) {
            tab[i] = v;
            v = v * 31 + 4;
            incrementInstructionCount();
        }
        return tab;
    }
}
