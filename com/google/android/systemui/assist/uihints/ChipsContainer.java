package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.leanback.R$color;
import com.android.p012wm.shell.C1777R;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import com.google.common.util.concurrent.ImmediateFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ChipsContainer extends LinearLayout implements TranscriptionController.TranscriptionSpaceView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int CHIP_MARGIN;
    public final int START_DELTA;
    public ValueAnimator mAnimator;
    public int mAvailableWidth;
    public ArrayList mChipViews;
    public List<Bundle> mChips;
    public boolean mDarkBackground;

    public ChipsContainer(Context context) {
        this(context, (AttributeSet) null);
    }

    public ChipsContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final ListenableFuture<Void> hide(boolean z) {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        removeAllViews();
        setVisibility(8);
        setTranslationY(0.0f);
        return ImmediateFuture.NULL;
    }

    public final void onFontSizeChanged() {
        float dimension = this.mContext.getResources().getDimension(C1777R.dimen.assist_chip_text_size);
        Iterator it = this.mChipViews.iterator();
        while (it.hasNext()) {
            ChipView chipView = (ChipView) it.next();
            Objects.requireNonNull(chipView);
            chipView.mLabelView.setTextSize(0, dimension);
        }
        requestLayout();
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x000a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setChipsInternal() {
        /*
            r12 = this;
            int r0 = r12.mAvailableWidth
            java.util.List<android.os.Bundle> r1 = r12.mChips
            java.util.Iterator r1 = r1.iterator()
            r2 = 0
            r3 = r2
        L_0x000a:
            boolean r4 = r1.hasNext()
            r5 = 8
            if (r4 == 0) goto L_0x0113
            java.lang.Object r4 = r1.next()
            android.os.Bundle r4 = (android.os.Bundle) r4
            java.util.ArrayList r6 = r12.mChipViews
            int r6 = r6.size()
            if (r3 >= r6) goto L_0x0029
            java.util.ArrayList r6 = r12.mChipViews
            java.lang.Object r6 = r6.get(r3)
            com.google.android.systemui.assist.uihints.ChipView r6 = (com.google.android.systemui.assist.uihints.ChipView) r6
            goto L_0x003f
        L_0x0029:
            android.content.Context r6 = r12.getContext()
            android.view.LayoutInflater r6 = android.view.LayoutInflater.from(r6)
            r7 = 2131623995(0x7f0e003b, float:1.8875157E38)
            android.view.View r6 = r6.inflate(r7, r12, r2)
            com.google.android.systemui.assist.uihints.ChipView r6 = (com.google.android.systemui.assist.uihints.ChipView) r6
            java.util.ArrayList r7 = r12.mChipViews
            r7.add(r6)
        L_0x003f:
            java.util.Objects.requireNonNull(r6)
            java.lang.String r7 = "icon"
            android.os.Parcelable r7 = r4.getParcelable(r7)
            android.graphics.drawable.Icon r7 = (android.graphics.drawable.Icon) r7
            java.lang.String r8 = "label"
            java.lang.String r8 = r4.getString(r8)
            java.lang.String r9 = "ChipView"
            r10 = 2
            if (r7 != 0) goto L_0x0063
            if (r8 == 0) goto L_0x005d
            int r11 = r8.length()
            if (r11 != 0) goto L_0x0063
        L_0x005d:
            java.lang.String r4 = "Neither icon nor label provided; ignoring chip"
            android.util.Log.w(r9, r4)
            goto L_0x00c8
        L_0x0063:
            if (r7 != 0) goto L_0x0086
            android.widget.ImageView r7 = r6.mIconView
            r7.setVisibility(r5)
            android.widget.Space r7 = r6.mSpaceView
            r7.setVisibility(r5)
            android.widget.TextView r5 = r6.mLabelView
            r5.setText(r8)
            android.widget.TextView r5 = r6.mLabelView
            android.view.ViewGroup$LayoutParams r5 = r5.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r5 = (android.widget.LinearLayout.LayoutParams) r5
            int r7 = r5.rightMargin
            int r8 = r5.topMargin
            int r11 = r5.bottomMargin
            r5.setMargins(r7, r8, r7, r11)
            goto L_0x00ba
        L_0x0086:
            if (r8 == 0) goto L_0x009a
            int r11 = r8.length()
            if (r11 != 0) goto L_0x008f
            goto L_0x009a
        L_0x008f:
            android.widget.ImageView r5 = r6.mIconView
            r5.setImageIcon(r7)
            android.widget.TextView r5 = r6.mLabelView
            r5.setText(r8)
            goto L_0x00ba
        L_0x009a:
            android.widget.TextView r8 = r6.mLabelView
            r8.setVisibility(r5)
            android.widget.Space r8 = r6.mSpaceView
            r8.setVisibility(r5)
            android.widget.ImageView r5 = r6.mIconView
            r5.setImageIcon(r7)
            android.widget.ImageView r5 = r6.mIconView
            android.view.ViewGroup$LayoutParams r5 = r5.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r5 = (android.widget.LinearLayout.LayoutParams) r5
            int r7 = r5.leftMargin
            int r8 = r5.topMargin
            int r11 = r5.bottomMargin
            r5.setMargins(r7, r8, r7, r11)
        L_0x00ba:
            java.lang.String r5 = "tap_action"
            android.os.Parcelable r7 = r4.getParcelable(r5)
            if (r7 != 0) goto L_0x00ca
            java.lang.String r4 = "No tap action provided; ignoring chip"
            android.util.Log.w(r9, r4)
        L_0x00c8:
            r4 = r2
            goto L_0x00d9
        L_0x00ca:
            android.os.Parcelable r4 = r4.getParcelable(r5)
            android.app.PendingIntent r4 = (android.app.PendingIntent) r4
            com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda3 r5 = new com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda3
            r5.<init>(r4, r10)
            r6.setOnClickListener(r5)
            r4 = 1
        L_0x00d9:
            if (r4 == 0) goto L_0x000a
            boolean r4 = r12.mDarkBackground
            android.widget.LinearLayout r5 = r6.mChip
            if (r4 == 0) goto L_0x00e4
            android.graphics.drawable.Drawable r7 = r6.BACKGROUND_DARK
            goto L_0x00e6
        L_0x00e4:
            android.graphics.drawable.Drawable r7 = r6.BACKGROUND_LIGHT
        L_0x00e6:
            r5.setBackground(r7)
            android.widget.TextView r5 = r6.mLabelView
            if (r4 == 0) goto L_0x00f0
            int r4 = r6.TEXT_COLOR_DARK
            goto L_0x00f2
        L_0x00f0:
            int r4 = r6.TEXT_COLOR_LIGHT
        L_0x00f2:
            r5.setTextColor(r4)
            r6.measure(r2, r2)
            int r4 = r6.getMeasuredWidth()
            int r5 = r12.CHIP_MARGIN
            int r5 = r5 * r10
            int r5 = r5 + r4
            if (r5 >= r0) goto L_0x000a
            android.view.ViewParent r4 = r6.getParent()
            if (r4 != 0) goto L_0x010e
            r6.setVisibility(r2)
            r12.addView(r6)
        L_0x010e:
            int r0 = r0 - r5
            int r3 = r3 + 1
            goto L_0x000a
        L_0x0113:
            java.util.ArrayList r0 = r12.mChipViews
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x0131
        L_0x011b:
            java.util.ArrayList r0 = r12.mChipViews
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x0131
            java.util.ArrayList r0 = r12.mChipViews
            java.lang.Object r0 = r0.get(r3)
            com.google.android.systemui.assist.uihints.ChipView r0 = (com.google.android.systemui.assist.uihints.ChipView) r0
            r0.setVisibility(r5)
            int r3 = r3 + 1
            goto L_0x011b
        L_0x0131:
            r12.requestLayout()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.ChipsContainer.setChipsInternal():void");
    }

    public final void setHasDarkBackground(boolean z) {
        Drawable drawable;
        int i;
        if (this.mDarkBackground != z) {
            this.mDarkBackground = z;
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                ChipView chipView = (ChipView) getChildAt(i2);
                Objects.requireNonNull(chipView);
                LinearLayout linearLayout = chipView.mChip;
                if (z) {
                    drawable = chipView.BACKGROUND_DARK;
                } else {
                    drawable = chipView.BACKGROUND_LIGHT;
                }
                linearLayout.setBackground(drawable);
                TextView textView = chipView.mLabelView;
                if (z) {
                    i = chipView.TEXT_COLOR_DARK;
                } else {
                    i = chipView.TEXT_COLOR_LIGHT;
                }
                textView.setTextColor(i);
            }
        }
    }

    public ChipsContainer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onMeasure(int i, int i2) {
        Display defaultDisplay = R$color.getDefaultDisplay(getContext());
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int i3 = point.x;
        if (i3 != this.mAvailableWidth) {
            this.mAvailableWidth = i3;
            setChipsInternal();
        }
        super.onMeasure(i, i2);
    }

    public ChipsContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChips = new ArrayList();
        this.mChipViews = new ArrayList();
        this.mAnimator = new ValueAnimator();
        this.CHIP_MARGIN = (int) getResources().getDimension(C1777R.dimen.assist_chip_horizontal_margin);
        this.START_DELTA = (int) getResources().getDimension(C1777R.dimen.assist_greeting_start_delta);
    }
}
