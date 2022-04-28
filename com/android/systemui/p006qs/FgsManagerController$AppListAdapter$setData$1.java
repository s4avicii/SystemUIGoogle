package com.android.systemui.p006qs;

import androidx.recyclerview.widget.DiffUtil;
import com.android.systemui.p006qs.FgsManagerController;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* renamed from: com.android.systemui.qs.FgsManagerController$AppListAdapter$setData$1 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$AppListAdapter$setData$1 extends DiffUtil.Callback {
    public final /* synthetic */ List<FgsManagerController.RunningApp> $newData;
    public final /* synthetic */ Ref$ObjectRef<List<FgsManagerController.RunningApp>> $oldData;

    public FgsManagerController$AppListAdapter$setData$1(Ref$ObjectRef<List<FgsManagerController.RunningApp>> ref$ObjectRef, List<FgsManagerController.RunningApp> list) {
        this.$oldData = ref$ObjectRef;
        this.$newData = list;
    }

    public final boolean areContentsTheSame(int i, int i2) {
        FgsManagerController.RunningApp runningApp = (FgsManagerController.RunningApp) ((List) this.$oldData.element).get(i);
        Objects.requireNonNull(runningApp);
        boolean z = runningApp.stopped;
        FgsManagerController.RunningApp runningApp2 = this.$newData.get(i2);
        Objects.requireNonNull(runningApp2);
        if (z == runningApp2.stopped) {
            return true;
        }
        return false;
    }

    public final boolean areItemsTheSame(int i, int i2) {
        return Intrinsics.areEqual(((List) this.$oldData.element).get(i), this.$newData.get(i2));
    }

    public final int getNewListSize() {
        return this.$newData.size();
    }

    public final int getOldListSize() {
        return ((List) this.$oldData.element).size();
    }
}
