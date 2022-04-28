package com.android.systemui;

import android.content.Intent;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ Intent f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda7(Intent intent, boolean z) {
        this.f$0 = intent;
        this.f$1 = z;
    }

    public final void accept(Object obj) {
        ((StatusBar) obj).startActivity(this.f$0, this.f$1);
    }
}
