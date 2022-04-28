package com.android.systemui.p006qs.tiles.dialog;

import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.wifitrackerlib.MergedCarrierEntry;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda7 implements View.OnClickListener {
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda7(InternetDialog internetDialog) {
        this.f$0 = internetDialog;
    }

    public final void onClick(View view) {
        InternetDialog internetDialog = this.f$0;
        Objects.requireNonNull(internetDialog);
        if (internetDialog.mInternetDialogController.isMobileDataEnabled()) {
            InternetDialogController internetDialogController = internetDialog.mInternetDialogController;
            Objects.requireNonNull(internetDialogController);
            boolean z = true;
            if (!(!internetDialogController.mKeyguardStateController.isUnlocked()) && !internetDialog.mInternetDialogController.activeNetworkIsCellular()) {
                InternetDialogController internetDialogController2 = internetDialog.mInternetDialogController;
                Objects.requireNonNull(internetDialogController2);
                MergedCarrierEntry mergedCarrierEntry = internetDialogController2.mAccessPointController.getMergedCarrierEntry();
                if (mergedCarrierEntry != null) {
                    synchronized (mergedCarrierEntry) {
                        if (mergedCarrierEntry.getConnectedState() != 0 || mergedCarrierEntry.mIsCellDefaultRoute) {
                            z = false;
                        }
                    }
                    if (z) {
                        mergedCarrierEntry.connect((InternetDialogController.WifiEntryConnectCallback) null, false);
                        internetDialogController2.makeOverlayToast(C1777R.string.wifi_wont_autoconnect_for_now);
                    }
                }
            }
        }
    }
}
