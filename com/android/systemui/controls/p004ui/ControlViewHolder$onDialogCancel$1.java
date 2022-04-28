package com.android.systemui.controls.p004ui;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$onDialogCancel$1 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$onDialogCancel$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ ControlViewHolder this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlViewHolder$onDialogCancel$1(ControlViewHolder controlViewHolder) {
        super(0);
        this.this$0 = controlViewHolder;
    }

    public final Object invoke() {
        this.this$0.lastChallengeDialog = null;
        return Unit.INSTANCE;
    }
}
