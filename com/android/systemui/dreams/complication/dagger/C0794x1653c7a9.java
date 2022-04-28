package com.android.systemui.dreams.complication.dagger;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel;
import com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda2;
import dagger.internal.Factory;

/* renamed from: com.android.systemui.dreams.complication.dagger.ComplicationModule_ProvidesComplicationCollectionViewModelFactory */
public final class C0794x1653c7a9 implements Factory<ComplicationCollectionViewModel> {
    public static ComplicationCollectionViewModel providesComplicationCollectionViewModel(ViewModelStore viewModelStore, ComplicationCollectionViewModel complicationCollectionViewModel) {
        return (ComplicationCollectionViewModel) new ViewModelProvider(viewModelStore, new DaggerViewModelProviderFactory(new NgaUiController$$ExternalSyntheticLambda2(complicationCollectionViewModel))).get(ComplicationCollectionViewModel.class);
    }
}
