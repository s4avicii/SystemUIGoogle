package com.android.systemui.unfold.updates.hinge;

import androidx.core.util.Consumer;

/* compiled from: EmptyHingeAngleProvider.kt */
public final class EmptyHingeAngleProvider implements HingeAngleProvider {
    public static final EmptyHingeAngleProvider INSTANCE = new EmptyHingeAngleProvider();

    public final void start() {
    }

    public final void stop() {
    }

    public final /* bridge */ /* synthetic */ void addCallback(Object obj) {
        Consumer consumer = (Consumer) obj;
    }

    public final /* bridge */ /* synthetic */ void removeCallback(Object obj) {
        Consumer consumer = (Consumer) obj;
    }
}
