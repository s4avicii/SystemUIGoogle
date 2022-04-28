package com.android.systemui.p006qs;

import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSFragment$$ExternalSyntheticLambda1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ QSFragment f$0;

    public /* synthetic */ QSFragment$$ExternalSyntheticLambda1(QSFragment qSFragment) {
        this.f$0 = qSFragment;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        boolean z;
        QSFragment qSFragment = this.f$0;
        Objects.requireNonNull(qSFragment);
        if (i6 - i8 != i2 - i4) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            qSFragment.setQsExpansion(qSFragment.mLastQSExpansion, qSFragment.mLastPanelFraction, qSFragment.mLastHeaderTranslation, qSFragment.mSquishinessFraction);
        }
    }
}
