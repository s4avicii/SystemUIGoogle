package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class DockTooltipView extends BaseTooltipView {
    public final AccessibilityFloatingMenuView mAnchorView;

    public final void show() {
        float f;
        if (!this.mIsShowing) {
            this.mIsShowing = true;
            updateTooltipView();
            this.mWindowManager.addView(this, this.mCurrentLayoutParams);
        }
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.mAnchorView;
        Objects.requireNonNull(accessibilityFloatingMenuView);
        accessibilityFloatingMenuView.fadeIn();
        if (accessibilityFloatingMenuView.mAlignment == 1) {
            f = 0.5f;
        } else {
            f = -0.5f;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, f, 1, 0.0f, 1, 0.0f);
        translateAnimation.setDuration(600);
        translateAnimation.setRepeatMode(2);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setStartOffset(600);
        accessibilityFloatingMenuView.mListView.startAnimation(translateAnimation);
    }

    public DockTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context, accessibilityFloatingMenuView);
        this.mAnchorView = accessibilityFloatingMenuView;
        this.mTextView.setText(getContext().getText(C1777R.string.accessibility_floating_button_docking_tooltip));
    }

    public final void hide() {
        super.hide();
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.mAnchorView;
        Objects.requireNonNull(accessibilityFloatingMenuView);
        accessibilityFloatingMenuView.mListView.clearAnimation();
        accessibilityFloatingMenuView.fadeOut();
    }
}
