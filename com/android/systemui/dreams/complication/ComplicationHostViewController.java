package com.android.systemui.dreams.complication;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.util.ViewController;
import java.util.HashMap;
import java.util.Objects;

public final class ComplicationHostViewController extends ViewController<ConstraintLayout> {
    public final ComplicationCollectionViewModel mComplicationCollectionViewModel;
    public final HashMap<ComplicationId, Complication.ViewHolder> mComplications = new HashMap<>();
    public final ComplicationLayoutEngine mLayoutEngine;
    public final LifecycleOwner mLifecycleOwner;

    public final void onViewAttached() {
    }

    public final void onViewDetached() {
    }

    public final void onInit() {
        ComplicationCollectionViewModel complicationCollectionViewModel = this.mComplicationCollectionViewModel;
        Objects.requireNonNull(complicationCollectionViewModel);
        complicationCollectionViewModel.mComplications.observe(this.mLifecycleOwner, new ComplicationHostViewController$$ExternalSyntheticLambda0(this));
    }

    public ComplicationHostViewController(ConstraintLayout constraintLayout, ComplicationLayoutEngine complicationLayoutEngine, LifecycleOwner lifecycleOwner, ComplicationCollectionViewModel complicationCollectionViewModel) {
        super(constraintLayout);
        this.mLayoutEngine = complicationLayoutEngine;
        this.mLifecycleOwner = lifecycleOwner;
        this.mComplicationCollectionViewModel = complicationCollectionViewModel;
    }
}
