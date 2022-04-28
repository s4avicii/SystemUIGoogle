package com.android.systemui.p006qs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ Collection f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda8(ArrayList arrayList) {
        this.f$0 = arrayList;
    }

    public final boolean test(Object obj) {
        return ((List) obj).removeAll(this.f$0);
    }
}
