package com.android.systemui.statusbar.notification.row;

import android.transition.Transition;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelEditorListView$updateRows$1 implements Transition.TransitionListener {
    public final /* synthetic */ ChannelEditorListView this$0;

    public final void onTransitionCancel(Transition transition) {
    }

    public final void onTransitionPause(Transition transition) {
    }

    public final void onTransitionResume(Transition transition) {
    }

    public final void onTransitionStart(Transition transition) {
    }

    public ChannelEditorListView$updateRows$1(ChannelEditorListView channelEditorListView) {
        this.this$0 = channelEditorListView;
    }

    public final void onTransitionEnd(Transition transition) {
        this.this$0.notifySubtreeAccessibilityStateChangedIfNeeded();
    }
}
