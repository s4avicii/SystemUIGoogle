package com.android.systemui.dreams.complication;

import androidx.lifecycle.Observer;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda8;
import com.android.settingslib.wifi.WifiTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda12;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ComplicationHostViewController$$ExternalSyntheticLambda0 implements Observer {
    public final /* synthetic */ ComplicationHostViewController f$0;

    public /* synthetic */ ComplicationHostViewController$$ExternalSyntheticLambda0(ComplicationHostViewController complicationHostViewController) {
        this.f$0 = complicationHostViewController;
    }

    public final void onChanged(Object obj) {
        ComplicationHostViewController complicationHostViewController = this.f$0;
        Collection collection = (Collection) obj;
        Objects.requireNonNull(complicationHostViewController);
        ((Collection) complicationHostViewController.mComplications.keySet().stream().filter(new WifiTracker$$ExternalSyntheticLambda0((Collection) collection.stream().map(ComplicationHostViewController$$ExternalSyntheticLambda1.INSTANCE).collect(Collectors.toSet()), 1)).collect(Collectors.toSet())).forEach(new WifiPickerTracker$$ExternalSyntheticLambda0(complicationHostViewController, 1));
        ((Collection) collection.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda12(complicationHostViewController, 1)).collect(Collectors.toSet())).forEach(new BubbleController$$ExternalSyntheticLambda8(complicationHostViewController, 1));
    }
}
