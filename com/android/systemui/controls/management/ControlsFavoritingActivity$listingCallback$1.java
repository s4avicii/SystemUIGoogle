package com.android.systemui.controls.management;

import android.view.View;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$listingCallback$1 implements ControlsListingController.ControlsListingCallback {
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$listingCallback$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void onServicesUpdated(ArrayList arrayList) {
        if (arrayList.size() > 1) {
            ControlsFavoritingActivity controlsFavoritingActivity = this.this$0;
            View view = controlsFavoritingActivity.otherAppsButton;
            if (view == null) {
                view = null;
            }
            view.post(new ControlsFavoritingActivity$listingCallback$1$onServicesUpdated$1(controlsFavoritingActivity));
        }
    }
}
