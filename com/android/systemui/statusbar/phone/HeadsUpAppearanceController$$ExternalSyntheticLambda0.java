package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ View f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda0(View view, int i, KeyguardUpdateMonitor$$ExternalSyntheticLambda6 keyguardUpdateMonitor$$ExternalSyntheticLambda6) {
        this.f$0 = view;
        this.f$1 = i;
        this.f$2 = keyguardUpdateMonitor$$ExternalSyntheticLambda6;
    }

    public final void run() {
        View view = this.f$0;
        int i = this.f$1;
        Runnable runnable = this.f$2;
        view.setVisibility(i);
        if (runnable != null) {
            runnable.run();
        }
    }
}
