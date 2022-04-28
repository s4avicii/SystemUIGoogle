package com.android.systemui.p006qs;

import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.tuner.TunerService;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ QSTileHost f$0;
    public final /* synthetic */ TunerService f$1;
    public final /* synthetic */ Provider f$2;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda0(QSTileHost qSTileHost, TunerService tunerService, Provider provider) {
        this.f$0 = qSTileHost;
        this.f$1 = tunerService;
        this.f$2 = provider;
    }

    public final void run() {
        QSTileHost qSTileHost = this.f$0;
        TunerService tunerService = this.f$1;
        Provider provider = this.f$2;
        Objects.requireNonNull(qSTileHost);
        tunerService.addTunable(qSTileHost, "sysui_qs_tiles");
        qSTileHost.mAutoTiles = (AutoTileManager) provider.get();
        qSTileHost.mTileServiceRequestController.init();
    }
}
