package com.android.wifitrackerlib;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0;
import java.util.StringJoiner;

public final class MergedCarrierEntry extends WifiEntry {
    public final Context mContext;
    public boolean mIsCellDefaultRoute;
    public final String mKey;
    public final int mSubscriptionId;

    public MergedCarrierEntry(Handler handler, WifiManager wifiManager, Context context, int i) throws IllegalArgumentException {
        super(handler, wifiManager, false);
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mKey = VendorAtomValue$$ExternalSyntheticOutline0.m0m("MergedCarrierEntry:", i);
    }

    public final synchronized void connect(InternetDialogController.WifiEntryConnectCallback wifiEntryConnectCallback) {
        connect(wifiEntryConnectCallback, true);
    }

    public final String getSummary(boolean z) {
        StringJoiner stringJoiner = new StringJoiner(this.mContext.getString(C1777R.string.wifitrackerlib_summary_separator));
        if (!z) {
            String verboseLoggingDescription = Utils.getVerboseLoggingDescription(this);
            if (!TextUtils.isEmpty(verboseLoggingDescription)) {
                stringJoiner.add(verboseLoggingDescription);
            }
        }
        return stringJoiner.toString();
    }

    public final synchronized void connect(InternetDialogController.WifiEntryConnectCallback wifiEntryConnectCallback, boolean z) {
        this.mConnectCallback = wifiEntryConnectCallback;
        this.mWifiManager.startRestrictingAutoJoinToSubscriptionId(this.mSubscriptionId);
        if (z) {
            Toast.makeText(this.mContext, C1777R.string.wifitrackerlib_wifi_wont_autoconnect_for_now, 0).show();
        }
        if (this.mConnectCallback != null) {
            this.mCallbackHandler.post(new PowerUI$$ExternalSyntheticLambda0(this, 4));
        }
    }

    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isCarrierMerged() || this.mSubscriptionId != wifiInfo.getSubscriptionId()) {
            return false;
        }
        return true;
    }

    public final String getKey() {
        return this.mKey;
    }
}
