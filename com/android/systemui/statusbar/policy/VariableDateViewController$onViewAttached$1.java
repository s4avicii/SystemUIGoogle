package com.android.systemui.statusbar.policy;

import com.android.systemui.util.ViewController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: VariableDateViewController.kt */
public /* synthetic */ class VariableDateViewController$onViewAttached$1 extends FunctionReferenceImpl implements Function0<Unit> {
    public VariableDateViewController$onViewAttached$1(ViewController viewController) {
        super(0, viewController, VariableDateViewController.class, "updateClock", "updateClock()V", 0);
    }

    public final Object invoke() {
        VariableDateViewController.access$updateClock((VariableDateViewController) this.receiver);
        return Unit.INSTANCE;
    }
}
