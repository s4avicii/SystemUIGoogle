package com.android.systemui.people.widget;

import android.app.people.PeopleSpaceTile;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda2 implements BiConsumer {
    public final /* synthetic */ PeopleSpaceWidgetManager f$0;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda2(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.f$0 = peopleSpaceWidgetManager;
    }

    public final void accept(Object obj, Object obj2) {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
        Optional optional = (Optional) obj2;
        Objects.requireNonNull(peopleSpaceWidgetManager);
        int intValue = ((Integer) obj).intValue();
        if (optional.isPresent()) {
            peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(intValue, (PeopleSpaceTile) optional.get());
        }
    }
}
