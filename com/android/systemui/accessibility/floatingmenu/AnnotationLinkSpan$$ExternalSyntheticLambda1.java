package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda3;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AnnotationLinkSpan$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ AnnotationLinkSpan.LinkInfo[] f$0;
    public final /* synthetic */ SpannableStringBuilder f$1;
    public final /* synthetic */ SpannableString f$2;

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda1(AnnotationLinkSpan.LinkInfo[] linkInfoArr, SpannableStringBuilder spannableStringBuilder, SpannableString spannableString) {
        this.f$0 = linkInfoArr;
        this.f$1 = spannableStringBuilder;
        this.f$2 = spannableString;
    }

    public final void accept(Object obj) {
        AnnotationLinkSpan.LinkInfo[] linkInfoArr = this.f$0;
        SpannableStringBuilder spannableStringBuilder = this.f$1;
        SpannableString spannableString = this.f$2;
        Annotation annotation = (Annotation) obj;
        Arrays.asList(linkInfoArr).stream().filter(new AnnotationLinkSpan$$ExternalSyntheticLambda2(annotation.getValue())).findFirst().flatMap(WMShellBaseModule$$ExternalSyntheticLambda3.INSTANCE$1).ifPresent(new AnnotationLinkSpan$$ExternalSyntheticLambda0(spannableStringBuilder, spannableString, annotation));
    }
}
