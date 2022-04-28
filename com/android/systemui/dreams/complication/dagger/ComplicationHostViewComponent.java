package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.dreams.complication.ComplicationHostViewController;

public interface ComplicationHostViewComponent {

    public interface Factory {
        ComplicationHostViewComponent create();
    }

    ComplicationHostViewController getController();
}
