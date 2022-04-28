package com.android.p012wm.shell.compatui;

import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUILayout$$ExternalSyntheticLambda6 implements View.OnLongClickListener {
    public final /* synthetic */ CompatUILayout f$0;

    public /* synthetic */ CompatUILayout$$ExternalSyntheticLambda6(CompatUILayout compatUILayout) {
        this.f$0 = compatUILayout;
    }

    public final boolean onLongClick(View view) {
        CompatUILayout compatUILayout = this.f$0;
        int i = CompatUILayout.$r8$clinit;
        Objects.requireNonNull(compatUILayout);
        CompatUIWindowManager compatUIWindowManager = compatUILayout.mWindowManager;
        Objects.requireNonNull(compatUIWindowManager);
        CompatUILayout compatUILayout2 = compatUIWindowManager.mLayout;
        if (compatUILayout2 != null) {
            compatUILayout2.setViewVisibility(C1777R.C1779id.camera_compat_hint, true);
        }
        return true;
    }
}
