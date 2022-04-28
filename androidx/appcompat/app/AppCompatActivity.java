package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.collection.ArraySet;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.savedstate.SavedStateRegistry;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class AppCompatActivity extends FragmentActivity implements AppCompatCallback {
    public AppCompatDelegateImpl mDelegate;

    public final void onContentChanged() {
    }

    public final void onSupportActionModeFinished() {
    }

    public final void onSupportActionModeStarted() {
    }

    public final void onWindowStartingSupportActionMode() {
    }

    public final void setContentView(int i) {
        initViewTreeOwners();
        getDelegate().setContentView(i);
    }

    public final AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            ArraySet<WeakReference<AppCompatDelegate>> arraySet = AppCompatDelegate.sActivityDelegates;
            this.mDelegate = new AppCompatDelegateImpl(this, (Window) null, this, this);
        }
        return this.mDelegate;
    }

    public final Resources getResources() {
        int i = VectorEnabledTintResources.$r8$clinit;
        return super.getResources();
    }

    public AppCompatActivity() {
        getSavedStateRegistry().registerSavedStateProvider("androidx:appcompat", new SavedStateRegistry.SavedStateProvider() {
            public final Bundle saveState() {
                Bundle bundle = new Bundle();
                Objects.requireNonNull(AppCompatActivity.this.getDelegate());
                return bundle;
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() {
            public final void onContextAvailable() {
                AppCompatDelegate delegate = AppCompatActivity.this.getDelegate();
                delegate.installViewFactory();
                AppCompatActivity.this.getSavedStateRegistry().consumeRestoredStateForKey("androidx:appcompat");
                delegate.onCreate();
            }
        });
    }

    private void initViewTreeOwners() {
        getWindow().getDecorView().setTag(C1777R.C1779id.view_tree_lifecycle_owner, this);
        getWindow().getDecorView().setTag(C1777R.C1779id.view_tree_view_model_store_owner, this);
        getWindow().getDecorView().setTag(C1777R.C1779id.view_tree_saved_state_registry_owner, this);
    }

    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().addContentView(view, layoutParams);
    }

    public final void attachBaseContext(Context context) {
        super.attachBaseContext(getDelegate().attachBaseContext2(context));
    }

    public final void closeOptionsMenu() {
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        Objects.requireNonNull(appCompatDelegateImpl);
        appCompatDelegateImpl.initWindowDecorActionBar();
        if (getWindow().hasFeature(0)) {
            super.closeOptionsMenu();
        }
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        keyEvent.getKeyCode();
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        Objects.requireNonNull(appCompatDelegateImpl);
        appCompatDelegateImpl.initWindowDecorActionBar();
        return super.dispatchKeyEvent(keyEvent);
    }

    public final <T extends View> T findViewById(int i) {
        return getDelegate().findViewById(i);
    }

    public final MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    public final void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getDelegate().onConfigurationChanged();
    }

    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        Objects.requireNonNull(appCompatDelegateImpl);
        appCompatDelegateImpl.initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = appCompatDelegateImpl.mActionBar;
        if (menuItem.getItemId() != 16908332 || windowDecorActionBar == null || (windowDecorActionBar.mDecorToolbar.getDisplayOptions() & 4) == 0) {
            return false;
        }
        Intent parentActivityIntent = NavUtils.getParentActivityIntent(this);
        if (parentActivityIntent == null) {
            return false;
        }
        if (shouldUpRecreateTask(parentActivityIntent)) {
            ArrayList arrayList = new ArrayList();
            Intent parentActivityIntent2 = NavUtils.getParentActivityIntent(this);
            if (parentActivityIntent2 == null) {
                parentActivityIntent2 = NavUtils.getParentActivityIntent(this);
            }
            if (parentActivityIntent2 != null) {
                ComponentName component = parentActivityIntent2.getComponent();
                if (component == null) {
                    component = parentActivityIntent2.resolveActivity(getPackageManager());
                }
                int size = arrayList.size();
                try {
                    Intent parentActivityIntent3 = NavUtils.getParentActivityIntent(this, component);
                    while (parentActivityIntent3 != null) {
                        arrayList.add(size, parentActivityIntent3);
                        parentActivityIntent3 = NavUtils.getParentActivityIntent(this, parentActivityIntent3.getComponent());
                    }
                    arrayList.add(parentActivityIntent2);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
                    throw new IllegalArgumentException(e);
                }
            }
            if (!arrayList.isEmpty()) {
                Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[arrayList.size()]);
                intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
                Object obj = ContextCompat.sLock;
                startActivities(intentArr, (Bundle) null);
                try {
                    int i2 = ActivityCompat.$r8$clinit;
                    finishAffinity();
                    return true;
                } catch (IllegalStateException unused) {
                    finish();
                    return true;
                }
            } else {
                throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
            }
        } else {
            navigateUpTo(parentActivityIntent);
            return true;
        }
    }

    public final boolean onMenuOpened(int i, Menu menu) {
        return super.onMenuOpened(i, menu);
    }

    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        Objects.requireNonNull(appCompatDelegateImpl);
        appCompatDelegateImpl.ensureSubDecor();
    }

    public final void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    public final void onStart() {
        super.onStart();
        getDelegate().onStart();
    }

    public final void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    public final void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        getDelegate().setTitle(charSequence);
    }

    public final void openOptionsMenu() {
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        Objects.requireNonNull(appCompatDelegateImpl);
        appCompatDelegateImpl.initWindowDecorActionBar();
        if (getWindow().hasFeature(0)) {
            super.openOptionsMenu();
        }
    }

    public final void setContentView(View view) {
        initViewTreeOwners();
        getDelegate().setContentView(view);
    }

    public final void setTheme(int i) {
        super.setTheme(i);
        getDelegate().setTheme(i);
    }

    public final void supportInvalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().setContentView(view, layoutParams);
    }

    public final void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
    }
}
