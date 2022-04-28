package com.android.systemui.p006qs;

import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSFragment$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ QSFragment f$0;

    public /* synthetic */ QSFragment$$ExternalSyntheticLambda0(QSFragment qSFragment) {
        this.f$0 = qSFragment;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        QSFragment qSFragment = this.f$0;
        Objects.requireNonNull(qSFragment);
        qSFragment.updateQsBounds();
    }
}
