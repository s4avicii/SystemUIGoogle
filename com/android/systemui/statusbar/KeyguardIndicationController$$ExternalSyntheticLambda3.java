package com.android.systemui.statusbar;

import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;
import java.util.concurrent.Callable;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda3 implements Callable {
    public final /* synthetic */ Resources f$0;
    public final /* synthetic */ CharSequence f$1;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda3(Resources resources, CharSequence charSequence) {
        this.f$0 = resources;
        this.f$1 = charSequence;
    }

    public final Object call() {
        return this.f$0.getString(C1777R.string.do_disclosure_with_name, new Object[]{this.f$1});
    }
}
