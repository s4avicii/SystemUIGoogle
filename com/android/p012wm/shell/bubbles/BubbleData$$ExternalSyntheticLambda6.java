package com.android.p012wm.shell.bubbles;

import android.net.wifi.ScanResult;
import com.android.wifitrackerlib.ScanResultUpdater;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* renamed from: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleData bubbleData = (BubbleData) this.f$0;
                Objects.requireNonNull(bubbleData);
                return !((Bubble) obj).equals(bubbleData.mSelectedBubble);
            default:
                ScanResultUpdater scanResultUpdater = (ScanResultUpdater) this.f$0;
                Objects.requireNonNull(scanResultUpdater);
                if (scanResultUpdater.mClock.millis() - (((ScanResult) ((Map.Entry) obj).getValue()).timestamp / 1000) > scanResultUpdater.mMaxScanAgeMillis) {
                    return true;
                }
                return false;
        }
    }
}
