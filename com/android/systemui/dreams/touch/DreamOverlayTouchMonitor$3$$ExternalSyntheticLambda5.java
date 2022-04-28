package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda27;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ DreamOverlayTouchMonitor.Evaluator f$0;
    public final /* synthetic */ Set f$1;

    public /* synthetic */ DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda5(DreamOverlayTouchMonitor.Evaluator evaluator, HashSet hashSet) {
        this.f$0 = evaluator;
        this.f$1 = hashSet;
    }

    public final Object apply(Object obj) {
        DreamOverlayTouchMonitor.Evaluator evaluator = this.f$0;
        Set set = this.f$1;
        DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) obj;
        Objects.requireNonNull(touchSessionImpl);
        boolean anyMatch = touchSessionImpl.mGestureListeners.stream().map(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda4(evaluator)).anyMatch(WifiPickerTracker$$ExternalSyntheticLambda27.INSTANCE$1);
        if (anyMatch) {
            set.add(touchSessionImpl);
        }
        return Boolean.valueOf(anyMatch);
    }
}
