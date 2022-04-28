package com.android.systemui.p006qs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ List f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda9(ArrayList arrayList) {
        this.f$0 = arrayList;
    }

    public final boolean test(Object obj) {
        return !this.f$0.contains(((Map.Entry) obj).getKey());
    }
}
