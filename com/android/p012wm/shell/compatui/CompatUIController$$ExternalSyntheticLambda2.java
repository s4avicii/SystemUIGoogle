package com.android.p012wm.shell.compatui;

import java.util.Objects;
import java.util.function.Predicate;

/* renamed from: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda2(int i) {
        this.f$0 = i;
    }

    public final boolean test(Object obj) {
        int i = this.f$0;
        CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = (CompatUIWindowManagerAbstract) obj;
        Objects.requireNonNull(compatUIWindowManagerAbstract);
        if (compatUIWindowManagerAbstract.mDisplayId == i) {
            return true;
        }
        return false;
    }
}
