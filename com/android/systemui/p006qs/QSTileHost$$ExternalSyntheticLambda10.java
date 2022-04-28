package com.android.systemui.p006qs;

import com.android.systemui.Dumpable;
import com.android.systemui.plugins.p005qs.QSTile;
import java.util.function.Predicate;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda10 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda10 implements Predicate {
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda10 INSTANCE = new QSTileHost$$ExternalSyntheticLambda10();

    public final boolean test(Object obj) {
        boolean z = QSTileHost.DEBUG;
        return ((QSTile) obj) instanceof Dumpable;
    }
}
