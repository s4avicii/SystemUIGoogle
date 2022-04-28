package com.android.systemui.statusbar.notification.collection.render;

import dagger.internal.Factory;

public final class RenderStageManager_Factory implements Factory<RenderStageManager> {

    public static final class InstanceHolder {
        public static final RenderStageManager_Factory INSTANCE = new RenderStageManager_Factory();
    }

    public final Object get() {
        return new RenderStageManager();
    }
}
