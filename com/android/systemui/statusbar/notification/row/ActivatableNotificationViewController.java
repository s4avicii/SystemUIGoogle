package com.android.systemui.statusbar.notification.row;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.phone.NotificationTapHelper;
import com.android.systemui.util.ViewController;
import java.util.Objects;

public final class ActivatableNotificationViewController extends ViewController<ActivatableNotificationView> {
    public final AccessibilityManager mAccessibilityManager;
    public final ExpandableOutlineViewController mExpandableOutlineViewController;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final TouchHandler mTouchHandler = new TouchHandler();

    public class TouchHandler implements Gefingerpoken, View.OnTouchListener {
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public TouchHandler() {
        }

        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) ActivatableNotificationViewController.this.mView;
                long uptimeMillis = SystemClock.uptimeMillis();
                Objects.requireNonNull(activatableNotificationView);
                activatableNotificationView.mLastActionUpTime = uptimeMillis;
            }
            if (ActivatableNotificationViewController.this.mAccessibilityManager.isTouchExplorationEnabled() || motionEvent.getAction() != 1) {
                return false;
            }
            boolean isFalseTap = ActivatableNotificationViewController.this.mFalsingManager.isFalseTap(1);
            if (!isFalseTap && (view instanceof ActivatableNotificationView)) {
                ((ActivatableNotificationView) view).onTap();
            }
            return isFalseTap;
        }
    }

    public final void onViewAttached() {
    }

    public final void onViewDetached() {
    }

    public final void onInit() {
        ExpandableOutlineViewController expandableOutlineViewController = this.mExpandableOutlineViewController;
        Objects.requireNonNull(expandableOutlineViewController);
        Objects.requireNonNull(expandableOutlineViewController.mExpandableViewController);
        ((ActivatableNotificationView) this.mView).setOnTouchListener(this.mTouchHandler);
        ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) this.mView;
        TouchHandler touchHandler = this.mTouchHandler;
        Objects.requireNonNull(activatableNotificationView);
        activatableNotificationView.mTouchHandler = touchHandler;
        Objects.requireNonNull((ActivatableNotificationView) this.mView);
    }

    public ActivatableNotificationViewController(ActivatableNotificationView activatableNotificationView, NotificationTapHelper.Factory factory, ExpandableOutlineViewController expandableOutlineViewController, AccessibilityManager accessibilityManager, FalsingManager falsingManager, FalsingCollector falsingCollector) {
        super(activatableNotificationView);
        this.mExpandableOutlineViewController = expandableOutlineViewController;
        this.mAccessibilityManager = accessibilityManager;
        this.mFalsingManager = falsingManager;
        this.mFalsingCollector = falsingCollector;
        ActivatableNotificationView activatableNotificationView2 = activatableNotificationView;
        Objects.requireNonNull(activatableNotificationView);
        ActivatableNotificationView activatableNotificationView3 = activatableNotificationView;
        Objects.requireNonNull(activatableNotificationView);
        Objects.requireNonNull(factory);
        ActivatableNotificationView activatableNotificationView4 = activatableNotificationView;
        C13051 r3 = new ActivatableNotificationView.OnActivatedListener() {
            public final void onActivationReset(ActivatableNotificationView activatableNotificationView) {
            }
        };
        Objects.requireNonNull(activatableNotificationView);
        activatableNotificationView.mOnActivatedListener = r3;
    }
}
