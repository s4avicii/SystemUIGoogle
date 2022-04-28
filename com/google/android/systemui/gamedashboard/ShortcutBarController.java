package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.android.internal.util.ScreenshotHelper;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Objects;
import java.util.Optional;

public final class ShortcutBarController {
    public EntryPointController mEntryPointController;
    public final FpsController mFpsController;
    public boolean mIsAttached;
    public final ToastController mToast;
    public final GameDashboardUiEventLogger mUiEventLogger;
    public final ShortcutBarView mView;
    public final WindowManager mWindowManager;

    public final void hideUI() {
        if (this.mIsAttached) {
            this.mWindowManager.removeViewImmediate(this.mView);
            this.mIsAttached = false;
        }
        ToastController toastController = this.mToast;
        Objects.requireNonNull(toastController);
        toastController.mLaunchLayout.setVisibility(8);
        toastController.mShortcutView.setVisibility(8);
        toastController.mRecordSaveView.setVisibility(8);
        toastController.removeViewImmediate();
    }

    public final void onButtonVisibilityChange(boolean z) {
        if (z && !this.mView.isAttachedToWindow() && this.mView.shouldBeVisible()) {
            show();
        } else if (!z && this.mView.isAttachedToWindow() && !this.mView.shouldBeVisible()) {
            hideUI();
        }
    }

    public final void registerFps(int i) {
        FpsController fpsController = this.mFpsController;
        Objects.requireNonNull(fpsController);
        fpsController.mWindowManager.unregisterTaskFpsCallback(fpsController.mListener);
        if (fpsController.mCallback != null) {
            fpsController.mCallback = null;
        }
        FpsController fpsController2 = this.mFpsController;
        ShortcutBarView shortcutBarView = this.mView;
        Objects.requireNonNull(shortcutBarView);
        StatusBar$$ExternalSyntheticLambda4 statusBar$$ExternalSyntheticLambda4 = new StatusBar$$ExternalSyntheticLambda4(shortcutBarView);
        Objects.requireNonNull(fpsController2);
        fpsController2.mCallback = statusBar$$ExternalSyntheticLambda4;
        fpsController2.mWindowManager.registerTaskFpsCallback(i, fpsController2.mExecutor, fpsController2.mListener);
    }

    public final void show() {
        if (!this.mIsAttached) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 264, -3);
            layoutParams.setTrustedOverlay();
            layoutParams.setFitInsetsTypes(0);
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.privateFlags |= 16;
            layoutParams.setTitle("Shortcut Bar");
            this.mWindowManager.addView(this.mView, layoutParams);
            this.mView.slideIn();
            this.mIsAttached = true;
        }
    }

    public final void updateVisibility(boolean z) {
        if (z && !this.mView.isAttachedToWindow() && this.mView.shouldBeVisible()) {
            show();
        } else if (!z && this.mView.isAttachedToWindow()) {
            hideUI();
        }
    }

    public ShortcutBarController(Context context, WindowManager windowManager, FpsController fpsController, ConfigurationController configurationController, Handler handler, ScreenRecordController screenRecordController, Optional<TaskSurfaceHelper> optional, GameDashboardUiEventLogger gameDashboardUiEventLogger, ToastController toastController) {
        this.mWindowManager = windowManager;
        this.mFpsController = fpsController;
        this.mToast = toastController;
        this.mUiEventLogger = gameDashboardUiEventLogger;
        int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
        ShortcutBarView shortcutBarView = (ShortcutBarView) LayoutInflater.from(context).inflate(C1777R.layout.game_dashboard_shortcut_bar, (ViewGroup) null);
        Objects.requireNonNull(shortcutBarView);
        shortcutBarView.mScreenRecordController = screenRecordController;
        shortcutBarView.mConfigurationController = configurationController;
        shortcutBarView.mUiEventLogger = gameDashboardUiEventLogger;
        ScreenshotHelper screenshotHelper = new ScreenshotHelper(shortcutBarView.getContext());
        shortcutBarView.mScreenshotButton.setOnTouchListener(shortcutBarView.mOnTouchListener);
        shortcutBarView.mScreenshotButton.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda2(shortcutBarView, optional, this, screenshotHelper, handler));
        shortcutBarView.mRecordButton.setOnTouchListener(shortcutBarView.mOnTouchListener);
        shortcutBarView.mRecordButton.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda0(shortcutBarView, 0));
        shortcutBarView.setOnTouchListener(shortcutBarView.mOnTouchListener);
        shortcutBarView.mRevealButton.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda1(shortcutBarView, 0));
        shortcutBarView.mRevealButton.setOnTouchListener(shortcutBarView.mRevealButtonOnTouchListener);
        RevealButton revealButton = shortcutBarView.mRevealButton;
        Objects.requireNonNull(revealButton);
        revealButton.mRightSide = true;
        revealButton.invalidate();
        shortcutBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public final void onGlobalLayout() {
                ShortcutBarView shortcutBarView = ShortcutBarView.this;
                shortcutBarView.mRevealButton.setTranslationY((((float) shortcutBarView.getHeight()) * 1.0f) / 3.0f);
                ShortcutBarView.this.slideIn();
                int dimensionPixelSize = ShortcutBarView.this.getResources().getDimensionPixelSize(C1777R.dimen.game_dashboard_shortcut_bar_stashed_horizontal_padding);
                int dimensionPixelSize2 = ShortcutBarView.this.getResources().getDimensionPixelSize(C1777R.dimen.game_dashboard_shortcut_bar_stashed_vertical_padding);
                Rect rect = new Rect();
                ShortcutBarView.this.mRevealButton.getHitRect(rect);
                rect.top -= dimensionPixelSize2;
                rect.bottom += dimensionPixelSize2;
                rect.left -= dimensionPixelSize;
                rect.right += dimensionPixelSize;
                ((View) ShortcutBarView.this.mRevealButton.getParent()).setTouchDelegate(new TouchDelegate(rect, ShortcutBarView.this.mRevealButton));
                ShortcutBarView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        this.mView = shortcutBarView;
    }
}
