package com.android.systemui.statusbar.policy;

public interface LocationController extends CallbackController<LocationChangeCallback> {

    public interface LocationChangeCallback {
        void onLocationActiveChanged() {
        }

        void onLocationSettingsChanged() {
        }
    }

    boolean isLocationActive();

    boolean isLocationEnabled();

    boolean setLocationEnabled(boolean z);
}
