package abench.function;

import abench.core.Function;

public class SqrFunction implements Function {
    @Override
    public float eval(float x) {
        return x*x;
    }
}
