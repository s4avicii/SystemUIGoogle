package com.android.systemui.dagger;

import com.android.systemui.media.RingtonePlayer_Factory;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dagger.DaggerGlobalRootComponent$SysUIComponentImpl$$ExternalSyntheticOutline2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0770xb6bb24d8 {
    /* renamed from: m */
    public static Provider m51m(Provider provider, int i) {
        return DoubleCheck.provider(new RingtonePlayer_Factory(provider, i));
    }
}
