package androidx.mediarouter.media;

import android.media.MediaRouter;
import androidx.mediarouter.media.MediaRouterJellybean$VolumeCallback;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import java.util.Objects;

public final class MediaRouterJellybean$VolumeCallbackProxy<T extends MediaRouterJellybean$VolumeCallback> extends MediaRouter.VolumeCallback {
    public final T mCallback;

    public final void onVolumeSetRequest(MediaRouter.RouteInfo routeInfo, int i) {
        Objects.requireNonNull((SystemMediaRouteProvider.JellybeanImpl) this.mCallback);
        SystemMediaRouteProvider.JellybeanImpl.UserRouteRecord userRouteRecord = SystemMediaRouteProvider.JellybeanImpl.getUserRouteRecord(routeInfo);
        if (userRouteRecord != null) {
            userRouteRecord.mRoute.requestSetVolume(i);
        }
    }

    public final void onVolumeUpdateRequest(MediaRouter.RouteInfo routeInfo, int i) {
        Objects.requireNonNull((SystemMediaRouteProvider.JellybeanImpl) this.mCallback);
        SystemMediaRouteProvider.JellybeanImpl.UserRouteRecord userRouteRecord = SystemMediaRouteProvider.JellybeanImpl.getUserRouteRecord(routeInfo);
        if (userRouteRecord != null) {
            userRouteRecord.mRoute.requestUpdateVolume(i);
        }
    }

    public MediaRouterJellybean$VolumeCallbackProxy(SystemMediaRouteProvider.JellybeanMr1Impl jellybeanMr1Impl) {
        this.mCallback = jellybeanMr1Impl;
    }
}
