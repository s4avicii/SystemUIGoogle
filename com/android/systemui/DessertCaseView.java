package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import java.util.HashSet;
import java.util.Objects;

public class DessertCaseView extends FrameLayout {
    public static final float[] ALPHA_MASK = {0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    public static final float[] MASK = {0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public static final int NUM_PASTRIES;
    public static final int[] PASTRIES;
    public static final int[] RARE_PASTRIES;
    public static final int[] XRARE_PASTRIES;
    public static final int[] XXRARE_PASTRIES;
    public float[] hsv = {0.0f, 1.0f, 0.85f};
    public int mCellSize;
    public View[] mCells;
    public int mColumns;
    public SparseArray<Drawable> mDrawables = new SparseArray<>(NUM_PASTRIES);
    public final HashSet mFreeList = new HashSet();
    public final Handler mHandler = new Handler();
    public int mHeight;
    public final C06251 mJuggle = new Runnable() {
        public final void run() {
            int childCount = DessertCaseView.this.getChildCount();
            for (int i = 0; i < 1; i++) {
                View childAt = DessertCaseView.this.getChildAt((int) (Math.random() * ((double) childCount)));
                DessertCaseView dessertCaseView = DessertCaseView.this;
                Objects.requireNonNull(dessertCaseView);
                float f = (float) 0;
                dessertCaseView.place(childAt, new Point((int) MotionController$$ExternalSyntheticOutline0.m7m((float) dessertCaseView.mColumns, f, (float) Math.random(), f), (int) MotionController$$ExternalSyntheticOutline0.m7m((float) dessertCaseView.mRows, f, (float) Math.random(), f)), true);
            }
            DessertCaseView dessertCaseView2 = DessertCaseView.this;
            Objects.requireNonNull(dessertCaseView2);
            dessertCaseView2.fillFreeList(500);
            DessertCaseView dessertCaseView3 = DessertCaseView.this;
            if (dessertCaseView3.mStarted) {
                dessertCaseView3.mHandler.postDelayed(dessertCaseView3.mJuggle, 2000);
            }
        }
    };
    public int mRows;
    public boolean mStarted;
    public int mWidth;
    public final HashSet<View> tmpSet = new HashSet<>();

    public final synchronized void fillFreeList(int i) {
        Drawable drawable;
        Context context = getContext();
        int i2 = this.mCellSize;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i2);
        while (!this.mFreeList.isEmpty()) {
            Point point = (Point) this.mFreeList.iterator().next();
            this.mFreeList.remove(point);
            if (this.mCells[(point.y * this.mColumns) + point.x] == null) {
                final ImageView imageView = new ImageView(context);
                imageView.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        DessertCaseView dessertCaseView = DessertCaseView.this;
                        ImageView imageView = imageView;
                        Objects.requireNonNull(dessertCaseView);
                        float f = (float) 0;
                        dessertCaseView.place(imageView, new Point((int) MotionController$$ExternalSyntheticOutline0.m7m((float) dessertCaseView.mColumns, f, (float) Math.random(), f), (int) MotionController$$ExternalSyntheticOutline0.m7m((float) dessertCaseView.mRows, f, (float) Math.random(), f)), true);
                        DessertCaseView.this.postDelayed(new Runnable() {
                            public final void run() {
                                DessertCaseView dessertCaseView = DessertCaseView.this;
                                Objects.requireNonNull(dessertCaseView);
                                dessertCaseView.fillFreeList(500);
                            }
                        }, 250);
                    }
                });
                float f = (float) 0;
                this.hsv[0] = ((float) ((int) MotionController$$ExternalSyntheticOutline0.m7m((float) 12, f, (float) Math.random(), f))) * 30.0f;
                imageView.setBackgroundColor(Color.HSVToColor(this.hsv));
                float random = (float) Math.random();
                if (random < 5.0E-4f) {
                    drawable = this.mDrawables.get(XXRARE_PASTRIES[(int) (Math.random() * ((double) 3))]);
                } else if (random < 0.005f) {
                    drawable = this.mDrawables.get(XRARE_PASTRIES[(int) (Math.random() * ((double) 4))]);
                } else if (random < 0.5f) {
                    drawable = this.mDrawables.get(RARE_PASTRIES[(int) (Math.random() * ((double) 8))]);
                } else if (random < 0.7f) {
                    drawable = this.mDrawables.get(PASTRIES[(int) (Math.random() * ((double) 2))]);
                } else {
                    drawable = null;
                }
                if (drawable != null) {
                    imageView.getOverlay().add(drawable);
                }
                int i3 = this.mCellSize;
                layoutParams.height = i3;
                layoutParams.width = i3;
                addView(imageView, layoutParams);
                place(imageView, point, false);
                if (i > 0) {
                    float intValue = (float) ((Integer) imageView.getTag(33554434)).intValue();
                    float f2 = 0.5f * intValue;
                    imageView.setScaleX(f2);
                    imageView.setScaleY(f2);
                    imageView.setAlpha(0.0f);
                    imageView.animate().withLayer().scaleX(intValue).scaleY(intValue).alpha(1.0f).setDuration((long) i);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0082, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void onSizeChanged(int r4, int r5, int r6, int r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            super.onSizeChanged(r4, r5, r6, r7)     // Catch:{ all -> 0x0083 }
            int r6 = r3.mWidth     // Catch:{ all -> 0x0083 }
            if (r6 != r4) goto L_0x000e
            int r6 = r3.mHeight     // Catch:{ all -> 0x0083 }
            if (r6 != r5) goto L_0x000e
            monitor-exit(r3)
            return
        L_0x000e:
            boolean r6 = r3.mStarted     // Catch:{ all -> 0x0083 }
            r7 = 0
            if (r6 == 0) goto L_0x001c
            r3.mStarted = r7     // Catch:{ all -> 0x0083 }
            android.os.Handler r0 = r3.mHandler     // Catch:{ all -> 0x0083 }
            com.android.systemui.DessertCaseView$1 r1 = r3.mJuggle     // Catch:{ all -> 0x0083 }
            r0.removeCallbacks(r1)     // Catch:{ all -> 0x0083 }
        L_0x001c:
            r3.mWidth = r4     // Catch:{ all -> 0x0083 }
            r3.mHeight = r5     // Catch:{ all -> 0x0083 }
            r4 = 0
            r3.mCells = r4     // Catch:{ all -> 0x0083 }
            r3.removeAllViewsInLayout()     // Catch:{ all -> 0x0083 }
            java.util.HashSet r4 = r3.mFreeList     // Catch:{ all -> 0x0083 }
            r4.clear()     // Catch:{ all -> 0x0083 }
            int r4 = r3.mHeight     // Catch:{ all -> 0x0083 }
            int r5 = r3.mCellSize     // Catch:{ all -> 0x0083 }
            int r4 = r4 / r5
            r3.mRows = r4     // Catch:{ all -> 0x0083 }
            int r0 = r3.mWidth     // Catch:{ all -> 0x0083 }
            int r0 = r0 / r5
            r3.mColumns = r0     // Catch:{ all -> 0x0083 }
            int r4 = r4 * r0
            android.view.View[] r4 = new android.view.View[r4]     // Catch:{ all -> 0x0083 }
            r3.mCells = r4     // Catch:{ all -> 0x0083 }
            r4 = 1048576000(0x3e800000, float:0.25)
            r3.setScaleX(r4)     // Catch:{ all -> 0x0083 }
            r3.setScaleY(r4)     // Catch:{ all -> 0x0083 }
            int r5 = r3.mWidth     // Catch:{ all -> 0x0083 }
            int r0 = r3.mCellSize     // Catch:{ all -> 0x0083 }
            int r1 = r3.mColumns     // Catch:{ all -> 0x0083 }
            int r0 = r0 * r1
            int r5 = r5 - r0
            float r5 = (float) r5     // Catch:{ all -> 0x0083 }
            r0 = 1056964608(0x3f000000, float:0.5)
            float r5 = r5 * r0
            float r5 = r5 * r4
            r3.setTranslationX(r5)     // Catch:{ all -> 0x0083 }
            int r5 = r3.mHeight     // Catch:{ all -> 0x0083 }
            int r1 = r3.mCellSize     // Catch:{ all -> 0x0083 }
            int r2 = r3.mRows     // Catch:{ all -> 0x0083 }
            int r1 = r1 * r2
            int r5 = r5 - r1
            float r5 = (float) r5     // Catch:{ all -> 0x0083 }
            float r5 = r5 * r0
            float r5 = r5 * r4
            r3.setTranslationY(r5)     // Catch:{ all -> 0x0083 }
            r4 = r7
        L_0x0063:
            int r5 = r3.mRows     // Catch:{ all -> 0x0083 }
            if (r4 >= r5) goto L_0x007c
            r5 = r7
        L_0x0068:
            int r0 = r3.mColumns     // Catch:{ all -> 0x0083 }
            if (r5 >= r0) goto L_0x0079
            java.util.HashSet r0 = r3.mFreeList     // Catch:{ all -> 0x0083 }
            android.graphics.Point r1 = new android.graphics.Point     // Catch:{ all -> 0x0083 }
            r1.<init>(r5, r4)     // Catch:{ all -> 0x0083 }
            r0.add(r1)     // Catch:{ all -> 0x0083 }
            int r5 = r5 + 1
            goto L_0x0068
        L_0x0079:
            int r4 = r4 + 1
            goto L_0x0063
        L_0x007c:
            if (r6 == 0) goto L_0x0081
            r3.start()     // Catch:{ all -> 0x0083 }
        L_0x0081:
            monitor-exit(r3)
            return
        L_0x0083:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.DessertCaseView.onSizeChanged(int, int, int, int):void");
    }

    public static class RescalingContainer extends FrameLayout {
        public DessertCaseView mView;

        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            float f = (float) (i3 - i);
            float f2 = (float) (i4 - i2);
            int i5 = (int) ((f / 0.25f) / 2.0f);
            int i6 = (int) ((f2 / 0.25f) / 2.0f);
            int i7 = (int) ((f * 0.5f) + ((float) i));
            int i8 = (int) ((f2 * 0.5f) + ((float) i2));
            this.mView.layout(i7 - i5, i8 - i6, i7 + i5, i8 + i6);
        }

        public RescalingContainer(Context context) {
            super(context);
            setSystemUiVisibility(5638);
        }
    }

    static {
        Class<DessertCaseView> cls = DessertCaseView.class;
        int[] iArr = {C1777R.C1778drawable.dessert_kitkat, C1777R.C1778drawable.dessert_android};
        PASTRIES = iArr;
        int[] iArr2 = {C1777R.C1778drawable.dessert_cupcake, C1777R.C1778drawable.dessert_donut, C1777R.C1778drawable.dessert_eclair, C1777R.C1778drawable.dessert_froyo, C1777R.C1778drawable.dessert_gingerbread, C1777R.C1778drawable.dessert_honeycomb, C1777R.C1778drawable.dessert_ics, C1777R.C1778drawable.dessert_jellybean};
        RARE_PASTRIES = iArr2;
        int[] iArr3 = {C1777R.C1778drawable.dessert_petitfour, C1777R.C1778drawable.dessert_donutburger, C1777R.C1778drawable.dessert_flan, C1777R.C1778drawable.dessert_keylimepie};
        XRARE_PASTRIES = iArr3;
        int[] iArr4 = {C1777R.C1778drawable.dessert_zombiegingerbread, C1777R.C1778drawable.dessert_dandroid, C1777R.C1778drawable.dessert_jandycane};
        XXRARE_PASTRIES = iArr4;
        NUM_PASTRIES = iArr.length + iArr2.length + iArr3.length + iArr4.length;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DessertCaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        int i = 0;
        Resources resources = getResources();
        this.mStarted = false;
        this.mCellSize = resources.getDimensionPixelSize(C1777R.dimen.dessert_case_cell_size);
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (this.mCellSize < 512) {
            options.inSampleSize = 2;
        }
        options.inMutable = true;
        Bitmap bitmap = null;
        int[][] iArr = {PASTRIES, RARE_PASTRIES, XRARE_PASTRIES, XXRARE_PASTRIES};
        int i2 = 0;
        for (int i3 = 4; i2 < i3; i3 = 4) {
            int[] iArr2 = iArr[i2];
            int length = iArr2.length;
            int i4 = i;
            while (i4 < length) {
                int i5 = iArr2[i4];
                options.inBitmap = bitmap;
                bitmap = BitmapFactory.decodeResource(resources, i5, options);
                Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ALPHA_8);
                Canvas canvas = new Canvas(createBitmap);
                Paint paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(MASK));
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, createBitmap);
                bitmapDrawable.setColorFilter(new ColorMatrixColorFilter(ALPHA_MASK));
                int i6 = this.mCellSize;
                bitmapDrawable.setBounds(0, 0, i6, i6);
                this.mDrawables.append(i5, bitmapDrawable);
                i4++;
                i = 0;
            }
            int i7 = i;
            i2++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x011b A[LOOP:4: B:48:0x0119->B:49:0x011b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0145 A[SYNTHETIC, Splitter:B:53:0x0145] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01db  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void place(android.view.View r17, android.graphics.Point r18, boolean r19) {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            r2 = r18
            monitor-enter(r16)
            int r3 = r2.x     // Catch:{ all -> 0x01ff }
            int r4 = r2.y     // Catch:{ all -> 0x01ff }
            double r5 = java.lang.Math.random()     // Catch:{ all -> 0x01ff }
            float r5 = (float) r5     // Catch:{ all -> 0x01ff }
            r6 = 33554433(0x2000001, float:9.403956E-38)
            java.lang.Object r7 = r0.getTag(r6)     // Catch:{ all -> 0x01ff }
            r8 = 0
            if (r7 == 0) goto L_0x0038
            android.graphics.Point[] r7 = getOccupied(r17)     // Catch:{ all -> 0x01ff }
            int r9 = r7.length     // Catch:{ all -> 0x01ff }
            r10 = 0
        L_0x0020:
            if (r10 >= r9) goto L_0x0038
            r11 = r7[r10]     // Catch:{ all -> 0x01ff }
            java.util.HashSet r12 = r1.mFreeList     // Catch:{ all -> 0x01ff }
            r12.add(r11)     // Catch:{ all -> 0x01ff }
            android.view.View[] r12 = r1.mCells     // Catch:{ all -> 0x01ff }
            int r13 = r11.y     // Catch:{ all -> 0x01ff }
            int r14 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r13 = r13 * r14
            int r11 = r11.x     // Catch:{ all -> 0x01ff }
            int r13 = r13 + r11
            r12[r13] = r8     // Catch:{ all -> 0x01ff }
            int r10 = r10 + 1
            goto L_0x0020
        L_0x0038:
            r7 = 1008981770(0x3c23d70a, float:0.01)
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            r9 = 3
            r10 = 2
            r11 = 1
            if (r7 >= 0) goto L_0x004e
            int r5 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r5 = r5 - r9
            if (r3 >= r5) goto L_0x0073
            int r5 = r1.mRows     // Catch:{ all -> 0x01ff }
            int r5 = r5 - r9
            if (r4 >= r5) goto L_0x0073
            r9 = 4
            goto L_0x0074
        L_0x004e:
            r7 = 1036831949(0x3dcccccd, float:0.1)
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x0060
            int r5 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r5 = r5 - r10
            if (r3 >= r5) goto L_0x0073
            int r5 = r1.mRows     // Catch:{ all -> 0x01ff }
            int r5 = r5 - r10
            if (r4 >= r5) goto L_0x0073
            goto L_0x0074
        L_0x0060:
            r7 = 1051260355(0x3ea8f5c3, float:0.33)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x0073
            int r5 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r5 = r5 - r11
            if (r3 == r5) goto L_0x0073
            int r5 = r1.mRows     // Catch:{ all -> 0x01ff }
            int r5 = r5 - r11
            if (r4 == r5) goto L_0x0073
            r9 = r10
            goto L_0x0074
        L_0x0073:
            r9 = r11
        L_0x0074:
            r0.setTag(r6, r2)     // Catch:{ all -> 0x01ff }
            r2 = 33554434(0x2000002, float:9.403957E-38)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x01ff }
            r0.setTag(r2, r5)     // Catch:{ all -> 0x01ff }
            java.util.HashSet<android.view.View> r2 = r1.tmpSet     // Catch:{ all -> 0x01ff }
            r2.clear()     // Catch:{ all -> 0x01ff }
            android.graphics.Point[] r2 = getOccupied(r17)     // Catch:{ all -> 0x01ff }
            int r5 = r2.length     // Catch:{ all -> 0x01ff }
            r6 = 0
        L_0x008c:
            if (r6 >= r5) goto L_0x00a6
            r7 = r2[r6]     // Catch:{ all -> 0x01ff }
            android.view.View[] r10 = r1.mCells     // Catch:{ all -> 0x01ff }
            int r11 = r7.y     // Catch:{ all -> 0x01ff }
            int r12 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r11 = r11 * r12
            int r7 = r7.x     // Catch:{ all -> 0x01ff }
            int r11 = r11 + r7
            r7 = r10[r11]     // Catch:{ all -> 0x01ff }
            if (r7 == 0) goto L_0x00a3
            java.util.HashSet<android.view.View> r10 = r1.tmpSet     // Catch:{ all -> 0x01ff }
            r10.add(r7)     // Catch:{ all -> 0x01ff }
        L_0x00a3:
            int r6 = r6 + 1
            goto L_0x008c
        L_0x00a6:
            java.util.HashSet<android.view.View> r5 = r1.tmpSet     // Catch:{ all -> 0x01ff }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x01ff }
        L_0x00ac:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x01ff }
            if (r6 == 0) goto L_0x0117
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x01ff }
            android.view.View r6 = (android.view.View) r6     // Catch:{ all -> 0x01ff }
            android.graphics.Point[] r7 = getOccupied(r6)     // Catch:{ all -> 0x01ff }
            int r10 = r7.length     // Catch:{ all -> 0x01ff }
            r11 = 0
        L_0x00be:
            if (r11 >= r10) goto L_0x00d6
            r12 = r7[r11]     // Catch:{ all -> 0x01ff }
            java.util.HashSet r13 = r1.mFreeList     // Catch:{ all -> 0x01ff }
            r13.add(r12)     // Catch:{ all -> 0x01ff }
            android.view.View[] r13 = r1.mCells     // Catch:{ all -> 0x01ff }
            int r14 = r12.y     // Catch:{ all -> 0x01ff }
            int r15 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r14 = r14 * r15
            int r12 = r12.x     // Catch:{ all -> 0x01ff }
            int r14 = r14 + r12
            r13[r14] = r8     // Catch:{ all -> 0x01ff }
            int r11 = r11 + 1
            goto L_0x00be
        L_0x00d6:
            if (r6 == r0) goto L_0x00ac
            r7 = 33554433(0x2000001, float:9.403956E-38)
            r6.setTag(r7, r8)     // Catch:{ all -> 0x01ff }
            if (r19 == 0) goto L_0x0113
            android.view.ViewPropertyAnimator r7 = r6.animate()     // Catch:{ all -> 0x01ff }
            android.view.ViewPropertyAnimator r7 = r7.withLayer()     // Catch:{ all -> 0x01ff }
            r10 = 1056964608(0x3f000000, float:0.5)
            android.view.ViewPropertyAnimator r7 = r7.scaleX(r10)     // Catch:{ all -> 0x01ff }
            android.view.ViewPropertyAnimator r7 = r7.scaleY(r10)     // Catch:{ all -> 0x01ff }
            r10 = 0
            android.view.ViewPropertyAnimator r7 = r7.alpha(r10)     // Catch:{ all -> 0x01ff }
            r10 = 500(0x1f4, double:2.47E-321)
            android.view.ViewPropertyAnimator r7 = r7.setDuration(r10)     // Catch:{ all -> 0x01ff }
            android.view.animation.AccelerateInterpolator r10 = new android.view.animation.AccelerateInterpolator     // Catch:{ all -> 0x01ff }
            r10.<init>()     // Catch:{ all -> 0x01ff }
            android.view.ViewPropertyAnimator r7 = r7.setInterpolator(r10)     // Catch:{ all -> 0x01ff }
            com.android.systemui.DessertCaseView$4 r10 = new com.android.systemui.DessertCaseView$4     // Catch:{ all -> 0x01ff }
            r10.<init>(r6)     // Catch:{ all -> 0x01ff }
            android.view.ViewPropertyAnimator r6 = r7.setListener(r10)     // Catch:{ all -> 0x01ff }
            r6.start()     // Catch:{ all -> 0x01ff }
            goto L_0x00ac
        L_0x0113:
            r1.removeView(r6)     // Catch:{ all -> 0x01ff }
            goto L_0x00ac
        L_0x0117:
            int r5 = r2.length     // Catch:{ all -> 0x01ff }
            r6 = 0
        L_0x0119:
            if (r6 >= r5) goto L_0x0131
            r7 = r2[r6]     // Catch:{ all -> 0x01ff }
            android.view.View[] r8 = r1.mCells     // Catch:{ all -> 0x01ff }
            int r10 = r7.y     // Catch:{ all -> 0x01ff }
            int r11 = r1.mColumns     // Catch:{ all -> 0x01ff }
            int r10 = r10 * r11
            int r11 = r7.x     // Catch:{ all -> 0x01ff }
            int r10 = r10 + r11
            r8[r10] = r0     // Catch:{ all -> 0x01ff }
            java.util.HashSet r8 = r1.mFreeList     // Catch:{ all -> 0x01ff }
            r8.remove(r7)     // Catch:{ all -> 0x01ff }
            int r6 = r6 + 1
            goto L_0x0119
        L_0x0131:
            r2 = 0
            float r2 = (float) r2     // Catch:{ all -> 0x01ff }
            r5 = 4
            float r5 = (float) r5     // Catch:{ all -> 0x01ff }
            double r6 = java.lang.Math.random()     // Catch:{ all -> 0x01ff }
            float r6 = (float) r6
            float r2 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r5, r2, r6, r2)
            int r2 = (int) r2
            float r2 = (float) r2
            r5 = 1119092736(0x42b40000, float:90.0)
            float r2 = r2 * r5
            if (r19 == 0) goto L_0x01db
            r17.bringToFront()     // Catch:{ all -> 0x01ff }
            android.animation.AnimatorSet r5 = new android.animation.AnimatorSet     // Catch:{ all -> 0x01ff }
            r5.<init>()     // Catch:{ all -> 0x01ff }
            r6 = 2
            android.animation.Animator[] r6 = new android.animation.Animator[r6]     // Catch:{ all -> 0x01ff }
            android.util.Property r7 = android.view.View.SCALE_X     // Catch:{ all -> 0x01ff }
            r8 = 1
            float[] r10 = new float[r8]     // Catch:{ all -> 0x01ff }
            float r11 = (float) r9     // Catch:{ all -> 0x01ff }
            r12 = 0
            r10[r12] = r11     // Catch:{ all -> 0x01ff }
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r0, r7, r10)     // Catch:{ all -> 0x01ff }
            r6[r12] = r7     // Catch:{ all -> 0x01ff }
            android.util.Property r7 = android.view.View.SCALE_Y     // Catch:{ all -> 0x01ff }
            float[] r10 = new float[r8]     // Catch:{ all -> 0x01ff }
            r10[r12] = r11     // Catch:{ all -> 0x01ff }
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r0, r7, r10)     // Catch:{ all -> 0x01ff }
            r6[r8] = r7     // Catch:{ all -> 0x01ff }
            r5.playTogether(r6)     // Catch:{ all -> 0x01ff }
            android.view.animation.AnticipateOvershootInterpolator r6 = new android.view.animation.AnticipateOvershootInterpolator     // Catch:{ all -> 0x01ff }
            r6.<init>()     // Catch:{ all -> 0x01ff }
            r5.setInterpolator(r6)     // Catch:{ all -> 0x01ff }
            r6 = 500(0x1f4, double:2.47E-321)
            r5.setDuration(r6)     // Catch:{ all -> 0x01ff }
            android.animation.AnimatorSet r6 = new android.animation.AnimatorSet     // Catch:{ all -> 0x01ff }
            r6.<init>()     // Catch:{ all -> 0x01ff }
            r7 = 3
            android.animation.Animator[] r7 = new android.animation.Animator[r7]     // Catch:{ all -> 0x01ff }
            android.util.Property r8 = android.view.View.ROTATION     // Catch:{ all -> 0x01ff }
            r10 = 1
            float[] r11 = new float[r10]     // Catch:{ all -> 0x01ff }
            r12 = 0
            r11[r12] = r2     // Catch:{ all -> 0x01ff }
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r0, r8, r11)     // Catch:{ all -> 0x01ff }
            r7[r12] = r2     // Catch:{ all -> 0x01ff }
            android.util.Property r2 = android.view.View.X     // Catch:{ all -> 0x01ff }
            float[] r8 = new float[r10]     // Catch:{ all -> 0x01ff }
            int r11 = r1.mCellSize     // Catch:{ all -> 0x01ff }
            int r3 = r3 * r11
            int r9 = r9 - r10
            int r11 = r11 * r9
            int r11 = r11 / 2
            int r3 = r3 + r11
            float r3 = (float) r3     // Catch:{ all -> 0x01ff }
            r11 = 0
            r8[r11] = r3     // Catch:{ all -> 0x01ff }
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r0, r2, r8)     // Catch:{ all -> 0x01ff }
            r7[r10] = r2     // Catch:{ all -> 0x01ff }
            android.util.Property r2 = android.view.View.Y     // Catch:{ all -> 0x01ff }
            float[] r3 = new float[r10]     // Catch:{ all -> 0x01ff }
            int r8 = r1.mCellSize     // Catch:{ all -> 0x01ff }
            int r4 = r4 * r8
            int r9 = r9 * r8
            r8 = 2
            int r9 = r9 / r8
            int r4 = r4 + r9
            float r4 = (float) r4     // Catch:{ all -> 0x01ff }
            r9 = 0
            r3[r9] = r4     // Catch:{ all -> 0x01ff }
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r0, r2, r3)     // Catch:{ all -> 0x01ff }
            r7[r8] = r2     // Catch:{ all -> 0x01ff }
            r6.playTogether(r7)     // Catch:{ all -> 0x01ff }
            android.view.animation.DecelerateInterpolator r2 = new android.view.animation.DecelerateInterpolator     // Catch:{ all -> 0x01ff }
            r2.<init>()     // Catch:{ all -> 0x01ff }
            r6.setInterpolator(r2)     // Catch:{ all -> 0x01ff }
            r2 = 500(0x1f4, double:2.47E-321)
            r6.setDuration(r2)     // Catch:{ all -> 0x01ff }
            com.android.systemui.DessertCaseView$3 r2 = new com.android.systemui.DessertCaseView$3     // Catch:{ all -> 0x01ff }
            r2.<init>(r0)     // Catch:{ all -> 0x01ff }
            r5.addListener(r2)     // Catch:{ all -> 0x01ff }
            r5.start()     // Catch:{ all -> 0x01ff }
            r6.start()     // Catch:{ all -> 0x01ff }
            goto L_0x01fd
        L_0x01db:
            int r5 = r1.mCellSize     // Catch:{ all -> 0x01ff }
            int r3 = r3 * r5
            int r6 = r9 + -1
            int r5 = r5 * r6
            int r5 = r5 / 2
            int r3 = r3 + r5
            float r3 = (float) r3     // Catch:{ all -> 0x01ff }
            r0.setX(r3)     // Catch:{ all -> 0x01ff }
            int r3 = r1.mCellSize     // Catch:{ all -> 0x01ff }
            int r4 = r4 * r3
            int r6 = r6 * r3
            int r6 = r6 / 2
            int r4 = r4 + r6
            float r3 = (float) r4     // Catch:{ all -> 0x01ff }
            r0.setY(r3)     // Catch:{ all -> 0x01ff }
            float r3 = (float) r9     // Catch:{ all -> 0x01ff }
            r0.setScaleX(r3)     // Catch:{ all -> 0x01ff }
            r0.setScaleY(r3)     // Catch:{ all -> 0x01ff }
            r0.setRotation(r2)     // Catch:{ all -> 0x01ff }
        L_0x01fd:
            monitor-exit(r16)
            return
        L_0x01ff:
            r0 = move-exception
            monitor-exit(r16)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.DessertCaseView.place(android.view.View, android.graphics.Point, boolean):void");
    }

    public final void start() {
        if (!this.mStarted) {
            this.mStarted = true;
            fillFreeList(2000);
        }
        this.mHandler.postDelayed(this.mJuggle, 5000);
    }

    public static Point[] getOccupied(View view) {
        int intValue = ((Integer) view.getTag(33554434)).intValue();
        Point point = (Point) view.getTag(33554433);
        if (point == null || intValue == 0) {
            return new Point[0];
        }
        Point[] pointArr = new Point[(intValue * intValue)];
        int i = 0;
        for (int i2 = 0; i2 < intValue; i2++) {
            int i3 = 0;
            while (i3 < intValue) {
                pointArr[i] = new Point(point.x + i2, point.y + i3);
                i3++;
                i++;
            }
        }
        return pointArr;
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
