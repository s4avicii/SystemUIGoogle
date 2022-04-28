package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.savedstate.SavedStateRegistry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FragmentManager$$ExternalSyntheticLambda0 implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ FragmentManager f$0;

    public /* synthetic */ FragmentManager$$ExternalSyntheticLambda0(FragmentManagerImpl fragmentManagerImpl) {
        this.f$0 = fragmentManagerImpl;
    }

    public final Bundle saveState() {
        FragmentManager fragmentManager = this.f$0;
        Objects.requireNonNull(fragmentManager);
        Bundle bundle = new Bundle();
        Parcelable saveAllStateInternal = fragmentManager.saveAllStateInternal();
        if (saveAllStateInternal != null) {
            bundle.putParcelable("android:support:fragments", saveAllStateInternal);
        }
        return bundle;
    }
}
