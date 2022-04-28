package com.android.systemui.util.service;

public interface Observer {

    public interface Callback {
        void onSourceChanged();
    }

    void addCallback(PersistentConnectionManager$$ExternalSyntheticLambda0 persistentConnectionManager$$ExternalSyntheticLambda0);

    void removeCallback(PersistentConnectionManager$$ExternalSyntheticLambda0 persistentConnectionManager$$ExternalSyntheticLambda0);
}
