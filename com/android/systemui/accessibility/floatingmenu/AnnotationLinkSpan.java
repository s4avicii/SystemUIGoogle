package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import java.util.Arrays;
import java.util.Optional;

public final class AnnotationLinkSpan extends ClickableSpan {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Optional<View.OnClickListener> mClickListener;

    public static class LinkInfo {
        public final Optional<String> mAnnotation = Optional.of("link");
        public final Optional<View.OnClickListener> mListener;

        public LinkInfo(View.OnClickListener onClickListener) {
            this.mListener = Optional.ofNullable(onClickListener);
        }
    }

    public static SpannableStringBuilder linkify(CharSequence charSequence, LinkInfo... linkInfoArr) {
        SpannableString spannableString = new SpannableString(charSequence);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
        Arrays.asList((Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class)).forEach(new AnnotationLinkSpan$$ExternalSyntheticLambda1(linkInfoArr, spannableStringBuilder, spannableString));
        return spannableStringBuilder;
    }

    public final void onClick(View view) {
        this.mClickListener.ifPresent(new QSTileHost$$ExternalSyntheticLambda1(view, 2));
    }

    public AnnotationLinkSpan(View.OnClickListener onClickListener) {
        this.mClickListener = Optional.ofNullable(onClickListener);
    }
}
