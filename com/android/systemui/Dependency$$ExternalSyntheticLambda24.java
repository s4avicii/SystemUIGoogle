package com.android.systemui;

import com.android.systemui.Dependency;
import dagger.Lazy;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Dependency$$ExternalSyntheticLambda24 implements Dependency.LazyDependencyCreator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Lazy f$0;

    public final Object createDependency() {
        switch (this.$r8$classId) {
        }
        return this.f$0.get();
    }

    public /* synthetic */ Dependency$$ExternalSyntheticLambda24(Lazy lazy, int i) {
        this.$r8$classId = i;
        this.f$0 = lazy;
    }
}
