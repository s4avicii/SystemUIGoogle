package com.android.settingslib;

import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.concurrent.Callable;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RestrictedPreferenceHelper$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ TextView f$0;

    public /* synthetic */ RestrictedPreferenceHelper$$ExternalSyntheticLambda0(TextView textView) {
        this.f$0 = textView;
    }

    public final Object call() {
        return this.f$0.getContext().getString(C1777R.string.disabled_by_admin_summary_text);
    }
}
