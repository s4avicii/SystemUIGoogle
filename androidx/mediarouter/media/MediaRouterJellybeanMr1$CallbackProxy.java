package androidx.mediarouter.media;

import android.media.MediaRouter;
import androidx.mediarouter.media.MediaRouterJellybeanMr1$Callback;

public final class MediaRouterJellybeanMr1$CallbackProxy<T extends MediaRouterJellybeanMr1$Callback> extends MediaRouterJellybean$CallbackProxy<T> {
    public final void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        ((MediaRouterJellybeanMr1$Callback) this.mCallback).onRoutePresentationDisplayChanged(routeInfo);
    }

    public MediaRouterJellybeanMr1$CallbackProxy(T t) {
        super(t);
    }
}
