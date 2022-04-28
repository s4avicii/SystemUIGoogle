package com.android.systemui.statusbar.policy;

import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Objects;

public final class RemoteInputUriController {
    public final C16341 mInlineUriListener = new NotifCollectionListener() {
        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            try {
                IStatusBarService iStatusBarService = RemoteInputUriController.this.mStatusBarManagerService;
                Objects.requireNonNull(notificationEntry);
                iStatusBarService.clearInlineReplyUriPermissions(notificationEntry.mKey);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    };
    public final IStatusBarService mStatusBarManagerService;

    public RemoteInputUriController(IStatusBarService iStatusBarService) {
        this.mStatusBarManagerService = iStatusBarService;
    }
}
