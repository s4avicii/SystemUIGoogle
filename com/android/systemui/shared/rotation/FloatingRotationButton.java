package com.android.systemui.shared.rotation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import androidx.core.view.OneShotPreDrawListener;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;
import com.android.systemui.shared.rotation.FloatingRotationButtonPositionCalculator;
import com.android.systemui.shared.rotation.RotationButton;
import java.util.Objects;

public final class FloatingRotationButton implements RotationButton {
    public AnimatedVectorDrawable mAnimatedDrawable;
    public final int mButtonDiameterResource;
    public boolean mCanShow = true;
    public int mContainerSize;
    public final int mContentDescriptionResource;
    public final Context mContext;
    public int mDisplayRotation;
    public boolean mIsShowing;
    public boolean mIsTaskbarStashed = false;
    public boolean mIsTaskbarVisible = false;
    public final ViewGroup mKeyButtonContainer;
    public final FloatingRotationButtonView mKeyButtonView;
    public final int mMinMarginResource;
    public FloatingRotationButtonPositionCalculator.Position mPosition;
    public FloatingRotationButtonPositionCalculator mPositionCalculator;
    public RotationButtonController mRotationButtonController;
    public final int mRoundedContentPaddingResource;
    public final int mTaskbarBottomMarginResource;
    public final int mTaskbarLeftMarginResource;
    public RotationButton.RotationButtonUpdatesCallback mUpdatesCallback;
    public final WindowManager mWindowManager;

    public final WindowManager.LayoutParams adjustViewPositionAndCreateLayoutParams() {
        int i = this.mContainerSize;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i, 0, 0, 2024, 8, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("FloatingRotationButton");
        layoutParams.setFitInsetsTypes(0);
        int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
        this.mDisplayRotation = rotation;
        FloatingRotationButtonPositionCalculator.Position calculatePosition = this.mPositionCalculator.calculatePosition(rotation, this.mIsTaskbarVisible, this.mIsTaskbarStashed);
        this.mPosition = calculatePosition;
        Objects.requireNonNull(calculatePosition);
        layoutParams.gravity = calculatePosition.gravity;
        FloatingRotationButtonPositionCalculator.Position position = this.mPosition;
        Objects.requireNonNull(position);
        ((FrameLayout.LayoutParams) this.mKeyButtonView.getLayoutParams()).gravity = position.gravity;
        updateTranslation(this.mPosition, false);
        return layoutParams;
    }

    public final boolean hide() {
        if (!this.mIsShowing) {
            return false;
        }
        this.mWindowManager.removeViewImmediate(this.mKeyButtonContainer);
        this.mIsShowing = false;
        RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback = this.mUpdatesCallback;
        if (rotationButtonUpdatesCallback == null) {
            return true;
        }
        ((NavigationBarView.C09242) rotationButtonUpdatesCallback).onVisibilityChanged(false);
        return true;
    }

    public final void setCanShowRotationButton(boolean z) {
        this.mCanShow = z;
        if (!z) {
            hide();
        }
    }

    public final void setDarkIntensity(float f) {
        boolean z;
        FloatingRotationButtonView floatingRotationButtonView = this.mKeyButtonView;
        Objects.requireNonNull(floatingRotationButtonView);
        KeyButtonRipple keyButtonRipple = floatingRotationButtonView.mRipple;
        Objects.requireNonNull(keyButtonRipple);
        if (f >= 0.5f) {
            z = true;
        } else {
            z = false;
        }
        keyButtonRipple.mDark = z;
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mKeyButtonView.setOnClickListener(onClickListener);
    }

    public final void setOnHoverListener(RotationButtonController$$ExternalSyntheticLambda0 rotationButtonController$$ExternalSyntheticLambda0) {
        this.mKeyButtonView.setOnHoverListener(rotationButtonController$$ExternalSyntheticLambda0);
    }

    public final void setRotationButtonController(RotationButtonController rotationButtonController) {
        this.mRotationButtonController = rotationButtonController;
        Objects.requireNonNull(rotationButtonController);
        int i = rotationButtonController.mLightIconColor;
        RotationButtonController rotationButtonController2 = this.mRotationButtonController;
        Objects.requireNonNull(rotationButtonController2);
        updateIcon(i, rotationButtonController2.mDarkIconColor);
    }

    public final boolean show() {
        if (!this.mCanShow || this.mIsShowing) {
            return false;
        }
        this.mIsShowing = true;
        this.mWindowManager.addView(this.mKeyButtonContainer, adjustViewPositionAndCreateLayoutParams());
        AnimatedVectorDrawable animatedVectorDrawable = this.mAnimatedDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.reset();
            this.mAnimatedDrawable.start();
        }
        OneShotPreDrawListener.add(this.mKeyButtonView, new AccessPoint$$ExternalSyntheticLambda0(this, 6));
        return true;
    }

    public final void updateDimensionResources() {
        Resources resources = this.mContext.getResources();
        int max = Math.max(resources.getDimensionPixelSize(this.mMinMarginResource), resources.getDimensionPixelSize(this.mRoundedContentPaddingResource));
        int dimensionPixelSize = resources.getDimensionPixelSize(this.mTaskbarLeftMarginResource);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(this.mTaskbarBottomMarginResource);
        this.mPositionCalculator = new FloatingRotationButtonPositionCalculator(max, dimensionPixelSize, dimensionPixelSize2);
        this.mContainerSize = Math.max(max, Math.max(dimensionPixelSize, dimensionPixelSize2)) + resources.getDimensionPixelSize(this.mButtonDiameterResource);
    }

    public final void updateIcon(int i, int i2) {
        Context context = this.mKeyButtonView.getContext();
        RotationButtonController rotationButtonController = this.mRotationButtonController;
        Objects.requireNonNull(rotationButtonController);
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) context.getDrawable(rotationButtonController.mIconResId);
        this.mAnimatedDrawable = animatedVectorDrawable;
        this.mKeyButtonView.setImageDrawable(animatedVectorDrawable);
        FloatingRotationButtonView floatingRotationButtonView = this.mKeyButtonView;
        Objects.requireNonNull(floatingRotationButtonView);
        floatingRotationButtonView.getDrawable().setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
        floatingRotationButtonView.mOvalBgPaint.setColor(Color.valueOf((float) Color.red(i2), (float) Color.green(i2), (float) Color.blue(i2), 0.92f).toArgb());
        KeyButtonRipple keyButtonRipple = floatingRotationButtonView.mRipple;
        KeyButtonRipple.Type type = KeyButtonRipple.Type.OVAL;
        Objects.requireNonNull(keyButtonRipple);
        keyButtonRipple.mType = type;
    }

    public FloatingRotationButton(Context context) {
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(C1777R.layout.rotate_suggestion, (ViewGroup) null);
        this.mKeyButtonContainer = viewGroup;
        FloatingRotationButtonView floatingRotationButtonView = (FloatingRotationButtonView) viewGroup.findViewById(C1777R.C1779id.rotate_suggestion);
        this.mKeyButtonView = floatingRotationButtonView;
        floatingRotationButtonView.setVisibility(0);
        floatingRotationButtonView.setContentDescription(context.getString(C1777R.string.accessibility_rotate_button));
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(floatingRotationButtonView.getContext(), floatingRotationButtonView);
        floatingRotationButtonView.mRipple = keyButtonRipple;
        floatingRotationButtonView.setBackground(keyButtonRipple);
        this.mContext = context;
        this.mContentDescriptionResource = C1777R.string.accessibility_rotate_button;
        this.mMinMarginResource = C1777R.dimen.floating_rotation_button_min_margin;
        this.mRoundedContentPaddingResource = C1777R.dimen.rounded_corner_content_padding;
        this.mTaskbarLeftMarginResource = C1777R.dimen.floating_rotation_button_taskbar_left_margin;
        this.mTaskbarBottomMarginResource = C1777R.dimen.floating_rotation_button_taskbar_bottom_margin;
        this.mButtonDiameterResource = C1777R.dimen.floating_rotation_button_diameter;
        updateDimensionResources();
    }

    public final void updateTranslation(FloatingRotationButtonPositionCalculator.Position position, boolean z) {
        Objects.requireNonNull(position);
        int i = position.translationX;
        int i2 = position.translationY;
        if (z) {
            this.mKeyButtonView.animate().translationX((float) i).translationY((float) i2).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).withEndAction(new AccessPoint$$ExternalSyntheticLambda1(this, 3)).start();
            return;
        }
        this.mKeyButtonView.setTranslationX((float) i);
        this.mKeyButtonView.setTranslationY((float) i2);
    }

    public final void setUpdatesCallback(RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback) {
        this.mUpdatesCallback = rotationButtonUpdatesCallback;
    }

    public final View getCurrentView() {
        return this.mKeyButtonView;
    }

    public final Drawable getImageDrawable() {
        return this.mAnimatedDrawable;
    }

    public final boolean isVisible() {
        return this.mIsShowing;
    }
}
