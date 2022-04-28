package com.android.systemui.shortcut;

import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.p012wm.shell.legacysplitscreen.DividerView;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutKeyDispatcher$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ ShortcutKeyDispatcher f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ ShortcutKeyDispatcher$$ExternalSyntheticLambda0(ShortcutKeyDispatcher shortcutKeyDispatcher, long j) {
        this.f$0 = shortcutKeyDispatcher;
        this.f$1 = j;
    }

    public final void accept(Object obj) {
        DividerSnapAlgorithm.SnapTarget snapTarget;
        ShortcutKeyDispatcher shortcutKeyDispatcher = this.f$0;
        long j = this.f$1;
        LegacySplitScreen legacySplitScreen = (LegacySplitScreen) obj;
        Objects.requireNonNull(shortcutKeyDispatcher);
        if (legacySplitScreen.isDividerVisible()) {
            DividerView dividerView = legacySplitScreen.getDividerView();
            DividerSnapAlgorithm snapAlgorithm = dividerView.getSnapAlgorithm();
            DividerSnapAlgorithm.SnapTarget calculateNonDismissingSnapTarget = snapAlgorithm.calculateNonDismissingSnapTarget(dividerView.getCurrentPosition());
            if (j == 281474976710727L) {
                snapTarget = snapAlgorithm.getPreviousTarget(calculateNonDismissingSnapTarget);
            } else {
                snapTarget = snapAlgorithm.getNextTarget(calculateNonDismissingSnapTarget);
            }
            dividerView.startDragging(true, false);
            dividerView.stopDragging(snapTarget.position, 0.0f);
            return;
        }
        legacySplitScreen.splitPrimaryTask();
    }
}
