package com.android.systemui.p006qs;

import java.util.Objects;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSPanelControllerBase$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ QSPanelControllerBase f$0;

    public /* synthetic */ QSPanelControllerBase$$ExternalSyntheticLambda1(QSPanelControllerBase qSPanelControllerBase) {
        this.f$0 = qSPanelControllerBase;
    }

    public final Object invoke(Object obj) {
        QSPanelControllerBase qSPanelControllerBase = this.f$0;
        Boolean bool = (Boolean) obj;
        Objects.requireNonNull(qSPanelControllerBase);
        Consumer<Boolean> consumer = qSPanelControllerBase.mMediaVisibilityChangedListener;
        if (consumer != null) {
            consumer.accept(bool);
        }
        qSPanelControllerBase.switchTileLayout(false);
        return null;
    }
}
