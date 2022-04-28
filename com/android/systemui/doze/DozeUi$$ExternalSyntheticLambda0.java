package com.android.systemui.doze;

import android.app.AlarmManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeUi$$ExternalSyntheticLambda0 implements AlarmManager.OnAlarmListener {
    public final /* synthetic */ DozeUi f$0;

    public /* synthetic */ DozeUi$$ExternalSyntheticLambda0(DozeUi dozeUi) {
        this.f$0 = dozeUi;
    }

    public final void onAlarm() {
        DozeUi dozeUi = this.f$0;
        Objects.requireNonNull(dozeUi);
        dozeUi.verifyLastTimeTick();
        dozeUi.mHost.dozeTimeTick();
        dozeUi.mHandler.post(dozeUi.mWakeLock.wrap(DozeUi$$ExternalSyntheticLambda2.INSTANCE));
        dozeUi.scheduleTimeTick();
    }
}
