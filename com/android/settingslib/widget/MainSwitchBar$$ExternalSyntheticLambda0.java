package com.android.settingslib.widget;

import android.widget.Switch;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MainSwitchBar$$ExternalSyntheticLambda0 implements OnMainSwitchChangeListener {
    public final /* synthetic */ MainSwitchBar f$0;

    public /* synthetic */ MainSwitchBar$$ExternalSyntheticLambda0(MainSwitchBar mainSwitchBar) {
        this.f$0 = mainSwitchBar;
    }

    public final void onSwitchChanged(boolean z) {
        MainSwitchBar mainSwitchBar = this.f$0;
        int i = MainSwitchBar.$r8$clinit;
        Objects.requireNonNull(mainSwitchBar);
        Switch switchR = mainSwitchBar.mSwitch;
        if (switchR != null) {
            switchR.setChecked(z);
        }
        mainSwitchBar.setBackground(z);
    }
}
