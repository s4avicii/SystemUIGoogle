package com.android.systemui.dagger;

import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideSplitScreenFactory;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dagger.DaggerGlobalRootComponent$SysUIComponentImpl$KeyguardBouncerComponentImpl$$ExternalSyntheticOutline1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0773x82ed519e {
    /* renamed from: m */
    public static Provider m54m(Provider provider, int i) {
        return DoubleCheck.provider(new WMShellBaseModule_ProvideSplitScreenFactory(provider, i));
    }
}
