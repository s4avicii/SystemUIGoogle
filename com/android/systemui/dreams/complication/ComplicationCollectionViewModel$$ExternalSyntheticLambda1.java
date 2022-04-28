package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.complication.ComplicationId;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ComplicationCollectionViewModel$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ ComplicationCollectionViewModel f$0;

    public /* synthetic */ ComplicationCollectionViewModel$$ExternalSyntheticLambda1(ComplicationCollectionViewModel complicationCollectionViewModel) {
        this.f$0 = complicationCollectionViewModel;
    }

    public final Object apply(Object obj) {
        ComplicationCollectionViewModel complicationCollectionViewModel = this.f$0;
        Complication complication = (Complication) obj;
        Objects.requireNonNull(complicationCollectionViewModel);
        ComplicationViewModelTransformer complicationViewModelTransformer = complicationCollectionViewModel.mTransformer;
        Objects.requireNonNull(complicationViewModelTransformer);
        if (!complicationViewModelTransformer.mComplicationIdMapping.containsKey(complication)) {
            HashMap<Complication, ComplicationId> hashMap = complicationViewModelTransformer.mComplicationIdMapping;
            ComplicationId.Factory factory = complicationViewModelTransformer.mComplicationIdFactory;
            Objects.requireNonNull(factory);
            int i = factory.mNextId;
            factory.mNextId = i + 1;
            hashMap.put(complication, new ComplicationId(i));
        }
        ComplicationId complicationId = complicationViewModelTransformer.mComplicationIdMapping.get(complication);
        return (ComplicationViewModel) complicationViewModelTransformer.mViewModelComponentFactory.create(complication, complicationId).getViewModelProvider().get(complicationId.toString(), ComplicationViewModel.class);
    }
}
