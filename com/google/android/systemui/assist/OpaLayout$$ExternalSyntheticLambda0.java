package com.google.android.systemui.assist;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OpaLayout$$ExternalSyntheticLambda0 implements View.OnLongClickListener {
    public final /* synthetic */ OpaLayout f$0;
    public final /* synthetic */ View.OnLongClickListener f$1;

    public /* synthetic */ OpaLayout$$ExternalSyntheticLambda0(OpaLayout opaLayout, View.OnLongClickListener onLongClickListener) {
        this.f$0 = opaLayout;
        this.f$1 = onLongClickListener;
    }

    public final boolean onLongClick(View view) {
        OpaLayout opaLayout = this.f$0;
        View.OnLongClickListener onLongClickListener = this.f$1;
        int i = OpaLayout.$r8$clinit;
        Objects.requireNonNull(opaLayout);
        return onLongClickListener.onLongClick(opaLayout.mHome);
    }
}
