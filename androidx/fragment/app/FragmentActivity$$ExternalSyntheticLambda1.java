package androidx.fragment.app;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.savedstate.SavedStateRegistry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FragmentActivity$$ExternalSyntheticLambda1 implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ FragmentActivity f$0;

    public /* synthetic */ FragmentActivity$$ExternalSyntheticLambda1(FragmentActivity fragmentActivity) {
        this.f$0 = fragmentActivity;
    }

    public final Bundle saveState() {
        FragmentController fragmentController;
        FragmentActivity fragmentActivity = this.f$0;
        Objects.requireNonNull(fragmentActivity);
        do {
            fragmentController = fragmentActivity.mFragments;
            Objects.requireNonNull(fragmentController);
        } while (FragmentActivity.markState(fragmentController.mHost.mFragmentManager));
        fragmentActivity.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        return new Bundle();
    }
}
