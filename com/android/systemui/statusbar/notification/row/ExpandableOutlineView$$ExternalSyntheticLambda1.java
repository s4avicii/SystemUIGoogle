package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.AnimatableProperty;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExpandableOutlineView$$ExternalSyntheticLambda1 implements BiConsumer {
    public static final /* synthetic */ ExpandableOutlineView$$ExternalSyntheticLambda1 INSTANCE = new ExpandableOutlineView$$ExternalSyntheticLambda1();

    public final void accept(Object obj, Object obj2) {
        ExpandableOutlineView expandableOutlineView = (ExpandableOutlineView) obj;
        float floatValue = ((Float) obj2).floatValue();
        AnimatableProperty.C12216 r2 = ExpandableOutlineView.TOP_ROUNDNESS;
        Objects.requireNonNull(expandableOutlineView);
        expandableOutlineView.mCurrentBottomRoundness = floatValue;
        expandableOutlineView.applyRoundness();
    }
}
