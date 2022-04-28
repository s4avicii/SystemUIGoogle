package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.service.notification.ConversationChannelWrapper;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda10 implements Function {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda10 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda10(0);
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda10 INSTANCE$1 = new WifiPickerTracker$$ExternalSyntheticLambda10(1);
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda10 INSTANCE$2 = new WifiPickerTracker$$ExternalSyntheticLambda10(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda10(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj, false);
            case 1:
                StatusBar statusBar = (StatusBar) obj;
                Objects.requireNonNull(statusBar);
                return Boolean.valueOf(statusBar.mDeviceInteractive);
            default:
                HashMap hashMap = PeopleSpaceWidgetManager.mListeners;
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
        }
    }
}
