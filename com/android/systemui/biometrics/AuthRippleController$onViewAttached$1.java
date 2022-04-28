package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.statusbar.commandline.Command;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$onViewAttached$1 extends Lambda implements Function0<Command> {
    public final /* synthetic */ AuthRippleController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AuthRippleController$onViewAttached$1(AuthRippleController authRippleController) {
        super(0);
        this.this$0 = authRippleController;
    }

    public final Object invoke() {
        return new AuthRippleController.AuthRippleCommand();
    }
}
