package com.android.settingslib.inputmethod;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda0 implements DialogInterface.OnCancelListener {
    public final /* synthetic */ InputMethodPreference f$0;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda0(InputMethodPreference inputMethodPreference) {
        this.f$0 = inputMethodPreference;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        InputMethodPreference inputMethodPreference = this.f$0;
        int i = InputMethodPreference.$r8$clinit;
        Objects.requireNonNull(inputMethodPreference);
        inputMethodPreference.setCheckedInternal(false);
    }
}
