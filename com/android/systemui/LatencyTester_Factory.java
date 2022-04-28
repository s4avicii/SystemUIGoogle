package com.android.systemui;

import android.content.Context;
import android.view.IWindowManager;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.keyguard.KeyguardLifecyclesDispatcher;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.television.TvOngoingPrivacyChip;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.sensors.AsyncSensorManager;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class LatencyTester_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider biometricUnlockControllerProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;

    public /* synthetic */ LatencyTester_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.biometricUnlockControllerProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new LatencyTester((Context) this.contextProvider.get(), (BiometricUnlockController) this.biometricUnlockControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
            case 1:
                AsyncSensorManager asyncSensorManager = (AsyncSensorManager) this.contextProvider.get();
                Context context = (Context) this.biometricUnlockControllerProvider.get();
                DozeParameters dozeParameters = (DozeParameters) this.broadcastDispatcherProvider.get();
                Objects.requireNonNull(dozeParameters);
                String[] stringArray = dozeParameters.mResources.getStringArray(C1777R.array.doze_brightness_sensor_name_posture_mapping);
                if (stringArray.length != 0) {
                    Optional[] optionalArr = new Optional[5];
                    Arrays.fill(optionalArr, Optional.empty());
                    HashMap hashMap = new HashMap();
                    for (int i = 0; i < stringArray.length; i++) {
                        String str = stringArray[i];
                        if (!hashMap.containsKey(str)) {
                            hashMap.put(str, Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(C1777R.string.doze_brightness_sensor_type), stringArray[i])));
                        }
                        optionalArr[i] = (Optional) hashMap.get(str);
                    }
                    return optionalArr;
                }
                return new Optional[]{Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(C1777R.string.doze_brightness_sensor_type), (String) null))};
            case 2:
                return new KeyguardService((KeyguardViewMediator) this.contextProvider.get(), (KeyguardLifecyclesDispatcher) this.biometricUnlockControllerProvider.get(), (ShellTransitions) this.broadcastDispatcherProvider.get());
            default:
                return new TvOngoingPrivacyChip((Context) this.contextProvider.get(), (PrivacyItemController) this.biometricUnlockControllerProvider.get(), (IWindowManager) this.broadcastDispatcherProvider.get());
        }
    }
}
