package com.android.settingslib;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PrimarySwitchPreference$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public static final /* synthetic */ PrimarySwitchPreference$$ExternalSyntheticLambda0 INSTANCE = new PrimarySwitchPreference$$ExternalSyntheticLambda0();

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 2) {
            return true;
        }
        return false;
    }
}
