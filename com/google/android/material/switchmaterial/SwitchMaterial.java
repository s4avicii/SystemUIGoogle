package com.google.android.material.switchmaterial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.leanback.R$string;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Objects;
import java.util.WeakHashMap;

public class SwitchMaterial extends SwitchCompat {
    public static final int[][] ENABLED_CHECKED_STATES = {new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    public final ElevationOverlayProvider elevationOverlayProvider;
    public ColorStateList materialThemeColorsThumbTintList;
    public ColorStateList materialThemeColorsTrackTintList;
    public boolean useMaterialThemeColors;

    public SwitchMaterial(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, C1777R.attr.switchStyle, 2132018659), attributeSet, C1777R.attr.switchStyle);
        Context context2 = getContext();
        this.elevationOverlayProvider = new ElevationOverlayProvider(context2);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.SwitchMaterial, C1777R.attr.switchStyle, 2132018659, new int[0]);
        this.useMaterialThemeColors = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && this.mThumbTintList == null) {
            if (this.materialThemeColorsThumbTintList == null) {
                int color = R$string.getColor(this, C1777R.attr.colorSurface);
                int color2 = R$string.getColor(this, C1777R.attr.colorControlActivated);
                float dimension = getResources().getDimension(C1777R.dimen.mtrl_switch_thumb_elevation);
                ElevationOverlayProvider elevationOverlayProvider2 = this.elevationOverlayProvider;
                Objects.requireNonNull(elevationOverlayProvider2);
                if (elevationOverlayProvider2.elevationOverlayEnabled) {
                    float f = 0.0f;
                    for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        f += ViewCompat.Api21Impl.getElevation((View) parent);
                    }
                    dimension += f;
                }
                int compositeOverlayIfNeeded = this.elevationOverlayProvider.compositeOverlayIfNeeded(color, dimension);
                this.materialThemeColorsThumbTintList = new ColorStateList(ENABLED_CHECKED_STATES, new int[]{R$string.layer(color, color2, 1.0f), compositeOverlayIfNeeded, R$string.layer(color, color2, 0.38f), compositeOverlayIfNeeded});
            }
            this.mThumbTintList = this.materialThemeColorsThumbTintList;
            this.mHasThumbTint = true;
            applyThumbTint();
        }
        if (this.useMaterialThemeColors && this.mTrackTintList == null) {
            if (this.materialThemeColorsTrackTintList == null) {
                int[][] iArr = ENABLED_CHECKED_STATES;
                int color3 = R$string.getColor(this, C1777R.attr.colorSurface);
                int color4 = R$string.getColor(this, C1777R.attr.colorControlActivated);
                int color5 = R$string.getColor(this, C1777R.attr.colorOnSurface);
                this.materialThemeColorsTrackTintList = new ColorStateList(iArr, new int[]{R$string.layer(color3, color4, 0.54f), R$string.layer(color3, color5, 0.32f), R$string.layer(color3, color4, 0.12f), R$string.layer(color3, color5, 0.12f)});
            }
            this.mTrackTintList = this.materialThemeColorsTrackTintList;
            this.mHasTrackTint = true;
            applyTrackTint();
        }
    }
}
