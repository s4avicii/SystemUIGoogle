package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.ArrayList;
import java.util.List;

/* compiled from: InflatedSmartReplyState.kt */
public final class InflatedSmartReplyState {
    public final boolean hasPhishingAction;
    public final SmartReplyView.SmartActions smartActions;
    public final SmartReplyView.SmartReplies smartReplies;
    public final SuppressedActions suppressedActions;

    /* compiled from: InflatedSmartReplyState.kt */
    public static final class SuppressedActions {
        public final List<Integer> suppressedActionIndices;

        public SuppressedActions(ArrayList arrayList) {
            this.suppressedActionIndices = arrayList;
        }
    }

    public InflatedSmartReplyState(SmartReplyView.SmartReplies smartReplies2, SmartReplyView.SmartActions smartActions2, SuppressedActions suppressedActions2, boolean z) {
        this.smartReplies = smartReplies2;
        this.smartActions = smartActions2;
        this.suppressedActions = suppressedActions2;
        this.hasPhishingAction = z;
    }
}
