package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.HotspotController;

/* renamed from: com.android.systemui.qs.tiles.HotspotTile */
public final class HotspotTile extends QSTileImpl<QSTile.BooleanState> {
    public final DataSaverController mDataSaverController;
    public final QSTile.Icon mEnabledStatic = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_hotspot);
    public final HotspotController mHotspotController;
    public boolean mListening;

    /* renamed from: com.android.systemui.qs.tiles.HotspotTile$CallbackInfo */
    public static final class CallbackInfo {
        public boolean isDataSaverEnabled;
        public boolean isHotspotEnabled;
        public int numConnectedDevices;

        public final String toString() {
            StringBuilder sb = new StringBuilder("CallbackInfo[");
            sb.append("isHotspotEnabled=");
            sb.append(this.isHotspotEnabled);
            sb.append(",numConnectedDevices=");
            sb.append(this.numConnectedDevices);
            sb.append(",isDataSaverEnabled=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.isDataSaverEnabled, ']');
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.HotspotTile$HotspotAndDataSaverCallbacks */
    public final class HotspotAndDataSaverCallbacks implements HotspotController.Callback, DataSaverController.Listener {
        public CallbackInfo mCallbackInfo = new CallbackInfo();

        public HotspotAndDataSaverCallbacks() {
        }

        public final void onDataSaverChanged(boolean z) {
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isDataSaverEnabled = z;
            HotspotTile.this.refreshState(callbackInfo);
        }

        public final void onHotspotAvailabilityChanged(boolean z) {
            if (!z) {
                Log.d(HotspotTile.this.TAG, "Tile removed. Hotspot no longer available");
                HotspotTile hotspotTile = HotspotTile.this;
                hotspotTile.mHost.removeTile(hotspotTile.mTileSpec);
            }
        }

        public final void onHotspotChanged(boolean z, int i) {
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isHotspotEnabled = z;
            callbackInfo.numConnectedDevices = i;
            HotspotTile.this.refreshState(callbackInfo);
        }
    }

    public final int getMetricsCategory() {
        return 120;
    }

    public final Intent getLongClickIntent() {
        return new Intent("com.android.settings.WIFI_TETHER_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_hotspot_label);
    }

    public final void handleClick(View view) {
        Object obj;
        boolean z = ((QSTile.BooleanState) this.mState).value;
        if (z || !this.mDataSaverController.isDataSaverEnabled()) {
            if (z) {
                obj = null;
            } else {
                obj = QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
            }
            refreshState(obj);
            this.mHotspotController.setHotspotEnabled(!z);
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        boolean z5;
        String str;
        int i2;
        boolean z6;
        boolean z7;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (obj == QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING) {
            z = true;
        } else {
            z = false;
        }
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        if (z || this.mHotspotController.isHotspotTransient()) {
            z2 = true;
        } else {
            z2 = false;
        }
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_tethering");
        if (obj instanceof CallbackInfo) {
            CallbackInfo callbackInfo = (CallbackInfo) obj;
            if (z || callbackInfo.isHotspotEnabled) {
                z7 = true;
            } else {
                z7 = false;
            }
            booleanState.value = z7;
            i = callbackInfo.numConnectedDevices;
            z3 = callbackInfo.isDataSaverEnabled;
        } else {
            if (z || this.mHotspotController.isHotspotEnabled()) {
                z6 = true;
            } else {
                z6 = false;
            }
            booleanState.value = z6;
            i = this.mHotspotController.getNumConnectedDevices();
            z3 = this.mDataSaverController.isDataSaverEnabled();
        }
        booleanState.icon = this.mEnabledStatic;
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_hotspot_label);
        booleanState.isTransient = z2;
        QSTile.SlashState slashState = booleanState.slash;
        if (booleanState.value || z2) {
            z4 = false;
        } else {
            z4 = true;
        }
        slashState.isSlashed = z4;
        if (z2) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(17302461);
        }
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
        if (booleanState.value || booleanState.isTransient) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z3) {
            booleanState.state = 0;
        } else {
            if (z5) {
                i2 = 2;
            } else {
                i2 = 1;
            }
            booleanState.state = i2;
        }
        if (z2) {
            str = this.mContext.getString(C1777R.string.quick_settings_hotspot_secondary_label_transient);
        } else if (z3) {
            str = this.mContext.getString(C1777R.string.quick_settings_hotspot_secondary_label_data_saver_enabled);
        } else if (i <= 0 || !z5) {
            str = null;
        } else {
            str = this.mContext.getResources().getQuantityString(C1777R.plurals.quick_settings_hotspot_secondary_label_num_devices, i, new Object[]{Integer.valueOf(i)});
        }
        booleanState.secondaryLabel = str;
        booleanState.stateDescription = str;
    }

    public final boolean isAvailable() {
        return this.mHotspotController.isHotspotSupported();
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public HotspotTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, HotspotController hotspotController, DataSaverController dataSaverController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        HotspotAndDataSaverCallbacks hotspotAndDataSaverCallbacks = new HotspotAndDataSaverCallbacks();
        this.mHotspotController = hotspotController;
        this.mDataSaverController = dataSaverController;
        hotspotController.observe((LifecycleOwner) this, hotspotAndDataSaverCallbacks);
        dataSaverController.observe((LifecycleOwner) this, hotspotAndDataSaverCallbacks);
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening != z) {
            this.mListening = z;
            if (z) {
                refreshState((Object) null);
            }
        }
    }

    public final void handleDestroy() {
        super.handleDestroy();
    }
}
