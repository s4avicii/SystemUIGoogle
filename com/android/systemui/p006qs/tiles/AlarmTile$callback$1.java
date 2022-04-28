package com.android.systemui.p006qs.tiles;

import android.app.AlarmManager;
import com.android.systemui.statusbar.policy.NextAlarmController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.AlarmTile$callback$1 */
/* compiled from: AlarmTile.kt */
public final class AlarmTile$callback$1 implements NextAlarmController.NextAlarmChangeCallback {
    public final /* synthetic */ AlarmTile this$0;

    public AlarmTile$callback$1(AlarmTile alarmTile) {
        this.this$0 = alarmTile;
    }

    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        AlarmTile alarmTile = this.this$0;
        alarmTile.lastAlarmInfo = alarmClockInfo;
        Objects.requireNonNull(alarmTile);
        alarmTile.refreshState((Object) null);
    }
}
