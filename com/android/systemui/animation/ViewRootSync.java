package com.android.systemui.animation;

import android.app.ActivityManager;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: ViewRootSync.kt */
public final class ViewRootSync {
    public static final boolean forceDisableSynchronization = ActivityManager.isLowRamDeviceStatic();

    public static void synchronizeNextDraw(View view, View view2, Function0 function0) {
        if (forceDisableSynchronization || !view.isAttachedToWindow() || view.getViewRootImpl() == null || !view2.isAttachedToWindow() || view2.getViewRootImpl() == null || Intrinsics.areEqual(view.getViewRootImpl(), view2.getViewRootImpl())) {
            function0.invoke();
            return;
        }
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl.consumeNextDraw(new ViewRootSync$synchronizeNextDraw$consumeNextDraw$1(ref$IntRef, transaction, function0))) {
            ref$IntRef.element++;
            viewRootImpl.getView().invalidate();
        }
        ViewRootImpl viewRootImpl2 = view2.getViewRootImpl();
        if (viewRootImpl2.consumeNextDraw(new ViewRootSync$synchronizeNextDraw$consumeNextDraw$1(ref$IntRef, transaction, function0))) {
            ref$IntRef.element++;
            viewRootImpl2.getView().invalidate();
        }
        if (ref$IntRef.element == 0) {
            function0.invoke();
        }
    }
}
