package com.android.systemui.keyguard;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WorkLockActivity$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ WorkLockActivity f$0;

    public /* synthetic */ WorkLockActivity$$ExternalSyntheticLambda0(WorkLockActivity workLockActivity) {
        this.f$0 = workLockActivity;
    }

    public final Object call() {
        WorkLockActivity workLockActivity = this.f$0;
        int i = WorkLockActivity.$r8$clinit;
        Objects.requireNonNull(workLockActivity);
        return workLockActivity.getString(C1777R.string.accessibility_desc_work_lock);
    }
}
