package com.android.systemui.p006qs;

import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda5 implements Consumer {
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda5 INSTANCE = new QSTileHost$$ExternalSyntheticLambda5(0);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda5 INSTANCE$1 = new QSTileHost$$ExternalSyntheticLambda5(1);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda5 INSTANCE$2 = new QSTileHost$$ExternalSyntheticLambda5(2);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda5 INSTANCE$3 = new QSTileHost$$ExternalSyntheticLambda5(3);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StatusBar statusBar = (StatusBar) obj;
                Objects.requireNonNull(statusBar);
                DelayableExecutor delayableExecutor = statusBar.mMainExecutor;
                ShadeController shadeController = statusBar.mShadeController;
                Objects.requireNonNull(shadeController);
                delayableExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda18(shadeController, 6));
                return;
            case 1:
                StatusBar statusBar2 = (StatusBar) obj;
                Objects.requireNonNull(statusBar2);
                statusBar2.mCommandQueueCallbacks.animateExpandSettingsPanel((String) null);
                return;
            case 2:
                ((StatusBar) obj).collapseShade();
                return;
            default:
                ((PhonePipMenuController.Listener) obj).onPipExpand();
                return;
        }
    }
}
