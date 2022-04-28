package com.android.systemui.p006qs;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.systemui.assist.uihints.FlingVelocityWrapper;
import com.google.android.systemui.assist.uihints.TouchInsideHandler;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSFgsManagerFooter_Factory */
public final class QSFgsManagerFooter_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider executorProvider;
    public final Provider fgsManagerControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider rootViewProvider;

    public /* synthetic */ QSFgsManagerFooter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.rootViewProvider = provider;
        this.mainExecutorProvider = provider2;
        this.executorProvider = provider3;
        this.fgsManagerControllerProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new QSFgsManagerFooter((View) this.rootViewProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.executorProvider.get(), (FgsManagerController) this.fgsManagerControllerProvider.get());
            case 1:
                return new StatusBarHideIconsForBouncerManager((CommandQueue) this.rootViewProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (StatusBarWindowStateController) this.executorProvider.get(), (DumpManager) this.fgsManagerControllerProvider.get());
            default:
                return new TranscriptionController((ViewGroup) this.rootViewProvider.get(), (TouchInsideHandler) this.mainExecutorProvider.get(), (FlingVelocityWrapper) this.executorProvider.get(), (ConfigurationController) this.fgsManagerControllerProvider.get());
        }
    }
}
