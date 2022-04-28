package com.android.p012wm.shell.common;

import android.graphics.Rect;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$positionAboveInBounds$2 */
/* compiled from: FloatingContentCoordinator.kt */
final class C1846x994e5850 extends Lambda implements Function0<Boolean> {
    public final /* synthetic */ Rect $allowedBounds;
    public final /* synthetic */ Lazy<Rect> $newContentBoundsAbove$delegate;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1846x994e5850(Rect rect, Lazy<Rect> lazy) {
        super(0);
        this.$allowedBounds = rect;
        this.$newContentBoundsAbove$delegate = lazy;
    }

    public final Object invoke() {
        return Boolean.valueOf(this.$allowedBounds.contains(this.$newContentBoundsAbove$delegate.getValue()));
    }
}
