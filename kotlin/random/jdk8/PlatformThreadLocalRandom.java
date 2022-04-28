package kotlin.random.jdk8;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.random.AbstractPlatformRandom;

/* compiled from: PlatformThreadLocalRandom.kt */
public final class PlatformThreadLocalRandom extends AbstractPlatformRandom {
    public final Random getImpl() {
        return ThreadLocalRandom.current();
    }
}
