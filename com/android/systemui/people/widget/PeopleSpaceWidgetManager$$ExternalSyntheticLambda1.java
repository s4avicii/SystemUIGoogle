package com.android.systemui.people.widget;

import android.app.people.PeopleSpaceTile;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ PeopleSpaceWidgetManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ PeopleSpaceTile f$2;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(PeopleSpaceWidgetManager peopleSpaceWidgetManager, int i, PeopleSpaceTile peopleSpaceTile) {
        this.f$0 = peopleSpaceWidgetManager;
        this.f$1 = i;
        this.f$2 = peopleSpaceTile;
    }

    public final void run() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
        int i = this.f$1;
        PeopleSpaceTile peopleSpaceTile = this.f$2;
        Objects.requireNonNull(peopleSpaceWidgetManager);
        peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(i, peopleSpaceTile);
    }
}
