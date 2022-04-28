package androidx.fragment.app;

import androidx.fragment.app.FragmentActivity;

public final class FragmentController {
    public final FragmentHostCallback<?> mHost;

    public final void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public FragmentController(FragmentActivity.HostCallbacks hostCallbacks) {
        this.mHost = hostCallbacks;
    }
}
