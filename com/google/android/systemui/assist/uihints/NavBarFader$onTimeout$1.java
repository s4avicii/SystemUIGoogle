package com.google.android.systemui.assist.uihints;

/* compiled from: NavBarFader.kt */
public final class NavBarFader$onTimeout$1 implements Runnable {
    public final /* synthetic */ NavBarFader this$0;

    public NavBarFader$onTimeout$1(NavBarFader navBarFader) {
        this.this$0 = navBarFader;
    }

    public final void run() {
        this.this$0.onVisibleRequest(true);
    }
}
