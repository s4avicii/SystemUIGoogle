package com.google.android.systemui.p016qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tiles.BatterySaverTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.settings.SecureSettings;

/* renamed from: com.google.android.systemui.qs.tiles.BatterySaverTileGoogle */
public final class BatterySaverTileGoogle extends BatterySaverTile {
    public boolean mExtreme;

    public final void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        super.handleUpdateState(booleanState, obj);
        if (booleanState.state != 2 || !this.mExtreme) {
            booleanState.secondaryLabel = "";
        } else {
            booleanState.secondaryLabel = this.mContext.getString(C1777R.string.extreme_battery_saver_text);
        }
        booleanState.stateDescription = booleanState.secondaryLabel;
    }

    public final void onExtremeBatterySaverChanged(boolean z) {
        if (this.mExtreme != z) {
            this.mExtreme = z;
            refreshState((Object) null);
        }
    }

    public BatterySaverTileGoogle(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BatteryController batteryController, SecureSettings secureSettings) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, batteryController, secureSettings);
    }
}
