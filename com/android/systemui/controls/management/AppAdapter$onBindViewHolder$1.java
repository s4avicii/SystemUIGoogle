package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.view.View;
import com.android.systemui.controls.ControlsServiceInfo;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: AppAdapter.kt */
public final class AppAdapter$onBindViewHolder$1 implements View.OnClickListener {
    public final /* synthetic */ int $index;
    public final /* synthetic */ AppAdapter this$0;

    public AppAdapter$onBindViewHolder$1(AppAdapter appAdapter, int i) {
        this.this$0 = appAdapter;
        this.$index = i;
    }

    public final void onClick(View view) {
        String str;
        AppAdapter appAdapter = this.this$0;
        Function1<ComponentName, Unit> function1 = appAdapter.onAppSelected;
        ControlsServiceInfo controlsServiceInfo = appAdapter.listOfServices.get(this.$index);
        Objects.requireNonNull(controlsServiceInfo);
        ComponentName componentName = controlsServiceInfo.componentName;
        if (componentName != null) {
            str = componentName.flattenToString();
        } else {
            str = null;
        }
        function1.invoke(ComponentName.unflattenFromString(str));
    }
}
