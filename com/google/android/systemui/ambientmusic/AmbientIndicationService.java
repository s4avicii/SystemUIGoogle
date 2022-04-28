package com.google.android.systemui.ambientmusic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import java.util.Objects;

public final class AmbientIndicationService extends BroadcastReceiver {
    public final AlarmManager mAlarmManager;
    public final AmbientIndicationContainer mAmbientIndicationContainer;
    public final C21631 mCallback = new KeyguardUpdateMonitorCallback() {
        public final void onUserSwitchComplete(int i) {
            AmbientIndicationService.this.onUserSwitched();
        }
    };
    public final Context mContext;
    public final AmbientIndicationService$$ExternalSyntheticLambda0 mHideIndicationListener;
    public boolean mStarted;

    public final void onReceive(Context context, Intent intent) {
        Intent intent2 = intent;
        if (!isForCurrentUser()) {
            Log.i("AmbientIndication", "Suppressing ambient, not for this user.");
            return;
        }
        int intExtra = intent2.getIntExtra("com.google.android.ambientindication.extra.VERSION", 0);
        boolean z = true;
        if (intExtra != 1) {
            Log.e("AmbientIndication", "AmbientIndicationApi.EXTRA_VERSION is " + 1 + ", but received an intent with version " + intExtra + ", dropping intent.");
            z = false;
        }
        if (z) {
            String action = intent.getAction();
            Objects.requireNonNull(action);
            if (action.equals("com.google.android.ambientindication.action.AMBIENT_INDICATION_HIDE")) {
                this.mAlarmManager.cancel(this.mHideIndicationListener);
                AmbientIndicationContainer ambientIndicationContainer = this.mAmbientIndicationContainer;
                Objects.requireNonNull(ambientIndicationContainer);
                ambientIndicationContainer.setAmbientMusic((CharSequence) null, (PendingIntent) null, (PendingIntent) null, 0, false, (String) null);
                Log.i("AmbientIndication", "Hiding ambient indication.");
            } else if (action.equals("com.google.android.ambientindication.action.AMBIENT_INDICATION_SHOW")) {
                long min = Math.min(Math.max(intent2.getLongExtra("com.google.android.ambientindication.extra.TTL_MILLIS", 180000), 0), 180000);
                boolean booleanExtra = intent2.getBooleanExtra("com.google.android.ambientindication.extra.SKIP_UNLOCK", false);
                int intExtra2 = intent2.getIntExtra("com.google.android.ambientindication.extra.ICON_OVERRIDE", 0);
                String stringExtra = intent2.getStringExtra("com.google.android.ambientindication.extra.ICON_DESCRIPTION");
                this.mAmbientIndicationContainer.setAmbientMusic(intent2.getCharSequenceExtra("com.google.android.ambientindication.extra.TEXT"), (PendingIntent) intent2.getParcelableExtra("com.google.android.ambientindication.extra.OPEN_INTENT"), (PendingIntent) intent2.getParcelableExtra("com.google.android.ambientindication.extra.FAVORITING_INTENT"), intExtra2, booleanExtra, stringExtra);
                this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + min, "AmbientIndication", this.mHideIndicationListener, (Handler) null);
                Log.i("AmbientIndication", "Showing ambient indication.");
            }
        }
    }

    public void onUserSwitched() {
        AmbientIndicationContainer ambientIndicationContainer = this.mAmbientIndicationContainer;
        Objects.requireNonNull(ambientIndicationContainer);
        ambientIndicationContainer.setAmbientMusic((CharSequence) null, (PendingIntent) null, (PendingIntent) null, 0, false, (String) null);
    }

    public AmbientIndicationService(Context context, AmbientIndicationContainer ambientIndicationContainer, AlarmManager alarmManager) {
        this.mContext = context;
        this.mAmbientIndicationContainer = ambientIndicationContainer;
        this.mAlarmManager = alarmManager;
        this.mHideIndicationListener = new AmbientIndicationService$$ExternalSyntheticLambda0(this);
    }

    public int getCurrentUser() {
        return KeyguardUpdateMonitor.getCurrentUser();
    }

    public boolean isForCurrentUser() {
        if (getSendingUserId() == getCurrentUser() || getSendingUserId() == -1) {
            return true;
        }
        return false;
    }
}
