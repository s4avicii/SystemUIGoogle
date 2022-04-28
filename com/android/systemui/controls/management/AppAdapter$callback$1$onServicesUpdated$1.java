package com.android.systemui.controls.management;

import com.android.systemui.controls.ControlsServiceInfo;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: AppAdapter.kt */
public final class AppAdapter$callback$1$onServicesUpdated$1 implements Runnable {
    public final /* synthetic */ List<ControlsServiceInfo> $serviceInfos;
    public final /* synthetic */ Executor $uiExecutor;
    public final /* synthetic */ AppAdapter this$0;

    public AppAdapter$callback$1$onServicesUpdated$1(AppAdapter appAdapter, ArrayList arrayList, Executor executor) {
        this.this$0 = appAdapter;
        this.$serviceInfos = arrayList;
        this.$uiExecutor = executor;
    }

    public final void run() {
        C0739xf045535a appAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1 = new C0739xf045535a(Collator.getInstance(this.this$0.resources.getConfiguration().getLocales().get(0)));
        this.this$0.listOfServices = CollectionsKt___CollectionsKt.sortedWith(this.$serviceInfos, appAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1);
        Executor executor = this.$uiExecutor;
        final AppAdapter appAdapter = this.this$0;
        executor.execute(new Runnable() {
            public final void run() {
                AppAdapter.this.notifyDataSetChanged();
            }
        });
    }
}
