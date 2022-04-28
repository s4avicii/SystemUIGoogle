package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotHelper;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda4;
import com.android.systemui.screenshot.TakeScreenshotService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TakeScreenshotService$$ExternalSyntheticLambda0 implements Handler.Callback {
    public final /* synthetic */ TakeScreenshotService f$0;

    public /* synthetic */ TakeScreenshotService$$ExternalSyntheticLambda0(TakeScreenshotService takeScreenshotService) {
        this.f$0 = takeScreenshotService;
    }

    public final boolean handleMessage(Message message) {
        ScreenshotEvent screenshotEvent;
        String str;
        boolean z;
        boolean z2;
        Insets insets;
        TakeScreenshotService takeScreenshotService = this.f$0;
        int i = TakeScreenshotService.$r8$clinit;
        Objects.requireNonNull(takeScreenshotService);
        Messenger messenger = message.replyTo;
        DozeTriggers$$ExternalSyntheticLambda4 dozeTriggers$$ExternalSyntheticLambda4 = new DozeTriggers$$ExternalSyntheticLambda4(messenger, 1);
        TakeScreenshotService.RequestCallbackImpl requestCallbackImpl = new TakeScreenshotService.RequestCallbackImpl(messenger);
        if (!takeScreenshotService.mUserManager.isUserUnlocked()) {
            Log.w("Screenshot", "Skipping screenshot because storage is locked!");
            takeScreenshotService.mNotificationsController.notifyScreenshotError(C1777R.string.screenshot_failed_to_save_user_locked_text);
            try {
                messenger.send(Message.obtain((Handler) null, 1, (Object) null));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
            try {
                requestCallbackImpl.mReplyTo.send(Message.obtain((Handler) null, 2));
                return true;
            } catch (RemoteException e2) {
                Log.d("Screenshot", "ignored remote exception", e2);
                return true;
            }
        } else {
            ScreenshotHelper.ScreenshotRequest screenshotRequest = (ScreenshotHelper.ScreenshotRequest) message.obj;
            ComponentName topComponent = screenshotRequest.getTopComponent();
            UiEventLogger uiEventLogger = takeScreenshotService.mUiEventLogger;
            int source = screenshotRequest.getSource();
            if (source == 0) {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_GLOBAL_ACTIONS;
            } else if (source == 1) {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_KEY_CHORD;
            } else if (source == 2) {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_KEY_OTHER;
            } else if (source == 3) {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_OVERVIEW;
            } else if (source == 4) {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_ACCESSIBILITY_ACTIONS;
            } else if (source != 6) {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_OTHER;
            } else {
                screenshotEvent = ScreenshotEvent.SCREENSHOT_REQUESTED_VENDOR_GESTURE;
            }
            if (topComponent == null) {
                str = "";
            } else {
                str = topComponent.getPackageName();
            }
            uiEventLogger.log(screenshotEvent, 0, str);
            int i2 = message.what;
            if (i2 == 1) {
                ScreenshotController screenshotController = takeScreenshotService.mScreenshot;
                Objects.requireNonNull(screenshotController);
                screenshotController.mCurrentRequestCallback = requestCallbackImpl;
                DisplayMetrics displayMetrics = new DisplayMetrics();
                screenshotController.mDisplayManager.getDisplay(0).getRealMetrics(displayMetrics);
                screenshotController.takeScreenshotInternal(topComponent, dozeTriggers$$ExternalSyntheticLambda4, new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels));
                return true;
            } else if (i2 == 2) {
                ScreenshotController screenshotController2 = takeScreenshotService.mScreenshot;
                Objects.requireNonNull(screenshotController2);
                screenshotController2.mScreenshotView.reset();
                screenshotController2.mCurrentRequestCallback = requestCallbackImpl;
                screenshotController2.attachWindow();
                screenshotController2.mWindow.setContentView(screenshotController2.mScreenshotView);
                screenshotController2.mScreenshotView.requestApplyInsets();
                ScreenshotView screenshotView = screenshotController2.mScreenshotView;
                ScreenshotController$$ExternalSyntheticLambda7 screenshotController$$ExternalSyntheticLambda7 = new ScreenshotController$$ExternalSyntheticLambda7(screenshotController2, topComponent, dozeTriggers$$ExternalSyntheticLambda4);
                Objects.requireNonNull(screenshotView);
                ScreenshotSelectorView screenshotSelectorView = screenshotView.mScreenshotSelectorView;
                Objects.requireNonNull(screenshotSelectorView);
                screenshotSelectorView.mOnScreenshotSelected = screenshotController$$ExternalSyntheticLambda7;
                screenshotView.mScreenshotSelectorView.setVisibility(0);
                screenshotView.mScreenshotSelectorView.requestFocus();
                return true;
            } else if (i2 != 3) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid screenshot option: ");
                m.append(message.what);
                Log.w("Screenshot", m.toString());
                return false;
            } else {
                Bundle bitmapBundle = screenshotRequest.getBitmapBundle();
                if (!bitmapBundle.containsKey("bitmap_util_buffer") || !bitmapBundle.containsKey("bitmap_util_color_space")) {
                    throw new IllegalArgumentException("Bundle does not contain a hardware bitmap");
                }
                HardwareBuffer hardwareBuffer = (HardwareBuffer) bitmapBundle.getParcelable("bitmap_util_buffer");
                Objects.requireNonNull(hardwareBuffer);
                Bitmap wrapHardwareBuffer = Bitmap.wrapHardwareBuffer(hardwareBuffer, bitmapBundle.getParcelable("bitmap_util_color_space").getColorSpace());
                Rect boundsInScreen = screenshotRequest.getBoundsInScreen();
                Insets insets2 = screenshotRequest.getInsets();
                screenshotRequest.getTaskId();
                screenshotRequest.getUserId();
                if (wrapHardwareBuffer == null) {
                    Log.e("Screenshot", "Got null bitmap from screenshot message");
                    takeScreenshotService.mNotificationsController.notifyScreenshotError(C1777R.string.screenshot_failed_to_capture_text);
                    try {
                        messenger.send(Message.obtain((Handler) null, 1, (Object) null));
                    } catch (RemoteException e3) {
                        Log.d("Screenshot", "ignored remote exception", e3);
                    }
                    try {
                        requestCallbackImpl.mReplyTo.send(Message.obtain((Handler) null, 2));
                        return true;
                    } catch (RemoteException e4) {
                        Log.d("Screenshot", "ignored remote exception", e4);
                        return true;
                    }
                } else {
                    ScreenshotController screenshotController3 = takeScreenshotService.mScreenshot;
                    Objects.requireNonNull(screenshotController3);
                    int width = (wrapHardwareBuffer.getWidth() - insets2.left) - insets2.right;
                    int height = (wrapHardwareBuffer.getHeight() - insets2.top) - insets2.bottom;
                    if (height == 0 || width == 0 || wrapHardwareBuffer.getWidth() == 0 || wrapHardwareBuffer.getHeight() == 0 || Math.abs((((float) width) / ((float) height)) - (((float) boundsInScreen.width()) / ((float) boundsInScreen.height()))) >= 0.1f) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (!z) {
                        insets = Insets.NONE;
                        boundsInScreen.set(0, 0, wrapHardwareBuffer.getWidth(), wrapHardwareBuffer.getHeight());
                        z2 = true;
                    } else {
                        z2 = false;
                        insets = insets2;
                    }
                    screenshotController3.mCurrentRequestCallback = requestCallbackImpl;
                    screenshotController3.saveScreenshot(wrapHardwareBuffer, dozeTriggers$$ExternalSyntheticLambda4, boundsInScreen, insets, topComponent, z2);
                    return true;
                }
            }
        }
    }
}
