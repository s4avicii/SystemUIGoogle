package com.android.settingslib.inputmethod;

import android.view.View;
import android.widget.Switch;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda5 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda5(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void onClick(View view) {
        boolean z;
        boolean z2 = true;
        switch (this.$r8$classId) {
            case 0:
                InputMethodPreference inputMethodPreference = (InputMethodPreference) this.f$0;
                Switch switchR = (Switch) this.f$1;
                int i = InputMethodPreference.$r8$clinit;
                Objects.requireNonNull(inputMethodPreference);
                if (switchR.isEnabled()) {
                    Switch switchR2 = inputMethodPreference.mSwitch;
                    if (switchR2 == null || !inputMethodPreference.mChecked) {
                        z = false;
                    } else {
                        z = true;
                    }
                    boolean z3 = !z;
                    if (switchR2 == null || !inputMethodPreference.mChecked) {
                        z2 = false;
                    }
                    switchR.setChecked(z2);
                    inputMethodPreference.callChangeListener(Boolean.valueOf(z3));
                    return;
                }
                return;
            default:
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.f$0;
                ExpandableNotificationRow.C13092 r2 = ExpandableNotificationRow.TRANSLATE_CONTENT;
                Objects.requireNonNull(expandableNotificationRow);
                expandableNotificationRow.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
                expandableNotificationRow.mNotificationGutsManager.openGuts(expandableNotificationRow, 0, 0, (NotificationMenuRowPlugin.MenuItem) this.f$1);
                expandableNotificationRow.mIsSnoozed = true;
                return;
        }
    }
}
