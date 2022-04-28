package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import java.util.Arrays;
import java.util.Objects;

public class BaseIconFactory implements AutoCloseable {
    public static int PLACEHOLDER_BACKGROUND_COLOR = Color.rgb(245, 245, 245);
    public final Canvas mCanvas;
    public final ColorExtractor mColorExtractor;
    public final Context mContext;
    public final int mFillResIconDpi;
    public final int mIconBitmapSize;
    public final SparseBooleanArray mIsUserBadged = new SparseBooleanArray();
    public IconNormalizer mNormalizer;
    public final Rect mOldBounds = new Rect();
    public final PackageManager mPm;
    public ShadowGenerator mShadowGenerator;
    public final boolean mShapeDetection;
    public int mWrapperBackgroundColor = -1;
    public Drawable mWrapperIcon;

    public static class IconOptions {
        public UserHandle mUserHandle;
    }

    public static class NoopDrawable extends ColorDrawable {
        public final int getIntrinsicHeight() {
            return 1;
        }

        public final int getIntrinsicWidth() {
            return 1;
        }
    }

    public void close() {
        this.mWrapperBackgroundColor = -1;
    }

    @TargetApi(10000)
    public final BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions) {
        DebugInfo$$ExternalSyntheticOutline0 debugInfo$$ExternalSyntheticOutline0;
        UserHandle userHandle;
        boolean z;
        int i;
        int i2;
        boolean z2 = true;
        float[] fArr = new float[1];
        Drawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, (RectF) null, fArr);
        float f = fArr[0];
        int i3 = this.mIconBitmapSize;
        Bitmap createBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_8888);
        if (normalizeAndWrapToAdaptiveIcon != null) {
            this.mCanvas.setBitmap(createBitmap);
            this.mOldBounds.set(normalizeAndWrapToAdaptiveIcon.getBounds());
            if (normalizeAndWrapToAdaptiveIcon instanceof AdaptiveIconDrawable) {
                float f2 = (float) i3;
                int max = Math.max((int) Math.ceil((double) (0.035f * f2)), Math.round(((1.0f - f) * f2) / 2.0f));
                int i4 = (i3 - max) - max;
                normalizeAndWrapToAdaptiveIcon.setBounds(0, 0, i4, i4);
                int save = this.mCanvas.save();
                float f3 = (float) max;
                this.mCanvas.translate(f3, f3);
                if (normalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
                    ((BitmapInfo.Extender) normalizeAndWrapToAdaptiveIcon).drawForPersistence(this.mCanvas);
                } else {
                    normalizeAndWrapToAdaptiveIcon.draw(this.mCanvas);
                }
                this.mCanvas.restoreToCount(save);
            } else {
                if (normalizeAndWrapToAdaptiveIcon instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) normalizeAndWrapToAdaptiveIcon;
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    if (createBitmap != null && bitmap.getDensity() == 0) {
                        bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                    }
                }
                int intrinsicWidth = normalizeAndWrapToAdaptiveIcon.getIntrinsicWidth();
                int intrinsicHeight = normalizeAndWrapToAdaptiveIcon.getIntrinsicHeight();
                if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                    float f4 = ((float) intrinsicWidth) / ((float) intrinsicHeight);
                    if (intrinsicWidth > intrinsicHeight) {
                        i = (int) (((float) i3) / f4);
                        i2 = i3;
                    } else if (intrinsicHeight > intrinsicWidth) {
                        i2 = (int) (((float) i3) * f4);
                        i = i3;
                    }
                    int i5 = (i3 - i2) / 2;
                    int i6 = (i3 - i) / 2;
                    normalizeAndWrapToAdaptiveIcon.setBounds(i5, i6, i2 + i5, i + i6);
                    this.mCanvas.save();
                    float f5 = (float) (i3 / 2);
                    this.mCanvas.scale(f, f, f5, f5);
                    normalizeAndWrapToAdaptiveIcon.draw(this.mCanvas);
                    this.mCanvas.restore();
                }
                i2 = i3;
                i = i2;
                int i52 = (i3 - i2) / 2;
                int i62 = (i3 - i) / 2;
                normalizeAndWrapToAdaptiveIcon.setBounds(i52, i62, i2 + i52, i + i62);
                this.mCanvas.save();
                float f52 = (float) (i3 / 2);
                this.mCanvas.scale(f, f, f52, f52);
                normalizeAndWrapToAdaptiveIcon.draw(this.mCanvas);
                this.mCanvas.restore();
            }
            normalizeAndWrapToAdaptiveIcon.setBounds(this.mOldBounds);
            this.mCanvas.setBitmap((Bitmap) null);
        }
        if (normalizeAndWrapToAdaptiveIcon instanceof AdaptiveIconDrawable) {
            this.mCanvas.setBitmap(createBitmap);
            if (this.mShadowGenerator == null) {
                this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
            }
            this.mShadowGenerator.recreateIcon(Bitmap.createBitmap(createBitmap), this.mCanvas);
            this.mCanvas.setBitmap((Bitmap) null);
        }
        int extractColor = extractColor(createBitmap);
        BitmapInfo bitmapInfo = new BitmapInfo(createBitmap, extractColor);
        if (normalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
            bitmapInfo = ((BitmapInfo.Extender) normalizeAndWrapToAdaptiveIcon).getExtendedInfo(createBitmap, extractColor, this, fArr[0]);
        }
        DebugInfo$$ExternalSyntheticOutline0 debugInfo$$ExternalSyntheticOutline02 = DebugInfo$$ExternalSyntheticOutline0.INSTANCE;
        if (iconOptions == null || (userHandle = iconOptions.mUserHandle) == null) {
            debugInfo$$ExternalSyntheticOutline0 = debugInfo$$ExternalSyntheticOutline02;
        } else {
            int hashCode = userHandle.hashCode();
            int indexOfKey = this.mIsUserBadged.indexOfKey(hashCode);
            if (indexOfKey >= 0) {
                z = this.mIsUserBadged.valueAt(indexOfKey);
            } else {
                NoopDrawable noopDrawable = new NoopDrawable();
                if (noopDrawable == this.mPm.getUserBadgedIcon(noopDrawable, iconOptions.mUserHandle)) {
                    z2 = false;
                }
                this.mIsUserBadged.put(hashCode, z2);
                z = z2;
            }
            if (z) {
                new FlagOp$$ExternalSyntheticLambda0
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x014b: CONSTRUCTOR  (r11v6 ? I:com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0) =  call: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0.<init>():void type: CONSTRUCTOR in method: com.android.launcher3.icons.BaseIconFactory.createBadgedIconBitmap(android.graphics.drawable.Drawable, com.android.launcher3.icons.BaseIconFactory$IconOptions):com.android.launcher3.icons.BitmapInfo, dex: classes.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                    	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r11v6 ?
                    	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:189)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:620)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	... 39 more
                    */
                /*
                    this = this;
                    r0 = 1
                    float[] r1 = new float[r0]
                    r2 = 0
                    android.graphics.drawable.Drawable r12 = r11.normalizeAndWrapToAdaptiveIcon(r12, r2, r1)
                    r3 = 0
                    r4 = r1[r3]
                    int r5 = r11.mIconBitmapSize
                    android.graphics.Bitmap$Config r6 = android.graphics.Bitmap.Config.ARGB_8888
                    android.graphics.Bitmap r6 = android.graphics.Bitmap.createBitmap(r5, r5, r6)
                    if (r12 != 0) goto L_0x0017
                    goto L_0x00da
                L_0x0017:
                    android.graphics.Canvas r7 = r11.mCanvas
                    r7.setBitmap(r6)
                    android.graphics.Rect r7 = r11.mOldBounds
                    android.graphics.Rect r8 = r12.getBounds()
                    r7.set(r8)
                    boolean r7 = r12 instanceof android.graphics.drawable.AdaptiveIconDrawable
                    if (r7 == 0) goto L_0x006c
                    r7 = 1024416809(0x3d0f5c29, float:0.035)
                    float r8 = (float) r5
                    float r7 = r7 * r8
                    double r9 = (double) r7
                    double r9 = java.lang.Math.ceil(r9)
                    int r7 = (int) r9
                    r9 = 1065353216(0x3f800000, float:1.0)
                    float r9 = r9 - r4
                    float r9 = r9 * r8
                    r4 = 1073741824(0x40000000, float:2.0)
                    float r9 = r9 / r4
                    int r4 = java.lang.Math.round(r9)
                    int r4 = java.lang.Math.max(r7, r4)
                    int r5 = r5 - r4
                    int r5 = r5 - r4
                    r12.setBounds(r3, r3, r5, r5)
                    android.graphics.Canvas r5 = r11.mCanvas
                    int r5 = r5.save()
                    android.graphics.Canvas r7 = r11.mCanvas
                    float r4 = (float) r4
                    r7.translate(r4, r4)
                    boolean r4 = r12 instanceof com.android.launcher3.icons.BitmapInfo.Extender
                    if (r4 == 0) goto L_0x0061
                    r4 = r12
                    com.android.launcher3.icons.BitmapInfo$Extender r4 = (com.android.launcher3.icons.BitmapInfo.Extender) r4
                    android.graphics.Canvas r7 = r11.mCanvas
                    r4.drawForPersistence(r7)
                    goto L_0x0066
                L_0x0061:
                    android.graphics.Canvas r4 = r11.mCanvas
                    r12.draw(r4)
                L_0x0066:
                    android.graphics.Canvas r4 = r11.mCanvas
                    r4.restoreToCount(r5)
                    goto L_0x00d0
                L_0x006c:
                    boolean r7 = r12 instanceof android.graphics.drawable.BitmapDrawable
                    if (r7 == 0) goto L_0x008c
                    r7 = r12
                    android.graphics.drawable.BitmapDrawable r7 = (android.graphics.drawable.BitmapDrawable) r7
                    android.graphics.Bitmap r8 = r7.getBitmap()
                    if (r6 == 0) goto L_0x008c
                    int r8 = r8.getDensity()
                    if (r8 != 0) goto L_0x008c
                    android.content.Context r8 = r11.mContext
                    android.content.res.Resources r8 = r8.getResources()
                    android.util.DisplayMetrics r8 = r8.getDisplayMetrics()
                    r7.setTargetDensity(r8)
                L_0x008c:
                    int r7 = r12.getIntrinsicWidth()
                    int r8 = r12.getIntrinsicHeight()
                    if (r7 <= 0) goto L_0x00aa
                    if (r8 <= 0) goto L_0x00aa
                    float r9 = (float) r7
                    float r10 = (float) r8
                    float r9 = r9 / r10
                    if (r7 <= r8) goto L_0x00a3
                    float r7 = (float) r5
                    float r7 = r7 / r9
                    int r7 = (int) r7
                    r8 = r7
                    r7 = r5
                    goto L_0x00ac
                L_0x00a3:
                    if (r8 <= r7) goto L_0x00aa
                    float r7 = (float) r5
                    float r7 = r7 * r9
                    int r7 = (int) r7
                    r8 = r5
                    goto L_0x00ac
                L_0x00aa:
                    r7 = r5
                    r8 = r7
                L_0x00ac:
                    int r9 = r5 - r7
                    int r9 = r9 / 2
                    int r10 = r5 - r8
                    int r10 = r10 / 2
                    int r7 = r7 + r9
                    int r8 = r8 + r10
                    r12.setBounds(r9, r10, r7, r8)
                    android.graphics.Canvas r7 = r11.mCanvas
                    r7.save()
                    android.graphics.Canvas r7 = r11.mCanvas
                    int r5 = r5 / 2
                    float r5 = (float) r5
                    r7.scale(r4, r4, r5, r5)
                    android.graphics.Canvas r4 = r11.mCanvas
                    r12.draw(r4)
                    android.graphics.Canvas r4 = r11.mCanvas
                    r4.restore()
                L_0x00d0:
                    android.graphics.Rect r4 = r11.mOldBounds
                    r12.setBounds(r4)
                    android.graphics.Canvas r4 = r11.mCanvas
                    r4.setBitmap(r2)
                L_0x00da:
                    boolean r4 = r12 instanceof android.graphics.drawable.AdaptiveIconDrawable
                    if (r4 == 0) goto L_0x0100
                    android.graphics.Canvas r4 = r11.mCanvas
                    r4.setBitmap(r6)
                    com.android.launcher3.icons.ShadowGenerator r4 = r11.mShadowGenerator
                    if (r4 != 0) goto L_0x00f0
                    com.android.launcher3.icons.ShadowGenerator r4 = new com.android.launcher3.icons.ShadowGenerator
                    int r5 = r11.mIconBitmapSize
                    r4.<init>(r5)
                    r11.mShadowGenerator = r4
                L_0x00f0:
                    com.android.launcher3.icons.ShadowGenerator r4 = r11.mShadowGenerator
                    android.graphics.Bitmap r5 = android.graphics.Bitmap.createBitmap(r6)
                    android.graphics.Canvas r7 = r11.mCanvas
                    r4.recreateIcon(r5, r7)
                    android.graphics.Canvas r4 = r11.mCanvas
                    r4.setBitmap(r2)
                L_0x0100:
                    int r2 = r11.extractColor(r6)
                    com.android.launcher3.icons.BitmapInfo r4 = new com.android.launcher3.icons.BitmapInfo
                    r4.<init>(r6, r2)
                    boolean r5 = r12 instanceof com.android.launcher3.icons.BitmapInfo.Extender
                    if (r5 == 0) goto L_0x0115
                    com.android.launcher3.icons.BitmapInfo$Extender r12 = (com.android.launcher3.icons.BitmapInfo.Extender) r12
                    r1 = r1[r3]
                    com.android.launcher3.icons.ClockDrawableWrapper$ClockBitmapInfo r4 = r12.getExtendedInfo(r6, r2, r11, r1)
                L_0x0115:
                    android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0 r12 = android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0.INSTANCE
                    if (r13 == 0) goto L_0x0155
                    android.os.UserHandle r1 = r13.mUserHandle
                    if (r1 == 0) goto L_0x0155
                    int r1 = r1.hashCode()
                    android.util.SparseBooleanArray r2 = r11.mIsUserBadged
                    int r2 = r2.indexOfKey(r1)
                    if (r2 < 0) goto L_0x0130
                    android.util.SparseBooleanArray r11 = r11.mIsUserBadged
                    boolean r11 = r11.valueAt(r2)
                    goto L_0x0147
                L_0x0130:
                    com.android.launcher3.icons.BaseIconFactory$NoopDrawable r2 = new com.android.launcher3.icons.BaseIconFactory$NoopDrawable
                    r2.<init>()
                    android.content.pm.PackageManager r5 = r11.mPm
                    android.os.UserHandle r13 = r13.mUserHandle
                    android.graphics.drawable.Drawable r13 = r5.getUserBadgedIcon(r2, r13)
                    if (r2 == r13) goto L_0x0140
                    goto L_0x0141
                L_0x0140:
                    r0 = r3
                L_0x0141:
                    android.util.SparseBooleanArray r11 = r11.mIsUserBadged
                    r11.put(r1, r0)
                    r11 = r0
                L_0x0147:
                    if (r11 == 0) goto L_0x014f
                    com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0 r11 = new com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0
                    r11.<init>()
                    goto L_0x0156
                L_0x014f:
                    com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1 r11 = new com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                    r11.<init>()
                    goto L_0x0156
                L_0x0155:
                    r11 = r12
                L_0x0156:
                    java.util.Objects.requireNonNull(r4)
                    if (r11 != r12) goto L_0x015c
                    goto L_0x0168
                L_0x015c:
                    com.android.launcher3.icons.BitmapInfo r4 = r4.clone()
                    int r12 = r4.flags
                    int r11 = r11.apply(r12)
                    r4.flags = r11
                L_0x0168:
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.BaseIconFactory.createBadgedIconBitmap(android.graphics.drawable.Drawable, com.android.launcher3.icons.BaseIconFactory$IconOptions):com.android.launcher3.icons.BitmapInfo");
            }

            public final Drawable normalizeAndWrapToAdaptiveIcon(Drawable drawable, RectF rectF, float[] fArr) {
                float f;
                if (drawable == null) {
                    return null;
                }
                if (!(drawable instanceof AdaptiveIconDrawable)) {
                    if (this.mWrapperIcon == null) {
                        this.mWrapperIcon = this.mContext.getDrawable(C1777R.C1778drawable.adaptive_icon_drawable_wrapper).mutate();
                    }
                    AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) this.mWrapperIcon;
                    adaptiveIconDrawable.setBounds(0, 0, 1, 1);
                    boolean[] zArr = new boolean[1];
                    f = getNormalizer().getScale(drawable, rectF, adaptiveIconDrawable.getIconMask(), zArr);
                    if (!zArr[0]) {
                        FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable.getForeground();
                        fixedScaleDrawable.setDrawable(drawable);
                        float intrinsicHeight = (float) fixedScaleDrawable.getIntrinsicHeight();
                        float intrinsicWidth = (float) fixedScaleDrawable.getIntrinsicWidth();
                        float f2 = f * 0.46669f;
                        fixedScaleDrawable.mScaleX = f2;
                        fixedScaleDrawable.mScaleY = f2;
                        if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0.0f) {
                            fixedScaleDrawable.mScaleX = (intrinsicWidth / intrinsicHeight) * f2;
                        } else if (intrinsicWidth > intrinsicHeight && intrinsicHeight > 0.0f) {
                            fixedScaleDrawable.mScaleY = (intrinsicHeight / intrinsicWidth) * f2;
                        }
                        f = getNormalizer().getScale(adaptiveIconDrawable, rectF, (Path) null, (boolean[]) null);
                        ((ColorDrawable) adaptiveIconDrawable.getBackground()).setColor(this.mWrapperBackgroundColor);
                        drawable = adaptiveIconDrawable;
                    }
                } else {
                    f = getNormalizer().getScale(drawable, rectF, (Path) null, (boolean[]) null);
                }
                fArr[0] = f;
                return drawable;
            }

            public final BitmapInfo createIconBitmap(Bitmap bitmap) {
                int i;
                int i2;
                if (!(this.mIconBitmapSize == bitmap.getWidth() && this.mIconBitmapSize == bitmap.getHeight())) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
                    int i3 = this.mIconBitmapSize;
                    Bitmap createBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_8888);
                    this.mCanvas.setBitmap(createBitmap);
                    this.mOldBounds.set(bitmapDrawable.getBounds());
                    if (bitmapDrawable instanceof AdaptiveIconDrawable) {
                        float f = (float) i3;
                        int max = Math.max((int) Math.ceil((double) (0.035f * f)), Math.round((0.0f * f) / 2.0f));
                        int i4 = (i3 - max) - max;
                        bitmapDrawable.setBounds(0, 0, i4, i4);
                        int save = this.mCanvas.save();
                        float f2 = (float) max;
                        this.mCanvas.translate(f2, f2);
                        if (bitmapDrawable instanceof BitmapInfo.Extender) {
                            ((BitmapInfo.Extender) bitmapDrawable).drawForPersistence(this.mCanvas);
                        } else {
                            bitmapDrawable.draw(this.mCanvas);
                        }
                        this.mCanvas.restoreToCount(save);
                    } else {
                        Bitmap bitmap2 = bitmapDrawable.getBitmap();
                        if (createBitmap != null && bitmap2.getDensity() == 0) {
                            bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                        }
                        int intrinsicWidth = bitmapDrawable.getIntrinsicWidth();
                        int intrinsicHeight = bitmapDrawable.getIntrinsicHeight();
                        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                            float f3 = ((float) intrinsicWidth) / ((float) intrinsicHeight);
                            if (intrinsicWidth > intrinsicHeight) {
                                i = (int) (((float) i3) / f3);
                                i2 = i3;
                            } else if (intrinsicHeight > intrinsicWidth) {
                                i2 = (int) (((float) i3) * f3);
                                i = i3;
                            }
                            int i5 = (i3 - i2) / 2;
                            int i6 = (i3 - i) / 2;
                            bitmapDrawable.setBounds(i5, i6, i2 + i5, i + i6);
                            this.mCanvas.save();
                            float f4 = (float) (i3 / 2);
                            this.mCanvas.scale(1.0f, 1.0f, f4, f4);
                            bitmapDrawable.draw(this.mCanvas);
                            this.mCanvas.restore();
                        }
                        i2 = i3;
                        i = i2;
                        int i52 = (i3 - i2) / 2;
                        int i62 = (i3 - i) / 2;
                        bitmapDrawable.setBounds(i52, i62, i2 + i52, i + i62);
                        this.mCanvas.save();
                        float f42 = (float) (i3 / 2);
                        this.mCanvas.scale(1.0f, 1.0f, f42, f42);
                        bitmapDrawable.draw(this.mCanvas);
                        this.mCanvas.restore();
                    }
                    bitmapDrawable.setBounds(this.mOldBounds);
                    this.mCanvas.setBitmap((Bitmap) null);
                    bitmap = createBitmap;
                }
                return new BitmapInfo(bitmap, extractColor(bitmap));
            }

            public final int extractColor(Bitmap bitmap) {
                int i;
                char c;
                ColorExtractor colorExtractor = this.mColorExtractor;
                Objects.requireNonNull(colorExtractor);
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                char c2 = 20;
                int sqrt = (int) Math.sqrt((double) ((height * width) / 20));
                if (sqrt < 1) {
                    sqrt = 1;
                }
                float[] fArr = colorExtractor.mTmpHsv;
                Arrays.fill(fArr, 0.0f);
                float[] fArr2 = colorExtractor.mTmpHueScoreHistogram;
                Arrays.fill(fArr2, 0.0f);
                int i2 = -1;
                int[] iArr = colorExtractor.mTmpPixels;
                int i3 = 0;
                Arrays.fill(iArr, 0);
                int i4 = 0;
                int i5 = 0;
                float f = -1.0f;
                while (true) {
                    i = -16777216;
                    if (i4 >= height) {
                        break;
                    }
                    int i6 = i3;
                    while (i6 < width) {
                        int pixel = bitmap.getPixel(i6, i4);
                        if (((pixel >> 24) & 255) >= 128) {
                            int i7 = pixel | -16777216;
                            Color.colorToHSV(i7, fArr);
                            int i8 = (int) fArr[i3];
                            if (i8 >= 0 && i8 < fArr2.length) {
                                c = 20;
                                if (i5 < 20) {
                                    iArr[i5] = i7;
                                    i5++;
                                }
                                fArr2[i8] = fArr2[i8] + (fArr[1] * fArr[2]);
                                if (fArr2[i8] > f) {
                                    f = fArr2[i8];
                                    i2 = i8;
                                }
                                i6 += sqrt;
                                c2 = c;
                                i3 = 0;
                            }
                        }
                        c = 20;
                        i6 += sqrt;
                        c2 = c;
                        i3 = 0;
                    }
                    Bitmap bitmap2 = bitmap;
                    char c3 = c2;
                    i4 += sqrt;
                    i3 = 0;
                }
                SparseArray<Float> sparseArray = colorExtractor.mTmpRgbScores;
                sparseArray.clear();
                float f2 = -1.0f;
                for (int i9 = 0; i9 < i5; i9++) {
                    int i10 = iArr[i9];
                    Color.colorToHSV(i10, fArr);
                    if (((int) fArr[0]) == i2) {
                        float f3 = fArr[1];
                        float f4 = fArr[2];
                        int i11 = ((int) (100.0f * f3)) + ((int) (10000.0f * f4));
                        float f5 = f3 * f4;
                        Float f6 = sparseArray.get(i11);
                        if (f6 != null) {
                            f5 += f6.floatValue();
                        }
                        sparseArray.put(i11, Float.valueOf(f5));
                        if (f5 > f2) {
                            i = i10;
                            f2 = f5;
                        }
                    }
                }
                return i;
            }

            public final IconNormalizer getNormalizer() {
                if (this.mNormalizer == null) {
                    this.mNormalizer = new IconNormalizer(this.mContext, this.mIconBitmapSize, this.mShapeDetection);
                }
                return this.mNormalizer;
            }

            public BaseIconFactory(Context context, int i, int i2, boolean z) {
                Paint paint = new Paint(3);
                Context applicationContext = context.getApplicationContext();
                this.mContext = applicationContext;
                this.mShapeDetection = z;
                this.mFillResIconDpi = i;
                this.mIconBitmapSize = i2;
                this.mPm = applicationContext.getPackageManager();
                this.mColorExtractor = new ColorExtractor();
                Canvas canvas = new Canvas();
                this.mCanvas = canvas;
                canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(PLACEHOLDER_BACKGROUND_COLOR);
                paint.setTextSize(context.getResources().getDisplayMetrics().density * 20.0f);
                this.mWrapperBackgroundColor = -1;
            }

            public final Bitmap createScaledBitmapWithShadow(AdaptiveIconDrawable adaptiveIconDrawable) {
                float scale = getNormalizer().getScale(adaptiveIconDrawable, (RectF) null, (Path) null, (boolean[]) null);
                int i = this.mIconBitmapSize;
                Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
                this.mCanvas.setBitmap(createBitmap);
                this.mOldBounds.set(adaptiveIconDrawable.getBounds());
                float f = (float) i;
                int max = Math.max((int) Math.ceil((double) (0.035f * f)), Math.round(((1.0f - scale) * f) / 2.0f));
                int i2 = (i - max) - max;
                adaptiveIconDrawable.setBounds(0, 0, i2, i2);
                int save = this.mCanvas.save();
                float f2 = (float) max;
                this.mCanvas.translate(f2, f2);
                if (adaptiveIconDrawable instanceof BitmapInfo.Extender) {
                    ((BitmapInfo.Extender) adaptiveIconDrawable).drawForPersistence(this.mCanvas);
                } else {
                    adaptiveIconDrawable.draw(this.mCanvas);
                }
                this.mCanvas.restoreToCount(save);
                adaptiveIconDrawable.setBounds(this.mOldBounds);
                this.mCanvas.setBitmap((Bitmap) null);
                this.mCanvas.setBitmap(createBitmap);
                if (this.mShadowGenerator == null) {
                    this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
                }
                this.mShadowGenerator.recreateIcon(Bitmap.createBitmap(createBitmap), this.mCanvas);
                this.mCanvas.setBitmap((Bitmap) null);
                return createBitmap;
            }
        }
