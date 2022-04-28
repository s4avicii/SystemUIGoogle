package com.android.systemui.wmshell;

import android.animation.ValueAnimator;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.util.Log;
import com.android.p012wm.shell.TaskView;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTransitions;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.SidefpsController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda1(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubblesManager.C17525 r0 = (BubblesManager.C17525) this.f$0;
                Objects.requireNonNull(r0);
                NotificationEntry entry = BubblesManager.this.mCommonNotifCollection.getEntry((String) this.f$1);
                if (entry != null) {
                    BubblesManager.this.onUserChangedBubble(entry, false);
                    return;
                }
                return;
            case 1:
                AuthController.C06901 r02 = (AuthController.C06901) this.f$0;
                List<FingerprintSensorPropertiesInternal> list = (List) this.f$1;
                int i = AuthController.C06901.$r8$clinit;
                Objects.requireNonNull(r02);
                AuthController authController = AuthController.this;
                Objects.requireNonNull(authController);
                authController.mExecution.assertIsMainThread();
                Log.d("AuthController", "handleAllAuthenticatorsRegistered | sensors: " + Arrays.toString(list.toArray()));
                authController.mAllAuthenticatorsRegistered = true;
                authController.mFpProps = list;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (FingerprintSensorPropertiesInternal next : authController.mFpProps) {
                    if (next.isAnyUdfpsType()) {
                        arrayList.add(next);
                    }
                    if (next.isAnySidefpsType()) {
                        arrayList2.add(next);
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList = null;
                }
                authController.mUdfpsProps = arrayList;
                if (arrayList != null) {
                    authController.mUdfpsController = authController.mUdfpsControllerFactory.get();
                }
                if (arrayList2.isEmpty()) {
                    arrayList2 = null;
                }
                authController.mSidefpsProps = arrayList2;
                if (arrayList2 != null) {
                    SidefpsController sidefpsController = authController.mSidefpsControllerFactory.get();
                }
                Iterator it = authController.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((AuthController.Callback) it.next()).onAllAuthenticatorsRegistered();
                }
                authController.mFingerprintManager.registerFingerprintStateListener(authController.mFingerprintStateListener);
                return;
            case 2:
                DelayedWakeLock delayedWakeLock = (DelayedWakeLock) this.f$0;
                Objects.requireNonNull(delayedWakeLock);
                delayedWakeLock.mInner.release((String) this.f$1);
                return;
            case 3:
                ((Consumer) this.f$0).accept((TaskView) this.f$1);
                return;
            default:
                LegacySplitScreenTransitions legacySplitScreenTransitions = (LegacySplitScreenTransitions) this.f$0;
                Objects.requireNonNull(legacySplitScreenTransitions);
                legacySplitScreenTransitions.mAnimations.remove((ValueAnimator) this.f$1);
                legacySplitScreenTransitions.onFinish();
                return;
        }
    }
}
