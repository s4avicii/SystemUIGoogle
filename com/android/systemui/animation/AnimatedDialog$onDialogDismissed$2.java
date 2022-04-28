package com.android.systemui.animation;

import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DialogLaunchAnimator.kt */
public final class AnimatedDialog$onDialogDismissed$2 extends Lambda implements Function1<Boolean, Unit> {
    public final /* synthetic */ AnimatedDialog this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AnimatedDialog$onDialogDismissed$2(AnimatedDialog animatedDialog) {
        super(1);
        this.this$0 = animatedDialog;
    }

    public final Object invoke(Object obj) {
        if (((Boolean) obj).booleanValue()) {
            AnimatedDialog animatedDialog = this.this$0;
            Objects.requireNonNull(animatedDialog);
            animatedDialog.dialog.hide();
        }
        AnimatedDialog animatedDialog2 = this.this$0;
        Objects.requireNonNull(animatedDialog2);
        animatedDialog2.dialog.setDismissOverride((Runnable) null);
        AnimatedDialog animatedDialog3 = this.this$0;
        Objects.requireNonNull(animatedDialog3);
        animatedDialog3.dialog.dismiss();
        return Unit.INSTANCE;
    }
}
