package androidx.leanback;

import android.graphics.Color;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.resources.MaterialAttributes;

public final class R$string {
    public static final int[] RestrictedPreference = {C1777R.attr.useAdminDisabledSummary, C1777R.attr.userRestriction};
    public static final int[] RestrictedSwitchPreference = {C1777R.attr.restrictedSwitchSummary, C1777R.attr.useAdditionalSummary};

    public static int compositeARGBWithAlpha(int i, int i2) {
        return ColorUtils.setAlphaComponent(i, (Color.alpha(i) * i2) / 255);
    }

    public static int getColor(View view, int i) {
        return MaterialAttributes.resolveOrThrow(view.getContext(), i, view.getClass().getCanonicalName());
    }

    public static int layer(int i, int i2, float f) {
        return ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, Math.round(((float) Color.alpha(i2)) * f)), i);
    }
}
