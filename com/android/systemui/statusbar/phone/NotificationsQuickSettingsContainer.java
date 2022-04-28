package com.android.systemui.statusbar.phone;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda28;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

public class NotificationsQuickSettingsContainer extends ConstraintLayout implements FragmentHostManager.FragmentListener, AboveShelfObserver.HasViewAboveShelfChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ArrayList<View> mDrawingOrderedChildren = new ArrayList<>();
    public final Comparator<View> mIndexComparator = Comparator.comparingInt(new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(this));
    public Consumer<WindowInsets> mInsetsChangedListener = BubbleStackView$$ExternalSyntheticLambda28.INSTANCE$1;
    public View mKeyguardStatusBar;
    public ArrayList<View> mLayoutDrawingOrder = new ArrayList<>();
    public View mQSContainer;
    public Consumer<C0961QS> mQSFragmentAttachedListener = NotificationsQuickSettingsContainer$$ExternalSyntheticLambda0.INSTANCE;
    public View mQSScrollView;
    public C0961QS mQs;
    public View mQsFrame;
    public View mStackScroller;

    public final void dispatchDraw(Canvas canvas) {
        this.mDrawingOrderedChildren.clear();
        this.mLayoutDrawingOrder.clear();
        if (this.mKeyguardStatusBar.getVisibility() == 0) {
            this.mDrawingOrderedChildren.add(this.mKeyguardStatusBar);
            this.mLayoutDrawingOrder.add(this.mKeyguardStatusBar);
        }
        if (this.mQsFrame.getVisibility() == 0) {
            this.mDrawingOrderedChildren.add(this.mQsFrame);
            this.mLayoutDrawingOrder.add(this.mQsFrame);
        }
        if (this.mStackScroller.getVisibility() == 0) {
            this.mDrawingOrderedChildren.add(this.mStackScroller);
            this.mLayoutDrawingOrder.add(this.mStackScroller);
        }
        this.mLayoutDrawingOrder.sort(this.mIndexComparator);
        super.dispatchDraw(canvas);
    }

    public final boolean drawChild(Canvas canvas, View view, long j) {
        int indexOf = this.mLayoutDrawingOrder.indexOf(view);
        if (indexOf >= 0) {
            return super.drawChild(canvas, this.mDrawingOrderedChildren.get(indexOf), j);
        }
        return super.drawChild(canvas, view, j);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mInsetsChangedListener.accept(windowInsets);
        return windowInsets;
    }

    public final void onFragmentViewCreated(Fragment fragment) {
        C0961QS qs = (C0961QS) fragment;
        this.mQs = qs;
        this.mQSFragmentAttachedListener.accept(qs);
        this.mQSScrollView = this.mQs.getView().findViewById(C1777R.C1779id.expanded_qs_scroll_view);
        this.mQSContainer = this.mQs.getView().findViewById(C1777R.C1779id.quick_settings_container);
    }

    public NotificationsQuickSettingsContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        FragmentHostManager.get(this).addTagListener(C0961QS.TAG, this);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        FragmentHostManager fragmentHostManager = FragmentHostManager.get(this);
        Objects.requireNonNull(fragmentHostManager);
        ArrayList arrayList = fragmentHostManager.mListeners.get(C0961QS.TAG);
        if (arrayList != null && arrayList.remove(this) && arrayList.size() == 0) {
            fragmentHostManager.mListeners.remove(C0961QS.TAG);
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mQsFrame = findViewById(C1777R.C1779id.qs_frame);
        this.mStackScroller = findViewById(C1777R.C1779id.notification_stack_scroller);
        this.mKeyguardStatusBar = findViewById(C1777R.C1779id.keyguard_header);
    }

    public final void onHasViewsAboveShelfChanged() {
        invalidate();
    }
}
