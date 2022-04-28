package com.android.systemui.statusbar.p007tv;

import android.content.Context;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.tv.TvStatusBar_Factory */
public final class TvStatusBar_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider assistManagerLazyProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;

    public /* synthetic */ TvStatusBar_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.assistManagerLazyProvider = provider3;
    }

    public final Object get() {
        Object obj;
        switch (this.$r8$classId) {
            case 0:
                return new TvStatusBar((Context) this.contextProvider.get(), (CommandQueue) this.commandQueueProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider));
            default:
                Lazy lazy = DoubleCheck.lazy(this.commandQueueProvider);
                Lazy lazy2 = DoubleCheck.lazy(this.assistManagerLazyProvider);
                if (((NotifPipelineFlags) this.contextProvider.get()).isNewPipelineEnabled()) {
                    obj = new GroupExpansionManagerImpl((GroupMembershipManager) lazy.get());
                } else {
                    obj = (GroupExpansionManager) lazy2.get();
                }
                Objects.requireNonNull(obj, "Cannot return null from a non-@Nullable @Provides method");
                return obj;
        }
    }
}
