package com.android.systemui.fragments;

import android.app.Fragment;
import android.app.FragmentController;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Trace;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dependency;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.util.leak.LeakDetector;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class FragmentHostManager {
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public FragmentController mFragments;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public C08071 mLifecycleCallbacks;
    public final HashMap<String, ArrayList<FragmentListener>> mListeners = new HashMap<>();
    public final FragmentService mManager;
    public final ExtensionFragmentManager mPlugins;
    public final View mRootView;

    public class ExtensionFragmentManager {
        public final ArrayMap<String, Context> mExtensionLookup = new ArrayMap<>();

        public ExtensionFragmentManager() {
        }

        public final Fragment instantiate(Context context, String str, Bundle bundle) {
            Context context2 = this.mExtensionLookup.get(str);
            if (context2 == null) {
                return instantiateWithInjections(context, str, bundle);
            }
            Fragment instantiateWithInjections = instantiateWithInjections(context2, str, bundle);
            if (instantiateWithInjections instanceof Plugin) {
                ((Plugin) instantiateWithInjections).onCreate(FragmentHostManager.this.mContext, context2);
            }
            return instantiateWithInjections;
        }

        public final Fragment instantiateWithInjections(Context context, String str, Bundle bundle) {
            FragmentService fragmentService = FragmentHostManager.this.mManager;
            Objects.requireNonNull(fragmentService);
            FragmentService.FragmentInstantiationInfo fragmentInstantiationInfo = fragmentService.mInjectionMap.get(str);
            if (fragmentInstantiationInfo == null) {
                return Fragment.instantiate(context, str, bundle);
            }
            try {
                Fragment fragment = (Fragment) fragmentInstantiationInfo.mMethod.invoke(fragmentInstantiationInfo.mDaggerComponent, new Object[0]);
                if (bundle != null) {
                    bundle.setClassLoader(fragment.getClass().getClassLoader());
                    fragment.setArguments(bundle);
                }
                return fragment;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new Fragment.InstantiationException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("Unable to instantiate ", str), e);
            }
        }
    }

    public interface FragmentListener {
        void onFragmentViewCreated(Fragment fragment);

        void onFragmentViewDestroyed(Fragment fragment) {
        }
    }

    public class HostCallbacks extends FragmentHostCallback<FragmentHostManager> {
        public final void onAttachFragment(Fragment fragment) {
        }

        public final int onGetWindowAnimations() {
            return 0;
        }

        public final boolean onHasView() {
            return true;
        }

        public final boolean onHasWindowAnimations() {
            return false;
        }

        public final boolean onShouldSaveFragmentState(Fragment fragment) {
            return true;
        }

        public final boolean onUseFragmentManagerInflaterFactory() {
            return true;
        }

        public HostCallbacks() {
            super(FragmentHostManager.this.mContext, FragmentHostManager.this.mHandler, 0);
        }

        public final Fragment instantiate(Context context, String str, Bundle bundle) {
            return FragmentHostManager.this.mPlugins.instantiate(context, str, bundle);
        }

        public final void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Objects.requireNonNull(FragmentHostManager.this);
        }

        public final <T extends View> T onFindViewById(int i) {
            FragmentHostManager fragmentHostManager = FragmentHostManager.this;
            Objects.requireNonNull(fragmentHostManager);
            return fragmentHostManager.mRootView.findViewById(i);
        }

        public final LayoutInflater onGetLayoutInflater() {
            return LayoutInflater.from(FragmentHostManager.this.mContext);
        }

        public final Object onGetHost() {
            return FragmentHostManager.this;
        }
    }

    public static FragmentHostManager get(View view) {
        try {
            return ((FragmentService) Dependency.get(FragmentService.class)).getFragmentHostManager(view);
        } catch (ClassCastException e) {
            throw e;
        }
    }

    public final FragmentHostManager addTagListener(String str, FragmentListener fragmentListener) {
        ArrayList arrayList = this.mListeners.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mListeners.put(str, arrayList);
        }
        arrayList.add(fragmentListener);
        Fragment findFragmentByTag = getFragmentManager().findFragmentByTag(str);
        if (!(findFragmentByTag == null || findFragmentByTag.getView() == null)) {
            fragmentListener.onFragmentViewCreated(findFragmentByTag);
        }
        return this;
    }

    public final void createFragmentHost(Parcelable parcelable) {
        FragmentController createController = FragmentController.createController(new HostCallbacks());
        this.mFragments = createController;
        createController.attachHost((Fragment) null);
        this.mLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
            public final void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                ((LeakDetector) Dependency.get(LeakDetector.class)).trackGarbage(fragment);
            }

            public final void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
                FragmentHostManager fragmentHostManager = FragmentHostManager.this;
                Objects.requireNonNull(fragmentHostManager);
                String tag = fragment.getTag();
                ArrayList arrayList = fragmentHostManager.mListeners.get(tag);
                if (arrayList != null) {
                    arrayList.forEach(new FragmentHostManager$$ExternalSyntheticLambda0(tag, fragment));
                }
            }

            public final void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                FragmentHostManager fragmentHostManager = FragmentHostManager.this;
                Objects.requireNonNull(fragmentHostManager);
                String tag = fragment.getTag();
                ArrayList arrayList = fragmentHostManager.mListeners.get(tag);
                if (arrayList != null) {
                    arrayList.forEach(new FragmentHostManager$$ExternalSyntheticLambda1(tag, fragment));
                }
            }
        };
        this.mFragments.getFragmentManager().registerFragmentLifecycleCallbacks(this.mLifecycleCallbacks, true);
        if (parcelable != null) {
            this.mFragments.restoreAllState(parcelable, (FragmentManagerNonConfig) null);
        }
        this.mFragments.dispatchCreate();
        this.mFragments.dispatchStart();
        this.mFragments.dispatchResume();
    }

    public final FragmentManager getFragmentManager() {
        return this.mFragments.getFragmentManager();
    }

    public final void reloadFragments() {
        Trace.beginSection("FrargmentHostManager#reloadFragments");
        this.mFragments.dispatchPause();
        Parcelable saveAllState = this.mFragments.saveAllState();
        this.mFragments.dispatchStop();
        this.mFragments.dispatchDestroy();
        this.mFragments.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.mLifecycleCallbacks);
        createFragmentHost(saveAllState);
        Trace.endSection();
    }

    public FragmentHostManager(FragmentService fragmentService, View view) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-1073733628);
        this.mConfigChanges = interestingConfigChanges;
        this.mPlugins = new ExtensionFragmentManager();
        Context context = view.getContext();
        this.mContext = context;
        this.mManager = fragmentService;
        this.mRootView = view;
        interestingConfigChanges.applyNewConfig(context.getResources());
        createFragmentHost((Parcelable) null);
    }
}
