package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.p006qs.QSFooterView;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class SecureSettingsImpl_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object contentResolverProvider;

    public /* synthetic */ SecureSettingsImpl_Factory(Object obj, int i) {
        this.$r8$classId = i;
        this.contentResolverProvider = obj;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SecureSettingsImpl((ContentResolver) ((Provider) this.contentResolverProvider).get());
            case 1:
                return new LifecycleScreenStatusProvider((ScreenLifecycle) ((Provider) this.contentResolverProvider).get());
            case 2:
                QSFooterView qSFooterView = (QSFooterView) ((View) ((Provider) this.contentResolverProvider).get()).findViewById(C1777R.C1779id.qs_footer);
                Objects.requireNonNull(qSFooterView, "Cannot return null from a non-@Nullable @Provides method");
                return qSFooterView;
            case 3:
                return new ShadeListBuilderLogger((LogBuffer) ((Provider) this.contentResolverProvider).get());
            case 4:
                return new WindowManagerShellWrapper((ShellExecutor) ((Provider) this.contentResolverProvider).get());
            default:
                Objects.requireNonNull((DependencyProvider) this.contentResolverProvider);
                return new MetricsLogger();
        }
    }
}
