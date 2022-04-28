package com.android.systemui.statusbar.events;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.preference.R$id;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController$scheduleUpdate$1 implements Runnable {
    public final /* synthetic */ PrivacyDotViewController this$0;

    public PrivacyDotViewController$scheduleUpdate$1(PrivacyDotViewController privacyDotViewController) {
        this.this$0 = privacyDotViewController;
    }

    public final void run() {
        ViewState copy$default;
        boolean z;
        boolean z2;
        View view;
        View view2;
        View view3;
        boolean z3;
        int i;
        int i2;
        int i3;
        PrivacyDotViewController privacyDotViewController = this.this$0;
        Objects.requireNonNull(privacyDotViewController);
        synchronized (privacyDotViewController.lock) {
            copy$default = ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, 0, (View) null, (String) null, 16383);
        }
        Intrinsics.stringPlus("resolveState ", copy$default);
        if (copy$default.viewInitialized && !Intrinsics.areEqual(copy$default, privacyDotViewController.currentViewState)) {
            int i4 = copy$default.rotation;
            ViewState viewState = privacyDotViewController.currentViewState;
            Objects.requireNonNull(viewState);
            boolean z4 = true;
            if (i4 != viewState.rotation) {
                int i5 = copy$default.rotation;
                int i6 = copy$default.paddingTop;
                for (View next : privacyDotViewController.getViews()) {
                    next.setPadding(0, i6, 0, 0);
                    int cornerForView = privacyDotViewController.cornerForView(next) - i5;
                    if (cornerForView < 0) {
                        cornerForView += 4;
                    }
                    ViewGroup.LayoutParams layoutParams = next.getLayoutParams();
                    Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                    if (cornerForView == 0) {
                        i3 = 51;
                    } else if (cornerForView == 1) {
                        i3 = 53;
                    } else if (cornerForView == 2) {
                        i3 = 85;
                    } else if (cornerForView == 3) {
                        i3 = 83;
                    } else {
                        throw new IllegalArgumentException("Not a corner");
                    }
                    layoutParams2.gravity = i3;
                    ViewGroup.LayoutParams layoutParams3 = next.findViewById(C1777R.C1779id.privacy_dot).getLayoutParams();
                    Objects.requireNonNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                    FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                    int i7 = 19;
                    if (cornerForView != 0) {
                        if (!(cornerForView == 1 || cornerForView == 2)) {
                            if (cornerForView != 3) {
                                throw new IllegalArgumentException("Not a corner");
                            }
                        }
                        layoutParams4.gravity = i7;
                    }
                    i7 = 21;
                    layoutParams4.gravity = i7;
                }
            }
            ViewState viewState2 = privacyDotViewController.currentViewState;
            if (copy$default.rotation != viewState2.rotation || copy$default.layoutRtl != viewState2.layoutRtl || !Intrinsics.areEqual(copy$default.portraitRect, viewState2.portraitRect) || !Intrinsics.areEqual(copy$default.landscapeRect, viewState2.landscapeRect) || !Intrinsics.areEqual(copy$default.upsideDownRect, viewState2.upsideDownRect) || !Intrinsics.areEqual(copy$default.seascapeRect, viewState2.seascapeRect)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                boolean z5 = copy$default.layoutRtl;
                Point point = new Point();
                View view4 = privacyDotViewController.f78tl;
                if (view4 == null) {
                    view4 = null;
                }
                view4.getContext().getDisplay().getRealSize(point);
                View view5 = privacyDotViewController.f78tl;
                if (view5 == null) {
                    view5 = null;
                }
                int exactRotation = R$id.getExactRotation(view5.getContext());
                if (exactRotation == 1 || exactRotation == 3) {
                    i2 = point.y;
                    i = point.x;
                } else {
                    i2 = point.x;
                    i = point.y;
                }
                View view6 = privacyDotViewController.f78tl;
                if (view6 == null) {
                    view6 = null;
                }
                Rect contentRectForRotation = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view6, z5));
                View view7 = privacyDotViewController.f78tl;
                if (view7 == null) {
                    view7 = null;
                }
                view7.setPadding(0, copy$default.paddingTop, 0, 0);
                View view8 = privacyDotViewController.f78tl;
                if (view8 == null) {
                    view8 = null;
                }
                ViewGroup.LayoutParams layoutParams5 = view8.getLayoutParams();
                Objects.requireNonNull(layoutParams5, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) layoutParams5;
                layoutParams6.height = contentRectForRotation.height();
                if (z5) {
                    layoutParams6.width = contentRectForRotation.left;
                } else {
                    layoutParams6.width = i - contentRectForRotation.right;
                }
                View view9 = privacyDotViewController.f79tr;
                if (view9 == null) {
                    view9 = null;
                }
                Rect contentRectForRotation2 = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view9, z5));
                View view10 = privacyDotViewController.f79tr;
                if (view10 == null) {
                    view10 = null;
                }
                view10.setPadding(0, copy$default.paddingTop, 0, 0);
                View view11 = privacyDotViewController.f79tr;
                if (view11 == null) {
                    view11 = null;
                }
                ViewGroup.LayoutParams layoutParams7 = view11.getLayoutParams();
                Objects.requireNonNull(layoutParams7, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) layoutParams7;
                layoutParams8.height = contentRectForRotation2.height();
                if (z5) {
                    layoutParams8.width = contentRectForRotation2.left;
                } else {
                    layoutParams8.width = i2 - contentRectForRotation2.right;
                }
                View view12 = privacyDotViewController.f77br;
                if (view12 == null) {
                    view12 = null;
                }
                Rect contentRectForRotation3 = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view12, z5));
                View view13 = privacyDotViewController.f77br;
                if (view13 == null) {
                    view13 = null;
                }
                view13.setPadding(0, copy$default.paddingTop, 0, 0);
                View view14 = privacyDotViewController.f77br;
                if (view14 == null) {
                    view14 = null;
                }
                ViewGroup.LayoutParams layoutParams9 = view14.getLayoutParams();
                Objects.requireNonNull(layoutParams9, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams10 = (FrameLayout.LayoutParams) layoutParams9;
                layoutParams10.height = contentRectForRotation3.height();
                if (z5) {
                    layoutParams10.width = contentRectForRotation3.left;
                } else {
                    layoutParams10.width = i - contentRectForRotation3.right;
                }
                View view15 = privacyDotViewController.f76bl;
                if (view15 == null) {
                    view15 = null;
                }
                Rect contentRectForRotation4 = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view15, z5));
                View view16 = privacyDotViewController.f76bl;
                if (view16 == null) {
                    view16 = null;
                }
                view16.setPadding(0, copy$default.paddingTop, 0, 0);
                View view17 = privacyDotViewController.f76bl;
                if (view17 == null) {
                    view17 = null;
                }
                ViewGroup.LayoutParams layoutParams11 = view17.getLayoutParams();
                Objects.requireNonNull(layoutParams11, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams12 = (FrameLayout.LayoutParams) layoutParams11;
                layoutParams12.height = contentRectForRotation4.height();
                if (z5) {
                    layoutParams12.width = contentRectForRotation4.left;
                } else {
                    layoutParams12.width = i2 - contentRectForRotation4.right;
                }
                for (View requestLayout : privacyDotViewController.getViews()) {
                    requestLayout.requestLayout();
                }
            }
            View view18 = copy$default.designatedCorner;
            ViewState viewState3 = privacyDotViewController.currentViewState;
            Objects.requireNonNull(viewState3);
            if (!Intrinsics.areEqual(view18, viewState3.designatedCorner)) {
                ViewState viewState4 = privacyDotViewController.currentViewState;
                Objects.requireNonNull(viewState4);
                View view19 = viewState4.designatedCorner;
                if (view19 != null) {
                    view19.setContentDescription((CharSequence) null);
                }
                View view20 = copy$default.designatedCorner;
                if (view20 != null) {
                    view20.setContentDescription(copy$default.contentDescription);
                }
                View view21 = copy$default.designatedCorner;
                if (!copy$default.systemPrivacyEventIsActive || copy$default.shadeExpanded || copy$default.qsExpanded) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (z3) {
                    PrivacyDotViewController.ShowingListener showingListener = privacyDotViewController.showingListener;
                    if (showingListener != null) {
                        ((ScreenDecorations.C06392) showingListener).onPrivacyDotShown(view21);
                    }
                    if (view21 != null) {
                        view21.clearAnimation();
                        view21.setVisibility(0);
                        view21.setAlpha(0.0f);
                        view21.animate().alpha(1.0f).setDuration(300).start();
                    }
                }
            } else {
                String str = copy$default.contentDescription;
                ViewState viewState5 = privacyDotViewController.currentViewState;
                Objects.requireNonNull(viewState5);
                if (!Intrinsics.areEqual(str, viewState5.contentDescription) && (view3 = copy$default.designatedCorner) != null) {
                    view3.setContentDescription(copy$default.contentDescription);
                }
            }
            if (!copy$default.systemPrivacyEventIsActive || copy$default.shadeExpanded || copy$default.qsExpanded) {
                z2 = false;
            } else {
                z2 = true;
            }
            ViewState viewState6 = privacyDotViewController.currentViewState;
            Objects.requireNonNull(viewState6);
            if (!viewState6.systemPrivacyEventIsActive || viewState6.shadeExpanded || viewState6.qsExpanded) {
                z4 = false;
            }
            if (z2 != z4) {
                if (z2 && (view2 = copy$default.designatedCorner) != null) {
                    PrivacyDotViewController.ShowingListener showingListener2 = privacyDotViewController.showingListener;
                    if (showingListener2 != null) {
                        ((ScreenDecorations.C06392) showingListener2).onPrivacyDotShown(view2);
                    }
                    view2.clearAnimation();
                    view2.setVisibility(0);
                    view2.setAlpha(0.0f);
                    view2.animate().alpha(1.0f).setDuration(160).setInterpolator(Interpolators.ALPHA_IN).start();
                } else if (!z2 && (view = copy$default.designatedCorner) != null) {
                    view.clearAnimation();
                    view.animate().setDuration(160).setInterpolator(Interpolators.ALPHA_OUT).alpha(0.0f).withEndAction(new PrivacyDotViewController$hideDotView$1(view, privacyDotViewController)).start();
                }
            }
            privacyDotViewController.currentViewState = copy$default;
        }
    }
}
