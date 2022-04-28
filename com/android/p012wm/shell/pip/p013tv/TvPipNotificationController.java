package com.android.p012wm.shell.pip.p013tv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Handler;
import android.text.TextUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.pip.PipMediaController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipNotificationController */
public final class TvPipNotificationController {
    public final ActionBroadcastReceiver mActionBroadcastReceiver = new ActionBroadcastReceiver();
    public Bitmap mArt;
    public final Context mContext;
    public String mDefaultTitle;
    public Delegate mDelegate;
    public final Handler mMainHandler;
    public String mMediaTitle;
    public final Notification.Builder mNotificationBuilder;
    public final NotificationManager mNotificationManager;
    public boolean mNotified;
    public final PackageManager mPackageManager;
    public String mPackageName;

    /* renamed from: com.android.wm.shell.pip.tv.TvPipNotificationController$ActionBroadcastReceiver */
    public class ActionBroadcastReceiver extends BroadcastReceiver {
        public final IntentFilter mIntentFilter;
        public boolean mRegistered = false;

        public ActionBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            this.mIntentFilter = intentFilter;
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.CLOSE_PIP");
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU");
        }

        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU".equals(action)) {
                TvPipController tvPipController = (TvPipController) TvPipNotificationController.this.mDelegate;
                Objects.requireNonNull(tvPipController);
                if (tvPipController.mState != 0) {
                    tvPipController.setState(2);
                    tvPipController.movePinnedStack();
                }
            } else if ("com.android.wm.shell.pip.tv.notification.action.CLOSE_PIP".equals(action)) {
                ((TvPipController) TvPipNotificationController.this.mDelegate).closePip();
            }
        }
    }

    /* renamed from: com.android.wm.shell.pip.tv.TvPipNotificationController$Delegate */
    public interface Delegate {
    }

    public final void update() {
        String str;
        this.mNotified = true;
        Notification.Builder when = this.mNotificationBuilder.setWhen(System.currentTimeMillis());
        if (!TextUtils.isEmpty(this.mMediaTitle)) {
            str = this.mMediaTitle;
        } else {
            try {
                str = this.mPackageManager.getApplicationLabel(this.mPackageManager.getApplicationInfo(this.mPackageName, 0)).toString();
            } catch (PackageManager.NameNotFoundException unused) {
                str = null;
            }
            if (TextUtils.isEmpty(str)) {
                str = this.mDefaultTitle;
            }
        }
        when.setContentTitle(str);
        if (this.mArt != null) {
            this.mNotificationBuilder.setStyle(new Notification.BigPictureStyle().bigPicture(this.mArt));
        } else {
            this.mNotificationBuilder.setStyle((Notification.Style) null);
        }
        this.mNotificationManager.notify("TvPip", 1100, this.mNotificationBuilder.build());
    }

    public TvPipNotificationController(Context context, PipMediaController pipMediaController, Handler handler) {
        MediaMetadata mediaMetadata;
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mMainHandler = handler;
        this.mNotificationBuilder = new Notification.Builder(context, "TVPIP").setLocalOnly(true).setOngoing(false).setCategory("sys").setShowWhen(true).setSmallIcon(C1777R.C1778drawable.pip_icon).extend(new Notification.TvExtender().setContentIntent(PendingIntent.getBroadcast(context, 0, new Intent("com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU").setPackage(context.getPackageName()), 335544320)).setDeleteIntent(PendingIntent.getBroadcast(context, 0, new Intent("com.android.wm.shell.pip.tv.notification.action.CLOSE_PIP").setPackage(context.getPackageName()), 335544320)));
        TvPipNotificationController$$ExternalSyntheticLambda0 tvPipNotificationController$$ExternalSyntheticLambda0 = new TvPipNotificationController$$ExternalSyntheticLambda0(this);
        Objects.requireNonNull(pipMediaController);
        if (!pipMediaController.mMetadataListeners.contains(tvPipNotificationController$$ExternalSyntheticLambda0)) {
            pipMediaController.mMetadataListeners.add(tvPipNotificationController$$ExternalSyntheticLambda0);
            MediaController mediaController = pipMediaController.mMediaController;
            if (mediaController != null) {
                mediaMetadata = mediaController.getMetadata();
            } else {
                mediaMetadata = null;
            }
            tvPipNotificationController$$ExternalSyntheticLambda0.onMediaMetadataChanged(mediaMetadata);
        }
        this.mDefaultTitle = context.getResources().getString(C1777R.string.pip_notification_unknown_title);
        if (this.mNotified) {
            update();
        }
    }
}
