package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

public abstract class Invalidator extends Pluggable<Invalidator> {
    public Invalidator() {
        super("SensitiveContentInvalidator");
    }
}
