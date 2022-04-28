package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import java.io.File;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$userSwitchReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$userSwitchReceiver$1(ControlsControllerImpl controlsControllerImpl) {
        this.this$0 = controlsControllerImpl;
    }

    public final void onReceive(Context context, Intent intent) {
        if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.USER_SWITCHED")) {
            this.this$0.userChanging = true;
            UserHandle of = UserHandle.of(intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId()));
            if (Intrinsics.areEqual(this.this$0.currentUser, of)) {
                this.this$0.userChanging = false;
                return;
            }
            ControlsControllerImpl controlsControllerImpl = this.this$0;
            Objects.requireNonNull(controlsControllerImpl);
            Log.d("ControlsControllerImpl", Intrinsics.stringPlus("Changing to user: ", of));
            controlsControllerImpl.currentUser = of;
            UserStructure userStructure = new UserStructure(controlsControllerImpl.context, of);
            controlsControllerImpl.userStructure = userStructure;
            ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl.persistenceWrapper;
            File file = userStructure.file;
            UserStructure userStructure2 = controlsControllerImpl.userStructure;
            Objects.requireNonNull(userStructure2);
            BackupManager backupManager = new BackupManager(userStructure2.userContext);
            Objects.requireNonNull(controlsFavoritePersistenceWrapper);
            controlsFavoritePersistenceWrapper.file = file;
            controlsFavoritePersistenceWrapper.backupManager = backupManager;
            AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper = controlsControllerImpl.auxiliaryPersistenceWrapper;
            UserStructure userStructure3 = controlsControllerImpl.userStructure;
            Objects.requireNonNull(userStructure3);
            File file2 = userStructure3.auxiliaryFile;
            Objects.requireNonNull(auxiliaryPersistenceWrapper);
            ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper2 = auxiliaryPersistenceWrapper.persistenceWrapper;
            Objects.requireNonNull(controlsFavoritePersistenceWrapper2);
            controlsFavoritePersistenceWrapper2.file = file2;
            controlsFavoritePersistenceWrapper2.backupManager = null;
            auxiliaryPersistenceWrapper.initialize();
            controlsControllerImpl.resetFavorites();
            controlsControllerImpl.bindingController.changeUser(of);
            controlsControllerImpl.listingController.changeUser(of);
            controlsControllerImpl.userChanging = false;
        }
    }
}
