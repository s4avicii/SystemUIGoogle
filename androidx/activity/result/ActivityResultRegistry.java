package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.transition.TransitionPropagation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class ActivityResultRegistry {
    public final transient HashMap mKeyToCallback = new HashMap();
    public final HashMap mKeyToLifecycleContainers = new HashMap();
    public final HashMap mKeyToRc = new HashMap();
    public ArrayList<String> mLaunchedKeys = new ArrayList<>();
    public final HashMap mParsedPendingResults = new HashMap();
    public final Bundle mPendingResults = new Bundle();
    public Random mRandom = new Random();
    public final HashMap mRcToKey = new HashMap();

    /* renamed from: androidx.activity.result.ActivityResultRegistry$1 */
    class C00221 implements LifecycleEventObserver {
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (Lifecycle.Event.ON_START.equals(event)) {
                throw null;
            } else if (Lifecycle.Event.ON_STOP.equals(event)) {
                throw null;
            } else if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                throw null;
            }
        }
    }

    public static class LifecycleContainer {
    }

    public static class CallbackAndContract<O> {
        public final ActivityResultCallback<O> mCallback;
        public final ActivityResultContract<?, O> mContract;

        public CallbackAndContract(ActivityResultCallback<O> activityResultCallback, ActivityResultContract<?, O> activityResultContract) {
            this.mCallback = activityResultCallback;
            this.mContract = activityResultContract;
        }
    }

    public final boolean dispatchResult(int i, int i2, Intent intent) {
        ActivityResultCallback<O> activityResultCallback;
        String str = (String) this.mRcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        this.mLaunchedKeys.remove(str);
        CallbackAndContract callbackAndContract = (CallbackAndContract) this.mKeyToCallback.get(str);
        if (callbackAndContract == null || (activityResultCallback = callbackAndContract.mCallback) == null) {
            this.mParsedPendingResults.remove(str);
            this.mPendingResults.putParcelable(str, new ActivityResult(i2, intent));
            return true;
        }
        activityResultCallback.onActivityResult(callbackAndContract.mContract.parseResult(i2, intent));
        return true;
    }

    public final C00233 register(final String str, ActivityResultContract activityResultContract, ActivityResultCallback activityResultCallback) {
        int i;
        if (((Integer) this.mKeyToRc.get(str)) == null) {
            int nextInt = this.mRandom.nextInt(2147418112);
            while (true) {
                i = nextInt + 65536;
                if (!this.mRcToKey.containsKey(Integer.valueOf(i))) {
                    break;
                }
                nextInt = this.mRandom.nextInt(2147418112);
            }
            this.mRcToKey.put(Integer.valueOf(i), str);
            this.mKeyToRc.put(str, Integer.valueOf(i));
        }
        this.mKeyToCallback.put(str, new CallbackAndContract(activityResultCallback, activityResultContract));
        if (this.mParsedPendingResults.containsKey(str)) {
            Object obj = this.mParsedPendingResults.get(str);
            this.mParsedPendingResults.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        ActivityResult activityResult = (ActivityResult) this.mPendingResults.getParcelable(str);
        if (activityResult != null) {
            this.mPendingResults.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.mResultCode, activityResult.mData));
        }
        return new TransitionPropagation() {
        };
    }
}
