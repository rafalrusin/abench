package abench.sample;

import abench.core.Benchmark;

public class MergeSort extends Benchmark {
    @Override
    protected Object run(int n) {
        int[] tab = new int[n];
        int v = 7;
        for (int i = 0; i < n; i++) {
            tab[i] = v;
            v = v * 31 + 4;
        }

        int[] tmp = new int[n];
        mergeSort(tab, tmp, 0, n - 1);

        for (int i = 1; i < n; i++) {
            if (tab[i - 1] > tab[i])
                throw new IllegalStateException("failed to sort an array");
        }
        return tab;
    }

    private void mergeSort(int[] tab, int[] tmp, int a, int b) {
        incrementInstructionCount();

        if (a >= b) return;
        int m = (a + b) / 2;
        mergeSort(tab, tmp, a, m);
        mergeSort(tab, tmp, m + 1, b);

        int i = a;
        int j = m + 1;
        int k = a;

        while (true) {
            incrementInstructionCount();

            if (i <= m && j <= b) {
                if (tab[i] < tab[j]) {
                    tmp[k++] = tab[i++];
                } else {
                    tmp[k++] = tab[j++];
                }
            } else if (i <= m) {
                tmp[k++] = tab[i++];
            } else if (j <= b) {
                tmp[k++] = tab[j++];
            } else {
                break;
            }
        }

        for (int l = a; l <= b; l++) {
            tab[l] = tmp[l];
            incrementInstructionCount();
        }
    }
}
