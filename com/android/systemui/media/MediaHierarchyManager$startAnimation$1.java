package com.android.systemui.media;

/* compiled from: MediaHierarchyManager.kt */
public final class MediaHierarchyManager$startAnimation$1 implements Runnable {
    public final /* synthetic */ MediaHierarchyManager this$0;

    public MediaHierarchyManager$startAnimation$1(MediaHierarchyManager mediaHierarchyManager) {
        this.this$0 = mediaHierarchyManager;
    }

    public final void run() {
        this.this$0.animator.start();
    }
}
