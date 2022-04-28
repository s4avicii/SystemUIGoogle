package com.android.systemui.wmshell;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.InputMonitor;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.window.WindowContext;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.clipboardoverlay.ClipboardOverlayController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.p006qs.tiles.LocationTile;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda10;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.android.systemui.screenshot.LongScreenshotData;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda5;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.screenshot.TimeoutHandler;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.wmshell.WMShell;
import com.google.android.systemui.smartspace.InterceptingViewPager;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShell$7$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$7$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WMShell.C17707 r10 = (WMShell.C17707) this.f$0;
                Objects.requireNonNull(r10);
                SysUiState sysUiState = WMShell.this.mSysUiState;
                sysUiState.setFlag(65536, true);
                sysUiState.commitUpdate(0);
                return;
            case 1:
                ClipboardOverlayController clipboardOverlayController = (ClipboardOverlayController) this.f$0;
                Objects.requireNonNull(clipboardOverlayController);
                TimeoutHandler timeoutHandler = clipboardOverlayController.mTimeoutHandler;
                Objects.requireNonNull(timeoutHandler);
                timeoutHandler.removeMessages(2);
                View peekDecorView = clipboardOverlayController.mWindow.peekDecorView();
                if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
                    clipboardOverlayController.mWindowManager.removeViewImmediate(peekDecorView);
                }
                ClipboardOverlayController.C07241 r0 = clipboardOverlayController.mCloseDialogsReceiver;
                if (r0 != null) {
                    clipboardOverlayController.mContext.unregisterReceiver(r0);
                    clipboardOverlayController.mCloseDialogsReceiver = null;
                }
                ClipboardOverlayController.C07252 r02 = clipboardOverlayController.mScreenshotReceiver;
                if (r02 != null) {
                    clipboardOverlayController.mContext.unregisterReceiver(r02);
                    clipboardOverlayController.mScreenshotReceiver = null;
                }
                ClipboardOverlayController.C07263 r03 = clipboardOverlayController.mInputEventReceiver;
                if (r03 != null) {
                    r03.dispose();
                    clipboardOverlayController.mInputEventReceiver = null;
                }
                InputMonitor inputMonitor = clipboardOverlayController.mInputMonitor;
                if (inputMonitor != null) {
                    inputMonitor.dispose();
                    clipboardOverlayController.mInputMonitor = null;
                }
                Runnable runnable = clipboardOverlayController.mOnSessionCompleteListener;
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            case 2:
                LocationTile locationTile = (LocationTile) this.f$0;
                int i = LocationTile.$r8$clinit;
                Objects.requireNonNull(locationTile);
                boolean z = ((QSTile.BooleanState) locationTile.mState).value;
                locationTile.mHost.openPanels();
                locationTile.mController.setLocationEnabled(!z);
                return;
            case 3:
                InternetDialog internetDialog = (InternetDialog) this.f$0;
                boolean z2 = InternetDialog.DEBUG;
                Objects.requireNonNull(internetDialog);
                InternetDialogController internetDialogController = internetDialog.mInternetDialogController;
                Objects.requireNonNull(internetDialogController);
                Drawable drawable = internetDialogController.mContext.getDrawable(C1777R.C1778drawable.ic_signal_strength_zero_bar_no_internet);
                try {
                    if (internetDialogController.mTelephonyManager != null) {
                        boolean isCarrierNetworkActive = internetDialogController.isCarrierNetworkActive();
                        if (internetDialogController.isDataStateInService() || internetDialogController.isVoiceStateInService() || isCarrierNetworkActive) {
                            AtomicReference atomicReference = new AtomicReference();
                            atomicReference.set(internetDialogController.getSignalStrengthDrawableWithLevel(isCarrierNetworkActive));
                            drawable = (Drawable) atomicReference.get();
                        }
                        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(internetDialogController.mContext, 16843282);
                        if (internetDialogController.activeNetworkIsCellular() || isCarrierNetworkActive) {
                            colorAttrDefaultColor = internetDialogController.mContext.getColor(C1777R.color.connected_network_primary_color);
                        }
                        drawable.setTint(colorAttrDefaultColor);
                    } else if (InternetDialogController.DEBUG) {
                        Log.d("InternetDialogController", "TelephonyManager is null");
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda10(internetDialog, drawable, 0));
                return;
            case 4:
                ScreenshotController screenshotController = (ScreenshotController) this.f$0;
                ScreenshotController.C10731 r04 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                Objects.requireNonNull(screenshotController);
                try {
                    ScrollCaptureController.LongScreenshot longScreenshot = (ScrollCaptureController.LongScreenshot) screenshotController.mLongScreenshotFuture.get();
                    Objects.requireNonNull(longScreenshot);
                    if (longScreenshot.mImageTileSet.getHeight() == 0) {
                        screenshotController.mScreenshotView.restoreNonScrollingUi();
                        return;
                    }
                    LongScreenshotData longScreenshotData = screenshotController.mLongScreenshotHolder;
                    Objects.requireNonNull(longScreenshotData);
                    longScreenshotData.mLongScreenshot.set(longScreenshot);
                    LongScreenshotData longScreenshotData2 = screenshotController.mLongScreenshotHolder;
                    ScreenshotController$$ExternalSyntheticLambda5 screenshotController$$ExternalSyntheticLambda5 = new ScreenshotController$$ExternalSyntheticLambda5(screenshotController, longScreenshot);
                    Objects.requireNonNull(longScreenshotData2);
                    longScreenshotData2.mTransitionDestinationCallback.set(screenshotController$$ExternalSyntheticLambda5);
                    Intent intent = new Intent(screenshotController.mContext, LongScreenshotActivity.class);
                    intent.setFlags(335544320);
                    WindowContext windowContext = screenshotController.mContext;
                    windowContext.startActivity(intent, ActivityOptions.makeCustomAnimation(windowContext, 0, 0).toBundle());
                    try {
                        WindowManagerGlobal.getWindowManagerService().overridePendingAppTransitionRemote(new RemoteAnimationAdapter(ScreenshotController.SCREENSHOT_REMOTE_RUNNER, 0, 0), 0);
                        return;
                    } catch (Exception e) {
                        Log.e("Screenshot", "Error overriding screenshot app transition", e);
                        return;
                    }
                } catch (CancellationException unused) {
                    Log.e("Screenshot", "Long screenshot cancelled");
                    return;
                } catch (InterruptedException | ExecutionException e2) {
                    Log.e("Screenshot", "Exception", e2);
                    screenshotController.mScreenshotView.restoreNonScrollingUi();
                    return;
                }
            case 5:
                KeyguardIndicationController keyguardIndicationController = (KeyguardIndicationController) this.f$0;
                Objects.requireNonNull(keyguardIndicationController);
                String deviceOwnerInfo = keyguardIndicationController.mLockPatternUtils.getDeviceOwnerInfo();
                if (deviceOwnerInfo == null && keyguardIndicationController.mLockPatternUtils.isOwnerInfoEnabled(KeyguardUpdateMonitor.getCurrentUser())) {
                    deviceOwnerInfo = keyguardIndicationController.mLockPatternUtils.getOwnerInfo(KeyguardUpdateMonitor.getCurrentUser());
                }
                keyguardIndicationController.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda0(keyguardIndicationController, deviceOwnerInfo, 2));
                return;
            case FalsingManager.VERSION:
                NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) this.f$0;
                boolean z3 = NotificationStackScrollLayout.SPEW;
                Objects.requireNonNull(notificationStackScrollLayout);
                notificationStackScrollLayout.animateScroll();
                return;
            case 7:
                StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) this.f$0;
                Objects.requireNonNull(statusBarNotificationPresenter);
                NotificationPanelViewController notificationPanelViewController = statusBarNotificationPresenter.mNotificationPanel;
                Objects.requireNonNull(notificationPanelViewController);
                if (!notificationPanelViewController.mTracking) {
                    NotificationPanelViewController notificationPanelViewController2 = statusBarNotificationPresenter.mNotificationPanel;
                    Objects.requireNonNull(notificationPanelViewController2);
                    if (!notificationPanelViewController2.mQsExpanded && statusBarNotificationPresenter.mStatusBarStateController.getState() == 2 && !statusBarNotificationPresenter.isCollapsing()) {
                        statusBarNotificationPresenter.mStatusBarStateController.setState(1);
                        return;
                    }
                    return;
                }
                return;
            default:
                InterceptingViewPager interceptingViewPager = (InterceptingViewPager) this.f$0;
                int i2 = InterceptingViewPager.$r8$clinit;
                Objects.requireNonNull(interceptingViewPager);
                interceptingViewPager.mHasPerformedLongPress = true;
                if (interceptingViewPager.performLongClick()) {
                    interceptingViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                    return;
                }
                return;
        }
    }
}
