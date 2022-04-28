package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.ShellTaskOrganizer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda1 implements ShellTaskOrganizer.LocusIdListener {
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda1(BubbleController bubbleController) {
        this.f$0 = bubbleController;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        if (r2.getTaskId() != r5) goto L_0x0039;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0052 A[PHI: r2 
      PHI: (r2v1 com.android.wm.shell.bubbles.Bubble) = (r2v0 com.android.wm.shell.bubbles.Bubble), (r2v3 com.android.wm.shell.bubbles.Bubble) binds: [B:15:0x0044, B:17:0x004f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onVisibilityChanged(int r5, android.content.LocusId r6, boolean r7) {
        /*
            r4 = this;
            com.android.wm.shell.bubbles.BubbleController r4 = r4.f$0
            java.util.Objects.requireNonNull(r4)
            com.android.wm.shell.bubbles.BubbleData r4 = r4.mBubbleData
            r0 = 0
            if (r6 != 0) goto L_0x000b
            goto L_0x002b
        L_0x000b:
            r1 = r0
        L_0x000c:
            java.util.ArrayList r2 = r4.mBubbles
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x002b
            java.util.ArrayList r2 = r4.mBubbles
            java.lang.Object r2 = r2.get(r1)
            com.android.wm.shell.bubbles.Bubble r2 = (com.android.p012wm.shell.bubbles.Bubble) r2
            java.util.Objects.requireNonNull(r2)
            android.content.LocusId r3 = r2.mLocusId
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0028
            goto L_0x002c
        L_0x0028:
            int r1 = r1 + 1
            goto L_0x000c
        L_0x002b:
            r2 = 0
        L_0x002c:
            if (r7 == 0) goto L_0x003f
            if (r2 == 0) goto L_0x0039
            java.util.Objects.requireNonNull(r4)
            int r1 = r2.getTaskId()
            if (r1 == r5) goto L_0x003f
        L_0x0039:
            android.util.ArraySet<android.content.LocusId> r1 = r4.mVisibleLocusIds
            r1.add(r6)
            goto L_0x0044
        L_0x003f:
            android.util.ArraySet<android.content.LocusId> r1 = r4.mVisibleLocusIds
            r1.remove(r6)
        L_0x0044:
            if (r2 != 0) goto L_0x0052
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r1 = r4.mSuppressedBubbles
            java.lang.Object r1 = r1.get(r6)
            r2 = r1
            com.android.wm.shell.bubbles.Bubble r2 = (com.android.p012wm.shell.bubbles.Bubble) r2
            if (r2 != 0) goto L_0x0052
            goto L_0x008f
        L_0x0052:
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r1 = r4.mSuppressedBubbles
            java.lang.Object r1 = r1.get(r6)
            r3 = 1
            if (r1 == 0) goto L_0x005d
            r1 = r3
            goto L_0x005e
        L_0x005d:
            r1 = r0
        L_0x005e:
            if (r7 == 0) goto L_0x007d
            if (r1 != 0) goto L_0x007d
            int r1 = r2.mFlags
            r1 = r1 & 4
            if (r1 == 0) goto L_0x0069
            r0 = r3
        L_0x0069:
            if (r0 == 0) goto L_0x007d
            int r0 = r2.getTaskId()
            if (r5 == r0) goto L_0x007d
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r5 = r4.mSuppressedBubbles
            r5.put(r6, r2)
            r4.doSuppress(r2)
            r4.dispatchPendingChanges()
            goto L_0x008f
        L_0x007d:
            if (r7 != 0) goto L_0x008f
            android.util.ArrayMap<android.content.LocusId, com.android.wm.shell.bubbles.Bubble> r5 = r4.mSuppressedBubbles
            java.lang.Object r5 = r5.remove(r6)
            com.android.wm.shell.bubbles.Bubble r5 = (com.android.p012wm.shell.bubbles.Bubble) r5
            if (r5 == 0) goto L_0x008c
            r4.doUnsuppress(r5)
        L_0x008c:
            r4.dispatchPendingChanges()
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda1.onVisibilityChanged(int, android.content.LocusId, boolean):void");
    }
}
