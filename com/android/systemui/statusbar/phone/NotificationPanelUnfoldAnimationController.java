package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* compiled from: NotificationPanelUnfoldAnimationController.kt */
public final class NotificationPanelUnfoldAnimationController {
    public final Context context;
    public final Lazy translateAnimator$delegate;

    public NotificationPanelUnfoldAnimationController(Context context2, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        this.context = context2;
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new NotificationPanelUnfoldAnimationController$translateAnimator$2(naturalRotationUnfoldProgressProvider));
    }
}
