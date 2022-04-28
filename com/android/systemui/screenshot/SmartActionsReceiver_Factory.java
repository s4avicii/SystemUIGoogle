package com.android.systemui.screenshot;

import android.app.smartspace.SmartspaceManager;
import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureLogger;
import com.android.systemui.statusbar.notification.init.NotificationsControllerStub;
import com.android.systemui.statusbar.policy.SensorPrivacyControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class SmartActionsReceiver_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider screenshotSmartActionsProvider;

    public /* synthetic */ SmartActionsReceiver_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.screenshotSmartActionsProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SmartActionsReceiver((ScreenshotSmartActions) this.screenshotSmartActionsProvider.get());
            case 1:
                SmartspaceManager smartspaceManager = (SmartspaceManager) ((Context) this.screenshotSmartActionsProvider.get()).getSystemService(SmartspaceManager.class);
                Objects.requireNonNull(smartspaceManager, "Cannot return null from a non-@Nullable @Provides method");
                return smartspaceManager;
            case 2:
                return ((LogBufferFactory) this.screenshotSmartActionsProvider.get()).create("LSShadeTransitionLog", 50);
            case 3:
                return new SwipeStatusBarAwayGestureLogger((LogBuffer) this.screenshotSmartActionsProvider.get());
            case 4:
                return new NotificationsControllerStub((NotificationListener) this.screenshotSmartActionsProvider.get());
            case 5:
                StatusBarWindowView statusBarWindowView = (StatusBarWindowView) ((LayoutInflater) this.screenshotSmartActionsProvider.get()).inflate(C1777R.layout.super_status_bar, (ViewGroup) null);
                if (statusBarWindowView != null) {
                    return statusBarWindowView;
                }
                throw new IllegalStateException("R.layout.super_status_bar could not be properly inflated");
            case FalsingManager.VERSION:
                return new Handler((Looper) this.screenshotSmartActionsProvider.get());
            default:
                SensorPrivacyControllerImpl sensorPrivacyControllerImpl = new SensorPrivacyControllerImpl((SensorPrivacyManager) this.screenshotSmartActionsProvider.get());
                sensorPrivacyControllerImpl.mSensorPrivacyEnabled = sensorPrivacyControllerImpl.mSensorPrivacyManager.isAllSensorPrivacyEnabled();
                sensorPrivacyControllerImpl.mSensorPrivacyManager.addAllSensorPrivacyListener(sensorPrivacyControllerImpl);
                return sensorPrivacyControllerImpl;
        }
    }
}
