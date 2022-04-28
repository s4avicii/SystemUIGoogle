package com.android.systemui.usb;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.notification.icon.IconBuilder;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class UsbDebuggingActivity_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider broadcastDispatcherProvider;

    public /* synthetic */ UsbDebuggingActivity_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.broadcastDispatcherProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new UsbDebuggingActivity((BroadcastDispatcher) this.broadcastDispatcherProvider.get());
            case 1:
                PackageManager packageManager = ((Context) this.broadcastDispatcherProvider.get()).getPackageManager();
                Objects.requireNonNull(packageManager, "Cannot return null from a non-@Nullable @Provides method");
                return packageManager;
            case 2:
                return new IconBuilder((Context) this.broadcastDispatcherProvider.get());
            case 3:
                return new UserInfoControllerImpl((Context) this.broadcastDispatcherProvider.get());
            case 4:
                Transitions transitions = (Transitions) this.broadcastDispatcherProvider.get();
                Objects.requireNonNull(transitions);
                Transitions.ShellTransitionImpl shellTransitionImpl = transitions.mImpl;
                Objects.requireNonNull(shellTransitionImpl, "Cannot return null from a non-@Nullable @Provides method");
                return shellTransitionImpl;
            default:
                ContentResolver contentResolver = ((Context) this.broadcastDispatcherProvider.get()).getContentResolver();
                Objects.requireNonNull(contentResolver, "Cannot return null from a non-@Nullable @Provides method");
                return contentResolver;
        }
    }
}
