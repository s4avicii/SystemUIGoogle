package com.android.keyguard;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: KeyguardUnfoldTransition.kt */
public final class KeyguardUnfoldTransition$translateAnimator$2 extends Lambda implements Function0<UnfoldConstantTranslateAnimator> {
    public final /* synthetic */ NaturalRotationUnfoldProgressProvider $unfoldProgressProvider;
    public final /* synthetic */ KeyguardUnfoldTransition this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardUnfoldTransition$translateAnimator$2(KeyguardUnfoldTransition keyguardUnfoldTransition, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        super(0);
        this.this$0 = keyguardUnfoldTransition;
        this.$unfoldProgressProvider = naturalRotationUnfoldProgressProvider;
    }

    public final Object invoke() {
        UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.LEFT;
        KeyguardUnfoldTransition keyguardUnfoldTransition = this.this$0;
        Function0<Boolean> function0 = keyguardUnfoldTransition.filterNever;
        Function0<Boolean> function02 = keyguardUnfoldTransition.filterSplitShadeOnly;
        UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.RIGHT;
        return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.keyguard_status_area, direction, function0), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.controls_button, direction, function0), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.lockscreen_clock_view_large, direction, function02), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.lockscreen_clock_view, direction, function0), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.notification_stack_scroller, direction2, function02), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(C1777R.C1779id.wallet_button, direction2, function0)), this.$unfoldProgressProvider);
    }
}
