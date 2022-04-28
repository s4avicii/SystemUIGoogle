package com.android.systemui.communal;

import android.content.Context;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.ActivityStarterDelegate;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.media.LocalMediaManagerFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.util.condition.Monitor;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class CommunalSourceMonitor_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider communalConditionsMonitorProvider;
    public final Provider executorProvider;

    public /* synthetic */ CommunalSourceMonitor_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.executorProvider = provider;
        this.communalConditionsMonitorProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new CommunalSourceMonitor((Executor) this.executorProvider.get(), (Monitor) this.communalConditionsMonitorProvider.get());
            case 1:
                ActivityStarterDelegate activityStarterDelegate = (ActivityStarterDelegate) this.executorProvider.get();
                ((PluginDependencyProvider) this.communalConditionsMonitorProvider.get()).allowPluginDependency(ActivityStarter.class, activityStarterDelegate);
                Objects.requireNonNull(activityStarterDelegate, "Cannot return null from a non-@Nullable @Provides method");
                return activityStarterDelegate;
            case 2:
                return new LocalMediaManagerFactory((Context) this.executorProvider.get(), (LocalBluetoothManager) this.communalConditionsMonitorProvider.get());
            default:
                Context context = (Context) this.executorProvider.get();
                ContextComponentHelper contextComponentHelper = (ContextComponentHelper) this.communalConditionsMonitorProvider.get();
                String string = context.getString(C1777R.string.config_recentsComponent);
                if (string == null || string.length() == 0) {
                    throw new RuntimeException("No recents component configured", (Throwable) null);
                }
                RecentsImplementation resolveRecents = contextComponentHelper.resolveRecents(string);
                if (resolveRecents == null) {
                    try {
                        try {
                            resolveRecents = (RecentsImplementation) context.getClassLoader().loadClass(string).newInstance();
                        } catch (Throwable th) {
                            throw new RuntimeException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("Error creating recents component: ", string), th);
                        }
                    } catch (Throwable th2) {
                        throw new RuntimeException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("Error loading recents component: ", string), th2);
                    }
                }
                Objects.requireNonNull(resolveRecents, "Cannot return null from a non-@Nullable @Provides method");
                return resolveRecents;
        }
    }
}
