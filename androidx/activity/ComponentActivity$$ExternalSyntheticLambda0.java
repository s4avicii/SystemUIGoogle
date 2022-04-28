package androidx.activity;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.contextaware.OnContextAvailableListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ComponentActivity$$ExternalSyntheticLambda0 implements OnContextAvailableListener {
    public final /* synthetic */ ComponentActivity f$0;

    public /* synthetic */ ComponentActivity$$ExternalSyntheticLambda0(ComponentActivity componentActivity) {
        this.f$0 = componentActivity;
    }

    public final void onContextAvailable() {
        ComponentActivity componentActivity = this.f$0;
        Objects.requireNonNull(componentActivity);
        Bundle consumeRestoredStateForKey = componentActivity.getSavedStateRegistry().consumeRestoredStateForKey("android:support:activity-result");
        if (consumeRestoredStateForKey != null) {
            ComponentActivity.C00172 r7 = componentActivity.mActivityResultRegistry;
            Objects.requireNonNull(r7);
            ArrayList<Integer> integerArrayList = consumeRestoredStateForKey.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
            ArrayList<String> stringArrayList = consumeRestoredStateForKey.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
            if (stringArrayList != null && integerArrayList != null) {
                r7.mLaunchedKeys = consumeRestoredStateForKey.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
                r7.mRandom = (Random) consumeRestoredStateForKey.getSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT");
                r7.mPendingResults.putAll(consumeRestoredStateForKey.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT"));
                for (int i = 0; i < stringArrayList.size(); i++) {
                    String str = stringArrayList.get(i);
                    if (r7.mKeyToRc.containsKey(str)) {
                        Integer num = (Integer) r7.mKeyToRc.remove(str);
                        if (!r7.mPendingResults.containsKey(str)) {
                            r7.mRcToKey.remove(num);
                        }
                    }
                    int intValue = integerArrayList.get(i).intValue();
                    String str2 = stringArrayList.get(i);
                    r7.mRcToKey.put(Integer.valueOf(intValue), str2);
                    r7.mKeyToRc.put(str2, Integer.valueOf(intValue));
                }
            }
        }
    }
}
