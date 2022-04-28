package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;
import com.android.internal.logging.UiEventLogger;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;

/* compiled from: ManageMedia.kt */
public final class ManageMedia extends UserAction {
    public final AudioManager audioManager;
    public final String tag = "Columbus/ManageMedia";
    public final UiEventLogger uiEventLogger;

    public final boolean availableOnLockscreen() {
        return true;
    }

    public final boolean availableOnScreenOff() {
        return true;
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        boolean z;
        if (this.audioManager.isMusicActive() || this.audioManager.isMusicActiveRemotely()) {
            z = true;
        } else {
            z = false;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        this.audioManager.dispatchMediaKeyEvent(new KeyEvent(uptimeMillis, uptimeMillis, 0, 85, 0));
        this.audioManager.dispatchMediaKeyEvent(new KeyEvent(uptimeMillis, uptimeMillis, 1, 85, 0));
        if (z) {
            this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_PAUSE_MEDIA);
        } else {
            this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_PLAY_MEDIA);
        }
    }

    public ManageMedia(Context context, AudioManager audioManager2, UiEventLogger uiEventLogger2) {
        super(context);
        this.audioManager = audioManager2;
        this.uiEventLogger = uiEventLogger2;
        setAvailable(true);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
