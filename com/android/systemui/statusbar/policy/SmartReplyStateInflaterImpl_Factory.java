package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.compatui.CompatUIController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SmartReplyStateInflaterImpl_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityManagerWrapperProvider;
    public final Provider constantsProvider;
    public final Provider devicePolicyManagerWrapperProvider;
    public final Provider packageManagerWrapperProvider;
    public final Provider smartActionsInflaterProvider;
    public final Provider smartRepliesInflaterProvider;

    public /* synthetic */ SmartReplyStateInflaterImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.constantsProvider = provider;
        this.activityManagerWrapperProvider = provider2;
        this.packageManagerWrapperProvider = provider3;
        this.devicePolicyManagerWrapperProvider = provider4;
        this.smartRepliesInflaterProvider = provider5;
        this.smartActionsInflaterProvider = provider6;
    }

    public static SmartReplyStateInflaterImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, SmartActionInflaterImpl_Factory smartActionInflaterImpl_Factory) {
        return new SmartReplyStateInflaterImpl_Factory(provider, provider2, provider3, provider4, provider5, smartActionInflaterImpl_Factory, 0);
    }

    public static SmartReplyStateInflaterImpl_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new SmartReplyStateInflaterImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SmartReplyStateInflaterImpl((SmartReplyConstants) this.constantsProvider.get(), (ActivityManagerWrapper) this.activityManagerWrapperProvider.get(), (PackageManagerWrapper) this.packageManagerWrapperProvider.get(), (DevicePolicyManagerWrapper) this.devicePolicyManagerWrapperProvider.get(), (SmartReplyInflater) this.smartRepliesInflaterProvider.get(), (SmartActionInflater) this.smartActionsInflaterProvider.get());
            default:
                return new CompatUIController((Context) this.constantsProvider.get(), (DisplayController) this.activityManagerWrapperProvider.get(), (DisplayInsetsController) this.packageManagerWrapperProvider.get(), (DisplayImeController) this.devicePolicyManagerWrapperProvider.get(), (SyncTransactionQueue) this.smartRepliesInflaterProvider.get(), (ShellExecutor) this.smartActionsInflaterProvider.get());
        }
    }
}
