package com.android.systemui.p006qs.tiles.dialog;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.widget.CompoundButton;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Prefs;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda9 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda9(InternetDialog internetDialog) {
        this.f$0 = internetDialog;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        boolean z2;
        InternetDialog internetDialog = this.f$0;
        Objects.requireNonNull(internetDialog);
        boolean z3 = true;
        if (!z) {
            boolean z4 = Prefs.getBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData");
            if (!internetDialog.mInternetDialogController.isMobileDataEnabled() || z4) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                CharSequence mobileNetworkTitle = internetDialog.getMobileNetworkTitle();
                boolean isVoiceStateInService = internetDialog.mInternetDialogController.isVoiceStateInService();
                if (TextUtils.isEmpty(mobileNetworkTitle) || !isVoiceStateInService) {
                    mobileNetworkTitle = internetDialog.mContext.getString(C1777R.string.mobile_data_disable_message_default_carrier);
                }
                AlertDialog create = new AlertDialog.Builder(internetDialog.mContext).setTitle(C1777R.string.mobile_data_disable_title).setMessage(internetDialog.mContext.getString(C1777R.string.mobile_data_disable_message, new Object[]{mobileNetworkTitle})).setNegativeButton(17039360, new InternetDialog$$ExternalSyntheticLambda1(internetDialog)).setPositiveButton(17039652, new InternetDialog$$ExternalSyntheticLambda2(internetDialog)).create();
                internetDialog.mAlertDialog = create;
                create.setOnCancelListener(new InternetDialog$$ExternalSyntheticLambda0(internetDialog));
                internetDialog.mAlertDialog.getWindow().setType(2009);
                SystemUIDialog.setShowForAllUsers(internetDialog.mAlertDialog);
                SystemUIDialog.registerDismissListener(internetDialog.mAlertDialog);
                SystemUIDialog.setWindowOnTop(internetDialog.mAlertDialog, internetDialog.mKeyguard.isShowing());
                internetDialog.mAlertDialog.show();
                return;
            }
        }
        boolean z5 = Prefs.getBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData");
        if (!internetDialog.mInternetDialogController.isMobileDataEnabled() || z5) {
            z3 = false;
        }
        if (!z3) {
            internetDialog.mInternetDialogController.setMobileDataEnabled(internetDialog.mContext, internetDialog.mDefaultDataSubId, z);
        }
    }
}
