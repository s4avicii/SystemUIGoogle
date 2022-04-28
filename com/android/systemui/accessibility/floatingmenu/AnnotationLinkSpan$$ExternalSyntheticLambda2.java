package com.android.systemui.accessibility.floatingmenu;

import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AnnotationLinkSpan$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ String f$0;

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda2(String str) {
        this.f$0 = str;
    }

    public final boolean test(Object obj) {
        String str = this.f$0;
        AnnotationLinkSpan.LinkInfo linkInfo = (AnnotationLinkSpan.LinkInfo) obj;
        if (!linkInfo.mAnnotation.isPresent() || !linkInfo.mAnnotation.get().equals(str)) {
            return false;
        }
        return true;
    }
}
