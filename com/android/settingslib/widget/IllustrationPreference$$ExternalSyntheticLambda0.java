package com.android.settingslib.widget;

import android.util.Log;
import com.airbnb.lottie.LottieListener;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class IllustrationPreference$$ExternalSyntheticLambda0 implements LottieListener {
    public final /* synthetic */ int f$0;

    public /* synthetic */ IllustrationPreference$$ExternalSyntheticLambda0(int i) {
        this.f$0 = i;
    }

    public final void onResult(Object obj) {
        int i = this.f$0;
        Log.w("IllustrationPreference", "Invalid illustration resource id: " + i, (Throwable) obj);
    }
}
