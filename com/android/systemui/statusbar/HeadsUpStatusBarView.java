package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

public class HeadsUpStatusBarView extends AlphaOptimizedLinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mIconDrawingRect;
    public View mIconPlaceholder;
    public final Rect mLayoutedIconRect;
    public Runnable mOnDrawingRectChangedListener;
    public final HeadsUpStatusBarView$$ExternalSyntheticLambda0 mOnSensitivityChangedListener;
    public NotificationEntry mShowingEntry;
    public TextView mTextView;
    public final int[] mTmpPosition;

    public HeadsUpStatusBarView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("heads_up_status_bar_view_super_parcelable"));
        if (bundle.containsKey("visibility")) {
            setVisibility(bundle.getInt("visibility"));
        }
        if (bundle.containsKey("alpha")) {
            setAlpha(bundle.getFloat("alpha"));
        }
    }

    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("heads_up_status_bar_view_super_parcelable", super.onSaveInstanceState());
        bundle.putInt("visibility", getVisibility());
        bundle.putFloat("alpha", getAlpha());
        return bundle;
    }

    public final void setEntry(NotificationEntry notificationEntry) {
        NotificationEntry notificationEntry2 = this.mShowingEntry;
        if (notificationEntry2 != null) {
            notificationEntry2.mOnSensitivityChangedListeners.remove(this.mOnSensitivityChangedListener);
        }
        this.mShowingEntry = notificationEntry;
        if (notificationEntry != null) {
            CharSequence charSequence = notificationEntry.headsUpStatusBarText;
            if (notificationEntry.mSensitive) {
                charSequence = notificationEntry.headsUpStatusBarTextPublic;
            }
            this.mTextView.setText(charSequence);
            NotificationEntry notificationEntry3 = this.mShowingEntry;
            HeadsUpStatusBarView$$ExternalSyntheticLambda0 headsUpStatusBarView$$ExternalSyntheticLambda0 = this.mOnSensitivityChangedListener;
            Objects.requireNonNull(notificationEntry3);
            notificationEntry3.mOnSensitivityChangedListeners.add(headsUpStatusBarView$$ExternalSyntheticLambda0);
        }
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mIconPlaceholder = findViewById(C1777R.C1779id.icon_placeholder);
        this.mTextView = (TextView) findViewById(C1777R.C1779id.text);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Runnable runnable;
        super.onLayout(z, i, i2, i3, i4);
        this.mIconPlaceholder.getLocationOnScreen(this.mTmpPosition);
        int[] iArr = this.mTmpPosition;
        int i5 = iArr[0];
        int i6 = iArr[1];
        this.mLayoutedIconRect.set(i5, i6, this.mIconPlaceholder.getWidth() + i5, this.mIconPlaceholder.getHeight() + i6);
        Rect rect = this.mIconDrawingRect;
        rect.set(this.mLayoutedIconRect);
        if (((float) rect.left) != ((float) this.mIconDrawingRect.left) && (runnable = this.mOnDrawingRectChangedListener) != null) {
            runnable.run();
        }
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutedIconRect = new Rect();
        this.mTmpPosition = new int[2];
        this.mIconDrawingRect = new Rect();
        this.mOnSensitivityChangedListener = new HeadsUpStatusBarView$$ExternalSyntheticLambda0(this);
    }

    @VisibleForTesting
    public HeadsUpStatusBarView(Context context, View view, TextView textView) {
        this(context);
        this.mIconPlaceholder = view;
        this.mTextView = textView;
    }
}
