package com.android.systemui.statusbar.notification.row;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.android.internal.widget.LocalImageResolver;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public final class NotificationInlineImageCache implements NotificationInlineImageResolver.ImageCache {
    public final ConcurrentHashMap<Uri, PreloadImageTask> mCache = new ConcurrentHashMap<>();
    public NotificationInlineImageResolver mResolver;

    public static class PreloadImageTask extends AsyncTask<Uri, Void, Drawable> {
        public final NotificationInlineImageResolver mResolver;

        public final Object doInBackground(Object[] objArr) {
            Uri uri = ((Uri[]) objArr)[0];
            try {
                NotificationInlineImageResolver notificationInlineImageResolver = this.mResolver;
                Objects.requireNonNull(notificationInlineImageResolver);
                return LocalImageResolver.resolveImage(uri, notificationInlineImageResolver.mContext, notificationInlineImageResolver.mMaxImageWidth, notificationInlineImageResolver.mMaxImageHeight);
            } catch (IOException | SecurityException e) {
                Log.d("NotificationInlineImageCache", "PreloadImageTask: Resolve failed from " + uri, e);
                return null;
            }
        }

        public PreloadImageTask(NotificationInlineImageResolver notificationInlineImageResolver) {
            this.mResolver = notificationInlineImageResolver;
        }
    }

    public final Drawable get(Uri uri) {
        try {
            return (Drawable) this.mCache.get(uri).get();
        } catch (InterruptedException | ExecutionException unused) {
            Log.d("NotificationInlineImageCache", "get: Failed get image from " + uri);
            return null;
        }
    }
}
