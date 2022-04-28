package com.android.systemui.screenrecord;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.screenrecord.ScreenMediaRecorder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.util.Objects;
import java.util.concurrent.Executor;

public class RecordingService extends Service implements MediaRecorder.OnInfoListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ScreenRecordingAudioSource mAudioSource;
    public final RecordingController mController;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final Executor mLongExecutor;
    public final NotificationManager mNotificationManager;
    public boolean mOriginalShowTaps;
    public ScreenMediaRecorder mRecorder;
    public boolean mShowTaps;
    public final UiEventLogger mUiEventLogger;
    public final UserContextProvider mUserContextTracker;

    public final IBinder onBind(Intent intent) {
        return null;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int onStartCommand(android.content.Intent r10, int r11, int r12) {
        /*
            r9 = this;
            r11 = 2
            if (r10 != 0) goto L_0x0004
            return r11
        L_0x0004:
            java.lang.String r12 = r10.getAction()
            java.lang.String r0 = "onStartCommand "
            java.lang.String r1 = "RecordingService"
            androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0.m17m(r0, r12, r1)
            com.android.systemui.settings.UserContextProvider r0 = r9.mUserContextTracker
            android.content.Context r0 = r0.getUserContext()
            int r0 = r0.getUserId()
            android.os.UserHandle r2 = new android.os.UserHandle
            r2.<init>(r0)
            java.util.Objects.requireNonNull(r12)
            int r3 = r12.hashCode()
            r4 = 0
            r5 = 1
            r6 = -1
            java.lang.String r7 = "com.android.systemui.screenrecord.STOP_FROM_NOTIF"
            switch(r3) {
                case -1688140755: goto L_0x004d;
                case -1687783248: goto L_0x0042;
                case -470086188: goto L_0x0037;
                case -288359034: goto L_0x002e;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x0058
        L_0x002e:
            boolean r3 = r12.equals(r7)
            if (r3 != 0) goto L_0x0035
            goto L_0x0058
        L_0x0035:
            r3 = 3
            goto L_0x0059
        L_0x0037:
            java.lang.String r3 = "com.android.systemui.screenrecord.STOP"
            boolean r3 = r12.equals(r3)
            if (r3 != 0) goto L_0x0040
            goto L_0x0058
        L_0x0040:
            r3 = r11
            goto L_0x0059
        L_0x0042:
            java.lang.String r3 = "com.android.systemui.screenrecord.START"
            boolean r3 = r12.equals(r3)
            if (r3 != 0) goto L_0x004b
            goto L_0x0058
        L_0x004b:
            r3 = r5
            goto L_0x0059
        L_0x004d:
            java.lang.String r3 = "com.android.systemui.screenrecord.SHARE"
            boolean r3 = r12.equals(r3)
            if (r3 != 0) goto L_0x0056
            goto L_0x0058
        L_0x0056:
            r3 = r4
            goto L_0x0059
        L_0x0058:
            r3 = r6
        L_0x0059:
            java.lang.String r8 = "show_touches"
            switch(r3) {
                case 0: goto L_0x01ca;
                case 1: goto L_0x0145;
                case 2: goto L_0x0061;
                case 3: goto L_0x0061;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x01fc
        L_0x0061:
            boolean r12 = r7.equals(r12)
            if (r12 == 0) goto L_0x006f
            com.android.internal.logging.UiEventLogger r12 = r9.mUiEventLogger
            com.android.systemui.screenrecord.Events$ScreenRecordEvent r0 = com.android.systemui.screenrecord.Events$ScreenRecordEvent.SCREEN_RECORD_END_NOTIFICATION
            r12.log(r0)
            goto L_0x0076
        L_0x006f:
            com.android.internal.logging.UiEventLogger r12 = r9.mUiEventLogger
            com.android.systemui.screenrecord.Events$ScreenRecordEvent r0 = com.android.systemui.screenrecord.Events$ScreenRecordEvent.SCREEN_RECORD_END_QS_TILE
            r12.log(r0)
        L_0x0076:
            java.lang.String r12 = "android.intent.extra.user_handle"
            int r10 = r10.getIntExtra(r12, r6)
            if (r10 != r6) goto L_0x0088
            com.android.systemui.settings.UserContextProvider r10 = r9.mUserContextTracker
            android.content.Context r10 = r10.getUserContext()
            int r10 = r10.getUserId()
        L_0x0088:
            java.lang.String r12 = "notifying for user "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r12, r10, r1)
            boolean r12 = r9.mOriginalShowTaps
            android.content.ContentResolver r0 = r9.getContentResolver()
            android.provider.Settings.System.putInt(r0, r8, r12)
            com.android.systemui.screenrecord.ScreenMediaRecorder r12 = r9.getRecorder()
            if (r12 == 0) goto L_0x0130
            com.android.systemui.screenrecord.ScreenMediaRecorder r12 = r9.getRecorder()
            java.util.Objects.requireNonNull(r12)
            android.media.MediaRecorder r0 = r12.mMediaRecorder
            r0.stop()
            android.media.MediaRecorder r0 = r12.mMediaRecorder
            r0.release()
            android.view.Surface r0 = r12.mInputSurface
            r0.release()
            android.hardware.display.VirtualDisplay r0 = r12.mVirtualDisplay
            r0.release()
            android.media.projection.MediaProjection r0 = r12.mMediaProjection
            r0.stop()
            r0 = 0
            r12.mMediaRecorder = r0
            r12.mMediaProjection = r0
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r1 = r12.mAudioSource
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r2 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.INTERNAL
            if (r1 == r2) goto L_0x00cb
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r2 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.MIC_AND_INTERNAL
            if (r1 != r2) goto L_0x010e
        L_0x00cb:
            com.android.systemui.screenrecord.ScreenInternalAudioRecorder r1 = r12.mAudio
            java.util.Objects.requireNonNull(r1)
            android.media.AudioRecord r2 = r1.mAudioRecord
            r2.stop()
            boolean r2 = r1.mMic
            if (r2 == 0) goto L_0x00de
            android.media.AudioRecord r2 = r1.mAudioRecordMic
            r2.stop()
        L_0x00de:
            android.media.AudioRecord r2 = r1.mAudioRecord
            r2.release()
            boolean r2 = r1.mMic
            if (r2 == 0) goto L_0x00ec
            android.media.AudioRecord r2 = r1.mAudioRecordMic
            r2.release()
        L_0x00ec:
            java.lang.Thread r2 = r1.mThread     // Catch:{ InterruptedException -> 0x00f2 }
            r2.join()     // Catch:{ InterruptedException -> 0x00f2 }
            goto L_0x00f6
        L_0x00f2:
            r2 = move-exception
            r2.printStackTrace()
        L_0x00f6:
            android.media.MediaCodec r2 = r1.mCodec
            r2.stop()
            android.media.MediaCodec r2 = r1.mCodec
            r2.release()
            android.media.MediaMuxer r2 = r1.mMuxer
            r2.stop()
            android.media.MediaMuxer r2 = r1.mMuxer
            r2.release()
            r1.mThread = r0
            r12.mAudio = r0
        L_0x010e:
            java.lang.String r12 = "ScreenMediaRecorder"
            java.lang.String r1 = "end recording"
            android.util.Log.d(r12, r1)
            android.os.UserHandle r12 = new android.os.UserHandle
            r12.<init>(r10)
            android.app.NotificationManager r10 = r9.mNotificationManager
            android.app.Notification r1 = r9.createProcessingNotification()
            r2 = 4275(0x10b3, float:5.99E-42)
            r10.notifyAsUser(r0, r2, r1, r12)
            java.util.concurrent.Executor r10 = r9.mLongExecutor
            com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0 r0 = new com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0
            r0.<init>(r9, r12, r11)
            r10.execute(r0)
            goto L_0x0136
        L_0x0130:
            java.lang.String r10 = "stopRecording called, but recorder was null"
            android.util.Log.e(r1, r10)
        L_0x0136:
            r9.updateState(r4)
            android.app.NotificationManager r10 = r9.mNotificationManager
            r11 = 4274(0x10b2, float:5.989E-42)
            r10.cancel(r11)
            r9.stopSelf()
            goto L_0x01fc
        L_0x0145:
            com.android.systemui.screenrecord.ScreenRecordingAudioSource[] r12 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.values()
            java.lang.String r2 = "extra_useAudio"
            int r2 = r10.getIntExtra(r2, r4)
            r12 = r12[r2]
            r9.mAudioSource = r12
            java.lang.String r12 = "recording with audio source"
            java.lang.StringBuilder r12 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r12)
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r2 = r9.mAudioSource
            r12.append(r2)
            java.lang.String r12 = r12.toString()
            android.util.Log.d(r1, r12)
            java.lang.String r12 = "extra_showTaps"
            boolean r10 = r10.getBooleanExtra(r12, r4)
            r9.mShowTaps = r10
            android.content.Context r10 = r9.getApplicationContext()
            android.content.ContentResolver r10 = r10.getContentResolver()
            int r10 = android.provider.Settings.System.getInt(r10, r8, r4)
            if (r10 == 0) goto L_0x017e
            r10 = r5
            goto L_0x017f
        L_0x017e:
            r10 = r4
        L_0x017f:
            r9.mOriginalShowTaps = r10
            boolean r10 = r9.mShowTaps
            android.content.ContentResolver r12 = r9.getContentResolver()
            android.provider.Settings.System.putInt(r12, r8, r10)
            com.android.systemui.screenrecord.ScreenMediaRecorder r10 = new com.android.systemui.screenrecord.ScreenMediaRecorder
            com.android.systemui.settings.UserContextProvider r12 = r9.mUserContextTracker
            android.content.Context r12 = r12.getUserContext()
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r1 = r9.mAudioSource
            r10.<init>(r12, r0, r1, r9)
            r9.mRecorder = r10
            com.android.systemui.screenrecord.ScreenMediaRecorder r10 = r9.getRecorder()     // Catch:{ RemoteException | IOException | RuntimeException -> 0x01a2 }
            r10.start()     // Catch:{ RemoteException | IOException | RuntimeException -> 0x01a2 }
            r10 = r5
            goto L_0x01ad
        L_0x01a2:
            r10 = move-exception
            r12 = 2131953216(0x7f130640, float:1.9542897E38)
            r9.showErrorToast(r12)
            r10.printStackTrace()
            r10 = r4
        L_0x01ad:
            if (r10 == 0) goto L_0x01bd
            r9.updateState(r5)
            r9.createRecordingNotification()
            com.android.internal.logging.UiEventLogger r9 = r9.mUiEventLogger
            com.android.systemui.screenrecord.Events$ScreenRecordEvent r10 = com.android.systemui.screenrecord.Events$ScreenRecordEvent.SCREEN_RECORD_START
            r9.log(r10)
            goto L_0x01fc
        L_0x01bd:
            r9.updateState(r4)
            r9.createErrorNotification()
            r9.stopForeground(r5)
            r9.stopSelf()
            return r11
        L_0x01ca:
            java.lang.String r11 = "extra_path"
            java.lang.String r10 = r10.getStringExtra(r11)
            android.net.Uri r10 = android.net.Uri.parse(r10)
            android.content.Intent r11 = new android.content.Intent
            java.lang.String r12 = "android.intent.action.SEND"
            r11.<init>(r12)
            java.lang.String r12 = "video/mp4"
            android.content.Intent r11 = r11.setType(r12)
            java.lang.String r12 = "android.intent.extra.STREAM"
            android.content.Intent r10 = r11.putExtra(r12, r10)
            com.android.systemui.statusbar.phone.KeyguardDismissUtil r11 = r9.mKeyguardDismissUtil
            com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda0 r12 = new com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda0
            r12.<init>(r9, r10, r2)
            r11.executeWhenUnlocked(r12, r4, r4)
            android.content.Intent r10 = new android.content.Intent
            java.lang.String r11 = "android.intent.action.CLOSE_SYSTEM_DIALOGS"
            r10.<init>(r11)
            r9.sendBroadcast(r10)
        L_0x01fc:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenrecord.RecordingService.onStartCommand(android.content.Intent, int, int):int");
    }

    @VisibleForTesting
    public void showErrorToast(int i) {
        Toast.makeText(this, i, 1).show();
    }

    public final void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        Log.d("RecordingService", "Media recorder info: " + i);
        onStartCommand(new Intent(this, RecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", getUserId()), 0, 0);
    }

    public final void updateState(boolean z) {
        if (this.mUserContextTracker.getUserContext().getUserId() == 0) {
            this.mController.updateState(z);
            return;
        }
        Intent intent = new Intent("com.android.systemui.screenrecord.UPDATE_STATE");
        intent.putExtra("extra_state", z);
        intent.addFlags(1073741824);
        sendBroadcast(intent, "com.android.systemui.permission.SELF");
    }

    public RecordingService(RecordingController recordingController, Executor executor, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil) {
        this.mController = recordingController;
        this.mLongExecutor = executor;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationManager = notificationManager;
        this.mUserContextTracker = userContextProvider;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
    }

    @VisibleForTesting
    public void createErrorNotification() {
        Resources resources = getResources();
        NotificationChannel notificationChannel = new NotificationChannel("screen_record", getString(C1777R.string.screenrecord_name), 3);
        notificationChannel.setDescription(getString(C1777R.string.screenrecord_channel_description));
        notificationChannel.enableVibration(true);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(C1777R.string.screenrecord_name));
        startForeground(4274, new Notification.Builder(this, "screen_record").setSmallIcon(C1777R.C1778drawable.ic_screenrecord).setContentTitle(resources.getString(C1777R.string.screenrecord_start_error)).addExtras(bundle).build());
    }

    @VisibleForTesting
    public Notification createProcessingNotification() {
        String str;
        Resources resources = getApplicationContext().getResources();
        if (this.mAudioSource == ScreenRecordingAudioSource.NONE) {
            str = resources.getString(C1777R.string.screenrecord_ongoing_screen_only);
        } else {
            str = resources.getString(C1777R.string.screenrecord_ongoing_screen_and_audio);
        }
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(C1777R.string.screenrecord_name));
        return new Notification.Builder(getApplicationContext(), "screen_record").setContentTitle(str).setContentText(getResources().getString(C1777R.string.screenrecord_background_processing_label)).setSmallIcon(C1777R.C1778drawable.ic_screenrecord).addExtras(bundle).build();
    }

    @VisibleForTesting
    public void createRecordingNotification() {
        String str;
        Resources resources = getResources();
        NotificationChannel notificationChannel = new NotificationChannel("screen_record", getString(C1777R.string.screenrecord_name), 3);
        notificationChannel.setDescription(getString(C1777R.string.screenrecord_channel_description));
        notificationChannel.enableVibration(true);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(C1777R.string.screenrecord_name));
        if (this.mAudioSource == ScreenRecordingAudioSource.NONE) {
            str = resources.getString(C1777R.string.screenrecord_ongoing_screen_only);
        } else {
            str = resources.getString(C1777R.string.screenrecord_ongoing_screen_and_audio);
        }
        startForeground(4274, new Notification.Builder(this, "screen_record").setSmallIcon(C1777R.C1778drawable.ic_screenrecord).setContentTitle(str).setUsesChronometer(true).setColorized(true).setColor(getResources().getColor(C1777R.color.GM2_red_700)).setOngoing(true).setForegroundServiceBehavior(1).addAction(new Notification.Action.Builder(Icon.createWithResource(this, C1777R.C1778drawable.ic_android), getResources().getString(C1777R.string.screenrecord_stop_label), PendingIntent.getService(this, 2, new Intent(this, RecordingService.class).setAction("com.android.systemui.screenrecord.STOP_FROM_NOTIF"), 201326592)).build()).addExtras(bundle).build());
    }

    @VisibleForTesting
    public Notification createSaveNotification(ScreenMediaRecorder.SavedRecording savedRecording) {
        Objects.requireNonNull(savedRecording);
        Uri uri = savedRecording.mUri;
        Intent dataAndType = new Intent("android.intent.action.VIEW").setFlags(268435457).setDataAndType(uri, "video/mp4");
        Notification.Action build = new Notification.Action.Builder(Icon.createWithResource(this, C1777R.C1778drawable.ic_screenrecord), getResources().getString(C1777R.string.screenrecord_share_label), PendingIntent.getService(this, 2, new Intent(this, RecordingService.class).setAction("com.android.systemui.screenrecord.SHARE").putExtra("extra_path", uri.toString()), 201326592)).build();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", getResources().getString(C1777R.string.screenrecord_name));
        Notification.Builder addExtras = new Notification.Builder(this, "screen_record").setSmallIcon(C1777R.C1778drawable.ic_screenrecord).setContentTitle(getResources().getString(C1777R.string.screenrecord_save_title)).setContentText(getResources().getString(C1777R.string.screenrecord_save_text)).setContentIntent(PendingIntent.getActivity(this, 2, dataAndType, 67108864)).addAction(build).setAutoCancel(true).addExtras(bundle);
        Bitmap bitmap = savedRecording.mThumbnailBitmap;
        if (bitmap != null) {
            addExtras.setStyle(new Notification.BigPictureStyle().bigPicture(bitmap).showBigPictureWhenCollapsed(true));
        }
        return addExtras.build();
    }

    @VisibleForTesting
    public ScreenMediaRecorder getRecorder() {
        return this.mRecorder;
    }

    public final void onCreate() {
        super.onCreate();
    }
}
