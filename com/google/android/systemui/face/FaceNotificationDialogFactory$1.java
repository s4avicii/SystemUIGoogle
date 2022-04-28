package com.google.android.systemui.face;

import android.content.Context;
import android.content.Intent;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.theme.ThemeOverlayApplier;

public final class FaceNotificationDialogFactory$1 extends FaceManager.RemovalCallback {
    public boolean mDidShowFailureDialog;
    public final /* synthetic */ Context val$context;

    public FaceNotificationDialogFactory$1(Context context) {
        this.val$context = context;
    }

    public final void onRemovalError(Face face, int i, CharSequence charSequence) {
        Log.e("FaceNotificationDialogF", "Not launching enrollment. Failed to remove existing face(s).");
        if (!this.mDidShowFailureDialog) {
            this.mDidShowFailureDialog = true;
            Context context = this.val$context;
            SystemUIDialog systemUIDialog = new SystemUIDialog(context);
            systemUIDialog.setMessage(context.getText(C1777R.string.face_reenroll_failure_dialog_content));
            systemUIDialog.setPositiveButton(C1777R.string.f97ok, FaceNotificationDialogFactory$$ExternalSyntheticLambda0.INSTANCE);
            systemUIDialog.show();
        }
    }

    public final void onRemovalSucceeded(Face face, int i) {
        if (!this.mDidShowFailureDialog && i == 0) {
            Intent intent = new Intent("android.settings.BIOMETRIC_ENROLL");
            intent.setPackage(ThemeOverlayApplier.SETTINGS_PACKAGE);
            intent.setFlags(268435456);
            this.val$context.startActivity(intent);
        }
    }
}
