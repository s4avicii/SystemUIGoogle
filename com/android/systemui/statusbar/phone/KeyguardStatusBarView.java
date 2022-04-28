package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.util.Utils;
import java.util.ArrayList;
import java.util.Objects;

public class KeyguardStatusBarView extends RelativeLayout {
    public boolean mBatteryCharging;
    public BatteryMeterView mBatteryView;
    public TextView mCarrierLabel;
    public final Rect mClipRect = new Rect(0, 0, 0, 0);
    public int mCutoutSideNudge = 0;
    public View mCutoutSpace;
    public DisplayCutout mDisplayCutout;
    public final ArrayList<Rect> mEmptyTintRect = new ArrayList<>();
    public boolean mIsPrivacyDotEnabled;
    public boolean mIsUserSwitcherEnabled;
    public boolean mKeyguardUserAvatarEnabled;
    public boolean mKeyguardUserSwitcherEnabled;
    public int mLayoutState = 0;
    public int mMinDotWidth;
    public ImageView mMultiUserAvatar;
    public Pair<Integer, Integer> mPadding = new Pair<>(0, 0);
    public boolean mShowPercentAvailable;
    public int mStatusBarPaddingEnd;
    public ViewGroup mStatusIconArea;
    public StatusIconContainer mStatusIconContainer;
    public View mSystemIconsContainer;
    public int mSystemIconsSwitcherHiddenExpandedMargin;
    public int mTopClipping;
    public ViewGroup mUserSwitcherContainer;

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final WindowInsets updateWindowInsets(WindowInsets windowInsets, StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        int i;
        int i2;
        int i3;
        boolean z = false;
        this.mLayoutState = 0;
        this.mDisplayCutout = getRootWindowInsets().getDisplayCutout();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = Utils.getStatusBarHeaderHeightKeyguard(this.mContext);
        setLayoutParams(marginLayoutParams);
        DisplayCutout displayCutout = this.mDisplayCutout;
        if (displayCutout == null) {
            i = 0;
        } else {
            i = displayCutout.getWaterfallInsets().top;
        }
        this.mPadding = statusBarContentInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        if (!isLayoutRtl() || !this.mIsPrivacyDotEnabled) {
            i2 = ((Integer) this.mPadding.first).intValue();
        } else {
            i2 = Math.max(this.mMinDotWidth, ((Integer) this.mPadding.first).intValue());
        }
        if (isLayoutRtl() || !this.mIsPrivacyDotEnabled) {
            i3 = ((Integer) this.mPadding.second).intValue();
        } else {
            i3 = Math.max(this.mMinDotWidth, ((Integer) this.mPadding.second).intValue());
        }
        setPadding(i2, i, i3, 0);
        if (this.mDisplayCutout == null || statusBarContentInsetsProvider.currentRotationHasCornerCutout()) {
            z = updateLayoutParamsNoCutout();
        } else if (this.mLayoutState != 1) {
            this.mLayoutState = 1;
            if (this.mCutoutSpace == null) {
                updateLayoutParamsNoCutout();
            }
            Rect rect = new Rect();
            ScreenDecorations.DisplayCutoutView.boundsFromDirection(this.mDisplayCutout, 48, rect);
            this.mCutoutSpace.setVisibility(0);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mCutoutSpace.getLayoutParams();
            int i4 = rect.left;
            int i5 = this.mCutoutSideNudge;
            rect.left = i4 + i5;
            rect.right -= i5;
            layoutParams.width = rect.width();
            layoutParams.height = rect.height();
            layoutParams.addRule(13);
            ((RelativeLayout.LayoutParams) this.mCarrierLabel.getLayoutParams()).addRule(16, C1777R.C1779id.cutout_space_view);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mStatusIconArea.getLayoutParams();
            layoutParams2.addRule(1, C1777R.C1779id.cutout_space_view);
            layoutParams2.width = -1;
            ((LinearLayout.LayoutParams) this.mSystemIconsContainer.getLayoutParams()).setMarginStart(0);
            z = true;
        }
        if (z) {
            requestLayout();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void onOverlayChanged() {
        int themeAttr = com.android.settingslib.Utils.getThemeAttr(this.mContext, 16842818);
        this.mCarrierLabel.setTextAppearance(themeAttr);
        BatteryMeterView batteryMeterView = this.mBatteryView;
        Objects.requireNonNull(batteryMeterView);
        TextView textView = batteryMeterView.mBatteryPercentView;
        if (textView != null) {
            batteryMeterView.removeView(textView);
            batteryMeterView.mBatteryPercentView = null;
        }
        batteryMeterView.updateShowPercent();
        TextView textView2 = (TextView) this.mUserSwitcherContainer.findViewById(C1777R.C1779id.current_user_name);
        if (textView2 != null) {
            textView2.setTextAppearance(themeAttr);
        }
    }

    public final void onThemeChanged(StatusBarIconController.TintedIconManager tintedIconManager) {
        float f;
        BatteryMeterView batteryMeterView = this.mBatteryView;
        Context context = this.mContext;
        if (context == null) {
            Objects.requireNonNull(batteryMeterView);
        } else {
            batteryMeterView.mDualToneHandler.setColorsFromContext(context);
        }
        int colorAttrDefaultColor = com.android.settingslib.Utils.getColorAttrDefaultColor(this.mContext, C1777R.attr.wallpaperTextColor);
        int colorStateListDefaultColor = com.android.settingslib.Utils.getColorStateListDefaultColor(this.mContext, ((double) Color.luminance(colorAttrDefaultColor)) < 0.5d ? C1777R.color.dark_mode_icon_color_single_tone : C1777R.color.light_mode_icon_color_single_tone);
        if (colorAttrDefaultColor == -1) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        this.mCarrierLabel.setTextColor(colorStateListDefaultColor);
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(C1777R.C1779id.current_user_name);
        if (textView != null) {
            textView.setTextColor(com.android.settingslib.Utils.getColorStateListDefaultColor(this.mContext, C1777R.color.light_mode_icon_color_single_tone));
        }
        if (tintedIconManager != null) {
            tintedIconManager.setTint(colorStateListDefaultColor);
        }
        ArrayList<Rect> arrayList = this.mEmptyTintRect;
        View findViewById = findViewById(C1777R.C1779id.battery);
        if (findViewById instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) findViewById).onDarkChanged(arrayList, f, colorStateListDefaultColor);
        }
        ArrayList<Rect> arrayList2 = this.mEmptyTintRect;
        View findViewById2 = findViewById(C1777R.C1779id.clock);
        if (findViewById2 instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) findViewById2).onDarkChanged(arrayList2, f, colorStateListDefaultColor);
        }
    }

    public final boolean updateLayoutParamsNoCutout() {
        if (this.mLayoutState == 2) {
            return false;
        }
        this.mLayoutState = 2;
        View view = this.mCutoutSpace;
        if (view != null) {
            view.setVisibility(8);
        }
        ((RelativeLayout.LayoutParams) this.mCarrierLabel.getLayoutParams()).addRule(16, C1777R.C1779id.status_icon_area);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mStatusIconArea.getLayoutParams();
        layoutParams.removeRule(1);
        layoutParams.width = -2;
        ((LinearLayout.LayoutParams) this.mSystemIconsContainer.getLayoutParams()).setMarginStart(getResources().getDimensionPixelSize(C1777R.dimen.system_icons_super_container_margin_start));
        return true;
    }

    public final void updateSystemIconsLayoutParams() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mSystemIconsContainer.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.system_icons_super_container_margin_start);
        int i = this.mStatusBarPaddingEnd;
        if (this.mKeyguardUserSwitcherEnabled) {
            i = this.mSystemIconsSwitcherHiddenExpandedMargin;
        }
        if (i != layoutParams.getMarginEnd() || dimensionPixelSize != layoutParams.getMarginStart()) {
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(i);
            this.mSystemIconsContainer.setLayoutParams(layoutParams);
        }
    }

    public final void updateVisibilities() {
        if (!this.mKeyguardUserAvatarEnabled) {
            ViewParent parent = this.mMultiUserAvatar.getParent();
            ViewGroup viewGroup = this.mStatusIconArea;
            if (parent == viewGroup) {
                viewGroup.removeView(this.mMultiUserAvatar);
            } else if (this.mMultiUserAvatar.getParent() != null) {
                getOverlay().remove(this.mMultiUserAvatar);
            }
        } else {
            int i = 0;
            if (this.mMultiUserAvatar.getParent() == this.mStatusIconArea || this.mKeyguardUserSwitcherEnabled) {
                ViewParent parent2 = this.mMultiUserAvatar.getParent();
                ViewGroup viewGroup2 = this.mStatusIconArea;
                if (parent2 == viewGroup2 && this.mKeyguardUserSwitcherEnabled) {
                    viewGroup2.removeView(this.mMultiUserAvatar);
                }
            } else {
                if (this.mMultiUserAvatar.getParent() != null) {
                    getOverlay().remove(this.mMultiUserAvatar);
                }
                this.mStatusIconArea.addView(this.mMultiUserAvatar, 0);
            }
            if (!this.mKeyguardUserSwitcherEnabled) {
                if (this.mIsUserSwitcherEnabled) {
                    this.mMultiUserAvatar.setVisibility(0);
                } else {
                    this.mMultiUserAvatar.setVisibility(8);
                }
            }
            BatteryMeterView batteryMeterView = this.mBatteryView;
            if (this.mBatteryCharging && this.mShowPercentAvailable) {
                i = 1;
            }
            Objects.requireNonNull(batteryMeterView);
            batteryMeterView.setPercentShowMode(i);
        }
    }

    public KeyguardStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void loadDimens() {
        Resources resources = getResources();
        this.mSystemIconsSwitcherHiddenExpandedMargin = resources.getDimensionPixelSize(C1777R.dimen.system_icons_switcher_hidden_expanded_margin);
        this.mStatusBarPaddingEnd = resources.getDimensionPixelSize(C1777R.dimen.status_bar_padding_end);
        this.mMinDotWidth = resources.getDimensionPixelSize(C1777R.dimen.ongoing_appops_dot_min_padding);
        this.mCutoutSideNudge = getResources().getDimensionPixelSize(C1777R.dimen.display_cutout_margin_consumption);
        this.mShowPercentAvailable = getContext().getResources().getBoolean(17891383);
        resources.getDimensionPixelSize(C1777R.dimen.rounded_corner_content_padding);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        loadDimens();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mMultiUserAvatar.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.multi_user_avatar_keyguard_size);
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        this.mMultiUserAvatar.setLayoutParams(marginLayoutParams);
        updateSystemIconsLayoutParams();
        ViewGroup viewGroup = this.mStatusIconArea;
        viewGroup.setPaddingRelative(viewGroup.getPaddingStart(), getResources().getDimensionPixelSize(C1777R.dimen.status_bar_padding_top), this.mStatusIconArea.getPaddingEnd(), this.mStatusIconArea.getPaddingBottom());
        StatusIconContainer statusIconContainer = this.mStatusIconContainer;
        statusIconContainer.setPaddingRelative(statusIconContainer.getPaddingStart(), this.mStatusIconContainer.getPaddingTop(), getResources().getDimensionPixelSize(C1777R.dimen.signal_cluster_battery_padding), this.mStatusIconContainer.getPaddingBottom());
        int i = 0;
        this.mCarrierLabel.setTextSize(0, (float) getResources().getDimensionPixelSize(17105581));
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mCarrierLabel.getLayoutParams();
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_carrier_text_margin);
        int intValue = ((Integer) this.mPadding.first).intValue();
        if (intValue < dimensionPixelSize2) {
            i = dimensionPixelSize2 - intValue;
        }
        marginLayoutParams2.setMarginStart(i);
        this.mCarrierLabel.setLayoutParams(marginLayoutParams2);
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams3.height = Utils.getStatusBarHeaderHeightKeyguard(this.mContext);
        setLayoutParams(marginLayoutParams3);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSystemIconsContainer = findViewById(C1777R.C1779id.system_icons_container);
        this.mMultiUserAvatar = (ImageView) findViewById(C1777R.C1779id.multi_user_avatar);
        this.mCarrierLabel = (TextView) findViewById(C1777R.C1779id.keyguard_carrier_text);
        this.mBatteryView = (BatteryMeterView) this.mSystemIconsContainer.findViewById(C1777R.C1779id.battery);
        this.mCutoutSpace = findViewById(C1777R.C1779id.cutout_space_view);
        this.mStatusIconArea = (ViewGroup) findViewById(C1777R.C1779id.status_icon_area);
        this.mStatusIconContainer = (StatusIconContainer) findViewById(C1777R.C1779id.statusIcons);
        this.mUserSwitcherContainer = (ViewGroup) findViewById(C1777R.C1779id.user_switcher_container);
        this.mIsPrivacyDotEnabled = this.mContext.getResources().getBoolean(C1777R.bool.config_enablePrivacyDot);
        loadDimens();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mClipRect.set(0, this.mTopClipping, getWidth(), getHeight());
        setClipBounds(this.mClipRect);
    }

    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 0) {
            this.mSystemIconsContainer.animate().cancel();
            this.mSystemIconsContainer.setTranslationX(0.0f);
            this.mMultiUserAvatar.animate().cancel();
            this.mMultiUserAvatar.setAlpha(1.0f);
            return;
        }
        updateVisibilities();
        updateSystemIconsLayoutParams();
    }

    public boolean isKeyguardUserAvatarEnabled() {
        return this.mKeyguardUserAvatarEnabled;
    }
}
