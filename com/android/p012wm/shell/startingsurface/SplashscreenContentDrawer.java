package com.android.p012wm.shell.startingsurface;

import android.app.ActivityThread;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer */
public final class SplashscreenContentDrawer {
    public int mBrandingImageHeight;
    public int mBrandingImageWidth;
    @VisibleForTesting
    public final ColorCache mColorCache;
    public final Context mContext;
    public int mDefaultIconSize;
    public final IconProvider mIconProvider;
    public int mIconSize;
    public int mLastPackageContextConfigHash;
    public int mMainWindowShiftLength;
    public final Handler mSplashscreenWorkerHandler;
    public final SplashScreenWindowAttrs mTmpAttrs = new SplashScreenWindowAttrs();
    public final TransactionPool mTransactionPool;

    @VisibleForTesting
    /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache */
    public static class ColorCache extends BroadcastReceiver {
        public final ArrayMap<String, Colors> mColorMap = new ArrayMap<>();

        /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors */
        public static class Colors {
            public final IconColor[] mIconColors = new IconColor[2];
            public final WindowColor[] mWindowColors = new WindowColor[2];
        }

        public static <T extends Cache> T getCache(T[] tArr, int i, int[] iArr) {
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < 2; i3++) {
                T t = tArr[i3];
                if (t == null) {
                    i2 = -1;
                    iArr[0] = i3;
                } else if (t.mHash == i) {
                    t.mReuseCount++;
                    return t;
                } else {
                    int i4 = t.mReuseCount;
                    if (i4 < i2) {
                        iArr[0] = i3;
                        i2 = i4;
                    }
                }
            }
            return null;
        }

        /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Cache */
        public static class Cache {
            public final int mHash;
            public int mReuseCount;

            public Cache(int i) {
                this.mHash = i;
            }
        }

        /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$IconColor */
        public static class IconColor extends Cache {
            public final int mBgColor;
            public final int mFgColor;
            public final float mFgNonTranslucentRatio;
            public final boolean mIsBgComplex;
            public final boolean mIsBgGrayscale;

            public IconColor(int i, int i2, int i3, boolean z, boolean z2, float f) {
                super(i);
                this.mFgColor = i2;
                this.mBgColor = i3;
                this.mIsBgComplex = z;
                this.mIsBgGrayscale = z2;
                this.mFgNonTranslucentRatio = f;
            }
        }

        /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor */
        public static class WindowColor extends Cache {
            public final int mBgColor;

            public WindowColor(int i, int i2) {
                super(i);
                this.mBgColor = i2;
            }
        }

        public ColorCache(Context context, Handler handler) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            context.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, (String) null, handler);
        }

        public final void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            if (data != null) {
                this.mColorMap.remove(data.getEncodedSchemeSpecificPart());
            }
        }
    }

    /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs */
    public static class SplashScreenWindowAttrs {
        public int mAnimationDuration = 0;
        public Drawable mBrandingImage = null;
        public int mIconBgColor = 0;
        public Drawable mSplashScreenIcon = null;
        public int mWindowBgColor = 0;
        public int mWindowBgResId = 0;
    }

    /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder */
    public class StartingWindowViewBuilder {
        public boolean mAllowHandleEmpty;
        public Drawable[] mFinalIconDrawables;
        public int mFinalIconSize;
        public Drawable mOverlayDrawable;
        public int mSuggestType;
        public int mThemeColor;
        public Consumer<Runnable> mUiThreadInitTask;

        /* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder$ShapeIconFactory */
        public class ShapeIconFactory extends BaseIconFactory {
            public ShapeIconFactory(Context context, int i, int i2) {
                super(context, i, i2, true);
            }
        }

        public final void createIconDrawable(Drawable drawable, boolean z) {
            boolean z2;
            Drawable drawable2;
            SplashscreenIconDrawableFactory$MaskBackgroundDrawable splashscreenIconDrawableFactory$MaskBackgroundDrawable;
            if (z) {
                SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
                this.mFinalIconDrawables = new Drawable[]{new SplashscreenIconDrawableFactory$ImmobileIconDrawable(drawable, splashscreenContentDrawer.mDefaultIconSize, this.mFinalIconSize, splashscreenContentDrawer.mSplashscreenWorkerHandler)};
                return;
            }
            SplashscreenContentDrawer splashscreenContentDrawer2 = SplashscreenContentDrawer.this;
            int i = splashscreenContentDrawer2.mTmpAttrs.mIconBgColor;
            int i2 = this.mThemeColor;
            int i3 = splashscreenContentDrawer2.mDefaultIconSize;
            int i4 = this.mFinalIconSize;
            Handler handler = splashscreenContentDrawer2.mSplashscreenWorkerHandler;
            if (i == 0 || i == i2) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (drawable instanceof Animatable) {
                drawable2 = new SplashscreenIconDrawableFactory$AnimatableIconAnimateListener(drawable);
            } else if (drawable instanceof AdaptiveIconDrawable) {
                drawable2 = new SplashscreenIconDrawableFactory$ImmobileIconDrawable(drawable, i3, i4, handler);
                z2 = false;
            } else {
                drawable2 = new SplashscreenIconDrawableFactory$ImmobileIconDrawable(new SplashscreenIconDrawableFactory$AdaptiveForegroundDrawable(drawable), i3, i4, handler);
            }
            if (z2) {
                splashscreenIconDrawableFactory$MaskBackgroundDrawable = new SplashscreenIconDrawableFactory$MaskBackgroundDrawable(i);
            } else {
                splashscreenIconDrawableFactory$MaskBackgroundDrawable = null;
            }
            this.mFinalIconDrawables = new Drawable[]{drawable2, splashscreenIconDrawableFactory$MaskBackgroundDrawable};
        }

        public StartingWindowViewBuilder(Context context, ActivityInfo activityInfo) {
            this.mFinalIconSize = SplashscreenContentDrawer.this.mIconSize;
        }
    }

    @VisibleForTesting
    public static long getShowingDuration(long j, long j2) {
        return (j > j2 && j2 < 500) ? (j > 500 || j2 < 400) ? 400 : 500 : j2;
    }

    /* renamed from: -$$Nest$smisRgbSimilarInHsv  reason: not valid java name */
    public static boolean m296$$Nest$smisRgbSimilarInHsv(int i, int i2) {
        float f;
        double d;
        boolean z;
        boolean z2;
        int i3 = i;
        int i4 = i2;
        if (i3 != i4) {
            float luminance = Color.luminance(i);
            float luminance2 = Color.luminance(i2);
            if (luminance > luminance2) {
                f = (luminance + 0.05f) / (luminance2 + 0.05f);
            } else {
                f = (luminance2 + 0.05f) / (luminance + 0.05f);
            }
            if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -853329785, 32, (String) null, String.valueOf(Integer.toHexString(i)), String.valueOf(Integer.toHexString(i2)), Double.valueOf((double) f));
            }
            if (f >= 2.0f) {
                float[] fArr = new float[3];
                float[] fArr2 = new float[3];
                Color.colorToHSV(i3, fArr);
                Color.colorToHSV(i4, fArr2);
                int abs = ((((int) Math.abs(fArr[0] - fArr2[0])) + 180) % 360) - 180;
                double pow = Math.pow((double) (((float) abs) / 180.0f), 2.0d);
                double pow2 = Math.pow((double) (fArr[1] - fArr2[1]), 2.0d);
                double pow3 = Math.pow((double) (fArr[2] - fArr2[2]), 2.0d);
                double sqrt = Math.sqrt(((pow + pow2) + pow3) / 3.0d);
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    double d2 = (double) fArr[0];
                    d = sqrt;
                    double d3 = pow3;
                    double d4 = (double) fArr[1];
                    double d5 = (double) fArr2[1];
                    double d6 = pow2;
                    double d7 = (double) fArr[2];
                    ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW;
                    z2 = false;
                    Double valueOf = Double.valueOf(d2);
                    z = true;
                    ShellProtoLogImpl.m80v(shellProtoLogGroup, -137676175, 2796201, (String) null, Long.valueOf((long) abs), valueOf, Double.valueOf((double) fArr2[0]), Double.valueOf(d4), Double.valueOf(d5), Double.valueOf(d7), Double.valueOf((double) fArr2[2]), Double.valueOf(pow), Double.valueOf(d6), Double.valueOf(d3), Double.valueOf(d));
                } else {
                    z2 = false;
                    z = true;
                    d = sqrt;
                }
                if (d < 0.1d) {
                    return z;
                }
                return z2;
            }
        }
        return true;
    }

    public static int estimateWindowBGColor(Drawable drawable) {
        SplashscreenContentDrawer$DrawableColorTester$ColorTester splashscreenContentDrawer$DrawableColorTester$ColorTester;
        SplashscreenContentDrawer$DrawableColorTester$ColorTester splashscreenContentDrawer$DrawableColorTester$ColorTester2;
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            if (layerDrawable.getNumberOfLayers() > 0) {
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 428468608, 0, (String) null, (Object[]) null);
                }
                drawable = layerDrawable.getDrawable(0);
            }
        }
        if (drawable == null) {
            splashscreenContentDrawer$DrawableColorTester$ColorTester = new SplashscreenContentDrawer$DrawableColorTester$SingleColorTester(new ColorDrawable(getSystemBGColor()));
        } else {
            if (drawable instanceof ColorDrawable) {
                splashscreenContentDrawer$DrawableColorTester$ColorTester2 = new SplashscreenContentDrawer$DrawableColorTester$SingleColorTester((ColorDrawable) drawable);
            } else {
                splashscreenContentDrawer$DrawableColorTester$ColorTester2 = new C1928xd75860e0(drawable, 1);
            }
            splashscreenContentDrawer$DrawableColorTester$ColorTester = splashscreenContentDrawer$DrawableColorTester$ColorTester2;
        }
        if (splashscreenContentDrawer$DrawableColorTester$ColorTester.passFilterRatio() != 0.0f) {
            return splashscreenContentDrawer$DrawableColorTester$ColorTester.getDominantColor();
        }
        Slog.w("ShellStartingWindow", "Window background is transparent, fill background with black color");
        return getSystemBGColor();
    }

    public static void getWindowAttrs(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        boolean z;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
        splashScreenWindowAttrs.mWindowBgResId = obtainStyledAttributes.getResourceId(1, 0);
        splashScreenWindowAttrs.mWindowBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda7(obtainStyledAttributes), 0)).intValue();
        splashScreenWindowAttrs.mSplashScreenIcon = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes), (Integer) null);
        splashScreenWindowAttrs.mAnimationDuration = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda8(obtainStyledAttributes), 0)).intValue();
        splashScreenWindowAttrs.mBrandingImage = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda5(obtainStyledAttributes), (Integer) null);
        splashScreenWindowAttrs.mIconBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda9(obtainStyledAttributes), 0)).intValue();
        obtainStyledAttributes.recycle();
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            String valueOf = String.valueOf(Integer.toHexString(splashScreenWindowAttrs.mWindowBgColor));
            if (splashScreenWindowAttrs.mSplashScreenIcon != null) {
                z = true;
            } else {
                z = false;
            }
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1508732488, 28, (String) null, valueOf, Boolean.valueOf(z), Long.valueOf((long) splashScreenWindowAttrs.mAnimationDuration));
        }
    }

    public static int peekWindowBGColor(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        Drawable drawable;
        Trace.traceBegin(32, "peekWindowBGColor");
        if (splashScreenWindowAttrs.mWindowBgColor != 0) {
            drawable = new ColorDrawable(splashScreenWindowAttrs.mWindowBgColor);
        } else {
            int i = splashScreenWindowAttrs.mWindowBgResId;
            if (i != 0) {
                drawable = context.getDrawable(i);
            } else {
                drawable = new ColorDrawable(getSystemBGColor());
                Slog.w("ShellStartingWindow", "Window background does not exist, using " + drawable);
            }
        }
        int estimateWindowBGColor = estimateWindowBGColor(drawable);
        Trace.traceEnd(32);
        return estimateWindowBGColor;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x002d, code lost:
        if (r5 != null) goto L_0x0049;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getBGColorFromCache(android.content.pm.ActivityInfo r5, java.util.function.IntSupplier r6) {
        /*
            r4 = this;
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache r0 = r4.mColorCache
            java.lang.String r5 = r5.packageName
            int r1 = r4.mLastPackageContextConfigHash
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r4 = r4.mTmpAttrs
            int r2 = r4.mWindowBgColor
            int r4 = r4.mWindowBgResId
            java.util.Objects.requireNonNull(r0)
            android.util.ArrayMap<java.lang.String, com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors> r3 = r0.mColorMap
            java.lang.Object r3 = r3.get(r5)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors r3 = (com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.Colors) r3
            int r1 = r1 * 31
            int r1 = r1 + r2
            int r1 = r1 * 31
            int r1 = r1 + r4
            r4 = 1
            int[] r4 = new int[r4]
            r2 = 0
            r4[r2] = r2
            if (r3 == 0) goto L_0x0030
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor[] r5 = r3.mWindowColors
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Cache r5 = com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.getCache(r5, r1, r4)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor r5 = (com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.WindowColor) r5
            if (r5 == 0) goto L_0x003a
            goto L_0x0049
        L_0x0030:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors r3 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors
            r3.<init>()
            android.util.ArrayMap<java.lang.String, com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors> r0 = r0.mColorMap
            r0.put(r5, r3)
        L_0x003a:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor r5 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor
            int r6 = r6.getAsInt()
            r5.<init>(r1, r6)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor[] r6 = r3.mWindowColors
            r4 = r4[r2]
            r6[r4] = r5
        L_0x0049:
            int r4 = r5.mBgColor
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.getBGColorFromCache(android.content.pm.ActivityInfo, java.util.function.IntSupplier):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0167, code lost:
        if (r9 != null) goto L_0x0220;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x041f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x042d  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0486  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0196  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01a5  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0224  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0291  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x02bc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.window.SplashScreenView makeSplashScreenContentView(android.content.Context r25, android.window.StartingWindowInfo r26, @android.window.StartingWindowInfo.StartingWindowType int r27, java.util.function.Consumer<java.lang.Runnable> r28) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r26
            r3 = r27
            android.content.Context r4 = r0.mContext
            android.content.res.Resources r4 = r4.getResources()
            r5 = 17105548(0x105028c, float:2.443007E-38)
            int r4 = r4.getDimensionPixelSize(r5)
            r0.mIconSize = r4
            android.content.Context r4 = r0.mContext
            android.content.res.Resources r4 = r4.getResources()
            r5 = 17105547(0x105028b, float:2.4430066E-38)
            int r4 = r4.getDimensionPixelSize(r5)
            r0.mDefaultIconSize = r4
            android.content.Context r4 = r0.mContext
            android.content.res.Resources r4 = r4.getResources()
            r5 = 2131167047(0x7f070747, float:1.7948357E38)
            int r4 = r4.getDimensionPixelSize(r5)
            r0.mBrandingImageWidth = r4
            android.content.Context r4 = r0.mContext
            android.content.res.Resources r4 = r4.getResources()
            r5 = 2131167046(0x7f070746, float:1.7948355E38)
            int r4 = r4.getDimensionPixelSize(r5)
            r0.mBrandingImageHeight = r4
            android.content.Context r4 = r0.mContext
            android.content.res.Resources r4 = r4.getResources()
            r5 = 2131167049(0x7f070749, float:1.794836E38)
            int r4 = r4.getDimensionPixelSize(r5)
            r0.mMainWindowShiftLength = r4
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r4 = r0.mTmpAttrs
            getWindowAttrs(r1, r4)
            android.content.res.Resources r4 = r25.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r4 = r4.hashCode()
            r0.mLastPackageContextConfigHash = r4
            r4 = 0
            r5 = 4
            r6 = 0
            if (r3 != r5) goto L_0x0099
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r7 = r0.mTmpAttrs
            int[] r8 = com.android.internal.R.styleable.Window
            android.content.res.TypedArray r8 = r1.obtainStyledAttributes(r8)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda6 r9 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda6
            r9.<init>(r8)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r4)
            java.lang.Object r9 = safeReturnAttrDefault(r9, r10)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            r8.recycle()
            if (r9 == 0) goto L_0x0090
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r9)
            goto L_0x009a
        L_0x0090:
            int r7 = r7.mWindowBgResId
            if (r7 == 0) goto L_0x0099
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r7)
            goto L_0x009a
        L_0x0099:
            r7 = r6
        L_0x009a:
            android.content.pm.ActivityInfo r8 = r2.targetActivityInfo
            if (r8 == 0) goto L_0x009f
            goto L_0x00a3
        L_0x009f:
            android.app.ActivityManager$RunningTaskInfo r8 = r2.taskInfo
            android.content.pm.ActivityInfo r8 = r8.topActivityInfo
        L_0x00a3:
            if (r7 == 0) goto L_0x00af
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda2 r9 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda2
            r9.<init>(r7)
            int r9 = r0.getBGColorFromCache(r8, r9)
            goto L_0x00b8
        L_0x00af:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda3 r9 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda3
            r9.<init>(r0, r1)
            int r9 = r0.getBGColorFromCache(r8, r9)
        L_0x00b8:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder r10 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder
            r10.<init>(r1, r8)
            r10.mThemeColor = r9
            r10.mOverlayDrawable = r7
            r10.mSuggestType = r3
            r3 = r28
            r10.mUiThreadInitTask = r3
            boolean r2 = r26.allowHandleEmptySplashScreen()
            r10.mAllowHandleEmpty = r2
            int r2 = r10.mSuggestType
            r3 = 3
            r11 = 32
            r7 = 1
            if (r2 == r3) goto L_0x0413
            if (r2 != r5) goto L_0x00d9
            goto L_0x0413
        L_0x00d9:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r2 = r0.mTmpAttrs
            android.graphics.drawable.Drawable r9 = r2.mSplashScreenIcon
            r13 = 1067030938(0x3f99999a, float:1.2)
            if (r9 == 0) goto L_0x00f9
            int r3 = r2.mAnimationDuration
            int r2 = r2.mIconBgColor
            if (r2 == 0) goto L_0x00ec
            int r8 = r10.mThemeColor
            if (r2 != r8) goto L_0x00f3
        L_0x00ec:
            int r2 = r10.mFinalIconSize
            float r2 = (float) r2
            float r2 = r2 * r13
            int r2 = (int) r2
            r10.mFinalIconSize = r2
        L_0x00f3:
            r10.createIconDrawable(r9, r4)
            r2 = r4
            goto L_0x0417
        L_0x00f9:
            int r2 = r0.mIconSize
            float r2 = (float) r2
            int r9 = r0.mDefaultIconSize
            float r9 = (float) r9
            float r2 = r2 / r9
            android.content.res.Resources r9 = r25.getResources()
            android.content.res.Configuration r9 = r9.getConfiguration()
            int r9 = r9.densityDpi
            float r9 = (float) r9
            float r2 = r2 * r9
            float r2 = r2 * r13
            r9 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r9
            int r2 = (int) r2
            java.lang.String r14 = "getIcon"
            android.os.Trace.traceBegin(r11, r14)
            com.android.launcher3.icons.IconProvider r14 = r0.mIconProvider
            android.graphics.drawable.Drawable r14 = r14.getIcon(r8, r2)
            android.os.Trace.traceEnd(r11)
            if (r14 != 0) goto L_0x0129
            android.content.pm.PackageManager r14 = r25.getPackageManager()
            android.graphics.drawable.Drawable r14 = r14.getDefaultActivityIcon()
        L_0x0129:
            boolean r15 = r14 instanceof android.graphics.drawable.AdaptiveIconDrawable
            r16 = 1065353216(0x3f800000, float:1.0)
            r13 = 2
            if (r15 != 0) goto L_0x0135
            r3 = r4
            r7 = r3
            r4 = r11
            goto L_0x02d2
        L_0x0135:
            java.lang.String r15 = "processAdaptiveIcon"
            android.os.Trace.traceBegin(r11, r15)
            r15 = r14
            android.graphics.drawable.AdaptiveIconDrawable r15 = (android.graphics.drawable.AdaptiveIconDrawable) r15
            android.graphics.drawable.Drawable r11 = r15.getForeground()
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache r12 = r0.mColorCache
            java.lang.String r9 = r8.packageName
            int r8 = r8.getIconResource()
            int r5 = r0.mLastPackageContextConfigHash
            java.util.Objects.requireNonNull(r12)
            android.util.ArrayMap<java.lang.String, com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors> r3 = r12.mColorMap
            java.lang.Object r3 = r3.get(r9)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors r3 = (com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.Colors) r3
            int r8 = r8 * 31
            int r5 = r5 + r8
            int[] r8 = new int[r7]
            r8[r4] = r4
            if (r3 == 0) goto L_0x016b
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$IconColor[] r9 = r3.mIconColors
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Cache r9 = com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.getCache(r9, r5, r8)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$IconColor r9 = (com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.IconColor) r9
            if (r9 == 0) goto L_0x0175
            goto L_0x0220
        L_0x016b:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors r3 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors
            r3.<init>()
            android.util.ArrayMap<java.lang.String, com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors> r12 = r12.mColorMap
            r12.put(r9, r3)
        L_0x0175:
            boolean r9 = r11 instanceof android.graphics.drawable.LayerDrawable
            r12 = 428468608(0x1989e980, float:1.4259778E-23)
            if (r9 == 0) goto L_0x0193
            r9 = r11
            android.graphics.drawable.LayerDrawable r9 = (android.graphics.drawable.LayerDrawable) r9
            int r17 = r9.getNumberOfLayers()
            if (r17 <= 0) goto L_0x0193
            boolean r17 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r17 == 0) goto L_0x018e
            com.android.wm.shell.protolog.ShellProtoLogGroup r7 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r7, r12, r4, r6, r6)
        L_0x018e:
            android.graphics.drawable.Drawable r7 = r9.getDrawable(r4)
            goto L_0x0194
        L_0x0193:
            r7 = r11
        L_0x0194:
            if (r7 != 0) goto L_0x01a5
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester r7 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester
            android.graphics.drawable.ColorDrawable r9 = new android.graphics.drawable.ColorDrawable
            int r4 = getSystemBGColor()
            r9.<init>(r4)
            r7.<init>(r9)
            goto L_0x01b7
        L_0x01a5:
            boolean r4 = r7 instanceof android.graphics.drawable.ColorDrawable
            if (r4 == 0) goto L_0x01b1
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester r4 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester
            android.graphics.drawable.ColorDrawable r7 = (android.graphics.drawable.ColorDrawable) r7
            r4.<init>(r7)
            goto L_0x01b6
        L_0x01b1:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester r4 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester
            r4.<init>(r7, r13)
        L_0x01b6:
            r7 = r4
        L_0x01b7:
            android.graphics.drawable.Drawable r4 = r15.getBackground()
            boolean r9 = r4 instanceof android.graphics.drawable.LayerDrawable
            if (r9 == 0) goto L_0x01d8
            r9 = r4
            android.graphics.drawable.LayerDrawable r9 = (android.graphics.drawable.LayerDrawable) r9
            int r15 = r9.getNumberOfLayers()
            if (r15 <= 0) goto L_0x01d8
            boolean r4 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r4 == 0) goto L_0x01d3
            com.android.wm.shell.protolog.ShellProtoLogGroup r4 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r15 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r4, r12, r15, r6, r6)
            goto L_0x01d4
        L_0x01d3:
            r15 = 0
        L_0x01d4:
            android.graphics.drawable.Drawable r4 = r9.getDrawable(r15)
        L_0x01d8:
            if (r4 != 0) goto L_0x01e9
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester r4 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester
            android.graphics.drawable.ColorDrawable r9 = new android.graphics.drawable.ColorDrawable
            int r12 = getSystemBGColor()
            r9.<init>(r12)
            r4.<init>(r9)
            goto L_0x01fc
        L_0x01e9:
            boolean r9 = r4 instanceof android.graphics.drawable.ColorDrawable
            if (r9 == 0) goto L_0x01f5
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester r9 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester
            android.graphics.drawable.ColorDrawable r4 = (android.graphics.drawable.ColorDrawable) r4
            r9.<init>(r4)
            goto L_0x01fb
        L_0x01f5:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester r9 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester
            r12 = 0
            r9.<init>(r4, r12)
        L_0x01fb:
            r4 = r9
        L_0x01fc:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$IconColor r9 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$IconColor
            int r19 = r7.getDominantColor()
            int r20 = r4.getDominantColor()
            boolean r21 = r4.isComplexColor()
            boolean r22 = r4.isGrayscale()
            float r23 = r7.passFilterRatio()
            r17 = r9
            r18 = r5
            r17.<init>(r18, r19, r20, r21, r22, r23)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$IconColor[] r3 = r3.mIconColors
            r4 = 0
            r5 = r8[r4]
            r3[r5] = r9
        L_0x0220:
            boolean r3 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r3 == 0) goto L_0x026f
            int r3 = r9.mFgColor
            java.lang.String r3 = java.lang.Integer.toHexString(r3)
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r4 = r9.mBgColor
            java.lang.String r4 = java.lang.Integer.toHexString(r4)
            java.lang.String r4 = java.lang.String.valueOf(r4)
            boolean r5 = r9.mIsBgComplex
            int r7 = r9.mReuseCount
            if (r7 <= 0) goto L_0x0240
            r7 = 1
            goto L_0x0241
        L_0x0240:
            r7 = 0
        L_0x0241:
            int r8 = r10.mThemeColor
            java.lang.String r8 = java.lang.Integer.toHexString(r8)
            java.lang.String r8 = java.lang.String.valueOf(r8)
            com.android.wm.shell.protolog.ShellProtoLogGroup r12 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r15 = 5
            java.lang.Object[] r15 = new java.lang.Object[r15]
            r17 = 0
            r15[r17] = r3
            r3 = 1
            r15[r3] = r4
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r5)
            r15[r13] = r3
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r7)
            r4 = 3
            r15[r4] = r3
            r3 = 4
            r15[r3] = r8
            r3 = -1141104614(0xffffffffbbfc201a, float:-0.0076942565)
            r4 = 240(0xf0, float:3.36E-43)
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r12, r3, r4, r6, r15)
        L_0x026f:
            boolean r3 = r9.mIsBgComplex
            if (r3 != 0) goto L_0x02bc
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r3 = r0.mTmpAttrs
            int r3 = r3.mIconBgColor
            if (r3 != 0) goto L_0x02bc
            int r3 = r10.mThemeColor
            int r4 = r9.mBgColor
            boolean r3 = m296$$Nest$smisRgbSimilarInHsv(r3, r4)
            if (r3 != 0) goto L_0x0291
            boolean r3 = r9.mIsBgGrayscale
            if (r3 == 0) goto L_0x02bc
            int r3 = r10.mThemeColor
            int r4 = r9.mFgColor
            boolean r3 = m296$$Nest$smisRgbSimilarInHsv(r3, r4)
            if (r3 != 0) goto L_0x02bc
        L_0x0291:
            boolean r3 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r3 == 0) goto L_0x029e
            com.android.wm.shell.protolog.ShellProtoLogGroup r3 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r4 = 1960014443(0x74d3726b, float:1.3402042E32)
            r5 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r3, r4, r5, r6, r6)
        L_0x029e:
            float r3 = r9.mFgNonTranslucentRatio
            r4 = 1055100473(0x3ee38e39, float:0.44444445)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x02ab
            r3 = 1067030938(0x3f99999a, float:1.2)
            goto L_0x02ad
        L_0x02ab:
            r3 = r16
        L_0x02ad:
            int r4 = r0.mIconSize
            float r4 = (float) r4
            float r4 = r4 * r3
            r3 = 1056964608(0x3f000000, float:0.5)
            float r4 = r4 + r3
            int r3 = (int) r4
            r10.mFinalIconSize = r3
            r3 = 0
            r10.createIconDrawable(r11, r3)
            goto L_0x02cc
        L_0x02bc:
            r3 = 0
            boolean r4 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r4 == 0) goto L_0x02c9
            com.android.wm.shell.protolog.ShellProtoLogGroup r4 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r5 = 1288760762(0x4cd0edba, float:1.09538768E8)
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r4, r5, r3, r6, r6)
        L_0x02c9:
            r10.createIconDrawable(r14, r3)
        L_0x02cc:
            r4 = 32
            android.os.Trace.traceEnd(r4)
            r7 = 1
        L_0x02d2:
            if (r7 != 0) goto L_0x0411
            boolean r7 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r7 == 0) goto L_0x02e0
            com.android.wm.shell.protolog.ShellProtoLogGroup r7 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r8 = 888452073(0x34f4b3e9, float:4.5579444E-7)
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r7, r8, r3, r6, r6)
        L_0x02e0:
            java.lang.String r3 = "legacy_icon_factory"
            android.os.Trace.traceBegin(r4, r3)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder$ShapeIconFactory r3 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder$ShapeIconFactory
            android.content.Context r4 = r0.mContext
            int r5 = r10.mFinalIconSize
            r3.<init>(r4, r2, r5)
            android.graphics.RectF r2 = new android.graphics.RectF
            r2.<init>()
            r4 = 1
            float[] r5 = new float[r4]
            android.graphics.drawable.Drawable r4 = r3.normalizeAndWrapToAdaptiveIcon(r14, r2, r5)
            r7 = 0
            r5 = r5[r7]
            float r7 = r2.left
            float r8 = r2.right
            float r7 = java.lang.Math.min(r7, r8)
            float r8 = r2.top
            float r7 = java.lang.Math.min(r7, r8)
            r8 = 1024416809(0x3d0f5c29, float:0.035)
            int r9 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r9 >= 0) goto L_0x031b
            r9 = 1055790203(0x3eee147b, float:0.465)
            r11 = 1056964608(0x3f000000, float:0.5)
            float r7 = r11 - r7
            float r9 = r9 / r7
            goto L_0x031f
        L_0x031b:
            r11 = 1056964608(0x3f000000, float:0.5)
            r9 = r16
        L_0x031f:
            r7 = 1030009214(0x3d64b17e, float:0.055833332)
            float r2 = r2.bottom
            int r7 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x0332
            r7 = 1055091152(0x3ee369d0, float:0.44416666)
            float r2 = r11 - r2
            float r7 = r7 / r2
            float r9 = java.lang.Math.min(r9, r7)
        L_0x0332:
            float r2 = java.lang.Math.min(r5, r9)
            int r5 = r3.mIconBitmapSize
            android.graphics.Bitmap$Config r7 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r7 = android.graphics.Bitmap.createBitmap(r5, r5, r7)
            if (r4 != 0) goto L_0x0342
            goto L_0x0401
        L_0x0342:
            android.graphics.Canvas r9 = r3.mCanvas
            r9.setBitmap(r7)
            android.graphics.Rect r9 = r3.mOldBounds
            android.graphics.Rect r11 = r4.getBounds()
            r9.set(r11)
            boolean r9 = r4 instanceof android.graphics.drawable.AdaptiveIconDrawable
            if (r9 == 0) goto L_0x0396
            float r9 = (float) r5
            float r8 = r8 * r9
            double r11 = (double) r8
            double r11 = java.lang.Math.ceil(r11)
            int r8 = (int) r11
            float r16 = r16 - r2
            float r16 = r16 * r9
            r2 = 1073741824(0x40000000, float:2.0)
            float r16 = r16 / r2
            int r2 = java.lang.Math.round(r16)
            int r2 = java.lang.Math.max(r8, r2)
            int r5 = r5 - r2
            int r5 = r5 - r2
            r8 = 0
            r4.setBounds(r8, r8, r5, r5)
            android.graphics.Canvas r5 = r3.mCanvas
            int r5 = r5.save()
            android.graphics.Canvas r8 = r3.mCanvas
            float r2 = (float) r2
            r8.translate(r2, r2)
            boolean r2 = r4 instanceof com.android.launcher3.icons.BitmapInfo.Extender
            if (r2 == 0) goto L_0x038b
            r2 = r4
            com.android.launcher3.icons.BitmapInfo$Extender r2 = (com.android.launcher3.icons.BitmapInfo.Extender) r2
            android.graphics.Canvas r8 = r3.mCanvas
            r2.drawForPersistence(r8)
            goto L_0x0390
        L_0x038b:
            android.graphics.Canvas r2 = r3.mCanvas
            r4.draw(r2)
        L_0x0390:
            android.graphics.Canvas r2 = r3.mCanvas
            r2.restoreToCount(r5)
            goto L_0x03f7
        L_0x0396:
            boolean r8 = r4 instanceof android.graphics.drawable.BitmapDrawable
            if (r8 == 0) goto L_0x03b6
            r8 = r4
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r9 = r8.getBitmap()
            if (r7 == 0) goto L_0x03b6
            int r9 = r9.getDensity()
            if (r9 != 0) goto L_0x03b6
            android.content.Context r9 = r3.mContext
            android.content.res.Resources r9 = r9.getResources()
            android.util.DisplayMetrics r9 = r9.getDisplayMetrics()
            r8.setTargetDensity(r9)
        L_0x03b6:
            int r8 = r4.getIntrinsicWidth()
            int r9 = r4.getIntrinsicHeight()
            if (r8 <= 0) goto L_0x03d4
            if (r9 <= 0) goto L_0x03d4
            float r11 = (float) r8
            float r12 = (float) r9
            float r11 = r11 / r12
            if (r8 <= r9) goto L_0x03cd
            float r8 = (float) r5
            float r8 = r8 / r11
            int r8 = (int) r8
            r9 = r8
            r8 = r5
            goto L_0x03d6
        L_0x03cd:
            if (r9 <= r8) goto L_0x03d4
            float r8 = (float) r5
            float r8 = r8 * r11
            int r8 = (int) r8
            r9 = r5
            goto L_0x03d6
        L_0x03d4:
            r8 = r5
            r9 = r8
        L_0x03d6:
            int r11 = r5 - r8
            int r11 = r11 / r13
            int r12 = r5 - r9
            int r12 = r12 / r13
            int r8 = r8 + r11
            int r9 = r9 + r12
            r4.setBounds(r11, r12, r8, r9)
            android.graphics.Canvas r8 = r3.mCanvas
            r8.save()
            android.graphics.Canvas r8 = r3.mCanvas
            int r5 = r5 / r13
            float r5 = (float) r5
            r8.scale(r2, r2, r5, r5)
            android.graphics.Canvas r2 = r3.mCanvas
            r4.draw(r2)
            android.graphics.Canvas r2 = r3.mCanvas
            r2.restore()
        L_0x03f7:
            android.graphics.Rect r2 = r3.mOldBounds
            r4.setBounds(r2)
            android.graphics.Canvas r2 = r3.mCanvas
            r2.setBitmap(r6)
        L_0x0401:
            r2 = 32
            android.os.Trace.traceEnd(r2)
            android.graphics.drawable.BitmapDrawable r2 = new android.graphics.drawable.BitmapDrawable
            r2.<init>(r7)
            r3 = 1
            r10.createIconDrawable(r2, r3)
            r2 = 0
            goto L_0x0416
        L_0x0411:
            r2 = r3
            goto L_0x0416
        L_0x0413:
            r2 = r4
            r10.mFinalIconSize = r2
        L_0x0416:
            r3 = r2
        L_0x0417:
            int r4 = r10.mFinalIconSize
            android.graphics.drawable.Drawable[] r5 = r10.mFinalIconDrawables
            java.util.function.Consumer<java.lang.Runnable> r7 = r10.mUiThreadInitTask
            if (r5 == 0) goto L_0x042d
            int r8 = r5.length
            if (r8 <= 0) goto L_0x0425
            r2 = r5[r2]
            goto L_0x0426
        L_0x0425:
            r2 = r6
        L_0x0426:
            int r8 = r5.length
            r9 = 1
            if (r8 <= r9) goto L_0x042e
            r6 = r5[r9]
            goto L_0x042e
        L_0x042d:
            r2 = r6
        L_0x042e:
            java.lang.String r5 = "fillViewWithIcon"
            r8 = 32
            android.os.Trace.traceBegin(r8, r5)
            android.view.ContextThemeWrapper r5 = new android.view.ContextThemeWrapper
            android.content.Context r8 = r0.mContext
            android.content.res.Resources$Theme r8 = r8.getTheme()
            r5.<init>(r1, r8)
            android.window.SplashScreenView$Builder r1 = new android.window.SplashScreenView$Builder
            r1.<init>(r5)
            int r5 = r10.mThemeColor
            android.window.SplashScreenView$Builder r1 = r1.setBackgroundColor(r5)
            android.graphics.drawable.Drawable r5 = r10.mOverlayDrawable
            android.window.SplashScreenView$Builder r1 = r1.setOverlayDrawable(r5)
            android.window.SplashScreenView$Builder r1 = r1.setIconSize(r4)
            android.window.SplashScreenView$Builder r1 = r1.setIconBackground(r6)
            android.window.SplashScreenView$Builder r1 = r1.setCenterViewDrawable(r2)
            android.window.SplashScreenView$Builder r1 = r1.setAnimationDurationMillis(r3)
            android.window.SplashScreenView$Builder r1 = r1.setUiThreadInitConsumer(r7)
            boolean r2 = r10.mAllowHandleEmpty
            android.window.SplashScreenView$Builder r1 = r1.setAllowHandleEmpty(r2)
            int r2 = r10.mSuggestType
            r3 = 1
            if (r2 != r3) goto L_0x047d
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r2 = r0.mTmpAttrs
            android.graphics.drawable.Drawable r2 = r2.mBrandingImage
            if (r2 == 0) goto L_0x047d
            int r3 = r0.mBrandingImageWidth
            int r0 = r0.mBrandingImageHeight
            r1.setBrandingDrawable(r2, r3, r0)
        L_0x047d:
            android.window.SplashScreenView r0 = r1.build()
            int r1 = r10.mSuggestType
            r2 = 4
            if (r1 == r2) goto L_0x048e
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder$1 r1 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$StartingWindowViewBuilder$1
            r1.<init>(r0)
            r0.addOnAttachStateChangeListener(r1)
        L_0x048e:
            r1 = 32
            android.os.Trace.traceEnd(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.makeSplashScreenContentView(android.content.Context, android.window.StartingWindowInfo, int, java.util.function.Consumer):android.window.SplashScreenView");
    }

    public SplashscreenContentDrawer(Context context, IconProvider iconProvider, TransactionPool transactionPool) {
        this.mContext = context;
        this.mIconProvider = iconProvider;
        this.mTransactionPool = transactionPool;
        HandlerThread handlerThread = new HandlerThread("wmshell.splashworker", -10);
        handlerThread.start();
        Handler threadHandler = handlerThread.getThreadHandler();
        this.mSplashscreenWorkerHandler = threadHandler;
        this.mColorCache = new ColorCache(context, threadHandler);
    }

    public static int getSystemBGColor() {
        Application currentApplication = ActivityThread.currentApplication();
        if (currentApplication != null) {
            return currentApplication.getResources().getColor(C1777R.color.splash_window_background_default);
        }
        Slog.e("ShellStartingWindow", "System context does not exist!");
        return -16777216;
    }

    public static Object safeReturnAttrDefault(UnaryOperator unaryOperator, Integer num) {
        try {
            return unaryOperator.apply(num);
        } catch (RuntimeException e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Get attribute fail, return default: ");
            m.append(e.getMessage());
            Slog.w("ShellStartingWindow", m.toString());
            return num;
        }
    }
}
