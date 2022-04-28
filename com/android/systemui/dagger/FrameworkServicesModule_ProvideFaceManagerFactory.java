package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.face.FaceManager;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class FrameworkServicesModule_ProvideFaceManagerFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ FrameworkServicesModule_ProvideFaceManagerFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return (FaceManager) ((Context) this.contextProvider.get()).getSystemService(FaceManager.class);
            case 1:
                SectionHeaderController headerController = ((SectionHeaderControllerSubcomponent) this.contextProvider.get()).getHeaderController();
                Objects.requireNonNull(headerController, "Cannot return null from a non-@Nullable @Provides method");
                return headerController;
            default:
                Optional ofNullable = Optional.ofNullable(((PhoneStatusBarView) this.contextProvider.get()).findViewById(C1777R.C1779id.operator_name_frame));
                Objects.requireNonNull(ofNullable, "Cannot return null from a non-@Nullable @Provides method");
                return ofNullable;
        }
    }
}
