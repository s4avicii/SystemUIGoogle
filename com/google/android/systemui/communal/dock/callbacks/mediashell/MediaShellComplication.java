package com.google.android.systemui.communal.dock.callbacks.mediashell;

import android.view.View;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.complication.ComplicationViewModel;

public final class MediaShellComplication implements Complication {
    public final ComplicationLayoutParams mLayoutParams;
    public final View mView;

    public final Complication.ViewHolder createView(ComplicationViewModel complicationViewModel) {
        return new Complication.ViewHolder() {
            public final ComplicationLayoutParams getLayoutParams() {
                return MediaShellComplication.this.mLayoutParams;
            }

            public final View getView() {
                return MediaShellComplication.this.mView;
            }
        };
    }

    public MediaShellComplication(View view, ComplicationLayoutParams complicationLayoutParams) {
        this.mView = view;
        this.mLayoutParams = complicationLayoutParams;
    }
}
