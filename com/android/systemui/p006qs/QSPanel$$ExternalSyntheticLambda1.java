package com.android.systemui.p006qs;

import android.content.res.Configuration;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.plugins.GlobalActions;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.QSPanel$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSPanel$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSPanel$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = QSPanel.$r8$clinit;
                ((QSPanel.OnConfigurationChangedListener) obj).onConfigurationChange((Configuration) this.f$0);
                return;
            case 1:
                ((UiEventLogger) this.f$0).log((DozeTriggers.DozingUpdateUiEvent) obj);
                return;
            default:
                GlobalActionsComponent globalActionsComponent = (GlobalActionsComponent) this.f$0;
                GlobalActions globalActions = (GlobalActions) obj;
                Objects.requireNonNull(globalActionsComponent);
                GlobalActions globalActions2 = globalActionsComponent.mPlugin;
                if (globalActions2 != null) {
                    globalActions2.destroy();
                }
                globalActionsComponent.mPlugin = globalActions;
                return;
        }
    }
}
