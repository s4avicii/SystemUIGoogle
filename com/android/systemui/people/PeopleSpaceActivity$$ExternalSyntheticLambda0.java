package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import android.content.Intent;
import android.view.View;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceActivity$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ PeopleSpaceActivity f$0;
    public final /* synthetic */ PeopleTileKey f$2;

    public /* synthetic */ PeopleSpaceActivity$$ExternalSyntheticLambda0(PeopleSpaceActivity peopleSpaceActivity, PeopleSpaceTile peopleSpaceTile, PeopleTileKey peopleTileKey) {
        this.f$0 = peopleSpaceActivity;
        this.f$2 = peopleTileKey;
    }

    public final void onClick(View view) {
        PeopleSpaceActivity peopleSpaceActivity = this.f$0;
        PeopleTileKey peopleTileKey = this.f$2;
        int i = PeopleSpaceActivity.$r8$clinit;
        Objects.requireNonNull(peopleSpaceActivity);
        peopleSpaceActivity.mPeopleSpaceWidgetManager.addNewWidget(peopleSpaceActivity.mAppWidgetId, peopleTileKey);
        Intent intent = new Intent();
        intent.putExtra("appWidgetId", peopleSpaceActivity.mAppWidgetId);
        peopleSpaceActivity.setResult(-1, intent);
        peopleSpaceActivity.finish();
    }
}
