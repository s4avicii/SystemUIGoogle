package com.android.systemui.communal;

import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda4;
import com.android.systemui.util.condition.Monitor;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CommunalSourceMonitor$$ExternalSyntheticLambda0 implements Monitor.Callback {
    public final /* synthetic */ CommunalSourceMonitor f$0;

    public /* synthetic */ CommunalSourceMonitor$$ExternalSyntheticLambda0(CommunalSourceMonitor communalSourceMonitor) {
        this.f$0 = communalSourceMonitor;
    }

    public final void onConditionsChanged(boolean z) {
        CommunalSourceMonitor communalSourceMonitor = this.f$0;
        Objects.requireNonNull(communalSourceMonitor);
        if (communalSourceMonitor.mAllCommunalConditionsMet != z) {
            if (CommunalSourceMonitor.DEBUG) {
                ViewCompat$$ExternalSyntheticLambda0.m12m("communal conditions changed: ", z, "CommunalSourceMonitor");
            }
            communalSourceMonitor.mAllCommunalConditionsMet = z;
            communalSourceMonitor.mExecutor.execute(new TaskView$$ExternalSyntheticLambda4(communalSourceMonitor, 5));
        }
    }
}
