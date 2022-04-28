package com.android.systemui.communal.dagger;

import com.android.systemui.communal.CommunalHostView;
import com.android.systemui.communal.CommunalHostViewController;

public interface CommunalViewComponent {

    public interface Factory {
        CommunalViewComponent build(CommunalHostView communalHostView);
    }

    CommunalHostViewController getCommunalHostViewController();
}
