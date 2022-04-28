package com.android.systemui.statusbar.phone;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: StatusBarContentInsetsProvider.kt */
public final class StatusBarContentInsetsProvider$isPrivacyDotEnabled$2 extends Lambda implements Function0<Boolean> {
    public final /* synthetic */ StatusBarContentInsetsProvider this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StatusBarContentInsetsProvider$isPrivacyDotEnabled$2(StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        super(0);
        this.this$0 = statusBarContentInsetsProvider;
    }

    public final Object invoke() {
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = this.this$0;
        Objects.requireNonNull(statusBarContentInsetsProvider);
        return Boolean.valueOf(statusBarContentInsetsProvider.context.getResources().getBoolean(C1777R.bool.config_enablePrivacyDot));
    }
}
