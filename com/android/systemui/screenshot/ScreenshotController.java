package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ExitTransitionCoordinator;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.LayoutInflater;
import android.view.RemoteAnimationTarget;
import android.view.ScrollCaptureResponse;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.window.WindowContext;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2;
import com.android.systemui.screenshot.ScreenshotView;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda1;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ScreenshotController {
    public static final C10731 SCREENSHOT_REMOTE_RUNNER = new IRemoteAnimationRunner.Stub() {
        public final void onAnimationCancelled() {
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                C10731 r1 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                Log.e("Screenshot", "Error finishing screenshot remote animation", e);
            }
        }
    };
    public final AccessibilityManager mAccessibilityManager;
    public final ExecutorService mBgExecutor;
    public boolean mBlockAttach;
    public final MediaPlayer mCameraSound;
    public final InterestingConfigChanges mConfigChanges;
    public final WindowContext mContext;
    public C10742 mCopyBroadcastReceiver;
    public TakeScreenshotService.RequestCallback mCurrentRequestCallback;
    public final DisplayManager mDisplayManager;
    public final ImageExporter mImageExporter;
    public final boolean mIsLowRamDevice;
    public CallbackToFutureAdapter.SafeFuture mLastScrollCaptureRequest;
    public ScrollCaptureResponse mLastScrollCaptureResponse;
    public CallbackToFutureAdapter.SafeFuture mLongScreenshotFuture;
    public final LongScreenshotData mLongScreenshotHolder;
    public final Executor mMainExecutor;
    public final ScreenshotNotificationsController mNotificationsController;
    public String mPackageName = "";
    public SaveImageInBackgroundTask mSaveInBgTask;
    public Bitmap mScreenBitmap;
    public AnimatorSet mScreenshotAnimation;
    public final TimeoutHandler mScreenshotHandler;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public boolean mScreenshotTakenInPortrait;
    public ScreenshotView mScreenshotView;
    public final ScrollCaptureClient mScrollCaptureClient;
    public final ScrollCaptureController mScrollCaptureController;
    public final UiEventLogger mUiEventLogger;
    public final PhoneWindow mWindow;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;

    public interface ActionsReadyListener {
        void onActionsReady(SavedImageData savedImageData);
    }

    public interface QuickShareActionReadyListener {
    }

    public static class QuickShareData {
        public Notification.Action quickShareAction;
    }

    public static class SaveImageInBackgroundData {
        public Consumer<Uri> finisher;
        public Bitmap image;
        public ActionsReadyListener mActionsReadyListener;
        public QuickShareActionReadyListener mQuickShareActionsReadyListener;
    }

    public static class SavedImageData {
        public Supplier<ActionTransition> editTransition;
        public Notification.Action quickShareAction;
        public Supplier<ActionTransition> shareTransition;
        public ArrayList smartActions;
        public Uri uri;

        public static class ActionTransition {
            public Notification.Action action;
            public Bundle bundle;
            public ScreenDecorations$$ExternalSyntheticLambda2 onCancelRunnable;
        }
    }

    public class ScreenshotExitTransitionCallbacksSupplier implements Supplier<ExitTransitionCoordinator.ExitTransitionCallbacks> {
        public ScreenshotExitTransitionCallbacksSupplier() {
        }

        public final Object get() {
            return new ExitTransitionCoordinator.ExitTransitionCallbacks() {
                public final boolean isReturnTransitionAllowed() {
                    return false;
                }

                public final void onFinish() {
                }

                public final void hideSharedElements() {
                    Objects.requireNonNull(ScreenshotExitTransitionCallbacksSupplier.this);
                    ScreenshotController screenshotController = ScreenshotController.this;
                    C10731 r0 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                    screenshotController.finishDismiss();
                }
            };
        }
    }

    public interface TransitionDestination {
        void setTransitionDestination(Rect rect, LongScreenshotActivity$1$$ExternalSyntheticLambda0 longScreenshotActivity$1$$ExternalSyntheticLambda0);
    }

    public final void attachWindow() {
        View decorView = this.mWindow.getDecorView();
        if (!decorView.isAttachedToWindow() && !this.mBlockAttach) {
            this.mBlockAttach = true;
            this.mWindowManager.addView(decorView, this.mWindowLayoutParams);
            decorView.requestApplyInsets();
        }
    }

    public final void dismissScreenshot() {
        boolean z;
        ScreenshotView screenshotView = this.mScreenshotView;
        Objects.requireNonNull(screenshotView);
        SwipeDismissHandler swipeDismissHandler = screenshotView.mSwipeDismissHandler;
        Objects.requireNonNull(swipeDismissHandler);
        ValueAnimator valueAnimator = swipeDismissHandler.mDismissAnimation;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            TimeoutHandler timeoutHandler = this.mScreenshotHandler;
            Objects.requireNonNull(timeoutHandler);
            timeoutHandler.removeMessages(2);
            ScreenshotView screenshotView2 = this.mScreenshotView;
            Objects.requireNonNull(screenshotView2);
            SwipeDismissHandler swipeDismissHandler2 = screenshotView2.mSwipeDismissHandler;
            Objects.requireNonNull(swipeDismissHandler2);
            swipeDismissHandler2.dismiss(1.0f);
        }
    }

    public final void finishDismiss() {
        CallbackToFutureAdapter.SafeFuture safeFuture = this.mLastScrollCaptureRequest;
        if (safeFuture != null) {
            safeFuture.cancel(true);
            this.mLastScrollCaptureRequest = null;
        }
        ScrollCaptureResponse scrollCaptureResponse = this.mLastScrollCaptureResponse;
        if (scrollCaptureResponse != null) {
            scrollCaptureResponse.close();
            this.mLastScrollCaptureResponse = null;
        }
        CallbackToFutureAdapter.SafeFuture safeFuture2 = this.mLongScreenshotFuture;
        if (safeFuture2 != null) {
            safeFuture2.cancel(true);
        }
        TakeScreenshotService.RequestCallback requestCallback = this.mCurrentRequestCallback;
        if (requestCallback != null) {
            Messenger messenger = ((TakeScreenshotService.RequestCallbackImpl) requestCallback).mReplyTo;
            int i = TakeScreenshotService.$r8$clinit;
            try {
                messenger.send(Message.obtain((Handler) null, 2));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
            this.mCurrentRequestCallback = null;
        }
        this.mScreenshotView.reset();
        removeWindow();
        TimeoutHandler timeoutHandler = this.mScreenshotHandler;
        Objects.requireNonNull(timeoutHandler);
        timeoutHandler.removeMessages(2);
    }

    public final void logSuccessOnActionsReady(SavedImageData savedImageData) {
        if (savedImageData.uri == null) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, this.mPackageName);
            this.mNotificationsController.notifyScreenshotError(C1777R.string.screenshot_failed_to_save_text);
            return;
        }
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, this.mPackageName);
    }

    public final void removeWindow() {
        View peekDecorView = this.mWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(peekDecorView);
        }
        ScreenshotView screenshotView = this.mScreenshotView;
        if (screenshotView != null) {
            screenshotView.stopInputListening();
        }
    }

    public final void requestScrollCapture() {
        if (!(!this.mIsLowRamDevice)) {
            Log.d("Screenshot", "Long screenshots not supported on this device");
            return;
        }
        ScrollCaptureClient scrollCaptureClient = this.mScrollCaptureClient;
        IBinder windowToken = this.mWindow.getDecorView().getWindowToken();
        Objects.requireNonNull(scrollCaptureClient);
        scrollCaptureClient.mHostWindowToken = windowToken;
        CallbackToFutureAdapter.SafeFuture safeFuture = this.mLastScrollCaptureRequest;
        if (safeFuture != null) {
            safeFuture.cancel(true);
        }
        ScrollCaptureClient scrollCaptureClient2 = this.mScrollCaptureClient;
        Objects.requireNonNull(scrollCaptureClient2);
        CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ScrollCaptureClient$$ExternalSyntheticLambda0(scrollCaptureClient2));
        this.mLastScrollCaptureRequest = future;
        future.delegate.addListener(new BubbleStackView$$ExternalSyntheticLambda17(this, 5), this.mMainExecutor);
    }

    public final void saveScreenshot(Bitmap bitmap, Consumer<Uri> consumer, final Rect rect, Insets insets, ComponentName componentName, final boolean z) {
        String str;
        boolean z2;
        boolean z3;
        if (this.mAccessibilityManager.isEnabled()) {
            AccessibilityEvent accessibilityEvent = new AccessibilityEvent(32);
            accessibilityEvent.setContentDescription(this.mContext.getResources().getString(C1777R.string.screenshot_saving_title));
            this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        }
        if (this.mScreenshotView.isAttachedToWindow()) {
            ScreenshotView screenshotView = this.mScreenshotView;
            Objects.requireNonNull(screenshotView);
            SwipeDismissHandler swipeDismissHandler = screenshotView.mSwipeDismissHandler;
            Objects.requireNonNull(swipeDismissHandler);
            ValueAnimator valueAnimator = swipeDismissHandler.mDismissAnimation;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                z3 = false;
            } else {
                z3 = true;
            }
            if (!z3) {
                this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_REENTERED, 0, this.mPackageName);
            }
            this.mScreenshotView.reset();
        }
        if (componentName == null) {
            str = "";
        } else {
            str = componentName.getPackageName();
        }
        this.mPackageName = str;
        ScreenshotView screenshotView2 = this.mScreenshotView;
        Objects.requireNonNull(screenshotView2);
        screenshotView2.mPackageName = str;
        this.mScreenshotView.updateOrientation(this.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
        this.mScreenBitmap = bitmap;
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "user_setup_complete", 0) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            Log.w("Screenshot", "User setup not complete, displaying toast only");
            MediaPlayer mediaPlayer = this.mCameraSound;
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
            saveScreenshotInWorkerThread(consumer, new ImageExporter$$ExternalSyntheticLambda1(this, consumer), (ScreenshotController$$ExternalSyntheticLambda4) null);
            return;
        }
        this.mScreenBitmap.setHasAlpha(false);
        this.mScreenBitmap.prepareToDraw();
        saveScreenshotInWorkerThread(consumer, new ScreenshotController$$ExternalSyntheticLambda2(this), new ScreenshotController$$ExternalSyntheticLambda4(this));
        setWindowFocusable(true);
        final BaseWifiTracker$$ExternalSyntheticLambda1 baseWifiTracker$$ExternalSyntheticLambda1 = new BaseWifiTracker$$ExternalSyntheticLambda1(this, 4);
        final View decorView = this.mWindow.getDecorView();
        if (decorView.isAttachedToWindow()) {
            baseWifiTracker$$ExternalSyntheticLambda1.run();
        } else {
            decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                public final void onWindowDetached() {
                }

                public final void onWindowAttached() {
                    ScreenshotController.this.mBlockAttach = false;
                    decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                    baseWifiTracker$$ExternalSyntheticLambda1.run();
                }
            });
        }
        attachWindow();
        this.mScreenshotView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public final boolean onPreDraw() {
                int i;
                ScreenshotController.this.mScreenshotView.getViewTreeObserver().removeOnPreDrawListener(this);
                ScreenshotController screenshotController = ScreenshotController.this;
                Rect rect = rect;
                boolean z = z;
                Objects.requireNonNull(screenshotController);
                AnimatorSet animatorSet = screenshotController.mScreenshotAnimation;
                if (animatorSet != null && animatorSet.isRunning()) {
                    screenshotController.mScreenshotAnimation.cancel();
                }
                ScreenshotView screenshotView = screenshotController.mScreenshotView;
                Objects.requireNonNull(screenshotView);
                Rect rect2 = new Rect();
                screenshotView.mScreenshotPreview.getHitRect(rect2);
                float f = screenshotView.mFixedSize;
                if (screenshotView.mOrientationPortrait) {
                    i = rect.width();
                } else {
                    i = rect.height();
                }
                float f2 = f / ((float) i);
                float f3 = 1.0f / f2;
                AnimatorSet animatorSet2 = new AnimatorSet();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.setDuration(133);
                ofFloat.setInterpolator(screenshotView.mFastOutSlowIn);
                ofFloat.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda2(screenshotView, 0));
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                ofFloat2.setDuration(217);
                ofFloat2.setInterpolator(screenshotView.mFastOutSlowIn);
                ofFloat2.addUpdateListener(new VolumeDialogImpl$$ExternalSyntheticLambda0(screenshotView, 1));
                PointF pointF = new PointF((float) rect.centerX(), (float) rect.centerY());
                PointF pointF2 = new PointF(rect2.exactCenterX(), rect2.exactCenterY());
                int[] locationOnScreen = screenshotView.mScreenshotPreview.getLocationOnScreen();
                pointF.offset((float) (rect2.left - locationOnScreen[0]), (float) (rect2.top - locationOnScreen[1]));
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat3.setDuration(500);
                ofFloat3.addListener(new AnimatorListenerAdapter(f3) {
                    public final /* synthetic */ float val$currentScale;

                    {
                        this.val$currentScale = r2;
                    }

                    public final void onAnimationStart(Animator animator) {
                        ScreenshotView.this.mScreenshotPreview.setScaleX(this.val$currentScale);
                        ScreenshotView.this.mScreenshotPreview.setScaleY(this.val$currentScale);
                        ScreenshotView.this.mScreenshotPreview.setVisibility(0);
                        if (ScreenshotView.this.mAccessibilityManager.isEnabled()) {
                            ScreenshotView.this.mDismissButton.setAlpha(0.0f);
                            ScreenshotView.this.mDismissButton.setVisibility(0);
                        }
                    }
                });
                ofFloat3.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda7(screenshotView, f3, pointF, pointF2));
                screenshotView.mScreenshotFlash.setAlpha(0.0f);
                screenshotView.mScreenshotFlash.setVisibility(0);
                ValueAnimator ofFloat4 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat4.setDuration(100);
                ofFloat4.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda0(screenshotView, 0));
                if (z) {
                    animatorSet2.play(ofFloat2).after(ofFloat);
                    animatorSet2.play(ofFloat2).with(ofFloat3);
                } else {
                    animatorSet2.play(ofFloat3);
                }
                animatorSet2.play(ofFloat4).after(ofFloat3);
                animatorSet2.addListener(new AnimatorListenerAdapter(pointF2, rect, f2) {
                    public static final /* synthetic */ int $r8$clinit = 0;
                    public final /* synthetic */ Rect val$bounds;
                    public final /* synthetic */ float val$cornerScale;
                    public final /* synthetic */ PointF val$finalPos;

                    {
                        this.val$finalPos = r2;
                        this.val$bounds = r3;
                        this.val$cornerScale = r4;
                    }

                    public final void onAnimationEnd(Animator animator) {
                        float f;
                        ScreenshotView.this.mDismissButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda5(this, 1));
                        ScreenshotView.this.mDismissButton.setAlpha(1.0f);
                        float width = ((float) ScreenshotView.this.mDismissButton.getWidth()) / 2.0f;
                        if (ScreenshotView.this.mDirectionLTR) {
                            f = ((((float) this.val$bounds.width()) * this.val$cornerScale) / 2.0f) + (this.val$finalPos.x - width);
                        } else {
                            f = (this.val$finalPos.x - width) - ((((float) this.val$bounds.width()) * this.val$cornerScale) / 2.0f);
                        }
                        ScreenshotView.this.mDismissButton.setX(f);
                        ScreenshotView.this.mDismissButton.setY((this.val$finalPos.y - width) - ((((float) this.val$bounds.height()) * this.val$cornerScale) / 2.0f));
                        ScreenshotView.this.mScreenshotPreview.setScaleX(1.0f);
                        ScreenshotView.this.mScreenshotPreview.setScaleY(1.0f);
                        ImageView imageView = ScreenshotView.this.mScreenshotPreview;
                        imageView.setX(this.val$finalPos.x - (((float) imageView.getWidth()) / 2.0f));
                        ImageView imageView2 = ScreenshotView.this.mScreenshotPreview;
                        imageView2.setY(this.val$finalPos.y - (((float) imageView2.getHeight()) / 2.0f));
                        ScreenshotView.this.requestLayout();
                        ScreenshotView.this.createScreenshotActionsShadeAnimation().start();
                        ScreenshotView screenshotView = ScreenshotView.this;
                        screenshotView.setOnTouchListener(screenshotView.mSwipeDismissHandler);
                    }
                });
                screenshotController.mScreenshotAnimation = animatorSet2;
                MediaPlayer mediaPlayer = screenshotController.mCameraSound;
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                screenshotController.mScreenshotAnimation.start();
                return true;
            }
        });
        ScreenshotView screenshotView3 = this.mScreenshotView;
        Bitmap bitmap2 = this.mScreenBitmap;
        Objects.requireNonNull(screenshotView3);
        ImageView imageView = screenshotView3.mScreenshotPreview;
        Resources resources = screenshotView3.mResources;
        int width = (bitmap2.getWidth() - insets.left) - insets.right;
        int height = (bitmap2.getHeight() - insets.top) - insets.bottom;
        Drawable bitmapDrawable = new BitmapDrawable(resources, bitmap2);
        if (height == 0 || width == 0 || bitmap2.getWidth() == 0 || bitmap2.getHeight() == 0) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Can't create inset drawable, using 0 insets bitmap and insets create degenerate region: ");
            m.append(bitmap2.getWidth());
            m.append("x");
            m.append(bitmap2.getHeight());
            m.append(" ");
            m.append(bitmapDrawable);
            Log.e("Screenshot", m.toString());
        } else {
            float f = (float) width;
            float f2 = (float) height;
            InsetDrawable insetDrawable = new InsetDrawable(bitmapDrawable, (((float) insets.left) * -1.0f) / f, (((float) insets.top) * -1.0f) / f2, (((float) insets.right) * -1.0f) / f, (((float) insets.bottom) * -1.0f) / f2);
            if (insets.left < 0 || insets.top < 0 || insets.right < 0 || insets.bottom < 0) {
                bitmapDrawable = new LayerDrawable(new Drawable[]{new ColorDrawable(-16777216), insetDrawable});
            } else {
                bitmapDrawable = insetDrawable;
            }
        }
        imageView.setImageDrawable(bitmapDrawable);
        this.mWindow.setContentView(this.mScreenshotView);
        this.mWindow.getDecorView().setOnApplyWindowInsetsListener(ScreenshotController$$ExternalSyntheticLambda0.INSTANCE);
        TimeoutHandler timeoutHandler = this.mScreenshotHandler;
        Objects.requireNonNull(timeoutHandler);
        timeoutHandler.removeMessages(2);
    }

    public final void saveScreenshotInWorkerThread(Consumer consumer, ActionsReadyListener actionsReadyListener, ScreenshotController$$ExternalSyntheticLambda4 screenshotController$$ExternalSyntheticLambda4) {
        SaveImageInBackgroundData saveImageInBackgroundData = new SaveImageInBackgroundData();
        saveImageInBackgroundData.image = this.mScreenBitmap;
        saveImageInBackgroundData.finisher = consumer;
        saveImageInBackgroundData.mActionsReadyListener = actionsReadyListener;
        saveImageInBackgroundData.mQuickShareActionsReadyListener = screenshotController$$ExternalSyntheticLambda4;
        SaveImageInBackgroundTask saveImageInBackgroundTask = this.mSaveInBgTask;
        if (saveImageInBackgroundTask != null) {
            saveImageInBackgroundTask.mParams.mActionsReadyListener = new ScreenshotController$$ExternalSyntheticLambda3(this);
        }
        SaveImageInBackgroundTask saveImageInBackgroundTask2 = new SaveImageInBackgroundTask(this.mContext, this.mImageExporter, this.mScreenshotSmartActions, saveImageInBackgroundData, new ScreenshotController$$ExternalSyntheticLambda8(this));
        this.mSaveInBgTask = saveImageInBackgroundTask2;
        saveImageInBackgroundTask2.execute(new Void[0]);
    }

    public final void setWindowFocusable(boolean z) {
        View peekDecorView;
        WindowManager.LayoutParams layoutParams = this.mWindowLayoutParams;
        int i = layoutParams.flags;
        if (z) {
            layoutParams.flags = i & -9;
        } else {
            layoutParams.flags = i | 8;
        }
        if (layoutParams.flags != i && (peekDecorView = this.mWindow.peekDecorView()) != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.updateViewLayout(peekDecorView, this.mWindowLayoutParams);
        }
    }

    public final void takeScreenshotInternal(ComponentName componentName, Consumer<Uri> consumer, Rect rect) {
        boolean z;
        if (this.mContext.getResources().getConfiguration().orientation == 1) {
            z = true;
        } else {
            z = false;
        }
        this.mScreenshotTakenInPortrait = z;
        Rect rect2 = new Rect(rect);
        Bitmap captureScreenshot = captureScreenshot(rect);
        if (captureScreenshot == null) {
            Log.e("Screenshot", "takeScreenshotInternal: Screenshot bitmap was null");
            this.mNotificationsController.notifyScreenshotError(C1777R.string.screenshot_failed_to_capture_text);
            TakeScreenshotService.RequestCallback requestCallback = this.mCurrentRequestCallback;
            if (requestCallback != null) {
                TakeScreenshotService.RequestCallbackImpl requestCallbackImpl = (TakeScreenshotService.RequestCallbackImpl) requestCallback;
                Messenger messenger = requestCallbackImpl.mReplyTo;
                int i = TakeScreenshotService.$r8$clinit;
                try {
                    messenger.send(Message.obtain((Handler) null, 1, (Object) null));
                } catch (RemoteException e) {
                    Log.d("Screenshot", "ignored remote exception", e);
                }
                try {
                    requestCallbackImpl.mReplyTo.send(Message.obtain((Handler) null, 2));
                } catch (RemoteException e2) {
                    Log.d("Screenshot", "ignored remote exception", e2);
                }
            }
        } else {
            saveScreenshot(captureScreenshot, consumer, rect2, Insets.NONE, componentName, true);
            this.mContext.sendBroadcast(new Intent("com.android.systemui.SCREENSHOT"), "com.android.systemui.permission.SELF");
        }
    }

    public ScreenshotController(Context context, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController screenshotNotificationsController, ScrollCaptureClient scrollCaptureClient, UiEventLogger uiEventLogger, ImageExporter imageExporter, Executor executor, ScrollCaptureController scrollCaptureController, LongScreenshotData longScreenshotData, ActivityManager activityManager, TimeoutHandler timeoutHandler) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-2147474556);
        this.mConfigChanges = interestingConfigChanges;
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mNotificationsController = screenshotNotificationsController;
        this.mScrollCaptureClient = scrollCaptureClient;
        this.mUiEventLogger = uiEventLogger;
        this.mImageExporter = imageExporter;
        this.mMainExecutor = executor;
        this.mScrollCaptureController = scrollCaptureController;
        this.mLongScreenshotHolder = longScreenshotData;
        this.mIsLowRamDevice = activityManager.isLowRamDevice();
        this.mBgExecutor = Executors.newSingleThreadExecutor();
        this.mScreenshotHandler = timeoutHandler;
        Objects.requireNonNull(timeoutHandler);
        timeoutHandler.mDefaultTimeout = 6000;
        timeoutHandler.mOnTimeout = new WMShell$7$$ExternalSyntheticLambda0(this, 3);
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        this.mDisplayManager = displayManager;
        WindowContext createWindowContext = context.createDisplayContext(displayManager.getDisplay(0)).createWindowContext(2036, (Bundle) null);
        this.mContext = createWindowContext;
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        this.mAccessibilityManager = AccessibilityManager.getInstance(createWindowContext);
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ScreenshotAnimation");
        PhoneWindow phoneWindow = new PhoneWindow(createWindowContext);
        phoneWindow.requestFeature(1);
        phoneWindow.requestFeature(13);
        phoneWindow.setBackgroundDrawableResource(17170445);
        this.mWindow = phoneWindow;
        phoneWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
        interestingConfigChanges.applyNewConfig(context.getResources());
        ScreenshotView screenshotView = (ScreenshotView) LayoutInflater.from(createWindowContext).inflate(C1777R.layout.screenshot, (ViewGroup) null);
        this.mScreenshotView = screenshotView;
        C10753 r5 = new ScreenshotView.ScreenshotViewCallback() {
        };
        Objects.requireNonNull(screenshotView);
        screenshotView.mUiEventLogger = uiEventLogger;
        screenshotView.mCallbacks = r5;
        this.mScreenshotView.setOnKeyListener(new ScreenshotController$$ExternalSyntheticLambda1(this));
        this.mScreenshotView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mScreenshotView);
        this.mCameraSound = MediaPlayer.create(createWindowContext, Uri.fromFile(new File(createWindowContext.getResources().getString(17039884))), (SurfaceHolder) null, new AudioAttributes.Builder().setUsage(13).setContentType(4).build(), AudioSystem.newAudioSessionId());
        C10742 r8 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("com.android.systemui.COPY".equals(intent.getAction())) {
                    ScreenshotController.this.dismissScreenshot();
                }
            }
        };
        this.mCopyBroadcastReceiver = r8;
        createWindowContext.registerReceiver(r8, new IntentFilter("com.android.systemui.COPY"), "com.android.systemui.permission.SELF", (Handler) null, 4);
    }

    public final Bitmap captureScreenshot(Rect rect) {
        int width = rect.width();
        int height = rect.height();
        Display display = this.mDisplayManager.getDisplay(0);
        DisplayAddress.Physical address = display.getAddress();
        if (!(address instanceof DisplayAddress.Physical)) {
            Log.e("Screenshot", "Skipping Screenshot - Default display does not have a physical address: " + display);
            return null;
        }
        SurfaceControl.ScreenshotHardwareBuffer captureDisplay = SurfaceControl.captureDisplay(new SurfaceControl.DisplayCaptureArgs.Builder(SurfaceControl.getPhysicalDisplayToken(address.getPhysicalDisplayId())).setSourceCrop(rect).setSize(width, height).build());
        if (captureDisplay == null) {
            return null;
        }
        return captureDisplay.asBitmap();
    }
}
