package com.android.systemui.assist;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AssistManager$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ AssistManager f$0;

    public /* synthetic */ AssistManager$$ExternalSyntheticLambda0(AssistManager assistManager) {
        this.f$0 = assistManager;
    }

    public final Object get() {
        AssistManager assistManager = this.f$0;
        Objects.requireNonNull(assistManager);
        return Boolean.valueOf(assistManager.mAssistUtils.activeServiceSupportsLaunchFromKeyguard());
    }
}
