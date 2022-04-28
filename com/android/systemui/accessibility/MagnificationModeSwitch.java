package com.android.systemui.accessibility;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda7;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.p006qs.QSFgsManagerFooter$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0;
import java.util.Objects;

public final class MagnificationModeSwitch implements MagnificationGestureDetector.OnGestureListener, ComponentCallbacks {
    @VisibleForTesting
    public static final int DEFAULT_FADE_OUT_ANIMATION_DELAY_MS = 5000;
    @VisibleForTesting
    public static final long FADING_ANIMATION_DURATION_MS = 300;
    public final AccessibilityManager mAccessibilityManager;
    public final Configuration mConfiguration;
    public final Context mContext;
    @VisibleForTesting
    public final Rect mDraggableWindowBounds = new Rect();
    public final OneHandedController$$ExternalSyntheticLambda1 mFadeInAnimationTask;
    public final BaseWifiTracker$$ExternalSyntheticLambda0 mFadeOutAnimationTask;
    public final MagnificationGestureDetector mGestureDetector;
    public final ImageView mImageView;
    @VisibleForTesting
    public boolean mIsFadeOutAnimating = false;
    public boolean mIsVisible = false;
    public int mMagnificationMode = 0;
    public final WindowManager.LayoutParams mParams;
    public final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    public boolean mSingleTapDetected = false;
    public final SwitchListener mSwitchListener;
    public boolean mToLeftScreenEdge = false;
    public int mUiTimeout;
    public final PipMenuView$$ExternalSyntheticLambda7 mWindowInsetChangeRunnable;
    public final WindowManager mWindowManager;

    public interface SwitchListener {
        void onSwitch(int i, int i2);
    }

    @VisibleForTesting
    public static int getIconResId(int i) {
        return i == 1 ? C1777R.C1778drawable.ic_open_in_new_window : C1777R.C1778drawable.ic_open_in_new_fullscreen;
    }

    public final void onLowMemory() {
    }

    public final void onSingleTap() {
        this.mSingleTapDetected = true;
        handleSingleTap();
    }

    public final Rect getDraggableWindowBounds() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.magnification_switch_button_margin);
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        Rect rect = new Rect(currentWindowMetrics.getBounds());
        rect.offsetTo(0, 0);
        WindowManager.LayoutParams layoutParams = this.mParams;
        rect.inset(0, 0, layoutParams.width, layoutParams.height);
        rect.inset(insetsIgnoringVisibility);
        rect.inset(dimensionPixelSize, dimensionPixelSize);
        return rect;
    }

    public final void moveButton(float f, float f2) {
        this.mSfVsyncFrameProvider.postFrameCallback(new MagnificationModeSwitch$$ExternalSyntheticLambda0(this, f, f2));
    }

    public final void onConfigurationChanged(Configuration configuration) {
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if (diff != 0) {
            if ((diff & 1152) != 0) {
                Rect rect = new Rect(this.mDraggableWindowBounds);
                this.mDraggableWindowBounds.set(getDraggableWindowBounds());
                float height = ((float) (this.mParams.y - rect.top)) / ((float) rect.height());
                this.mParams.y = ((int) (height * ((float) this.mDraggableWindowBounds.height()))) + this.mDraggableWindowBounds.top;
                stickToScreenEdge(this.mToLeftScreenEdge);
            } else if ((diff & 4096) != 0) {
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.magnification_switch_button_size);
                WindowManager.LayoutParams layoutParams = this.mParams;
                layoutParams.height = dimensionPixelSize;
                layoutParams.width = dimensionPixelSize;
                if (this.mIsVisible) {
                    stickToScreenEdge(this.mToLeftScreenEdge);
                    removeButton();
                    showButton(this.mMagnificationMode, false);
                }
            } else if ((diff & 4) != 0) {
                this.mParams.accessibilityTitle = this.mContext.getString(17039658);
                if (this.mIsVisible) {
                    this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
                }
            }
        }
    }

    public final boolean onFinish() {
        boolean z;
        if (this.mIsVisible) {
            if (this.mParams.x < this.mWindowManager.getCurrentWindowMetrics().getBounds().width() / 2) {
                z = true;
            } else {
                z = false;
            }
            this.mToLeftScreenEdge = z;
            stickToScreenEdge(z);
        }
        if (!this.mSingleTapDetected) {
            showButton(this.mMagnificationMode, true);
        }
        this.mSingleTapDetected = false;
        return true;
    }

    public final void removeButton() {
        if (this.mIsVisible) {
            this.mImageView.removeCallbacks(this.mFadeInAnimationTask);
            this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
            this.mImageView.animate().cancel();
            this.mIsFadeOutAnimating = false;
            this.mImageView.setAlpha(0.0f);
            this.mWindowManager.removeView(this.mImageView);
            this.mContext.unregisterComponentCallbacks(this);
            this.mIsVisible = false;
        }
    }

    public final void showButton(int i, boolean z) {
        if (this.mMagnificationMode != i) {
            this.mMagnificationMode = i;
            this.mImageView.setImageResource(getIconResId(i));
        }
        if (!this.mIsVisible) {
            onConfigurationChanged(this.mContext.getResources().getConfiguration());
            this.mContext.registerComponentCallbacks(this);
            if (z) {
                this.mDraggableWindowBounds.set(getDraggableWindowBounds());
                WindowManager.LayoutParams layoutParams = this.mParams;
                Rect rect = this.mDraggableWindowBounds;
                layoutParams.x = rect.right;
                layoutParams.y = rect.bottom;
                this.mToLeftScreenEdge = false;
            }
            this.mWindowManager.addView(this.mImageView, this.mParams);
            this.mImageView.post(new QSFgsManagerFooter$$ExternalSyntheticLambda0(this, 1));
            this.mIsVisible = true;
            this.mImageView.postOnAnimation(this.mFadeInAnimationTask);
            this.mUiTimeout = this.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 5);
        }
        stopFadeOutAnimation();
        this.mImageView.postOnAnimationDelayed(this.mFadeOutAnimationTask, (long) this.mUiTimeout);
    }

    public final void stickToScreenEdge(boolean z) {
        int i;
        WindowManager.LayoutParams layoutParams = this.mParams;
        if (z) {
            i = this.mDraggableWindowBounds.left;
        } else {
            i = this.mDraggableWindowBounds.right;
        }
        layoutParams.x = i;
        updateButtonViewLayoutIfNeeded();
    }

    public final void stopFadeOutAnimation() {
        this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
        if (this.mIsFadeOutAnimating) {
            this.mImageView.animate().cancel();
            this.mImageView.setAlpha(1.0f);
            this.mIsFadeOutAnimating = false;
        }
    }

    public final void updateButtonViewLayoutIfNeeded() {
        if (this.mIsVisible) {
            WindowManager.LayoutParams layoutParams = this.mParams;
            int i = layoutParams.x;
            Rect rect = this.mDraggableWindowBounds;
            layoutParams.x = MathUtils.constrain(i, rect.left, rect.right);
            WindowManager.LayoutParams layoutParams2 = this.mParams;
            int i2 = layoutParams2.y;
            Rect rect2 = this.mDraggableWindowBounds;
            layoutParams2.y = MathUtils.constrain(i2, rect2.top, rect2.bottom);
            this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
        }
    }

    @VisibleForTesting
    public MagnificationModeSwitch(Context context, ImageView imageView, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, SwitchListener switchListener) {
        this.mContext = context;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mSwitchListener = switchListener;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(C1777R.dimen.magnification_switch_button_size);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, dimensionPixelSize, 2039, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.accessibilityTitle = context.getString(17039658);
        layoutParams.layoutInDisplayCutoutMode = 3;
        this.mParams = layoutParams;
        this.mImageView = imageView;
        imageView.setOnTouchListener(new MagnificationModeSwitch$$ExternalSyntheticLambda2(this));
        imageView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0062 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0063  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final boolean performAccessibilityAction(android.view.View r5, int r6, android.os.Bundle r7) {
                /*
                    r4 = this;
                    com.android.systemui.accessibility.MagnificationModeSwitch r0 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    android.view.WindowManager r0 = r0.mWindowManager
                    android.view.WindowMetrics r0 = r0.getCurrentWindowMetrics()
                    android.graphics.Rect r0 = r0.getBounds()
                    android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r1 = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK
                    int r1 = r1.getId()
                    r2 = 1
                    if (r6 != r1) goto L_0x001b
                    com.android.systemui.accessibility.MagnificationModeSwitch r0 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    r0.handleSingleTap()
                    goto L_0x005d
                L_0x001b:
                    r1 = 2131427363(0x7f0b0023, float:1.847634E38)
                    r3 = 0
                    if (r6 != r1) goto L_0x002d
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.height()
                    int r0 = -r0
                    float r0 = (float) r0
                    r1.moveButton(r3, r0)
                    goto L_0x005d
                L_0x002d:
                    r1 = 2131427360(0x7f0b0020, float:1.8476334E38)
                    if (r6 != r1) goto L_0x003d
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.height()
                    float r0 = (float) r0
                    r1.moveButton(r3, r0)
                    goto L_0x005d
                L_0x003d:
                    r1 = 2131427361(0x7f0b0021, float:1.8476336E38)
                    if (r6 != r1) goto L_0x004e
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.width()
                    int r0 = -r0
                    float r0 = (float) r0
                    r1.moveButton(r0, r3)
                    goto L_0x005d
                L_0x004e:
                    r1 = 2131427362(0x7f0b0022, float:1.8476338E38)
                    if (r6 != r1) goto L_0x005f
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.width()
                    float r0 = (float) r0
                    r1.moveButton(r0, r3)
                L_0x005d:
                    r0 = r2
                    goto L_0x0060
                L_0x005f:
                    r0 = 0
                L_0x0060:
                    if (r0 == 0) goto L_0x0063
                    return r2
                L_0x0063:
                    boolean r4 = super.performAccessibilityAction(r5, r6, r7)
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.MagnificationModeSwitch.C06531.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
            }

            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                int i;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                MagnificationModeSwitch magnificationModeSwitch = MagnificationModeSwitch.this;
                Objects.requireNonNull(magnificationModeSwitch);
                if (magnificationModeSwitch.mMagnificationMode == 2) {
                    i = C1777R.string.magnification_mode_switch_state_window;
                } else {
                    i = C1777R.string.magnification_mode_switch_state_full_screen;
                }
                accessibilityNodeInfo.setStateDescription(magnificationModeSwitch.mContext.getResources().getString(i));
                accessibilityNodeInfo.setContentDescription(MagnificationModeSwitch.this.mContext.getResources().getString(C1777R.string.magnification_mode_switch_description));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), MagnificationModeSwitch.this.mContext.getResources().getString(C1777R.string.magnification_mode_switch_click_label)));
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.accessibility_action_move_up, MagnificationModeSwitch.this.mContext.getString(C1777R.string.accessibility_control_move_up)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.accessibility_action_move_down, MagnificationModeSwitch.this.mContext.getString(C1777R.string.accessibility_control_move_down)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.accessibility_action_move_left, MagnificationModeSwitch.this.mContext.getString(C1777R.string.accessibility_control_move_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.accessibility_action_move_right, MagnificationModeSwitch.this.mContext.getString(C1777R.string.accessibility_control_move_right)));
            }
        });
        this.mWindowInsetChangeRunnable = new PipMenuView$$ExternalSyntheticLambda7(this, 1);
        imageView.setOnApplyWindowInsetsListener(new MagnificationModeSwitch$$ExternalSyntheticLambda1(this));
        this.mFadeInAnimationTask = new OneHandedController$$ExternalSyntheticLambda1(this, 1);
        this.mFadeOutAnimationTask = new BaseWifiTracker$$ExternalSyntheticLambda0(this, 1);
        this.mGestureDetector = new MagnificationGestureDetector(context, context.getMainThreadHandler(), this);
    }

    public final void handleSingleTap() {
        removeButton();
        int i = this.mMagnificationMode ^ 3;
        this.mMagnificationMode = i;
        this.mImageView.setImageResource(getIconResId(i));
        this.mSwitchListener.onSwitch(this.mContext.getDisplayId(), i);
    }

    public final void onDrag(float f, float f2) {
        moveButton(f, f2);
    }

    public final void onStart() {
        stopFadeOutAnimation();
    }
}
