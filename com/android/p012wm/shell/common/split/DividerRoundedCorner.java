package com.android.p012wm.shell.common.split;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.RoundedCorner;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.split.DividerRoundedCorner */
public class DividerRoundedCorner extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public InvertedRoundedCornerDrawInfo mBottomLeftCorner;
    public InvertedRoundedCornerDrawInfo mBottomRightCorner;
    public final Paint mDividerBarBackground;
    public final int mDividerWidth = getResources().getDimensionPixelSize(C1777R.dimen.split_divider_bar_width);
    public final Point mStartPos = new Point();
    public InvertedRoundedCornerDrawInfo mTopLeftCorner;
    public InvertedRoundedCornerDrawInfo mTopRightCorner;

    /* renamed from: com.android.wm.shell.common.split.DividerRoundedCorner$InvertedRoundedCornerDrawInfo */
    public class InvertedRoundedCornerDrawInfo {
        public final int mCornerPosition;
        public final Path mPath;
        public final int mRadius;

        public InvertedRoundedCornerDrawInfo(int i) {
            int i2;
            boolean z;
            float f;
            Path path = new Path();
            this.mPath = path;
            this.mCornerPosition = i;
            RoundedCorner roundedCorner = DividerRoundedCorner.this.getDisplay().getRoundedCorner(i);
            boolean z2 = false;
            if (roundedCorner == null) {
                i2 = 0;
            } else {
                i2 = roundedCorner.getRadius();
            }
            this.mRadius = i2;
            Path path2 = new Path();
            float f2 = 0.0f;
            float f3 = (float) i2;
            path2.addRect(0.0f, 0.0f, f3, f3, Path.Direction.CW);
            Path path3 = new Path();
            if (i == 0 || i == 3) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                f = f3;
            } else {
                f = 0.0f;
            }
            path3.addCircle(f, (i == 0 || i == 1) ? true : z2 ? f3 : f2, f3, Path.Direction.CW);
            path.op(path2, path3, Path.Op.DIFFERENCE);
        }

        /* renamed from: -$$Nest$mcalculateStartPos  reason: not valid java name */
        public static void m294$$Nest$mcalculateStartPos(InvertedRoundedCornerDrawInfo invertedRoundedCornerDrawInfo, Point point) {
            boolean z;
            boolean z2;
            int i;
            int i2;
            boolean z3;
            int i3;
            Objects.requireNonNull(invertedRoundedCornerDrawInfo);
            DividerRoundedCorner dividerRoundedCorner = DividerRoundedCorner.this;
            int i4 = DividerRoundedCorner.$r8$clinit;
            Objects.requireNonNull(dividerRoundedCorner);
            boolean z4 = true;
            int i5 = 0;
            if (dividerRoundedCorner.getResources().getConfiguration().orientation == 2) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                int i6 = invertedRoundedCornerDrawInfo.mCornerPosition;
                if (i6 == 0 || i6 == 3) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    i3 = (DividerRoundedCorner.this.mDividerWidth / 2) + (DividerRoundedCorner.this.getWidth() / 2);
                } else {
                    i3 = ((DividerRoundedCorner.this.getWidth() / 2) - (DividerRoundedCorner.this.mDividerWidth / 2)) - invertedRoundedCornerDrawInfo.mRadius;
                }
                point.x = i3;
                int i7 = invertedRoundedCornerDrawInfo.mCornerPosition;
                if (!(i7 == 0 || i7 == 1)) {
                    z4 = false;
                }
                if (!z4) {
                    i5 = DividerRoundedCorner.this.getHeight() - invertedRoundedCornerDrawInfo.mRadius;
                }
                point.y = i5;
                return;
            }
            int i8 = invertedRoundedCornerDrawInfo.mCornerPosition;
            if (i8 == 0 || i8 == 3) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                i = 0;
            } else {
                i = DividerRoundedCorner.this.getWidth() - invertedRoundedCornerDrawInfo.mRadius;
            }
            point.x = i;
            int i9 = invertedRoundedCornerDrawInfo.mCornerPosition;
            if (!(i9 == 0 || i9 == 1)) {
                z4 = false;
            }
            if (z4) {
                i2 = (DividerRoundedCorner.this.mDividerWidth / 2) + (DividerRoundedCorner.this.getHeight() / 2);
            } else {
                i2 = ((DividerRoundedCorner.this.getHeight() / 2) - (DividerRoundedCorner.this.mDividerWidth / 2)) - invertedRoundedCornerDrawInfo.mRadius;
            }
            point.y = i2;
        }
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public DividerRoundedCorner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mDividerBarBackground = paint;
        paint.setColor(getResources().getColor(C1777R.color.split_divider_background, (Resources.Theme) null));
        paint.setFlags(1);
        paint.setStyle(Paint.Style.FILL);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTopLeftCorner = new InvertedRoundedCornerDrawInfo(0);
        this.mTopRightCorner = new InvertedRoundedCornerDrawInfo(1);
        this.mBottomLeftCorner = new InvertedRoundedCornerDrawInfo(3);
        this.mBottomRightCorner = new InvertedRoundedCornerDrawInfo(2);
    }

    public final void onDraw(Canvas canvas) {
        canvas.save();
        InvertedRoundedCornerDrawInfo.m294$$Nest$mcalculateStartPos(this.mTopLeftCorner, this.mStartPos);
        Point point = this.mStartPos;
        canvas.translate((float) point.x, (float) point.y);
        canvas.drawPath(this.mTopLeftCorner.mPath, this.mDividerBarBackground);
        Point point2 = this.mStartPos;
        canvas.translate((float) (-point2.x), (float) (-point2.y));
        InvertedRoundedCornerDrawInfo.m294$$Nest$mcalculateStartPos(this.mTopRightCorner, this.mStartPos);
        Point point3 = this.mStartPos;
        canvas.translate((float) point3.x, (float) point3.y);
        canvas.drawPath(this.mTopRightCorner.mPath, this.mDividerBarBackground);
        Point point4 = this.mStartPos;
        canvas.translate((float) (-point4.x), (float) (-point4.y));
        InvertedRoundedCornerDrawInfo.m294$$Nest$mcalculateStartPos(this.mBottomLeftCorner, this.mStartPos);
        Point point5 = this.mStartPos;
        canvas.translate((float) point5.x, (float) point5.y);
        canvas.drawPath(this.mBottomLeftCorner.mPath, this.mDividerBarBackground);
        Point point6 = this.mStartPos;
        canvas.translate((float) (-point6.x), (float) (-point6.y));
        InvertedRoundedCornerDrawInfo.m294$$Nest$mcalculateStartPos(this.mBottomRightCorner, this.mStartPos);
        Point point7 = this.mStartPos;
        canvas.translate((float) point7.x, (float) point7.y);
        canvas.drawPath(this.mBottomRightCorner.mPath, this.mDividerBarBackground);
        canvas.restore();
    }
}
