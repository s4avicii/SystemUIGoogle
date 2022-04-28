package com.android.systemui.statusbar.phone;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationPanelUnfoldAnimationController.kt */
public final class NotificationPanelUnfoldAnimationController$translateAnimator$2 extends Lambda implements Function0<UnfoldConstantTranslateAnimator> {
    public final /* synthetic */ NaturalRotationUnfoldProgressProvider $progressProvider;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationPanelUnfoldAnimationController$translateAnimator$2(NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        super(0);
        this.$progressProvider = naturalRotationUnfoldProgressProvider;
    }

    public final Object invoke() {
        UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.LEFT;
        UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.RIGHT;
        return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.quick_settings_panel, direction), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.notification_stack_scroller, direction2), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.rightLayout, direction2), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.clock, direction), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.date, direction)), this.$progressProvider);
    }
}
