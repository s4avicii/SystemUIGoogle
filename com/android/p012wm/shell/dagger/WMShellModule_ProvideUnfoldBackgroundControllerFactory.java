package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.provider.Settings;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.unfold.UnfoldBackgroundController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvideUnfoldBackgroundControllerFactory */
public final class WMShellModule_ProvideUnfoldBackgroundControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;

    public /* synthetic */ WMShellModule_ProvideUnfoldBackgroundControllerFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.rootTaskDisplayAreaOrganizerProvider = provider;
        this.contextProvider = provider2;
    }

    public final Object get() {
        int i;
        Object obj;
        switch (this.$r8$classId) {
            case 0:
                return new UnfoldBackgroundController((Context) this.contextProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get());
            case 1:
                Lazy lazy = DoubleCheck.lazy(this.contextProvider);
                if (((NotifPipelineFlags) this.rootTaskDisplayAreaOrganizerProvider.get()).isNewPipelineEnabled()) {
                    obj = new GroupMembershipManagerImpl();
                } else {
                    obj = (GroupMembershipManager) lazy.get();
                }
                Objects.requireNonNull(obj, "Cannot return null from a non-@Nullable @Provides method");
                return obj;
            default:
                try {
                    i = ((SecureSettings) this.rootTaskDisplayAreaOrganizerProvider.get()).getIntForUser(((Integer) this.contextProvider.get()).intValue());
                } catch (Settings.SettingNotFoundException unused) {
                    i = 0;
                }
                return Integer.valueOf(i);
        }
    }
}
