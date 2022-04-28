package com.google.android.systemui.assist.uihints;

import android.content.ContentResolver;
import android.content.Context;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class AssistantWarmer_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ AssistantWarmer_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new AssistantWarmer((Context) this.contextProvider.get());
            case 1:
                ContentResolver contentResolver = ((Context) this.contextProvider.get()).getContentResolver();
                Objects.requireNonNull(contentResolver, "Cannot return null from a non-@Nullable @Provides method");
                return contentResolver;
            case 2:
                NodeController nodeController = ((SectionHeaderControllerSubcomponent) this.contextProvider.get()).getNodeController();
                Objects.requireNonNull(nodeController, "Cannot return null from a non-@Nullable @Provides method");
                return nodeController;
            case 3:
                return new PipAnimationController((PipSurfaceTransactionHelper) this.contextProvider.get());
            default:
                return new TakeScreenshotHandler((Context) this.contextProvider.get());
        }
    }
}
