package com.android.systemui.p006qs;

import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda3 implements Consumer {
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda3 INSTANCE = new QSTileHost$$ExternalSyntheticLambda3(0);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda3 INSTANCE$1 = new QSTileHost$$ExternalSyntheticLambda3(1);
    public static final /* synthetic */ QSTileHost$$ExternalSyntheticLambda3 INSTANCE$2 = new QSTileHost$$ExternalSyntheticLambda3(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StatusBar statusBar = (StatusBar) obj;
                Objects.requireNonNull(statusBar);
                statusBar.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda2(statusBar, 6));
                return;
            case 1:
                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
                return;
            default:
                Objects.requireNonNull((QRCodeScannerController.Callback) obj);
                return;
        }
    }
}
