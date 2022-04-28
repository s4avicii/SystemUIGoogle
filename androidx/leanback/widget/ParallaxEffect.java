package androidx.leanback.widget;

import java.util.ArrayList;

public abstract class ParallaxEffect {
    public final ArrayList mMarkerValues = new ArrayList(2);
    public final ArrayList mTargets;

    public abstract float calculateFraction();

    public ParallaxEffect() {
        new ArrayList(2);
        new ArrayList(2);
        this.mTargets = new ArrayList(4);
    }
}
