package com.android.p012wm.shell.bubbles;

import java.util.List;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda12 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda12 implements Function1 {
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda12(BubbleController bubbleController) {
        this.f$0 = bubbleController;
    }

    public final Object invoke(Object obj) {
        BubbleController bubbleController = this.f$0;
        Objects.requireNonNull(bubbleController);
        ((List) obj).forEach(new BubbleController$$ExternalSyntheticLambda7(bubbleController, 0));
        return null;
    }
}
