package com.android.systemui.controls;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.internal.logging.InstanceIdSequence;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.p004ui.ControlViewHolder;

/* compiled from: ControlsMetricsLoggerImpl.kt */
public final class ControlsMetricsLoggerImpl implements ControlsMetricsLogger {
    public int instanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(8192);

    public final void assignInstanceId() {
        this.instanceId = this.instanceIdSequence.newInstanceId().getId();
    }

    public final void drag(ControlViewHolder controlViewHolder, boolean z) {
        log(ControlsMetricsLogger.ControlsEvents.CONTROL_DRAG.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, z);
    }

    public final void log(int i, int i2, int i3, boolean z) {
        int i4 = this.instanceId;
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(349);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i4);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeBoolean(z);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public final void longPress(ControlViewHolder controlViewHolder, boolean z) {
        log(ControlsMetricsLogger.ControlsEvents.CONTROL_LONG_PRESS.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, z);
    }

    public final void refreshEnd(ControlViewHolder controlViewHolder, boolean z) {
        log(ControlsMetricsLogger.ControlsEvents.CONTROL_REFRESH_END.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, z);
    }

    public final void touch(ControlViewHolder controlViewHolder, boolean z) {
        log(ControlsMetricsLogger.ControlsEvents.CONTROL_TOUCH.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, z);
    }

    public final void refreshBegin(int i, boolean z) {
        assignInstanceId();
        log(ControlsMetricsLogger.ControlsEvents.CONTROL_REFRESH_BEGIN.getId(), 0, i, z);
    }
}
