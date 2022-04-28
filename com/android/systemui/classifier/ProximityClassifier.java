package com.android.systemui.classifier;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.provider.DeviceConfig;
import android.view.MotionEvent;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Objects;

public final class ProximityClassifier extends FalsingClassifier {
    public final DistanceClassifier mDistanceClassifier;
    public long mGestureStartTimeNs;
    public boolean mNear;
    public long mNearDurationNs;
    public final float mPercentCoveredThreshold = DeviceConfig.getFloat("systemui", "brightline_falsing_proximity_percent_covered_threshold", 0.1f);
    public float mPercentNear;
    public long mPrevNearTimeNs;

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0063, code lost:
        if (java.lang.Math.abs(r2.mDx) >= r9.mHorizontalSwipeThresholdPx) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x009a, code lost:
        if (java.lang.Math.abs(r2.mDy) >= r9.mVerticalSwipeThresholdPx) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x009e, code lost:
        r2 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.systemui.classifier.FalsingClassifier.Result calculateFalsingResult(int r9) {
        /*
            r8 = this;
            if (r9 == 0) goto L_0x00f7
            r0 = 10
            if (r9 == r0) goto L_0x00f7
            r0 = 12
            if (r9 == r0) goto L_0x00f7
            r0 = 15
            if (r9 != r0) goto L_0x0010
            goto L_0x00f7
        L_0x0010:
            float r9 = r8.mPercentNear
            float r0 = r8.mPercentCoveredThreshold
            int r9 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            r0 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            if (r9 <= 0) goto L_0x00f2
            com.android.systemui.classifier.DistanceClassifier r9 = r8.mDistanceClassifier
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.classifier.DistanceClassifier$DistanceVectors r2 = r9.getDistances()
            com.android.systemui.classifier.FalsingDataProvider r3 = r9.mDataProvider
            boolean r3 = r3.isHorizontal()
            java.lang.String r4 = "Threshold: "
            r5 = 0
            r6 = 1
            if (r3 == 0) goto L_0x0066
            java.lang.String r3 = "Horizontal swipe distance: "
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            float r7 = r2.mDx
            float r7 = java.lang.Math.abs(r7)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            com.android.systemui.classifier.BrightLineFalsingManager.logDebug(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            float r4 = r9.mHorizontalSwipeThresholdPx
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.android.systemui.classifier.BrightLineFalsingManager.logDebug(r3)
            float r2 = r2.mDx
            float r2 = java.lang.Math.abs(r2)
            float r3 = r9.mHorizontalSwipeThresholdPx
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x009e
            goto L_0x009c
        L_0x0066:
            java.lang.String r3 = "Vertical swipe distance: "
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            float r7 = r2.mDy
            float r7 = java.lang.Math.abs(r7)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            com.android.systemui.classifier.BrightLineFalsingManager.logDebug(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            float r4 = r9.mVerticalSwipeThresholdPx
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.android.systemui.classifier.BrightLineFalsingManager.logDebug(r3)
            float r2 = r2.mDy
            float r2 = java.lang.Math.abs(r2)
            float r3 = r9.mVerticalSwipeThresholdPx
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x009e
        L_0x009c:
            r2 = r6
            goto L_0x009f
        L_0x009e:
            r2 = r5
        L_0x009f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Is longSwipe? "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            com.android.systemui.classifier.BrightLineFalsingManager.logDebug(r3)
            if (r2 == 0) goto L_0x00ba
            com.android.systemui.classifier.FalsingClassifier$Result r9 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r0)
            goto L_0x00c2
        L_0x00ba:
            java.lang.String r2 = r9.getReason()
            com.android.systemui.classifier.FalsingClassifier$Result r9 = r9.falsed(r0, r2)
        L_0x00c2:
            boolean r2 = r9.mFalsed
            if (r2 == 0) goto L_0x00ed
            float r2 = r8.mPercentNear
            float r3 = r8.mPercentCoveredThreshold
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r4[r5] = r2
            java.lang.Float r2 = java.lang.Float.valueOf(r3)
            r4[r6] = r2
            java.lang.String r9 = r9.getReason()
            r2 = 2
            r4[r2] = r9
            r9 = 0
            java.lang.String r2 = "{percentInProximity=%f, threshold=%f, distanceClassifier=%s}"
            java.lang.String r9 = java.lang.String.format(r9, r2, r4)
            com.android.systemui.classifier.FalsingClassifier$Result r8 = r8.falsed(r0, r9)
            goto L_0x00f1
        L_0x00ed:
            com.android.systemui.classifier.FalsingClassifier$Result r8 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r0)
        L_0x00f1:
            return r8
        L_0x00f2:
            com.android.systemui.classifier.FalsingClassifier$Result r8 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r0)
            return r8
        L_0x00f7:
            r8 = 0
            com.android.systemui.classifier.FalsingClassifier$Result r8 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.classifier.ProximityClassifier.calculateFalsingResult(int):com.android.systemui.classifier.FalsingClassifier$Result");
    }

    public final void onSessionEnded() {
        this.mPrevNearTimeNs = 0;
        this.mPercentNear = 0.0f;
    }

    public final void onSessionStarted() {
        this.mPrevNearTimeNs = 0;
        this.mPercentNear = 0.0f;
    }

    public final void update(boolean z, long j) {
        long j2 = this.mPrevNearTimeNs;
        if (j2 != 0 && j > j2 && this.mNear) {
            this.mNearDurationNs = (j - j2) + this.mNearDurationNs;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Updating duration: ");
            m.append(this.mNearDurationNs);
            BrightLineFalsingManager.logDebug(m.toString());
        }
        if (z) {
            BrightLineFalsingManager.logDebug("Set prevNearTimeNs: " + j);
            this.mPrevNearTimeNs = j;
        }
        this.mNear = z;
    }

    public ProximityClassifier(DistanceClassifier distanceClassifier, FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        super(falsingDataProvider);
        this.mDistanceClassifier = distanceClassifier;
        Objects.requireNonNull(deviceConfigProxy);
    }

    public final void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
        boolean covered = proximityEvent.getCovered();
        long timestampNs = proximityEvent.getTimestampNs();
        BrightLineFalsingManager.logDebug("Sensor is: " + covered + " at time " + timestampNs);
        update(covered, timestampNs);
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
        float f;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mGestureStartTimeNs = motionEvent.getEventTimeNano();
            if (this.mPrevNearTimeNs > 0) {
                this.mPrevNearTimeNs = motionEvent.getEventTimeNano();
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Gesture start time: ");
            m.append(this.mGestureStartTimeNs);
            BrightLineFalsingManager.logDebug(m.toString());
            this.mNearDurationNs = 0;
        }
        if (actionMasked == 1 || actionMasked == 3) {
            update(this.mNear, motionEvent.getEventTimeNano());
            long eventTimeNano = motionEvent.getEventTimeNano() - this.mGestureStartTimeNs;
            BrightLineFalsingManager.logDebug("Gesture duration, Proximity duration: " + eventTimeNano + ", " + this.mNearDurationNs);
            if (eventTimeNano == 0) {
                if (this.mNear) {
                    f = 1.0f;
                } else {
                    f = 0.0f;
                }
                this.mPercentNear = f;
                return;
            }
            this.mPercentNear = ((float) this.mNearDurationNs) / ((float) eventTimeNano);
        }
    }
}
