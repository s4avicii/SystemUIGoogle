package com.android.p012wm.shell.legacysplitscreen;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenController$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ LegacySplitScreenController$$ExternalSyntheticLambda2(boolean z) {
        this.f$0 = z;
    }

    public final boolean test(Object obj) {
        boolean z = this.f$0;
        Consumer consumer = (Consumer) ((WeakReference) obj).get();
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        if (consumer == null) {
            return true;
        }
        return false;
    }
}
