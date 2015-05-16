package abench.sample;

import abench.core.Benchmark;

import java.util.Random;

public class NestedLoop extends Benchmark {
    @Override
    protected Object run(int n) {
        int[] tab = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            tab[i] = random.nextInt();
            incrementInstructionCount();
        }

        int v = 0;
        for (int i = 0; i < n; i++) {
            incrementInstructionCount();
            for (int j = 0; j < n; j++) {
                v = v + tab[i] + tab[j];
                incrementInstructionCount();
            }
        }

        return v;
    }
}
