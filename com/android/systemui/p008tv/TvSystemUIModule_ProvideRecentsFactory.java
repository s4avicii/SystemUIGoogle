package com.android.systemui.p008tv;

import android.content.Context;
import com.android.systemui.communal.CommunalSourceMonitor_Factory;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.statusbar.CommandQueue;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.tv.TvSystemUIModule_ProvideRecentsFactory */
public final class TvSystemUIModule_ProvideRecentsFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider recentsImplementationProvider;

    public /* synthetic */ TvSystemUIModule_ProvideRecentsFactory(Provider provider, CommunalSourceMonitor_Factory communalSourceMonitor_Factory, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.recentsImplementationProvider = communalSourceMonitor_Factory;
        this.commandQueueProvider = provider2;
    }

    public final Recents get() {
        switch (this.$r8$classId) {
            case 0:
                return new Recents((Context) this.contextProvider.get(), (RecentsImplementation) this.recentsImplementationProvider.get(), (CommandQueue) this.commandQueueProvider.get());
            default:
                return new Recents((Context) this.contextProvider.get(), (RecentsImplementation) this.recentsImplementationProvider.get(), (CommandQueue) this.commandQueueProvider.get());
        }
    }
}
