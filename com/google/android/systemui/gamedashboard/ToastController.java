package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.leanback.R$color;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.policy.ConfigurationController;

public final class ToastController implements ConfigurationController.ConfigurationListener, NavigationModeController.ModeChangedListener {
    public final Context mContext;
    public int mFadeInAnimationDuration;
    public int mFadeOutAnimationDuration;
    public int mGameTaskId;
    public int mIsAddedToWindowManager = 0;
    public View mLaunchContainer;
    public TextView mLaunchDndMessageView;
    public TextView mLaunchGameModeMessageView;
    public View mLaunchLayout;
    public int mNavBarMode;
    public int mOrientationMargin;
    public View mRecordSaveContainer;
    public TextView mRecordSaveView;
    public TextView mShortcutView;
    public int mTranslateDownAnimationDuration;
    public int mTranslateUpAnimationDuration;
    public final UiEventLogger mUiEventLogger;
    public final WindowManager mWindowManager;

    public final int getMargin() {
        int i;
        int i2 = this.mContext.getResources().getConfiguration().orientation;
        int i3 = this.mOrientationMargin;
        if (i2 != 1) {
            return i3;
        }
        if (R$color.isGesturalMode(this.mNavBarMode)) {
            i = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.navigation_bar_gesture_height);
        } else {
            i = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.navigation_bar_height);
        }
        return i3 + i;
    }

    public final boolean hide() {
        int i = this.mIsAddedToWindowManager;
        if (i == 0) {
            return false;
        }
        if (i == 1) {
            this.mLaunchLayout.animate().translationY(500.0f).setDuration((long) this.mTranslateDownAnimationDuration).setStartDelay(3000).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ToastController.this.mLaunchLayout.setVisibility(8);
                    ToastController.this.removeViewImmediate();
                    ToastController.this.mLaunchLayout.animate().setListener((Animator.AnimatorListener) null);
                }
            });
        } else if (i == 2) {
            this.mShortcutView.animate().alpha(0.0f).setDuration((long) this.mFadeOutAnimationDuration).setStartDelay(1000).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ToastController.this.mShortcutView.setVisibility(8);
                    ToastController.this.removeViewImmediate();
                    ToastController.this.mShortcutView.animate().setListener((Animator.AnimatorListener) null);
                }
            });
        } else if (i == 3) {
            this.mRecordSaveView.animate().translationY(500.0f).setDuration((long) this.mTranslateDownAnimationDuration).setStartDelay(3000).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ToastController.this.mRecordSaveView.setVisibility(8);
                    ToastController.this.removeViewImmediate();
                    ToastController.this.mRecordSaveView.animate().setListener((Animator.AnimatorListener) null);
                }
            });
        }
        return true;
    }

    public final void removeViewImmediate() {
        this.mLaunchLayout.animate().cancel();
        this.mShortcutView.animate().cancel();
        this.mRecordSaveView.animate().cancel();
        int i = this.mIsAddedToWindowManager;
        if (i == 1) {
            this.mWindowManager.removeViewImmediate(this.mLaunchContainer);
        } else if (i == 2) {
            this.mWindowManager.removeViewImmediate(this.mShortcutView);
        } else if (i == 3) {
            this.mWindowManager.removeViewImmediate(this.mRecordSaveContainer);
        }
        this.mIsAddedToWindowManager = 0;
    }

    public final void setResourceValues() {
        Resources resources = this.mContext.getResources();
        if (this.mIsAddedToWindowManager != 1) {
            View inflate = LayoutInflater.from(this.mContext).inflate(C1777R.layout.game_launch_toast, (ViewGroup) null);
            this.mLaunchContainer = inflate;
            View findViewById = inflate.findViewById(C1777R.C1779id.game_launch_toast);
            this.mLaunchLayout = findViewById;
            this.mLaunchDndMessageView = (TextView) findViewById.findViewById(C1777R.C1779id.game_launch_toast_dnd_message);
            this.mLaunchGameModeMessageView = (TextView) this.mLaunchLayout.findViewById(C1777R.C1779id.game_launch_toast_game_mode_message);
            ((TextView) this.mLaunchLayout.findViewById(C1777R.C1779id.game_launch_toast_change)).setOnClickListener(new ToastController$$ExternalSyntheticLambda0(this));
        }
        if (this.mIsAddedToWindowManager != 2) {
            this.mShortcutView = (TextView) LayoutInflater.from(this.mContext).inflate(C1777R.layout.game_menu_shortcut_toast, (ViewGroup) null);
        }
        if (this.mIsAddedToWindowManager != 3) {
            View inflate2 = LayoutInflater.from(this.mContext).inflate(C1777R.layout.game_screen_record_save_toast, (ViewGroup) null);
            this.mRecordSaveContainer = inflate2;
            this.mRecordSaveView = (TextView) inflate2.findViewById(C1777R.C1779id.game_screen_record_save_toast);
        }
        this.mFadeInAnimationDuration = resources.getInteger(C1777R.integer.game_toast_fade_in_animation_duration);
        this.mFadeOutAnimationDuration = resources.getInteger(C1777R.integer.game_toast_fade_out_animation_duration);
        this.mTranslateUpAnimationDuration = resources.getInteger(C1777R.integer.game_toast_translate_up_animation_duration);
        this.mTranslateDownAnimationDuration = resources.getInteger(C1777R.integer.game_toast_translate_down_animation_duration);
        this.mOrientationMargin = resources.getDimensionPixelSize(C1777R.dimen.game_toast_margin);
    }

    public final void show(WindowManager.LayoutParams layoutParams, int i) {
        if (i == 1) {
            this.mLaunchLayout.setTranslationY(500.0f);
            this.mWindowManager.addView(this.mLaunchContainer, layoutParams);
            this.mIsAddedToWindowManager = 1;
            this.mLaunchLayout.setVisibility(0);
            this.mLaunchLayout.animate().translationY(0.0f).setDuration((long) this.mTranslateUpAnimationDuration).setStartDelay(1000).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ToastController.this.mLaunchLayout.animate().setListener((Animator.AnimatorListener) null);
                    ToastController.this.hide();
                }
            });
        } else if (i == 2) {
            this.mShortcutView.setAlpha(0.0f);
            this.mWindowManager.addView(this.mShortcutView, layoutParams);
            this.mIsAddedToWindowManager = 2;
            this.mShortcutView.setVisibility(0);
            this.mShortcutView.animate().alpha(1.0f).setDuration((long) this.mFadeInAnimationDuration).setStartDelay(0).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ToastController.this.mShortcutView.animate().setListener((Animator.AnimatorListener) null);
                    ToastController.this.hide();
                }
            });
        } else if (i == 3) {
            this.mRecordSaveView.setTranslationY(500.0f);
            this.mWindowManager.addView(this.mRecordSaveContainer, layoutParams);
            this.mIsAddedToWindowManager = 3;
            this.mRecordSaveView.setVisibility(0);
            this.mRecordSaveView.animate().translationY(0.0f).setDuration((long) this.mTranslateUpAnimationDuration).setStartDelay(1000).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ToastController.this.mRecordSaveView.animate().setListener((Animator.AnimatorListener) null);
                    ToastController.this.hide();
                }
            });
        }
    }

    public final void showShortcutText(int i) {
        String str = (String) this.mContext.getResources().getText(i);
        this.mShortcutView.setText(str);
        int margin = getMargin();
        removeViewImmediate();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 0, margin, 2024, 8, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setTitle("ToastText");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 80;
        show(layoutParams, 2);
        this.mShortcutView.announceForAccessibility(str);
    }

    public ToastController(Context context, ConfigurationController configurationController, WindowManager windowManager, UiEventLogger uiEventLogger, NavigationModeController navigationModeController) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mUiEventLogger = uiEventLogger;
        this.mNavBarMode = navigationModeController.addListener(this);
        this.mGameTaskId = 0;
        configurationController.addCallback(this);
        setResourceValues();
    }

    public final void onConfigChanged(Configuration configuration) {
        setResourceValues();
    }

    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }
}
