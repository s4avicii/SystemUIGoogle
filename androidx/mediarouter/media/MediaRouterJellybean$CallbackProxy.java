package androidx.mediarouter.media;

import android.media.MediaRouter;
import androidx.mediarouter.media.MediaRouterJellybean$Callback;

public class MediaRouterJellybean$CallbackProxy<T extends MediaRouterJellybean$Callback> extends MediaRouter.Callback {
    public final T mCallback;

    public final void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.onRouteAdded(routeInfo);
    }

    public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.onRouteChanged(routeInfo);
    }

    public final void onRouteGrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup, int i) {
        this.mCallback.onRouteGrouped();
    }

    public final void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.onRouteRemoved(routeInfo);
    }

    public final void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.onRouteSelected(routeInfo);
    }

    public final void onRouteUngrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup) {
        this.mCallback.onRouteUngrouped();
    }

    public final void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.onRouteUnselected();
    }

    public final void onRouteVolumeChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.onRouteVolumeChanged(routeInfo);
    }

    public MediaRouterJellybean$CallbackProxy(T t) {
        this.mCallback = t;
    }
}
