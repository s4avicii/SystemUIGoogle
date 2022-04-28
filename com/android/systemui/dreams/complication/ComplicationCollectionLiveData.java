package com.android.systemui.dreams.complication;

import androidx.lifecycle.LiveData;
import com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda20;
import java.util.Collection;
import java.util.Objects;

public final class ComplicationCollectionLiveData extends LiveData<Collection<Complication>> {
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final C07891 mStateControllerCallback = new DreamOverlayStateController.Callback() {
        public final void onAvailableComplicationTypesChanged() {
            ComplicationCollectionLiveData complicationCollectionLiveData = ComplicationCollectionLiveData.this;
            complicationCollectionLiveData.setValue(complicationCollectionLiveData.mDreamOverlayStateController.getComplications());
        }

        public final void onComplicationsChanged() {
            ComplicationCollectionLiveData complicationCollectionLiveData = ComplicationCollectionLiveData.this;
            complicationCollectionLiveData.setValue(complicationCollectionLiveData.mDreamOverlayStateController.getComplications());
        }
    };

    public final void onActive() {
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        C07891 r1 = this.mStateControllerCallback;
        Objects.requireNonNull(dreamOverlayStateController);
        dreamOverlayStateController.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda20(dreamOverlayStateController, r1, 2));
        setValue(this.mDreamOverlayStateController.getComplications());
    }

    public final void onInactive() {
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        C07891 r4 = this.mStateControllerCallback;
        Objects.requireNonNull(dreamOverlayStateController);
        dreamOverlayStateController.mExecutor.execute(new SystemUIApplication$$ExternalSyntheticLambda1(dreamOverlayStateController, r4, 1));
    }

    public ComplicationCollectionLiveData(DreamOverlayStateController dreamOverlayStateController) {
        this.mDreamOverlayStateController = dreamOverlayStateController;
    }
}
