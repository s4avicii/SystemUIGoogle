package com.android.systemui.statusbar;

import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.function.ToIntFunction;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarStateControllerImpl$$ExternalSyntheticLambda1 implements ToIntFunction {
    public static final /* synthetic */ StatusBarStateControllerImpl$$ExternalSyntheticLambda1 INSTANCE = new StatusBarStateControllerImpl$$ExternalSyntheticLambda1();

    public final int applyAsInt(Object obj) {
        boolean z = StatusBarStateControllerImpl.DEBUG_IMMERSIVE_APPS;
        return ((SysuiStatusBarStateController.RankedListener) obj).mRank;
    }
}
