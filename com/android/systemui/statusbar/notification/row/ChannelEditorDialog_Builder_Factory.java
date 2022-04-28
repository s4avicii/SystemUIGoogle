package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.row.ChannelEditorDialog;
import dagger.internal.Factory;

public final class ChannelEditorDialog_Builder_Factory implements Factory<ChannelEditorDialog.Builder> {

    public static final class InstanceHolder {
        public static final ChannelEditorDialog_Builder_Factory INSTANCE = new ChannelEditorDialog_Builder_Factory();
    }

    public final Object get() {
        return new ChannelEditorDialog.Builder();
    }
}
