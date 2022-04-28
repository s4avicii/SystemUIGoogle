package com.android.settingslib.inputmethod;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda3 implements DialogInterface.OnClickListener {
    public final /* synthetic */ InputMethodPreference f$0;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda3(InputMethodPreference inputMethodPreference) {
        this.f$0 = inputMethodPreference;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        InputMethodPreference inputMethodPreference = this.f$0;
        int i2 = InputMethodPreference.$r8$clinit;
        Objects.requireNonNull(inputMethodPreference);
        if (inputMethodPreference.mImi.getServiceInfo().directBootAware || inputMethodPreference.isTv()) {
            inputMethodPreference.setCheckedInternal(true);
        } else {
            inputMethodPreference.showDirectBootWarnDialog();
        }
    }
}
