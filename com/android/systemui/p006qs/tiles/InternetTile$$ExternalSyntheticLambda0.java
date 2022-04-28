package com.android.systemui.p006qs.tiles;

import android.view.View;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda5;
import com.android.systemui.dreams.complication.Complication;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.InternetTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ InternetTile$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                InternetTile internetTile = (InternetTile) this.f$0;
                Objects.requireNonNull(internetTile);
                internetTile.mInternetDialogFactory.create(internetTile.mAccessPointController.canConfigMobileData(), internetTile.mAccessPointController.canConfigWifi(), (View) this.f$1);
                return;
            case 1:
                DreamOverlayStateController dreamOverlayStateController = (DreamOverlayStateController) this.f$0;
                int i = DreamOverlayStateController.$r8$clinit;
                Objects.requireNonNull(dreamOverlayStateController);
                if (dreamOverlayStateController.mComplications.add((Complication) this.f$1)) {
                    dreamOverlayStateController.mCallbacks.stream().forEach(DreamOverlayStateController$$ExternalSyntheticLambda5.INSTANCE);
                    return;
                }
                return;
            default:
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) this.f$0;
                Objects.requireNonNull(startingSurfaceImpl);
                StartingSurfaceDrawer startingSurfaceDrawer = StartingWindowController.this.mStartingSurfaceDrawer;
                Objects.requireNonNull(startingSurfaceDrawer);
                startingSurfaceDrawer.mSysuiProxy = (StartingSurface.SysuiProxy) this.f$1;
                return;
        }
    }
}
