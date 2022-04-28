package com.android.systemui.p006qs;

import android.animation.ValueAnimator;
import com.google.android.systemui.assist.uihints.ChipsContainer;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.SlashDrawable$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SlashDrawable$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SlashDrawable$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                SlashDrawable slashDrawable = (SlashDrawable) this.f$0;
                Objects.requireNonNull(slashDrawable);
                slashDrawable.invalidateSelf();
                return;
            default:
                ChipsContainer chipsContainer = (ChipsContainer) this.f$0;
                int i = ChipsContainer.$r8$clinit;
                Objects.requireNonNull(chipsContainer);
                chipsContainer.setTranslationY((1.0f - ((Float) valueAnimator.getAnimatedValue()).floatValue()) * ((float) chipsContainer.START_DELTA));
                return;
        }
    }
}
