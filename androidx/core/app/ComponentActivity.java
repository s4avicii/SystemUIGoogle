package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import java.util.Objects;
import java.util.WeakHashMap;

public class ComponentActivity extends Activity implements LifecycleOwner, KeyEventDispatcher$Component {
    public LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this, true);

    public void onSaveInstanceState(Bundle bundle) {
        LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
        Lifecycle.State state = Lifecycle.State.CREATED;
        Objects.requireNonNull(lifecycleRegistry);
        lifecycleRegistry.enforceMainThreadIfNeeded("markState");
        lifecycleRegistry.setCurrentState(state);
        super.onSaveInstanceState(bundle);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (getWindow().getDecorView() != null) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        }
        return superDispatchKeyEvent(keyEvent);
    }

    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (getWindow().getDecorView() != null) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        }
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    @SuppressLint({"RestrictedApi"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.injectIfNeededIn(this);
    }

    public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }
}
