package com.android.systemui.controls.p004ui;

import android.app.PendingIntent;
import android.service.controls.Control;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$longPress$1 */
/* compiled from: ControlActionCoordinatorImpl.kt */
public final class ControlActionCoordinatorImpl$longPress$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ ControlActionCoordinatorImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlActionCoordinatorImpl$longPress$1(ControlViewHolder controlViewHolder, ControlActionCoordinatorImpl controlActionCoordinatorImpl) {
        super(0);
        this.$cvh = controlViewHolder;
        this.this$0 = controlActionCoordinatorImpl;
    }

    public final Object invoke() {
        ControlWithState cws = this.$cvh.getCws();
        Objects.requireNonNull(cws);
        Control control = cws.control;
        if (control != null) {
            ControlViewHolder controlViewHolder = this.$cvh;
            ControlActionCoordinatorImpl controlActionCoordinatorImpl = this.this$0;
            Objects.requireNonNull(controlViewHolder);
            controlViewHolder.layout.performHapticFeedback(0);
            PendingIntent appIntent = control.getAppIntent();
            Objects.requireNonNull(controlActionCoordinatorImpl);
            controlActionCoordinatorImpl.bgExecutor.execute(new ControlActionCoordinatorImpl$showDetail$1(controlActionCoordinatorImpl, appIntent, controlViewHolder));
        }
        return Unit.INSTANCE;
    }
}
