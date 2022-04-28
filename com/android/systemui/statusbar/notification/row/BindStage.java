package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

public abstract class BindStage<Params> extends BindRequester {
    public ArrayMap mContentParams = new ArrayMap();

    public interface StageCallback {
    }

    public final Params getStageParams(NotificationEntry notificationEntry) {
        Params params = this.mContentParams.get(notificationEntry);
        if (params != null) {
            return params;
        }
        Objects.requireNonNull(notificationEntry);
        Log.wtf("BindStage", String.format("Entry does not have any stage parameters. key: %s", new Object[]{notificationEntry.mKey}));
        return new RowContentBindParams();
    }
}
