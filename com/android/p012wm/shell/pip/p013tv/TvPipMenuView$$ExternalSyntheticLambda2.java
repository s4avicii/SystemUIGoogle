package com.android.p012wm.shell.pip.p013tv;

import android.view.View;

/* renamed from: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TvPipMenuView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ float f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ TvPipMenuView$$ExternalSyntheticLambda2(float f, View view) {
        this.f$0 = f;
        this.f$1 = view;
    }

    public final void run() {
        float f = this.f$0;
        View view = this.f$1;
        int i = TvPipMenuView.$r8$clinit;
        if (f == 0.0f) {
            view.setVisibility(8);
        }
    }
}
