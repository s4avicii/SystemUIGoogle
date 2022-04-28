package com.android.systemui.statusbar;

import android.content.res.Resources;
import android.view.CrossWindowBlurListeners;
import com.android.systemui.dump.DumpManager;
import com.google.android.systemui.assist.uihints.GlowController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.ScrimController;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import javax.inject.Provider;

public final class BlurUtils_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider crossWindowBlurListenersProvider;
    public final Provider dumpManagerProvider;
    public final Provider resourcesProvider;

    public /* synthetic */ BlurUtils_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.resourcesProvider = provider;
        this.crossWindowBlurListenersProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new BlurUtils((Resources) this.resourcesProvider.get(), (CrossWindowBlurListeners) this.crossWindowBlurListenersProvider.get(), (DumpManager) this.dumpManagerProvider.get());
            default:
                return new HashSet(Arrays.asList(new NgaMessageHandler.CardInfoListener[]{(GlowController) this.resourcesProvider.get(), (ScrimController) this.crossWindowBlurListenersProvider.get(), (TranscriptionController) this.dumpManagerProvider.get()}));
        }
    }
}
