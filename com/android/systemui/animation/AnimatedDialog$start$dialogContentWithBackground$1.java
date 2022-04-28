package com.android.systemui.animation;

import android.view.View;
import java.util.Objects;

/* compiled from: DialogLaunchAnimator.kt */
public final class AnimatedDialog$start$dialogContentWithBackground$1 implements View.OnClickListener {
    public final /* synthetic */ AnimatedDialog this$0;

    public AnimatedDialog$start$dialogContentWithBackground$1(AnimatedDialog animatedDialog) {
        this.this$0 = animatedDialog;
    }

    public final void onClick(View view) {
        AnimatedDialog animatedDialog = this.this$0;
        Objects.requireNonNull(animatedDialog);
        animatedDialog.dialog.dismiss();
    }
}
