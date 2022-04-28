package com.android.p012wm.shell.legacysplitscreen;

import com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda3;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreen */
public interface LegacySplitScreen {
    DividerView getDividerView();

    boolean isDividerVisible();

    boolean isHomeStackResizable();

    boolean isMinimized();

    void onAppTransitionFinished();

    void onKeyguardVisibilityChanged(boolean z);

    void onUndockingTask();

    void registerBoundsChangeListener(OverviewProxyService$$ExternalSyntheticLambda3 overviewProxyService$$ExternalSyntheticLambda3);

    void registerInSplitScreenListener(Consumer<Boolean> consumer);

    void setMinimized(boolean z);

    boolean splitPrimaryTask();
}
