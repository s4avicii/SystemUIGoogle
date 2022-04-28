package com.android.systemui;

import androidx.lifecycle.Lifecycle;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0;
import com.google.android.systemui.gamedashboard.ScreenRecordController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                ScreenDecorations screenDecorations = (ScreenDecorations) this.f$0;
                boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                Objects.requireNonNull(screenDecorations);
                screenDecorations.mTunerService.removeTunable(screenDecorations);
                return;
            case 1:
                PluginActionManager.$r8$lambda$kBZKG6dtfbpWOlthMBUiG2NGTM4((PluginActionManager) this.f$0);
                return;
            case 2:
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) this.f$0;
                boolean z2 = AccessPointControllerImpl.DEBUG;
                Objects.requireNonNull(accessPointControllerImpl);
                accessPointControllerImpl.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                return;
            case 3:
                StatusBarSignalPolicy statusBarSignalPolicy = (StatusBarSignalPolicy) this.f$0;
                boolean z3 = StatusBarSignalPolicy.DEBUG;
                Objects.requireNonNull(statusBarSignalPolicy);
                boolean isVpnEnabled = statusBarSignalPolicy.mSecurityController.isVpnEnabled();
                if (statusBarSignalPolicy.mSecurityController.isVpnBranded()) {
                    i = C1777R.C1778drawable.stat_sys_branded_vpn;
                } else {
                    i = C1777R.C1778drawable.stat_sys_vpn_ic;
                }
                statusBarSignalPolicy.mIconController.setIcon(statusBarSignalPolicy.mSlotVpn, i, statusBarSignalPolicy.mContext.getResources().getString(C1777R.string.accessibility_vpn_on));
                statusBarSignalPolicy.mIconController.setIconVisibility(statusBarSignalPolicy.mSlotVpn, isVpnEnabled);
                return;
            case 4:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                int i2 = BubbleStackView.FLYOUT_HIDE_AFTER;
                Objects.requireNonNull(bubbleStackView);
                bubbleStackView.post(new StandardWifiEntry$$ExternalSyntheticLambda0(bubbleStackView, 9));
                return;
            default:
                ScreenRecordController screenRecordController = (ScreenRecordController) this.f$0;
                Objects.requireNonNull(screenRecordController);
                screenRecordController.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                return;
        }
    }
}
