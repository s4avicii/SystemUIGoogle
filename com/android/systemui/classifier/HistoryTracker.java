package com.android.systemui.classifier;

import com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda4;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public final class HistoryTracker {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final double HISTORY_DECAY = Math.pow(10.0d, (Math.log10(0.1d) / 10000.0d) * 100.0d);
    public final ArrayList mBeliefListeners = new ArrayList();
    public DelayQueue<CombinedResult> mResults = new DelayQueue<>();
    public final SystemClock mSystemClock;

    public interface BeliefListener {
        void onBeliefChanged(double d);
    }

    public class CombinedResult implements Delayed {
        public final long mExpiryMs;
        public final double mScore;

        public CombinedResult(long j, double d) {
            this.mExpiryMs = j + 10000;
            this.mScore = d;
        }

        public final int compareTo(Object obj) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            return Long.compare(getDelay(timeUnit), ((Delayed) obj).getDelay(timeUnit));
        }

        public final long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.mExpiryMs - HistoryTracker.this.mSystemClock.uptimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public final double falseBelief() {
        do {
        } while (this.mResults.poll() != null);
        if (this.mResults.isEmpty()) {
            return 0.5d;
        }
        return ((Double) this.mResults.stream().map(new HistoryTracker$$ExternalSyntheticLambda4(this.mSystemClock.uptimeMillis())).reduce(Double.valueOf(0.5d), HistoryTracker$$ExternalSyntheticLambda1.INSTANCE)).doubleValue();
    }

    public final double falseConfidence() {
        do {
        } while (this.mResults.poll() != null);
        if (this.mResults.isEmpty()) {
            return 0.0d;
        }
        return 1.0d - Math.sqrt(((Double) this.mResults.stream().map(new HistoryTracker$$ExternalSyntheticLambda3(((Double) this.mResults.stream().map(WMShellBaseModule$$ExternalSyntheticLambda4.INSTANCE$1).reduce(Double.valueOf(0.0d), HistoryTracker$$ExternalSyntheticLambda0.INSTANCE)).doubleValue() / ((double) this.mResults.size()))).reduce(Double.valueOf(0.0d), HistoryTracker$$ExternalSyntheticLambda0.INSTANCE)).doubleValue() / ((double) this.mResults.size()));
    }

    public HistoryTracker(SystemClock systemClock) {
        this.mSystemClock = systemClock;
    }

    public final void addResults(Collection<FalsingClassifier.Result> collection, long j) {
        double d;
        double d2 = 0.0d;
        for (FalsingClassifier.Result next : collection) {
            Objects.requireNonNull(next);
            if (next.mFalsed) {
                d = 0.5d;
            } else {
                d = -0.5d;
            }
            d2 += (d * next.mConfidence) + 0.5d;
        }
        double size = d2 / ((double) collection.size());
        if (size == 1.0d) {
            size = 0.99999d;
        } else if (size == 0.0d) {
            size = 1.0E-5d;
        }
        double d3 = size;
        do {
        } while (this.mResults.poll() != null);
        this.mResults.add(new CombinedResult(j, d3));
        this.mBeliefListeners.forEach(new HistoryTracker$$ExternalSyntheticLambda2(this, 0));
    }
}
