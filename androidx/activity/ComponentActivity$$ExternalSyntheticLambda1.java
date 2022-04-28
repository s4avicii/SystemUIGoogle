package androidx.activity;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.savedstate.SavedStateRegistry;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ComponentActivity$$ExternalSyntheticLambda1 implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ ComponentActivity f$0;

    public /* synthetic */ ComponentActivity$$ExternalSyntheticLambda1(ComponentActivity componentActivity) {
        this.f$0 = componentActivity;
    }

    public final Bundle saveState() {
        ComponentActivity componentActivity = this.f$0;
        Objects.requireNonNull(componentActivity);
        Bundle bundle = new Bundle();
        ComponentActivity.C00172 r3 = componentActivity.mActivityResultRegistry;
        Objects.requireNonNull(r3);
        bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList(r3.mKeyToRc.values()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList(r3.mKeyToRc.keySet()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList(r3.mLaunchedKeys));
        bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) r3.mPendingResults.clone());
        bundle.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", r3.mRandom);
        return bundle;
    }
}
