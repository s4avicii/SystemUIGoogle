package com.android.systemui.p006qs;

import androidx.fragment.R$styleable;
import com.android.systemui.p006qs.FgsManagerController;
import java.util.Comparator;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.FgsManagerController$updateAppItemsLocked$3$run$$inlined$sortedByDescending$1 */
/* compiled from: Comparisons.kt */
public final class C0978x3693e947<T> implements Comparator {
    public final int compare(T t, T t2) {
        FgsManagerController.RunningApp runningApp = (FgsManagerController.RunningApp) t2;
        Objects.requireNonNull(runningApp);
        Long valueOf = Long.valueOf(runningApp.timeStarted);
        FgsManagerController.RunningApp runningApp2 = (FgsManagerController.RunningApp) t;
        Objects.requireNonNull(runningApp2);
        return R$styleable.compareValues(valueOf, Long.valueOf(runningApp2.timeStarted));
    }
}
