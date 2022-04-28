package com.android.systemui.controls.p004ui;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.controls.Control;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.StatusBehavior$showNotFoundDialog$builder$1$1 */
/* compiled from: StatusBehavior.kt */
public final class StatusBehavior$showNotFoundDialog$builder$1$1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ ControlWithState $cws;
    public final /* synthetic */ AlertDialog.Builder $this_apply;

    public StatusBehavior$showNotFoundDialog$builder$1$1(ControlWithState controlWithState, AlertDialog.Builder builder, ControlViewHolder controlViewHolder) {
        this.$cws = controlWithState;
        this.$this_apply = builder;
        this.$cvh = controlViewHolder;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        try {
            ControlWithState controlWithState = this.$cws;
            Objects.requireNonNull(controlWithState);
            Control control = controlWithState.control;
            if (control != null) {
                PendingIntent appIntent = control.getAppIntent();
                if (appIntent != null) {
                    appIntent.send();
                }
            }
            this.$this_apply.getContext().sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        } catch (PendingIntent.CanceledException unused) {
            this.$cvh.setErrorStatus();
        }
        dialogInterface.dismiss();
    }
}
