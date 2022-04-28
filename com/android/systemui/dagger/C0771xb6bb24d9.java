package com.android.systemui.dagger;

import com.android.systemui.screenshot.ImageTileSet_Factory;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dagger.DaggerGlobalRootComponent$SysUIComponentImpl$$ExternalSyntheticOutline3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0771xb6bb24d9 {
    /* renamed from: m */
    public static Provider m52m(Provider provider, int i) {
        return DoubleCheck.provider(new ImageTileSet_Factory(provider, i));
    }
}
