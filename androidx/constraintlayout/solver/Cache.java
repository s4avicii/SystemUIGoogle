package androidx.constraintlayout.solver;

public final class Cache {
    public Pools$SimplePool arrayRowPool = new Pools$SimplePool();
    public SolverVariable[] mIndexedVariables = new SolverVariable[32];
    public Pools$SimplePool solverVariablePool = new Pools$SimplePool();
}
