package com.android.settingslib.inputmethod;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.google.android.systemui.face.FaceNotificationDialogFactory$$ExternalSyntheticLambda0;
import com.google.android.systemui.face.FaceNotificationDialogFactory$1;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda2 implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                InputMethodPreference inputMethodPreference = (InputMethodPreference) this.f$0;
                int i2 = InputMethodPreference.$r8$clinit;
                Objects.requireNonNull(inputMethodPreference);
                inputMethodPreference.setCheckedInternal(false);
                return;
            default:
                Context context = (Context) this.f$0;
                FaceManager faceManager = (FaceManager) context.getSystemService(FaceManager.class);
                if (faceManager == null) {
                    Log.e("FaceNotificationDialogF", "Not launching enrollment. Face manager was null!");
                    SystemUIDialog systemUIDialog = new SystemUIDialog(context);
                    systemUIDialog.setMessage(context.getText(C1777R.string.face_reenroll_failure_dialog_content));
                    systemUIDialog.setPositiveButton(C1777R.string.f97ok, FaceNotificationDialogFactory$$ExternalSyntheticLambda0.INSTANCE);
                    systemUIDialog.show();
                    return;
                }
                faceManager.remove(new Face("", 0, 0), context.getUserId(), new FaceNotificationDialogFactory$1(context));
                return;
        }
    }
}
