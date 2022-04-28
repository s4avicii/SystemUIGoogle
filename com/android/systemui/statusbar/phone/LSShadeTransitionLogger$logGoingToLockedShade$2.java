package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: LSShadeTransitionLogger.kt */
public final class LSShadeTransitionLogger$logGoingToLockedShade$2 extends Lambda implements Function1<LogMessage, String> {
    public final /* synthetic */ boolean $customAnimationHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LSShadeTransitionLogger$logGoingToLockedShade$2(boolean z) {
        super(1);
        this.$customAnimationHandler = z;
    }

    public final Object invoke(Object obj) {
        String str;
        LogMessage logMessage = (LogMessage) obj;
        if (this.$customAnimationHandler) {
            str = "with";
        } else {
            str = "without a custom handler";
        }
        return Intrinsics.stringPlus("Going to locked shade ", str);
    }
}
