package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.util.SizeF;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ PeopleSpaceTile f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ PeopleTileKey f$3;

    public /* synthetic */ PeopleTileViewHelper$$ExternalSyntheticLambda1(Context context, PeopleSpaceTile peopleSpaceTile, int i, PeopleTileKey peopleTileKey) {
        this.f$0 = context;
        this.f$1 = peopleSpaceTile;
        this.f$2 = i;
        this.f$3 = peopleTileKey;
    }

    public final Object apply(Object obj) {
        SizeF sizeF = (SizeF) obj;
        return new PeopleTileViewHelper(this.f$0, this.f$1, this.f$2, (int) sizeF.getWidth(), (int) sizeF.getHeight(), this.f$3).getViews();
    }
}
