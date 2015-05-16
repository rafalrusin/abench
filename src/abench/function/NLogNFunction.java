package abench.function;

import abench.core.Function;

public class NLogNFunction implements Function {
    @Override
    public float eval(float x) {
        return (float) (Math.log(x) * x);
    }
}
