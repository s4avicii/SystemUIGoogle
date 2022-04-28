package com.android.systemui.statusbar.notification.row;

import android.util.ArraySet;
import androidx.core.p002os.CancellationSignal;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import java.util.Objects;

public abstract class BindRequester {
    public BindRequestListener mBindRequestListener;

    public interface BindRequestListener {
    }

    public final CancellationSignal requestRebind(NotificationEntry notificationEntry, NotifBindPipeline.BindCallback bindCallback) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        BindRequestListener bindRequestListener = this.mBindRequestListener;
        if (bindRequestListener != null) {
            NotifBindPipeline notifBindPipeline = ((NotifBindPipeline$$ExternalSyntheticLambda1) bindRequestListener).f$0;
            Objects.requireNonNull(notifBindPipeline);
            NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) notifBindPipeline.mBindEntries.get(notificationEntry);
            if (bindEntry != null) {
                bindEntry.invalidated = true;
                if (bindCallback != null) {
                    ArraySet arraySet = bindEntry.callbacks;
                    arraySet.add(bindCallback);
                    cancellationSignal.setOnCancelListener(new NotifBindPipeline$$ExternalSyntheticLambda0(arraySet, bindCallback));
                }
                notifBindPipeline.requestPipelineRun(notificationEntry);
            }
        }
        return cancellationSignal;
    }
}
