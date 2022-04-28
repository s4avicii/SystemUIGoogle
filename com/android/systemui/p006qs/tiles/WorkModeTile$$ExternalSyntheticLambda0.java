package com.android.systemui.p006qs.tiles;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* renamed from: com.android.systemui.qs.tiles.WorkModeTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WorkModeTile$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ WorkModeTile f$0;

    public /* synthetic */ WorkModeTile$$ExternalSyntheticLambda0(WorkModeTile workModeTile) {
        this.f$0 = workModeTile;
    }

    public final Object call() {
        WorkModeTile workModeTile = this.f$0;
        Objects.requireNonNull(workModeTile);
        return workModeTile.mContext.getString(C1777R.string.quick_settings_work_mode_label);
    }
}
