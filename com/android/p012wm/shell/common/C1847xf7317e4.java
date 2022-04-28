package com.android.p012wm.shell.common;

import android.graphics.Rect;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$positionBelowInBounds$2 */
/* compiled from: FloatingContentCoordinator.kt */
final class C1847xf7317e4 extends Lambda implements Function0<Boolean> {
    public final /* synthetic */ Rect $allowedBounds;
    public final /* synthetic */ Lazy<Rect> $newContentBoundsBelow$delegate;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1847xf7317e4(Rect rect, Lazy<Rect> lazy) {
        super(0);
        this.$allowedBounds = rect;
        this.$newContentBoundsBelow$delegate = lazy;
    }

    public final Object invoke() {
        return Boolean.valueOf(this.$allowedBounds.contains(this.$newContentBoundsBelow$delegate.getValue()));
    }
}
