package com.android.systemui.classifier;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import com.android.p012wm.shell.RootDisplayAreaOrganizer;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.displayareahelper.DisplayAreaHelperController;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import com.google.android.systemui.columbus.feedback.HapticClick;
import com.google.android.systemui.columbus.feedback.UserActivity;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Provider;
import kotlin.collections.SetsKt__SetsKt;

public final class SingleTapClassifier_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dataProvider;
    public final Provider touchSlopProvider;

    public /* synthetic */ SingleTapClassifier_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.dataProvider = provider;
        this.touchSlopProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SingleTapClassifier((FalsingDataProvider) this.dataProvider.get(), ((Float) this.touchSlopProvider.get()).floatValue());
            case 1:
                Optional of = Optional.of(new DisplayAreaHelperController((ShellExecutor) this.dataProvider.get(), (RootDisplayAreaOrganizer) this.touchSlopProvider.get()));
                Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
                return of;
            case 2:
                return new AssistantPresenceHandler((Context) this.dataProvider.get(), (AssistUtils) this.touchSlopProvider.get());
            default:
                Set of2 = SetsKt__SetsKt.setOf((HapticClick) this.dataProvider.get(), (UserActivity) this.touchSlopProvider.get());
                Objects.requireNonNull(of2, "Cannot return null from a non-@Nullable @Provides method");
                return of2;
        }
    }
}
