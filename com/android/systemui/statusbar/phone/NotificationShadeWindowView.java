package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.DisplayCutout;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.FrameLayout;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$styleable;
import com.android.systemui.statusbar.phone.NotificationShadeWindowViewController;
import java.util.Objects;

public class NotificationShadeWindowView extends FrameLayout {
    public C14952 mFakeWindow = new Window(this.mContext) {
        public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        }

        public final void alwaysReadCloseOnTouchAttr() {
        }

        public final void clearContentView() {
        }

        public final void closeAllPanels() {
        }

        public final void closePanel(int i) {
        }

        public final View getCurrentFocus() {
            return null;
        }

        public final WindowInsetsController getInsetsController() {
            return null;
        }

        public final LayoutInflater getLayoutInflater() {
            return null;
        }

        public final int getNavigationBarColor() {
            return 0;
        }

        public final int getStatusBarColor() {
            return 0;
        }

        public final int getVolumeControlStream() {
            return 0;
        }

        public final void invalidatePanelMenu(int i) {
        }

        public final boolean isFloating() {
            return false;
        }

        public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
            return false;
        }

        public final void onActive() {
        }

        public final void onConfigurationChanged(Configuration configuration) {
        }

        public final void onMultiWindowModeChanged() {
        }

        public final void onPictureInPictureModeChanged(boolean z) {
        }

        public final void openPanel(int i, KeyEvent keyEvent) {
        }

        public final View peekDecorView() {
            return null;
        }

        public final boolean performContextMenuIdentifierAction(int i, int i2) {
            return false;
        }

        public final boolean performPanelIdentifierAction(int i, int i2, int i3) {
            return false;
        }

        public final boolean performPanelShortcut(int i, int i2, KeyEvent keyEvent, int i3) {
            return false;
        }

        public final void reportActivityRelaunched() {
        }

        public final void restoreHierarchyState(Bundle bundle) {
        }

        public final Bundle saveHierarchyState() {
            return null;
        }

        public final void setBackgroundDrawable(Drawable drawable) {
        }

        public final void setChildDrawable(int i, Drawable drawable) {
        }

        public final void setChildInt(int i, int i2) {
        }

        public final void setContentView(int i) {
        }

        public final void setContentView(View view) {
        }

        public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        }

        public final void setDecorCaptionShade(int i) {
        }

        public final void setFeatureDrawable(int i, Drawable drawable) {
        }

        public final void setFeatureDrawableAlpha(int i, int i2) {
        }

        public final void setFeatureDrawableResource(int i, int i2) {
        }

        public final void setFeatureDrawableUri(int i, Uri uri) {
        }

        public final void setFeatureInt(int i, int i2) {
        }

        public final void setNavigationBarColor(int i) {
        }

        public final void setResizingCaptionDrawable(Drawable drawable) {
        }

        public final void setStatusBarColor(int i) {
        }

        public final void setTitle(CharSequence charSequence) {
        }

        public final void setTitleColor(int i) {
        }

        public final void setVolumeControlStream(int i) {
        }

        public final boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
            return false;
        }

        public final boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return false;
        }

        public final boolean superDispatchTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public final boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
            return false;
        }

        public final void takeInputQueue(InputQueue.Callback callback) {
        }

        public final void takeKeyEvents(boolean z) {
        }

        public final void takeSurface(SurfaceHolder.Callback2 callback2) {
        }

        public final void togglePanel(int i, KeyEvent keyEvent) {
        }

        public final View getDecorView() {
            return NotificationShadeWindowView.this;
        }
    };
    public ActionMode mFloatingActionMode;
    public View mFloatingActionModeOriginatingView;
    public FloatingToolbar mFloatingToolbar;
    public C14941 mFloatingToolbarPreDrawListener;
    public InteractionEventHandler mInteractionEventHandler;
    public int mLeftInset = 0;
    public int mRightInset = 0;

    public class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        public final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            NotificationShadeWindowView notificationShadeWindowView = NotificationShadeWindowView.this;
            if (actionMode == notificationShadeWindowView.mFloatingActionMode) {
                notificationShadeWindowView.cleanupFloatingActionModeViews();
                NotificationShadeWindowView.this.mFloatingActionMode = null;
            }
            NotificationShadeWindowView.this.requestFitSystemWindows();
        }

        public final void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            ActionMode.Callback callback = this.mWrapped;
            if (callback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) callback).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }

        public final boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            NotificationShadeWindowView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    public interface InteractionEventHandler {
    }

    public class LayoutParams extends FrameLayout.LayoutParams {
        public boolean ignoreRightInset;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StatusBarWindowView_Layout);
            this.ignoreRightInset = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }
    }

    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        if (i != 1) {
            return super.startActionModeForChild(view, callback, i);
        }
        ActionModeCallback2Wrapper actionModeCallback2Wrapper = new ActionModeCallback2Wrapper(callback);
        ActionMode actionMode = this.mFloatingActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mFakeWindow);
        final FloatingActionMode floatingActionMode = new FloatingActionMode(this.mContext, actionModeCallback2Wrapper, view, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = view;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            public final boolean onPreDraw() {
                floatingActionMode.updateViewLocationInWindow();
                return true;
            }
        };
        if (!actionModeCallback2Wrapper.onCreateActionMode(floatingActionMode, floatingActionMode.getMenu())) {
            return null;
        }
        this.mFloatingActionMode = floatingActionMode;
        floatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
        return floatingActionMode;
    }

    public final void cleanupFloatingActionModeViews() {
        FloatingToolbar floatingToolbar = this.mFloatingToolbar;
        if (floatingToolbar != null) {
            floatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        View view = this.mFloatingActionModeOriginatingView;
        if (view != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                view.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r7) {
        /*
            r6 = this;
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r0 = r6.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r0 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r0 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r0 = r0.mService
            java.util.Objects.requireNonNull(r0)
            int r1 = r0.mState
            r2 = 0
            r3 = 1
            if (r1 != r3) goto L_0x002b
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r0 = r0.mStatusBarKeyguardViewManager
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.KeyguardBouncer r0 = r0.mBouncer
            java.util.Objects.requireNonNull(r0)
            r0.ensureView()
            com.android.keyguard.KeyguardHostViewController r0 = r0.mKeyguardViewController
            boolean r0 = r0.interceptMediaKey(r7)
            if (r0 == 0) goto L_0x002b
            r0 = r3
            goto L_0x002c
        L_0x002b:
            r0 = r2
        L_0x002c:
            if (r0 == 0) goto L_0x002f
            return r3
        L_0x002f:
            boolean r0 = super.dispatchKeyEvent(r7)
            if (r0 == 0) goto L_0x0036
            return r3
        L_0x0036:
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r6 = r6.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r6 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r6
            java.util.Objects.requireNonNull(r6)
            int r0 = r7.getAction()
            if (r0 != 0) goto L_0x0045
            r0 = r3
            goto L_0x0046
        L_0x0045:
            r0 = r2
        L_0x0046:
            int r1 = r7.getKeyCode()
            r4 = 4
            if (r1 == r4) goto L_0x00eb
            r4 = 62
            r5 = 2
            if (r1 == r4) goto L_0x00d2
            r4 = 82
            if (r1 == r4) goto L_0x007d
            r0 = 24
            if (r1 == r0) goto L_0x0060
            r0 = 25
            if (r1 == r0) goto L_0x0060
            goto L_0x00e9
        L_0x0060:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r0 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.SysuiStatusBarStateController r0 = r0.mStatusBarStateController
            boolean r0 = r0.isDozing()
            if (r0 == 0) goto L_0x00e9
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r6 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.NotificationShadeWindowView r6 = r6.mView
            android.content.Context r6 = r6.getContext()
            android.media.session.MediaSessionLegacyHelper r6 = android.media.session.MediaSessionLegacyHelper.getHelper(r6)
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r6.sendVolumeKeyEvent(r7, r0, r3)
            goto L_0x00f4
        L_0x007d:
            if (r0 != 0) goto L_0x00e9
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r6 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r6 = r6.mService
            java.util.Objects.requireNonNull(r6)
            boolean r7 = r6.mDeviceInteractive
            if (r7 == 0) goto L_0x00c9
            int r7 = r6.mState
            if (r7 == 0) goto L_0x00c9
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r7 = r6.mStatusBarKeyguardViewManager
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.statusbar.phone.KeyguardBouncer r7 = r7.mBouncer
            java.util.Objects.requireNonNull(r7)
            com.android.keyguard.KeyguardHostViewController r7 = r7.mKeyguardViewController
            java.util.Objects.requireNonNull(r7)
            T r7 = r7.mView
            com.android.keyguard.KeyguardHostView r7 = (com.android.keyguard.KeyguardHostView) r7
            android.content.res.Resources r7 = r7.getResources()
            r0 = 2131034123(0x7f05000b, float:1.7678755E38)
            boolean r7 = r7.getBoolean(r0)
            boolean r0 = android.app.ActivityManager.isRunningInTestHarness()
            java.io.File r1 = new java.io.File
            java.lang.String r4 = "/data/local/enable_menu_key"
            r1.<init>(r4)
            boolean r1 = r1.exists()
            if (r7 == 0) goto L_0x00c4
            if (r0 != 0) goto L_0x00c4
            if (r1 == 0) goto L_0x00c2
            goto L_0x00c4
        L_0x00c2:
            r7 = r2
            goto L_0x00c5
        L_0x00c4:
            r7 = r3
        L_0x00c5:
            if (r7 == 0) goto L_0x00c9
            r7 = r3
            goto L_0x00ca
        L_0x00c9:
            r7 = r2
        L_0x00ca:
            if (r7 == 0) goto L_0x00e9
            com.android.systemui.statusbar.phone.ShadeController r6 = r6.mShadeController
            r6.animateCollapsePanels$1(r5)
            goto L_0x00e8
        L_0x00d2:
            if (r0 != 0) goto L_0x00e9
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r6 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r6 = r6.mService
            java.util.Objects.requireNonNull(r6)
            boolean r7 = r6.mDeviceInteractive
            if (r7 == 0) goto L_0x00e9
            int r7 = r6.mState
            if (r7 == 0) goto L_0x00e9
            com.android.systemui.statusbar.phone.ShadeController r6 = r6.mShadeController
            r6.animateCollapsePanels$1(r5)
        L_0x00e8:
            r2 = r3
        L_0x00e9:
            r3 = r2
            goto L_0x00f4
        L_0x00eb:
            if (r0 != 0) goto L_0x00f4
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r6 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r6 = r6.mService
            r6.onBackPressed()
        L_0x00f4:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationShadeWindowView.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        NotificationShadeWindowViewController.C14972 r3 = (NotificationShadeWindowViewController.C14972) this.mInteractionEventHandler;
        Objects.requireNonNull(r3);
        StatusBar statusBar = NotificationShadeWindowViewController.this.mService;
        Objects.requireNonNull(statusBar);
        if (keyEvent.getKeyCode() != 4) {
            return false;
        }
        boolean z = true;
        if (statusBar.mState != 1) {
            return false;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = statusBar.mStatusBarKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        KeyguardBouncer keyguardBouncer = statusBarKeyguardViewManager.mBouncer;
        Objects.requireNonNull(keyguardBouncer);
        keyguardBouncer.ensureView();
        KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
        Objects.requireNonNull(keyguardHostViewController);
        KeyguardSecurityContainerController keyguardSecurityContainerController = keyguardHostViewController.mKeyguardSecurityContainerController;
        Objects.requireNonNull(keyguardSecurityContainerController);
        if (keyguardSecurityContainerController.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.Password) {
            z = false;
        }
        if (z) {
            return statusBar.onBackPressed();
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r14v3, types: [android.view.View] */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        if (r12.mIgnoreTouchWhilePulsing == false) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0178, code lost:
        if (r12 != 10) goto L_0x0234;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0069  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchTouchEvent(android.view.MotionEvent r23) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r2 = r0.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r2 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r2
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r3 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.PhoneStatusBarViewController r3 = r3.mStatusBarViewController
            if (r3 != 0) goto L_0x001c
            java.lang.String r2 = "NotifShadeWindowVC"
            java.lang.String r3 = "Ignoring touch while statusBarView not yet set."
            android.util.Log.w(r2, r3)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            goto L_0x0313
        L_0x001c:
            int r3 = r23.getActionMasked()
            r5 = 1
            r6 = 0
            if (r3 != 0) goto L_0x0026
            r3 = r5
            goto L_0x0027
        L_0x0026:
            r3 = r6
        L_0x0027:
            int r7 = r23.getActionMasked()
            if (r7 != r5) goto L_0x002f
            r7 = r5
            goto L_0x0030
        L_0x002f:
            r7 = r6
        L_0x0030:
            int r8 = r23.getActionMasked()
            r9 = 3
            if (r8 != r9) goto L_0x0039
            r8 = r5
            goto L_0x003a
        L_0x0039:
            r8 = r6
        L_0x003a:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            boolean r11 = r10.mExpandingBelowNotch
            if (r7 != 0) goto L_0x0042
            if (r8 == 0) goto L_0x0044
        L_0x0042:
            r10.mExpandingBelowNotch = r6
        L_0x0044:
            if (r8 != 0) goto L_0x006d
            com.android.systemui.statusbar.phone.StatusBar r10 = r10.mService
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.statusbar.SysuiStatusBarStateController r12 = r10.mStatusBarStateController
            boolean r12 = r12.isDozing()
            if (r12 == 0) goto L_0x005c
            com.android.systemui.statusbar.phone.DozeServiceHost r12 = r10.mDozeServiceHost
            java.util.Objects.requireNonNull(r12)
            boolean r12 = r12.mIgnoreTouchWhilePulsing
            if (r12 != 0) goto L_0x0064
        L_0x005c:
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r10 = r10.mScreenOffAnimationController
            boolean r10 = r10.shouldIgnoreKeyguardTouches()
            if (r10 == 0) goto L_0x0066
        L_0x0064:
            r10 = r5
            goto L_0x0067
        L_0x0066:
            r10 = r6
        L_0x0067:
            if (r10 == 0) goto L_0x006d
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            goto L_0x0313
        L_0x006d:
            if (r3 == 0) goto L_0x0076
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            r10.mTouchActive = r5
            r10.mTouchCancelled = r6
            goto L_0x0086
        L_0x0076:
            int r10 = r23.getActionMasked()
            if (r10 == r5) goto L_0x0082
            int r10 = r23.getActionMasked()
            if (r10 != r9) goto L_0x0086
        L_0x0082:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            r10.mTouchActive = r6
        L_0x0086:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            boolean r12 = r10.mTouchCancelled
            if (r12 != 0) goto L_0x0311
            boolean r12 = r10.mExpandAnimationRunning
            if (r12 == 0) goto L_0x0092
            goto L_0x0311
        L_0x0092:
            com.android.systemui.classifier.FalsingCollector r10 = r10.mFalsingCollector
            r10.onTouchEvent(r1)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            android.view.GestureDetector r10 = r10.mGestureDetector
            r10.onTouchEvent(r1)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r10 = r10.mStatusBarKeyguardViewManager
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$AlternateAuthInterceptor r10 = r10.mAlternateAuthInterceptor
            if (r10 != 0) goto L_0x00aa
            goto L_0x00d1
        L_0x00aa:
            com.android.systemui.biometrics.UdfpsKeyguardViewController$2 r10 = (com.android.systemui.biometrics.UdfpsKeyguardViewController.C07092) r10
            com.android.systemui.biometrics.UdfpsKeyguardViewController r10 = com.android.systemui.biometrics.UdfpsKeyguardViewController.this
            float r12 = r10.mTransitionToFullShadeProgress
            r13 = 0
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 == 0) goto L_0x00b6
            goto L_0x00d1
        L_0x00b6:
            com.android.systemui.biometrics.UdfpsController r10 = r10.mUdfpsController
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.biometrics.UdfpsControllerOverlay r12 = r10.mOverlay
            if (r12 == 0) goto L_0x00d1
            com.android.systemui.biometrics.UdfpsView r13 = r12.overlayView
            if (r13 != 0) goto L_0x00c5
            r13 = r5
            goto L_0x00c6
        L_0x00c5:
            r13 = r6
        L_0x00c6:
            if (r13 == 0) goto L_0x00c9
            goto L_0x00d1
        L_0x00c9:
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.biometrics.UdfpsView r12 = r12.overlayView
            r10.onTouch(r12, r1, r6)
        L_0x00d1:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            android.view.View r10 = r10.mBrightnessMirror
            if (r10 == 0) goto L_0x00e8
            int r10 = r10.getVisibility()
            if (r10 != 0) goto L_0x00e8
            int r10 = r23.getActionMasked()
            r12 = 5
            if (r10 != r12) goto L_0x00e8
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            goto L_0x0313
        L_0x00e8:
            if (r3 == 0) goto L_0x012f
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r10 = r10.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.statusbar.notification.row.NotificationGutsManager r12 = r10.mNotificationGutsManager
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.statusbar.notification.row.NotificationGuts r12 = r12.mNotificationGutsExposed
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r13 = r10.mSwipeHelper
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r13 = r13.getCurrentMenuRow()
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r14 = r10.mSwipeHelper
            java.util.Objects.requireNonNull(r14)
            android.view.View r14 = r14.mTranslatingParentView
            if (r12 == 0) goto L_0x0110
            com.android.systemui.statusbar.notification.row.NotificationGuts$GutsContent r15 = r12.mGutsContent
            boolean r15 = r15.isLeavebehind()
            if (r15 != 0) goto L_0x0110
            goto L_0x011d
        L_0x0110:
            if (r13 == 0) goto L_0x011c
            boolean r12 = r13.isMenuVisible()
            if (r12 == 0) goto L_0x011c
            if (r14 == 0) goto L_0x011c
            r12 = r14
            goto L_0x011d
        L_0x011c:
            r12 = 0
        L_0x011d:
            if (r12 == 0) goto L_0x012f
            boolean r12 = com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.isTouchInView(r1, r12)
            if (r12 != 0) goto L_0x012f
            com.android.systemui.statusbar.notification.row.NotificationGutsManager r12 = r10.mNotificationGutsManager
            r12.closeAndSaveGuts(r6, r6, r5, r6)
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r10 = r10.mSwipeHelper
            r10.resetExposedMenuView(r5, r5)
        L_0x012f:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.SysuiStatusBarStateController r10 = r10.mStatusBarStateController
            boolean r10 = r10.isDozing()
            if (r10 == 0) goto L_0x0149
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r10 = r10.mService
            com.android.systemui.statusbar.phone.DozeScrimController r10 = r10.mDozeScrimController
            java.util.Objects.requireNonNull(r10)
            android.os.Handler r12 = r10.mHandler
            com.android.systemui.statusbar.phone.DozeScrimController$3 r10 = r10.mPulseOut
            r12.removeCallbacks(r10)
        L_0x0149:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r10 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.keyguard.LockIconViewController r10 = r10.mLockIconViewController
            com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17 r12 = new com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17
            r13 = 7
            r12.<init>(r2, r13)
            java.util.Objects.requireNonNull(r10)
            boolean r14 = r10.onInterceptTouchEvent(r1)
            if (r14 != 0) goto L_0x0161
            r10.cancelTouches()
            goto L_0x0234
        L_0x0161:
            r10.mOnGestureDetectedRunnable = r12
            int r12 = r23.getActionMasked()
            if (r12 == 0) goto L_0x01ea
            if (r12 == r5) goto L_0x01e6
            r4 = 2
            if (r12 == r4) goto L_0x017c
            if (r12 == r9) goto L_0x01e6
            if (r12 == r13) goto L_0x017c
            r4 = 9
            if (r12 == r4) goto L_0x01ea
            r4 = 10
            if (r12 == r4) goto L_0x01e6
            goto L_0x0234
        L_0x017c:
            android.view.VelocityTracker r9 = r10.mVelocityTracker
            r9.addMovement(r1)
            android.view.VelocityTracker r9 = r10.mVelocityTracker
            r12 = 1000(0x3e8, float:1.401E-42)
            r9.computeCurrentVelocity(r12)
            android.view.VelocityTracker r9 = r10.mVelocityTracker
            int r12 = r10.mActivePointerId
            android.os.VibrationAttributes r13 = com.android.systemui.biometrics.UdfpsController.VIBRATION_ATTRIBUTES
            float r13 = r9.getXVelocity(r12)
            float r9 = r9.getYVelocity(r12)
            double r12 = (double) r13
            r14 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r12 = java.lang.Math.pow(r12, r14)
            double r5 = (double) r9
            double r5 = java.lang.Math.pow(r5, r14)
            double r5 = r5 + r12
            double r5 = java.lang.Math.sqrt(r5)
            float r5 = (float) r5
            int r6 = r23.getClassification()
            if (r6 == r4) goto L_0x0234
            r4 = 1144750080(0x443b8000, float:750.0)
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x01b7
            r4 = 1
            goto L_0x01b8
        L_0x01b7:
            r4 = 0
        L_0x01b8:
            if (r4 == 0) goto L_0x0234
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "lock icon long-press rescheduled due to high pointer velocity="
            r4.append(r6)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "LockIconViewController"
            android.util.Log.v(r5, r4)
            java.lang.Runnable r4 = r10.mLongPressCancelRunnable
            r4.run()
            com.android.systemui.util.concurrency.DelayableExecutor r4 = r10.mExecutor
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1 r5 = new com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1
            r6 = 0
            r5.<init>(r10, r6)
            r12 = 200(0xc8, double:9.9E-322)
            java.lang.Runnable r4 = r4.executeDelayed(r5, r12)
            r10.mLongPressCancelRunnable = r4
            goto L_0x0234
        L_0x01e6:
            r10.cancelTouches()
            goto L_0x0234
        L_0x01ea:
            boolean r4 = r10.mDownDetected
            if (r4 != 0) goto L_0x0207
            com.android.systemui.statusbar.VibratorHelper r4 = r10.mVibrator
            int r17 = android.os.Process.myUid()
            android.content.Context r5 = r10.getContext()
            java.lang.String r18 = r5.getOpPackageName()
            android.os.VibrationEffect r19 = com.android.systemui.biometrics.UdfpsController.EFFECT_CLICK
            android.os.VibrationAttributes r21 = com.android.keyguard.LockIconViewController.TOUCH_VIBRATION_ATTRIBUTES
            java.lang.String r20 = "lock-icon-down"
            r16 = r4
            r16.vibrate(r17, r18, r19, r20, r21)
        L_0x0207:
            r4 = 0
            int r5 = r1.getPointerId(r4)
            r10.mActivePointerId = r5
            android.view.VelocityTracker r4 = r10.mVelocityTracker
            if (r4 != 0) goto L_0x0219
            android.view.VelocityTracker r4 = android.view.VelocityTracker.obtain()
            r10.mVelocityTracker = r4
            goto L_0x021c
        L_0x0219:
            r4.clear()
        L_0x021c:
            android.view.VelocityTracker r4 = r10.mVelocityTracker
            r4.addMovement(r1)
            r4 = 1
            r10.mDownDetected = r4
            com.android.systemui.util.concurrency.DelayableExecutor r4 = r10.mExecutor
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2 r5 = new com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2
            r6 = 0
            r5.<init>(r10, r6)
            r12 = 200(0xc8, double:9.9E-322)
            java.lang.Runnable r4 = r4.executeDelayed(r5, r12)
            r10.mLongPressCancelRunnable = r4
        L_0x0234:
            if (r3 == 0) goto L_0x024d
            float r4 = r23.getY()
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r5 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.NotificationShadeWindowView r5 = r5.mView
            int r5 = r5.getBottom()
            float r5 = (float) r5
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 < 0) goto L_0x024d
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r4 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            r5 = 1
            r4.mExpandingBelowNotch = r5
            r11 = 1
        L_0x024d:
            if (r11 == 0) goto L_0x0264
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.PhoneStatusBarViewController r2 = r2.mStatusBarViewController
            java.util.Objects.requireNonNull(r2)
            T r2 = r2.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r2 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r2
            boolean r2 = r2.dispatchTouchEvent(r1)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r2)
            goto L_0x0313
        L_0x0264:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r4 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            boolean r5 = r4.mIsTrackingBarGesture
            if (r5 != 0) goto L_0x02ee
            if (r3 == 0) goto L_0x02ee
            com.android.systemui.statusbar.phone.NotificationPanelViewController r3 = r4.mNotificationPanelViewController
            boolean r3 = r3.isFullyCollapsed()
            if (r3 == 0) goto L_0x02ee
            float r3 = r23.getRawX()
            float r4 = r23.getRawY()
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r5 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.PhoneStatusBarViewController r5 = r5.mStatusBarViewController
            java.util.Objects.requireNonNull(r5)
            T r6 = r5.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r6 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r6
            int[] r6 = r6.getLocationOnScreen()
            r7 = 0
            r6 = r6[r7]
            T r7 = r5.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r7 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r7
            int[] r7 = r7.getLocationOnScreen()
            r8 = 1
            r7 = r7[r8]
            float r8 = (float) r6
            int r8 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r8 > 0) goto L_0x02c1
            T r8 = r5.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r8 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r8
            int r8 = r8.getWidth()
            int r8 = r8 + r6
            float r6 = (float) r8
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 > 0) goto L_0x02c1
            float r3 = (float) r7
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 > 0) goto L_0x02c1
            T r3 = r5.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r3 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r3
            int r3 = r3.getHeight()
            int r3 = r3 + r7
            float r3 = (float) r3
            int r3 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r3 > 0) goto L_0x02c1
            r6 = 1
            goto L_0x02c2
        L_0x02c1:
            r6 = 0
        L_0x02c2:
            if (r6 == 0) goto L_0x030f
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r3 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.window.StatusBarWindowStateController r3 = r3.mStatusBarWindowStateController
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.windowState
            if (r3 != 0) goto L_0x02d1
            r6 = 1
            goto L_0x02d2
        L_0x02d1:
            r6 = 0
        L_0x02d2:
            if (r6 == 0) goto L_0x02eb
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            r3 = 1
            r2.mIsTrackingBarGesture = r3
            com.android.systemui.statusbar.phone.PhoneStatusBarViewController r2 = r2.mStatusBarViewController
            java.util.Objects.requireNonNull(r2)
            T r2 = r2.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r2 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r2
            boolean r2 = r2.dispatchTouchEvent(r1)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r2)
            goto L_0x0313
        L_0x02eb:
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            goto L_0x0313
        L_0x02ee:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r3 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            boolean r4 = r3.mIsTrackingBarGesture
            if (r4 == 0) goto L_0x030f
            com.android.systemui.statusbar.phone.PhoneStatusBarViewController r3 = r3.mStatusBarViewController
            java.util.Objects.requireNonNull(r3)
            T r3 = r3.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r3 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r3
            boolean r3 = r3.dispatchTouchEvent(r1)
            if (r7 != 0) goto L_0x0305
            if (r8 == 0) goto L_0x030a
        L_0x0305:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            r4 = 0
            r2.mIsTrackingBarGesture = r4
        L_0x030a:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r3)
            goto L_0x0313
        L_0x030f:
            r4 = 0
            goto L_0x0313
        L_0x0311:
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
        L_0x0313:
            if (r4 == 0) goto L_0x031a
            boolean r1 = r4.booleanValue()
            goto L_0x031e
        L_0x031a:
            boolean r1 = super.dispatchTouchEvent(r23)
        L_0x031e:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r0 = r0.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r0 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r0 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.classifier.FalsingCollector r0 = r0.mFalsingCollector
            r0.onMotionEventComplete()
            boolean r0 = r1.booleanValue()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationShadeWindowView.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0029, code lost:
        if (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this.mDockManager.isDocked() == false) goto L_0x0041;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r0 = r4.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r0 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.SysuiStatusBarStateController r1 = r1.mStatusBarStateController
            boolean r1 = r1.isDozing()
            if (r1 == 0) goto L_0x002c
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r1 = r1.mService
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.phone.DozeServiceHost r1 = r1.mDozeServiceHost
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mPulsing
            if (r1 != 0) goto L_0x002c
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.dock.DockManager r1 = r1.mDockManager
            boolean r1 = r1.isDocked()
            if (r1 != 0) goto L_0x002c
            goto L_0x0041
        L_0x002c:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r1 = r1.mStatusBarKeyguardViewManager
            boolean r1 = r1.isShowingAlternateAuthOrAnimating()
            if (r1 == 0) goto L_0x0037
            goto L_0x0041
        L_0x0037:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.keyguard.LockIconViewController r1 = r1.mLockIconViewController
            boolean r1 = r1.onInterceptTouchEvent(r5)
            if (r1 == 0) goto L_0x0043
        L_0x0041:
            r0 = 1
            goto L_0x007d
        L_0x0043:
            r1 = 0
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = r2.mNotificationPanelViewController
            boolean r2 = r2.isFullyExpanded()
            if (r2 == 0) goto L_0x007c
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.DragDownHelper r2 = r2.mDragDownHelper
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.LockscreenShadeTransitionController r2 = r2.dragDownCallback
            r3 = 0
            boolean r2 = r2.mo11566x229f8ab3(r3)
            if (r2 == 0) goto L_0x007c
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r2 = r2.mService
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mBouncerShowing
            if (r2 != 0) goto L_0x007c
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r2 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.SysuiStatusBarStateController r2 = r2.mStatusBarStateController
            boolean r2 = r2.isDozing()
            if (r2 != 0) goto L_0x007c
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r0 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.DragDownHelper r0 = r0.mDragDownHelper
            boolean r0 = r0.onInterceptTouchEvent(r5)
            goto L_0x007d
        L_0x007c:
            r0 = r1
        L_0x007d:
            if (r0 != 0) goto L_0x0083
            boolean r0 = super.onInterceptTouchEvent(r5)
        L_0x0083:
            if (r0 == 0) goto L_0x00aa
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r4 = r4.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r4 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r4
            java.util.Objects.requireNonNull(r4)
            android.view.MotionEvent r5 = android.view.MotionEvent.obtain(r5)
            r1 = 3
            r5.setAction(r1)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r1.mStackScrollLayout
            r1.onInterceptTouchEvent(r5)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r4 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = r4.mNotificationPanelViewController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.phone.PanelView r4 = r4.mView
            r4.onInterceptTouchEvent(r5)
            r5.recycle()
        L_0x00aa:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationShadeWindowView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004a, code lost:
        if (r4.isDraggingDown != false) goto L_0x004c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r0 = r6.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r0 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.SysuiStatusBarStateController r1 = r1.mStatusBarStateController
            boolean r1 = r1.isDozing()
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x0023
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r1 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r1 = r1.mService
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.phone.DozeServiceHost r1 = r1.mDozeServiceHost
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mPulsing
            r1 = r1 ^ r3
            goto L_0x0024
        L_0x0023:
            r1 = r2
        L_0x0024:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r4 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r4 = r4.mStatusBarKeyguardViewManager
            boolean r4 = r4.isShowingAlternateAuthOrAnimating()
            if (r4 == 0) goto L_0x002f
            r1 = r3
        L_0x002f:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r4 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.DragDownHelper r4 = r4.mDragDownHelper
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.LockscreenShadeTransitionController r4 = r4.dragDownCallback
            r5 = 0
            boolean r4 = r4.mo11566x229f8ab3(r5)
            if (r4 == 0) goto L_0x0041
            if (r1 == 0) goto L_0x004c
        L_0x0041:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r4 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.DragDownHelper r4 = r4.mDragDownHelper
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4.isDraggingDown
            if (r4 == 0) goto L_0x0054
        L_0x004c:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r0 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.DragDownHelper r0 = r0.mDragDownHelper
            boolean r1 = r0.onTouchEvent(r7)
        L_0x0054:
            if (r1 != 0) goto L_0x005a
            boolean r1 = super.onTouchEvent(r7)
        L_0x005a:
            if (r1 != 0) goto L_0x0073
            com.android.systemui.statusbar.phone.NotificationShadeWindowView$InteractionEventHandler r6 = r6.mInteractionEventHandler
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$2 r6 = (com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.C14972) r6
            java.util.Objects.requireNonNull(r6)
            int r7 = r7.getActionMasked()
            if (r7 == r3) goto L_0x006c
            r0 = 3
            if (r7 != r0) goto L_0x0073
        L_0x006c:
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController r6 = com.android.systemui.statusbar.phone.NotificationShadeWindowViewController.this
            com.android.systemui.statusbar.phone.StatusBar r6 = r6.mService
            r6.setInteracting(r3, r2)
        L_0x0073:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationShadeWindowView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public NotificationShadeWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setMotionEventSplittingEnabled(false);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int i;
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        boolean z = true;
        if (getFitsSystemWindows()) {
            if (insetsIgnoringVisibility.top == getPaddingTop() && insetsIgnoringVisibility.bottom == getPaddingBottom()) {
                z = false;
            }
            if (z) {
                setPadding(0, 0, 0, 0);
            }
        } else {
            if (getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingTop() == 0 && getPaddingBottom() == 0) {
                z = false;
            }
            if (z) {
                setPadding(0, 0, 0, 0);
            }
        }
        this.mLeftInset = 0;
        this.mRightInset = 0;
        DisplayCutout displayCutout = getRootWindowInsets().getDisplayCutout();
        if (displayCutout != null) {
            this.mLeftInset = displayCutout.getSafeInsetLeft();
            this.mRightInset = displayCutout.getSafeInsetRight();
        }
        this.mLeftInset = Math.max(insetsIgnoringVisibility.left, this.mLeftInset);
        this.mRightInset = Math.max(insetsIgnoringVisibility.right, this.mRightInset);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getLayoutParams() instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreRightInset && !(layoutParams.rightMargin == (i = this.mRightInset) && layoutParams.leftMargin == this.mLeftInset)) {
                    layoutParams.rightMargin = i;
                    layoutParams.leftMargin = this.mLeftInset;
                    childAt.requestLayout();
                }
            }
        }
        return windowInsets;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(true);
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
