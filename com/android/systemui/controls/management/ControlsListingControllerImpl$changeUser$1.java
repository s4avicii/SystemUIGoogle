package com.android.systemui.controls.management;

import android.content.Context;
import android.os.UserHandle;
import com.android.settingslib.applications.ServiceListing;
import java.util.Objects;

/* compiled from: ControlsListingControllerImpl.kt */
public final class ControlsListingControllerImpl$changeUser$1 implements Runnable {
    public final /* synthetic */ UserHandle $newUser;
    public final /* synthetic */ ControlsListingControllerImpl this$0;

    public ControlsListingControllerImpl$changeUser$1(ControlsListingControllerImpl controlsListingControllerImpl, UserHandle userHandle) {
        this.this$0 = controlsListingControllerImpl;
        this.$newUser = userHandle;
    }

    public final void run() {
        if (this.this$0.userChangeInProgress.decrementAndGet() == 0) {
            this.this$0.currentUserId = this.$newUser.getIdentifier();
            Context createContextAsUser = this.this$0.context.createContextAsUser(this.$newUser, 0);
            ControlsListingControllerImpl controlsListingControllerImpl = this.this$0;
            controlsListingControllerImpl.serviceListing = controlsListingControllerImpl.serviceListingBuilder.invoke(createContextAsUser);
            ControlsListingControllerImpl controlsListingControllerImpl2 = this.this$0;
            ServiceListing serviceListing = controlsListingControllerImpl2.serviceListing;
            ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = controlsListingControllerImpl2.serviceListingCallback;
            Objects.requireNonNull(serviceListing);
            serviceListing.mCallbacks.add(controlsListingControllerImpl$serviceListingCallback$1);
            this.this$0.serviceListing.setListening(true);
            this.this$0.serviceListing.reload();
        }
    }
}
