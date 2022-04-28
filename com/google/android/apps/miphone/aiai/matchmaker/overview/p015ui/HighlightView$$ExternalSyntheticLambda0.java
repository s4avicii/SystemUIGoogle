package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.view.MotionEvent;
import android.view.View;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.HighlightView;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.HighlightView$$ExternalSyntheticLambda0 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HighlightView$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public final /* synthetic */ HighlightView f$0;

    public /* synthetic */ HighlightView$$ExternalSyntheticLambda0(HighlightView highlightView) {
        this.f$0 = highlightView;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        HighlightView highlightView = this.f$0;
        Objects.requireNonNull(highlightView);
        if (motionEvent.getAction() != 0) {
            return false;
        }
        highlightView.performClick();
        motionEvent.getX();
        motionEvent.getY();
        Iterator it = highlightView.listeners.iterator();
        while (it.hasNext()) {
            ((HighlightView.TapListener) it.next()).onTap();
        }
        return true;
    }
}
