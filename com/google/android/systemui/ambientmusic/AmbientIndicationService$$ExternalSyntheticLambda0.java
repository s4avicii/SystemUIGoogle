package com.google.android.systemui.ambientmusic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AmbientIndicationService$$ExternalSyntheticLambda0 implements AlarmManager.OnAlarmListener {
    public final /* synthetic */ AmbientIndicationService f$0;

    public /* synthetic */ AmbientIndicationService$$ExternalSyntheticLambda0(AmbientIndicationService ambientIndicationService) {
        this.f$0 = ambientIndicationService;
    }

    public final void onAlarm() {
        AmbientIndicationService ambientIndicationService = this.f$0;
        Objects.requireNonNull(ambientIndicationService);
        AmbientIndicationContainer ambientIndicationContainer = ambientIndicationService.mAmbientIndicationContainer;
        Objects.requireNonNull(ambientIndicationContainer);
        ambientIndicationContainer.setAmbientMusic((CharSequence) null, (PendingIntent) null, (PendingIntent) null, 0, false, (String) null);
    }
}
