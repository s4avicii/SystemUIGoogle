package kotlin.jvm.internal;

import java.io.Serializable;
import java.util.Objects;

/* compiled from: Lambda.kt */
public abstract class Lambda<R> implements FunctionBase<R>, Serializable {
    private final int arity;

    public final String toString() {
        Objects.requireNonNull(Reflection.factory);
        return ReflectionFactory.renderLambdaToString(this);
    }

    public Lambda(int i) {
        this.arity = i;
    }

    public final int getArity() {
        return this.arity;
    }
}
