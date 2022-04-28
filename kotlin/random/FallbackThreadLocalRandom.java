package kotlin.random;

import java.util.Random;

/* compiled from: PlatformRandom.kt */
public final class FallbackThreadLocalRandom extends AbstractPlatformRandom {
    public final FallbackThreadLocalRandom$implStorage$1 implStorage = new FallbackThreadLocalRandom$implStorage$1();

    public final Random getImpl() {
        return (Random) this.implStorage.get();
    }
}
