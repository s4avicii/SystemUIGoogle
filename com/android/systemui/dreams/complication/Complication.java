package com.android.systemui.dreams.complication;

import android.view.View;

public interface Complication {

    public interface Host {
    }

    public interface ViewHolder {
        ComplicationLayoutParams getLayoutParams();

        View getView();
    }

    ViewHolder createView(ComplicationViewModel complicationViewModel);

    int getRequiredTypeAvailability() {
        return 0;
    }
}
