package com.android.systemui.screenshot;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.UserManager;
import com.android.internal.logging.UiEventLogger;
import java.util.Objects;

public class TakeScreenshotService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C10921 mCloseSystemDialogs = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            ScreenshotController screenshotController;
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction()) && (screenshotController = TakeScreenshotService.this.mScreenshot) != null) {
                ScreenshotView screenshotView = screenshotController.mScreenshotView;
                Objects.requireNonNull(screenshotView);
                if (!screenshotView.mPendingSharedTransition) {
                    TakeScreenshotService.this.mScreenshot.dismissScreenshot();
                }
            }
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new TakeScreenshotService$$ExternalSyntheticLambda0(this));
    public final ScreenshotNotificationsController mNotificationsController;
    public ScreenshotController mScreenshot;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;

    public interface RequestCallback {
    }

    public final void onCreate() {
    }

    public static class RequestCallbackImpl implements RequestCallback {
        public final Messenger mReplyTo;

        public RequestCallbackImpl(Messenger messenger) {
            this.mReplyTo = messenger;
        }
    }

    public final IBinder onBind(Intent intent) {
        registerReceiver(this.mCloseSystemDialogs, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
        return new Messenger(this.mHandler).getBinder();
    }

    public final boolean onUnbind(Intent intent) {
        ScreenshotController screenshotController = this.mScreenshot;
        if (screenshotController != null) {
            screenshotController.removeWindow();
            this.mScreenshot = null;
        }
        unregisterReceiver(this.mCloseSystemDialogs);
        return false;
    }

    public TakeScreenshotService(ScreenshotController screenshotController, UserManager userManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController screenshotNotificationsController) {
        this.mScreenshot = screenshotController;
        this.mUserManager = userManager;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationsController = screenshotNotificationsController;
    }

    public final void onDestroy() {
        super.onDestroy();
        ScreenshotController screenshotController = this.mScreenshot;
        if (screenshotController != null) {
            screenshotController.removeWindow();
            ScreenshotController screenshotController2 = this.mScreenshot;
            Objects.requireNonNull(screenshotController2);
            screenshotController2.mContext.unregisterReceiver(screenshotController2.mCopyBroadcastReceiver);
            screenshotController2.mContext.release();
            MediaPlayer mediaPlayer = screenshotController2.mCameraSound;
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            screenshotController2.mBgExecutor.shutdownNow();
            this.mScreenshot = null;
        }
    }

    static {
        Class<TakeScreenshotService> cls = TakeScreenshotService.class;
    }
}
