package com.android.p012wm.shell.pip.phone;

import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Range;
import android.view.View;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda9;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.google.android.systemui.elmyra.sensors.config.Adjustment;
import com.google.android.systemui.elmyra.sensors.config.GestureConfiguration;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipMotionHelper$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipMotionHelper$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        Boolean bool = null;
        switch (this.$r8$classId) {
            case 0:
                PipMotionHelper pipMotionHelper = (PipMotionHelper) this.f$0;
                Rect rect = (Rect) obj;
                Objects.requireNonNull(pipMotionHelper);
                if (!pipMotionHelper.mPipBoundsState.getBounds().equals(rect)) {
                    pipMotionHelper.mMenuController.updateMenuLayout();
                    pipMotionHelper.mPipBoundsState.setBounds(rect);
                    return;
                }
                return;
            case 1:
                NavigationBarView navigationBarView = (NavigationBarView) this.f$0;
                int i = NavigationBarView.$r8$clinit;
                Objects.requireNonNull(navigationBarView);
                navigationBarView.post(new NavigationBarView$$ExternalSyntheticLambda1(navigationBarView, (Rect) obj));
                return;
            case 2:
                ((StatusBar) obj).wakeUpIfDozing(SystemClock.uptimeMillis(), (View) this.f$0, "NOTIFICATION_CLICK");
                return;
            case 3:
                BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.f$0;
                UiEventLoggerImpl uiEventLoggerImpl = BiometricUnlockController.UI_EVENT_LOGGER;
                Objects.requireNonNull(biometricUnlockController);
                UiEventLoggerImpl uiEventLoggerImpl2 = BiometricUnlockController.UI_EVENT_LOGGER;
                SessionTracker sessionTracker = biometricUnlockController.mSessionTracker;
                Objects.requireNonNull(sessionTracker);
                uiEventLoggerImpl2.log((BiometricUnlockController.BiometricUiEvent) obj, (InstanceId) sessionTracker.mSessionToInstanceId.getOrDefault(1, (Object) null));
                return;
            case 4:
                ThresholdSensorEvent thresholdSensorEvent = (ThresholdSensorEvent) this.f$0;
                Consumer consumer = (Consumer) obj;
                if (thresholdSensorEvent != null) {
                    bool = Boolean.valueOf(thresholdSensorEvent.mBelow);
                }
                consumer.accept(bool);
                return;
            case 5:
                RecentTasksController.IRecentTasksImpl iRecentTasksImpl = (RecentTasksController.IRecentTasksImpl) this.f$0;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                int i2 = RecentTasksController.IRecentTasksImpl.$r8$clinit;
                Objects.requireNonNull(iRecentTasksImpl);
                RecentTasksController.IRecentTasksImpl.C19171 r3 = iRecentTasksImpl.mRecentTasksListener;
                Objects.requireNonNull(recentTasksController);
                recentTasksController.mCallbacks.remove(r3);
                return;
            default:
                GestureConfiguration gestureConfiguration = (GestureConfiguration) this.f$0;
                Adjustment adjustment = (Adjustment) obj;
                Range<Float> range = GestureConfiguration.SENSITIVITY_RANGE;
                Objects.requireNonNull(gestureConfiguration);
                BubbleController$$ExternalSyntheticLambda9 bubbleController$$ExternalSyntheticLambda9 = gestureConfiguration.mAdjustmentCallback;
                Objects.requireNonNull(adjustment);
                adjustment.mCallback = bubbleController$$ExternalSyntheticLambda9;
                return;
        }
    }
}
