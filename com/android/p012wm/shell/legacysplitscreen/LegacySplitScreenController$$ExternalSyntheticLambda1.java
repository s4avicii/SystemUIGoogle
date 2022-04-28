package com.android.p012wm.shell.legacysplitscreen;

import android.graphics.Rect;
import java.lang.ref.WeakReference;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenController$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ Rect f$0;
    public final /* synthetic */ Rect f$1;

    public /* synthetic */ LegacySplitScreenController$$ExternalSyntheticLambda1(Rect rect, Rect rect2) {
        this.f$0 = rect;
        this.f$1 = rect2;
    }

    public final boolean test(Object obj) {
        Rect rect = this.f$0;
        Rect rect2 = this.f$1;
        BiConsumer biConsumer = (BiConsumer) ((WeakReference) obj).get();
        if (biConsumer != null) {
            biConsumer.accept(rect, rect2);
        }
        if (biConsumer == null) {
            return true;
        }
        return false;
    }
}
