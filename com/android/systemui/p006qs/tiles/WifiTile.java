package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.systemui.p006qs.AlphaControlledSignalTileView;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSIconView;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.WifiTile */
public final class WifiTile extends QSTileImpl<QSTile.SignalState> {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final NetworkController mController;
    public boolean mExpectDisabled;
    public final WifiSignalCallback mSignalCallback;
    public final QSTile.SignalState mStateBeforeClick;
    public final AccessPointController mWifiController;

    /* renamed from: com.android.systemui.qs.tiles.WifiTile$CallbackInfo */
    public static final class CallbackInfo {
        public boolean activityIn;
        public boolean activityOut;
        public boolean connected;
        public boolean enabled;
        public boolean isTransient;
        public String ssid;
        public String statusLabel;
        public String wifiSignalContentDescription;
        public int wifiSignalIconId;

        public final String toString() {
            StringBuilder sb = new StringBuilder("CallbackInfo[");
            sb.append("enabled=");
            sb.append(this.enabled);
            sb.append(",connected=");
            sb.append(this.connected);
            sb.append(",wifiSignalIconId=");
            sb.append(this.wifiSignalIconId);
            sb.append(",ssid=");
            sb.append(this.ssid);
            sb.append(",activityIn=");
            sb.append(this.activityIn);
            sb.append(",activityOut=");
            sb.append(this.activityOut);
            sb.append(",wifiSignalContentDescription=");
            sb.append(this.wifiSignalContentDescription);
            sb.append(",isTransient=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.isTransient, ']');
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.WifiTile$WifiSignalCallback */
    public final class WifiSignalCallback implements SignalCallback {
        public final CallbackInfo mInfo = new CallbackInfo();

        public WifiSignalCallback() {
        }

        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            if (QSTileImpl.DEBUG) {
                KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("onWifiSignalChanged enabled="), wifiIndicators.enabled, WifiTile.this.TAG);
            }
            IconState iconState = wifiIndicators.qsIcon;
            if (iconState != null) {
                CallbackInfo callbackInfo = this.mInfo;
                callbackInfo.enabled = wifiIndicators.enabled;
                callbackInfo.connected = iconState.visible;
                callbackInfo.wifiSignalIconId = iconState.icon;
                callbackInfo.ssid = wifiIndicators.description;
                callbackInfo.activityIn = wifiIndicators.activityIn;
                callbackInfo.activityOut = wifiIndicators.activityOut;
                callbackInfo.wifiSignalContentDescription = iconState.contentDescription;
                callbackInfo.isTransient = wifiIndicators.isTransient;
                callbackInfo.statusLabel = wifiIndicators.statusLabel;
                WifiTile wifiTile = WifiTile.this;
                Objects.requireNonNull(wifiTile);
                wifiTile.refreshState((Object) null);
            }
        }
    }

    public final int getMetricsCategory() {
        return 126;
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        if (str.charAt(i) == '\"') {
            return str.substring(1, i);
        }
        return str;
    }

    public final QSIconView createTileView(Context context) {
        return new AlphaControlledSignalTileView(context);
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_wifi_label);
    }

    public final void handleClick(View view) {
        Object obj;
        ((QSTile.SignalState) this.mState).copyTo(this.mStateBeforeClick);
        boolean z = ((QSTile.SignalState) this.mState).value;
        if (z) {
            obj = null;
        } else {
            obj = QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        }
        refreshState(obj);
        this.mController.setWifiEnabled(!z);
        this.mExpectDisabled = z;
        if (z) {
            this.mHandler.postDelayed(new TaskView$$ExternalSyntheticLambda3(this, 3), 350);
        }
    }

    public final void handleSecondaryClick(View view) {
        if (!this.mWifiController.canConfigWifi()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
        } else if (!((QSTile.SignalState) this.mState).value) {
            this.mController.setWifiEnabled(true);
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        CharSequence charSequence;
        int i;
        QSTile.SignalState signalState = (QSTile.SignalState) state;
        if (QSTileImpl.DEBUG) {
            Log.d(this.TAG, "handleUpdateState arg=" + obj);
        }
        CallbackInfo callbackInfo = this.mSignalCallback.mInfo;
        if (this.mExpectDisabled) {
            if (!callbackInfo.enabled) {
                this.mExpectDisabled = false;
            } else {
                return;
            }
        }
        if (obj == QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING) {
            z = true;
        } else {
            z = false;
        }
        if (!callbackInfo.enabled || (i = callbackInfo.wifiSignalIconId) <= 0 || (callbackInfo.ssid == null && i == 17302891)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (callbackInfo.ssid == null && callbackInfo.wifiSignalIconId == 17302891) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (signalState.slash == null) {
            QSTile.SlashState slashState = new QSTile.SlashState();
            signalState.slash = slashState;
            slashState.rotation = 6.0f;
        }
        signalState.slash.isSlashed = false;
        if (z || callbackInfo.isTransient) {
            z4 = true;
        } else {
            z4 = false;
        }
        String str = callbackInfo.statusLabel;
        if (z4) {
            str = this.mContext.getString(C1777R.string.quick_settings_wifi_secondary_label_transient);
        }
        signalState.secondaryLabel = str;
        signalState.state = 2;
        signalState.dualTarget = true;
        if (z || callbackInfo.enabled) {
            z5 = true;
        } else {
            z5 = false;
        }
        signalState.value = z5;
        boolean z8 = callbackInfo.enabled;
        if (!z8 || !callbackInfo.activityIn) {
            z6 = false;
        } else {
            z6 = true;
        }
        signalState.activityIn = z6;
        if (!z8 || !callbackInfo.activityOut) {
            z7 = false;
        } else {
            z7 = true;
        }
        signalState.activityOut = z7;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        Resources resources = this.mContext.getResources();
        if (z4) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302859);
            signalState.label = resources.getString(C1777R.string.quick_settings_wifi_label);
        } else if (!signalState.value) {
            signalState.slash.isSlashed = true;
            signalState.state = 1;
            signalState.icon = QSTileImpl.ResourceIcon.get(17302891);
            signalState.label = resources.getString(C1777R.string.quick_settings_wifi_label);
        } else if (z2) {
            signalState.icon = QSTileImpl.ResourceIcon.get(callbackInfo.wifiSignalIconId);
            String str2 = callbackInfo.ssid;
            if (str2 != null) {
                charSequence = removeDoubleQuotes(str2);
            } else {
                charSequence = getTileLabel();
            }
            signalState.label = charSequence;
        } else if (z3) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302891);
            signalState.label = resources.getString(C1777R.string.quick_settings_wifi_label);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302891);
            signalState.label = resources.getString(C1777R.string.quick_settings_wifi_label);
        }
        stringBuffer.append(this.mContext.getString(C1777R.string.quick_settings_wifi_label));
        stringBuffer.append(",");
        if (signalState.value && z2) {
            stringBuffer2.append(callbackInfo.wifiSignalContentDescription);
            stringBuffer.append(removeDoubleQuotes(callbackInfo.ssid));
            if (!TextUtils.isEmpty(signalState.secondaryLabel)) {
                stringBuffer.append(",");
                stringBuffer.append(signalState.secondaryLabel);
            }
        }
        signalState.stateDescription = stringBuffer2.toString();
        signalState.contentDescription = stringBuffer.toString();
        signalState.dualLabelContentDescription = resources.getString(C1777R.string.accessibility_quick_settings_open_settings, new Object[]{getTileLabel()});
        signalState.expandedAccessibilityClassName = Switch.class.getName();
    }

    public final boolean isAvailable() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi");
    }

    public final QSTile.State newTileState() {
        return new QSTile.SignalState();
    }

    public WifiTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTile.SignalState signalState = new QSTile.SignalState();
        this.mStateBeforeClick = signalState;
        WifiSignalCallback wifiSignalCallback = new WifiSignalCallback();
        this.mSignalCallback = wifiSignalCallback;
        this.mController = networkController;
        this.mWifiController = accessPointController;
        networkController.observe((Lifecycle) this.mLifecycle, wifiSignalCallback);
        signalState.spec = "wifi";
    }

    public final Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }
}
