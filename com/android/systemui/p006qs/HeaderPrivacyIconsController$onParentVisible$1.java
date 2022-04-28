package com.android.systemui.p006qs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyDialogController$showDialog$1;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.HeaderPrivacyIconsController$onParentVisible$1 */
/* compiled from: HeaderPrivacyIconsController.kt */
public final class HeaderPrivacyIconsController$onParentVisible$1 implements View.OnClickListener {
    public final /* synthetic */ HeaderPrivacyIconsController this$0;

    public HeaderPrivacyIconsController$onParentVisible$1(HeaderPrivacyIconsController headerPrivacyIconsController) {
        this.this$0 = headerPrivacyIconsController;
    }

    public final void onClick(View view) {
        this.this$0.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        if (headerPrivacyIconsController.safetyCenterEnabled) {
            headerPrivacyIconsController.backgroundExecutor.execute(new HeaderPrivacyIconsController$showSafetyHub$1(headerPrivacyIconsController));
            return;
        }
        PrivacyDialogController privacyDialogController = headerPrivacyIconsController.privacyDialogController;
        Context context = headerPrivacyIconsController.privacyChip.getContext();
        Objects.requireNonNull(privacyDialogController);
        Dialog dialog = privacyDialogController.dialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        privacyDialogController.backgroundExecutor.execute(new PrivacyDialogController$showDialog$1(privacyDialogController, context));
    }
}
