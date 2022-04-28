package com.android.systemui.dreams.complication;

import androidx.lifecycle.ViewModel;
import com.android.systemui.dreams.complication.Complication;

public final class ComplicationViewModel extends ViewModel {
    public final Complication mComplication;
    public final Complication.Host mHost;
    public final ComplicationId mId;

    public ComplicationViewModel(Complication complication, ComplicationId complicationId, Complication.Host host) {
        this.mComplication = complication;
        this.mId = complicationId;
        this.mHost = host;
    }
}
