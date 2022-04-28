package com.google.android.systemui.face;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.phone.SystemUIDialog;

public final class FaceNotificationBroadcastReceiver extends BroadcastReceiver {
    public final Context mContext;

    public FaceNotificationBroadcastReceiver(Context context) {
        this.mContext = context;
    }

    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            Log.e("FaceNotificationBCR", "Received broadcast with null action.");
            this.mContext.unregisterReceiver(this);
            return;
        }
        if (action.equals("face_action_show_reenroll_dialog")) {
            Context context2 = this.mContext;
            SystemUIDialog systemUIDialog = new SystemUIDialog(context2);
            systemUIDialog.setTitle(context2.getString(C1777R.string.face_reenroll_dialog_title));
            systemUIDialog.setMessage(context2.getString(C1777R.string.face_reenroll_dialog_content));
            systemUIDialog.setPositiveButton(C1777R.string.face_reenroll_dialog_confirm, new InputMethodPreference$$ExternalSyntheticLambda2(context2, 1));
            systemUIDialog.setNegativeButton(C1777R.string.face_reenroll_dialog_cancel, FaceNotificationDialogFactory$$ExternalSyntheticLambda0.INSTANCE);
            systemUIDialog.show();
        }
        this.mContext.unregisterReceiver(this);
    }
}
