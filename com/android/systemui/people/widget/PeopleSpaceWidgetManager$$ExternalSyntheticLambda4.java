package com.android.systemui.people.widget;

import android.app.people.PeopleSpaceTile;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ PeopleSpaceWidgetManager f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(PeopleSpaceWidgetManager peopleSpaceWidgetManager, Map map) {
        this.f$0 = peopleSpaceWidgetManager;
        this.f$1 = map;
    }

    public final Object apply(Object obj) {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
        Map map = this.f$1;
        Objects.requireNonNull(peopleSpaceWidgetManager);
        int intValue = ((Integer) obj).intValue();
        PeopleSpaceTile tileForExistingWidget = peopleSpaceWidgetManager.getTileForExistingWidget(intValue);
        if (tileForExistingWidget == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(peopleSpaceWidgetManager.augmentTileFromNotifications(tileForExistingWidget, new PeopleTileKey(tileForExistingWidget), peopleSpaceWidgetManager.mSharedPrefs.getString(String.valueOf(intValue), (String) null), map, Optional.of(Integer.valueOf(intValue))));
    }
}
