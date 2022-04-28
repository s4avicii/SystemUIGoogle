package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.privacy.OngoingPrivacyChip;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: StatusEvent.kt */
public final class PrivacyEvent$viewCreator$1 extends Lambda implements Function1<Context, OngoingPrivacyChip> {
    public final /* synthetic */ PrivacyEvent this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PrivacyEvent$viewCreator$1(PrivacyEvent privacyEvent) {
        super(1);
        this.this$0 = privacyEvent;
    }

    public final Object invoke(Object obj) {
        View inflate = LayoutInflater.from((Context) obj).inflate(C1777R.layout.ongoing_privacy_chip, (ViewGroup) null);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type com.android.systemui.privacy.OngoingPrivacyChip");
        OngoingPrivacyChip ongoingPrivacyChip = (OngoingPrivacyChip) inflate;
        PrivacyEvent privacyEvent = this.this$0;
        Objects.requireNonNull(privacyEvent);
        ongoingPrivacyChip.setPrivacyList(privacyEvent.privacyItems);
        PrivacyEvent privacyEvent2 = this.this$0;
        Objects.requireNonNull(privacyEvent2);
        ongoingPrivacyChip.setContentDescription(privacyEvent2.contentDescription);
        this.this$0.privacyChip = ongoingPrivacyChip;
        return ongoingPrivacyChip;
    }
}
