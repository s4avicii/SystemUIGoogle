package com.android.p012wm.shell.bubbles;

import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.wm.shell.bubbles.StackEducationView$descTextView$2 */
/* compiled from: StackEducationView.kt */
public final class StackEducationView$descTextView$2 extends Lambda implements Function0<TextView> {
    public final /* synthetic */ StackEducationView this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StackEducationView$descTextView$2(StackEducationView stackEducationView) {
        super(0);
        this.this$0 = stackEducationView;
    }

    public final Object invoke() {
        return (TextView) this.this$0.findViewById(C1777R.C1779id.stack_education_description);
    }
}
