package androidx.fragment.app;

import androidx.activity.contextaware.OnContextAvailableListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FragmentActivity$$ExternalSyntheticLambda0 implements OnContextAvailableListener {
    public final /* synthetic */ FragmentActivity f$0;

    public /* synthetic */ FragmentActivity$$ExternalSyntheticLambda0(FragmentActivity fragmentActivity) {
        this.f$0 = fragmentActivity;
    }

    public final void onContextAvailable() {
        FragmentActivity fragmentActivity = this.f$0;
        Objects.requireNonNull(fragmentActivity);
        FragmentController fragmentController = fragmentActivity.mFragments;
        Objects.requireNonNull(fragmentController);
        FragmentHostCallback<?> fragmentHostCallback = fragmentController.mHost;
        fragmentHostCallback.mFragmentManager.attachController(fragmentHostCallback, fragmentHostCallback, (Fragment) null);
    }
}
