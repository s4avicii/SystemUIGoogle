package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.leanback.R$string;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialCheckBox extends AppCompatCheckBox {
    public static final int[][] ENABLED_CHECKED_STATES = {new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    public ColorStateList materialThemeColorsTintList;
    public boolean useMaterialThemeColors;

    public MaterialCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, C1777R.attr.checkboxStyle, 2132018657), attributeSet, C1777R.attr.checkboxStyle);
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.MaterialCheckBox, C1777R.attr.checkboxStyle, 2132018657, new int[0]);
        if (obtainStyledAttributes.hasValue(0)) {
            setButtonTintList(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 0));
        }
        this.useMaterialThemeColors = obtainStyledAttributes.getBoolean(1, false);
        obtainStyledAttributes.recycle();
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && getButtonTintList() == null) {
            this.useMaterialThemeColors = true;
            if (this.materialThemeColorsTintList == null) {
                int[][] iArr = ENABLED_CHECKED_STATES;
                int color = R$string.getColor(this, C1777R.attr.colorControlActivated);
                int color2 = R$string.getColor(this, C1777R.attr.colorSurface);
                int color3 = R$string.getColor(this, C1777R.attr.colorOnSurface);
                this.materialThemeColorsTintList = new ColorStateList(iArr, new int[]{R$string.layer(color2, color, 1.0f), R$string.layer(color2, color3, 0.54f), R$string.layer(color2, color3, 0.38f), R$string.layer(color2, color3, 0.38f)});
            }
            setButtonTintList(this.materialThemeColorsTintList);
        }
    }
}
