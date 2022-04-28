package com.android.systemui.statusbar.policy;

import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.functions.Function0;

/* renamed from: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$sam$com_android_systemui_plugins_ActivityStarter_OnDismissAction$0 */
/* compiled from: SmartReplyStateInflater.kt */
public final class C1645xc0c4f386 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ Function0 function;

    public C1645xc0c4f386(Function0 function0) {
        this.function = function0;
    }

    public final /* synthetic */ boolean onDismiss() {
        return ((Boolean) this.function.invoke()).booleanValue();
    }
}
