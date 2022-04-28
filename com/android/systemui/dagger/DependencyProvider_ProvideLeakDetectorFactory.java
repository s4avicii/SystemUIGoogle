package com.android.systemui.dagger;

import android.view.LayoutInflater;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.dagger.LogModule_ProvidePrivacyLogBufferFactory;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.util.leak.LeakDetector;
import com.android.systemui.util.leak.TrackedCollections;
import com.android.systemui.util.leak.TrackedGarbage;
import com.android.systemui.util.leak.TrackedObjects;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DependencyProvider_ProvideLeakDetectorFactory implements Factory {
    public final /* synthetic */ int $r8$classId = 0;
    public final Provider dumpManagerProvider;
    public final Object module;

    public DependencyProvider_ProvideLeakDetectorFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.module = dependencyProvider;
        this.dumpManagerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                DumpManager dumpManager = (DumpManager) this.dumpManagerProvider.get();
                Objects.requireNonNull((DependencyProvider) this.module);
                if (!LeakDetector.ENABLED) {
                    return new LeakDetector((TrackedCollections) null, (TrackedGarbage) null, (TrackedObjects) null, dumpManager);
                }
                TrackedCollections trackedCollections = new TrackedCollections();
                return new LeakDetector(trackedCollections, new TrackedGarbage(trackedCollections), new TrackedObjects(trackedCollections), dumpManager);
            default:
                View inflate = ((LayoutInflater) this.dumpManagerProvider.get()).inflate(C1777R.layout.fgs_footer, (QSPanel) ((Provider) this.module).get(), false);
                Objects.requireNonNull(inflate, "Cannot return null from a non-@Nullable @Provides method");
                return inflate;
        }
    }

    public DependencyProvider_ProvideLeakDetectorFactory(LogModule_ProvidePrivacyLogBufferFactory logModule_ProvidePrivacyLogBufferFactory, Provider provider) {
        this.dumpManagerProvider = logModule_ProvidePrivacyLogBufferFactory;
        this.module = provider;
    }
}
