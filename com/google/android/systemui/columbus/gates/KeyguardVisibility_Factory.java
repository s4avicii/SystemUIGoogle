package com.google.android.systemui.columbus.gates;

import android.content.Context;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.UnfoldTransitionModule$providesFoldStateLogger$1;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class KeyguardVisibility_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Object keyguardStateControllerProvider;

    public /* synthetic */ KeyguardVisibility_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.keyguardStateControllerProvider = provider2;
    }

    public KeyguardVisibility_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider) {
        this.$r8$classId = 3;
        this.keyguardStateControllerProvider = unfoldTransitionModule;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyguardVisibility((Context) this.contextProvider.get(), DoubleCheck.lazy((Provider) this.keyguardStateControllerProvider));
            case 1:
                return new MediaMuteAwaitConnectionCli((CommandRegistry) this.contextProvider.get(), (Context) ((Provider) this.keyguardStateControllerProvider).get());
            case 2:
                return new RootTaskDisplayAreaOrganizer((ShellExecutor) this.contextProvider.get(), (Context) ((Provider) this.keyguardStateControllerProvider).get());
            default:
                Objects.requireNonNull((UnfoldTransitionModule) this.keyguardStateControllerProvider);
                return ((Optional) this.contextProvider.get()).map(UnfoldTransitionModule$providesFoldStateLogger$1.INSTANCE);
        }
    }
}
