package com.android.p012wm.shell.bubbles;

import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.plugins.FalsingManager;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda7(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) this.f$0;
                Bubble bubble = (Bubble) obj;
                Objects.requireNonNull(bubbleController);
                BubbleData bubbleData = bubbleController.mBubbleData;
                Objects.requireNonNull(bubble);
                if (!bubbleData.hasAnyBubbleWithKey(bubble.mKey)) {
                    bubble.inflate(new BubbleController$$ExternalSyntheticLambda2(bubbleController, bubble), bubbleController.mContext, bubbleController, bubbleController.mStackView, bubbleController.mBubbleIconFactory, bubbleController.mBubbleBadgeIconFactory, true);
                    return;
                }
                return;
            default:
                boolean z = BrightLineFalsingManager.DEBUG;
                ((FalsingClassifier) obj).onProximityEvent((FalsingManager.ProximityEvent) this.f$0);
                return;
        }
    }
}
