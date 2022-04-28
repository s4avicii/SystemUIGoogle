package com.android.systemui.p006qs;

import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.FgsManagerController;
import java.util.Objects;
import kotlin.jvm.internal.Ref$ObjectRef;

/* renamed from: com.android.systemui.qs.FgsManagerController$AppListAdapter$onBindViewHolder$2$1 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$AppListAdapter$onBindViewHolder$2$1 implements View.OnClickListener {
    public final /* synthetic */ Ref$ObjectRef<FgsManagerController.RunningApp> $runningApp;
    public final /* synthetic */ FgsManagerController.AppItemViewHolder $this_with;
    public final /* synthetic */ FgsManagerController this$0;

    public FgsManagerController$AppListAdapter$onBindViewHolder$2$1(FgsManagerController.AppItemViewHolder appItemViewHolder, FgsManagerController fgsManagerController, Ref$ObjectRef<FgsManagerController.RunningApp> ref$ObjectRef) {
        this.$this_with = appItemViewHolder;
        this.this$0 = fgsManagerController;
        this.$runningApp = ref$ObjectRef;
    }

    public final void onClick(View view) {
        FgsManagerController.AppItemViewHolder appItemViewHolder = this.$this_with;
        Objects.requireNonNull(appItemViewHolder);
        appItemViewHolder.stopButton.setText(C1777R.string.fgs_manager_app_item_stop_button_stopped_label);
        FgsManagerController fgsManagerController = this.this$0;
        FgsManagerController.RunningApp runningApp = (FgsManagerController.RunningApp) this.$runningApp.element;
        Objects.requireNonNull(runningApp);
        int i = runningApp.userId;
        FgsManagerController.RunningApp runningApp2 = (FgsManagerController.RunningApp) this.$runningApp.element;
        Objects.requireNonNull(runningApp2);
        String str = runningApp2.packageName;
        int i2 = FgsManagerController.$r8$clinit;
        Objects.requireNonNull(fgsManagerController);
        fgsManagerController.activityManager.stopAppForUser(str, i);
    }
}
