package androidx.mediarouter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.leanback.R$drawable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.controls.management.ControlsAnimations$exitAnimation$1$1$1;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import java.util.Objects;
import java.util.WeakHashMap;
import kotlin.jvm.internal.Intrinsics;

public final class R$bool {
    public static float translationY = -1.0f;

    public static void setParentAbsoluteElevation(View view) {
        Drawable background = view.getBackground();
        if (background instanceof MaterialShapeDrawable) {
            setParentAbsoluteElevation(view, (MaterialShapeDrawable) background);
        }
    }

    public static void checkArgument(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(str));
        }
    }

    public static int checkArgumentNonnegative(int i) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException();
    }

    public static R$drawable createCornerTreatment(int i) {
        if (i == 0) {
            return new RoundedCornerTreatment();
        }
        if (i != 1) {
            return new RoundedCornerTreatment();
        }
        return new CutCornerTreatment();
    }

    public static AnimatorSet enterAnimation(View view) {
        Log.d("ControlsUiController", Intrinsics.stringPlus("Enter animation for ", view));
        view.setTransitionAlpha(0.0f);
        view.setAlpha(1.0f);
        view.setTranslationY(translationY);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", new float[]{0.0f, 1.0f});
        DecelerateInterpolator decelerateInterpolator = Interpolators.DECELERATE_QUINT;
        ofFloat.setInterpolator(decelerateInterpolator);
        ofFloat.setStartDelay(183);
        ofFloat.setDuration(167);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0f});
        ofFloat2.setInterpolator(decelerateInterpolator);
        ofFloat2.setStartDelay(217);
        ofFloat2.setDuration(217);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        return animatorSet;
    }

    public static final AnimatorSet exitAnimation(View view, Runnable runnable) {
        Log.d("ControlsUiController", Intrinsics.stringPlus("Exit animation for ", view));
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", new float[]{0.0f});
        AccelerateInterpolator accelerateInterpolator = Interpolators.ACCELERATE;
        ofFloat.setInterpolator(accelerateInterpolator);
        ofFloat.setDuration(183);
        view.setTranslationY(0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", new float[]{-translationY});
        ofFloat2.setInterpolator(accelerateInterpolator);
        ofFloat2.setDuration(183);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        if (runnable != null) {
            animatorSet.addListener(new ControlsAnimations$exitAnimation$1$1$1(runnable));
        }
        return animatorSet;
    }

    public static void setElevation(View view, float f) {
        Drawable background = view.getBackground();
        if (background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) background).setElevation(f);
        }
    }

    public static void setParentAbsoluteElevation(View view, MaterialShapeDrawable materialShapeDrawable) {
        Objects.requireNonNull(materialShapeDrawable);
        ElevationOverlayProvider elevationOverlayProvider = materialShapeDrawable.drawableState.elevationOverlayProvider;
        if (elevationOverlayProvider != null && elevationOverlayProvider.elevationOverlayEnabled) {
            float f = 0.0f;
            for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                f += ViewCompat.Api21Impl.getElevation((View) parent);
            }
            MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = materialShapeDrawable.drawableState;
            if (materialShapeDrawableState.parentAbsoluteElevation != f) {
                materialShapeDrawableState.parentAbsoluteElevation = f;
                materialShapeDrawable.updateZ();
            }
        }
    }
}
