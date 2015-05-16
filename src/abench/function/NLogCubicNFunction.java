package abench.function;

import abench.core.Function;

public class NLogCubicNFunction implements Function {
    @Override
    public float eval(float x) {
        return (float) (Math.pow(Math.log(x), 3) * x);
    }
}
