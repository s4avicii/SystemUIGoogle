package com.android.systemui.p006qs.tiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Prefs;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.SignalTileView;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSIconView;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.CellularTile */
public final class CellularTile extends QSTileImpl<QSTile.SignalState> {
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final KeyguardStateController mKeyguard;
    public final CellSignalCallback mSignalCallback;

    /* renamed from: com.android.systemui.qs.tiles.CellularTile$CallbackInfo */
    public static final class CallbackInfo {
        public boolean activityIn;
        public boolean activityOut;
        public boolean airplaneModeEnabled;
        public CharSequence dataContentDescription;
        public String dataSubscriptionName;
        public boolean multipleSubs;
        public boolean noSim;
        public boolean roaming;

        public CallbackInfo() {
        }

        public CallbackInfo(int i) {
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.CellularTile$CellSignalCallback */
    public final class CellSignalCallback implements SignalCallback {
        public final CallbackInfo mInfo = new CallbackInfo(0);

        public CellSignalCallback() {
        }

        public final void setIsAirplaneMode(IconState iconState) {
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.airplaneModeEnabled = iconState.visible;
            CellularTile.this.refreshState(callbackInfo);
        }

        public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
            CharSequence charSequence;
            if (mobileDataIndicators.qsIcon != null) {
                this.mInfo.dataSubscriptionName = CellularTile.this.mController.getMobileDataNetworkName();
                CallbackInfo callbackInfo = this.mInfo;
                if (mobileDataIndicators.qsDescription != null) {
                    charSequence = mobileDataIndicators.typeContentDescriptionHtml;
                } else {
                    charSequence = null;
                }
                callbackInfo.dataContentDescription = charSequence;
                callbackInfo.activityIn = mobileDataIndicators.activityIn;
                callbackInfo.activityOut = mobileDataIndicators.activityOut;
                callbackInfo.roaming = mobileDataIndicators.roaming;
                boolean z = true;
                if (CellularTile.this.mController.getNumberSubscriptions() <= 1) {
                    z = false;
                }
                callbackInfo.multipleSubs = z;
                CellularTile.this.refreshState(this.mInfo);
            }
        }

        public final void setNoSims(boolean z, boolean z2) {
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.noSim = z;
            CellularTile.this.refreshState(callbackInfo);
        }
    }

    public final int getMetricsCategory() {
        return 115;
    }

    public final QSIconView createTileView(Context context) {
        return new SignalTileView(context);
    }

    public final Intent getLongClickIntent() {
        if (((QSTile.SignalState) this.mState).state == 0) {
            return new Intent("android.settings.WIRELESS_SETTINGS");
        }
        Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
        if (SubscriptionManager.getDefaultDataSubscriptionId() != -1) {
            intent.putExtra("android.provider.extra.SUB_ID", SubscriptionManager.getDefaultDataSubscriptionId());
        }
        return intent;
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_cellular_detail_title);
    }

    public final void handleClick(View view) {
        if (((QSTile.SignalState) this.mState).state != 0) {
            DataUsageController dataUsageController = this.mDataController;
            Objects.requireNonNull(dataUsageController);
            if (!dataUsageController.getTelephonyManager().isDataEnabled()) {
                this.mDataController.setMobileDataEnabled(true);
            } else if (Prefs.getBoolean(this.mContext, "QsHasTurnedOffMobileData")) {
                this.mDataController.setMobileDataEnabled(false);
            } else {
                String mobileDataNetworkName = this.mController.getMobileDataNetworkName();
                boolean isMobileDataNetworkInService = this.mController.isMobileDataNetworkInService();
                if (TextUtils.isEmpty(mobileDataNetworkName) || !isMobileDataNetworkInService) {
                    mobileDataNetworkName = this.mContext.getString(C1777R.string.mobile_data_disable_message_default_carrier);
                }
                AlertDialog create = new AlertDialog.Builder(this.mContext).setTitle(C1777R.string.mobile_data_disable_title).setMessage(this.mContext.getString(C1777R.string.mobile_data_disable_message, new Object[]{mobileDataNetworkName})).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).setPositiveButton(17039652, new CellularTile$$ExternalSyntheticLambda0(this)).create();
                create.getWindow().setType(2009);
                SystemUIDialog.setShowForAllUsers(create);
                SystemUIDialog.registerDismissListener(create);
                SystemUIDialog.setWindowOnTop(create, this.mKeyguard.isShowing());
                create.show();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0136  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleUpdateState(com.android.systemui.plugins.p005qs.QSTile.State r10, java.lang.Object r11) {
        /*
            r9 = this;
            com.android.systemui.plugins.qs.QSTile$SignalState r10 = (com.android.systemui.plugins.p005qs.QSTile.SignalState) r10
            com.android.systemui.qs.tiles.CellularTile$CallbackInfo r11 = (com.android.systemui.p006qs.tiles.CellularTile.CallbackInfo) r11
            if (r11 != 0) goto L_0x000a
            com.android.systemui.qs.tiles.CellularTile$CellSignalCallback r11 = r9.mSignalCallback
            com.android.systemui.qs.tiles.CellularTile$CallbackInfo r11 = r11.mInfo
        L_0x000a:
            android.content.Context r0 = r9.mContext
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131952764(0x7f13047c, float:1.954198E38)
            java.lang.String r1 = r0.getString(r1)
            r10.label = r1
            com.android.settingslib.net.DataUsageController r1 = r9.mDataController
            java.util.Objects.requireNonNull(r1)
            android.telephony.TelephonyManager r2 = r1.getTelephonyManager()
            boolean r2 = r2.isDataCapable()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0037
            android.telephony.TelephonyManager r1 = r1.getTelephonyManager()
            int r1 = r1.getSimState()
            r2 = 5
            if (r1 != r2) goto L_0x0037
            r1 = r3
            goto L_0x0038
        L_0x0037:
            r1 = r4
        L_0x0038:
            if (r1 == 0) goto L_0x004b
            com.android.settingslib.net.DataUsageController r1 = r9.mDataController
            java.util.Objects.requireNonNull(r1)
            android.telephony.TelephonyManager r1 = r1.getTelephonyManager()
            boolean r1 = r1.isDataEnabled()
            if (r1 == 0) goto L_0x004b
            r1 = r3
            goto L_0x004c
        L_0x004b:
            r1 = r4
        L_0x004c:
            r10.value = r1
            if (r1 == 0) goto L_0x0056
            boolean r2 = r11.activityIn
            if (r2 == 0) goto L_0x0056
            r2 = r3
            goto L_0x0057
        L_0x0056:
            r2 = r4
        L_0x0057:
            r10.activityIn = r2
            if (r1 == 0) goto L_0x0061
            boolean r2 = r11.activityOut
            if (r2 == 0) goto L_0x0061
            r2 = r3
            goto L_0x0062
        L_0x0061:
            r2 = r4
        L_0x0062:
            r10.activityOut = r2
            java.lang.Class<android.widget.Switch> r2 = android.widget.Switch.class
            java.lang.String r2 = r2.getName()
            r10.expandedAccessibilityClassName = r2
            boolean r2 = r11.noSim
            if (r2 == 0) goto L_0x007a
            r2 = 2131232230(0x7f0805e6, float:1.8080563E38)
            com.android.systemui.plugins.qs.QSTile$Icon r2 = com.android.systemui.p006qs.tileimpl.QSTileImpl.ResourceIcon.get(r2)
            r10.icon = r2
            goto L_0x0083
        L_0x007a:
            r2 = 2131232276(0x7f080614, float:1.8080657E38)
            com.android.systemui.plugins.qs.QSTile$Icon r2 = com.android.systemui.p006qs.tileimpl.QSTileImpl.ResourceIcon.get(r2)
            r10.icon = r2
        L_0x0083:
            boolean r2 = r11.noSim
            java.lang.String r5 = ""
            if (r2 == 0) goto L_0x0096
            r10.state = r4
            r9 = 2131952547(0x7f1303a3, float:1.954154E38)
            java.lang.String r9 = r0.getString(r9)
            r10.secondaryLabel = r9
            goto L_0x012b
        L_0x0096:
            boolean r2 = r11.airplaneModeEnabled
            if (r2 == 0) goto L_0x00a7
            r10.state = r4
            r9 = 2131953313(0x7f1306a1, float:1.9543093E38)
            java.lang.String r9 = r0.getString(r9)
            r10.secondaryLabel = r9
            goto L_0x012b
        L_0x00a7:
            if (r1 == 0) goto L_0x0120
            r0 = 2
            r10.state = r0
            boolean r1 = r11.multipleSubs
            if (r1 == 0) goto L_0x00b3
            java.lang.String r1 = r11.dataSubscriptionName
            goto L_0x00b4
        L_0x00b3:
            r1 = r5
        L_0x00b4:
            boolean r2 = r11.roaming
            r6 = 2131952236(0x7f13026c, float:1.954091E38)
            if (r2 == 0) goto L_0x00df
            java.lang.CharSequence r2 = r11.dataContentDescription
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00df
            android.content.Context r2 = r9.mContext
            java.lang.String r2 = r2.getString(r6)
            java.lang.CharSequence r11 = r11.dataContentDescription
            java.lang.String r11 = r11.toString()
            android.content.Context r6 = r9.mContext
            r7 = 2131952774(0x7f130486, float:1.9542E38)
            java.lang.Object[] r8 = new java.lang.Object[r0]
            r8[r4] = r2
            r8[r3] = r11
            java.lang.String r11 = r6.getString(r7, r8)
            goto L_0x00ec
        L_0x00df:
            boolean r2 = r11.roaming
            if (r2 == 0) goto L_0x00ea
            android.content.Context r11 = r9.mContext
            java.lang.String r11 = r11.getString(r6)
            goto L_0x00ec
        L_0x00ea:
            java.lang.CharSequence r11 = r11.dataContentDescription
        L_0x00ec:
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 == 0) goto L_0x00fb
            java.lang.String r9 = r1.toString()
            android.text.Spanned r9 = android.text.Html.fromHtml(r9, r4)
            goto L_0x011d
        L_0x00fb:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x010a
            java.lang.String r9 = r11.toString()
            android.text.Spanned r9 = android.text.Html.fromHtml(r9, r4)
            goto L_0x011d
        L_0x010a:
            android.content.Context r9 = r9.mContext
            r2 = 2131952763(0x7f13047b, float:1.9541978E38)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r4] = r1
            r0[r3] = r11
            java.lang.String r9 = r9.getString(r2, r0)
            android.text.Spanned r9 = android.text.Html.fromHtml(r9, r4)
        L_0x011d:
            r10.secondaryLabel = r9
            goto L_0x012b
        L_0x0120:
            r10.state = r3
            r9 = 2131952098(0x7f1301e2, float:1.954063E38)
            java.lang.String r9 = r0.getString(r9)
            r10.secondaryLabel = r9
        L_0x012b:
            java.lang.CharSequence r9 = r10.label
            r10.contentDescription = r9
            int r9 = r10.state
            if (r9 != r3) goto L_0x0136
            r10.stateDescription = r5
            goto L_0x013a
        L_0x0136:
            java.lang.CharSequence r9 = r10.secondaryLabel
            r10.stateDescription = r9
        L_0x013a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.CellularTile.handleUpdateState(com.android.systemui.plugins.qs.QSTile$State, java.lang.Object):void");
    }

    public final boolean isAvailable() {
        if (!this.mController.hasMobileDataFeature() || this.mHost.getUserContext().getUserId() != 0) {
            return false;
        }
        return true;
    }

    public final QSTile.State newTileState() {
        return new QSTile.SignalState();
    }

    public CellularTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        CellSignalCallback cellSignalCallback = new CellSignalCallback();
        this.mSignalCallback = cellSignalCallback;
        this.mController = networkController;
        this.mKeyguard = keyguardStateController;
        this.mDataController = networkController.getMobileDataController();
        networkController.observe((Lifecycle) this.mLifecycle, cellSignalCallback);
    }

    public final void handleSecondaryClick(View view) {
        handleLongClick(view);
    }
}
