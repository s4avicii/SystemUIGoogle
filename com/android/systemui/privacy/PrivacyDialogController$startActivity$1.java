package com.android.systemui.privacy;

import android.app.ActivityManager;
import android.app.Dialog;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController$startActivity$1 implements ActivityStarter.Callback {
    public final /* synthetic */ PrivacyDialogController this$0;

    public PrivacyDialogController$startActivity$1(PrivacyDialogController privacyDialogController) {
        this.this$0 = privacyDialogController;
    }

    public final void onActivityStarted(int i) {
        if (ActivityManager.isStartResultSuccessful(i)) {
            PrivacyDialogController privacyDialogController = this.this$0;
            Objects.requireNonNull(privacyDialogController);
            Dialog dialog = privacyDialogController.dialog;
            if (dialog != null) {
                dialog.dismiss();
                return;
            }
            return;
        }
        Dialog dialog2 = this.this$0.dialog;
        if (dialog2 != null) {
            dialog2.show();
        }
    }
}
