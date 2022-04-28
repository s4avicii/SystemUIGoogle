package com.android.systemui.statusbar.phone;

import android.app.IStopUserCallback;
import android.os.RemoteException;
import android.util.Log;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthCredentialView;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.p006qs.ReduceBrightColorsController;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.policy.DateView;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.wifitrackerlib.SavedNetworkTracker;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda18 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda18(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ShadeController) this.f$0).collapsePanel();
                return;
            case 1:
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) this.f$0;
                Objects.requireNonNull(cachedBluetoothDevice);
                cachedBluetoothDevice.dispatchAttributesChanged();
                return;
            case 2:
                AuthCredentialView authCredentialView = (AuthCredentialView) this.f$0;
                int i = AuthCredentialView.$r8$clinit;
                Objects.requireNonNull(authCredentialView);
                authCredentialView.animate().translationY(0.0f).setDuration(150).alpha(1.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).withLayer().start();
                return;
            case 3:
                GlobalActionsDialogLite.LogoutAction logoutAction = (GlobalActionsDialogLite.LogoutAction) this.f$0;
                Objects.requireNonNull(logoutAction);
                try {
                    int i2 = GlobalActionsDialogLite.this.getCurrentUser().id;
                    GlobalActionsDialogLite.this.mIActivityManager.switchUser(0);
                    GlobalActionsDialogLite.this.mIActivityManager.stopUser(i2, true, (IStopUserCallback) null);
                    return;
                } catch (RemoteException e) {
                    Log.e("GlobalActionsDialogLite", "Couldn't logout user " + e);
                    return;
                }
            case 4:
                NavigationBarTransitions.C09221 r4 = (NavigationBarTransitions.C09221) this.f$0;
                int i3 = NavigationBarTransitions.C09221.$r8$clinit;
                Objects.requireNonNull(r4);
                NavigationBarTransitions.this.applyLightsOut(true, false);
                return;
            case 5:
                InternetDialog internetDialog = (InternetDialog) this.f$0;
                boolean z = InternetDialog.DEBUG;
                Objects.requireNonNull(internetDialog);
                internetDialog.updateDialog(true);
                return;
            case FalsingManager.VERSION:
                KeyguardIndicationController keyguardIndicationController = (KeyguardIndicationController) this.f$0;
                Objects.requireNonNull(keyguardIndicationController);
                keyguardIndicationController.mWakeLock.setAcquired(false);
                return;
            case 7:
                boolean z2 = StatusBarStateControllerImpl.DEBUG_IMMERSIVE_APPS;
                ((StatusBarStateControllerImpl) this.f$0).beginInteractionJankMonitor();
                return;
            case 9:
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.f$0;
                Objects.requireNonNull(expandableNotificationRow);
                expandableNotificationRow.resetTranslation();
                expandableNotificationRow.updateContentAccessibilityImportanceForGuts(false);
                return;
            case 10:
                AutoTileManager.C14046 r42 = (AutoTileManager.C14046) this.f$0;
                Objects.requireNonNull(r42);
                AutoTileManager.this.mReduceBrightColorsController.removeCallback((ReduceBrightColorsController.Listener) r42);
                return;
            case QSTileImpl.C1034H.STALE:
                DateView.C16101 r43 = (DateView.C16101) this.f$0;
                int i4 = DateView.C16101.$r8$clinit;
                Objects.requireNonNull(r43);
                DateView.this.updateClock();
                return;
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                VolumeDialogImpl.RingerDrawerItemClickListener ringerDrawerItemClickListener = (VolumeDialogImpl.RingerDrawerItemClickListener) this.f$0;
                Objects.requireNonNull(ringerDrawerItemClickListener);
                VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.setAlpha(0.0f);
                if (!VolumeDialogImpl.this.isLandscape()) {
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    volumeDialogImpl.mSelectedRingerContainer.setTranslationY(volumeDialogImpl.getTranslationInDrawerForRingerMode(ringerDrawerItemClickListener.mClickedRingerMode));
                } else {
                    VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                    volumeDialogImpl2.mSelectedRingerContainer.setTranslationX(volumeDialogImpl2.getTranslationInDrawerForRingerMode(ringerDrawerItemClickListener.mClickedRingerMode));
                }
                VolumeDialogImpl.this.mSelectedRingerContainer.setVisibility(0);
                VolumeDialogImpl.this.hideRingerDrawer();
                return;
            default:
                ((SavedNetworkTracker.SavedNetworkTrackerCallback) this.f$0).onSubscriptionWifiEntriesChanged();
                return;
        }
    }
}
