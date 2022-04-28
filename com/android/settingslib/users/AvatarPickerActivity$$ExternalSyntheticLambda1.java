package com.android.settingslib.users;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import com.android.settingslib.users.AvatarPickerActivity;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingService;
import com.android.systemui.screenrecord.ScreenRecordDialog;
import com.android.systemui.screenrecord.ScreenRecordingAudioSource;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import com.google.android.systemui.gamedashboard.GameMenuActivity;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AvatarPickerActivity$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AvatarPickerActivity$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onClick(View view) {
        ScreenRecordingAudioSource screenRecordingAudioSource;
        switch (this.$r8$classId) {
            case 0:
                AvatarPickerActivity avatarPickerActivity = (AvatarPickerActivity) this.f$0;
                int i = AvatarPickerActivity.$r8$clinit;
                Objects.requireNonNull(avatarPickerActivity);
                AvatarPickerActivity.AvatarAdapter avatarAdapter = avatarPickerActivity.mAdapter;
                Objects.requireNonNull(avatarAdapter);
                int i2 = avatarAdapter.mSelectedPosition - avatarAdapter.mPreselectedImageStartPosition;
                if (avatarAdapter.mPreselectedImages.length() > 0) {
                    int resourceId = avatarAdapter.mPreselectedImages.getResourceId(i2, -1);
                    if (resourceId != -1) {
                        AvatarPickerActivity avatarPickerActivity2 = AvatarPickerActivity.this;
                        Uri build = new Uri.Builder().scheme("android.resource").authority(AvatarPickerActivity.this.getResources().getResourcePackageName(resourceId)).appendPath(AvatarPickerActivity.this.getResources().getResourceTypeName(resourceId)).appendPath(AvatarPickerActivity.this.getResources().getResourceEntryName(resourceId)).build();
                        Objects.requireNonNull(avatarPickerActivity2);
                        Intent intent = new Intent();
                        intent.setData(build);
                        avatarPickerActivity2.setResult(-1, intent);
                        avatarPickerActivity2.finish();
                        return;
                    }
                    throw new IllegalStateException("Preselected avatar images must be resources.");
                }
                AvatarPickerActivity avatarPickerActivity3 = AvatarPickerActivity.this;
                int i3 = avatarAdapter.mUserIconColors[i2];
                Objects.requireNonNull(avatarPickerActivity3);
                Intent intent2 = new Intent();
                intent2.putExtra("default_icon_tint_color", i3);
                avatarPickerActivity3.setResult(-1, intent2);
                avatarPickerActivity3.finish();
                return;
            case 1:
                ScreenRecordDialog screenRecordDialog = (ScreenRecordDialog) this.f$0;
                List<ScreenRecordingAudioSource> list = ScreenRecordDialog.MODES;
                Objects.requireNonNull(screenRecordDialog);
                Runnable runnable = screenRecordDialog.mOnStartRecordingClicked;
                if (runnable != null) {
                    runnable.run();
                }
                Context userContext = screenRecordDialog.mUserContextProvider.getUserContext();
                boolean isChecked = screenRecordDialog.mTapsSwitch.isChecked();
                if (screenRecordDialog.mAudioSwitch.isChecked()) {
                    screenRecordingAudioSource = (ScreenRecordingAudioSource) screenRecordDialog.mOptions.getSelectedItem();
                } else {
                    screenRecordingAudioSource = ScreenRecordingAudioSource.NONE;
                }
                int ordinal = screenRecordingAudioSource.ordinal();
                int i4 = RecordingService.$r8$clinit;
                PendingIntent foregroundService = PendingIntent.getForegroundService(userContext, 2, new Intent(userContext, RecordingService.class).setAction("com.android.systemui.screenrecord.START").putExtra("extra_resultCode", -1).putExtra("extra_useAudio", ordinal).putExtra("extra_showTaps", isChecked), 201326592);
                PendingIntent service = PendingIntent.getService(userContext, 2, new Intent(userContext, RecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", userContext.getUserId()), 201326592);
                RecordingController recordingController = screenRecordDialog.mController;
                Objects.requireNonNull(recordingController);
                recordingController.mIsStarting = true;
                recordingController.mStopIntent = service;
                RecordingController.C10673 r8 = new CountDownTimer(foregroundService) {
                    public final /* synthetic */ PendingIntent val$startIntent;

                    {
                        this.val$startIntent = r4;
                    }

                    public final void onFinish() {
                        RecordingController recordingController = RecordingController.this;
                        recordingController.mIsStarting = false;
                        recordingController.mIsRecording = true;
                        Iterator<RecordingStateChangeCallback> it = recordingController.mListeners.iterator();
                        while (it.hasNext()) {
                            it.next().onCountdownEnd();
                        }
                        try {
                            this.val$startIntent.send();
                            IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_SWITCHED");
                            RecordingController recordingController2 = RecordingController.this;
                            recordingController2.mBroadcastDispatcher.registerReceiver(recordingController2.mUserChangeReceiver, intentFilter, (Executor) null, UserHandle.ALL);
                            IntentFilter intentFilter2 = new IntentFilter("com.android.systemui.screenrecord.UPDATE_STATE");
                            RecordingController recordingController3 = RecordingController.this;
                            recordingController3.mBroadcastDispatcher.registerReceiver(recordingController3.mStateChangeReceiver, intentFilter2, (Executor) null, UserHandle.ALL);
                            Log.d("RecordingController", "sent start intent");
                        } catch (PendingIntent.CanceledException e) {
                            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Pending intent was cancelled: ");
                            m.append(e.getMessage());
                            Log.e("RecordingController", m.toString());
                        }
                    }

                    public final void onTick(long j) {
                        Iterator<RecordingStateChangeCallback> it = RecordingController.this.mListeners.iterator();
                        while (it.hasNext()) {
                            it.next().onCountdown(j);
                        }
                    }
                };
                recordingController.mCountDownTimer = r8;
                r8.start();
                screenRecordDialog.dismiss();
                return;
            case 2:
                NotificationInfo notificationInfo = (NotificationInfo) this.f$0;
                int i5 = NotificationInfo.$r8$clinit;
                Objects.requireNonNull(notificationInfo);
                notificationInfo.mChosenImportance = 3;
                notificationInfo.mIsAutomaticChosen = false;
                notificationInfo.applyAlertingBehavior(0, true);
                return;
            default:
                GameMenuActivity gameMenuActivity = (GameMenuActivity) this.f$0;
                IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
                Objects.requireNonNull(gameMenuActivity);
                gameMenuActivity.onGameModeSelectionChanged(view);
                return;
        }
    }
}
