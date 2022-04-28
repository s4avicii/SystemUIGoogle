package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleViewInfoTask;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda3 implements BubbleViewInfoTask.Callback {
    public final /* synthetic */ BubbleController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda3(BubbleController bubbleController, boolean z, boolean z2) {
        this.f$0 = bubbleController;
        this.f$1 = z;
        this.f$2 = z2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e4, code lost:
        if (r2 == false) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x010a, code lost:
        if (r0.mVisibleLocusIds.contains(r6) != false) goto L_0x010c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onBubbleViewsReady(com.android.p012wm.shell.bubbles.Bubble r7) {
        /*
            r6 = this;
            com.android.wm.shell.bubbles.BubbleController r0 = r6.f$0
            boolean r1 = r6.f$1
            boolean r6 = r6.f$2
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.bubbles.BubbleData r0 = r0.mBubbleData
            java.util.Objects.requireNonNull(r0)
            java.util.HashMap<java.lang.String, com.android.wm.shell.bubbles.Bubble> r2 = r0.mPendingBubbles
            java.util.Objects.requireNonNull(r7)
            java.lang.String r3 = r7.mKey
            r2.remove(r3)
            java.lang.String r2 = r7.mKey
            com.android.wm.shell.bubbles.Bubble r2 = r0.getBubbleInStackWithKey(r2)
            boolean r3 = r7.mIsTextChanged
            r4 = 1
            r3 = r3 ^ r4
            r1 = r1 | r3
            r3 = 0
            if (r2 != 0) goto L_0x005e
            r7.mSuppressFlyout = r1
            com.android.wm.shell.bubbles.BubbleData$TimeSource r1 = r0.mTimeSource
            com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda0 r1 = (com.android.p012wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda0) r1
            java.util.Objects.requireNonNull(r1)
            long r1 = java.lang.System.currentTimeMillis()
            r7.mLastUpdated = r1
            java.util.ArrayList r1 = r0.mBubbles
            r1.add(r3, r7)
            com.android.wm.shell.bubbles.BubbleData$Update r1 = r0.mStateChange
            r1.addedBubble = r7
            java.util.ArrayList r2 = r0.mBubbles
            int r2 = r2.size()
            if (r2 <= r4) goto L_0x0048
            r2 = r4
            goto L_0x0049
        L_0x0048:
            r2 = r3
        L_0x0049:
            r1.orderChanged = r2
            boolean r1 = r0.mExpanded
            if (r1 != 0) goto L_0x005a
            java.util.ArrayList r1 = r0.mBubbles
            java.lang.Object r1 = r1.get(r3)
            com.android.wm.shell.bubbles.BubbleViewProvider r1 = (com.android.p012wm.shell.bubbles.BubbleViewProvider) r1
            r0.setSelectedBubbleInternal(r1)
        L_0x005a:
            r0.trim()
            goto L_0x008f
        L_0x005e:
            r7.mSuppressFlyout = r1
            r1 = r1 ^ r4
            com.android.wm.shell.bubbles.BubbleData$Update r2 = r0.mStateChange
            r2.updatedBubble = r7
            boolean r2 = r0.mExpanded
            if (r2 != 0) goto L_0x008f
            if (r1 == 0) goto L_0x008f
            java.util.ArrayList r1 = r0.mBubbles
            int r1 = r1.indexOf(r7)
            java.util.ArrayList r2 = r0.mBubbles
            r2.remove(r7)
            java.util.ArrayList r2 = r0.mBubbles
            r2.add(r3, r7)
            com.android.wm.shell.bubbles.BubbleData$Update r2 = r0.mStateChange
            if (r1 == 0) goto L_0x0081
            r1 = r4
            goto L_0x0082
        L_0x0081:
            r1 = r3
        L_0x0082:
            r2.orderChanged = r1
            java.util.ArrayList r1 = r0.mBubbles
            java.lang.Object r1 = r1.get(r3)
            com.android.wm.shell.bubbles.BubbleViewProvider r1 = (com.android.p012wm.shell.bubbles.BubbleViewProvider) r1
            r0.setSelectedBubbleInternal(r1)
        L_0x008f:
            boolean r1 = r7.isEnabled(r4)
            if (r1 == 0) goto L_0x00a5
            int r1 = r7.mFlags
            r1 = r1 & -2
            r7.mFlags = r1
            r0.setSelectedBubbleInternal(r7)
            boolean r1 = r0.mExpanded
            if (r1 != 0) goto L_0x00a5
            r0.setExpandedInternal(r4)
        L_0x00a5:
            boolean r1 = r0.mExpanded
            if (r1 == 0) goto L_0x00af
            com.android.wm.shell.bubbles.BubbleViewProvider r1 = r0.mSelectedBubble
            if (r1 != r7) goto L_0x00af
            r1 = r4
            goto L_0x00b0
        L_0x00af:
            r1 = r3
        L_0x00b0:
            if (r1 != 0) goto L_0x00bd
            if (r6 == 0) goto L_0x00bd
            boolean r6 = r7.showInShade()
            if (r6 != 0) goto L_0x00bb
            goto L_0x00bd
        L_0x00bb:
            r6 = r3
            goto L_0x00be
        L_0x00bd:
            r6 = r4
        L_0x00be:
            r7.setSuppressNotification(r6)
            r6 = r1 ^ 1
            r7.setShowDot(r6)
            android.content.LocusId r6 = r7.mLocusId
            if (r6 == 0) goto L_0x0114
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r1 = r0.mSuppressedBubbles
            boolean r1 = r1.containsKey(r6)
            if (r1 == 0) goto L_0x00ef
            int r2 = r7.mFlags
            r5 = r2 & 8
            if (r5 == 0) goto L_0x00da
            r5 = r4
            goto L_0x00db
        L_0x00da:
            r5 = r3
        L_0x00db:
            if (r5 == 0) goto L_0x00e6
            r2 = r2 & 4
            if (r2 == 0) goto L_0x00e3
            r2 = r4
            goto L_0x00e4
        L_0x00e3:
            r2 = r3
        L_0x00e4:
            if (r2 != 0) goto L_0x00ef
        L_0x00e6:
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r1 = r0.mSuppressedBubbles
            r1.remove(r6)
            r0.doUnsuppress(r7)
            goto L_0x0114
        L_0x00ef:
            if (r1 != 0) goto L_0x0114
            int r1 = r7.mFlags
            r2 = r1 & 8
            if (r2 == 0) goto L_0x00f9
            r2 = r4
            goto L_0x00fa
        L_0x00f9:
            r2 = r3
        L_0x00fa:
            if (r2 != 0) goto L_0x010c
            r1 = r1 & 4
            if (r1 == 0) goto L_0x0101
            goto L_0x0102
        L_0x0101:
            r4 = r3
        L_0x0102:
            if (r4 == 0) goto L_0x0114
            android.util.ArraySet<android.content.LocusId> r1 = r0.mVisibleLocusIds
            boolean r1 = r1.contains(r6)
            if (r1 == 0) goto L_0x0114
        L_0x010c:
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r1 = r0.mSuppressedBubbles
            r1.put(r6, r7)
            r0.doSuppress(r7)
        L_0x0114:
            r0.dispatchPendingChanges()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda3.onBubbleViewsReady(com.android.wm.shell.bubbles.Bubble):void");
    }
}
