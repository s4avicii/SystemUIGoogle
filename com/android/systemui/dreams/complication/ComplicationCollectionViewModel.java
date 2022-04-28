package com.android.systemui.dreams.complication;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations$1;
import androidx.lifecycle.ViewModel;

public final class ComplicationCollectionViewModel extends ViewModel {
    public final MediatorLiveData mComplications;
    public final ComplicationViewModelTransformer mTransformer;

    public ComplicationCollectionViewModel(ComplicationCollectionLiveData complicationCollectionLiveData, ComplicationViewModelTransformer complicationViewModelTransformer) {
        boolean z;
        ComplicationCollectionViewModel$$ExternalSyntheticLambda0 complicationCollectionViewModel$$ExternalSyntheticLambda0 = new ComplicationCollectionViewModel$$ExternalSyntheticLambda0(this);
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        Transformations$1 transformations$1 = new Transformations$1(mediatorLiveData, complicationCollectionViewModel$$ExternalSyntheticLambda0);
        MediatorLiveData.Source source = new MediatorLiveData.Source(complicationCollectionLiveData, transformations$1);
        MediatorLiveData.Source putIfAbsent = mediatorLiveData.mSources.putIfAbsent(complicationCollectionLiveData, source);
        if (putIfAbsent == null || putIfAbsent.mObserver == transformations$1) {
            if (putIfAbsent == null) {
                if (mediatorLiveData.mActiveCount > 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    complicationCollectionLiveData.observeForever(source);
                }
            }
            this.mComplications = mediatorLiveData;
            this.mTransformer = complicationViewModelTransformer;
            return;
        }
        throw new IllegalArgumentException("This source was already added with the different observer");
    }
}
