package com.android.systemui.p006qs.tiles;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.lifecycle.LifecycleOwner;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda4;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.CastTile */
public final class CastTile extends QSTileImpl<QSTile.BooleanState> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CastController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final C10402 mHotspotCallback;
    public boolean mHotspotConnected;
    public final KeyguardStateController mKeyguard;
    public final NetworkController mNetworkController;
    public final C10391 mSignalCallback;
    public boolean mWifiConnected;

    /* renamed from: com.android.systemui.qs.tiles.CastTile$Callback */
    public final class Callback implements CastController.Callback, KeyguardStateController.Callback {
        public Callback() {
        }

        public final void onCastDevicesChanged() {
            CastTile castTile = CastTile.this;
            Objects.requireNonNull(castTile);
            castTile.refreshState((Object) null);
        }

        public final void onKeyguardShowingChanged() {
            CastTile castTile = CastTile.this;
            Objects.requireNonNull(castTile);
            castTile.refreshState((Object) null);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.CastTile$DialogHolder */
    public static class DialogHolder {
        public Dialog mDialog;
    }

    public final int getMetricsCategory() {
        return 114;
    }

    public final void handleUserSwitch(int i) {
        handleRefreshState((Object) null);
        this.mController.setCurrentUserId(i);
    }

    static {
        new Intent("android.settings.CAST_SETTINGS");
    }

    public final ArrayList getActiveDevices() {
        ArrayList arrayList = new ArrayList();
        for (CastController.CastDevice castDevice : this.mController.getCastDevices()) {
            int i = castDevice.state;
            if (i == 2 || i == 1) {
                arrayList.add(castDevice);
            }
        }
        return arrayList;
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_cast_title);
    }

    public final void handleClick(View view) {
        boolean z;
        if (((QSTile.BooleanState) this.mState).state != 0) {
            ArrayList activeDevices = getActiveDevices();
            ArrayList activeDevices2 = getActiveDevices();
            if (activeDevices2.isEmpty() || (((CastController.CastDevice) activeDevices2.get(0)).tag instanceof MediaRouter.RouteInfo)) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                this.mController.stopCasting((CastController.CastDevice) activeDevices.get(0));
            } else if (!this.mKeyguard.isShowing()) {
                this.mUiHandler.post(new CastTile$$ExternalSyntheticLambda1(this, view, 0));
            } else {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new TaskView$$ExternalSyntheticLambda4(this, 7));
            }
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        int i2;
        boolean z;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        String string = this.mContext.getString(C1777R.string.quick_settings_cast_title);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.stateDescription = "";
        boolean z2 = false;
        booleanState.value = false;
        Iterator it = this.mController.getCastDevices().iterator();
        boolean z3 = false;
        while (true) {
            i = 2;
            if (!it.hasNext()) {
                break;
            }
            CastController.CastDevice castDevice = (CastController.CastDevice) it.next();
            int i3 = castDevice.state;
            if (i3 == 2) {
                booleanState.value = true;
                String str = castDevice.name;
                if (str == null) {
                    str = this.mContext.getString(C1777R.string.quick_settings_cast_device_default_name);
                }
                booleanState.secondaryLabel = str;
                booleanState.stateDescription += "," + this.mContext.getString(C1777R.string.accessibility_cast_name, new Object[]{booleanState.label});
                z3 = false;
            } else if (i3 == 1) {
                z3 = true;
            }
        }
        if (z3 && !booleanState.value) {
            booleanState.secondaryLabel = this.mContext.getString(C1777R.string.quick_settings_connecting);
        }
        if (booleanState.value) {
            i2 = C1777R.C1778drawable.ic_cast_connected;
        } else {
            i2 = C1777R.C1778drawable.ic_cast;
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(i2);
        if (this.mWifiConnected || this.mHotspotConnected) {
            z = true;
        } else {
            z = false;
        }
        if (z || booleanState.value) {
            boolean z4 = booleanState.value;
            if (!z4) {
                i = 1;
            }
            booleanState.state = i;
            if (!z4) {
                booleanState.secondaryLabel = "";
            }
            booleanState.expandedAccessibilityClassName = Button.class.getName();
            ArrayList activeDevices = getActiveDevices();
            if (activeDevices.isEmpty() || (((CastController.CastDevice) activeDevices.get(0)).tag instanceof MediaRouter.RouteInfo)) {
                z2 = true;
            }
            booleanState.forceExpandIcon = z2;
        } else {
            booleanState.state = 0;
            booleanState.secondaryLabel = this.mContext.getString(C1777R.string.quick_settings_cast_no_wifi);
            booleanState.forceExpandIcon = false;
        }
        booleanState.stateDescription += ", " + booleanState.secondaryLabel;
    }

    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public CastTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CastController castController, KeyguardStateController keyguardStateController, NetworkController networkController, HotspotController hotspotController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        C10391 r2 = new SignalCallback() {
            public final void setWifiIndicators(WifiIndicators wifiIndicators) {
                boolean z;
                IconState iconState;
                if (!wifiIndicators.enabled || (iconState = wifiIndicators.qsIcon) == null || !iconState.visible) {
                    z = false;
                } else {
                    z = true;
                }
                CastTile castTile = CastTile.this;
                if (z != castTile.mWifiConnected) {
                    castTile.mWifiConnected = z;
                    if (!castTile.mHotspotConnected) {
                        Objects.requireNonNull(castTile);
                        castTile.refreshState((Object) null);
                    }
                }
            }
        };
        this.mSignalCallback = r2;
        C10402 r3 = new HotspotController.Callback() {
            public final void onHotspotChanged(boolean z, int i) {
                boolean z2;
                if (!z || i <= 0) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                CastTile castTile = CastTile.this;
                if (z2 != castTile.mHotspotConnected) {
                    castTile.mHotspotConnected = z2;
                    if (!castTile.mWifiConnected) {
                        castTile.refreshState((Object) null);
                    }
                }
            }
        };
        this.mHotspotCallback = r3;
        this.mController = castController;
        this.mKeyguard = keyguardStateController;
        this.mNetworkController = networkController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        castController.observe((LifecycleOwner) this, callback);
        keyguardStateController.observe((LifecycleOwner) this, callback);
        networkController.observe((LifecycleOwner) this, r2);
        hotspotController.observe((LifecycleOwner) this, r3);
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (QSTileImpl.DEBUG) {
            ViewCompat$$ExternalSyntheticLambda0.m12m("handleSetListening ", z, this.TAG);
        }
        if (!z) {
            this.mController.setDiscovering();
        }
    }

    public final void handleLongClick(View view) {
        handleClick(view);
    }
}
