package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.Comparator;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda1 implements Comparator {
    public static final /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda1 INSTANCE = new PeopleSpaceUtils$$ExternalSyntheticLambda1();

    public final int compare(Object obj, Object obj2) {
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        return new Long(((PeopleSpaceTile) obj2).getLastInteractionTimestamp()).compareTo(new Long(((PeopleSpaceTile) obj).getLastInteractionTimestamp()));
    }
}
