package com.android.p012wm.shell.compatui;

import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ CompatUIController f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda1(CompatUIController compatUIController) {
        this.f$0 = compatUIController;
    }

    public final void accept(Object obj) {
        CompatUIController compatUIController = this.f$0;
        CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = (CompatUIWindowManagerAbstract) obj;
        Objects.requireNonNull(compatUIController);
        Objects.requireNonNull(compatUIWindowManagerAbstract);
        compatUIWindowManagerAbstract.updateVisibility(compatUIController.showOnDisplay(compatUIWindowManagerAbstract.mDisplayId));
    }
}
