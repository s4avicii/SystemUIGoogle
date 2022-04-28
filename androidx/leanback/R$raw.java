package androidx.leanback;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.CrossFadeHelper$1;

public final class R$raw {
    public static void fadeIn(View view, long j, int i) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
        }
        view.animate().alpha(1.0f).setDuration(j).setStartDelay((long) i).setInterpolator(Interpolators.ALPHA_IN).withEndAction((Runnable) null);
        if (view.hasOverlappingRendering() && view.getLayerType() != 2) {
            view.animate().withLayer();
        }
    }

    public static void fadeOut(View view, long j, Runnable runnable) {
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(j).setInterpolator(Interpolators.ALPHA_OUT).setStartDelay((long) 0).withEndAction(new CrossFadeHelper$1(runnable, view));
        if (view.hasOverlappingRendering()) {
            view.animate().withLayer();
        }
    }

    public static void updateFontSize(View view, int i, int i2) {
        updateFontSize((TextView) view.findViewById(i), i2);
    }

    public static void updateFontSize(TextView textView, int i) {
        if (textView != null) {
            textView.setTextSize(0, (float) textView.getResources().getDimensionPixelSize(i));
        }
    }

    public static void updateLayerType(View view, float f) {
        if (!view.hasOverlappingRendering() || f <= 0.0f || f >= 1.0f) {
            if (view.getLayerType() == 2 && view.getTag(C1777R.C1779id.cross_fade_layer_type_changed_tag) != null) {
                view.setLayerType(0, (Paint) null);
            }
        } else if (view.getLayerType() != 2) {
            view.setLayerType(2, (Paint) null);
            view.setTag(C1777R.C1779id.cross_fade_layer_type_changed_tag, Boolean.TRUE);
        }
    }

    public static void fadeOut(View view, float f, boolean z) {
        view.animate().cancel();
        if (f == 1.0f && view.getVisibility() != 8) {
            view.setVisibility(4);
        } else if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        if (z) {
            f = Math.min(f / 0.5833333f, 1.0f);
        }
        float interpolation = Interpolators.ALPHA_OUT.getInterpolation(1.0f - f);
        view.setAlpha(interpolation);
        updateLayerType(view, interpolation);
    }

    public static void fadeIn(View view, float f, boolean z) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        if (z) {
            f = Math.min(f / 0.5833333f, 1.0f);
        }
        float interpolation = Interpolators.ALPHA_IN.getInterpolation(f);
        view.setAlpha(interpolation);
        updateLayerType(view, interpolation);
    }
}
