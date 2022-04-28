package com.android.systemui.statusbar.notification;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.PanelView;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/* renamed from: com.android.systemui.statusbar.notification.ViewGroupFadeHelper$Companion$fadeOutAllChildrenExcept$animator$1$1 */
/* compiled from: ViewGroupFadeHelper.kt */
public final class C1242xbb47cb26 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ViewGroup $root;
    public final /* synthetic */ Set<View> $viewsToFadeOut;

    public C1242xbb47cb26(PanelView panelView, LinkedHashSet linkedHashSet) {
        this.$root = panelView;
        this.$viewsToFadeOut = linkedHashSet;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        boolean z;
        Float f = (Float) this.$root.getTag(C1777R.C1779id.view_group_fade_helper_previous_value_tag);
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float floatValue = ((Float) animatedValue).floatValue();
        for (View next : this.$viewsToFadeOut) {
            float alpha = next.getAlpha();
            if (f == null || alpha != f.floatValue()) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                next.setTag(C1777R.C1779id.view_group_fade_helper_restore_tag, Float.valueOf(next.getAlpha()));
            }
            next.setAlpha(floatValue);
        }
        this.$root.setTag(C1777R.C1779id.view_group_fade_helper_previous_value_tag, Float.valueOf(floatValue));
    }
}
