package com.google.android.systemui.communal.dreams.dagger;

import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;

public interface SetupDreamComponent$Factory {
    DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.SetupDreamComponentImpl create(ComplicationViewModel complicationViewModel);
}
