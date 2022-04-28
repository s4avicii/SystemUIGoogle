package com.android.p012wm.shell.bubbles;

import java.util.Objects;
import java.util.function.ToLongFunction;

/* renamed from: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda9 implements ToLongFunction {
    public static final /* synthetic */ BubbleData$$ExternalSyntheticLambda9 INSTANCE = new BubbleData$$ExternalSyntheticLambda9();

    public final long applyAsLong(Object obj) {
        Bubble bubble = (Bubble) obj;
        Objects.requireNonNull(bubble);
        return Math.max(bubble.mLastUpdated, bubble.mLastAccessed);
    }
}
