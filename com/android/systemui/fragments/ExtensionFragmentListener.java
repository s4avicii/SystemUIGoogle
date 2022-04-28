package com.android.systemui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.FragmentBase;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.Objects;
import java.util.function.Consumer;

public final class ExtensionFragmentListener<T extends FragmentBase> implements Consumer<T> {
    public final ExtensionController.Extension<T> mExtension;
    public final FragmentHostManager mFragmentHostManager;
    public final int mId;
    public String mOldClass;
    public final String mTag = C0961QS.TAG;

    public final void accept(Object obj) {
        FragmentBase fragmentBase = (FragmentBase) obj;
        try {
            Fragment.class.cast(fragmentBase);
            FragmentHostManager fragmentHostManager = this.mFragmentHostManager;
            Objects.requireNonNull(fragmentHostManager);
            FragmentHostManager.ExtensionFragmentManager extensionFragmentManager = fragmentHostManager.mPlugins;
            int i = this.mId;
            String str = this.mTag;
            String str2 = this.mOldClass;
            String name = fragmentBase.getClass().getName();
            ExtensionControllerImpl.ExtensionImpl extensionImpl = (ExtensionControllerImpl.ExtensionImpl) this.mExtension;
            Objects.requireNonNull(extensionImpl);
            Context context = extensionImpl.mPluginContext;
            if (context == null) {
                context = ExtensionControllerImpl.this.mDefaultContext;
            }
            Objects.requireNonNull(extensionFragmentManager);
            if (str2 != null) {
                extensionFragmentManager.mExtensionLookup.remove(str2);
            }
            extensionFragmentManager.mExtensionLookup.put(name, context);
            FragmentHostManager.this.getFragmentManager().beginTransaction().replace(i, extensionFragmentManager.instantiate(context, name, (Bundle) null), str).commit();
            FragmentHostManager.this.reloadFragments();
            this.mOldClass = fragmentBase.getClass().getName();
        } catch (ClassCastException e) {
            Log.e("ExtensionFragmentListener", fragmentBase.getClass().getName() + " must be a Fragment", e);
        }
        ExtensionControllerImpl.ExtensionImpl extensionImpl2 = (ExtensionControllerImpl.ExtensionImpl) this.mExtension;
        Objects.requireNonNull(extensionImpl2);
        T t = extensionImpl2.mItem;
        if (t != null) {
            ExtensionControllerImpl.this.mLeakDetector.trackGarbage(t);
        }
        extensionImpl2.mItem = null;
    }

    public ExtensionFragmentListener(View view, ExtensionControllerImpl.ExtensionImpl extensionImpl) {
        FragmentHostManager fragmentHostManager = FragmentHostManager.get(view);
        this.mFragmentHostManager = fragmentHostManager;
        this.mExtension = extensionImpl;
        this.mId = C1777R.C1779id.qs_frame;
        FragmentTransaction beginTransaction = fragmentHostManager.getFragmentManager().beginTransaction();
        Objects.requireNonNull(extensionImpl);
        beginTransaction.replace(C1777R.C1779id.qs_frame, (Fragment) extensionImpl.mItem, C0961QS.TAG).commit();
        Objects.requireNonNull(extensionImpl);
        extensionImpl.mItem = null;
    }
}
