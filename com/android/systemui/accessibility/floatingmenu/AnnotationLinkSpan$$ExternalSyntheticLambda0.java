package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AnnotationLinkSpan$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ SpannableStringBuilder f$0;
    public final /* synthetic */ SpannableString f$1;
    public final /* synthetic */ Annotation f$2;

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda0(SpannableStringBuilder spannableStringBuilder, SpannableString spannableString, Annotation annotation) {
        this.f$0 = spannableStringBuilder;
        this.f$1 = spannableString;
        this.f$2 = annotation;
    }

    public final void accept(Object obj) {
        SpannableStringBuilder spannableStringBuilder = this.f$0;
        SpannableString spannableString = this.f$1;
        Annotation annotation = this.f$2;
        AnnotationLinkSpan annotationLinkSpan = new AnnotationLinkSpan((View.OnClickListener) obj);
        spannableStringBuilder.setSpan(annotationLinkSpan, spannableString.getSpanStart(annotation), spannableString.getSpanEnd(annotation), spannableString.getSpanFlags(annotationLinkSpan));
    }
}
