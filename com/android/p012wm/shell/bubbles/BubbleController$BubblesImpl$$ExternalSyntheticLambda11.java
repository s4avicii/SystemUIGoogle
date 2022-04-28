package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.bubbles.BubbleData;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda11 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda11 implements Supplier {
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ BubbleEntry f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ IntConsumer f$3;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda11(BubbleController.BubblesImpl bubblesImpl, BubbleEntry bubbleEntry, ArrayList arrayList, BubbleController$BubblesImpl$$ExternalSyntheticLambda10 bubbleController$BubblesImpl$$ExternalSyntheticLambda10) {
        this.f$0 = bubblesImpl;
        this.f$1 = bubbleEntry;
        this.f$2 = arrayList;
        this.f$3 = bubbleController$BubblesImpl$$ExternalSyntheticLambda10;
    }

    public final Object get() {
        BubbleController.BubblesImpl bubblesImpl = this.f$0;
        BubbleEntry bubbleEntry = this.f$1;
        List list = this.f$2;
        IntConsumer intConsumer = this.f$3;
        Objects.requireNonNull(bubblesImpl);
        BubbleController bubbleController = BubbleController.this;
        Objects.requireNonNull(bubbleController);
        boolean z = true;
        if (bubbleController.isSummaryOfBubbles(bubbleEntry)) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    BubbleEntry bubbleEntry2 = (BubbleEntry) list.get(i);
                    if (bubbleController.mBubbleData.hasAnyBubbleWithKey(bubbleEntry2.getKey())) {
                        Bubble anyBubbleWithkey = bubbleController.mBubbleData.getAnyBubbleWithkey(bubbleEntry2.getKey());
                        if (anyBubbleWithkey != null) {
                            Bubbles.SysuiProxy sysuiProxy = bubbleController.mSysuiProxy;
                            String str = anyBubbleWithkey.mKey;
                            BubblesManager.C17525 r7 = (BubblesManager.C17525) sysuiProxy;
                            Objects.requireNonNull(r7);
                            executor2.execute(new BubblesManager$5$$ExternalSyntheticLambda0(r7, str, 0));
                            anyBubbleWithkey.setSuppressNotification(true);
                            anyBubbleWithkey.setShowDot(false);
                        }
                    } else {
                        intConsumer.accept(i);
                    }
                }
            }
            intConsumer.accept(-1);
            BubbleData bubbleData = bubbleController.mBubbleData;
            String groupKey = bubbleEntry.mSbn.getGroupKey();
            String key = bubbleEntry.getKey();
            Objects.requireNonNull(bubbleData);
            bubbleData.mSuppressedGroupKeys.put(groupKey, key);
            BubbleData.Update update = bubbleData.mStateChange;
            update.suppressedSummaryChanged = true;
            update.suppressedSummaryGroup = groupKey;
            bubbleData.dispatchPendingChanges();
        } else {
            Bubble bubbleInStackWithKey = bubbleController.mBubbleData.getBubbleInStackWithKey(bubbleEntry.getKey());
            if (bubbleInStackWithKey == null || !bubbleEntry.isBubble()) {
                bubbleInStackWithKey = bubbleController.mBubbleData.getOverflowBubbleWithKey(bubbleEntry.getKey());
            }
            if (bubbleInStackWithKey == null) {
                z = false;
                return Boolean.valueOf(z);
            }
            bubbleInStackWithKey.setSuppressNotification(true);
            bubbleInStackWithKey.setShowDot(false);
        }
        BubblesManager.C17525 r11 = (BubblesManager.C17525) bubbleController.mSysuiProxy;
        Objects.requireNonNull(r11);
        executor2.execute(new BubblesManager$5$$ExternalSyntheticLambda3(r11, "BubbleController.handleDismissalInterception", 0));
        return Boolean.valueOf(z);
    }
}
