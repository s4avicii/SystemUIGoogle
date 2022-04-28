package com.android.keyguard;

import android.app.Presentation;
import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardDisplayManager$$ExternalSyntheticLambda0 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ KeyguardDisplayManager f$0;
    public final /* synthetic */ Presentation f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KeyguardDisplayManager$$ExternalSyntheticLambda0(KeyguardDisplayManager keyguardDisplayManager, Presentation presentation, int i) {
        this.f$0 = keyguardDisplayManager;
        this.f$1 = presentation;
        this.f$2 = i;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        KeyguardDisplayManager keyguardDisplayManager = this.f$0;
        Presentation presentation = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(keyguardDisplayManager);
        if (presentation.equals(keyguardDisplayManager.mPresentations.get(i))) {
            keyguardDisplayManager.mPresentations.remove(i);
        }
    }
}
