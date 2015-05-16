package abench.core;

public abstract class Benchmark {
    private int instructionCount = 0;

    protected void incrementInstructionCount() {
        instructionCount++;
    }

    public void resetInstructionCount() {
        instructionCount = 0;
    }

    public int getInstructionCount() {
        return instructionCount;
    }

    protected abstract Object run(int n);
}
