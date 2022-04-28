package com.android.systemui.p006qs.external;

import android.content.ComponentName;
import com.android.internal.logging.InstanceId;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1 */
/* compiled from: TileServiceRequestController.kt */
public final class TileServiceRequestController$requestTileAdd$dialogResponse$1<T> implements Consumer {
    public final /* synthetic */ Consumer<Integer> $callback;
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ InstanceId $instanceId;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ TileServiceRequestController this$0;

    public TileServiceRequestController$requestTileAdd$dialogResponse$1(TileServiceRequestController tileServiceRequestController, ComponentName componentName, String str, InstanceId instanceId, Consumer<Integer> consumer) {
        this.this$0 = tileServiceRequestController;
        this.$componentName = componentName;
        this.$packageName = str;
        this.$instanceId = instanceId;
        this.$callback = consumer;
    }

    public final void accept(Object obj) {
        TileRequestDialogEvent tileRequestDialogEvent;
        int intValue = ((Number) obj).intValue();
        if (intValue == 2) {
            TileServiceRequestController tileServiceRequestController = this.this$0;
            ComponentName componentName = this.$componentName;
            Objects.requireNonNull(tileServiceRequestController);
            tileServiceRequestController.qsTileHost.addTile(componentName, true);
        }
        TileServiceRequestController tileServiceRequestController2 = this.this$0;
        tileServiceRequestController2.dialogCanceller = null;
        TileRequestDialogEventLogger tileRequestDialogEventLogger = tileServiceRequestController2.eventLogger;
        String str = this.$packageName;
        InstanceId instanceId = this.$instanceId;
        if (intValue == 0) {
            tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_NOT_ADDED;
        } else if (intValue == 2) {
            tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ADDED;
        } else if (intValue == 3) {
            tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_DISMISSED;
        } else {
            Objects.requireNonNull(tileRequestDialogEventLogger);
            throw new IllegalArgumentException(Intrinsics.stringPlus("User response not valid: ", Integer.valueOf(intValue)));
        }
        tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(tileRequestDialogEvent, 0, str, instanceId);
        this.$callback.accept(Integer.valueOf(intValue));
    }
}
