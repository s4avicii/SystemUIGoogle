package com.android.p012wm.shell.bubbles;

import android.content.ComponentName;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda4 implements Function {
    public static final /* synthetic */ BubbleData$$ExternalSyntheticLambda4 INSTANCE = new BubbleData$$ExternalSyntheticLambda4(0);
    public static final /* synthetic */ BubbleData$$ExternalSyntheticLambda4 INSTANCE$1 = new BubbleData$$ExternalSyntheticLambda4(1);
    public static final /* synthetic */ BubbleData$$ExternalSyntheticLambda4 INSTANCE$2 = new BubbleData$$ExternalSyntheticLambda4(2);
    public static final /* synthetic */ BubbleData$$ExternalSyntheticLambda4 INSTANCE$3 = new BubbleData$$ExternalSyntheticLambda4(3);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Bubble bubble = (Bubble) obj;
                Comparator<Bubble> comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                Objects.requireNonNull(bubble);
                return Long.valueOf(Math.max(bubble.mLastUpdated, bubble.mLastAccessed));
            case 1:
                return ComponentName.unflattenFromString((String) obj);
            case 2:
                DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) obj;
                Objects.requireNonNull(touchSessionImpl);
                return touchSessionImpl.mEventListeners;
            default:
                return ((ActivityManagerWrapper) obj).getRunningTask();
        }
    }
}
