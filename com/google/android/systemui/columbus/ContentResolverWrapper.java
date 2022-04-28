package com.google.android.systemui.columbus;

import android.content.ContentResolver;
import android.content.Context;

/* compiled from: ContentResolverWrapper.kt */
public final class ContentResolverWrapper {
    public final ContentResolver contentResolver;

    public ContentResolverWrapper(Context context) {
        this.contentResolver = context.getContentResolver();
    }
}
