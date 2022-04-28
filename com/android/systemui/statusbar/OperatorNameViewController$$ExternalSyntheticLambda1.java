package com.android.systemui.statusbar;

import com.android.systemui.tuner.TunerService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OperatorNameViewController$$ExternalSyntheticLambda1 implements TunerService.Tunable {
    public final /* synthetic */ OperatorNameViewController f$0;

    public /* synthetic */ OperatorNameViewController$$ExternalSyntheticLambda1(OperatorNameViewController operatorNameViewController) {
        this.f$0 = operatorNameViewController;
    }

    public final void onTuningChanged(String str, String str2) {
        OperatorNameViewController operatorNameViewController = this.f$0;
        Objects.requireNonNull(operatorNameViewController);
        operatorNameViewController.update();
    }
}
