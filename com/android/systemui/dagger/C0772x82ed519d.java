package com.android.systemui.dagger;

import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvideQSPanelFactory;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dagger.DaggerGlobalRootComponent$SysUIComponentImpl$KeyguardBouncerComponentImpl$$ExternalSyntheticOutline0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0772x82ed519d {
    /* renamed from: m */
    public static Provider m53m(Provider provider, int i) {
        return DoubleCheck.provider(new QSFragmentModule_ProvideQSPanelFactory(provider, i));
    }
}
