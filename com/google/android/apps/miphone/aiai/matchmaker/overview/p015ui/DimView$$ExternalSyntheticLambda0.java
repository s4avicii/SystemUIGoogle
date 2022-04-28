package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.DimView$$ExternalSyntheticLambda0 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DimView$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public final /* synthetic */ DimView f$0;

    public /* synthetic */ DimView$$ExternalSyntheticLambda0(DimView dimView) {
        this.f$0 = dimView;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        Objects.requireNonNull(this.f$0);
        if (motionEvent.getAction() != 0) {
            return false;
        }
        Log.i("AiAiSuggestUi", "Handle touch for the background scrim.");
        return true;
    }
}
