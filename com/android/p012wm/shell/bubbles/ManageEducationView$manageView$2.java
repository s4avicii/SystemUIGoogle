package com.android.p012wm.shell.bubbles;

import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.wm.shell.bubbles.ManageEducationView$manageView$2 */
/* compiled from: ManageEducationView.kt */
public final class ManageEducationView$manageView$2 extends Lambda implements Function0<ViewGroup> {
    public final /* synthetic */ ManageEducationView this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ManageEducationView$manageView$2(ManageEducationView manageEducationView) {
        super(0);
        this.this$0 = manageEducationView;
    }

    public final Object invoke() {
        return (ViewGroup) this.this$0.findViewById(C1777R.C1779id.manage_education_view);
    }
}
