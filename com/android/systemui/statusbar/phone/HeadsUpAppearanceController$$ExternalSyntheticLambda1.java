package com.android.systemui.statusbar.phone;

import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ HeadsUpAppearanceController f$0;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda1(HeadsUpAppearanceController headsUpAppearanceController) {
        this.f$0 = headsUpAppearanceController;
    }

    public final void accept(Object obj, Object obj2) {
        boolean z;
        HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
        float floatValue = ((Float) obj).floatValue();
        float floatValue2 = ((Float) obj2).floatValue();
        Objects.requireNonNull(headsUpAppearanceController);
        if (floatValue != headsUpAppearanceController.mExpandedHeight) {
            z = true;
        } else {
            z = false;
        }
        boolean isExpanded = headsUpAppearanceController.isExpanded();
        headsUpAppearanceController.mExpandedHeight = floatValue;
        headsUpAppearanceController.mAppearFraction = floatValue2;
        if (z) {
            headsUpAppearanceController.mHeadsUpManager.getAllEntries().forEach(new DozeTriggers$$ExternalSyntheticLambda2(headsUpAppearanceController, 4));
        }
        if (headsUpAppearanceController.isExpanded() != isExpanded) {
            headsUpAppearanceController.updateTopEntry();
        }
    }
}
