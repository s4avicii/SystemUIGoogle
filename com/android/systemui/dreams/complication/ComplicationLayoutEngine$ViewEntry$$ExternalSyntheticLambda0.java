package com.android.systemui.dreams.complication;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ ComplicationLayoutEngine.ViewEntry f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ ConstraintLayout.LayoutParams f$3;
    public final /* synthetic */ View f$4;
    public final /* synthetic */ boolean f$5;

    public /* synthetic */ ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0(ComplicationLayoutEngine.ViewEntry viewEntry, boolean z, int i, Constraints.LayoutParams layoutParams, View view, boolean z2) {
        this.f$0 = viewEntry;
        this.f$1 = z;
        this.f$2 = i;
        this.f$3 = layoutParams;
        this.f$4 = view;
        this.f$5 = z2;
    }

    public final void accept(Object obj) {
        ComplicationLayoutEngine.ViewEntry viewEntry = this.f$0;
        boolean z = this.f$1;
        int i = this.f$2;
        ConstraintLayout.LayoutParams layoutParams = this.f$3;
        View view = this.f$4;
        boolean z2 = this.f$5;
        Objects.requireNonNull(viewEntry);
        int intValue = ((Integer) obj).intValue();
        if (intValue == 1) {
            if (z || i != 2) {
                layoutParams.topToTop = 0;
            } else {
                layoutParams.topToBottom = view.getId();
            }
            if (z2 && (i == 8 || i == 4)) {
                layoutParams.endToStart = C1777R.C1779id.complication_top_guide;
            }
        } else if (intValue == 2) {
            if (z || i != 1) {
                layoutParams.bottomToBottom = 0;
            } else {
                layoutParams.bottomToTop = view.getId();
            }
            if (z2 && (i == 8 || i == 4)) {
                layoutParams.topToBottom = C1777R.C1779id.complication_bottom_guide;
            }
        } else if (intValue == 4) {
            if (z || i != 8) {
                layoutParams.startToStart = 0;
            } else {
                layoutParams.startToEnd = view.getId();
            }
            if (z2 && (i == 2 || i == 1)) {
                layoutParams.endToStart = C1777R.C1779id.complication_start_guide;
            }
        } else if (intValue == 8) {
            if (z || i != 4) {
                layoutParams.endToEnd = 0;
            } else {
                layoutParams.endToStart = view.getId();
            }
            if (z2 && (i == 1 || i == 2)) {
                layoutParams.startToEnd = C1777R.C1779id.complication_end_guide;
            }
        }
        if (z) {
            return;
        }
        if (i == 1) {
            layoutParams.setMargins(0, 0, 0, viewEntry.mMargin);
        } else if (i == 2) {
            layoutParams.setMargins(0, viewEntry.mMargin, 0, 0);
        } else if (i == 4) {
            layoutParams.setMarginEnd(viewEntry.mMargin);
        } else if (i == 8) {
            layoutParams.setMarginStart(viewEntry.mMargin);
        }
    }
}
