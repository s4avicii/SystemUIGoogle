package com.android.systemui.p006qs;

import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda4 implements Consumer {
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda4 INSTANCE = new QSTileHost$$ExternalSyntheticLambda4(0);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda4 INSTANCE$1 = new QSTileHost$$ExternalSyntheticLambda4(1);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda4 INSTANCE$2 = new QSTileHost$$ExternalSyntheticLambda4(2);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda4 INSTANCE$3 = new QSTileHost$$ExternalSyntheticLambda4(3);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StatusBar statusBar = (StatusBar) obj;
                Objects.requireNonNull(statusBar);
                statusBar.mMessageRouter.sendMessage(1002);
                return;
            case 1:
                StatusBar statusBar2 = (StatusBar) obj;
                Objects.requireNonNull(statusBar2);
                statusBar2.mCommandQueueCallbacks.animateCollapsePanels(0, false);
                return;
            case 2:
                ((QRCodeScannerController.Callback) obj).onQRCodeScannerActivityChanged();
                return;
            default:
                ((PhonePipMenuController.Listener) obj).onPipShowMenu();
                return;
        }
    }
}
