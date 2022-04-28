package com.android.systemui.p006qs;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.tileimpl.QSIconViewImpl;
import com.android.systemui.p006qs.tileimpl.SlashImageView;
import com.android.systemui.plugins.p005qs.QSTile;

/* renamed from: com.android.systemui.qs.SignalTileView */
public class SignalTileView extends QSIconViewImpl {
    public FrameLayout mIconFrame;
    public ImageView mIn;
    public ImageView mOut;
    public ImageView mOverlay;
    public SlashImageView mSignal;
    public int mSignalIndicatorToIconFrameSpacing;
    public int mWideOverlayIconStartPadding;

    public final int getIconMeasureMode() {
        return Integer.MIN_VALUE;
    }

    static {
        long duration = new ValueAnimator().getDuration() / 3;
    }

    public final View createIcon() {
        this.mIconFrame = new FrameLayout(this.mContext);
        SlashImageView createSlashImageView = createSlashImageView(this.mContext);
        this.mSignal = createSlashImageView;
        this.mIconFrame.addView(createSlashImageView);
        ImageView imageView = new ImageView(this.mContext);
        this.mOverlay = imageView;
        this.mIconFrame.addView(imageView, -2, -2);
        return this.mIconFrame;
    }

    public SlashImageView createSlashImageView(Context context) {
        return new SlashImageView(context);
    }

    public final void setIcon(QSTile.State state, boolean z) {
        QSTile.SignalState signalState = (QSTile.SignalState) state;
        setIcon(this.mSignal, signalState, z);
        if (signalState.overlayIconId > 0) {
            this.mOverlay.setVisibility(0);
            this.mOverlay.setImageResource(signalState.overlayIconId);
        } else {
            this.mOverlay.setVisibility(8);
        }
        if (signalState.overlayIconId <= 0 || !signalState.isOverlayIconWide) {
            this.mSignal.setPaddingRelative(0, 0, 0, 0);
        } else {
            this.mSignal.setPaddingRelative(this.mWideOverlayIconStartPadding, 0, 0, 0);
        }
        if (z) {
            isShown();
        }
    }

    public SignalTileView(Context context) {
        super(context);
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageResource(C1777R.C1778drawable.ic_qs_signal_in);
        imageView.setAlpha(0.0f);
        addView(imageView);
        this.mIn = imageView;
        ImageView imageView2 = new ImageView(this.mContext);
        imageView2.setImageResource(C1777R.C1778drawable.ic_qs_signal_out);
        imageView2.setAlpha(0.0f);
        addView(imageView2);
        this.mOut = imageView2;
        setClipChildren(false);
        setClipToPadding(false);
        this.mWideOverlayIconStartPadding = context.getResources().getDimensionPixelSize(C1777R.dimen.wide_type_icon_start_padding_qs);
        this.mSignalIndicatorToIconFrameSpacing = context.getResources().getDimensionPixelSize(C1777R.dimen.signal_indicator_to_icon_frame_spacing);
    }

    public final void layoutIndicator(ImageView imageView) {
        int i;
        int i2;
        boolean z = true;
        if (getLayoutDirection() != 1) {
            z = false;
        }
        if (z) {
            i2 = getLeft() - this.mSignalIndicatorToIconFrameSpacing;
            i = i2 - imageView.getMeasuredWidth();
        } else {
            i = this.mSignalIndicatorToIconFrameSpacing + getRight();
            i2 = imageView.getMeasuredWidth() + i;
        }
        imageView.layout(i, this.mIconFrame.getBottom() - imageView.getMeasuredHeight(), i2, this.mIconFrame.getBottom());
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        layoutIndicator(this.mIn);
        layoutIndicator(this.mOut);
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconFrame.getMeasuredHeight(), 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mIconFrame.getMeasuredHeight(), Integer.MIN_VALUE);
        this.mIn.measure(makeMeasureSpec2, makeMeasureSpec);
        this.mOut.measure(makeMeasureSpec2, makeMeasureSpec);
    }
}
