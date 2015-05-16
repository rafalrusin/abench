package abench.sample;

import abench.core.Benchmark;

public class BinaryCounter extends Benchmark {
    @Override
    protected Object run(int n) {
        int[] counter = new int[64];
        for (int i = 0; i < n; i++) {
            int p = 0;
            while (counter[p] == 1) {
                counter[p++] = 0;
                incrementInstructionCount();
            }
            counter[p] = 1;
            incrementInstructionCount();
        }
        return counter;
    }
}
