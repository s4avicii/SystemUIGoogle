package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.Clock;
import java.util.Locale;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Clock$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Clock.C16052 f$0;
    public final /* synthetic */ Locale f$1;

    public /* synthetic */ Clock$2$$ExternalSyntheticLambda0(Clock.C16052 r1, Locale locale) {
        this.f$0 = r1;
        this.f$1 = locale;
    }

    public final void run() {
        Clock.C16052 r0 = this.f$0;
        Locale locale = this.f$1;
        Objects.requireNonNull(r0);
        if (!locale.equals(Clock.this.mLocale)) {
            Clock clock = Clock.this;
            clock.mLocale = locale;
            clock.mClockFormatString = "";
        }
    }
}
