package com.android.systemui.controls.p004ui;

import android.app.AlertDialog;
import android.service.controls.actions.ControlAction;
import android.util.Log;
import androidx.slice.view.R$id;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.controller.ControlInfo;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$onActionResponse$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$onActionResponse$1 implements Runnable {
    public final /* synthetic */ ControlKey $key;
    public final /* synthetic */ int $response;
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$onActionResponse$1(ControlsUiControllerImpl controlsUiControllerImpl, ControlKey controlKey, int i) {
        this.this$0 = controlsUiControllerImpl;
        this.$key = controlKey;
        this.$response = i;
    }

    public final void run() {
        boolean z;
        ControlViewHolder controlViewHolder = (ControlViewHolder) this.this$0.controlViewsById.get(this.$key);
        if (controlViewHolder != null) {
            int i = this.$response;
            ControlActionCoordinator controlActionCoordinator = controlViewHolder.controlActionCoordinator;
            ControlWithState cws = controlViewHolder.getCws();
            Objects.requireNonNull(cws);
            ControlInfo controlInfo = cws.f48ci;
            Objects.requireNonNull(controlInfo);
            controlActionCoordinator.enableActionOnTouch(controlInfo.controlId);
            if (controlViewHolder.lastChallengeDialog != null) {
                z = true;
            } else {
                z = false;
            }
            AlertDialog alertDialog = null;
            if (i == 0) {
                controlViewHolder.lastChallengeDialog = null;
                controlViewHolder.setErrorStatus();
            } else if (i == 1) {
                controlViewHolder.lastChallengeDialog = null;
            } else if (i == 2) {
                controlViewHolder.lastChallengeDialog = null;
                controlViewHolder.setErrorStatus();
            } else if (i == 3) {
                Function0<Unit> function0 = controlViewHolder.onDialogCancel;
                ControlAction controlAction = controlViewHolder.lastAction;
                if (controlAction == null) {
                    Log.e("ControlsUiController", "Confirmation Dialog attempted but no last action is set. Will not show");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(controlViewHolder.context, 16974545);
                    builder.setTitle(controlViewHolder.context.getResources().getString(C1777R.string.controls_confirmation_message, new Object[]{controlViewHolder.title.getText()}));
                    builder.setPositiveButton(17039370, new ChallengeDialogs$createConfirmationDialog$builder$1$1(controlViewHolder, controlAction));
                    builder.setNegativeButton(17039360, new ChallengeDialogs$createConfirmationDialog$builder$1$2(function0));
                    alertDialog = builder.create();
                    alertDialog.getWindow().setType(2020);
                }
                controlViewHolder.lastChallengeDialog = alertDialog;
                if (alertDialog != null) {
                    alertDialog.show();
                }
            } else if (i == 4) {
                ChallengeDialogs$createPinDialog$1 createPinDialog = R$id.createPinDialog(controlViewHolder, false, z, controlViewHolder.onDialogCancel);
                controlViewHolder.lastChallengeDialog = createPinDialog;
                if (createPinDialog != null) {
                    createPinDialog.show();
                }
            } else if (i == 5) {
                ChallengeDialogs$createPinDialog$1 createPinDialog2 = R$id.createPinDialog(controlViewHolder, true, z, controlViewHolder.onDialogCancel);
                controlViewHolder.lastChallengeDialog = createPinDialog2;
                if (createPinDialog2 != null) {
                    createPinDialog2.show();
                }
            }
        }
    }
}
