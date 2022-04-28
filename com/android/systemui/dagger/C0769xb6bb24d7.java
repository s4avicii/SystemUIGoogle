package com.android.systemui.dagger;

import com.android.keyguard.LiftToActivateListener_Factory;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dagger.DaggerGlobalRootComponent$SysUIComponentImpl$$ExternalSyntheticOutline1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0769xb6bb24d7 {
    /* renamed from: m */
    public static Provider m50m(Provider provider, int i) {
        return DoubleCheck.provider(new LiftToActivateListener_Factory(provider, i));
    }
}
