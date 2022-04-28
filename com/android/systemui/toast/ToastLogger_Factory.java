package com.android.systemui.toast;

import android.content.ClipboardManager;
import android.content.Context;
import com.android.p012wm.shell.compatui.CompatUIController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class ToastLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bufferProvider;

    public /* synthetic */ ToastLogger_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.bufferProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ToastLogger((LogBuffer) this.bufferProvider.get());
            case 1:
                ClipboardManager clipboardManager = (ClipboardManager) ((Context) this.bufferProvider.get()).getSystemService(ClipboardManager.class);
                Objects.requireNonNull(clipboardManager, "Cannot return null from a non-@Nullable @Provides method");
                return clipboardManager;
            case 2:
                return new ShadeViewDifferLogger((LogBuffer) this.bufferProvider.get());
            default:
                CompatUIController compatUIController = (CompatUIController) this.bufferProvider.get();
                Objects.requireNonNull(compatUIController);
                Optional of = Optional.of(compatUIController.mImpl);
                Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
                return of;
        }
    }
}
