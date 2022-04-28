package com.android.systemui.statusbar.notification.collection.render;

/* compiled from: NotifPanelEventSource.kt */
public interface NotifPanelEventSource {

    /* compiled from: NotifPanelEventSource.kt */
    public interface Callbacks {
        void onPanelCollapsingChanged(boolean z);
    }

    void registerCallbacks(Callbacks callbacks);

    void unregisterCallbacks(Callbacks callbacks);
}
