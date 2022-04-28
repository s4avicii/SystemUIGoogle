package com.android.systemui.communal;

import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.Objects;

public final class CommunalStateController implements CallbackController<Callback> {
    public final ArrayList<Callback> mCallbacks = new ArrayList<>();
    public boolean mCommunalViewOccluded;
    public boolean mCommunalViewShowing;

    public interface Callback {
        void onCommunalViewShowingChanged() {
        }
    }

    public final void addCallback(Callback callback) {
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        if (!this.mCallbacks.contains(callback)) {
            this.mCallbacks.add(callback);
        }
    }

    public final void removeCallback(Object obj) {
        Callback callback = (Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        this.mCallbacks.remove(callback);
    }
}
