package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.systemui.statusbar.StatusBarMobileView;
import com.android.systemui.statusbar.StatusBarWifiView;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarIconControllerImpl$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ StatusBarIconHolder f$1;

    public /* synthetic */ StatusBarIconControllerImpl$$ExternalSyntheticLambda4(int i, StatusBarIconHolder statusBarIconHolder) {
        this.f$0 = i;
        this.f$1 = statusBarIconHolder;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        StatusBarIconHolder statusBarIconHolder = this.f$1;
        StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) obj;
        Objects.requireNonNull(iconManager);
        Objects.requireNonNull(statusBarIconHolder);
        int i2 = statusBarIconHolder.mType;
        if (i2 == 0) {
            iconManager.onSetIcon(i, statusBarIconHolder.mIcon);
        } else if (i2 == 1) {
            StatusBarSignalPolicy.WifiIconState wifiIconState = statusBarIconHolder.mWifiState;
            StatusBarWifiView statusBarWifiView = (StatusBarWifiView) iconManager.mGroup.getChildAt(i);
            if (statusBarWifiView != null) {
                statusBarWifiView.applyWifiState(wifiIconState);
            }
            if (iconManager.mIsInDemoMode) {
                DemoStatusIcons demoStatusIcons = iconManager.mDemoStatusIcons;
                Objects.requireNonNull(demoStatusIcons);
                Log.d("DemoStatusIcons", "updateWifiState: ");
                StatusBarWifiView statusBarWifiView2 = demoStatusIcons.mWifiView;
                if (statusBarWifiView2 == null) {
                    demoStatusIcons.addDemoWifiView(wifiIconState);
                } else {
                    statusBarWifiView2.applyWifiState(wifiIconState);
                }
            }
        } else if (i2 == 2) {
            StatusBarSignalPolicy.MobileIconState mobileIconState = statusBarIconHolder.mMobileState;
            StatusBarMobileView statusBarMobileView = (StatusBarMobileView) iconManager.mGroup.getChildAt(i);
            if (statusBarMobileView != null) {
                statusBarMobileView.applyMobileState(mobileIconState);
            }
            if (iconManager.mIsInDemoMode) {
                DemoStatusIcons demoStatusIcons2 = iconManager.mDemoStatusIcons;
                Objects.requireNonNull(demoStatusIcons2);
                Log.d("DemoStatusIcons", "updateMobileState: ");
                for (int i3 = 0; i3 < demoStatusIcons2.mMobileViews.size(); i3++) {
                    StatusBarMobileView statusBarMobileView2 = demoStatusIcons2.mMobileViews.get(i3);
                    if (statusBarMobileView2.getState().subId == mobileIconState.subId) {
                        statusBarMobileView2.applyMobileState(mobileIconState);
                        return;
                    }
                }
                demoStatusIcons2.addMobileView(mobileIconState);
            }
        }
    }
}
