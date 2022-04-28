package com.android.systemui.media;

import android.content.Context;

public final class ResumeMediaBrowserFactory {
    public final MediaBrowserFactory mBrowserFactory;
    public final Context mContext;

    public ResumeMediaBrowserFactory(Context context, MediaBrowserFactory mediaBrowserFactory) {
        this.mContext = context;
        this.mBrowserFactory = mediaBrowserFactory;
    }
}
