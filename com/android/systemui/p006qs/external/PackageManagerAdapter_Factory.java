package com.android.systemui.p006qs.external;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.freeform.FreeformTaskListener;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.external.PackageManagerAdapter_Factory */
public final class PackageManagerAdapter_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ PackageManagerAdapter_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new PackageManagerAdapter((Context) this.contextProvider.get());
            case 1:
                AudioManager audioManager = (AudioManager) ((Context) this.contextProvider.get()).getSystemService(AudioManager.class);
                Objects.requireNonNull(audioManager, "Cannot return null from a non-@Nullable @Provides method");
                return audioManager;
            case 2:
                return new PrivacyDotDecorProviderFactory((Resources) this.contextProvider.get());
            default:
                return new FreeformTaskListener((SyncTransactionQueue) this.contextProvider.get());
        }
    }
}
