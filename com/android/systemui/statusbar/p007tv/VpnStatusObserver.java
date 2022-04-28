package com.android.systemui.statusbar.p007tv;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import com.android.internal.net.VpnConfig;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.policy.SecurityController;

/* renamed from: com.android.systemui.statusbar.tv.VpnStatusObserver */
/* compiled from: VpnStatusObserver.kt */
public final class VpnStatusObserver extends CoreStartable implements SecurityController.SecurityControllerCallback {
    public final NotificationManager notificationManager;
    public final SecurityController securityController;
    public boolean vpnConnected;
    public final Notification.Builder vpnConnectedNotificationBuilder;
    public final Notification vpnDisconnectedNotification;

    public final void onStateChanged() {
        boolean isVpnEnabled = this.securityController.isVpnEnabled();
        if (this.vpnConnected != isVpnEnabled) {
            if (isVpnEnabled) {
                NotificationManager notificationManager2 = this.notificationManager;
                Notification.Builder builder = this.vpnConnectedNotificationBuilder;
                String primaryVpnName = this.securityController.getPrimaryVpnName();
                if (primaryVpnName == null) {
                    primaryVpnName = this.securityController.getWorkProfileVpnName();
                }
                if (primaryVpnName != null) {
                    builder.setContentText(this.mContext.getString(C1777R.string.notification_disclosure_vpn_text, new Object[]{primaryVpnName}));
                }
                notificationManager2.notify("VpnStatusObserver", 20, builder.build());
            } else {
                NotificationManager notificationManager3 = this.notificationManager;
                notificationManager3.cancel("VpnStatusObserver", 20);
                notificationManager3.notify("VpnStatusObserver", 17, this.vpnDisconnectedNotification);
            }
            this.vpnConnected = isVpnEnabled;
        }
    }

    public final void start() {
        this.securityController.addCallback(this);
    }

    public VpnStatusObserver(Context context, SecurityController securityController2) {
        super(context);
        this.securityController = securityController2;
        NotificationManager from = NotificationManager.from(context);
        this.notificationManager = from;
        from.createNotificationChannel(new NotificationChannel("VPN Status", "VPN Status", 4));
        Notification.Builder builder = new Notification.Builder(this.mContext, "VPN Status");
        boolean isVpnBranded = securityController2.isVpnBranded();
        int i = C1777R.C1778drawable.stat_sys_branded_vpn;
        this.vpnConnectedNotificationBuilder = builder.setSmallIcon(isVpnBranded ? C1777R.C1778drawable.stat_sys_branded_vpn : C1777R.C1778drawable.stat_sys_vpn_ic).setVisibility(1).setCategory("sys").extend(new Notification.TvExtender()).setOngoing(true).setContentTitle(this.mContext.getString(C1777R.string.notification_vpn_connected)).setContentIntent(VpnConfig.getIntentForStatusPanel(this.mContext));
        this.vpnDisconnectedNotification = new Notification.Builder(this.mContext, "VPN Status").setSmallIcon(!securityController2.isVpnBranded() ? C1777R.C1778drawable.stat_sys_vpn_ic : i).setVisibility(1).setCategory("sys").extend(new Notification.TvExtender()).setTimeoutAfter(5000).setContentTitle(this.mContext.getString(C1777R.string.notification_vpn_disconnected)).build();
    }
}
