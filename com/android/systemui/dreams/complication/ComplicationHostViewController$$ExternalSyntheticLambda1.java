package com.android.systemui.dreams.complication;

import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ComplicationHostViewController$$ExternalSyntheticLambda1 implements Function {
    public static final /* synthetic */ ComplicationHostViewController$$ExternalSyntheticLambda1 INSTANCE = new ComplicationHostViewController$$ExternalSyntheticLambda1();

    public final Object apply(Object obj) {
        ComplicationViewModel complicationViewModel = (ComplicationViewModel) obj;
        Objects.requireNonNull(complicationViewModel);
        return complicationViewModel.mId;
    }
}
