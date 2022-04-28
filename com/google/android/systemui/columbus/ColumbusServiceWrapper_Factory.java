package com.google.android.systemui.columbus;

import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.fullscreen.FullscreenTaskListener;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class ColumbusServiceWrapper_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider columbusServiceProvider;
    public final Provider columbusSettingsProvider;
    public final Provider columbusStructuredDataManagerProvider;
    public final Provider settingsActionProvider;

    public /* synthetic */ ColumbusServiceWrapper_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.columbusSettingsProvider = provider;
        this.columbusServiceProvider = provider2;
        this.settingsActionProvider = provider3;
        this.columbusStructuredDataManagerProvider = provider4;
    }

    public final Object get() {
        FullscreenTaskListener fullscreenTaskListener;
        switch (this.$r8$classId) {
            case 0:
                return new ColumbusServiceWrapper((ColumbusSettings) this.columbusSettingsProvider.get(), DoubleCheck.lazy(this.columbusServiceProvider), DoubleCheck.lazy(this.settingsActionProvider), DoubleCheck.lazy(this.columbusStructuredDataManagerProvider));
            default:
                Optional optional = (Optional) this.columbusSettingsProvider.get();
                SyncTransactionQueue syncTransactionQueue = (SyncTransactionQueue) this.columbusServiceProvider.get();
                Optional optional2 = (Optional) this.settingsActionProvider.get();
                Optional optional3 = (Optional) this.columbusStructuredDataManagerProvider.get();
                if (optional.isPresent()) {
                    fullscreenTaskListener = (FullscreenTaskListener) optional.get();
                } else {
                    fullscreenTaskListener = new FullscreenTaskListener(syncTransactionQueue, optional2, optional3);
                }
                Objects.requireNonNull(fullscreenTaskListener, "Cannot return null from a non-@Nullable @Provides method");
                return fullscreenTaskListener;
        }
    }
}
