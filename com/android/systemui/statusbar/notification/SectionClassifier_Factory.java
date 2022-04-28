package com.android.systemui.statusbar.notification;

import dagger.internal.Factory;

public final class SectionClassifier_Factory implements Factory<SectionClassifier> {

    public static final class InstanceHolder {
        public static final SectionClassifier_Factory INSTANCE = new SectionClassifier_Factory();
    }

    public final Object get() {
        return new SectionClassifier();
    }
}
