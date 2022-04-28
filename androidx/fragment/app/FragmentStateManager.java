package androidx.fragment.app;

import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.collection.SparseArrayCompat;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.SpecialEffectsController;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManagerImpl;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.FalsingManager;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

public final class FragmentStateManager {
    public final FragmentLifecycleCallbacksDispatcher mDispatcher;
    public final Fragment mFragment;
    public int mFragmentManagerState = -1;
    public final FragmentStore mFragmentStore;
    public boolean mMovingToState = false;

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, Fragment fragment) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
    }

    public final void activityCreated() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto ACTIVITY_CREATED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        Bundle bundle = fragment.mSavedFragmentState;
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mState = 3;
        fragment.mCalled = true;
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "moveto RESTORE_VIEW_STATE: " + fragment);
        }
        View view = fragment.mView;
        if (view != null) {
            Bundle bundle2 = fragment.mSavedFragmentState;
            SparseArray<Parcelable> sparseArray = fragment.mSavedViewState;
            if (sparseArray != null) {
                view.restoreHierarchyState(sparseArray);
                fragment.mSavedViewState = null;
            }
            if (fragment.mView != null) {
                FragmentViewLifecycleOwner fragmentViewLifecycleOwner = fragment.mViewLifecycleOwner;
                Bundle bundle3 = fragment.mSavedViewRegistryState;
                Objects.requireNonNull(fragmentViewLifecycleOwner);
                fragmentViewLifecycleOwner.mSavedStateRegistryController.performRestore(bundle3);
                fragment.mSavedViewRegistryState = null;
            }
            fragment.mCalled = false;
            fragment.onViewStateRestored(bundle2);
            if (!fragment.mCalled) {
                throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onViewStateRestored()");
            } else if (fragment.mView != null) {
                fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            }
        }
        fragment.mSavedFragmentState = null;
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(4);
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        Bundle bundle4 = this.mFragment.mSavedFragmentState;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated(false);
    }

    public final void attach() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto ATTACHED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        Fragment fragment2 = fragment.mTarget;
        FragmentStateManager fragmentStateManager = null;
        if (fragment2 != null) {
            FragmentStore fragmentStore = this.mFragmentStore;
            String str = fragment2.mWho;
            Objects.requireNonNull(fragmentStore);
            FragmentStateManager fragmentStateManager2 = fragmentStore.mActive.get(str);
            if (fragmentStateManager2 != null) {
                Fragment fragment3 = this.mFragment;
                fragment3.mTargetWho = fragment3.mTarget.mWho;
                fragment3.mTarget = null;
                fragmentStateManager = fragmentStateManager2;
            } else {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Fragment ");
                m2.append(this.mFragment);
                m2.append(" declared target fragment ");
                m2.append(this.mFragment.mTarget);
                m2.append(" that does not belong to this FragmentManager!");
                throw new IllegalStateException(m2.toString());
            }
        } else {
            String str2 = fragment.mTargetWho;
            if (str2 != null) {
                FragmentStore fragmentStore2 = this.mFragmentStore;
                Objects.requireNonNull(fragmentStore2);
                fragmentStateManager = fragmentStore2.mActive.get(str2);
                if (fragmentStateManager == null) {
                    StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Fragment ");
                    m3.append(this.mFragment);
                    m3.append(" declared target fragment ");
                    throw new IllegalStateException(MotionController$$ExternalSyntheticOutline1.m8m(m3, this.mFragment.mTargetWho, " that does not belong to this FragmentManager!"));
                }
            }
        }
        if (fragmentStateManager != null) {
            fragmentStateManager.moveToExpectedState();
        }
        Fragment fragment4 = this.mFragment;
        FragmentManager fragmentManager = fragment4.mFragmentManager;
        Objects.requireNonNull(fragmentManager);
        fragment4.mHost = fragmentManager.mHost;
        Fragment fragment5 = this.mFragment;
        FragmentManager fragmentManager2 = fragment5.mFragmentManager;
        Objects.requireNonNull(fragmentManager2);
        fragment5.mParentFragment = fragmentManager2.mParent;
        this.mDispatcher.dispatchOnFragmentPreAttached(false);
        Fragment fragment6 = this.mFragment;
        Objects.requireNonNull(fragment6);
        Iterator<Fragment.OnPreAttachedListener> it = fragment6.mOnPreAttachedListeners.iterator();
        while (it.hasNext()) {
            it.next().onPreAttached();
        }
        fragment6.mOnPreAttachedListeners.clear();
        fragment6.mChildFragmentManager.attachController(fragment6.mHost, fragment6.createFragmentContainer(), fragment6);
        fragment6.mState = 0;
        fragment6.mCalled = false;
        FragmentHostCallback<?> fragmentHostCallback = fragment6.mHost;
        Objects.requireNonNull(fragmentHostCallback);
        fragment6.onAttach(fragmentHostCallback.mContext);
        if (fragment6.mCalled) {
            FragmentManager fragmentManager3 = fragment6.mFragmentManager;
            Objects.requireNonNull(fragmentManager3);
            Iterator<FragmentOnAttachListener> it2 = fragmentManager3.mOnAttachListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onAttachFragment$1();
            }
            FragmentManagerImpl fragmentManagerImpl = fragment6.mChildFragmentManager;
            Objects.requireNonNull(fragmentManagerImpl);
            fragmentManagerImpl.mStateSaved = false;
            fragmentManagerImpl.mStopped = false;
            FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
            Objects.requireNonNull(fragmentManagerViewModel);
            fragmentManagerViewModel.mIsStateSaved = false;
            fragmentManagerImpl.dispatchStateChange(0);
            this.mDispatcher.dispatchOnFragmentAttached(false);
            return;
        }
        throw new SuperNotCalledException("Fragment " + fragment6 + " did not call through to super.onAttach()");
    }

    public final void create() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto CREATED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        if (!fragment.mIsCreated) {
            this.mDispatcher.dispatchOnFragmentPreCreated(false);
            Fragment fragment2 = this.mFragment;
            Bundle bundle = fragment2.mSavedFragmentState;
            fragment2.mChildFragmentManager.noteStateNotSaved();
            fragment2.mState = 1;
            fragment2.mCalled = false;
            fragment2.mLifecycleRegistry.addObserver(new LifecycleEventObserver() {
                public final void onStateChanged(
/*
Method generation error in method: androidx.fragment.app.Fragment.5.onStateChanged(androidx.lifecycle.LifecycleOwner, androidx.lifecycle.Lifecycle$Event):void, dex: classes.dex
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: androidx.fragment.app.Fragment.5.onStateChanged(androidx.lifecycle.LifecycleOwner, androidx.lifecycle.Lifecycle$Event):void, class status: UNLOADED
                	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                
*/
            });
            fragment2.mSavedStateRegistryController.performRestore(bundle);
            fragment2.onCreate(bundle);
            fragment2.mIsCreated = true;
            if (fragment2.mCalled) {
                fragment2.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
                Bundle bundle2 = this.mFragment.mSavedFragmentState;
                fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentCreated(false);
                return;
            }
            throw new SuperNotCalledException("Fragment " + fragment2 + " did not call through to super.onCreate()");
        }
        fragment.restoreChildFragmentState(fragment.mSavedFragmentState);
        this.mFragment.mState = 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0096, code lost:
        if (r1 != false) goto L_0x0098;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0120  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void destroy() {
        /*
            r9 = this;
            r0 = 3
            boolean r1 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r0)
            java.lang.String r2 = "FragmentManager"
            if (r1 == 0) goto L_0x001b
            java.lang.String r1 = "movefrom CREATED: "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            androidx.fragment.app.Fragment r3 = r9.mFragment
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r2, r1)
        L_0x001b:
            androidx.fragment.app.Fragment r1 = r9.mFragment
            boolean r3 = r1.mRemoving
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x002e
            int r3 = r1.mBackStackNesting
            if (r3 <= 0) goto L_0x0029
            r3 = r5
            goto L_0x002a
        L_0x0029:
            r3 = r4
        L_0x002a:
            if (r3 != 0) goto L_0x002e
            r3 = r5
            goto L_0x002f
        L_0x002e:
            r3 = r4
        L_0x002f:
            r6 = 0
            if (r3 == 0) goto L_0x003e
            java.util.Objects.requireNonNull(r1)
            androidx.fragment.app.FragmentStore r1 = r9.mFragmentStore
            androidx.fragment.app.Fragment r7 = r9.mFragment
            java.lang.String r7 = r7.mWho
            r1.setSavedState(r7, r6)
        L_0x003e:
            if (r3 != 0) goto L_0x0064
            androidx.fragment.app.FragmentStore r1 = r9.mFragmentStore
            java.util.Objects.requireNonNull(r1)
            androidx.fragment.app.FragmentManagerViewModel r1 = r1.mNonConfig
            androidx.fragment.app.Fragment r7 = r9.mFragment
            java.util.Objects.requireNonNull(r1)
            java.util.HashMap<java.lang.String, androidx.fragment.app.Fragment> r8 = r1.mRetainedFragments
            java.lang.String r7 = r7.mWho
            boolean r7 = r8.containsKey(r7)
            if (r7 != 0) goto L_0x0057
            goto L_0x005e
        L_0x0057:
            boolean r7 = r1.mStateAutomaticallySaved
            if (r7 == 0) goto L_0x005e
            boolean r1 = r1.mHasBeenCleared
            goto L_0x005f
        L_0x005e:
            r1 = r5
        L_0x005f:
            if (r1 == 0) goto L_0x0062
            goto L_0x0064
        L_0x0062:
            r1 = r4
            goto L_0x0065
        L_0x0064:
            r1 = r5
        L_0x0065:
            if (r1 == 0) goto L_0x0120
            androidx.fragment.app.Fragment r1 = r9.mFragment
            androidx.fragment.app.FragmentHostCallback<?> r1 = r1.mHost
            boolean r7 = r1 instanceof androidx.lifecycle.ViewModelStoreOwner
            if (r7 == 0) goto L_0x007c
            androidx.fragment.app.FragmentStore r1 = r9.mFragmentStore
            java.util.Objects.requireNonNull(r1)
            androidx.fragment.app.FragmentManagerViewModel r1 = r1.mNonConfig
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mHasBeenCleared
            goto L_0x008e
        L_0x007c:
            java.util.Objects.requireNonNull(r1)
            android.content.Context r1 = r1.mContext
            boolean r7 = r1 instanceof android.app.Activity
            if (r7 == 0) goto L_0x008d
            android.app.Activity r1 = (android.app.Activity) r1
            boolean r1 = r1.isChangingConfigurations()
            r1 = r1 ^ r5
            goto L_0x008e
        L_0x008d:
            r1 = r5
        L_0x008e:
            if (r3 == 0) goto L_0x0096
            androidx.fragment.app.Fragment r1 = r9.mFragment
            java.util.Objects.requireNonNull(r1)
            goto L_0x0098
        L_0x0096:
            if (r1 == 0) goto L_0x00c3
        L_0x0098:
            androidx.fragment.app.FragmentStore r1 = r9.mFragmentStore
            java.util.Objects.requireNonNull(r1)
            androidx.fragment.app.FragmentManagerViewModel r1 = r1.mNonConfig
            androidx.fragment.app.Fragment r3 = r9.mFragment
            java.util.Objects.requireNonNull(r1)
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r0)
            if (r0 == 0) goto L_0x00be
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r7 = "Clearing non-config state for "
            r0.append(r7)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r2, r0)
        L_0x00be:
            java.lang.String r0 = r3.mWho
            r1.clearNonConfigStateInternal(r0)
        L_0x00c3:
            androidx.fragment.app.Fragment r0 = r9.mFragment
            java.util.Objects.requireNonNull(r0)
            androidx.fragment.app.FragmentManagerImpl r1 = r0.mChildFragmentManager
            r1.dispatchDestroy()
            androidx.lifecycle.LifecycleRegistry r1 = r0.mLifecycleRegistry
            androidx.lifecycle.Lifecycle$Event r2 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY
            r1.handleLifecycleEvent(r2)
            r0.mState = r4
            r0.mIsCreated = r4
            r0.mCalled = r5
            androidx.fragment.app.FragmentLifecycleCallbacksDispatcher r0 = r9.mDispatcher
            r0.dispatchOnFragmentDestroyed(r4)
            androidx.fragment.app.FragmentStore r0 = r9.mFragmentStore
            java.util.ArrayList r0 = r0.getActiveFragmentStateManagers()
            java.util.Iterator r0 = r0.iterator()
        L_0x00e9:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x010c
            java.lang.Object r1 = r0.next()
            androidx.fragment.app.FragmentStateManager r1 = (androidx.fragment.app.FragmentStateManager) r1
            if (r1 == 0) goto L_0x00e9
            androidx.fragment.app.Fragment r1 = r1.mFragment
            androidx.fragment.app.Fragment r2 = r9.mFragment
            java.lang.String r2 = r2.mWho
            java.lang.String r3 = r1.mTargetWho
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x00e9
            androidx.fragment.app.Fragment r2 = r9.mFragment
            r1.mTarget = r2
            r1.mTargetWho = r6
            goto L_0x00e9
        L_0x010c:
            androidx.fragment.app.Fragment r0 = r9.mFragment
            java.lang.String r1 = r0.mTargetWho
            if (r1 == 0) goto L_0x011a
            androidx.fragment.app.FragmentStore r2 = r9.mFragmentStore
            androidx.fragment.app.Fragment r1 = r2.findActiveFragment(r1)
            r0.mTarget = r1
        L_0x011a:
            androidx.fragment.app.FragmentStore r0 = r9.mFragmentStore
            r0.makeInactive(r9)
            goto L_0x013a
        L_0x0120:
            androidx.fragment.app.Fragment r0 = r9.mFragment
            java.lang.String r0 = r0.mTargetWho
            if (r0 == 0) goto L_0x0136
            androidx.fragment.app.FragmentStore r1 = r9.mFragmentStore
            androidx.fragment.app.Fragment r0 = r1.findActiveFragment(r0)
            if (r0 == 0) goto L_0x0136
            boolean r1 = r0.mRetainInstance
            if (r1 == 0) goto L_0x0136
            androidx.fragment.app.Fragment r1 = r9.mFragment
            r1.mTarget = r0
        L_0x0136:
            androidx.fragment.app.Fragment r9 = r9.mFragment
            r9.mState = r4
        L_0x013a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentStateManager.destroy():void");
    }

    public final void destroyFragmentView() {
        View view;
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("movefrom CREATE_VIEW: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        ViewGroup viewGroup = fragment.mContainer;
        if (!(viewGroup == null || (view = fragment.mView) == null)) {
            viewGroup.removeView(view);
        }
        Fragment fragment2 = this.mFragment;
        Objects.requireNonNull(fragment2);
        FragmentManagerImpl fragmentManagerImpl = fragment2.mChildFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.dispatchStateChange(1);
        if (fragment2.mView != null) {
            FragmentViewLifecycleOwner fragmentViewLifecycleOwner = fragment2.mViewLifecycleOwner;
            Objects.requireNonNull(fragmentViewLifecycleOwner);
            fragmentViewLifecycleOwner.initialize();
            LifecycleRegistry lifecycleRegistry = fragmentViewLifecycleOwner.mLifecycleRegistry;
            Objects.requireNonNull(lifecycleRegistry);
            if (lifecycleRegistry.mState.isAtLeast(Lifecycle.State.CREATED)) {
                fragment2.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            }
        }
        fragment2.mState = 1;
        fragment2.mCalled = false;
        fragment2.onDestroyView();
        if (fragment2.mCalled) {
            LoaderManagerImpl.LoaderViewModel loaderViewModel = (LoaderManagerImpl.LoaderViewModel) new ViewModelProvider(fragment2.getViewModelStore(), LoaderManagerImpl.LoaderViewModel.FACTORY).get(LoaderManagerImpl.LoaderViewModel.class);
            Objects.requireNonNull(loaderViewModel);
            SparseArrayCompat<LoaderManagerImpl.LoaderInfo> sparseArrayCompat = loaderViewModel.mLoaders;
            Objects.requireNonNull(sparseArrayCompat);
            int i = sparseArrayCompat.mSize;
            for (int i2 = 0; i2 < i; i2++) {
                SparseArrayCompat<LoaderManagerImpl.LoaderInfo> sparseArrayCompat2 = loaderViewModel.mLoaders;
                Objects.requireNonNull(sparseArrayCompat2);
                Objects.requireNonNull((LoaderManagerImpl.LoaderInfo) sparseArrayCompat2.mValues[i2]);
            }
            fragment2.mPerformedCreateView = false;
            this.mDispatcher.dispatchOnFragmentViewDestroyed(false);
            Fragment fragment3 = this.mFragment;
            fragment3.mContainer = null;
            fragment3.mView = null;
            fragment3.mViewLifecycleOwner = null;
            fragment3.mViewLifecycleOwnerLiveData.setValue(null);
            this.mFragment.mInLayout = false;
            return;
        }
        throw new SuperNotCalledException("Fragment " + fragment2 + " did not call through to super.onDestroyView()");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void detach() {
        /*
            r8 = this;
            r0 = 3
            boolean r1 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r0)
            java.lang.String r2 = "FragmentManager"
            if (r1 == 0) goto L_0x001b
            java.lang.String r1 = "movefrom ATTACHED: "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            androidx.fragment.app.Fragment r3 = r8.mFragment
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r2, r1)
        L_0x001b:
            androidx.fragment.app.Fragment r1 = r8.mFragment
            java.util.Objects.requireNonNull(r1)
            r3 = -1
            r1.mState = r3
            r4 = 0
            r1.mCalled = r4
            r1.onDetach()
            r5 = 0
            boolean r6 = r1.mCalled
            if (r6 == 0) goto L_0x00e1
            androidx.fragment.app.FragmentManagerImpl r6 = r1.mChildFragmentManager
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mDestroyed
            if (r6 != 0) goto L_0x0043
            androidx.fragment.app.FragmentManagerImpl r6 = r1.mChildFragmentManager
            r6.dispatchDestroy()
            androidx.fragment.app.FragmentManagerImpl r6 = new androidx.fragment.app.FragmentManagerImpl
            r6.<init>()
            r1.mChildFragmentManager = r6
        L_0x0043:
            androidx.fragment.app.FragmentLifecycleCallbacksDispatcher r1 = r8.mDispatcher
            r1.dispatchOnFragmentDetached(r4)
            androidx.fragment.app.Fragment r1 = r8.mFragment
            r1.mState = r3
            r1.mHost = r5
            r1.mParentFragment = r5
            r1.mFragmentManager = r5
            boolean r3 = r1.mRemoving
            r6 = 1
            if (r3 == 0) goto L_0x0062
            int r1 = r1.mBackStackNesting
            if (r1 <= 0) goto L_0x005d
            r1 = r6
            goto L_0x005e
        L_0x005d:
            r1 = r4
        L_0x005e:
            if (r1 != 0) goto L_0x0062
            r1 = r6
            goto L_0x0063
        L_0x0062:
            r1 = r4
        L_0x0063:
            if (r1 != 0) goto L_0x0086
            androidx.fragment.app.FragmentStore r1 = r8.mFragmentStore
            java.util.Objects.requireNonNull(r1)
            androidx.fragment.app.FragmentManagerViewModel r1 = r1.mNonConfig
            androidx.fragment.app.Fragment r3 = r8.mFragment
            java.util.Objects.requireNonNull(r1)
            java.util.HashMap<java.lang.String, androidx.fragment.app.Fragment> r7 = r1.mRetainedFragments
            java.lang.String r3 = r3.mWho
            boolean r3 = r7.containsKey(r3)
            if (r3 != 0) goto L_0x007c
            goto L_0x0083
        L_0x007c:
            boolean r3 = r1.mStateAutomaticallySaved
            if (r3 == 0) goto L_0x0083
            boolean r1 = r1.mHasBeenCleared
            goto L_0x0084
        L_0x0083:
            r1 = r6
        L_0x0084:
            if (r1 == 0) goto L_0x00e0
        L_0x0086:
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r0)
            if (r0 == 0) goto L_0x009e
            java.lang.String r0 = "initState called for fragment: "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            androidx.fragment.app.Fragment r1 = r8.mFragment
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r2, r0)
        L_0x009e:
            androidx.fragment.app.Fragment r8 = r8.mFragment
            java.util.Objects.requireNonNull(r8)
            androidx.lifecycle.LifecycleRegistry r0 = new androidx.lifecycle.LifecycleRegistry
            r0.<init>(r8, r6)
            r8.mLifecycleRegistry = r0
            androidx.savedstate.SavedStateRegistryController r0 = new androidx.savedstate.SavedStateRegistryController
            r0.<init>(r8)
            r8.mSavedStateRegistryController = r0
            java.lang.String r0 = r8.mWho
            r8.mPreviousWho = r0
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            r8.mWho = r0
            r8.mAdded = r4
            r8.mRemoving = r4
            r8.mFromLayout = r4
            r8.mInLayout = r4
            r8.mRestored = r4
            r8.mBackStackNesting = r4
            r8.mFragmentManager = r5
            androidx.fragment.app.FragmentManagerImpl r0 = new androidx.fragment.app.FragmentManagerImpl
            r0.<init>()
            r8.mChildFragmentManager = r0
            r8.mHost = r5
            r8.mFragmentId = r4
            r8.mContainerId = r4
            r8.mTag = r5
            r8.mHidden = r4
            r8.mDetached = r4
        L_0x00e0:
            return
        L_0x00e1:
            androidx.fragment.app.SuperNotCalledException r8 = new androidx.fragment.app.SuperNotCalledException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Fragment "
            r0.append(r2)
            r0.append(r1)
            java.lang.String r1 = " did not call through to super.onDetach()"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentStateManager.detach():void");
    }

    public final void pause() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("movefrom RESUMED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        Objects.requireNonNull(fragment);
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.dispatchStateChange(5);
        if (fragment.mView != null) {
            fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
        fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        fragment.mState = 6;
        fragment.mCalled = true;
        this.mDispatcher.dispatchOnFragmentPaused(false);
    }

    public final void resume() {
        View view;
        boolean z;
        String str;
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto RESUMED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        Objects.requireNonNull(fragment);
        Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
        if (animationInfo == null) {
            view = null;
        } else {
            view = animationInfo.mFocusedView;
        }
        if (view != null) {
            if (view != this.mFragment.mView) {
                ViewParent parent = view.getParent();
                while (true) {
                    if (parent == null) {
                        z = false;
                        break;
                    } else if (parent == this.mFragment.mView) {
                        break;
                    } else {
                        parent = parent.getParent();
                    }
                }
            }
            z = true;
            if (z) {
                boolean requestFocus = view.requestFocus();
                if (FragmentManager.isLoggingEnabled(2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("requestFocus: Restoring focused view ");
                    sb.append(view);
                    sb.append(" ");
                    if (requestFocus) {
                        str = "succeeded";
                    } else {
                        str = "failed";
                    }
                    sb.append(str);
                    sb.append(" on Fragment ");
                    sb.append(this.mFragment);
                    sb.append(" resulting in focused view ");
                    sb.append(this.mFragment.mView.findFocus());
                    Log.v("FragmentManager", sb.toString());
                }
            }
        }
        Fragment fragment2 = this.mFragment;
        Objects.requireNonNull(fragment2);
        fragment2.ensureAnimationInfo().mFocusedView = null;
        Fragment fragment3 = this.mFragment;
        Objects.requireNonNull(fragment3);
        fragment3.mChildFragmentManager.noteStateNotSaved();
        fragment3.mChildFragmentManager.execPendingActions(true);
        fragment3.mState = 7;
        fragment3.mCalled = true;
        LifecycleRegistry lifecycleRegistry = fragment3.mLifecycleRegistry;
        Lifecycle.Event event = Lifecycle.Event.ON_RESUME;
        lifecycleRegistry.handleLifecycleEvent(event);
        if (fragment3.mView != null) {
            fragment3.mViewLifecycleOwner.handleLifecycleEvent(event);
        }
        FragmentManagerImpl fragmentManagerImpl = fragment3.mChildFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(7);
        this.mDispatcher.dispatchOnFragmentResumed(false);
        Fragment fragment4 = this.mFragment;
        fragment4.mSavedFragmentState = null;
        fragment4.mSavedViewState = null;
        fragment4.mSavedViewRegistryState = null;
    }

    public final void start() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto STARTED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        Objects.requireNonNull(fragment);
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mChildFragmentManager.execPendingActions(true);
        fragment.mState = 5;
        fragment.mCalled = false;
        fragment.onStart();
        if (fragment.mCalled) {
            LifecycleRegistry lifecycleRegistry = fragment.mLifecycleRegistry;
            Lifecycle.Event event = Lifecycle.Event.ON_START;
            lifecycleRegistry.handleLifecycleEvent(event);
            if (fragment.mView != null) {
                fragment.mViewLifecycleOwner.handleLifecycleEvent(event);
            }
            FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
            Objects.requireNonNull(fragmentManagerImpl);
            fragmentManagerImpl.mStateSaved = false;
            fragmentManagerImpl.mStopped = false;
            FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
            Objects.requireNonNull(fragmentManagerViewModel);
            fragmentManagerViewModel.mIsStateSaved = false;
            fragmentManagerImpl.dispatchStateChange(5);
            this.mDispatcher.dispatchOnFragmentStarted(false);
            return;
        }
        throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onStart()");
    }

    public final void stop() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("movefrom STARTED: ");
            m.append(this.mFragment);
            Log.d("FragmentManager", m.toString());
        }
        Fragment fragment = this.mFragment;
        Objects.requireNonNull(fragment);
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.mStopped = true;
        FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = true;
        fragmentManagerImpl.dispatchStateChange(4);
        if (fragment.mView != null) {
            fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
        fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        fragment.mState = 4;
        fragment.mCalled = false;
        fragment.onStop();
        if (fragment.mCalled) {
            this.mDispatcher.dispatchOnFragmentStopped(false);
            return;
        }
        throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onStop()");
    }

    public final void addViewToContainer() {
        View view;
        View view2;
        FragmentStore fragmentStore = this.mFragmentStore;
        Fragment fragment = this.mFragment;
        Objects.requireNonNull(fragmentStore);
        ViewGroup viewGroup = fragment.mContainer;
        int i = -1;
        if (viewGroup != null) {
            int indexOf = fragmentStore.mAdded.indexOf(fragment);
            int i2 = indexOf - 1;
            while (true) {
                if (i2 < 0) {
                    while (true) {
                        indexOf++;
                        if (indexOf >= fragmentStore.mAdded.size()) {
                            break;
                        }
                        Fragment fragment2 = fragmentStore.mAdded.get(indexOf);
                        if (fragment2.mContainer == viewGroup && (view = fragment2.mView) != null) {
                            i = viewGroup.indexOfChild(view);
                            break;
                        }
                    }
                } else {
                    Fragment fragment3 = fragmentStore.mAdded.get(i2);
                    if (fragment3.mContainer == viewGroup && (view2 = fragment3.mView) != null) {
                        i = viewGroup.indexOfChild(view2) + 1;
                        break;
                    }
                    i2--;
                }
            }
        }
        Fragment fragment4 = this.mFragment;
        fragment4.mContainer.addView(fragment4.mView, i);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact} */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r10v4, types: [androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact] */
    /* JADX WARNING: type inference failed for: r9v6, types: [androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int computeExpectedState() {
        /*
            r14 = this;
            androidx.fragment.app.Fragment r0 = r14.mFragment
            androidx.fragment.app.FragmentManager r1 = r0.mFragmentManager
            if (r1 != 0) goto L_0x0009
            int r14 = r0.mState
            return r14
        L_0x0009:
            int r1 = r14.mFragmentManagerState
            androidx.lifecycle.Lifecycle$State r0 = r0.mMaxState
            int r0 = r0.ordinal()
            r2 = -1
            r3 = 5
            r4 = 3
            r5 = 4
            r6 = 2
            r7 = 0
            r8 = 1
            if (r0 == r8) goto L_0x002f
            if (r0 == r6) goto L_0x002a
            if (r0 == r4) goto L_0x0025
            if (r0 == r5) goto L_0x0033
            int r1 = java.lang.Math.min(r1, r2)
            goto L_0x0033
        L_0x0025:
            int r1 = java.lang.Math.min(r1, r3)
            goto L_0x0033
        L_0x002a:
            int r1 = java.lang.Math.min(r1, r8)
            goto L_0x0033
        L_0x002f:
            int r1 = java.lang.Math.min(r1, r7)
        L_0x0033:
            androidx.fragment.app.Fragment r0 = r14.mFragment
            boolean r9 = r0.mFromLayout
            if (r9 == 0) goto L_0x0063
            boolean r9 = r0.mInLayout
            if (r9 == 0) goto L_0x0054
            int r0 = r14.mFragmentManagerState
            int r1 = java.lang.Math.max(r0, r6)
            androidx.fragment.app.Fragment r0 = r14.mFragment
            android.view.View r0 = r0.mView
            if (r0 == 0) goto L_0x0063
            android.view.ViewParent r0 = r0.getParent()
            if (r0 != 0) goto L_0x0063
            int r1 = java.lang.Math.min(r1, r6)
            goto L_0x0063
        L_0x0054:
            int r9 = r14.mFragmentManagerState
            if (r9 >= r5) goto L_0x005f
            int r0 = r0.mState
            int r1 = java.lang.Math.min(r1, r0)
            goto L_0x0063
        L_0x005f:
            int r1 = java.lang.Math.min(r1, r8)
        L_0x0063:
            androidx.fragment.app.Fragment r0 = r14.mFragment
            boolean r0 = r0.mAdded
            if (r0 != 0) goto L_0x006d
            int r1 = java.lang.Math.min(r1, r8)
        L_0x006d:
            androidx.fragment.app.Fragment r0 = r14.mFragment
            android.view.ViewGroup r9 = r0.mContainer
            r10 = 0
            if (r9 == 0) goto L_0x00bf
            androidx.fragment.app.FragmentManager r0 = r0.getParentFragmentManager()
            androidx.fragment.app.SpecialEffectsControllerFactory r0 = r0.getSpecialEffectsControllerFactory()
            androidx.fragment.app.SpecialEffectsController r0 = androidx.fragment.app.SpecialEffectsController.getOrCreateController(r9, r0)
            java.util.Objects.requireNonNull(r0)
            androidx.fragment.app.Fragment r9 = r14.mFragment
            androidx.fragment.app.SpecialEffectsController$Operation r9 = r0.findPendingOperation(r9)
            if (r9 == 0) goto L_0x008e
            androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact r9 = r9.mLifecycleImpact
            goto L_0x008f
        L_0x008e:
            r9 = r10
        L_0x008f:
            androidx.fragment.app.Fragment r11 = r14.mFragment
            java.util.ArrayList<androidx.fragment.app.SpecialEffectsController$Operation> r0 = r0.mRunningOperations
            java.util.Iterator r0 = r0.iterator()
        L_0x0097:
            boolean r12 = r0.hasNext()
            if (r12 == 0) goto L_0x00b3
            java.lang.Object r12 = r0.next()
            androidx.fragment.app.SpecialEffectsController$Operation r12 = (androidx.fragment.app.SpecialEffectsController.Operation) r12
            java.util.Objects.requireNonNull(r12)
            androidx.fragment.app.Fragment r13 = r12.mFragment
            boolean r13 = r13.equals(r11)
            if (r13 == 0) goto L_0x0097
            boolean r13 = r12.mIsCanceled
            if (r13 != 0) goto L_0x0097
            r10 = r12
        L_0x00b3:
            if (r10 == 0) goto L_0x00be
            if (r9 == 0) goto L_0x00bb
            androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact r0 = androidx.fragment.app.SpecialEffectsController.Operation.LifecycleImpact.NONE
            if (r9 != r0) goto L_0x00be
        L_0x00bb:
            androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact r10 = r10.mLifecycleImpact
            goto L_0x00bf
        L_0x00be:
            r10 = r9
        L_0x00bf:
            androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact r0 = androidx.fragment.app.SpecialEffectsController.Operation.LifecycleImpact.ADDING
            if (r10 != r0) goto L_0x00c9
            r0 = 6
            int r1 = java.lang.Math.min(r1, r0)
            goto L_0x00e8
        L_0x00c9:
            androidx.fragment.app.SpecialEffectsController$Operation$LifecycleImpact r0 = androidx.fragment.app.SpecialEffectsController.Operation.LifecycleImpact.REMOVING
            if (r10 != r0) goto L_0x00d2
            int r1 = java.lang.Math.max(r1, r4)
            goto L_0x00e8
        L_0x00d2:
            androidx.fragment.app.Fragment r0 = r14.mFragment
            boolean r4 = r0.mRemoving
            if (r4 == 0) goto L_0x00e8
            int r0 = r0.mBackStackNesting
            if (r0 <= 0) goto L_0x00dd
            r7 = r8
        L_0x00dd:
            if (r7 == 0) goto L_0x00e4
            int r1 = java.lang.Math.min(r1, r8)
            goto L_0x00e8
        L_0x00e4:
            int r1 = java.lang.Math.min(r1, r2)
        L_0x00e8:
            androidx.fragment.app.Fragment r0 = r14.mFragment
            boolean r2 = r0.mDeferStart
            if (r2 == 0) goto L_0x00f6
            int r0 = r0.mState
            if (r0 >= r3) goto L_0x00f6
            int r1 = java.lang.Math.min(r1, r5)
        L_0x00f6:
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r6)
            if (r0 == 0) goto L_0x0112
            java.lang.String r0 = "computeExpectedState() of "
            java.lang.String r2 = " for "
            java.lang.StringBuilder r0 = androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0.m13m(r0, r1, r2)
            androidx.fragment.app.Fragment r14 = r14.mFragment
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            java.lang.String r0 = "FragmentManager"
            android.util.Log.v(r0, r14)
        L_0x0112:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentStateManager.computeExpectedState():int");
    }

    public final void createView() {
        String str;
        if (!this.mFragment.mFromLayout) {
            if (FragmentManager.isLoggingEnabled(3)) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto CREATE_VIEW: ");
                m.append(this.mFragment);
                Log.d("FragmentManager", m.toString());
            }
            Fragment fragment = this.mFragment;
            LayoutInflater onGetLayoutInflater = fragment.onGetLayoutInflater(fragment.mSavedFragmentState);
            ViewGroup viewGroup = null;
            Fragment fragment2 = this.mFragment;
            ViewGroup viewGroup2 = fragment2.mContainer;
            if (viewGroup2 != null) {
                viewGroup = viewGroup2;
            } else {
                int i = fragment2.mContainerId;
                if (i != 0) {
                    if (i != -1) {
                        FragmentManager fragmentManager = fragment2.mFragmentManager;
                        Objects.requireNonNull(fragmentManager);
                        viewGroup = (ViewGroup) fragmentManager.mContainer.onFindViewById(this.mFragment.mContainerId);
                        if (viewGroup == null) {
                            Fragment fragment3 = this.mFragment;
                            if (!fragment3.mRestored) {
                                try {
                                    str = fragment3.requireContext().getResources().getResourceName(this.mFragment.mContainerId);
                                } catch (Resources.NotFoundException unused) {
                                    str = "unknown";
                                }
                                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No view found for id 0x");
                                m2.append(Integer.toHexString(this.mFragment.mContainerId));
                                m2.append(" (");
                                m2.append(str);
                                m2.append(") for fragment ");
                                m2.append(this.mFragment);
                                throw new IllegalArgumentException(m2.toString());
                            }
                        } else if (!(viewGroup instanceof FragmentContainerView)) {
                            FragmentStrictMode.onWrongFragmentContainer(this.mFragment, viewGroup);
                        }
                    } else {
                        StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot create fragment ");
                        m3.append(this.mFragment);
                        m3.append(" for a container view with no id");
                        throw new IllegalArgumentException(m3.toString());
                    }
                }
            }
            Fragment fragment4 = this.mFragment;
            fragment4.mContainer = viewGroup;
            fragment4.performCreateView(onGetLayoutInflater, viewGroup, fragment4.mSavedFragmentState);
            View view = this.mFragment.mView;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                Fragment fragment5 = this.mFragment;
                fragment5.mView.setTag(C1777R.C1779id.fragment_container_view_tag, fragment5);
                if (viewGroup != null) {
                    addViewToContainer();
                }
                Fragment fragment6 = this.mFragment;
                if (fragment6.mHidden) {
                    fragment6.mView.setVisibility(8);
                }
                View view2 = this.mFragment.mView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api19Impl.isAttachedToWindow(view2)) {
                    ViewCompat.Api20Impl.requestApplyInsets(this.mFragment.mView);
                } else {
                    final View view3 = this.mFragment.mView;
                    view3.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        public final void onViewDetachedFromWindow(View view) {
                        }

                        public final void onViewAttachedToWindow(View view) {
                            view3.removeOnAttachStateChangeListener(this);
                            View view2 = view3;
                            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api20Impl.requestApplyInsets(view2);
                        }
                    });
                }
                Fragment fragment7 = this.mFragment;
                Objects.requireNonNull(fragment7);
                FragmentManagerImpl fragmentManagerImpl = fragment7.mChildFragmentManager;
                Objects.requireNonNull(fragmentManagerImpl);
                fragmentManagerImpl.dispatchStateChange(2);
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
                View view4 = this.mFragment.mView;
                fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(false);
                int visibility = this.mFragment.mView.getVisibility();
                float alpha = this.mFragment.mView.getAlpha();
                Fragment fragment8 = this.mFragment;
                Objects.requireNonNull(fragment8);
                fragment8.ensureAnimationInfo().mPostOnViewCreatedAlpha = alpha;
                Fragment fragment9 = this.mFragment;
                if (fragment9.mContainer != null && visibility == 0) {
                    View findFocus = fragment9.mView.findFocus();
                    if (findFocus != null) {
                        Fragment fragment10 = this.mFragment;
                        Objects.requireNonNull(fragment10);
                        fragment10.ensureAnimationInfo().mFocusedView = findFocus;
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "requestFocus: Saved focused view " + findFocus + " for Fragment " + this.mFragment);
                        }
                    }
                    this.mFragment.mView.setAlpha(0.0f);
                }
            }
            this.mFragment.mState = 2;
        }
    }

    public final void ensureInflatedView() {
        Fragment fragment = this.mFragment;
        if (fragment.mFromLayout && fragment.mInLayout && !fragment.mPerformedCreateView) {
            if (FragmentManager.isLoggingEnabled(3)) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("moveto CREATE_VIEW: ");
                m.append(this.mFragment);
                Log.d("FragmentManager", m.toString());
            }
            Fragment fragment2 = this.mFragment;
            fragment2.performCreateView(fragment2.onGetLayoutInflater(fragment2.mSavedFragmentState), (ViewGroup) null, this.mFragment.mSavedFragmentState);
            View view = this.mFragment.mView;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                Fragment fragment3 = this.mFragment;
                fragment3.mView.setTag(C1777R.C1779id.fragment_container_view_tag, fragment3);
                Fragment fragment4 = this.mFragment;
                if (fragment4.mHidden) {
                    fragment4.mView.setVisibility(8);
                }
                Fragment fragment5 = this.mFragment;
                Objects.requireNonNull(fragment5);
                FragmentManagerImpl fragmentManagerImpl = fragment5.mChildFragmentManager;
                Objects.requireNonNull(fragmentManagerImpl);
                fragmentManagerImpl.dispatchStateChange(2);
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
                View view2 = this.mFragment.mView;
                fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(false);
                this.mFragment.mState = 2;
            }
        }
    }

    public final void moveToExpectedState() {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        ViewGroup viewGroup3;
        if (!this.mMovingToState) {
            boolean z = false;
            z = true;
            try {
                while (true) {
                    int computeExpectedState = computeExpectedState();
                    Fragment fragment = this.mFragment;
                    int i = fragment.mState;
                    if (computeExpectedState != i) {
                        if (computeExpectedState <= i) {
                            switch (i - 1) {
                                case -1:
                                    detach();
                                    break;
                                case 0:
                                    destroy();
                                    break;
                                case 1:
                                    destroyFragmentView();
                                    this.mFragment.mState = z ? 1 : 0;
                                    break;
                                case 2:
                                    fragment.mInLayout = z;
                                    fragment.mState = 2;
                                    break;
                                case 3:
                                    if (FragmentManager.isLoggingEnabled(3)) {
                                        Log.d("FragmentManager", "movefrom ACTIVITY_CREATED: " + this.mFragment);
                                    }
                                    Objects.requireNonNull(this.mFragment);
                                    Fragment fragment2 = this.mFragment;
                                    if (fragment2.mView != null && fragment2.mSavedViewState == null) {
                                        saveViewState();
                                    }
                                    Fragment fragment3 = this.mFragment;
                                    if (!(fragment3.mView == null || (viewGroup2 = fragment3.mContainer) == null)) {
                                        SpecialEffectsController.getOrCreateController(viewGroup2, fragment3.getParentFragmentManager().getSpecialEffectsControllerFactory()).enqueueRemove(this);
                                    }
                                    this.mFragment.mState = 3;
                                    break;
                                case 4:
                                    stop();
                                    break;
                                case 5:
                                    fragment.mState = 5;
                                    break;
                                case FalsingManager.VERSION /*6*/:
                                    pause();
                                    break;
                            }
                        } else {
                            switch (i + 1) {
                                case 0:
                                    attach();
                                    break;
                                case 1:
                                    create();
                                    break;
                                case 2:
                                    ensureInflatedView();
                                    createView();
                                    break;
                                case 3:
                                    activityCreated();
                                    break;
                                case 4:
                                    if (!(fragment.mView == null || (viewGroup3 = fragment.mContainer) == null)) {
                                        SpecialEffectsController orCreateController = SpecialEffectsController.getOrCreateController(viewGroup3, fragment.getParentFragmentManager().getSpecialEffectsControllerFactory());
                                        SpecialEffectsController.Operation.State from = SpecialEffectsController.Operation.State.from(this.mFragment.mView.getVisibility());
                                        Objects.requireNonNull(orCreateController);
                                        if (FragmentManager.isLoggingEnabled(2)) {
                                            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + this.mFragment);
                                        }
                                        orCreateController.enqueue(from, SpecialEffectsController.Operation.LifecycleImpact.ADDING, this);
                                    }
                                    this.mFragment.mState = 4;
                                    break;
                                case 5:
                                    start();
                                    break;
                                case FalsingManager.VERSION /*6*/:
                                    fragment.mState = 6;
                                    break;
                                case 7:
                                    resume();
                                    break;
                            }
                        }
                    } else {
                        if (fragment.mHiddenChanged) {
                            if (!(fragment.mView == null || (viewGroup = fragment.mContainer) == null)) {
                                SpecialEffectsController orCreateController2 = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager().getSpecialEffectsControllerFactory());
                                if (this.mFragment.mHidden) {
                                    orCreateController2.enqueueHide(this);
                                } else {
                                    orCreateController2.enqueueShow(this);
                                }
                            }
                            Fragment fragment4 = this.mFragment;
                            FragmentManager fragmentManager = fragment4.mFragmentManager;
                            if (fragmentManager != null && fragment4.mAdded && FragmentManager.isMenuAvailable(fragment4)) {
                                fragmentManager.mNeedMenuInvalidate = z;
                            }
                            this.mFragment.mHiddenChanged = z;
                        }
                        this.mMovingToState = z;
                        return;
                    }
                }
            } finally {
                this.mMovingToState = z;
            }
        } else if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Ignoring re-entrant call to moveToExpectedState() for ");
            m.append(this.mFragment);
            Log.v("FragmentManager", m.toString());
        }
    }

    public final void restoreState(ClassLoader classLoader) {
        Bundle bundle = this.mFragment.mSavedFragmentState;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
            Fragment fragment = this.mFragment;
            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
            Fragment fragment2 = this.mFragment;
            fragment2.mSavedViewRegistryState = fragment2.mSavedFragmentState.getBundle("android:view_registry_state");
            Fragment fragment3 = this.mFragment;
            fragment3.mTargetWho = fragment3.mSavedFragmentState.getString("android:target_state");
            Fragment fragment4 = this.mFragment;
            if (fragment4.mTargetWho != null) {
                fragment4.mTargetRequestCode = fragment4.mSavedFragmentState.getInt("android:target_req_state", 0);
            }
            Fragment fragment5 = this.mFragment;
            Objects.requireNonNull(fragment5);
            fragment5.mUserVisibleHint = fragment5.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
            Fragment fragment6 = this.mFragment;
            if (!fragment6.mUserVisibleHint) {
                fragment6.mDeferStart = true;
            }
        }
    }

    public final void saveState() {
        FragmentState fragmentState = new FragmentState(this.mFragment);
        Fragment fragment = this.mFragment;
        if (fragment.mState <= -1 || fragmentState.mSavedFragmentState != null) {
            fragmentState.mSavedFragmentState = fragment.mSavedFragmentState;
        } else {
            Bundle bundle = new Bundle();
            Fragment fragment2 = this.mFragment;
            Objects.requireNonNull(fragment2);
            fragment2.onSaveInstanceState(bundle);
            fragment2.mSavedStateRegistryController.performSave(bundle);
            Parcelable saveAllStateInternal = fragment2.mChildFragmentManager.saveAllStateInternal();
            if (saveAllStateInternal != null) {
                bundle.putParcelable("android:support:fragments", saveAllStateInternal);
            }
            this.mDispatcher.dispatchOnFragmentSaveInstanceState(false);
            if (bundle.isEmpty()) {
                bundle = null;
            }
            if (this.mFragment.mView != null) {
                saveViewState();
            }
            if (this.mFragment.mSavedViewState != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putSparseParcelableArray("android:view_state", this.mFragment.mSavedViewState);
            }
            if (this.mFragment.mSavedViewRegistryState != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBundle("android:view_registry_state", this.mFragment.mSavedViewRegistryState);
            }
            if (!this.mFragment.mUserVisibleHint) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBoolean("android:user_visible_hint", this.mFragment.mUserVisibleHint);
            }
            fragmentState.mSavedFragmentState = bundle;
            if (this.mFragment.mTargetWho != null) {
                if (bundle == null) {
                    fragmentState.mSavedFragmentState = new Bundle();
                }
                fragmentState.mSavedFragmentState.putString("android:target_state", this.mFragment.mTargetWho);
                int i = this.mFragment.mTargetRequestCode;
                if (i != 0) {
                    fragmentState.mSavedFragmentState.putInt("android:target_req_state", i);
                }
            }
        }
        this.mFragmentStore.setSavedState(this.mFragment.mWho, fragmentState);
    }

    public final void saveViewState() {
        if (this.mFragment.mView != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.mFragment.mView.saveHierarchyState(sparseArray);
            if (sparseArray.size() > 0) {
                this.mFragment.mSavedViewState = sparseArray;
            }
            Bundle bundle = new Bundle();
            FragmentViewLifecycleOwner fragmentViewLifecycleOwner = this.mFragment.mViewLifecycleOwner;
            Objects.requireNonNull(fragmentViewLifecycleOwner);
            fragmentViewLifecycleOwner.mSavedStateRegistryController.performSave(bundle);
            if (!bundle.isEmpty()) {
                this.mFragment.mSavedViewRegistryState = bundle;
            }
        }
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, ClassLoader classLoader, FragmentFactory fragmentFactory, FragmentState fragmentState) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        Fragment instantiate = fragmentFactory.instantiate(fragmentState.mClassName);
        Bundle bundle = fragmentState.mArguments;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
        instantiate.setArguments(fragmentState.mArguments);
        instantiate.mWho = fragmentState.mWho;
        instantiate.mFromLayout = fragmentState.mFromLayout;
        instantiate.mRestored = true;
        instantiate.mFragmentId = fragmentState.mFragmentId;
        instantiate.mContainerId = fragmentState.mContainerId;
        instantiate.mTag = fragmentState.mTag;
        instantiate.mRetainInstance = fragmentState.mRetainInstance;
        instantiate.mRemoving = fragmentState.mRemoving;
        instantiate.mDetached = fragmentState.mDetached;
        instantiate.mHidden = fragmentState.mHidden;
        instantiate.mMaxState = Lifecycle.State.values()[fragmentState.mMaxLifecycleState];
        Bundle bundle2 = fragmentState.mSavedFragmentState;
        if (bundle2 != null) {
            instantiate.mSavedFragmentState = bundle2;
        } else {
            instantiate.mSavedFragmentState = new Bundle();
        }
        this.mFragment = instantiate;
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Instantiated fragment " + instantiate);
        }
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, Fragment fragment, FragmentState fragmentState) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
        fragment.mBackStackNesting = 0;
        fragment.mInLayout = false;
        fragment.mAdded = false;
        Fragment fragment2 = fragment.mTarget;
        fragment.mTargetWho = fragment2 != null ? fragment2.mWho : null;
        fragment.mTarget = null;
        Bundle bundle = fragmentState.mSavedFragmentState;
        if (bundle != null) {
            fragment.mSavedFragmentState = bundle;
        } else {
            fragment.mSavedFragmentState = new Bundle();
        }
    }
}
