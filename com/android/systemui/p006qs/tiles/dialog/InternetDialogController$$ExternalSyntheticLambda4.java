package com.android.systemui.p006qs.tiles.dialog;

import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda4(HashSet hashSet) {
        this.f$0 = hashSet;
    }

    public final boolean test(Object obj) {
        return !this.f$0.add(((InternetDialogController.AnonymousClass1DisplayInfo) obj).originalName);
    }
}
