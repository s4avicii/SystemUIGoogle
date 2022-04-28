package com.android.systemui;

import android.content.ContentProvider;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import com.android.systemui.SystemUIAppComponentFactory;
import com.android.systemui.dagger.SysUIComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIAppComponentFactory$$ExternalSyntheticLambda0 implements SystemUIAppComponentFactory.ContextAvailableCallback {
    public final /* synthetic */ ContentProvider f$0;

    public /* synthetic */ SystemUIAppComponentFactory$$ExternalSyntheticLambda0(ContentProvider contentProvider) {
        this.f$0 = contentProvider;
    }

    public final void onContextAvailable(Context context) {
        ContentProvider contentProvider = this.f$0;
        int i = SystemUIAppComponentFactory.$r8$clinit;
        SystemUIFactory.createFromConfig(context, false);
        SystemUIFactory systemUIFactory = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory);
        SysUIComponent sysUIComponent = systemUIFactory.mSysUIComponent;
        try {
            sysUIComponent.getClass().getMethod("inject", new Class[]{contentProvider.getClass()}).invoke(sysUIComponent, new Object[]{contentProvider});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No injector for class: ");
            m.append(contentProvider.getClass());
            Log.w("AppComponentFactory", m.toString(), e);
        }
    }
}
