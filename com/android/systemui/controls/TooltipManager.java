package com.android.systemui.controls;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.recents.TriangleShape;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: TooltipManager.kt */
public final class TooltipManager {
    public final View arrowView;
    public final boolean below = true;
    public final View dismissView;
    public final ViewGroup layout;
    public final int maxTimesShown = 2;
    public final Function1<Integer, Unit> preferenceStorer;
    public int shown;
    public final TextView textView;

    public TooltipManager(Context context) {
        this.shown = context.getSharedPreferences(context.getPackageName(), 0).getInt("ControlsStructureSwipeTooltipCount", 0);
        View inflate = LayoutInflater.from(context).inflate(C1777R.layout.controls_onboarding, (ViewGroup) null);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) inflate;
        this.layout = viewGroup;
        this.preferenceStorer = new TooltipManager$preferenceStorer$1(context, this);
        viewGroup.setAlpha(0.0f);
        this.textView = (TextView) viewGroup.requireViewById(C1777R.C1779id.onboarding_text);
        View requireViewById = viewGroup.requireViewById(C1777R.C1779id.dismiss);
        requireViewById.setOnClickListener(new TooltipManager$dismissView$1$1(this));
        this.dismissView = requireViewById;
        View requireViewById2 = viewGroup.requireViewById(C1777R.C1779id.arrow);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843829, typedValue, true);
        int color = context.getResources().getColor(typedValue.resourceId, context.getTheme());
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(C1777R.dimen.recents_onboarding_toast_arrow_corner_radius);
        ViewGroup.LayoutParams layoutParams = requireViewById2.getLayoutParams();
        float f = (float) layoutParams.width;
        float f2 = (float) layoutParams.height;
        int i = TriangleShape.$r8$clinit;
        Path path = new Path();
        path.moveTo(0.0f, f2);
        path.lineTo(f, f2);
        path.lineTo(f / 2.0f, 0.0f);
        path.close();
        ShapeDrawable shapeDrawable = new ShapeDrawable(new TriangleShape(path, f, f2));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(color);
        paint.setPathEffect(new CornerPathEffect((float) dimensionPixelSize));
        requireViewById2.setBackground(shapeDrawable);
        this.arrowView = requireViewById2;
    }

    public final void hide(boolean z) {
        boolean z2;
        if (this.layout.getAlpha() == 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            this.layout.post(new TooltipManager$hide$1(z, this));
        }
    }

    public final void show(int i, int i2) {
        boolean z;
        if (this.shown < this.maxTimesShown) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.textView.setText(C1777R.string.controls_structure_tooltip);
            int i3 = this.shown + 1;
            this.shown = i3;
            ((TooltipManager$preferenceStorer$1) this.preferenceStorer).invoke(Integer.valueOf(i3));
            this.layout.post(new TooltipManager$show$1(this, i, i2));
        }
    }
}
