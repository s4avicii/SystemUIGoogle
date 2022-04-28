package androidx.lifecycle;

import com.android.systemui.dreams.complication.ComplicationCollectionViewModel;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda1;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Transformations$1 implements Observer<Object> {
    public final /* synthetic */ ComplicationCollectionViewModel$$ExternalSyntheticLambda0 val$mapFunction;
    public final /* synthetic */ MediatorLiveData val$result;

    public Transformations$1(MediatorLiveData mediatorLiveData, ComplicationCollectionViewModel$$ExternalSyntheticLambda0 complicationCollectionViewModel$$ExternalSyntheticLambda0) {
        this.val$result = mediatorLiveData;
        this.val$mapFunction = complicationCollectionViewModel$$ExternalSyntheticLambda0;
    }

    public final void onChanged(Object obj) {
        MediatorLiveData mediatorLiveData = this.val$result;
        ComplicationCollectionViewModel$$ExternalSyntheticLambda0 complicationCollectionViewModel$$ExternalSyntheticLambda0 = this.val$mapFunction;
        Objects.requireNonNull(complicationCollectionViewModel$$ExternalSyntheticLambda0);
        ComplicationCollectionViewModel complicationCollectionViewModel = complicationCollectionViewModel$$ExternalSyntheticLambda0.f$0;
        Objects.requireNonNull(complicationCollectionViewModel);
        mediatorLiveData.setValue((Collection) ((Collection) obj).stream().map(new ComplicationCollectionViewModel$$ExternalSyntheticLambda1(complicationCollectionViewModel)).collect(Collectors.toSet()));
    }
}
