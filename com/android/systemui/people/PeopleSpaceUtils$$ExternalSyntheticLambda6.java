package com.android.systemui.people;

import android.content.pm.ShortcutInfo;
import android.os.UserManager;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ UserManager f$0;

    public /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda6(UserManager userManager) {
        this.f$0 = userManager;
    }

    public final boolean test(Object obj) {
        return !this.f$0.isQuietModeEnabled(((ShortcutInfo) obj).getUserHandle());
    }
}
