package com.android.systemui.fragments;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.transition.OneShotRemoteHandler$2$$ExternalSyntheticLambda0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

public final class FragmentService implements Dumpable {
    public C08081 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            for (FragmentHostState next : FragmentService.this.mHosts.values()) {
                Objects.requireNonNull(next);
                FragmentService.this.mHandler.post(new OneShotRemoteHandler$2$$ExternalSyntheticLambda0(next, configuration, 1));
            }
        }
    };
    public final Handler mHandler = new Handler();
    public final ArrayMap<View, FragmentHostState> mHosts = new ArrayMap<>();
    public final ArrayMap<String, FragmentInstantiationInfo> mInjectionMap = new ArrayMap<>();

    public interface FragmentCreator {

        public interface Factory {
            FragmentCreator build();
        }

        QSFragment createQSFragment();
    }

    public class FragmentHostState {
        public FragmentHostManager mFragmentHostManager;
        public final View mView;

        public FragmentHostState(View view) {
            this.mView = view;
            this.mFragmentHostManager = new FragmentHostManager(FragmentService.this, view);
        }
    }

    public static class FragmentInstantiationInfo {
        public final Object mDaggerComponent;
        public final Method mMethod;

        public FragmentInstantiationInfo(Method method, Object obj) {
            this.mMethod = method;
            this.mDaggerComponent = obj;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("Dumping fragments:");
        for (FragmentHostState fragmentHostState : this.mHosts.values()) {
            fragmentHostState.mFragmentHostManager.getFragmentManager().dump("  ", fileDescriptor, printWriter, strArr);
        }
    }

    public FragmentService(FragmentCreator.Factory factory, ConfigurationController configurationController, DumpManager dumpManager) {
        addFragmentInstantiationProvider(factory.build());
        configurationController.addCallback(this.mConfigurationListener);
        dumpManager.registerDumpable("FragmentService", this);
    }

    public final void addFragmentInstantiationProvider(Object obj) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (Fragment.class.isAssignableFrom(method.getReturnType()) && (method.getModifiers() & 1) != 0) {
                String name = method.getReturnType().getName();
                if (this.mInjectionMap.containsKey(name)) {
                    Log.w("FragmentService", "Fragment " + name + " is already provided by different Dagger component; Not adding method");
                } else {
                    this.mInjectionMap.put(name, new FragmentInstantiationInfo(method, obj));
                }
            }
        }
    }

    public final FragmentHostManager getFragmentHostManager(View view) {
        View rootView = view.getRootView();
        FragmentHostState fragmentHostState = this.mHosts.get(rootView);
        if (fragmentHostState == null) {
            fragmentHostState = new FragmentHostState(rootView);
            this.mHosts.put(rootView, fragmentHostState);
        }
        return fragmentHostState.mFragmentHostManager;
    }
}
