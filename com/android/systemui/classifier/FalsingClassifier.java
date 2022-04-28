package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.plugins.FalsingManager;
import java.util.Objects;

public abstract class FalsingClassifier {
    public final FalsingDataProvider mDataProvider;
    public final FalsingClassifier$$ExternalSyntheticLambda0 mMotionEventListener;

    public static class Result {
        public final double mConfidence;
        public final String mContext;
        public final boolean mFalsed;
        public final String mReason;

        public final String getReason() {
            return String.format("{context=%s reason=%s}", new Object[]{this.mContext, this.mReason});
        }

        public static Result passed(double d) {
            return new Result(false, d, (String) null, (String) null);
        }

        public Result(boolean z, double d, String str, String str2) {
            this.mFalsed = z;
            this.mConfidence = d;
            this.mContext = str;
            this.mReason = str2;
        }
    }

    public abstract Result calculateFalsingResult(int i);

    public void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
    }

    public void onSessionEnded() {
    }

    public void onSessionStarted() {
    }

    public void onTouchEvent(MotionEvent motionEvent) {
    }

    public final TimeLimitedMotionEventBuffer getRecentMotionEvents() {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        return falsingDataProvider.mRecentMotionEvents;
    }

    public final boolean isRight() {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        falsingDataProvider.recalculateData();
        if (!falsingDataProvider.mRecentMotionEvents.isEmpty() && falsingDataProvider.mLastMotionEvent.getX() > falsingDataProvider.mFirstRecentMotionEvent.getX()) {
            return true;
        }
        return false;
    }

    public final boolean isUp() {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        falsingDataProvider.recalculateData();
        if (!falsingDataProvider.mRecentMotionEvents.isEmpty() && falsingDataProvider.mLastMotionEvent.getY() < falsingDataProvider.mFirstRecentMotionEvent.getY()) {
            return true;
        }
        return false;
    }

    public FalsingClassifier(FalsingDataProvider falsingDataProvider) {
        FalsingClassifier$$ExternalSyntheticLambda0 falsingClassifier$$ExternalSyntheticLambda0 = new FalsingClassifier$$ExternalSyntheticLambda0(this);
        this.mMotionEventListener = falsingClassifier$$ExternalSyntheticLambda0;
        this.mDataProvider = falsingDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        falsingDataProvider.mMotionEventListeners.add(falsingClassifier$$ExternalSyntheticLambda0);
    }

    public final Result falsed(double d, String str) {
        return new Result(true, d, getClass().getSimpleName(), str);
    }
}
