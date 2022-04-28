package com.android.p012wm.shell.compatui;

import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUILayout$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ CompatUILayout f$0;

    public /* synthetic */ CompatUILayout$$ExternalSyntheticLambda1(CompatUILayout compatUILayout) {
        this.f$0 = compatUILayout;
    }

    public final void onClick(View view) {
        CompatUILayout compatUILayout = this.f$0;
        int i = CompatUILayout.$r8$clinit;
        Objects.requireNonNull(compatUILayout);
        compatUILayout.setViewVisibility(C1777R.C1779id.camera_compat_hint, false);
    }
}
