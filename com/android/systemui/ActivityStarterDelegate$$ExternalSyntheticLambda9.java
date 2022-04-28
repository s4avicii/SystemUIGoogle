package com.android.systemui;

import android.content.Intent;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ Intent f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ActivityStarter.Callback f$2;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda9(Intent intent, boolean z, ActivityStarter.Callback callback) {
        this.f$0 = intent;
        this.f$1 = z;
        this.f$2 = callback;
    }

    public final void accept(Object obj) {
        ((StatusBar) obj).startActivity(this.f$0, this.f$1, this.f$2);
    }
}
