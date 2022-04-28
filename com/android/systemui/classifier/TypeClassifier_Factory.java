package com.android.systemui.classifier;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.google.android.systemui.assist.uihints.AssistUIView;
import com.google.android.systemui.assist.uihints.OverlayUiHost;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class TypeClassifier_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dataProvider;

    public /* synthetic */ TypeClassifier_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.dataProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new TypeClassifier((FalsingDataProvider) this.dataProvider.get());
            case 1:
                return ((ThreadFactory) this.dataProvider.get()).buildExecutorOnNewThread("biometrics");
            case 2:
                return Boolean.valueOf(((PackageManager) this.dataProvider.get()).hasSystemFeature("android.software.controls"));
            case 3:
                ConnectivityManager connectivityManager = (ConnectivityManager) ((Context) this.dataProvider.get()).getSystemService(ConnectivityManager.class);
                Objects.requireNonNull(connectivityManager, "Cannot return null from a non-@Nullable @Provides method");
                return connectivityManager;
            case 4:
                return new PreparationCoordinatorLogger((LogBuffer) this.dataProvider.get());
            case 5:
                SectionHeaderController headerController = ((SectionHeaderControllerSubcomponent) this.dataProvider.get()).getHeaderController();
                Objects.requireNonNull(headerController, "Cannot return null from a non-@Nullable @Provides method");
                return headerController;
            default:
                OverlayUiHost overlayUiHost = (OverlayUiHost) this.dataProvider.get();
                Objects.requireNonNull(overlayUiHost);
                AssistUIView assistUIView = overlayUiHost.mRoot;
                Objects.requireNonNull(assistUIView, "Cannot return null from a non-@Nullable @Provides method");
                return assistUIView;
        }
    }
}
