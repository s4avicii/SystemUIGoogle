package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.preference.R$id;
import com.android.internal.policy.SystemBarUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import java.util.Objects;

public class PhoneStatusBarView extends FrameLayout {
    public DarkIconDispatcher.DarkReceiver mBattery;
    public DarkIconDispatcher.DarkReceiver mClock;
    public final StatusBarContentInsetsProvider mContentInsetsProvider = ((StatusBarContentInsetsProvider) Dependency.get(StatusBarContentInsetsProvider.class));
    public int mCutoutSideNudge = 0;
    public View mCutoutSpace;
    public DisplayCutout mDisplayCutout;
    public int mRotationOrientation = -1;
    public TouchEventHandler mTouchEventHandler;

    public interface TouchEventHandler {
    }

    public final void onAttachedToWindow() {
        Class cls = DarkIconDispatcher.class;
        super.onAttachedToWindow();
        ((DarkIconDispatcher) Dependency.get(cls)).addDarkReceiver(this.mBattery);
        ((DarkIconDispatcher) Dependency.get(cls)).addDarkReceiver(this.mClock);
        if (updateOrientationAndCutout()) {
            updateLayoutForCutout();
        }
    }

    public final void onDetachedFromWindow() {
        Class cls = DarkIconDispatcher.class;
        super.onDetachedFromWindow();
        ((DarkIconDispatcher) Dependency.get(cls)).removeDarkReceiver(this.mBattery);
        ((DarkIconDispatcher) Dependency.get(cls)).removeDarkReceiver(this.mClock);
        this.mDisplayCutout = null;
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationPanelViewController.C148318 r0 = (NotificationPanelViewController.C148318) this.mTouchEventHandler;
        Objects.requireNonNull(r0);
        NotificationPanelViewController.this.mStatusBar.onTouchEvent(motionEvent);
        return super.onInterceptTouchEvent(motionEvent);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        TouchEventHandler touchEventHandler = this.mTouchEventHandler;
        if (touchEventHandler == null) {
            Log.w("PhoneStatusBarView", String.format("onTouch: No touch handler provided; eating gesture at (%d,%d)", new Object[]{Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY())}));
            return true;
        }
        NotificationPanelViewController.C148318 r5 = (NotificationPanelViewController.C148318) touchEventHandler;
        Objects.requireNonNull(r5);
        NotificationPanelViewController.this.mStatusBar.onTouchEvent(motionEvent);
        if (!NotificationPanelViewController.this.mCommandQueue.panelsEnabled()) {
            if (motionEvent.getAction() != 0) {
                return false;
            }
            int i = PanelViewController.$r8$clinit;
            Log.v("PanelView", String.format("onTouchForwardedFromStatusBar: panel disabled, ignoring touch at (%d,%d)", new Object[]{Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY())}));
            return false;
        } else if (motionEvent.getAction() != 0 || NotificationPanelViewController.this.mView.isEnabled()) {
            return NotificationPanelViewController.this.mView.dispatchTouchEvent(motionEvent);
        } else {
            int i2 = PanelViewController.$r8$clinit;
            Log.v("PanelView", String.format("onTouchForwardedFromStatusBar: panel view disabled, eating touch at (%d,%d)", new Object[]{Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY())}));
            return true;
        }
    }

    public final boolean updateOrientationAndCutout() {
        boolean z;
        int exactRotation = R$id.getExactRotation(this.mContext);
        if (exactRotation != this.mRotationOrientation) {
            this.mRotationOrientation = exactRotation;
            z = true;
        } else {
            z = false;
        }
        if (Objects.equals(getRootWindowInsets().getDisplayCutout(), this.mDisplayCutout)) {
            return z;
        }
        this.mDisplayCutout = getRootWindowInsets().getDisplayCutout();
        return true;
    }

    public final void updateStatusBarHeight() {
        int i;
        DisplayCutout displayCutout = this.mDisplayCutout;
        if (displayCutout == null) {
            i = 0;
        } else {
            i = displayCutout.getWaterfallInsets().top;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = SystemBarUtils.getStatusBarHeight(this.mContext) - i;
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.status_bar_padding_top);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.status_bar_padding_start);
        findViewById(C1777R.C1779id.status_bar_contents).setPaddingRelative(dimensionPixelSize2, dimensionPixelSize, getResources().getDimensionPixelSize(C1777R.dimen.status_bar_padding_end), 0);
        findViewById(C1777R.C1779id.notification_lights_out).setPaddingRelative(0, dimensionPixelSize2, 0, 0);
        setLayoutParams(layoutParams);
    }

    public PhoneStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (updateOrientationAndCutout()) {
            updateLayoutForCutout();
            requestLayout();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
        if (updateOrientationAndCutout()) {
            updateLayoutForCutout();
            requestLayout();
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBattery = (DarkIconDispatcher.DarkReceiver) findViewById(C1777R.C1779id.battery);
        this.mClock = (DarkIconDispatcher.DarkReceiver) findViewById(C1777R.C1779id.clock);
        this.mCutoutSpace = findViewById(C1777R.C1779id.cutout_space_view);
        updateResources();
    }

    public final boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(obtain);
        dispatchPopulateAccessibilityEvent(obtain);
        accessibilityEvent.appendRecord(obtain);
        return true;
    }

    public final void updateLayoutForCutout() {
        updateStatusBarHeight();
        if (this.mCutoutSpace != null) {
            boolean currentRotationHasCornerCutout = this.mContentInsetsProvider.currentRotationHasCornerCutout();
            DisplayCutout displayCutout = this.mDisplayCutout;
            if (displayCutout == null || displayCutout.isEmpty() || currentRotationHasCornerCutout) {
                this.mCutoutSpace.setVisibility(8);
            } else {
                this.mCutoutSpace.setVisibility(0);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mCutoutSpace.getLayoutParams();
                Rect boundingRectTop = this.mDisplayCutout.getBoundingRectTop();
                int i = boundingRectTop.left;
                int i2 = this.mCutoutSideNudge;
                boundingRectTop.left = i + i2;
                boundingRectTop.right -= i2;
                layoutParams.width = boundingRectTop.width();
                layoutParams.height = boundingRectTop.height();
            }
        }
        Pair<Integer, Integer> statusBarContentInsetsForCurrentRotation = this.mContentInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        setPadding(((Integer) statusBarContentInsetsForCurrentRotation.first).intValue(), getPaddingTop(), ((Integer) statusBarContentInsetsForCurrentRotation.second).intValue(), getPaddingBottom());
    }

    public final void updateResources() {
        this.mCutoutSideNudge = getResources().getDimensionPixelSize(C1777R.dimen.display_cutout_margin_consumption);
        updateStatusBarHeight();
    }
}
