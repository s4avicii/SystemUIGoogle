package com.android.systemui.wallet.p011ui;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.DotIndicatorDecoration */
public final class DotIndicatorDecoration extends RecyclerView.ItemDecoration {
    public WalletCardCarousel mCardCarousel;
    public final int mDotMargin;
    public final Paint mPaint = new Paint(1);
    public final int mSelectedColor;
    public final int mSelectedRadius;
    public final int mUnselectedColor;
    public final int mUnselectedRadius;

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00bb, code lost:
        if (r6.mEdgeToCenterDistance >= 0.0f) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c5, code lost:
        if (r6.mEdgeToCenterDistance < 0.0f) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00d4, code lost:
        if (r6.mEdgeToCenterDistance >= 0.0f) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00de, code lost:
        if (r6.mEdgeToCenterDistance < 0.0f) goto L_0x00e0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x010a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onDrawOver(android.graphics.Canvas r12, androidx.recyclerview.widget.RecyclerView r13) {
        /*
            r11 = this;
            r0 = r13
            com.android.systemui.wallet.ui.WalletCardCarousel r0 = (com.android.systemui.wallet.p011ui.WalletCardCarousel) r0
            r11.mCardCarousel = r0
            java.util.Objects.requireNonNull(r13)
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r13.mAdapter
            int r0 = r0.getItemCount()
            r1 = 1
            if (r0 > r1) goto L_0x0012
            return
        L_0x0012:
            r12.save()
            int r2 = r13.getWidth()
            float r2 = (float) r2
            r3 = 1086324736(0x40c00000, float:6.0)
            float r2 = r2 / r3
            r3 = 1065353216(0x3f800000, float:1.0)
            com.android.systemui.wallet.ui.WalletCardCarousel r4 = r11.mCardCarousel
            float r4 = r4.mEdgeToCenterDistance
            float r4 = java.lang.Math.abs(r4)
            float r4 = java.lang.Math.min(r4, r2)
            float r4 = r4 / r2
            float r3 = r3 - r4
            int r2 = r11.mDotMargin
            int r4 = r0 + -1
            int r4 = r4 * r2
            int r2 = r11.mUnselectedRadius
            int r2 = r2 * 2
            int r5 = r0 + -2
            int r5 = r5 * r2
            int r5 = r5 + r4
            int r2 = r11.mSelectedRadius
            int r2 = r2 * 2
            int r2 = r2 + r5
            float r2 = (float) r2
            int r4 = r13.getWidth()
            float r4 = (float) r4
            float r4 = r4 - r2
            r2 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r2
            int r13 = r13.getHeight()
            int r5 = r11.mDotMargin
            int r13 = r13 - r5
            float r13 = (float) r13
            r12.translate(r4, r13)
            r13 = 0
            r4 = r13
        L_0x0056:
            if (r4 >= r0) goto L_0x012b
            com.android.systemui.wallet.ui.WalletCardCarousel r5 = r11.mCardCarousel
            if (r5 != 0) goto L_0x005d
            goto L_0x0063
        L_0x005d:
            int r5 = r5.getLayoutDirection()
            if (r5 != 0) goto L_0x0065
        L_0x0063:
            r5 = r1
            goto L_0x0066
        L_0x0065:
            r5 = r13
        L_0x0066:
            if (r5 == 0) goto L_0x006a
            r5 = r4
            goto L_0x006d
        L_0x006a:
            int r5 = r0 - r4
            int r5 = r5 - r1
        L_0x006d:
            com.android.systemui.wallet.ui.WalletCardCarousel r6 = r11.mCardCarousel
            int r7 = r6.mCenteredAdapterPosition
            if (r7 != r5) goto L_0x0075
            r7 = r1
            goto L_0x0076
        L_0x0075:
            r7 = r13
        L_0x0076:
            r8 = 0
            r9 = 255(0xff, float:3.57E-43)
            if (r7 == 0) goto L_0x00a1
            android.graphics.Paint r5 = r11.mPaint
            int r6 = r11.mSelectedColor
            int r7 = r11.mUnselectedColor
            float r10 = r3 / r2
            int r6 = androidx.core.graphics.ColorUtils.blendARGB(r6, r7, r10)
            int r6 = androidx.core.graphics.ColorUtils.setAlphaComponent(r6, r9)
            r5.setColor(r6)
            int r5 = r11.mSelectedRadius
            int r6 = r11.mUnselectedRadius
            float r5 = android.util.MathUtils.lerp(r5, r6, r10)
            android.graphics.Paint r6 = r11.mPaint
            r12.drawCircle(r5, r8, r5, r6)
            float r5 = r5 * r2
            r12.translate(r5, r8)
            goto L_0x0121
        L_0x00a1:
            if (r6 != 0) goto L_0x00a4
            goto L_0x00aa
        L_0x00a4:
            int r6 = r6.getLayoutDirection()
            if (r6 != 0) goto L_0x00ac
        L_0x00aa:
            r6 = r1
            goto L_0x00ad
        L_0x00ac:
            r6 = r13
        L_0x00ad:
            if (r6 == 0) goto L_0x00c8
            com.android.systemui.wallet.ui.WalletCardCarousel r6 = r11.mCardCarousel
            int r7 = r6.mCenteredAdapterPosition
            int r10 = r7 + 1
            if (r10 != r5) goto L_0x00bd
            float r10 = r6.mEdgeToCenterDistance
            int r10 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x00e0
        L_0x00bd:
            int r7 = r7 + -1
            if (r7 != r5) goto L_0x00e2
            float r5 = r6.mEdgeToCenterDistance
            int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r5 >= 0) goto L_0x00e2
            goto L_0x00e0
        L_0x00c8:
            com.android.systemui.wallet.ui.WalletCardCarousel r6 = r11.mCardCarousel
            int r7 = r6.mCenteredAdapterPosition
            int r10 = r7 + -1
            if (r10 != r5) goto L_0x00d6
            float r10 = r6.mEdgeToCenterDistance
            int r10 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x00e0
        L_0x00d6:
            int r7 = r7 + 1
            if (r7 != r5) goto L_0x00e2
            float r5 = r6.mEdgeToCenterDistance
            int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r5 >= 0) goto L_0x00e2
        L_0x00e0:
            r5 = r1
            goto L_0x00e3
        L_0x00e2:
            r5 = r13
        L_0x00e3:
            if (r5 == 0) goto L_0x010a
            int r5 = r11.mUnselectedColor
            int r6 = r11.mSelectedColor
            float r7 = r3 / r2
            int r5 = androidx.core.graphics.ColorUtils.blendARGB(r5, r6, r7)
            android.graphics.Paint r6 = r11.mPaint
            int r5 = androidx.core.graphics.ColorUtils.setAlphaComponent(r5, r9)
            r6.setColor(r5)
            int r5 = r11.mUnselectedRadius
            int r6 = r11.mSelectedColor
            float r5 = android.util.MathUtils.lerp(r5, r6, r7)
            android.graphics.Paint r6 = r11.mPaint
            r12.drawCircle(r5, r8, r5, r6)
            float r5 = r5 * r2
            r12.translate(r5, r8)
            goto L_0x0121
        L_0x010a:
            android.graphics.Paint r5 = r11.mPaint
            int r6 = r11.mUnselectedColor
            r5.setColor(r6)
            int r5 = r11.mUnselectedRadius
            float r5 = (float) r5
            android.graphics.Paint r6 = r11.mPaint
            r12.drawCircle(r5, r8, r5, r6)
            int r5 = r11.mUnselectedRadius
            int r5 = r5 * 2
            float r5 = (float) r5
            r12.translate(r5, r8)
        L_0x0121:
            int r5 = r11.mDotMargin
            float r5 = (float) r5
            r12.translate(r5, r8)
            int r4 = r4 + 1
            goto L_0x0056
        L_0x012b:
            r12.restore()
            r12 = 0
            r11.mCardCarousel = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallet.p011ui.DotIndicatorDecoration.onDrawOver(android.graphics.Canvas, androidx.recyclerview.widget.RecyclerView):void");
    }

    public DotIndicatorDecoration(Context context) {
        this.mUnselectedRadius = context.getResources().getDimensionPixelSize(C1777R.dimen.card_carousel_dot_unselected_radius);
        this.mSelectedRadius = context.getResources().getDimensionPixelSize(C1777R.dimen.card_carousel_dot_selected_radius);
        this.mDotMargin = context.getResources().getDimensionPixelSize(C1777R.dimen.card_carousel_dot_margin);
        this.mUnselectedColor = context.getColor(C1777R.color.material_dynamic_neutral70);
        this.mSelectedColor = context.getColor(C1777R.color.material_dynamic_neutral100);
    }

    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        Objects.requireNonNull(recyclerView);
        if (recyclerView.mAdapter.getItemCount() > 1) {
            rect.bottom = view.getResources().getDimensionPixelSize(C1777R.dimen.card_carousel_dot_offset);
        }
    }
}
