package com.android.systemui.statusbar;

import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OperatorNameViewController$$ExternalSyntheticLambda0 implements DarkIconDispatcher.DarkReceiver {
    public final /* synthetic */ OperatorNameViewController f$0;

    public /* synthetic */ OperatorNameViewController$$ExternalSyntheticLambda0(OperatorNameViewController operatorNameViewController) {
        this.f$0 = operatorNameViewController;
    }

    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        OperatorNameViewController operatorNameViewController = this.f$0;
        Objects.requireNonNull(operatorNameViewController);
        T t = operatorNameViewController.mView;
        ((OperatorNameView) t).setTextColor(DarkIconDispatcher.getTint(arrayList, t, i));
    }
}
