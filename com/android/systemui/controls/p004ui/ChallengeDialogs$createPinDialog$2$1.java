package com.android.systemui.controls.p004ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.service.controls.actions.ControlAction;
import android.widget.EditText;
import androidx.slice.view.R$id;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$1 */
/* compiled from: ChallengeDialogs.kt */
public final class ChallengeDialogs$createPinDialog$2$1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ ControlAction $lastAction;

    public ChallengeDialogs$createPinDialog$2$1(ControlViewHolder controlViewHolder, ControlAction controlAction) {
        this.$cvh = controlViewHolder;
        this.$lastAction = controlAction;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (dialogInterface instanceof Dialog) {
            Dialog dialog = (Dialog) dialogInterface;
            dialog.requireViewById(C1777R.C1779id.controls_pin_input);
            this.$cvh.action(R$id.access$addChallengeValue(this.$lastAction, ((EditText) dialog.requireViewById(C1777R.C1779id.controls_pin_input)).getText().toString()));
            dialogInterface.dismiss();
        }
    }
}
