package androidx.leanback.widget;

import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SearchOrbView$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SearchOrbView f$0;

    public /* synthetic */ SearchOrbView$$ExternalSyntheticLambda1(SearchOrbView searchOrbView) {
        this.f$0 = searchOrbView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SearchOrbView searchOrbView = this.f$0;
        int i = SearchOrbView.$r8$clinit;
        Objects.requireNonNull(searchOrbView);
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (searchOrbView.mSearchOrbView.getBackground() instanceof GradientDrawable) {
            ((GradientDrawable) searchOrbView.mSearchOrbView.getBackground()).setColor(intValue);
        }
    }
}
