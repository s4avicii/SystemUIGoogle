package com.android.systemui.statusbar.notification.row;

import android.app.INotificationManager;
import android.content.Context;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialog;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialog_Builder_Factory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ChannelEditorDialogController_Factory implements Factory<ChannelEditorDialogController> {
    public final Provider<Context> cProvider;
    public final Provider<ChannelEditorDialog.Builder> dialogBuilderProvider;
    public final Provider<INotificationManager> noManProvider;

    public ChannelEditorDialogController_Factory(Provider provider, Provider provider2) {
        ChannelEditorDialog_Builder_Factory channelEditorDialog_Builder_Factory = ChannelEditorDialog_Builder_Factory.InstanceHolder.INSTANCE;
        this.cProvider = provider;
        this.noManProvider = provider2;
        this.dialogBuilderProvider = channelEditorDialog_Builder_Factory;
    }

    public final Object get() {
        return new ChannelEditorDialogController(this.cProvider.get(), this.noManProvider.get(), this.dialogBuilderProvider.get());
    }
}
