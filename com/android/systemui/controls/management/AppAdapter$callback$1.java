package com.android.systemui.controls.management;

import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: AppAdapter.kt */
public final class AppAdapter$callback$1 implements ControlsListingController.ControlsListingCallback {
    public final /* synthetic */ Executor $backgroundExecutor;
    public final /* synthetic */ Executor $uiExecutor;
    public final /* synthetic */ AppAdapter this$0;

    public AppAdapter$callback$1(Executor executor, AppAdapter appAdapter, Executor executor2) {
        this.$backgroundExecutor = executor;
        this.this$0 = appAdapter;
        this.$uiExecutor = executor2;
    }

    public final void onServicesUpdated(ArrayList arrayList) {
        this.$backgroundExecutor.execute(new AppAdapter$callback$1$onServicesUpdated$1(this.this$0, arrayList, this.$uiExecutor));
    }
}
