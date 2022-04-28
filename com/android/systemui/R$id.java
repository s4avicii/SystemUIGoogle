package com.android.systemui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.theme.ThemeOverlayApplier;

public final class R$id {
    public static final int[] CollapsingCoordinatorLayout = {C1777R.attr.collapsing_toolbar_title, C1777R.attr.content_frame_height_match_parent};
    public static final int[] SucFooterBarMixin = {C1777R.attr.sucFooterBarButtonAllCaps, C1777R.attr.sucFooterBarButtonColorControlHighlight, C1777R.attr.sucFooterBarButtonColorControlHighlightRipple, C1777R.attr.sucFooterBarButtonCornerRadius, C1777R.attr.sucFooterBarButtonFontFamily, C1777R.attr.sucFooterBarButtonHighlightAlpha, C1777R.attr.sucFooterBarMinHeight, C1777R.attr.sucFooterBarPaddingBottom, C1777R.attr.sucFooterBarPaddingEnd, C1777R.attr.sucFooterBarPaddingStart, C1777R.attr.sucFooterBarPaddingTop, C1777R.attr.sucFooterBarPaddingVertical, C1777R.attr.sucFooterBarPrimaryFooterBackground, C1777R.attr.sucFooterBarPrimaryFooterButton, C1777R.attr.sucFooterBarSecondaryFooterBackground, C1777R.attr.sucFooterBarSecondaryFooterButton};
    public static final int[] SucFooterButton = {16842752, 16843087, C1777R.attr.sucButtonType, C1777R.attr.sucFooterButtonPaddingEnd, C1777R.attr.sucFooterButtonPaddingStart};
    public static final int[] SucPartnerCustomizationLayout = {C1777R.attr.sucFullDynamicColor, C1777R.attr.sucLayoutFullscreen, C1777R.attr.sucUsePartnerResource};
    public static final int[] SucStatusBarMixin = {C1777R.attr.sucLightStatusBar, C1777R.attr.sucStatusBarBackground};
    public static final int[] SucSystemNavBarMixin = {C1777R.attr.sucLightSystemNavBar, C1777R.attr.sucSystemNavBarBackgroundColor, C1777R.attr.sucSystemNavBarDividerColor};
    public static final int[] SucTemplateLayout = {16842994, C1777R.attr.sucContainer};

    public static boolean isDescendant(ViewGroup viewGroup, View view) {
        while (view != null) {
            if (view == viewGroup) {
                return true;
            }
            ViewParent parent = view.getParent();
            if (!(parent instanceof View)) {
                return false;
            }
            view = (View) parent;
        }
        return false;
    }

    public static int getCornerRadiusBottom(Context context) {
        int i;
        int identifier = context.getResources().getIdentifier("config_rounded_mask_size_bottom", "dimen", ThemeOverlayApplier.SYSUI_PACKAGE);
        int i2 = 0;
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        } else {
            i = 0;
        }
        if (i != 0) {
            return i;
        }
        int identifier2 = context.getResources().getIdentifier("config_rounded_mask_size", "dimen", ThemeOverlayApplier.SYSUI_PACKAGE);
        if (identifier2 > 0) {
            i2 = context.getResources().getDimensionPixelSize(identifier2);
        }
        return i2;
    }

    public static int getCornerRadiusTop(Context context) {
        int i;
        int identifier = context.getResources().getIdentifier("config_rounded_mask_size_top", "dimen", ThemeOverlayApplier.SYSUI_PACKAGE);
        int i2 = 0;
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        } else {
            i = 0;
        }
        if (i != 0) {
            return i;
        }
        int identifier2 = context.getResources().getIdentifier("config_rounded_mask_size", "dimen", ThemeOverlayApplier.SYSUI_PACKAGE);
        if (identifier2 > 0) {
            i2 = context.getResources().getDimensionPixelSize(identifier2);
        }
        return i2;
    }

    public static int getHeight(Context context) {
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int rotation = display.getRotation();
        if (rotation == 0 || rotation == 2) {
            return displayMetrics.heightPixels;
        }
        return displayMetrics.widthPixels;
    }

    public static int getWidth(Context context) {
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int rotation = display.getRotation();
        if (rotation == 0 || rotation == 2) {
            return displayMetrics.widthPixels;
        }
        return displayMetrics.heightPixels;
    }
}
