package com.android.systemui.controls.p004ui;

import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$4 */
/* compiled from: ChallengeDialogs.kt */
public final class ChallengeDialogs$createPinDialog$2$4 implements DialogInterface.OnShowListener {
    public final /* synthetic */ int $instructions;
    public final /* synthetic */ ChallengeDialogs$createPinDialog$1 $this_apply;
    public final /* synthetic */ boolean $useAlphaNumeric;

    public ChallengeDialogs$createPinDialog$2$4(ChallengeDialogs$createPinDialog$1 challengeDialogs$createPinDialog$1, int i, boolean z) {
        this.$this_apply = challengeDialogs$createPinDialog$1;
        this.$instructions = i;
        this.$useAlphaNumeric = z;
    }

    public final void onShow(DialogInterface dialogInterface) {
        final EditText editText = (EditText) this.$this_apply.requireViewById(C1777R.C1779id.controls_pin_input);
        editText.setHint(this.$instructions);
        final CheckBox checkBox = (CheckBox) this.$this_apply.requireViewById(C1777R.C1779id.controls_pin_use_alpha);
        checkBox.setChecked(this.$useAlphaNumeric);
        if (checkBox.isChecked()) {
            editText.setInputType(129);
        } else {
            editText.setInputType(18);
        }
        ((CheckBox) this.$this_apply.requireViewById(C1777R.C1779id.controls_pin_use_alpha)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                EditText editText = editText;
                if (checkBox.isChecked()) {
                    editText.setInputType(129);
                } else {
                    editText.setInputType(18);
                }
            }
        });
        editText.requestFocus();
    }
}
