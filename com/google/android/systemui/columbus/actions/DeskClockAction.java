package com.google.android.systemui.columbus.actions;

import android.app.ActivityOptions;
import android.app.IActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeskClockAction.kt */
public abstract class DeskClockAction extends Action {
    public boolean alertFiring;
    public final DeskClockAction$alertReceiver$1 alertReceiver = new DeskClockAction$alertReceiver$1(this);
    public boolean receiverRegistered;
    public final SilenceAlertsDisabled silenceAlertsDisabled;

    public abstract Intent createDismissIntent();

    public abstract String getAlertAction();

    public abstract String getDoneAction();

    public final void updateBroadcastReceiver() {
        this.alertFiring = false;
        if (this.receiverRegistered) {
            this.context.unregisterReceiver(this.alertReceiver);
            this.receiverRegistered = false;
        }
        if (!this.silenceAlertsDisabled.isBlocking()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(getAlertAction());
            intentFilter.addAction(getDoneAction());
            this.context.registerReceiverAsUser(this.alertReceiver, UserHandle.CURRENT, intentFilter, "com.android.systemui.permission.SEND_ALERT_BROADCASTS", (Handler) null, 2);
            this.receiverRegistered = true;
        }
        setAvailable(this.alertFiring);
    }

    public DeskClockAction(Context context, SilenceAlertsDisabled silenceAlertsDisabled2, IActivityManager iActivityManager) {
        super(context, (Set<? extends FeedbackEffect>) null);
        this.silenceAlertsDisabled = silenceAlertsDisabled2;
        DeskClockAction$gateListener$1 deskClockAction$gateListener$1 = new DeskClockAction$gateListener$1(this);
        DeskClockAction$userSwitchCallback$1 deskClockAction$userSwitchCallback$1 = new DeskClockAction$userSwitchCallback$1(this);
        silenceAlertsDisabled2.registerListener(deskClockAction$gateListener$1);
        try {
            iActivityManager.registerUserSwitchObserver(deskClockAction$userSwitchCallback$1, "Columbus/DeskClockAct");
        } catch (RemoteException e) {
            Log.e("Columbus/DeskClockAct", "Failed to register user switch observer", e);
        }
        updateBroadcastReceiver();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" [receiverRegistered -> ");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.receiverRegistered, ']');
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        Intent createDismissIntent = createDismissIntent();
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
        createDismissIntent.setFlags(268435456);
        createDismissIntent.putExtra("android.intent.extra.REFERRER", Uri.parse(Intrinsics.stringPlus("android-app://", this.context.getPackageName())));
        try {
            this.context.startActivityAsUser(createDismissIntent, makeBasic.toBundle(), UserHandle.CURRENT);
        } catch (ActivityNotFoundException e) {
            Log.e("Columbus/DeskClockAct", "Failed to dismiss alert", e);
        }
        this.alertFiring = false;
        setAvailable(false);
    }
}
