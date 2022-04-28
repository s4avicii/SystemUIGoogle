package com.android.systemui.p006qs;

import android.content.res.Configuration;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSPanel;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QuickQSPanelController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QuickQSPanelController$$ExternalSyntheticLambda0 implements QSPanel.OnConfigurationChangedListener {
    public final /* synthetic */ QuickQSPanelController f$0;

    public /* synthetic */ QuickQSPanelController$$ExternalSyntheticLambda0(QuickQSPanelController quickQSPanelController) {
        this.f$0 = quickQSPanelController;
    }

    public final void onConfigurationChange(Configuration configuration) {
        QuickQSPanelController quickQSPanelController = this.f$0;
        Objects.requireNonNull(quickQSPanelController);
        int integer = quickQSPanelController.getResources().getInteger(C1777R.integer.quick_qs_panel_max_tiles);
        QuickQSPanel quickQSPanel = (QuickQSPanel) quickQSPanelController.mView;
        Objects.requireNonNull(quickQSPanel);
        if (integer != quickQSPanel.mMaxTiles) {
            QuickQSPanel quickQSPanel2 = (QuickQSPanel) quickQSPanelController.mView;
            Objects.requireNonNull(quickQSPanel2);
            quickQSPanel2.mMaxTiles = integer;
            quickQSPanelController.setTiles();
        }
    }
}
