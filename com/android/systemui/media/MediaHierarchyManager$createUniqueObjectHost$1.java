package com.android.systemui.media;

import android.view.View;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaHierarchyManager.kt */
public final class MediaHierarchyManager$createUniqueObjectHost$1 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ UniqueObjectHostView $viewHost;
    public final /* synthetic */ MediaHierarchyManager this$0;

    public final void onViewDetachedFromWindow(View view) {
    }

    public MediaHierarchyManager$createUniqueObjectHost$1(MediaHierarchyManager mediaHierarchyManager, UniqueObjectHostView uniqueObjectHostView) {
        this.this$0 = mediaHierarchyManager;
        this.$viewHost = uniqueObjectHostView;
    }

    public final void onViewAttachedToWindow(View view) {
        MediaHierarchyManager mediaHierarchyManager = this.this$0;
        if (mediaHierarchyManager.rootOverlay == null) {
            mediaHierarchyManager.rootView = this.$viewHost.getViewRootImpl().getView();
            MediaHierarchyManager mediaHierarchyManager2 = this.this$0;
            View view2 = mediaHierarchyManager2.rootView;
            Intrinsics.checkNotNull(view2);
            ViewOverlay overlay = view2.getOverlay();
            Objects.requireNonNull(overlay, "null cannot be cast to non-null type android.view.ViewGroupOverlay");
            mediaHierarchyManager2.rootOverlay = (ViewGroupOverlay) overlay;
        }
        this.$viewHost.removeOnAttachStateChangeListener(this);
    }
}
