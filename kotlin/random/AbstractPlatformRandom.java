package kotlin.random;

import java.util.Random;

/* compiled from: PlatformRandom.kt */
public abstract class AbstractPlatformRandom extends Random {
    public abstract Random getImpl();

    public final int nextInt() {
        return getImpl().nextInt();
    }
}
