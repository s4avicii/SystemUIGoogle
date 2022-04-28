package com.android.p012wm.shell.common;

import android.graphics.Rect;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;

/* renamed from: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$newContentBoundsAbove$2 */
/* compiled from: FloatingContentCoordinator.kt */
final class C1844xa8ad3931 extends Lambda implements Function0<Rect> {
    public final /* synthetic */ Rect $contentRect;
    public final /* synthetic */ Rect $newlyOverlappingRect;
    public final /* synthetic */ Ref$ObjectRef<List<Rect>> $rectsToAvoidAbove;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1844xa8ad3931(Rect rect, Ref$ObjectRef<List<Rect>> ref$ObjectRef, Rect rect2) {
        super(0);
        this.$contentRect = rect;
        this.$rectsToAvoidAbove = ref$ObjectRef;
        this.$newlyOverlappingRect = rect2;
    }

    public final Object invoke() {
        Rect rect = this.$contentRect;
        List<Rect> sortedWith = CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.plus((Collection) this.$rectsToAvoidAbove.element, (Object) this.$newlyOverlappingRect), new C1843x8b489ee0(true));
        Rect rect2 = new Rect(rect);
        for (Rect rect3 : sortedWith) {
            if (!Rect.intersects(rect2, rect3)) {
                break;
            }
            rect2.offsetTo(rect2.left, rect3.top + (-rect.height()));
        }
        return rect2;
    }
}
