package com.android.systemui.statusbar;

import android.view.ViewGroup;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.StatusBar$2$Callback$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;

public interface NotificationShadeWindowController extends RemoteInputController.Callback {

    public interface ForcePluginOpenListener {
    }

    public interface OtherwisedCollapsedListener {
    }

    void attach$1() {
    }

    boolean getForcePluginOpen() {
        return false;
    }

    ViewGroup getNotificationShadeView() {
        return null;
    }

    boolean getPanelExpanded() {
        return false;
    }

    boolean isLaunchingActivity() {
        return false;
    }

    boolean isShowingWallpaper() {
        return false;
    }

    void notifyStateChangedCallbacks() {
    }

    void onRemoteInputActive(boolean z) {
    }

    void registerCallback(StatusBarWindowCallback statusBarWindowCallback) {
    }

    void setBackdropShowing(boolean z) {
    }

    void setBackgroundBlurRadius(int i) {
    }

    void setBouncerShowing(boolean z) {
    }

    void setDozeScreenBrightness(int i) {
    }

    void setForceDozeBrightness(boolean z) {
    }

    void setForcePluginOpen(boolean z, Object obj) {
    }

    void setForcePluginOpenListener(StatusBarTouchableRegionManager$$ExternalSyntheticLambda0 statusBarTouchableRegionManager$$ExternalSyntheticLambda0) {
    }

    void setForceWindowCollapsed(boolean z) {
    }

    void setHeadsUpShowing(boolean z) {
    }

    void setKeyguardFadingAway(boolean z) {
    }

    void setKeyguardGoingAway(boolean z) {
    }

    void setKeyguardNeedsInput(boolean z) {
    }

    void setKeyguardOccluded(boolean z) {
    }

    void setKeyguardShowing(boolean z) {
    }

    void setLaunchingActivity(boolean z) {
    }

    void setLightRevealScrimOpaque(boolean z) {
    }

    void setNotTouchable() {
    }

    void setNotificationShadeFocusable(boolean z) {
    }

    void setNotificationShadeView(NotificationShadeWindowView notificationShadeWindowView) {
    }

    void setPanelExpanded(boolean z) {
    }

    void setPanelVisible(boolean z) {
    }

    void setQsExpanded(boolean z) {
    }

    void setRequestTopUi(boolean z, String str) {
    }

    void setScrimsVisibility(int i) {
    }

    void setScrimsVisibilityListener(NotificationShadeDepthController.C11721 r1) {
    }

    void setStateListener(StatusBar$2$Callback$$ExternalSyntheticLambda0 statusBar$2$Callback$$ExternalSyntheticLambda0) {
    }

    void setWallpaperSupportsAmbientMode() {
    }

    void unregisterCallback(StatusBarWindowCallback statusBarWindowCallback) {
    }

    void batchApplyWindowLayoutParams(Runnable runnable) {
        runnable.run();
    }
}
