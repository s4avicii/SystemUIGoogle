package com.google.android.systemui.communal.dock.callbacks.mediashell.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaShellModule_ProvidesMediaShellViewFactory implements Factory<View> {
    public final Provider<Context> contextProvider;
    public final Provider<String> nameProvider;

    public final Object get() {
        View inflate = LayoutInflater.from(this.contextProvider.get()).inflate(C1777R.layout.media_shell_complication_layout, (ViewGroup) null);
        ((TextView) inflate.findViewById(C1777R.C1779id.media_shell_device_title)).setText(this.nameProvider.get());
        return inflate;
    }

    public MediaShellModule_ProvidesMediaShellViewFactory(Provider<Context> provider, Provider<String> provider2) {
        this.contextProvider = provider;
        this.nameProvider = provider2;
    }
}
