package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.Cancellable;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import com.android.p012wm.shell.C1777R;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager {
    public ArrayList<BackStackRecord> mBackStack;
    public final AtomicInteger mBackStackIndex = new AtomicInteger();
    public final Map<String, BackStackState> mBackStackStates = Collections.synchronizedMap(new HashMap());
    public FragmentContainer mContainer;
    public ArrayList<Fragment> mCreatedMenus;
    public int mCurState = -1;
    public C01793 mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() {
    };
    public boolean mDestroyed;
    public C01804 mExecCommit = new Runnable() {
        public final void run() {
            FragmentManager.this.execPendingActions(true);
        }
    };
    public boolean mExecutingActions;
    public final FragmentStore mFragmentStore = new FragmentStore();
    public boolean mHavePendingDeferredStart;
    public FragmentHostCallback<?> mHost;
    public C01782 mHostFragmentFactory = new FragmentFactory() {
        public final Fragment instantiate(String str) {
            FragmentManager fragmentManager = FragmentManager.this;
            Objects.requireNonNull(fragmentManager);
            FragmentHostCallback<?> fragmentHostCallback = fragmentManager.mHost;
            FragmentManager fragmentManager2 = FragmentManager.this;
            Objects.requireNonNull(fragmentManager2);
            FragmentHostCallback<?> fragmentHostCallback2 = fragmentManager2.mHost;
            Objects.requireNonNull(fragmentHostCallback2);
            Context context = fragmentHostCallback2.mContext;
            Objects.requireNonNull(fragmentHostCallback);
            Object obj = Fragment.USE_DEFAULT_TRANSITION;
            try {
                return (Fragment) FragmentFactory.loadFragmentClass(context.getClassLoader(), str).getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (InstantiationException e) {
                throw new Fragment.InstantiationException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
            } catch (IllegalAccessException e2) {
                throw new Fragment.InstantiationException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
            } catch (NoSuchMethodException e3) {
                throw new Fragment.InstantiationException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
            } catch (InvocationTargetException e4) {
                throw new Fragment.InstantiationException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
            }
        }
    };
    public ArrayDeque<LaunchedFragmentInfo> mLaunchedFragments = new ArrayDeque<>();
    public final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    public final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    public boolean mNeedMenuInvalidate;
    public FragmentManagerViewModel mNonConfig;
    public final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList<>();
    public final C01771 mOnBackPressedCallback = new OnBackPressedCallback() {
        public final void handleOnBackPressed() {
            FragmentManager fragmentManager = FragmentManager.this;
            Objects.requireNonNull(fragmentManager);
            fragmentManager.execPendingActions(true);
            C01771 r0 = fragmentManager.mOnBackPressedCallback;
            Objects.requireNonNull(r0);
            if (r0.mEnabled) {
                fragmentManager.popBackStackImmediate$1();
            } else {
                fragmentManager.mOnBackPressedDispatcher.onBackPressed();
            }
        }
    };
    public OnBackPressedDispatcher mOnBackPressedDispatcher;
    public Fragment mParent;
    public final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    public Fragment mPrimaryNav;
    public ActivityResultRegistry.C00233 mRequestPermissions;
    public final Map<String, Object> mResultListeners = Collections.synchronizedMap(new HashMap());
    public final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
    public ActivityResultRegistry.C00233 mStartActivityForResult;
    public ActivityResultRegistry.C00233 mStartIntentSenderForResult;
    public boolean mStateSaved;
    public boolean mStopped;
    public ArrayList<Fragment> mTmpAddedFragments;
    public ArrayList<Boolean> mTmpIsPop;
    public ArrayList<BackStackRecord> mTmpRecords;

    /* renamed from: androidx.fragment.app.FragmentManager$5 */
    class C01815 implements LifecycleEventObserver {
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                throw null;
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                throw null;
            }
        }
    }

    public static class FragmentIntentSenderContract extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        public final Object parseResult(int i, Intent intent) {
            return new ActivityResult(i, intent);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator<LaunchedFragmentInfo>() {
            public final Object createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            public final Object[] newArray(int i) {
                return new LaunchedFragmentInfo[i];
            }
        };
        public int mRequestCode;
        public String mWho;

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mWho);
            parcel.writeInt(this.mRequestCode);
        }

        public LaunchedFragmentInfo(Parcel parcel) {
            this.mWho = parcel.readString();
            this.mRequestCode = parcel.readInt();
        }
    }

    public interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    public class PopBackStackState implements OpGenerator {
        public final int mFlags = 1;
        public final int mId;

        public PopBackStackState(int i) {
            this.mId = i;
        }

        public final boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            Fragment fragment = FragmentManager.this.mPrimaryNav;
            if (fragment == null || this.mId >= 0 || !fragment.getChildFragmentManager().popBackStackImmediate$1()) {
                return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mId, this.mFlags);
            }
            return false;
        }
    }

    public static boolean isParentMenuVisible(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.mMenuVisible && (fragment.mFragmentManager == null || isParentMenuVisible(fragment.mParentFragment));
    }

    public static boolean isPrimaryNavigation(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        Objects.requireNonNull(fragmentManager);
        return fragment.equals(fragmentManager.mPrimaryNav) && isPrimaryNavigation(fragmentManager.mParent);
    }

    public static void showFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    public final void attachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                this.mFragmentStore.addFragment(fragment);
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "add from attach: " + fragment);
                }
                if (isMenuAvailable(fragment)) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    public final void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    public final void detachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "remove from detach: " + fragment);
                }
                FragmentStore fragmentStore = this.mFragmentStore;
                Objects.requireNonNull(fragmentStore);
                synchronized (fragmentStore.mAdded) {
                    fragmentStore.mAdded.remove(fragment);
                }
                fragment.mAdded = false;
                if (isMenuAvailable(fragment)) {
                    this.mNeedMenuInvalidate = true;
                }
                setVisibleRemovingFragment(fragment);
            }
        }
    }

    public final void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        FragmentManagerViewModel fragmentManagerViewModel = this.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = false;
        dispatchStateChange(1);
    }

    public final void dispatchDestroy() {
        Integer num;
        Integer num2;
        Integer num3;
        boolean z = true;
        this.mDestroyed = true;
        execPendingActions(true);
        Iterator it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
        }
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            FragmentStore fragmentStore = this.mFragmentStore;
            Objects.requireNonNull(fragmentStore);
            FragmentManagerViewModel fragmentManagerViewModel = fragmentStore.mNonConfig;
            Objects.requireNonNull(fragmentManagerViewModel);
            z = fragmentManagerViewModel.mHasBeenCleared;
        } else {
            Objects.requireNonNull(fragmentHostCallback);
            if (fragmentHostCallback.mContext instanceof Activity) {
                FragmentHostCallback<?> fragmentHostCallback2 = this.mHost;
                Objects.requireNonNull(fragmentHostCallback2);
                z = true ^ ((Activity) fragmentHostCallback2.mContext).isChangingConfigurations();
            }
        }
        if (z) {
            for (BackStackState backStackState : this.mBackStackStates.values()) {
                for (String str : backStackState.mFragments) {
                    FragmentStore fragmentStore2 = this.mFragmentStore;
                    Objects.requireNonNull(fragmentStore2);
                    FragmentManagerViewModel fragmentManagerViewModel2 = fragmentStore2.mNonConfig;
                    Objects.requireNonNull(fragmentManagerViewModel2);
                    if (isLoggingEnabled(3)) {
                        DialogFragment$$ExternalSyntheticOutline0.m17m("Clearing non-config state for saved state of Fragment ", str, "FragmentManager");
                    }
                    fragmentManagerViewModel2.clearNonConfigStateInternal(str);
                }
            }
        }
        dispatchStateChange(-1);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            C01771 r1 = this.mOnBackPressedCallback;
            Objects.requireNonNull(r1);
            Iterator<Cancellable> it2 = r1.mCancellables.iterator();
            while (it2.hasNext()) {
                it2.next().cancel();
            }
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultRegistry.C00233 r12 = this.mStartActivityForResult;
        if (r12 != null) {
            ActivityResultRegistry activityResultRegistry = ActivityResultRegistry.this;
            String str2 = str;
            Objects.requireNonNull(activityResultRegistry);
            if (!activityResultRegistry.mLaunchedKeys.contains(str2) && (num3 = (Integer) activityResultRegistry.mKeyToRc.remove(str2)) != null) {
                activityResultRegistry.mRcToKey.remove(num3);
            }
            activityResultRegistry.mKeyToCallback.remove(str2);
            if (activityResultRegistry.mParsedPendingResults.containsKey(str2)) {
                StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Dropping pending result for request ", str2, ": ");
                m.append(activityResultRegistry.mParsedPendingResults.get(str2));
                Log.w("ActivityResultRegistry", m.toString());
                activityResultRegistry.mParsedPendingResults.remove(str2);
            }
            if (activityResultRegistry.mPendingResults.containsKey(str2)) {
                StringBuilder m2 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Dropping pending result for request ", str2, ": ");
                m2.append(activityResultRegistry.mPendingResults.getParcelable(str2));
                Log.w("ActivityResultRegistry", m2.toString());
                activityResultRegistry.mPendingResults.remove(str2);
            }
            if (((ActivityResultRegistry.LifecycleContainer) activityResultRegistry.mKeyToLifecycleContainers.get(str2)) == null) {
                ActivityResultRegistry.C00233 r13 = this.mStartIntentSenderForResult;
                Objects.requireNonNull(r13);
                ActivityResultRegistry activityResultRegistry2 = ActivityResultRegistry.this;
                String str3 = str;
                Objects.requireNonNull(activityResultRegistry2);
                if (!activityResultRegistry2.mLaunchedKeys.contains(str3) && (num2 = (Integer) activityResultRegistry2.mKeyToRc.remove(str3)) != null) {
                    activityResultRegistry2.mRcToKey.remove(num2);
                }
                activityResultRegistry2.mKeyToCallback.remove(str3);
                if (activityResultRegistry2.mParsedPendingResults.containsKey(str3)) {
                    StringBuilder m3 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Dropping pending result for request ", str3, ": ");
                    m3.append(activityResultRegistry2.mParsedPendingResults.get(str3));
                    Log.w("ActivityResultRegistry", m3.toString());
                    activityResultRegistry2.mParsedPendingResults.remove(str3);
                }
                if (activityResultRegistry2.mPendingResults.containsKey(str3)) {
                    StringBuilder m4 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Dropping pending result for request ", str3, ": ");
                    m4.append(activityResultRegistry2.mPendingResults.getParcelable(str3));
                    Log.w("ActivityResultRegistry", m4.toString());
                    activityResultRegistry2.mPendingResults.remove(str3);
                }
                if (((ActivityResultRegistry.LifecycleContainer) activityResultRegistry2.mKeyToLifecycleContainers.get(str3)) == null) {
                    ActivityResultRegistry.C00233 r8 = this.mRequestPermissions;
                    Objects.requireNonNull(r8);
                    ActivityResultRegistry activityResultRegistry3 = ActivityResultRegistry.this;
                    String str4 = str;
                    Objects.requireNonNull(activityResultRegistry3);
                    if (!activityResultRegistry3.mLaunchedKeys.contains(str4) && (num = (Integer) activityResultRegistry3.mKeyToRc.remove(str4)) != null) {
                        activityResultRegistry3.mRcToKey.remove(num);
                    }
                    activityResultRegistry3.mKeyToCallback.remove(str4);
                    if (activityResultRegistry3.mParsedPendingResults.containsKey(str4)) {
                        StringBuilder m5 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Dropping pending result for request ", str4, ": ");
                        m5.append(activityResultRegistry3.mParsedPendingResults.get(str4));
                        Log.w("ActivityResultRegistry", m5.toString());
                        activityResultRegistry3.mParsedPendingResults.remove(str4);
                    }
                    if (activityResultRegistry3.mPendingResults.containsKey(str4)) {
                        StringBuilder m6 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Dropping pending result for request ", str4, ": ");
                        m6.append(activityResultRegistry3.mPendingResults.getParcelable(str4));
                        Log.w("ActivityResultRegistry", m6.toString());
                        activityResultRegistry3.mPendingResults.remove(str4);
                    }
                    if (((ActivityResultRegistry.LifecycleContainer) activityResultRegistry3.mKeyToLifecycleContainers.get(str4)) != null) {
                        throw null;
                    }
                    return;
                }
                throw null;
            }
            throw null;
        }
    }

    /* JADX INFO: finally extract failed */
    public final void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            FragmentStore fragmentStore = this.mFragmentStore;
            Objects.requireNonNull(fragmentStore);
            for (FragmentStateManager next : fragmentStore.mActive.values()) {
                if (next != null) {
                    next.mFragmentManagerState = i;
                }
            }
            moveToState(i, false);
            Iterator it = collectAllSpecialEffectsController().iterator();
            while (it.hasNext()) {
                ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
            }
            this.mExecutingActions = false;
            execPendingActions(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    public final void hideFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
            setVisibleRemovingFragment(fragment);
        }
    }

    public final boolean popBackStackImmediate$1() {
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && fragment.getChildFragmentManager().popBackStackImmediate$1()) {
            return true;
        }
        boolean popBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, -1, 0);
        if (popBackStackState) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        FragmentStore fragmentStore = this.mFragmentStore;
        Objects.requireNonNull(fragmentStore);
        fragmentStore.mActive.values().removeAll(Collections.singleton((Object) null));
        return popBackStackState;
    }

    public final boolean popBackStackState(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        boolean z;
        if ((i2 & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        int i3 = -1;
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            if (i < 0) {
                i3 = z ? 0 : -1 + this.mBackStack.size();
            } else {
                int size = this.mBackStack.size() - 1;
                while (size >= 0) {
                    BackStackRecord backStackRecord = this.mBackStack.get(size);
                    if (i >= 0 && i == backStackRecord.mIndex) {
                        break;
                    }
                    size--;
                }
                if (size >= 0) {
                    if (z) {
                        while (size > 0) {
                            int i4 = size - 1;
                            BackStackRecord backStackRecord2 = this.mBackStack.get(i4);
                            if (i < 0 || i != backStackRecord2.mIndex) {
                                break;
                            }
                            size = i4;
                        }
                    } else if (size != this.mBackStack.size() - 1) {
                        size++;
                    }
                }
                i3 = size;
            }
        }
        if (i3 < 0) {
            return false;
        }
        for (int size2 = this.mBackStack.size() - 1; size2 >= i3; size2--) {
            arrayList.add(this.mBackStack.remove(size2));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    public final void removeFragment(Fragment fragment) {
        boolean z;
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        if (fragment.mBackStackNesting > 0) {
            z = true;
        } else {
            z = false;
        }
        boolean z2 = !z;
        if (!fragment.mDetached || z2) {
            FragmentStore fragmentStore = this.mFragmentStore;
            Objects.requireNonNull(fragmentStore);
            synchronized (fragmentStore.mAdded) {
                fragmentStore.mAdded.remove(fragment);
            }
            fragment.mAdded = false;
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    public static boolean isLoggingEnabled(int i) {
        if (Log.isLoggable("FragmentManager", i)) {
            return true;
        }
        return false;
    }

    public static boolean isMenuAvailable(Fragment fragment) {
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        FragmentStore fragmentStore = fragmentManagerImpl.mFragmentStore;
        Objects.requireNonNull(fragmentStore);
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager next : fragmentStore.mActive.values()) {
            if (next != null) {
                arrayList.add(next.mFragment);
            } else {
                arrayList.add((Object) null);
            }
        }
        Iterator it = arrayList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Fragment fragment2 = (Fragment) it.next();
            if (fragment2 != null) {
                z = isMenuAvailable(fragment2);
                continue;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final FragmentStateManager addFragment(Fragment fragment) {
        String str = fragment.mPreviousWho;
        if (str != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str);
        }
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        FragmentStateManager createOrGetFragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        this.mFragmentStore.makeActive(createOrGetFragmentStateManager);
        if (!fragment.mDetached) {
            this.mFragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return createOrGetFragmentStateManager;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v17, resolved type: androidx.activity.OnBackPressedDispatcherOwner} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v24, resolved type: androidx.fragment.app.Fragment} */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.SuppressLint({"SyntheticAccessor"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void attachController(androidx.fragment.app.FragmentHostCallback<?> r4, androidx.fragment.app.FragmentContainer r5, final androidx.fragment.app.Fragment r6) {
        /*
            r3 = this;
            androidx.fragment.app.FragmentHostCallback<?> r0 = r3.mHost
            if (r0 != 0) goto L_0x0137
            r3.mHost = r4
            r3.mContainer = r5
            r3.mParent = r6
            if (r6 == 0) goto L_0x0017
            androidx.fragment.app.FragmentManager$6 r5 = new androidx.fragment.app.FragmentManager$6
            r5.<init>()
            java.util.concurrent.CopyOnWriteArrayList<androidx.fragment.app.FragmentOnAttachListener> r0 = r3.mOnAttachListeners
            r0.add(r5)
            goto L_0x0023
        L_0x0017:
            boolean r5 = r4 instanceof androidx.fragment.app.FragmentOnAttachListener
            if (r5 == 0) goto L_0x0023
            r5 = r4
            androidx.fragment.app.FragmentOnAttachListener r5 = (androidx.fragment.app.FragmentOnAttachListener) r5
            java.util.concurrent.CopyOnWriteArrayList<androidx.fragment.app.FragmentOnAttachListener> r0 = r3.mOnAttachListeners
            r0.add(r5)
        L_0x0023:
            androidx.fragment.app.Fragment r5 = r3.mParent
            if (r5 == 0) goto L_0x002a
            r3.updateOnBackPressedCallbackEnabled()
        L_0x002a:
            boolean r5 = r4 instanceof androidx.activity.OnBackPressedDispatcherOwner
            if (r5 == 0) goto L_0x003f
            r5 = r4
            androidx.activity.OnBackPressedDispatcherOwner r5 = (androidx.activity.OnBackPressedDispatcherOwner) r5
            androidx.activity.OnBackPressedDispatcher r0 = r5.getOnBackPressedDispatcher()
            r3.mOnBackPressedDispatcher = r0
            if (r6 == 0) goto L_0x003a
            r5 = r6
        L_0x003a:
            androidx.fragment.app.FragmentManager$1 r1 = r3.mOnBackPressedCallback
            r0.addCallback(r5, r1)
        L_0x003f:
            r5 = 0
            if (r6 == 0) goto L_0x0069
            androidx.fragment.app.FragmentManager r4 = r6.mFragmentManager
            java.util.Objects.requireNonNull(r4)
            androidx.fragment.app.FragmentManagerViewModel r4 = r4.mNonConfig
            java.util.Objects.requireNonNull(r4)
            java.util.HashMap<java.lang.String, androidx.fragment.app.FragmentManagerViewModel> r0 = r4.mChildNonConfigs
            java.lang.String r1 = r6.mWho
            java.lang.Object r0 = r0.get(r1)
            androidx.fragment.app.FragmentManagerViewModel r0 = (androidx.fragment.app.FragmentManagerViewModel) r0
            if (r0 != 0) goto L_0x0066
            androidx.fragment.app.FragmentManagerViewModel r0 = new androidx.fragment.app.FragmentManagerViewModel
            boolean r1 = r4.mStateAutomaticallySaved
            r0.<init>(r1)
            java.util.HashMap<java.lang.String, androidx.fragment.app.FragmentManagerViewModel> r4 = r4.mChildNonConfigs
            java.lang.String r1 = r6.mWho
            r4.put(r1, r0)
        L_0x0066:
            r3.mNonConfig = r0
            goto L_0x008c
        L_0x0069:
            boolean r0 = r4 instanceof androidx.lifecycle.ViewModelStoreOwner
            if (r0 == 0) goto L_0x0085
            androidx.lifecycle.ViewModelStoreOwner r4 = (androidx.lifecycle.ViewModelStoreOwner) r4
            androidx.lifecycle.ViewModelStore r4 = r4.getViewModelStore()
            androidx.lifecycle.ViewModelProvider r0 = new androidx.lifecycle.ViewModelProvider
            androidx.fragment.app.FragmentManagerViewModel$1 r1 = androidx.fragment.app.FragmentManagerViewModel.FACTORY
            r0.<init>(r4, r1)
            java.lang.Class<androidx.fragment.app.FragmentManagerViewModel> r4 = androidx.fragment.app.FragmentManagerViewModel.class
            androidx.lifecycle.ViewModel r4 = r0.get(r4)
            androidx.fragment.app.FragmentManagerViewModel r4 = (androidx.fragment.app.FragmentManagerViewModel) r4
            r3.mNonConfig = r4
            goto L_0x008c
        L_0x0085:
            androidx.fragment.app.FragmentManagerViewModel r4 = new androidx.fragment.app.FragmentManagerViewModel
            r4.<init>(r5)
            r3.mNonConfig = r4
        L_0x008c:
            androidx.fragment.app.FragmentManagerViewModel r4 = r3.mNonConfig
            boolean r0 = r3.mStateSaved
            if (r0 != 0) goto L_0x0096
            boolean r0 = r3.mStopped
            if (r0 == 0) goto L_0x0097
        L_0x0096:
            r5 = 1
        L_0x0097:
            java.util.Objects.requireNonNull(r4)
            r4.mIsStateSaved = r5
            androidx.fragment.app.FragmentStore r4 = r3.mFragmentStore
            androidx.fragment.app.FragmentManagerViewModel r5 = r3.mNonConfig
            java.util.Objects.requireNonNull(r4)
            r4.mNonConfig = r5
            androidx.fragment.app.FragmentHostCallback<?> r4 = r3.mHost
            boolean r5 = r4 instanceof androidx.savedstate.SavedStateRegistryOwner
            if (r5 == 0) goto L_0x00cd
            if (r6 != 0) goto L_0x00cd
            androidx.savedstate.SavedStateRegistryOwner r4 = (androidx.savedstate.SavedStateRegistryOwner) r4
            androidx.savedstate.SavedStateRegistry r4 = r4.getSavedStateRegistry()
            androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0 r5 = new androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
            r0 = r3
            androidx.fragment.app.FragmentManagerImpl r0 = (androidx.fragment.app.FragmentManagerImpl) r0
            r5.<init>(r0)
            java.lang.String r0 = "android:support:fragments"
            r4.registerSavedStateProvider(r0, r5)
            android.os.Bundle r4 = r4.consumeRestoredStateForKey(r0)
            if (r4 == 0) goto L_0x00cd
            android.os.Parcelable r4 = r4.getParcelable(r0)
            r3.restoreSaveStateInternal(r4)
        L_0x00cd:
            androidx.fragment.app.FragmentHostCallback<?> r4 = r3.mHost
            boolean r5 = r4 instanceof androidx.activity.result.ActivityResultRegistryOwner
            if (r5 == 0) goto L_0x0136
            androidx.activity.result.ActivityResultRegistryOwner r4 = (androidx.activity.result.ActivityResultRegistryOwner) r4
            androidx.activity.result.ActivityResultRegistry r4 = r4.getActivityResultRegistry()
            if (r6 == 0) goto L_0x00e9
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = r6.mWho
            java.lang.String r0 = ":"
            java.lang.String r5 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1.m8m(r5, r6, r0)
            goto L_0x00eb
        L_0x00e9:
            java.lang.String r5 = ""
        L_0x00eb:
            java.lang.String r6 = "FragmentManager:"
            java.lang.String r5 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r6, r5)
            java.lang.String r6 = "StartActivityForResult"
            java.lang.String r6 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r5, r6)
            androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult r0 = new androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult
            r0.<init>()
            androidx.fragment.app.FragmentManager$7 r1 = new androidx.fragment.app.FragmentManager$7
            r2 = r3
            androidx.fragment.app.FragmentManagerImpl r2 = (androidx.fragment.app.FragmentManagerImpl) r2
            r1.<init>(r2)
            androidx.activity.result.ActivityResultRegistry$3 r6 = r4.register(r6, r0, r1)
            r3.mStartActivityForResult = r6
            java.lang.String r6 = "StartIntentSenderForResult"
            java.lang.String r6 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r5, r6)
            androidx.fragment.app.FragmentManager$FragmentIntentSenderContract r0 = new androidx.fragment.app.FragmentManager$FragmentIntentSenderContract
            r0.<init>()
            androidx.fragment.app.FragmentManager$8 r1 = new androidx.fragment.app.FragmentManager$8
            r1.<init>(r2)
            androidx.activity.result.ActivityResultRegistry$3 r6 = r4.register(r6, r0, r1)
            r3.mStartIntentSenderForResult = r6
            java.lang.String r6 = "RequestPermissions"
            java.lang.String r5 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r5, r6)
            androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions r6 = new androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions
            r6.<init>()
            androidx.fragment.app.FragmentManager$9 r0 = new androidx.fragment.app.FragmentManager$9
            r0.<init>(r2)
            androidx.activity.result.ActivityResultRegistry$3 r4 = r4.register(r5, r6, r0)
            r3.mRequestPermissions = r4
        L_0x0136:
            return
        L_0x0137:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "Already attached"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.attachController(androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer, androidx.fragment.app.Fragment):void");
    }

    public final HashSet collectAllSpecialEffectsController() {
        HashSet hashSet = new HashSet();
        Iterator it = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = (FragmentStateManager) it.next();
            Objects.requireNonNull(fragmentStateManager);
            ViewGroup viewGroup = fragmentStateManager.mFragment.mContainer;
            if (viewGroup != null) {
                hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, getSpecialEffectsControllerFactory()));
            }
        }
        return hashSet;
    }

    public final FragmentStateManager createOrGetFragmentStateManager(Fragment fragment) {
        FragmentStore fragmentStore = this.mFragmentStore;
        String str = fragment.mWho;
        Objects.requireNonNull(fragmentStore);
        FragmentStateManager fragmentStateManager = fragmentStore.mActive.get(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager;
        }
        FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        Objects.requireNonNull(fragmentHostCallback);
        fragmentStateManager2.restoreState(fragmentHostCallback.mContext.getClassLoader());
        fragmentStateManager2.mFragmentManagerState = this.mCurState;
        return fragmentStateManager2;
    }

    public final void dispatchConfigurationChanged(Configuration configuration) {
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null) {
                next.onConfigurationChanged(configuration);
                next.mChildFragmentManager.dispatchConfigurationChanged(configuration);
            }
        }
    }

    public final boolean dispatchContextItemSelected() {
        boolean z;
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null) {
                if (!next.mHidden) {
                    z = next.mChildFragmentManager.dispatchContextItemSelected();
                } else {
                    z = false;
                }
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean dispatchCreateOptionsMenu() {
        boolean z;
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z2 = false;
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null && isParentMenuVisible(next)) {
                if (!next.mHidden) {
                    z = next.mChildFragmentManager.dispatchCreateOptionsMenu() | false;
                } else {
                    z = false;
                }
                if (z) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(next);
                    z2 = true;
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment fragment = this.mCreatedMenus.get(i);
                if (arrayList == null || !arrayList.contains(fragment)) {
                    Objects.requireNonNull(fragment);
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z2;
    }

    public final void dispatchLowMemory() {
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null) {
                next.performLowMemory();
            }
        }
    }

    public final void dispatchMultiWindowModeChanged(boolean z) {
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null) {
                next.performMultiWindowModeChanged(z);
            }
        }
    }

    public final boolean dispatchOptionsItemSelected() {
        boolean z;
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null) {
                if (!next.mHidden) {
                    z = next.mChildFragmentManager.dispatchOptionsItemSelected();
                } else {
                    z = false;
                }
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void dispatchOptionsMenuClosed() {
        if (this.mCurState >= 1) {
            for (Fragment next : this.mFragmentStore.getFragments()) {
                if (next != null && !next.mHidden) {
                    next.mChildFragmentManager.dispatchOptionsMenuClosed();
                }
            }
        }
    }

    public final void dispatchParentPrimaryNavigationFragmentChanged(Fragment fragment) {
        if (fragment != null && fragment.equals(findActiveFragment(fragment.mWho))) {
            Objects.requireNonNull(fragment.mFragmentManager);
            boolean isPrimaryNavigation = isPrimaryNavigation(fragment);
            Boolean bool = fragment.mIsPrimaryNavigationFragment;
            if (bool == null || bool.booleanValue() != isPrimaryNavigation) {
                fragment.mIsPrimaryNavigationFragment = Boolean.valueOf(isPrimaryNavigation);
                FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
                Objects.requireNonNull(fragmentManagerImpl);
                fragmentManagerImpl.updateOnBackPressedCallbackEnabled();
                fragmentManagerImpl.dispatchParentPrimaryNavigationFragmentChanged(fragmentManagerImpl.mPrimaryNav);
            }
        }
    }

    public final void dispatchPictureInPictureModeChanged(boolean z) {
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null) {
                next.performPictureInPictureModeChanged(z);
            }
        }
    }

    public final boolean dispatchPrepareOptionsMenu() {
        boolean z = false;
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment next : this.mFragmentStore.getFragments()) {
            if (next != null && isParentMenuVisible(next) && next.performPrepareOptionsMenu()) {
                z = true;
            }
        }
        return z;
    }

    public final void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, "    ");
        FragmentStore fragmentStore = this.mFragmentStore;
        Objects.requireNonNull(fragmentStore);
        String str2 = str + "    ";
        if (!fragmentStore.mActive.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (FragmentStateManager next : fragmentStore.mActive.values()) {
                printWriter.print(str);
                if (next != null) {
                    Fragment fragment = next.mFragment;
                    printWriter.println(fragment);
                    fragment.dump(str2, fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        int size3 = fragmentStore.mAdded.size();
        if (size3 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size3; i++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragmentStore.mAdded.get(i).toString());
            }
        }
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.mCreatedMenus.get(i2).toString());
            }
        }
        ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i3 = 0; i3 < size; i3++) {
                BackStackRecord backStackRecord = this.mBackStack.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(m, printWriter, true);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int size4 = this.mPendingActions.size();
            if (size4 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i4 = 0; i4 < size4; i4++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i4);
                    printWriter.print(": ");
                    printWriter.println(this.mPendingActions.get(i4));
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
    }

    public final void enqueueAction(OpGenerator opGenerator, boolean z) {
        boolean z2;
        if (!z) {
            if (this.mHost != null) {
                if (this.mStateSaved || this.mStopped) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
                }
            } else if (this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            } else {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
        }
        synchronized (this.mPendingActions) {
            if (this.mHost != null) {
                this.mPendingActions.add(opGenerator);
                scheduleCommit();
            } else if (!z) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    public final void ensureExecReady(boolean z) {
        boolean z2;
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.mHost != null) {
            Looper myLooper = Looper.myLooper();
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
            Objects.requireNonNull(fragmentHostCallback);
            if (myLooper == fragmentHostCallback.mHandler.getLooper()) {
                if (!z) {
                    if (this.mStateSaved || this.mStopped) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
                    }
                }
                if (this.mTmpRecords == null) {
                    this.mTmpRecords = new ArrayList<>();
                    this.mTmpIsPop = new ArrayList<>();
                    return;
                }
                return;
            }
            throw new IllegalStateException("Must be called from main thread of fragment host");
        } else if (this.mDestroyed) {
            throw new IllegalStateException("FragmentManager has been destroyed");
        } else {
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        }
    }

    /* JADX INFO: finally extract failed */
    public final void execSingleAction(BackStackRecord backStackRecord, boolean z) {
        if (!z || (this.mHost != null && !this.mDestroyed)) {
            ensureExecReady(z);
            backStackRecord.generateOps(this.mTmpRecords, this.mTmpIsPop);
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                updateOnBackPressedCallbackEnabled();
                doPendingDeferredStart();
                FragmentStore fragmentStore = this.mFragmentStore;
                Objects.requireNonNull(fragmentStore);
                fragmentStore.mActive.values().removeAll(Collections.singleton((Object) null));
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0158, code lost:
        r6 = r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void executeOpsTogether(java.util.ArrayList<androidx.fragment.app.BackStackRecord> r18, java.util.ArrayList<java.lang.Boolean> r19, int r20, int r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            java.lang.Object r5 = r1.get(r3)
            androidx.fragment.app.BackStackRecord r5 = (androidx.fragment.app.BackStackRecord) r5
            boolean r5 = r5.mReorderingAllowed
            java.util.ArrayList<androidx.fragment.app.Fragment> r6 = r0.mTmpAddedFragments
            if (r6 != 0) goto L_0x001e
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r0.mTmpAddedFragments = r6
            goto L_0x0021
        L_0x001e:
            r6.clear()
        L_0x0021:
            java.util.ArrayList<androidx.fragment.app.Fragment> r6 = r0.mTmpAddedFragments
            androidx.fragment.app.FragmentStore r7 = r0.mFragmentStore
            java.util.List r7 = r7.getFragments()
            r6.addAll(r7)
            androidx.fragment.app.Fragment r6 = r0.mPrimaryNav
            r7 = 0
            r8 = r3
        L_0x0030:
            r9 = 1
            if (r8 >= r4) goto L_0x0180
            java.lang.Object r10 = r1.get(r8)
            androidx.fragment.app.BackStackRecord r10 = (androidx.fragment.app.BackStackRecord) r10
            java.lang.Object r11 = r2.get(r8)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            r12 = 3
            if (r11 != 0) goto L_0x012e
            java.util.ArrayList<androidx.fragment.app.Fragment> r11 = r0.mTmpAddedFragments
            java.util.Objects.requireNonNull(r10)
            r13 = 0
        L_0x004c:
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r14 = r10.mOps
            int r14 = r14.size()
            if (r13 >= r14) goto L_0x0169
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r14 = r10.mOps
            java.lang.Object r14 = r14.get(r13)
            androidx.fragment.app.FragmentTransaction$Op r14 = (androidx.fragment.app.FragmentTransaction.C0192Op) r14
            int r15 = r14.mCmd
            if (r15 == r9) goto L_0x011d
            r9 = 2
            r3 = 9
            if (r15 == r9) goto L_0x00a4
            if (r15 == r12) goto L_0x008a
            r9 = 6
            if (r15 == r9) goto L_0x008a
            r9 = 7
            if (r15 == r9) goto L_0x0087
            r9 = 8
            if (r15 == r9) goto L_0x0073
            goto L_0x0111
        L_0x0073:
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r9 = r10.mOps
            androidx.fragment.app.FragmentTransaction$Op r12 = new androidx.fragment.app.FragmentTransaction$Op
            r15 = 0
            r12.<init>(r3, r6, r15)
            r9.add(r13, r12)
            r3 = 1
            r14.mFromExpandedOp = r3
            int r13 = r13 + 1
            androidx.fragment.app.Fragment r6 = r14.mFragment
            goto L_0x0111
        L_0x0087:
            r9 = 1
            goto L_0x011d
        L_0x008a:
            androidx.fragment.app.Fragment r9 = r14.mFragment
            r11.remove(r9)
            androidx.fragment.app.Fragment r9 = r14.mFragment
            if (r9 != r6) goto L_0x0111
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r6 = r10.mOps
            androidx.fragment.app.FragmentTransaction$Op r12 = new androidx.fragment.app.FragmentTransaction$Op
            r12.<init>(r3, r9)
            r6.add(r13, r12)
            int r13 = r13 + 1
            r3 = 1
            r6 = 0
            r9 = r3
            goto L_0x0122
        L_0x00a4:
            androidx.fragment.app.Fragment r3 = r14.mFragment
            int r9 = r3.mContainerId
            int r12 = r11.size()
            int r12 = r12 + -1
            r15 = 0
        L_0x00af:
            if (r12 < 0) goto L_0x0108
            java.lang.Object r16 = r11.get(r12)
            r2 = r16
            androidx.fragment.app.Fragment r2 = (androidx.fragment.app.Fragment) r2
            int r1 = r2.mContainerId
            if (r1 != r9) goto L_0x00fb
            if (r2 != r3) goto L_0x00c2
            r1 = 1
            r15 = r1
            goto L_0x00fb
        L_0x00c2:
            if (r2 != r6) goto L_0x00d7
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r1 = r10.mOps
            androidx.fragment.app.FragmentTransaction$Op r6 = new androidx.fragment.app.FragmentTransaction$Op
            r16 = r9
            r9 = 0
            r4 = 9
            r6.<init>(r4, r2, r9)
            r1.add(r13, r6)
            int r13 = r13 + 1
            r6 = 0
            goto L_0x00da
        L_0x00d7:
            r16 = r9
            r9 = 0
        L_0x00da:
            androidx.fragment.app.FragmentTransaction$Op r1 = new androidx.fragment.app.FragmentTransaction$Op
            r4 = 3
            r1.<init>(r4, r2, r9)
            int r4 = r14.mEnterAnim
            r1.mEnterAnim = r4
            int r4 = r14.mPopEnterAnim
            r1.mPopEnterAnim = r4
            int r4 = r14.mExitAnim
            r1.mExitAnim = r4
            int r4 = r14.mPopExitAnim
            r1.mPopExitAnim = r4
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r4 = r10.mOps
            r4.add(r13, r1)
            r11.remove(r2)
            int r13 = r13 + 1
            goto L_0x00fd
        L_0x00fb:
            r16 = r9
        L_0x00fd:
            int r12 = r12 + -1
            r1 = r18
            r2 = r19
            r4 = r21
            r9 = r16
            goto L_0x00af
        L_0x0108:
            if (r15 == 0) goto L_0x0114
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r1 = r10.mOps
            r1.remove(r13)
            int r13 = r13 + -1
        L_0x0111:
            r1 = 1
        L_0x0112:
            r9 = r1
            goto L_0x0122
        L_0x0114:
            r1 = 1
            r14.mCmd = r1
            r14.mFromExpandedOp = r1
            r11.add(r3)
            goto L_0x0112
        L_0x011d:
            androidx.fragment.app.Fragment r1 = r14.mFragment
            r11.add(r1)
        L_0x0122:
            int r13 = r13 + r9
            r12 = 3
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            goto L_0x004c
        L_0x012e:
            java.util.ArrayList<androidx.fragment.app.Fragment> r1 = r0.mTmpAddedFragments
            java.util.Objects.requireNonNull(r10)
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r2 = r10.mOps
            int r2 = r2.size()
            int r2 = r2 - r9
        L_0x013a:
            if (r2 < 0) goto L_0x0169
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r3 = r10.mOps
            java.lang.Object r3 = r3.get(r2)
            androidx.fragment.app.FragmentTransaction$Op r3 = (androidx.fragment.app.FragmentTransaction.C0192Op) r3
            int r4 = r3.mCmd
            if (r4 == r9) goto L_0x0160
            r9 = 3
            if (r4 == r9) goto L_0x015a
            switch(r4) {
                case 6: goto L_0x015a;
                case 7: goto L_0x0160;
                case 8: goto L_0x0157;
                case 9: goto L_0x0154;
                case 10: goto L_0x014f;
                default: goto L_0x014e;
            }
        L_0x014e:
            goto L_0x0165
        L_0x014f:
            androidx.lifecycle.Lifecycle$State r4 = r3.mOldMaxState
            r3.mCurrentMaxState = r4
            goto L_0x0165
        L_0x0154:
            androidx.fragment.app.Fragment r3 = r3.mFragment
            goto L_0x0158
        L_0x0157:
            r3 = 0
        L_0x0158:
            r6 = r3
            goto L_0x0165
        L_0x015a:
            androidx.fragment.app.Fragment r3 = r3.mFragment
            r1.add(r3)
            goto L_0x0165
        L_0x0160:
            androidx.fragment.app.Fragment r3 = r3.mFragment
            r1.remove(r3)
        L_0x0165:
            int r2 = r2 + -1
            r9 = 1
            goto L_0x013a
        L_0x0169:
            if (r7 != 0) goto L_0x0172
            boolean r1 = r10.mAddToBackStack
            if (r1 == 0) goto L_0x0170
            goto L_0x0172
        L_0x0170:
            r1 = 0
            goto L_0x0173
        L_0x0172:
            r1 = 1
        L_0x0173:
            r7 = r1
            int r8 = r8 + 1
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            goto L_0x0030
        L_0x0180:
            java.util.ArrayList<androidx.fragment.app.Fragment> r1 = r0.mTmpAddedFragments
            r1.clear()
            if (r5 != 0) goto L_0x01c1
            int r1 = r0.mCurState
            r2 = 1
            if (r1 < r2) goto L_0x01c1
            r2 = r20
            r1 = r21
        L_0x0190:
            r3 = r18
            if (r2 >= r1) goto L_0x01c5
            java.lang.Object r4 = r3.get(r2)
            androidx.fragment.app.BackStackRecord r4 = (androidx.fragment.app.BackStackRecord) r4
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r4 = r4.mOps
            java.util.Iterator r4 = r4.iterator()
        L_0x01a0:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x01be
            java.lang.Object r5 = r4.next()
            androidx.fragment.app.FragmentTransaction$Op r5 = (androidx.fragment.app.FragmentTransaction.C0192Op) r5
            androidx.fragment.app.Fragment r5 = r5.mFragment
            if (r5 == 0) goto L_0x01a0
            androidx.fragment.app.FragmentManager r6 = r5.mFragmentManager
            if (r6 == 0) goto L_0x01a0
            androidx.fragment.app.FragmentStateManager r5 = r0.createOrGetFragmentStateManager(r5)
            androidx.fragment.app.FragmentStore r6 = r0.mFragmentStore
            r6.makeActive(r5)
            goto L_0x01a0
        L_0x01be:
            int r2 = r2 + 1
            goto L_0x0190
        L_0x01c1:
            r3 = r18
            r1 = r21
        L_0x01c5:
            r2 = r20
        L_0x01c7:
            if (r2 >= r1) goto L_0x03e1
            java.lang.Object r4 = r3.get(r2)
            androidx.fragment.app.BackStackRecord r4 = (androidx.fragment.app.BackStackRecord) r4
            r5 = r19
            java.lang.Object r6 = r5.get(r2)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            java.lang.String r7 = "Unknown cmd: "
            if (r6 == 0) goto L_0x02ed
            r6 = -1
            r4.bumpBackStackNesting(r6)
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r6 = r4.mOps
            int r6 = r6.size()
            int r6 = r6 + -1
        L_0x01eb:
            if (r6 < 0) goto L_0x03dd
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r8 = r4.mOps
            java.lang.Object r8 = r8.get(r6)
            androidx.fragment.app.FragmentTransaction$Op r8 = (androidx.fragment.app.FragmentTransaction.C0192Op) r8
            androidx.fragment.app.Fragment r9 = r8.mFragment
            if (r9 == 0) goto L_0x023f
            androidx.fragment.app.Fragment$AnimationInfo r10 = r9.mAnimationInfo
            if (r10 != 0) goto L_0x01fe
            goto L_0x0205
        L_0x01fe:
            androidx.fragment.app.Fragment$AnimationInfo r10 = r9.ensureAnimationInfo()
            r11 = 1
            r10.mIsPop = r11
        L_0x0205:
            int r10 = r4.mTransition
            r11 = 4100(0x1004, float:5.745E-42)
            r12 = 4099(0x1003, float:5.744E-42)
            r13 = 8197(0x2005, float:1.1486E-41)
            r14 = 8194(0x2002, float:1.1482E-41)
            r15 = 4097(0x1001, float:5.741E-42)
            if (r10 == r15) goto L_0x0223
            if (r10 == r14) goto L_0x0221
            if (r10 == r13) goto L_0x0224
            if (r10 == r12) goto L_0x021f
            if (r10 == r11) goto L_0x021d
            r11 = 0
            goto L_0x0224
        L_0x021d:
            r11 = r13
            goto L_0x0224
        L_0x021f:
            r11 = r12
            goto L_0x0224
        L_0x0221:
            r11 = r15
            goto L_0x0224
        L_0x0223:
            r11 = r14
        L_0x0224:
            androidx.fragment.app.Fragment$AnimationInfo r10 = r9.mAnimationInfo
            if (r10 != 0) goto L_0x022b
            if (r11 != 0) goto L_0x022b
            goto L_0x0232
        L_0x022b:
            r9.ensureAnimationInfo()
            androidx.fragment.app.Fragment$AnimationInfo r10 = r9.mAnimationInfo
            r10.mNextTransition = r11
        L_0x0232:
            java.util.ArrayList<java.lang.String> r10 = r4.mSharedElementTargetNames
            java.util.ArrayList<java.lang.String> r11 = r4.mSharedElementSourceNames
            r9.ensureAnimationInfo()
            androidx.fragment.app.Fragment$AnimationInfo r12 = r9.mAnimationInfo
            r12.mSharedElementSourceNames = r10
            r12.mSharedElementTargetNames = r11
        L_0x023f:
            int r10 = r8.mCmd
            switch(r10) {
                case 1: goto L_0x02d3;
                case 2: goto L_0x0244;
                case 3: goto L_0x02c2;
                case 4: goto L_0x02ae;
                case 5: goto L_0x0297;
                case 6: goto L_0x0286;
                case 7: goto L_0x026f;
                case 8: goto L_0x0267;
                case 9: goto L_0x0260;
                case 10: goto L_0x0257;
                default: goto L_0x0244;
            }
        L_0x0244:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r7)
            int r2 = r8.mCmd
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0257:
            androidx.fragment.app.FragmentManager r10 = r4.mManager
            androidx.lifecycle.Lifecycle$State r8 = r8.mOldMaxState
            r10.setMaxLifecycle(r9, r8)
            goto L_0x02e9
        L_0x0260:
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r8.setPrimaryNavigationFragment(r9)
            goto L_0x02e9
        L_0x0267:
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r9 = 0
            r8.setPrimaryNavigationFragment(r9)
            goto L_0x02e9
        L_0x026f:
            int r10 = r8.mEnterAnim
            int r11 = r8.mExitAnim
            int r12 = r8.mPopEnterAnim
            int r8 = r8.mPopExitAnim
            r9.setAnimations(r10, r11, r12, r8)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r10 = 1
            r8.setExitAnimationOrder(r9, r10)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r8.detachFragment(r9)
            goto L_0x02e9
        L_0x0286:
            int r10 = r8.mEnterAnim
            int r11 = r8.mExitAnim
            int r12 = r8.mPopEnterAnim
            int r8 = r8.mPopExitAnim
            r9.setAnimations(r10, r11, r12, r8)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r8.attachFragment(r9)
            goto L_0x02e9
        L_0x0297:
            int r10 = r8.mEnterAnim
            int r11 = r8.mExitAnim
            int r12 = r8.mPopEnterAnim
            int r8 = r8.mPopExitAnim
            r9.setAnimations(r10, r11, r12, r8)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r10 = 1
            r8.setExitAnimationOrder(r9, r10)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r8.hideFragment(r9)
            goto L_0x02e9
        L_0x02ae:
            int r10 = r8.mEnterAnim
            int r11 = r8.mExitAnim
            int r12 = r8.mPopEnterAnim
            int r8 = r8.mPopExitAnim
            r9.setAnimations(r10, r11, r12, r8)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            java.util.Objects.requireNonNull(r8)
            showFragment(r9)
            goto L_0x02e9
        L_0x02c2:
            int r10 = r8.mEnterAnim
            int r11 = r8.mExitAnim
            int r12 = r8.mPopEnterAnim
            int r8 = r8.mPopExitAnim
            r9.setAnimations(r10, r11, r12, r8)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r8.addFragment(r9)
            goto L_0x02e9
        L_0x02d3:
            int r10 = r8.mEnterAnim
            int r11 = r8.mExitAnim
            int r12 = r8.mPopEnterAnim
            int r8 = r8.mPopExitAnim
            r9.setAnimations(r10, r11, r12, r8)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r10 = 1
            r8.setExitAnimationOrder(r9, r10)
            androidx.fragment.app.FragmentManager r8 = r4.mManager
            r8.removeFragment(r9)
        L_0x02e9:
            int r6 = r6 + -1
            goto L_0x01eb
        L_0x02ed:
            r6 = 1
            r4.bumpBackStackNesting(r6)
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r6 = r4.mOps
            int r6 = r6.size()
            r8 = 0
        L_0x02f8:
            if (r8 >= r6) goto L_0x03dd
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r9 = r4.mOps
            java.lang.Object r9 = r9.get(r8)
            androidx.fragment.app.FragmentTransaction$Op r9 = (androidx.fragment.app.FragmentTransaction.C0192Op) r9
            androidx.fragment.app.Fragment r10 = r9.mFragment
            if (r10 == 0) goto L_0x032f
            androidx.fragment.app.Fragment$AnimationInfo r11 = r10.mAnimationInfo
            if (r11 != 0) goto L_0x030b
            goto L_0x0312
        L_0x030b:
            androidx.fragment.app.Fragment$AnimationInfo r11 = r10.ensureAnimationInfo()
            r12 = 0
            r11.mIsPop = r12
        L_0x0312:
            int r11 = r4.mTransition
            androidx.fragment.app.Fragment$AnimationInfo r12 = r10.mAnimationInfo
            if (r12 != 0) goto L_0x031b
            if (r11 != 0) goto L_0x031b
            goto L_0x0322
        L_0x031b:
            r10.ensureAnimationInfo()
            androidx.fragment.app.Fragment$AnimationInfo r12 = r10.mAnimationInfo
            r12.mNextTransition = r11
        L_0x0322:
            java.util.ArrayList<java.lang.String> r11 = r4.mSharedElementSourceNames
            java.util.ArrayList<java.lang.String> r12 = r4.mSharedElementTargetNames
            r10.ensureAnimationInfo()
            androidx.fragment.app.Fragment$AnimationInfo r13 = r10.mAnimationInfo
            r13.mSharedElementSourceNames = r11
            r13.mSharedElementTargetNames = r12
        L_0x032f:
            int r11 = r9.mCmd
            switch(r11) {
                case 1: goto L_0x03c3;
                case 2: goto L_0x0334;
                case 3: goto L_0x03b2;
                case 4: goto L_0x03a1;
                case 5: goto L_0x0387;
                case 6: goto L_0x0376;
                case 7: goto L_0x035f;
                case 8: goto L_0x0358;
                case 9: goto L_0x0350;
                case 10: goto L_0x0347;
                default: goto L_0x0334;
            }
        L_0x0334:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r7)
            int r2 = r9.mCmd
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0347:
            androidx.fragment.app.FragmentManager r11 = r4.mManager
            androidx.lifecycle.Lifecycle$State r9 = r9.mCurrentMaxState
            r11.setMaxLifecycle(r10, r9)
            goto L_0x03d9
        L_0x0350:
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r10 = 0
            r9.setPrimaryNavigationFragment(r10)
            goto L_0x03d9
        L_0x0358:
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r9.setPrimaryNavigationFragment(r10)
            goto L_0x03d9
        L_0x035f:
            int r11 = r9.mEnterAnim
            int r12 = r9.mExitAnim
            int r13 = r9.mPopEnterAnim
            int r9 = r9.mPopExitAnim
            r10.setAnimations(r11, r12, r13, r9)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r11 = 0
            r9.setExitAnimationOrder(r10, r11)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r9.attachFragment(r10)
            goto L_0x03d9
        L_0x0376:
            int r11 = r9.mEnterAnim
            int r12 = r9.mExitAnim
            int r13 = r9.mPopEnterAnim
            int r9 = r9.mPopExitAnim
            r10.setAnimations(r11, r12, r13, r9)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r9.detachFragment(r10)
            goto L_0x03d9
        L_0x0387:
            int r11 = r9.mEnterAnim
            int r12 = r9.mExitAnim
            int r13 = r9.mPopEnterAnim
            int r9 = r9.mPopExitAnim
            r10.setAnimations(r11, r12, r13, r9)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r11 = 0
            r9.setExitAnimationOrder(r10, r11)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            java.util.Objects.requireNonNull(r9)
            showFragment(r10)
            goto L_0x03d9
        L_0x03a1:
            int r11 = r9.mEnterAnim
            int r12 = r9.mExitAnim
            int r13 = r9.mPopEnterAnim
            int r9 = r9.mPopExitAnim
            r10.setAnimations(r11, r12, r13, r9)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r9.hideFragment(r10)
            goto L_0x03d9
        L_0x03b2:
            int r11 = r9.mEnterAnim
            int r12 = r9.mExitAnim
            int r13 = r9.mPopEnterAnim
            int r9 = r9.mPopExitAnim
            r10.setAnimations(r11, r12, r13, r9)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r9.removeFragment(r10)
            goto L_0x03d9
        L_0x03c3:
            int r11 = r9.mEnterAnim
            int r12 = r9.mExitAnim
            int r13 = r9.mPopEnterAnim
            int r9 = r9.mPopExitAnim
            r10.setAnimations(r11, r12, r13, r9)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r11 = 0
            r9.setExitAnimationOrder(r10, r11)
            androidx.fragment.app.FragmentManager r9 = r4.mManager
            r9.addFragment(r10)
        L_0x03d9:
            int r8 = r8 + 1
            goto L_0x02f8
        L_0x03dd:
            int r2 = r2 + 1
            goto L_0x01c7
        L_0x03e1:
            r5 = r19
            int r2 = r1 + -1
            java.lang.Object r2 = r5.get(r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            r4 = r20
        L_0x03f1:
            if (r4 >= r1) goto L_0x043c
            java.lang.Object r6 = r3.get(r4)
            androidx.fragment.app.BackStackRecord r6 = (androidx.fragment.app.BackStackRecord) r6
            if (r2 == 0) goto L_0x041b
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r7 = r6.mOps
            int r7 = r7.size()
            int r7 = r7 + -1
        L_0x0403:
            if (r7 < 0) goto L_0x0439
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r8 = r6.mOps
            java.lang.Object r8 = r8.get(r7)
            androidx.fragment.app.FragmentTransaction$Op r8 = (androidx.fragment.app.FragmentTransaction.C0192Op) r8
            androidx.fragment.app.Fragment r8 = r8.mFragment
            if (r8 == 0) goto L_0x0418
            androidx.fragment.app.FragmentStateManager r8 = r0.createOrGetFragmentStateManager(r8)
            r8.moveToExpectedState()
        L_0x0418:
            int r7 = r7 + -1
            goto L_0x0403
        L_0x041b:
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r6 = r6.mOps
            java.util.Iterator r6 = r6.iterator()
        L_0x0421:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0439
            java.lang.Object r7 = r6.next()
            androidx.fragment.app.FragmentTransaction$Op r7 = (androidx.fragment.app.FragmentTransaction.C0192Op) r7
            androidx.fragment.app.Fragment r7 = r7.mFragment
            if (r7 == 0) goto L_0x0421
            androidx.fragment.app.FragmentStateManager r7 = r0.createOrGetFragmentStateManager(r7)
            r7.moveToExpectedState()
            goto L_0x0421
        L_0x0439:
            int r4 = r4 + 1
            goto L_0x03f1
        L_0x043c:
            int r4 = r0.mCurState
            r6 = 1
            r0.moveToState(r4, r6)
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            r6 = r20
        L_0x0449:
            if (r6 >= r1) goto L_0x047a
            java.lang.Object r7 = r3.get(r6)
            androidx.fragment.app.BackStackRecord r7 = (androidx.fragment.app.BackStackRecord) r7
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r7 = r7.mOps
            java.util.Iterator r7 = r7.iterator()
        L_0x0457:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0477
            java.lang.Object r8 = r7.next()
            androidx.fragment.app.FragmentTransaction$Op r8 = (androidx.fragment.app.FragmentTransaction.C0192Op) r8
            androidx.fragment.app.Fragment r8 = r8.mFragment
            if (r8 == 0) goto L_0x0457
            android.view.ViewGroup r8 = r8.mContainer
            if (r8 == 0) goto L_0x0457
            androidx.fragment.app.SpecialEffectsControllerFactory r9 = r17.getSpecialEffectsControllerFactory()
            androidx.fragment.app.SpecialEffectsController r8 = androidx.fragment.app.SpecialEffectsController.getOrCreateController(r8, r9)
            r4.add(r8)
            goto L_0x0457
        L_0x0477:
            int r6 = r6 + 1
            goto L_0x0449
        L_0x047a:
            java.util.Iterator r0 = r4.iterator()
        L_0x047e:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x0496
            java.lang.Object r4 = r0.next()
            androidx.fragment.app.SpecialEffectsController r4 = (androidx.fragment.app.SpecialEffectsController) r4
            java.util.Objects.requireNonNull(r4)
            r4.mOperationDirectionIsPop = r2
            r4.markPostponedState()
            r4.executePendingOperations()
            goto L_0x047e
        L_0x0496:
            r0 = r20
        L_0x0498:
            if (r0 >= r1) goto L_0x04b9
            java.lang.Object r2 = r3.get(r0)
            androidx.fragment.app.BackStackRecord r2 = (androidx.fragment.app.BackStackRecord) r2
            java.lang.Object r4 = r5.get(r0)
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 == 0) goto L_0x04b3
            int r4 = r2.mIndex
            if (r4 < 0) goto L_0x04b3
            r4 = -1
            r2.mIndex = r4
        L_0x04b3:
            java.util.Objects.requireNonNull(r2)
            int r0 = r0 + 1
            goto L_0x0498
        L_0x04b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.executeOpsTogether(java.util.ArrayList, java.util.ArrayList, int, int):void");
    }

    public final Fragment findActiveFragment(String str) {
        return this.mFragmentStore.findActiveFragment(str);
    }

    public final Fragment findFragmentById(int i) {
        FragmentStore fragmentStore = this.mFragmentStore;
        Objects.requireNonNull(fragmentStore);
        int size = fragmentStore.mAdded.size();
        while (true) {
            size--;
            if (size >= 0) {
                Fragment fragment = fragmentStore.mAdded.get(size);
                if (fragment != null && fragment.mFragmentId == i) {
                    return fragment;
                }
            } else {
                for (FragmentStateManager next : fragmentStore.mActive.values()) {
                    if (next != null) {
                        Fragment fragment2 = next.mFragment;
                        if (fragment2.mFragmentId == i) {
                            return fragment2;
                        }
                    }
                }
                return null;
            }
        }
    }

    public final Fragment findFragmentByTag(String str) {
        FragmentStore fragmentStore = this.mFragmentStore;
        int size = fragmentStore.mAdded.size();
        while (true) {
            size--;
            if (size >= 0) {
                Fragment fragment = fragmentStore.mAdded.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            } else {
                for (FragmentStateManager next : fragmentStore.mActive.values()) {
                    if (next != null) {
                        Fragment fragment2 = next.mFragment;
                        if (str.equals(fragment2.mTag)) {
                            return fragment2;
                        }
                    }
                }
                return null;
            }
        }
    }

    public final ViewGroup getFragmentContainer(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.mContainerId > 0 && this.mContainer.onHasView()) {
            View onFindViewById = this.mContainer.onFindViewById(fragment.mContainerId);
            if (onFindViewById instanceof ViewGroup) {
                return (ViewGroup) onFindViewById;
            }
        }
        return null;
    }

    public final FragmentFactory getFragmentFactory() {
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getFragmentFactory();
        }
        return this.mHostFragmentFactory;
    }

    public final SpecialEffectsControllerFactory getSpecialEffectsControllerFactory() {
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getSpecialEffectsControllerFactory();
        }
        return this.mDefaultSpecialEffectsControllerFactory;
    }

    public final void moveToState(int i, boolean z) {
        FragmentHostCallback<?> fragmentHostCallback;
        boolean z2;
        if (this.mHost == null && i != -1) {
            throw new IllegalStateException("No activity");
        } else if (z || i != this.mCurState) {
            this.mCurState = i;
            FragmentStore fragmentStore = this.mFragmentStore;
            Objects.requireNonNull(fragmentStore);
            Iterator<Fragment> it = fragmentStore.mAdded.iterator();
            while (it.hasNext()) {
                FragmentStateManager fragmentStateManager = fragmentStore.mActive.get(it.next().mWho);
                if (fragmentStateManager != null) {
                    fragmentStateManager.moveToExpectedState();
                }
            }
            Iterator<FragmentStateManager> it2 = fragmentStore.mActive.values().iterator();
            while (true) {
                boolean z3 = false;
                if (!it2.hasNext()) {
                    break;
                }
                FragmentStateManager next = it2.next();
                if (next != null) {
                    next.moveToExpectedState();
                    Fragment fragment = next.mFragment;
                    if (fragment.mRemoving) {
                        if (fragment.mBackStackNesting > 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            z3 = true;
                        }
                    }
                    if (z3) {
                        fragmentStore.makeInactive(next);
                    }
                }
            }
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    public final void noteStateNotSaved() {
        if (this.mHost != null) {
            this.mStateSaved = false;
            this.mStopped = false;
            FragmentManagerViewModel fragmentManagerViewModel = this.mNonConfig;
            Objects.requireNonNull(fragmentManagerViewModel);
            fragmentManagerViewModel.mIsStateSaved = false;
            for (Fragment next : this.mFragmentStore.getFragments()) {
                if (next != null) {
                    next.mChildFragmentManager.noteStateNotSaved();
                }
            }
        }
    }

    public final void restoreSaveStateInternal(Parcelable parcelable) {
        FragmentManagerState fragmentManagerState;
        ArrayList<FragmentState> arrayList;
        int i;
        boolean z;
        FragmentStateManager fragmentStateManager;
        if (parcelable != null && (arrayList = fragmentManagerState.mSavedState) != null) {
            FragmentStore fragmentStore = this.mFragmentStore;
            Objects.requireNonNull(fragmentStore);
            fragmentStore.mSavedState.clear();
            Iterator<FragmentState> it = arrayList.iterator();
            while (it.hasNext()) {
                FragmentState next = it.next();
                fragmentStore.mSavedState.put(next.mWho, next);
            }
            FragmentStore fragmentStore2 = this.mFragmentStore;
            Objects.requireNonNull(fragmentStore2);
            fragmentStore2.mActive.clear();
            Iterator<String> it2 = (fragmentManagerState = (FragmentManagerState) parcelable).mActive.iterator();
            while (it2.hasNext()) {
                FragmentState savedState = this.mFragmentStore.setSavedState(it2.next(), (FragmentState) null);
                if (savedState != null) {
                    FragmentManagerViewModel fragmentManagerViewModel = this.mNonConfig;
                    String str = savedState.mWho;
                    Objects.requireNonNull(fragmentManagerViewModel);
                    Fragment fragment = fragmentManagerViewModel.mRetainedFragments.get(str);
                    if (fragment != null) {
                        if (isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + fragment);
                        }
                        fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment, savedState);
                    } else {
                        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mLifecycleCallbacksDispatcher;
                        FragmentStore fragmentStore3 = this.mFragmentStore;
                        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
                        Objects.requireNonNull(fragmentHostCallback);
                        fragmentStateManager = new FragmentStateManager(fragmentLifecycleCallbacksDispatcher, fragmentStore3, fragmentHostCallback.mContext.getClassLoader(), getFragmentFactory(), savedState);
                    }
                    Fragment fragment2 = fragmentStateManager.mFragment;
                    fragment2.mFragmentManager = this;
                    if (isLoggingEnabled(2)) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("restoreSaveState: active (");
                        m.append(fragment2.mWho);
                        m.append("): ");
                        m.append(fragment2);
                        Log.v("FragmentManager", m.toString());
                    }
                    FragmentHostCallback<?> fragmentHostCallback2 = this.mHost;
                    Objects.requireNonNull(fragmentHostCallback2);
                    fragmentStateManager.restoreState(fragmentHostCallback2.mContext.getClassLoader());
                    this.mFragmentStore.makeActive(fragmentStateManager);
                    fragmentStateManager.mFragmentManagerState = this.mCurState;
                }
            }
            FragmentManagerViewModel fragmentManagerViewModel2 = this.mNonConfig;
            Objects.requireNonNull(fragmentManagerViewModel2);
            Iterator it3 = new ArrayList(fragmentManagerViewModel2.mRetainedFragments.values()).iterator();
            while (true) {
                i = 0;
                if (!it3.hasNext()) {
                    break;
                }
                Fragment fragment3 = (Fragment) it3.next();
                FragmentStore fragmentStore4 = this.mFragmentStore;
                String str2 = fragment3.mWho;
                Objects.requireNonNull(fragmentStore4);
                if (fragmentStore4.mActive.get(str2) != null) {
                    i = 1;
                }
                if (i == 0) {
                    if (isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "Discarding retained Fragment " + fragment3 + " that was not found in the set of active Fragments " + fragmentManagerState.mActive);
                    }
                    this.mNonConfig.removeRetainedFragment(fragment3);
                    fragment3.mFragmentManager = this;
                    FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment3);
                    fragmentStateManager2.mFragmentManagerState = 1;
                    fragmentStateManager2.moveToExpectedState();
                    fragment3.mRemoving = true;
                    fragmentStateManager2.moveToExpectedState();
                }
            }
            FragmentStore fragmentStore5 = this.mFragmentStore;
            ArrayList<String> arrayList2 = fragmentManagerState.mAdded;
            Objects.requireNonNull(fragmentStore5);
            fragmentStore5.mAdded.clear();
            if (arrayList2 != null) {
                for (String next2 : arrayList2) {
                    Fragment findActiveFragment = fragmentStore5.findActiveFragment(next2);
                    if (findActiveFragment != null) {
                        if (isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "restoreSaveState: added (" + next2 + "): " + findActiveFragment);
                        }
                        fragmentStore5.addFragment(findActiveFragment);
                    } else {
                        throw new IllegalStateException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("No instantiated fragment for (", next2, ")"));
                    }
                }
            }
            if (fragmentManagerState.mBackStack != null) {
                this.mBackStack = new ArrayList<>(fragmentManagerState.mBackStack.length);
                int i2 = 0;
                while (true) {
                    BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.mBackStack;
                    if (i2 >= backStackRecordStateArr.length) {
                        break;
                    }
                    BackStackRecordState backStackRecordState = backStackRecordStateArr[i2];
                    Objects.requireNonNull(backStackRecordState);
                    BackStackRecord backStackRecord = new BackStackRecord(this);
                    int i3 = 0;
                    int i4 = 0;
                    while (true) {
                        int[] iArr = backStackRecordState.mOps;
                        if (i3 >= iArr.length) {
                            break;
                        }
                        FragmentTransaction.C0192Op op = new FragmentTransaction.C0192Op();
                        int i5 = i3 + 1;
                        op.mCmd = iArr[i3];
                        if (isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Instantiate " + backStackRecord + " op #" + i4 + " base fragment #" + backStackRecordState.mOps[i5]);
                        }
                        op.mOldMaxState = Lifecycle.State.values()[backStackRecordState.mOldMaxLifecycleStates[i4]];
                        op.mCurrentMaxState = Lifecycle.State.values()[backStackRecordState.mCurrentMaxLifecycleStates[i4]];
                        int[] iArr2 = backStackRecordState.mOps;
                        int i6 = i5 + 1;
                        if (iArr2[i5] != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        op.mFromExpandedOp = z;
                        int i7 = i6 + 1;
                        int i8 = iArr2[i6];
                        op.mEnterAnim = i8;
                        int i9 = i7 + 1;
                        int i10 = iArr2[i7];
                        op.mExitAnim = i10;
                        int i11 = i9 + 1;
                        int i12 = iArr2[i9];
                        op.mPopEnterAnim = i12;
                        int i13 = iArr2[i11];
                        op.mPopExitAnim = i13;
                        backStackRecord.mEnterAnim = i8;
                        backStackRecord.mExitAnim = i10;
                        backStackRecord.mPopEnterAnim = i12;
                        backStackRecord.mPopExitAnim = i13;
                        backStackRecord.addOp(op);
                        i4++;
                        i3 = i11 + 1;
                    }
                    backStackRecord.mTransition = backStackRecordState.mTransition;
                    backStackRecord.mName = backStackRecordState.mName;
                    backStackRecord.mAddToBackStack = true;
                    backStackRecord.mBreadCrumbTitleRes = backStackRecordState.mBreadCrumbTitleRes;
                    backStackRecord.mBreadCrumbTitleText = backStackRecordState.mBreadCrumbTitleText;
                    backStackRecord.mBreadCrumbShortTitleRes = backStackRecordState.mBreadCrumbShortTitleRes;
                    backStackRecord.mBreadCrumbShortTitleText = backStackRecordState.mBreadCrumbShortTitleText;
                    backStackRecord.mSharedElementSourceNames = backStackRecordState.mSharedElementSourceNames;
                    backStackRecord.mSharedElementTargetNames = backStackRecordState.mSharedElementTargetNames;
                    backStackRecord.mReorderingAllowed = backStackRecordState.mReorderingAllowed;
                    backStackRecord.mIndex = backStackRecordState.mIndex;
                    for (int i14 = 0; i14 < backStackRecordState.mFragmentWhos.size(); i14++) {
                        String str3 = backStackRecordState.mFragmentWhos.get(i14);
                        if (str3 != null) {
                            backStackRecord.mOps.get(i14).mFragment = findActiveFragment(str3);
                        }
                    }
                    backStackRecord.bumpBackStackNesting(1);
                    if (isLoggingEnabled(2)) {
                        StringBuilder m2 = ExifInterface$$ExternalSyntheticOutline0.m13m("restoreAllState: back stack #", i2, " (index ");
                        m2.append(backStackRecord.mIndex);
                        m2.append("): ");
                        m2.append(backStackRecord);
                        Log.v("FragmentManager", m2.toString());
                        PrintWriter printWriter = new PrintWriter(new LogWriter());
                        backStackRecord.dump("  ", printWriter, false);
                        printWriter.close();
                    }
                    this.mBackStack.add(backStackRecord);
                    i2++;
                }
            } else {
                this.mBackStack = null;
            }
            this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
            String str4 = fragmentManagerState.mPrimaryNavActiveWho;
            if (str4 != null) {
                Fragment findActiveFragment2 = findActiveFragment(str4);
                this.mPrimaryNav = findActiveFragment2;
                dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment2);
            }
            ArrayList<String> arrayList3 = fragmentManagerState.mBackStackStateKeys;
            if (arrayList3 != null) {
                for (int i15 = 0; i15 < arrayList3.size(); i15++) {
                    this.mBackStackStates.put(arrayList3.get(i15), fragmentManagerState.mBackStackStates.get(i15));
                }
            }
            ArrayList<String> arrayList4 = fragmentManagerState.mResultKeys;
            if (arrayList4 != null) {
                while (i < arrayList4.size()) {
                    Bundle bundle = fragmentManagerState.mResults.get(i);
                    FragmentHostCallback<?> fragmentHostCallback3 = this.mHost;
                    Objects.requireNonNull(fragmentHostCallback3);
                    bundle.setClassLoader(fragmentHostCallback3.mContext.getClassLoader());
                    this.mResults.put(arrayList4.get(i), bundle);
                    i++;
                }
            }
            this.mLaunchedFragments = new ArrayDeque<>(fragmentManagerState.mLaunchedFragments);
        }
    }

    public final void scheduleCommit() {
        synchronized (this.mPendingActions) {
            boolean z = true;
            if (this.mPendingActions.size() != 1) {
                z = false;
            }
            if (z) {
                FragmentHostCallback<?> fragmentHostCallback = this.mHost;
                Objects.requireNonNull(fragmentHostCallback);
                fragmentHostCallback.mHandler.removeCallbacks(this.mExecCommit);
                FragmentHostCallback<?> fragmentHostCallback2 = this.mHost;
                Objects.requireNonNull(fragmentHostCallback2);
                fragmentHostCallback2.mHandler.post(this.mExecCommit);
                updateOnBackPressedCallbackEnabled();
            }
        }
    }

    public final void setMaxLifecycle(Fragment fragment, Lifecycle.State state) {
        if (!fragment.equals(findActiveFragment(fragment.mWho)) || !(fragment.mHost == null || fragment.mFragmentManager == this)) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        fragment.mMaxState = state;
    }

    public final void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment == null || (fragment.equals(findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this))) {
            Fragment fragment2 = this.mPrimaryNav;
            this.mPrimaryNav = fragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragment2);
            dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    public final void startPendingDeferredFragments() {
        Iterator it = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = (FragmentStateManager) it.next();
            Objects.requireNonNull(fragmentStateManager);
            Fragment fragment = fragmentStateManager.mFragment;
            if (fragment.mDeferStart) {
                if (this.mExecutingActions) {
                    this.mHavePendingDeferredStart = true;
                } else {
                    fragment.mDeferStart = false;
                    fragmentStateManager.moveToExpectedState();
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append("}");
        } else {
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.mHost)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
        if (r1 == null) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        r1 = r1.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        if (r1 <= 0) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002b, code lost:
        if (isPrimaryNavigation(r4.mParent) == false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002f, code lost:
        java.util.Objects.requireNonNull(r0);
        r0.mEnabled = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
        r0 = r4.mOnBackPressedCallback;
        r1 = r4.mBackStack;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateOnBackPressedCallbackEnabled() {
        /*
            r4 = this;
            java.util.ArrayList<androidx.fragment.app.FragmentManager$OpGenerator> r0 = r4.mPendingActions
            monitor-enter(r0)
            java.util.ArrayList<androidx.fragment.app.FragmentManager$OpGenerator> r1 = r4.mPendingActions     // Catch:{ all -> 0x0035 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0035 }
            r2 = 1
            if (r1 != 0) goto L_0x0015
            androidx.fragment.app.FragmentManager$1 r4 = r4.mOnBackPressedCallback     // Catch:{ all -> 0x0035 }
            java.util.Objects.requireNonNull(r4)     // Catch:{ all -> 0x0035 }
            r4.mEnabled = r2     // Catch:{ all -> 0x0035 }
            monitor-exit(r0)     // Catch:{ all -> 0x0035 }
            return
        L_0x0015:
            monitor-exit(r0)     // Catch:{ all -> 0x0035 }
            androidx.fragment.app.FragmentManager$1 r0 = r4.mOnBackPressedCallback
            java.util.ArrayList<androidx.fragment.app.BackStackRecord> r1 = r4.mBackStack
            r3 = 0
            if (r1 == 0) goto L_0x0022
            int r1 = r1.size()
            goto L_0x0023
        L_0x0022:
            r1 = r3
        L_0x0023:
            if (r1 <= 0) goto L_0x002e
            androidx.fragment.app.Fragment r4 = r4.mParent
            boolean r4 = isPrimaryNavigation(r4)
            if (r4 == 0) goto L_0x002e
            goto L_0x002f
        L_0x002e:
            r2 = r3
        L_0x002f:
            java.util.Objects.requireNonNull(r0)
            r0.mEnabled = r2
            return
        L_0x0035:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0035 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.updateOnBackPressedCallbackEnabled():void");
    }

    /* JADX INFO: finally extract failed */
    public final boolean execPendingActions(boolean z) {
        boolean z2;
        ensureExecReady(z);
        boolean z3 = false;
        while (true) {
            ArrayList<BackStackRecord> arrayList = this.mTmpRecords;
            ArrayList<Boolean> arrayList2 = this.mTmpIsPop;
            synchronized (this.mPendingActions) {
                if (this.mPendingActions.isEmpty()) {
                    z2 = false;
                } else {
                    try {
                        int size = this.mPendingActions.size();
                        z2 = false;
                        for (int i = 0; i < size; i++) {
                            z2 |= this.mPendingActions.get(i).generateOps(arrayList, arrayList2);
                        }
                    } finally {
                        this.mPendingActions.clear();
                        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
                        Objects.requireNonNull(fragmentHostCallback);
                        fragmentHostCallback.mHandler.removeCallbacks(this.mExecCommit);
                    }
                }
            }
            if (z2) {
                this.mExecutingActions = true;
                try {
                    removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                    cleanupExec();
                    z3 = true;
                } catch (Throwable th) {
                    cleanupExec();
                    throw th;
                }
            } else {
                updateOnBackPressedCallbackEnabled();
                doPendingDeferredStart();
                FragmentStore fragmentStore = this.mFragmentStore;
                Objects.requireNonNull(fragmentStore);
                fragmentStore.mActive.values().removeAll(Collections.singleton((Object) null));
                return z3;
            }
        }
    }

    public final void forcePostponedTransactions() {
        Iterator it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            SpecialEffectsController specialEffectsController = (SpecialEffectsController) it.next();
            Objects.requireNonNull(specialEffectsController);
            if (specialEffectsController.mIsContainerPostponed) {
                specialEffectsController.mIsContainerPostponed = false;
                specialEffectsController.executePendingOperations();
            }
        }
    }

    public final void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (!arrayList.isEmpty()) {
            if (arrayList.size() == arrayList2.size()) {
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i < size) {
                    if (!arrayList.get(i).mReorderingAllowed) {
                        if (i2 != i) {
                            executeOpsTogether(arrayList, arrayList2, i2, i);
                        }
                        i2 = i + 1;
                        if (arrayList2.get(i).booleanValue()) {
                            while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).mReorderingAllowed) {
                                i2++;
                            }
                        }
                        executeOpsTogether(arrayList, arrayList2, i, i2);
                        i = i2 - 1;
                    }
                    i++;
                }
                if (i2 != size) {
                    executeOpsTogether(arrayList, arrayList2, i2, size);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    public final Parcelable saveAllStateInternal() {
        ArrayList<String> arrayList;
        int size;
        forcePostponedTransactions();
        Iterator it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
        }
        execPendingActions(true);
        this.mStateSaved = true;
        FragmentManagerViewModel fragmentManagerViewModel = this.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = true;
        FragmentStore fragmentStore = this.mFragmentStore;
        Objects.requireNonNull(fragmentStore);
        ArrayList<String> arrayList2 = new ArrayList<>(fragmentStore.mActive.size());
        for (FragmentStateManager next : fragmentStore.mActive.values()) {
            if (next != null) {
                Fragment fragment = next.mFragment;
                next.saveState();
                arrayList2.add(fragment.mWho);
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Saved state of " + fragment + ": " + fragment.mSavedFragmentState);
                }
            }
        }
        FragmentStore fragmentStore2 = this.mFragmentStore;
        Objects.requireNonNull(fragmentStore2);
        ArrayList<FragmentState> arrayList3 = new ArrayList<>(fragmentStore2.mSavedState.values());
        BackStackRecordState[] backStackRecordStateArr = null;
        if (arrayList3.isEmpty()) {
            if (isLoggingEnabled(2)) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        FragmentStore fragmentStore3 = this.mFragmentStore;
        Objects.requireNonNull(fragmentStore3);
        synchronized (fragmentStore3.mAdded) {
            if (fragmentStore3.mAdded.isEmpty()) {
                arrayList = null;
            } else {
                arrayList = new ArrayList<>(fragmentStore3.mAdded.size());
                Iterator<Fragment> it2 = fragmentStore3.mAdded.iterator();
                while (it2.hasNext()) {
                    Fragment next2 = it2.next();
                    arrayList.add(next2.mWho);
                    if (isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "saveAllState: adding fragment (" + next2.mWho + "): " + next2);
                    }
                }
            }
        }
        ArrayList<BackStackRecord> arrayList4 = this.mBackStack;
        if (arrayList4 != null && (size = arrayList4.size()) > 0) {
            backStackRecordStateArr = new BackStackRecordState[size];
            for (int i = 0; i < size; i++) {
                backStackRecordStateArr[i] = new BackStackRecordState(this.mBackStack.get(i));
                if (isLoggingEnabled(2)) {
                    StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("saveAllState: adding back stack #", i, ": ");
                    m.append(this.mBackStack.get(i));
                    Log.v("FragmentManager", m.toString());
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.mSavedState = arrayList3;
        fragmentManagerState.mActive = arrayList2;
        fragmentManagerState.mAdded = arrayList;
        fragmentManagerState.mBackStack = backStackRecordStateArr;
        fragmentManagerState.mBackStackIndex = this.mBackStackIndex.get();
        Fragment fragment2 = this.mPrimaryNav;
        if (fragment2 != null) {
            fragmentManagerState.mPrimaryNavActiveWho = fragment2.mWho;
        }
        fragmentManagerState.mBackStackStateKeys.addAll(this.mBackStackStates.keySet());
        fragmentManagerState.mBackStackStates.addAll(this.mBackStackStates.values());
        fragmentManagerState.mResultKeys.addAll(this.mResults.keySet());
        fragmentManagerState.mResults.addAll(this.mResults.values());
        fragmentManagerState.mLaunchedFragments = new ArrayList<>(this.mLaunchedFragments);
        return fragmentManagerState;
    }

    public final void setExitAnimationOrder(Fragment fragment, boolean z) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer != null && (fragmentContainer instanceof FragmentContainerView)) {
            ((FragmentContainerView) fragmentContainer).mDrawDisappearingViewsFirst = !z;
        }
    }

    public final void setVisibleRemovingFragment(Fragment fragment) {
        int i;
        int i2;
        int i3;
        int i4;
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer != null) {
            Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
            boolean z = false;
            if (animationInfo == null) {
                i = 0;
            } else {
                i = animationInfo.mEnterAnim;
            }
            if (animationInfo == null) {
                i2 = 0;
            } else {
                i2 = animationInfo.mExitAnim;
            }
            int i5 = i2 + i;
            if (animationInfo == null) {
                i3 = 0;
            } else {
                i3 = animationInfo.mPopEnterAnim;
            }
            int i6 = i3 + i5;
            if (animationInfo == null) {
                i4 = 0;
            } else {
                i4 = animationInfo.mPopExitAnim;
            }
            if (i4 + i6 > 0) {
                if (fragmentContainer.getTag(C1777R.C1779id.visible_removing_fragment_view_tag) == null) {
                    fragmentContainer.setTag(C1777R.C1779id.visible_removing_fragment_view_tag, fragment);
                }
                Fragment fragment2 = (Fragment) fragmentContainer.getTag(C1777R.C1779id.visible_removing_fragment_view_tag);
                Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
                if (animationInfo2 != null) {
                    z = animationInfo2.mIsPop;
                }
                Objects.requireNonNull(fragment2);
                if (fragment2.mAnimationInfo != null) {
                    fragment2.ensureAnimationInfo().mIsPop = z;
                }
            }
        }
    }
}
