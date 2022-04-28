package com.android.systemui.communal;

import com.google.common.util.concurrent.ListenableFuture;

public interface CommunalSource {

    public static class CommunalViewResult {
    }

    ListenableFuture requestCommunalView();
}
