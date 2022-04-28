package com.android.systemui;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.DisplayUtils;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.RoundedCorners;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.decor.OverlayWindow;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.ViewState;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.settings.SecureSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

public class ScreenDecorations extends CoreStartable implements TunerService.Tunable {
    public static final boolean DEBUG_COLOR;
    public static final boolean DEBUG_DISABLE_SCREEN_DECORATIONS = SystemProperties.getBoolean("debug.disable_screen_decorations", false);
    public static final boolean DEBUG_SCREENSHOT_ROUNDED_CORNERS;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public CameraAvailabilityListener mCameraListener;
    public C06381 mCameraTransitionCallback = new CameraAvailabilityListener.CameraTransitionCallback() {
        public final void onApplyCameraProtection(Path path, Rect rect) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorHwcLayer screenDecorHwcLayer = screenDecorations.mScreenDecorHwcLayer;
            if (screenDecorHwcLayer != null) {
                screenDecorHwcLayer.setProtection(path, rect);
                ScreenDecorations.this.mScreenDecorHwcLayer.enableShowProtection(true);
                return;
            }
            DisplayCutoutView[] displayCutoutViewArr = screenDecorations.mCutoutViews;
            if (displayCutoutViewArr == null) {
                Log.w("ScreenDecorations", "DisplayCutoutView do not initialized");
                return;
            }
            for (DisplayCutoutView displayCutoutView : displayCutoutViewArr) {
                if (displayCutoutView != null) {
                    displayCutoutView.setProtection(path, rect);
                    displayCutoutView.enableShowProtection(true);
                }
            }
        }

        public final void onHideCameraProtection() {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorHwcLayer screenDecorHwcLayer = screenDecorations.mScreenDecorHwcLayer;
            if (screenDecorHwcLayer != null) {
                screenDecorHwcLayer.enableShowProtection(false);
                return;
            }
            DisplayCutoutView[] displayCutoutViewArr = screenDecorations.mCutoutViews;
            if (displayCutoutViewArr == null) {
                Log.w("ScreenDecorations", "DisplayCutoutView do not initialized");
                return;
            }
            for (DisplayCutoutView displayCutoutView : displayCutoutViewArr) {
                if (displayCutoutView != null) {
                    displayCutoutView.enableShowProtection(false);
                }
            }
        }
    };
    public C06414 mColorInversionSetting;
    public DisplayCutoutView[] mCutoutViews;
    public float mDensity;
    public DisplayManager.DisplayListener mDisplayListener;
    public DisplayManager mDisplayManager;
    public String mDisplayUniqueId;
    public final PrivacyDotDecorProviderFactory mDotFactory;
    public final PrivacyDotViewController mDotViewController;
    public DelayableExecutor mExecutor;
    public Handler mHandler;
    public DisplayDecorationSupport mHwcScreenDecorationSupport;
    public boolean mIsRegistered;
    public boolean mIsRoundedCornerMultipleRadius;
    public final Executor mMainExecutor;
    public OverlayWindow[] mOverlays = null;
    public boolean mPendingRotationChange;
    public C06392 mPrivacyDotShowingListener = new PrivacyDotViewController.ShowingListener() {
        public final void onPrivacyDotShown(View view) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (screenDecorations.mHwcScreenDecorationSupport != null && view != null) {
                screenDecorations.mExecutor.execute(new ScreenDecorations$2$$ExternalSyntheticLambda1(this, view, 0));
            }
        }
    };
    public int mRotation;
    public Drawable mRoundedCornerDrawable;
    public Drawable mRoundedCornerDrawableBottom;
    public Drawable mRoundedCornerDrawableTop;
    public Point mRoundedDefault = new Point(0, 0);
    public Point mRoundedDefaultBottom = new Point(0, 0);
    public Point mRoundedDefaultTop = new Point(0, 0);
    public ScreenDecorHwcLayer mScreenDecorHwcLayer;
    public ViewGroup mScreenDecorHwcWindow;
    public final SecureSettings mSecureSettings;
    public final ThreadFactory mThreadFactory;
    public int mTintColor = -16777216;
    public final TunerService mTunerService;
    public final C06436 mUserSwitchIntentReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            ScreenDecorations.this.mColorInversionSetting.setUserId(ActivityManager.getCurrentUser());
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorations.m166$$Nest$mupdateColorInversion(screenDecorations, screenDecorations.mColorInversionSetting.getValue());
        }
    };
    public final UserTracker mUserTracker;
    public WindowManager mWindowManager;

    public static class DisplayCutoutView extends DisplayCutoutBaseView {
        public final Rect mBoundingRect = new Rect();
        public final ArrayList mBounds = new ArrayList();
        public int mInitialPosition;
        public int mPosition;
        public int mRotation;
        public Rect mTotalBounds = new Rect();

        public static void boundsFromDirection(DisplayCutout displayCutout, int i, Rect rect) {
            if (i == 3) {
                rect.set(displayCutout.getBoundingRectLeft());
            } else if (i == 5) {
                rect.set(displayCutout.getBoundingRectRight());
            } else if (i == 48) {
                rect.set(displayCutout.getBoundingRectTop());
            } else if (i != 80) {
                rect.setEmpty();
            } else {
                rect.set(displayCutout.getBoundingRectBottom());
            }
        }

        public final int getGravity(DisplayCutout displayCutout) {
            int i = this.mPosition;
            if (i == 0) {
                if (!displayCutout.getBoundingRectLeft().isEmpty()) {
                    return 3;
                }
                return 0;
            } else if (i == 1) {
                if (!displayCutout.getBoundingRectTop().isEmpty()) {
                    return 48;
                }
                return 0;
            } else if (i == 3) {
                if (!displayCutout.getBoundingRectBottom().isEmpty()) {
                    return 80;
                }
                return 0;
            } else if (i != 2 || displayCutout.getBoundingRectRight().isEmpty()) {
                return 0;
            } else {
                return 5;
            }
        }

        public final void onMeasure(int i, int i2) {
            if (this.mBounds.isEmpty()) {
                super.onMeasure(i, i2);
            } else if (this.showProtection) {
                this.mTotalBounds.union(this.mBoundingRect);
                Rect rect = this.mTotalBounds;
                RectF rectF = this.protectionRect;
                rect.union((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                setMeasuredDimension(View.resolveSizeAndState(this.mTotalBounds.width(), i, 0), View.resolveSizeAndState(this.mTotalBounds.height(), i2, 0));
            } else {
                setMeasuredDimension(View.resolveSizeAndState(this.mBoundingRect.width(), i, 0), View.resolveSizeAndState(this.mBoundingRect.height(), i2, 0));
            }
        }

        public DisplayCutoutView(Context context, int i) {
            super(context);
            this.mInitialPosition = i;
            this.paint.setColor(-16777216);
            this.paint.setStyle(Paint.Style.FILL);
            setId(C1777R.C1779id.display_cutout);
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x007e  */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0104  */
        /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void updateCutout() {
            /*
                r6 = this;
                boolean r0 = r6.isAttachedToWindow()
                if (r0 == 0) goto L_0x0107
                int r0 = r6.mInitialPosition
                int r1 = r6.mRotation
                int r0 = com.android.systemui.ScreenDecorations.getBoundPositionFromRotation(r0, r1)
                r6.mPosition = r0
                r6.requestLayout()
                android.view.Display r0 = r6.getDisplay()
                android.view.DisplayInfo r1 = r6.displayInfo
                r0.getDisplayInfo(r1)
                java.util.ArrayList r0 = r6.mBounds
                r0.clear()
                android.graphics.Rect r0 = r6.mBoundingRect
                r0.setEmpty()
                android.graphics.Path r0 = r6.cutoutPath
                r0.reset()
                android.content.Context r0 = r6.getContext()
                android.content.res.Resources r1 = r0.getResources()
                android.view.Display r0 = r0.getDisplay()
                java.lang.String r0 = r0.getUniqueId()
                boolean r0 = android.view.DisplayCutout.getFillBuiltInDisplayCutout(r1, r0)
                r1 = 0
                if (r0 == 0) goto L_0x00fc
                android.view.DisplayInfo r0 = r6.displayInfo
                android.view.DisplayCutout r0 = r0.displayCutout
                r2 = 3
                r3 = 1
                if (r0 != 0) goto L_0x004b
                goto L_0x007b
            L_0x004b:
                int r4 = r6.mPosition
                if (r4 != 0) goto L_0x0058
                android.graphics.Rect r0 = r0.getBoundingRectLeft()
                boolean r0 = r0.isEmpty()
                goto L_0x0079
            L_0x0058:
                if (r4 != r3) goto L_0x0063
                android.graphics.Rect r0 = r0.getBoundingRectTop()
                boolean r0 = r0.isEmpty()
                goto L_0x0079
            L_0x0063:
                if (r4 != r2) goto L_0x006e
                android.graphics.Rect r0 = r0.getBoundingRectBottom()
                boolean r0 = r0.isEmpty()
                goto L_0x0079
            L_0x006e:
                r5 = 2
                if (r4 != r5) goto L_0x007b
                android.graphics.Rect r0 = r0.getBoundingRectRight()
                boolean r0 = r0.isEmpty()
            L_0x0079:
                r0 = r0 ^ r3
                goto L_0x007c
            L_0x007b:
                r0 = r1
            L_0x007c:
                if (r0 == 0) goto L_0x00fc
                java.util.ArrayList r0 = r6.mBounds
                android.view.DisplayInfo r4 = r6.displayInfo
                android.view.DisplayCutout r4 = r4.displayCutout
                java.util.List r4 = r4.getBoundingRects()
                r0.addAll(r4)
                android.graphics.Rect r0 = r6.mBoundingRect
                android.view.DisplayInfo r4 = r6.displayInfo
                android.view.DisplayCutout r4 = r4.displayCutout
                int r5 = r6.getGravity(r4)
                boundsFromDirection(r4, r5, r0)
                android.view.ViewGroup$LayoutParams r0 = r6.getLayoutParams()
                boolean r4 = r0 instanceof android.widget.FrameLayout.LayoutParams
                if (r4 == 0) goto L_0x00b3
                android.widget.FrameLayout$LayoutParams r0 = (android.widget.FrameLayout.LayoutParams) r0
                android.view.DisplayInfo r4 = r6.displayInfo
                android.view.DisplayCutout r4 = r4.displayCutout
                int r4 = r6.getGravity(r4)
                int r5 = r0.gravity
                if (r5 == r4) goto L_0x00b3
                r0.gravity = r4
                r6.setLayoutParams(r0)
            L_0x00b3:
                android.view.DisplayInfo r0 = r6.displayInfo
                int r4 = r0.logicalWidth
                int r5 = r0.logicalHeight
                int r0 = r0.rotation
                if (r0 == r3) goto L_0x00c1
                if (r0 != r2) goto L_0x00c0
                goto L_0x00c1
            L_0x00c0:
                r3 = r1
            L_0x00c1:
                if (r3 == 0) goto L_0x00c5
                r0 = r5
                goto L_0x00c6
            L_0x00c5:
                r0 = r4
            L_0x00c6:
                if (r3 == 0) goto L_0x00c9
                goto L_0x00ca
            L_0x00c9:
                r4 = r5
            L_0x00ca:
                android.content.res.Resources r2 = r6.getResources()
                android.view.Display r3 = r6.getDisplay()
                java.lang.String r3 = r3.getUniqueId()
                android.graphics.Path r2 = android.view.DisplayCutout.pathFromResources(r2, r3, r0, r4)
                if (r2 == 0) goto L_0x00e2
                android.graphics.Path r3 = r6.cutoutPath
                r3.set(r2)
                goto L_0x00e7
            L_0x00e2:
                android.graphics.Path r2 = r6.cutoutPath
                r2.reset()
            L_0x00e7:
                android.graphics.Matrix r2 = new android.graphics.Matrix
                r2.<init>()
                android.view.DisplayInfo r3 = r6.displayInfo
                int r3 = r3.rotation
                com.android.systemui.DisplayCutoutBaseView.transformPhysicalToLogicalCoordinates(r3, r0, r4, r2)
                android.graphics.Path r0 = r6.cutoutPath
                r0.transform(r2)
                r6.invalidate()
                goto L_0x00fe
            L_0x00fc:
                r1 = 8
            L_0x00fe:
                int r0 = r6.getVisibility()
                if (r1 == r0) goto L_0x0107
                r6.setVisibility(r1)
            L_0x0107:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorations.DisplayCutoutView.updateCutout():void");
        }
    }

    public class RestartingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final int mTargetRotation;
        public final View mView;

        public RestartingPreDrawListener(ViewGroup viewGroup, int i) {
            this.mView = viewGroup;
            this.mTargetRotation = i;
        }

        public final boolean onPreDraw() {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            int i = this.mTargetRotation;
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (i == screenDecorations.mRotation) {
                return true;
            }
            screenDecorations.mPendingRotationChange = false;
            screenDecorations.updateOrientation();
            this.mView.invalidate();
            return false;
        }
    }

    public class ValidatingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final View mView;

        public ValidatingPreDrawListener(View view) {
            this.mView = view;
        }

        public final boolean onPreDraw() {
            int rotation = ScreenDecorations.this.mContext.getDisplay().getRotation();
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (rotation == screenDecorations.mRotation || screenDecorations.mPendingRotationChange) {
                return true;
            }
            this.mView.invalidate();
            return false;
        }
    }

    public static int getBoundPositionFromRotation(int i, int i2) {
        int i3 = i - i2;
        return i3 < 0 ? i3 + 4 : i3;
    }

    public final boolean isDefaultShownOverlayPos(int i, DisplayCutout displayCutout) {
        boolean z;
        if (displayCutout == null || displayCutout.isBoundsEmpty()) {
            z = true;
        } else {
            z = false;
        }
        return (z || !displayCutout.getBoundingRectsAll()[getBoundPositionFromRotation(1, this.mRotation)].isEmpty() || !displayCutout.getBoundingRectsAll()[getBoundPositionFromRotation(3, this.mRotation)].isEmpty()) ? i == 1 || i == 3 : i == 0 || i == 2;
    }

    public boolean isTopRoundedCorner(int i, int i2) {
        if (i != 0) {
            if (i == 1) {
                return true;
            }
            if (i != 2) {
                if (i == 3) {
                    return false;
                }
                throw new IllegalArgumentException("Unknown bounds position");
            }
        }
        if (this.mRotation == 3) {
            if (i2 == C1777R.C1779id.left) {
                return false;
            }
            return true;
        } else if (i2 == C1777R.C1779id.left) {
            return true;
        } else {
            return false;
        }
    }

    static {
        boolean z = SystemProperties.getBoolean("debug.screenshot_rounded_corners", false);
        DEBUG_SCREENSHOT_ROUNDED_CORNERS = z;
        DEBUG_COLOR = z;
    }

    public static WindowManager.LayoutParams getWindowLayoutBaseParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 545259816, -3);
        int i = layoutParams.privateFlags | 80 | 536870912;
        layoutParams.privateFlags = i;
        if (!DEBUG_SCREENSHOT_ROUNDED_CORNERS) {
            layoutParams.privateFlags = i | 1048576;
        }
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= 16777216;
        return layoutParams;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "ScreenDecorations state:", "  DEBUG_DISABLE_SCREEN_DECORATIONS:"), DEBUG_DISABLE_SCREEN_DECORATIONS, printWriter, "  mIsRoundedCornerMultipleRadius:"), this.mIsRoundedCornerMultipleRadius, printWriter, "  mIsPrivacyDotEnabled:");
        PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory = this.mDotFactory;
        Objects.requireNonNull(privacyDotDecorProviderFactory);
        m.append(privacyDotDecorProviderFactory.res.getBoolean(C1777R.bool.config_enablePrivacyDot));
        printWriter.println(m.toString());
        printWriter.println("  mPendingRotationChange:" + this.mPendingRotationChange);
        printWriter.println("  mHwcScreenDecorationSupport:");
        boolean z4 = true;
        if (this.mHwcScreenDecorationSupport == null) {
            printWriter.println("    null");
        } else {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    format: ");
            m2.append(PixelFormat.formatToString(this.mHwcScreenDecorationSupport.format));
            printWriter.println(m2.toString());
            StringBuilder sb = new StringBuilder();
            sb.append("    alphaInterpretation: ");
            int i = this.mHwcScreenDecorationSupport.alphaInterpretation;
            if (i == 0) {
                str = "COVERAGE";
            } else if (i != 1) {
                str = VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unknown: ", i);
            } else {
                str = "MASK";
            }
            sb.append(str);
            printWriter.println(sb.toString());
        }
        StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  mRoundedDefault(x,y)=(");
        m3.append(this.mRoundedDefault.x);
        m3.append(",");
        m3.append(this.mRoundedDefault.y);
        m3.append(")");
        printWriter.println(m3.toString());
        printWriter.println("  mRoundedDefaultTop(x,y)=(" + this.mRoundedDefaultTop.x + "," + this.mRoundedDefaultTop.y + ")");
        printWriter.println("  mRoundedDefaultBottom(x,y)=(" + this.mRoundedDefaultBottom.x + "," + this.mRoundedDefaultBottom.y + ")");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  mOverlays(left,top,right,bottom)=(");
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null || overlayWindowArr[0] == null) {
            z = false;
        } else {
            z = true;
        }
        sb2.append(z);
        sb2.append(",");
        OverlayWindow[] overlayWindowArr2 = this.mOverlays;
        if (overlayWindowArr2 == null || overlayWindowArr2[1] == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        sb2.append(z2);
        sb2.append(",");
        OverlayWindow[] overlayWindowArr3 = this.mOverlays;
        if (overlayWindowArr3 == null || overlayWindowArr3[2] == null) {
            z3 = false;
        } else {
            z3 = true;
        }
        sb2.append(z3);
        sb2.append(",");
        OverlayWindow[] overlayWindowArr4 = this.mOverlays;
        if (overlayWindowArr4 == null || overlayWindowArr4[3] == null) {
            z4 = false;
        }
        sb2.append(z4);
        sb2.append(")");
        printWriter.println(sb2.toString());
    }

    public DisplayCutout getCutout() {
        return this.mContext.getDisplay().getCutout();
    }

    public final View getOverlayView(int i) {
        View view;
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null) {
            return null;
        }
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null) {
                Pair pair = (Pair) overlayWindow.viewProviderMap.get(Integer.valueOf(i));
                if (pair == null) {
                    view = null;
                } else {
                    view = (View) pair.getFirst();
                }
                if (view != null) {
                    return view;
                }
            }
        }
        return null;
    }

    public boolean hasOverlays() {
        if (this.mOverlays == null) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (this.mOverlays[i] != null) {
                return true;
            }
        }
        this.mOverlays = null;
        return false;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda2(this, 0));
        }
    }

    public final void onTuningChanged(String str, String str2) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda0(this, str, str2, 0));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:143:0x02fb, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r7, r3) != false) goto L_0x036b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0371  */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x039a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x039b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setupDecorations() {
        /*
            r33 = this;
            r0 = r33
            com.android.systemui.decor.PrivacyDotDecorProviderFactory r1 = r0.mDotFactory
            java.util.Objects.requireNonNull(r1)
            android.content.res.Resources r1 = r1.res
            r2 = 2131034133(0x7f050015, float:1.7678775E38)
            boolean r1 = r1.getBoolean(r2)
            r3 = 2131428621(0x7f0b050d, float:1.8478892E38)
            r4 = 3
            r5 = 2131428620(0x7f0b050c, float:1.847889E38)
            r6 = 2131428623(0x7f0b050f, float:1.8478896E38)
            r7 = 2131428622(0x7f0b050e, float:1.8478894E38)
            r8 = 4
            r9 = 2
            r10 = 0
            r11 = 1
            if (r1 == 0) goto L_0x0052
            com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl[] r1 = new com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl[r8]
            com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl r12 = new com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl
            r13 = 2131624416(0x7f0e01e0, float:1.8876011E38)
            r12.<init>(r7, r11, r10, r13)
            r1[r10] = r12
            com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl r12 = new com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl
            r13 = 2131624417(0x7f0e01e1, float:1.8876013E38)
            r12.<init>(r6, r11, r9, r13)
            r1[r11] = r12
            com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl r12 = new com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl
            r13 = 2131624414(0x7f0e01de, float:1.8876007E38)
            r12.<init>(r5, r4, r10, r13)
            r1[r9] = r12
            com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl r12 = new com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl
            r13 = 2131624415(0x7f0e01df, float:1.887601E38)
            r12.<init>(r3, r4, r9, r13)
            r1[r4] = r12
            java.util.List r1 = kotlin.collections.SetsKt__SetsKt.listOf(r1)
            goto L_0x0054
        L_0x0052:
            kotlin.collections.EmptyList r1 = kotlin.collections.EmptyList.INSTANCE
        L_0x0054:
            android.graphics.Point r12 = r0.mRoundedDefault
            int r12 = r12.x
            if (r12 > 0) goto L_0x006d
            android.graphics.Point r12 = r0.mRoundedDefaultBottom
            int r12 = r12.x
            if (r12 > 0) goto L_0x006d
            android.graphics.Point r12 = r0.mRoundedDefaultTop
            int r12 = r12.x
            if (r12 > 0) goto L_0x006d
            boolean r12 = r0.mIsRoundedCornerMultipleRadius
            if (r12 == 0) goto L_0x006b
            goto L_0x006d
        L_0x006b:
            r12 = r10
            goto L_0x006e
        L_0x006d:
            r12 = r11
        L_0x006e:
            r13 = 0
            if (r12 != 0) goto L_0x00c5
            android.content.Context r12 = r0.mContext
            android.content.res.Resources r14 = r12.getResources()
            android.view.Display r12 = r12.getDisplay()
            java.lang.String r12 = r12.getUniqueId()
            boolean r12 = android.view.DisplayCutout.getFillBuiltInDisplayCutout(r14, r12)
            if (r12 != 0) goto L_0x00c5
            boolean r12 = r1.isEmpty()
            if (r12 != 0) goto L_0x008c
            goto L_0x00c5
        L_0x008c:
            com.android.systemui.decor.OverlayWindow[] r1 = r0.mOverlays
            if (r1 != 0) goto L_0x0091
            goto L_0x00b4
        L_0x0091:
            r1 = r10
        L_0x0092:
            if (r1 >= r8) goto L_0x00b2
            com.android.systemui.decor.OverlayWindow[] r2 = r0.mOverlays
            r3 = r2[r1]
            if (r3 == 0) goto L_0x00af
            r3 = r2[r1]
            if (r3 != 0) goto L_0x009f
            goto L_0x00af
        L_0x009f:
            android.view.WindowManager r3 = r0.mWindowManager
            r2 = r2[r1]
            java.util.Objects.requireNonNull(r2)
            android.view.ViewGroup r2 = r2.rootView
            r3.removeViewImmediate(r2)
            com.android.systemui.decor.OverlayWindow[] r2 = r0.mOverlays
            r2[r1] = r13
        L_0x00af:
            int r1 = r1 + 1
            goto L_0x0092
        L_0x00b2:
            r0.mOverlays = r13
        L_0x00b4:
            android.view.ViewGroup r1 = r0.mScreenDecorHwcWindow
            if (r1 != 0) goto L_0x00ba
            goto L_0x036b
        L_0x00ba:
            android.view.WindowManager r2 = r0.mWindowManager
            r2.removeViewImmediate(r1)
            r0.mScreenDecorHwcWindow = r13
            r0.mScreenDecorHwcLayer = r13
            goto L_0x036b
        L_0x00c5:
            android.hardware.graphics.common.DisplayDecorationSupport r12 = r0.mHwcScreenDecorationSupport
            r14 = -1
            if (r12 == 0) goto L_0x0132
            android.view.ViewGroup r12 = r0.mScreenDecorHwcWindow
            if (r12 == 0) goto L_0x00cf
            goto L_0x0140
        L_0x00cf:
            android.content.Context r12 = r0.mContext
            android.view.LayoutInflater r12 = android.view.LayoutInflater.from(r12)
            r15 = 2131624452(0x7f0e0204, float:1.8876084E38)
            android.view.View r12 = r12.inflate(r15, r13)
            android.view.ViewGroup r12 = (android.view.ViewGroup) r12
            r0.mScreenDecorHwcWindow = r12
            com.android.systemui.ScreenDecorHwcLayer r12 = new com.android.systemui.ScreenDecorHwcLayer
            android.content.Context r15 = r0.mContext
            android.hardware.graphics.common.DisplayDecorationSupport r9 = r0.mHwcScreenDecorationSupport
            r12.<init>(r15, r9)
            r0.mScreenDecorHwcLayer = r12
            android.view.ViewGroup r9 = r0.mScreenDecorHwcWindow
            android.widget.FrameLayout$LayoutParams r15 = new android.widget.FrameLayout$LayoutParams
            r11 = 8388659(0x800033, float:1.1755015E-38)
            r15.<init>(r14, r14, r11)
            r9.addView(r12, r15)
            android.view.WindowManager r9 = r0.mWindowManager
            android.view.ViewGroup r12 = r0.mScreenDecorHwcWindow
            android.view.WindowManager$LayoutParams r15 = getWindowLayoutBaseParams()
            r15.width = r14
            r15.height = r14
            java.lang.String r14 = "ScreenDecorHwcOverlay"
            r15.setTitle(r14)
            r15.gravity = r11
            boolean r11 = DEBUG_COLOR
            if (r11 != 0) goto L_0x0112
            r15.setColorMode(r8)
        L_0x0112:
            r9.addView(r12, r15)
            android.graphics.Point r9 = r0.mRoundedDefault
            android.graphics.Point r11 = r0.mRoundedDefaultTop
            android.graphics.Point r12 = r0.mRoundedDefaultBottom
            r0.updateRoundedCornerSize(r9, r11, r12)
            r33.updateRoundedCornerImageView()
            android.view.ViewGroup r9 = r0.mScreenDecorHwcWindow
            android.view.ViewTreeObserver r9 = r9.getViewTreeObserver()
            com.android.systemui.ScreenDecorations$ValidatingPreDrawListener r11 = new com.android.systemui.ScreenDecorations$ValidatingPreDrawListener
            android.view.ViewGroup r12 = r0.mScreenDecorHwcWindow
            r11.<init>(r12)
            r9.addOnPreDrawListener(r11)
            goto L_0x0140
        L_0x0132:
            android.view.ViewGroup r9 = r0.mScreenDecorHwcWindow
            if (r9 != 0) goto L_0x0137
            goto L_0x0140
        L_0x0137:
            android.view.WindowManager r11 = r0.mWindowManager
            r11.removeViewImmediate(r9)
            r0.mScreenDecorHwcWindow = r13
            r0.mScreenDecorHwcLayer = r13
        L_0x0140:
            android.view.DisplayCutout r9 = r33.getCutout()
            r11 = r10
        L_0x0145:
            if (r11 >= r8) goto L_0x02a9
            if (r9 != 0) goto L_0x014b
            r12 = r13
            goto L_0x014f
        L_0x014b:
            android.graphics.Rect[] r12 = r9.getBoundingRectsAll()
        L_0x014f:
            int r14 = r0.mRotation
            int r14 = getBoundPositionFromRotation(r11, r14)
            if (r12 == 0) goto L_0x0165
            r12 = r12[r14]
            boolean r12 = r12.isEmpty()
            if (r12 != 0) goto L_0x0165
            android.hardware.graphics.common.DisplayDecorationSupport r12 = r0.mHwcScreenDecorationSupport
            if (r12 != 0) goto L_0x0165
            r12 = 1
            goto L_0x0166
        L_0x0165:
            r12 = r10
        L_0x0166:
            if (r12 != 0) goto L_0x01c8
            android.graphics.Point r12 = r0.mRoundedDefault
            int r12 = r12.x
            if (r12 > 0) goto L_0x0181
            android.graphics.Point r12 = r0.mRoundedDefaultBottom
            int r12 = r12.x
            if (r12 > 0) goto L_0x0181
            android.graphics.Point r12 = r0.mRoundedDefaultTop
            int r12 = r12.x
            if (r12 > 0) goto L_0x0181
            boolean r12 = r0.mIsRoundedCornerMultipleRadius
            if (r12 == 0) goto L_0x017f
            goto L_0x0181
        L_0x017f:
            r12 = r10
            goto L_0x0182
        L_0x0181:
            r12 = 1
        L_0x0182:
            if (r12 == 0) goto L_0x0190
            boolean r12 = r0.isDefaultShownOverlayPos(r11, r9)
            if (r12 == 0) goto L_0x0190
            android.hardware.graphics.common.DisplayDecorationSupport r12 = r0.mHwcScreenDecorationSupport
            if (r12 != 0) goto L_0x0190
            r12 = 1
            goto L_0x0191
        L_0x0190:
            r12 = r10
        L_0x0191:
            if (r12 != 0) goto L_0x01c8
            com.android.systemui.decor.PrivacyDotDecorProviderFactory r12 = r0.mDotFactory
            java.util.Objects.requireNonNull(r12)
            android.content.res.Resources r12 = r12.res
            boolean r12 = r12.getBoolean(r2)
            if (r12 == 0) goto L_0x01a8
            boolean r12 = r0.isDefaultShownOverlayPos(r11, r9)
            if (r12 == 0) goto L_0x01a8
            r12 = 1
            goto L_0x01a9
        L_0x01a8:
            r12 = r10
        L_0x01a9:
            if (r12 == 0) goto L_0x01ac
            goto L_0x01c8
        L_0x01ac:
            com.android.systemui.decor.OverlayWindow[] r12 = r0.mOverlays
            if (r12 == 0) goto L_0x02a1
            r14 = r12[r11]
            if (r14 != 0) goto L_0x01b6
            goto L_0x02a1
        L_0x01b6:
            android.view.WindowManager r14 = r0.mWindowManager
            r12 = r12[r11]
            java.util.Objects.requireNonNull(r12)
            android.view.ViewGroup r12 = r12.rootView
            r14.removeViewImmediate(r12)
            com.android.systemui.decor.OverlayWindow[] r12 = r0.mOverlays
            r12[r11] = r13
            goto L_0x02a1
        L_0x01c8:
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x01d6:
            boolean r15 = r1.hasNext()
            if (r15 == 0) goto L_0x01fe
            java.lang.Object r15 = r1.next()
            r16 = r15
            com.android.systemui.decor.DecorProvider r16 = (com.android.systemui.decor.DecorProvider) r16
            java.util.List r2 = r16.getAlignedBounds()
            java.lang.Integer r13 = java.lang.Integer.valueOf(r11)
            boolean r2 = r2.contains(r13)
            if (r2 == 0) goto L_0x01f6
            r12.add(r15)
            goto L_0x01f9
        L_0x01f6:
            r14.add(r15)
        L_0x01f9:
            r2 = 2131034133(0x7f050015, float:1.7678775E38)
            r13 = 0
            goto L_0x01d6
        L_0x01fe:
            kotlin.Pair r1 = new kotlin.Pair
            r1.<init>(r12, r14)
            java.lang.Object r2 = r1.getSecond()
            java.util.List r2 = (java.util.List) r2
            java.lang.Object r1 = r1.getFirst()
            java.util.List r1 = (java.util.List) r1
            com.android.systemui.decor.OverlayWindow[] r12 = r0.mOverlays
            if (r12 != 0) goto L_0x0217
            com.android.systemui.decor.OverlayWindow[] r12 = new com.android.systemui.decor.OverlayWindow[r8]
            r0.mOverlays = r12
        L_0x0217:
            com.android.systemui.decor.OverlayWindow[] r12 = r0.mOverlays
            r13 = r12[r11]
            if (r13 == 0) goto L_0x021f
            goto L_0x02a0
        L_0x021f:
            com.android.systemui.decor.OverlayWindow r13 = new com.android.systemui.decor.OverlayWindow
            android.content.Context r14 = r0.mContext
            android.view.LayoutInflater r14 = android.view.LayoutInflater.from(r14)
            r13.<init>(r14, r11)
            com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5 r14 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5
            r14.<init>(r0, r13, r10)
            r1.forEach(r14)
            r12[r11] = r13
            com.android.systemui.decor.OverlayWindow[] r1 = r0.mOverlays
            r1 = r1[r11]
            java.util.Objects.requireNonNull(r1)
            android.view.ViewGroup r1 = r1.rootView
            r12 = 256(0x100, float:3.59E-43)
            r1.setSystemUiVisibility(r12)
            r12 = 0
            r1.setAlpha(r12)
            r1.setForceDarkAllowed(r10)
            android.hardware.graphics.common.DisplayDecorationSupport r12 = r0.mHwcScreenDecorationSupport
            if (r12 != 0) goto L_0x027b
            com.android.systemui.ScreenDecorations$DisplayCutoutView[] r12 = r0.mCutoutViews
            if (r12 != 0) goto L_0x0255
            com.android.systemui.ScreenDecorations$DisplayCutoutView[] r12 = new com.android.systemui.ScreenDecorations.DisplayCutoutView[r8]
            r0.mCutoutViews = r12
        L_0x0255:
            com.android.systemui.ScreenDecorations$DisplayCutoutView[] r12 = r0.mCutoutViews
            com.android.systemui.ScreenDecorations$DisplayCutoutView r13 = new com.android.systemui.ScreenDecorations$DisplayCutoutView
            android.content.Context r14 = r0.mContext
            r13.<init>(r14, r11)
            r12[r11] = r13
            com.android.systemui.ScreenDecorations$DisplayCutoutView[] r12 = r0.mCutoutViews
            r12 = r12[r11]
            int r13 = r0.mTintColor
            java.util.Objects.requireNonNull(r12)
            android.graphics.Paint r14 = r12.paint
            r14.setColor(r13)
            r12.invalidate()
            com.android.systemui.ScreenDecorations$DisplayCutoutView[] r12 = r0.mCutoutViews
            r12 = r12[r11]
            r1.addView(r12)
            r0.updateView(r11, r9)
        L_0x027b:
            android.view.WindowManager r12 = r0.mWindowManager
            android.view.WindowManager$LayoutParams r13 = r0.getWindowLayoutParams(r11)
            r12.addView(r1, r13)
            com.android.systemui.ScreenDecorations$5 r12 = new com.android.systemui.ScreenDecorations$5
            r12.<init>(r1)
            r1.addOnLayoutChangeListener(r12)
            android.view.View r12 = r1.getRootView()
            android.view.ViewTreeObserver r12 = r12.getViewTreeObserver()
            com.android.systemui.ScreenDecorations$ValidatingPreDrawListener r13 = new com.android.systemui.ScreenDecorations$ValidatingPreDrawListener
            android.view.View r1 = r1.getRootView()
            r13.<init>(r1)
            r12.addOnPreDrawListener(r13)
        L_0x02a0:
            r1 = r2
        L_0x02a1:
            int r11 = r11 + 1
            r2 = 2131034133(0x7f050015, float:1.7678775E38)
            r13 = 0
            goto L_0x0145
        L_0x02a9:
            android.view.View r1 = r0.getOverlayView(r7)
            if (r1 == 0) goto L_0x036b
            android.view.View r2 = r0.getOverlayView(r6)
            if (r2 == 0) goto L_0x036b
            android.view.View r5 = r0.getOverlayView(r5)
            if (r5 == 0) goto L_0x036b
            android.view.View r3 = r0.getOverlayView(r3)
            if (r3 == 0) goto L_0x036b
            com.android.systemui.statusbar.events.PrivacyDotViewController r6 = r0.mDotViewController
            java.util.Objects.requireNonNull(r6)
            android.view.View r7 = r6.f78tl
            if (r7 == 0) goto L_0x02fe
            android.view.View r8 = r6.f79tr
            if (r8 == 0) goto L_0x02fe
            android.view.View r8 = r6.f76bl
            if (r8 == 0) goto L_0x02fe
            android.view.View r8 = r6.f77br
            if (r8 == 0) goto L_0x02fe
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r1)
            if (r7 == 0) goto L_0x02fe
            android.view.View r7 = r6.f79tr
            if (r7 != 0) goto L_0x02e1
            r7 = 0
        L_0x02e1:
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r2)
            if (r7 == 0) goto L_0x02fe
            android.view.View r7 = r6.f76bl
            if (r7 != 0) goto L_0x02ec
            r7 = 0
        L_0x02ec:
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r5)
            if (r7 == 0) goto L_0x02fe
            android.view.View r7 = r6.f77br
            if (r7 != 0) goto L_0x02f7
            r7 = 0
        L_0x02f7:
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r3)
            if (r7 == 0) goto L_0x02fe
            goto L_0x036b
        L_0x02fe:
            r6.f78tl = r1
            r6.f79tr = r2
            r6.f76bl = r5
            r6.f77br = r3
            com.android.systemui.statusbar.policy.ConfigurationController r1 = r6.configurationController
            boolean r1 = r1.isLayoutRtl()
            android.view.View r2 = r6.selectDesignatedCorner(r10, r1)
            if (r2 == 0) goto L_0x0319
            int r14 = r6.cornerForView(r2)
            r29 = r14
            goto L_0x031b
        L_0x0319:
            r29 = -1
        L_0x031b:
            java.util.concurrent.Executor r3 = r6.mainExecutor
            com.android.systemui.statusbar.events.PrivacyDotViewController$initialize$5 r5 = new com.android.systemui.statusbar.events.PrivacyDotViewController$initialize$5
            r5.<init>(r6)
            r3.execute(r5)
            com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider r3 = r6.contentInsetsProvider
            android.graphics.Rect r25 = r3.getStatusBarContentAreaForRotation(r4)
            com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider r3 = r6.contentInsetsProvider
            android.graphics.Rect r22 = r3.getStatusBarContentAreaForRotation(r10)
            com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider r3 = r6.contentInsetsProvider
            r4 = 1
            android.graphics.Rect r23 = r3.getStatusBarContentAreaForRotation(r4)
            com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider r3 = r6.contentInsetsProvider
            r4 = 2
            android.graphics.Rect r24 = r3.getStatusBarContentAreaForRotation(r4)
            com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider r3 = r6.contentInsetsProvider
            r4 = 0
            int r28 = r3.getStatusBarPaddingTop(r4)
            java.lang.Object r3 = r6.lock
            monitor-enter(r3)
            com.android.systemui.statusbar.events.ViewState r4 = r6.nextViewState     // Catch:{ all -> 0x0368 }
            r18 = 1
            r19 = 0
            r20 = 0
            r21 = 0
            r27 = 0
            r31 = 0
            r32 = 8718(0x220e, float:1.2217E-41)
            r17 = r4
            r26 = r1
            r30 = r2
            com.android.systemui.statusbar.events.ViewState r1 = com.android.systemui.statusbar.events.ViewState.copy$default(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)     // Catch:{ all -> 0x0368 }
            r6.setNextViewState(r1)     // Catch:{ all -> 0x0368 }
            monitor-exit(r3)
            goto L_0x036b
        L_0x0368:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x036b:
            boolean r1 = r33.hasOverlays()
            if (r1 != 0) goto L_0x0396
            android.view.ViewGroup r1 = r0.mScreenDecorHwcWindow
            if (r1 == 0) goto L_0x0377
            r1 = 1
            goto L_0x0378
        L_0x0377:
            r1 = r10
        L_0x0378:
            if (r1 == 0) goto L_0x037b
            goto L_0x0396
        L_0x037b:
            java.util.concurrent.Executor r1 = r0.mMainExecutor
            com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3 r2 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3
            r2.<init>(r0, r10)
            r1.execute(r2)
            com.android.systemui.ScreenDecorations$4 r1 = r0.mColorInversionSetting
            if (r1 == 0) goto L_0x038c
            r1.setListening(r10)
        L_0x038c:
            com.android.systemui.broadcast.BroadcastDispatcher r1 = r0.mBroadcastDispatcher
            com.android.systemui.ScreenDecorations$6 r2 = r0.mUserSwitchIntentReceiver
            r1.unregisterReceiver(r2)
            r0.mIsRegistered = r10
            goto L_0x03ef
        L_0x0396:
            boolean r1 = r0.mIsRegistered
            if (r1 == 0) goto L_0x039b
            return
        L_0x039b:
            android.util.DisplayMetrics r1 = new android.util.DisplayMetrics
            r1.<init>()
            android.content.Context r2 = r0.mContext
            android.view.Display r2 = r2.getDisplay()
            r2.getMetrics(r1)
            float r1 = r1.density
            r0.mDensity = r1
            java.util.concurrent.Executor r1 = r0.mMainExecutor
            com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4 r2 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4
            r2.<init>(r0, r10)
            r1.execute(r2)
            com.android.systemui.ScreenDecorations$4 r1 = r0.mColorInversionSetting
            if (r1 != 0) goto L_0x03cc
            com.android.systemui.ScreenDecorations$4 r1 = new com.android.systemui.ScreenDecorations$4
            com.android.systemui.util.settings.SecureSettings r2 = r0.mSecureSettings
            android.os.Handler r3 = r0.mHandler
            com.android.systemui.settings.UserTracker r4 = r0.mUserTracker
            int r4 = r4.getUserId()
            r1.<init>(r2, r3, r4)
            r0.mColorInversionSetting = r1
        L_0x03cc:
            com.android.systemui.ScreenDecorations$4 r1 = r0.mColorInversionSetting
            r2 = 1
            r1.setListening(r2)
            com.android.systemui.ScreenDecorations$4 r1 = r0.mColorInversionSetting
            r1.onChange(r10)
            android.content.IntentFilter r1 = new android.content.IntentFilter
            r1.<init>()
            java.lang.String r2 = "android.intent.action.USER_SWITCHED"
            r1.addAction(r2)
            com.android.systemui.broadcast.BroadcastDispatcher r2 = r0.mBroadcastDispatcher
            com.android.systemui.ScreenDecorations$6 r3 = r0.mUserSwitchIntentReceiver
            com.android.systemui.util.concurrency.DelayableExecutor r4 = r0.mExecutor
            android.os.UserHandle r5 = android.os.UserHandle.ALL
            r2.registerReceiver(r3, r1, r4, r5)
            r1 = 1
            r0.mIsRegistered = r1
        L_0x03ef:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorations.setupDecorations():void");
    }

    public final void start() {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
            return;
        }
        Handler buildHandlerOnNewThread = this.mThreadFactory.buildHandlerOnNewThread();
        this.mHandler = buildHandlerOnNewThread;
        ExecutorImpl buildDelayableExecutorOnHandler = this.mThreadFactory.buildDelayableExecutorOnHandler(buildHandlerOnNewThread);
        this.mExecutor = buildDelayableExecutorOnHandler;
        buildDelayableExecutorOnHandler.execute(new ScreenDecorations$$ExternalSyntheticLambda1(this, 0));
        PrivacyDotViewController privacyDotViewController = this.mDotViewController;
        DelayableExecutor delayableExecutor = this.mExecutor;
        Objects.requireNonNull(privacyDotViewController);
        privacyDotViewController.uiExecutor = delayableExecutor;
    }

    public final void updateLayoutParams() {
        if (this.mOverlays != null) {
            for (int i = 0; i < 4; i++) {
                OverlayWindow[] overlayWindowArr = this.mOverlays;
                if (overlayWindowArr[i] != null) {
                    WindowManager windowManager = this.mWindowManager;
                    OverlayWindow overlayWindow = overlayWindowArr[i];
                    Objects.requireNonNull(overlayWindow);
                    windowManager.updateViewLayout(overlayWindow.rootView, getWindowLayoutParams(i));
                }
            }
        }
    }

    public final void updateOrientation() {
        boolean z;
        int i;
        Object obj;
        if (this.mHandler.getLooper().getThread() == Thread.currentThread()) {
            z = true;
        } else {
            z = false;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("must call on ");
        m.append(this.mHandler.getLooper().getThread());
        m.append(", but was ");
        m.append(Thread.currentThread());
        Preconditions.checkState(z, m.toString());
        int rotation = this.mContext.getDisplay().getRotation();
        if (this.mRotation != rotation) {
            PrivacyDotViewController privacyDotViewController = this.mDotViewController;
            Objects.requireNonNull(privacyDotViewController);
            Intrinsics.stringPlus("updateRotation: ", Integer.valueOf(rotation));
            synchronized (privacyDotViewController.lock) {
                ViewState viewState = privacyDotViewController.nextViewState;
                Objects.requireNonNull(viewState);
                if (rotation != viewState.rotation) {
                    ViewState viewState2 = privacyDotViewController.nextViewState;
                    Objects.requireNonNull(viewState2);
                    boolean z2 = viewState2.layoutRtl;
                    privacyDotViewController.setCornerVisibilities();
                    View selectDesignatedCorner = privacyDotViewController.selectDesignatedCorner(rotation, z2);
                    if (selectDesignatedCorner != null) {
                        i = privacyDotViewController.cornerForView(selectDesignatedCorner);
                    } else {
                        i = -1;
                    }
                    int i2 = i;
                    int statusBarPaddingTop = privacyDotViewController.contentInsetsProvider.getStatusBarPaddingTop(Integer.valueOf(rotation));
                    Object obj2 = privacyDotViewController.lock;
                    synchronized (obj2) {
                        try {
                            obj = obj2;
                            try {
                                privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, rotation, statusBarPaddingTop, i2, selectDesignatedCorner, (String) null, 8703));
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            obj = obj2;
                            throw th;
                        }
                    }
                }
            }
        }
        if (!this.mPendingRotationChange && rotation != this.mRotation) {
            this.mRotation = rotation;
            ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
            if (screenDecorHwcLayer != null) {
                screenDecorHwcLayer.displayRotation = rotation;
                screenDecorHwcLayer.updateCutout();
                screenDecorHwcLayer.updateProtectionBoundingPath();
            }
            if (this.mOverlays != null) {
                updateLayoutParams();
                DisplayCutout cutout = getCutout();
                for (int i3 = 0; i3 < 4; i3++) {
                    if (this.mOverlays[i3] != null) {
                        updateView(i3, cutout);
                    }
                }
            }
        }
    }

    public final void updateRoundedCornerDrawable() {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Context context = this.mContext;
        String str = this.mDisplayUniqueId;
        Resources resources = context.getResources();
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray obtainTypedArray = resources.obtainTypedArray(C1777R.array.config_roundedCornerDrawableArray);
        if (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= obtainTypedArray.length()) {
            drawable = context.getDrawable(C1777R.C1778drawable.rounded);
        } else {
            drawable = obtainTypedArray.getDrawable(displayUniqueIdConfigIndex);
        }
        obtainTypedArray.recycle();
        this.mRoundedCornerDrawable = drawable;
        Context context2 = this.mContext;
        String str2 = this.mDisplayUniqueId;
        Resources resources2 = context2.getResources();
        int displayUniqueIdConfigIndex2 = DisplayUtils.getDisplayUniqueIdConfigIndex(resources2, str2);
        TypedArray obtainTypedArray2 = resources2.obtainTypedArray(C1777R.array.config_roundedCornerTopDrawableArray);
        if (displayUniqueIdConfigIndex2 < 0 || displayUniqueIdConfigIndex2 >= obtainTypedArray2.length()) {
            drawable2 = context2.getDrawable(C1777R.C1778drawable.rounded_corner_top);
        } else {
            drawable2 = obtainTypedArray2.getDrawable(displayUniqueIdConfigIndex2);
        }
        obtainTypedArray2.recycle();
        this.mRoundedCornerDrawableTop = drawable2;
        Context context3 = this.mContext;
        String str3 = this.mDisplayUniqueId;
        Resources resources3 = context3.getResources();
        int displayUniqueIdConfigIndex3 = DisplayUtils.getDisplayUniqueIdConfigIndex(resources3, str3);
        TypedArray obtainTypedArray3 = resources3.obtainTypedArray(C1777R.array.config_roundedCornerBottomDrawableArray);
        if (displayUniqueIdConfigIndex3 < 0 || displayUniqueIdConfigIndex3 >= obtainTypedArray3.length()) {
            drawable3 = context3.getDrawable(C1777R.C1778drawable.rounded_corner_bottom);
        } else {
            drawable3 = obtainTypedArray3.getDrawable(displayUniqueIdConfigIndex3);
        }
        obtainTypedArray3.recycle();
        this.mRoundedCornerDrawableBottom = drawable3;
        updateRoundedCornerImageView();
    }

    public final void updateRoundedCornerImageView() {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3 = this.mRoundedCornerDrawableTop;
        if (drawable3 == null) {
            drawable3 = this.mRoundedCornerDrawable;
        }
        Drawable drawable4 = this.mRoundedCornerDrawableBottom;
        if (drawable4 == null) {
            drawable4 = this.mRoundedCornerDrawable;
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer != null) {
            Objects.requireNonNull(screenDecorHwcLayer);
            screenDecorHwcLayer.roundedCornerDrawableTop = drawable3;
            screenDecorHwcLayer.roundedCornerDrawableBottom = drawable4;
            screenDecorHwcLayer.updateRoundedCornerDrawableBounds();
            screenDecorHwcLayer.invalidate();
        } else if (this.mOverlays != null) {
            ColorStateList valueOf = ColorStateList.valueOf(this.mTintColor);
            for (int i = 0; i < 4; i++) {
                OverlayWindow[] overlayWindowArr = this.mOverlays;
                if (overlayWindowArr[i] != null) {
                    OverlayWindow overlayWindow = overlayWindowArr[i];
                    Objects.requireNonNull(overlayWindow);
                    ViewGroup viewGroup = overlayWindow.rootView;
                    ((ImageView) viewGroup.findViewById(C1777R.C1779id.left)).setImageTintList(valueOf);
                    ((ImageView) viewGroup.findViewById(C1777R.C1779id.right)).setImageTintList(valueOf);
                    ImageView imageView = (ImageView) viewGroup.findViewById(C1777R.C1779id.left);
                    if (isTopRoundedCorner(i, C1777R.C1779id.left)) {
                        drawable = drawable3;
                    } else {
                        drawable = drawable4;
                    }
                    imageView.setImageDrawable(drawable);
                    ImageView imageView2 = (ImageView) viewGroup.findViewById(C1777R.C1779id.right);
                    if (isTopRoundedCorner(i, C1777R.C1779id.right)) {
                        drawable2 = drawable3;
                    } else {
                        drawable2 = drawable4;
                    }
                    imageView2.setImageDrawable(drawable2);
                }
            }
        }
    }

    public final void updateRoundedCornerRadii() {
        boolean z;
        int roundedCornerRadius = RoundedCorners.getRoundedCornerRadius(this.mContext.getResources(), this.mDisplayUniqueId);
        int roundedCornerTopRadius = RoundedCorners.getRoundedCornerTopRadius(this.mContext.getResources(), this.mDisplayUniqueId);
        int roundedCornerBottomRadius = RoundedCorners.getRoundedCornerBottomRadius(this.mContext.getResources(), this.mDisplayUniqueId);
        Point point = this.mRoundedDefault;
        if (point.x == roundedCornerRadius && this.mRoundedDefaultTop.x == roundedCornerTopRadius && this.mRoundedDefaultBottom.x == roundedCornerBottomRadius) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            if (this.mIsRoundedCornerMultipleRadius) {
                point.set(this.mRoundedCornerDrawable.getIntrinsicWidth(), this.mRoundedCornerDrawable.getIntrinsicHeight());
                this.mRoundedDefaultTop.set(this.mRoundedCornerDrawableTop.getIntrinsicWidth(), this.mRoundedCornerDrawableTop.getIntrinsicHeight());
                this.mRoundedDefaultBottom.set(this.mRoundedCornerDrawableBottom.getIntrinsicWidth(), this.mRoundedCornerDrawableBottom.getIntrinsicHeight());
            } else {
                point.set(roundedCornerRadius, roundedCornerRadius);
                this.mRoundedDefaultTop.set(roundedCornerTopRadius, roundedCornerTopRadius);
                this.mRoundedDefaultBottom.set(roundedCornerBottomRadius, roundedCornerBottomRadius);
            }
            onTuningChanged("sysui_rounded_size", (String) null);
        }
    }

    public final void updateRoundedCornerSize(Point point, Point point2, Point point3) {
        Point point4;
        Point point5;
        if (point2.x == 0) {
            point2 = point;
        }
        if (point3.x != 0) {
            point = point3;
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer != null) {
            int i = point2.x;
            int i2 = point.x;
            Objects.requireNonNull(screenDecorHwcLayer);
            screenDecorHwcLayer.roundedCornerTopSize = i;
            screenDecorHwcLayer.roundedCornerBottomSize = i2;
            screenDecorHwcLayer.updateRoundedCornerDrawableBounds();
            screenDecorHwcLayer.invalidate();
        } else if (this.mOverlays != null) {
            for (int i3 = 0; i3 < 4; i3++) {
                OverlayWindow[] overlayWindowArr = this.mOverlays;
                if (overlayWindowArr[i3] != null) {
                    OverlayWindow overlayWindow = overlayWindowArr[i3];
                    Objects.requireNonNull(overlayWindow);
                    ViewGroup viewGroup = overlayWindow.rootView;
                    View findViewById = viewGroup.findViewById(C1777R.C1779id.left);
                    if (isTopRoundedCorner(i3, C1777R.C1779id.left)) {
                        point4 = point2;
                    } else {
                        point4 = point;
                    }
                    setSize(findViewById, point4);
                    View findViewById2 = viewGroup.findViewById(C1777R.C1779id.right);
                    if (isTopRoundedCorner(i3, C1777R.C1779id.right)) {
                        point5 = point2;
                    } else {
                        point5 = point;
                    }
                    setSize(findViewById2, point5);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0062, code lost:
        if (r7 != false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0071, code lost:
        if (r7 != false) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0076, code lost:
        if (r7 != false) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007b, code lost:
        if (r7 != false) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x007f, code lost:
        r5 = 83;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0096  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateRoundedCornerView(int r6, int r7, android.view.DisplayCutout r8) {
        /*
            r5 = this;
            com.android.systemui.decor.OverlayWindow[] r0 = r5.mOverlays
            r0 = r0[r6]
            java.util.Objects.requireNonNull(r0)
            android.view.ViewGroup r0 = r0.rootView
            android.view.View r0 = r0.findViewById(r7)
            if (r0 != 0) goto L_0x0010
            return
        L_0x0010:
            r1 = 8
            r0.setVisibility(r1)
            android.graphics.Point r1 = r5.mRoundedDefault
            int r1 = r1.x
            r2 = 1
            r3 = 0
            if (r1 > 0) goto L_0x0030
            android.graphics.Point r1 = r5.mRoundedDefaultBottom
            int r1 = r1.x
            if (r1 > 0) goto L_0x0030
            android.graphics.Point r1 = r5.mRoundedDefaultTop
            int r1 = r1.x
            if (r1 > 0) goto L_0x0030
            boolean r1 = r5.mIsRoundedCornerMultipleRadius
            if (r1 == 0) goto L_0x002e
            goto L_0x0030
        L_0x002e:
            r1 = r3
            goto L_0x0031
        L_0x0030:
            r1 = r2
        L_0x0031:
            if (r1 == 0) goto L_0x003f
            boolean r8 = r5.isDefaultShownOverlayPos(r6, r8)
            if (r8 == 0) goto L_0x003f
            android.hardware.graphics.common.DisplayDecorationSupport r8 = r5.mHwcScreenDecorationSupport
            if (r8 != 0) goto L_0x003f
            r8 = r2
            goto L_0x0040
        L_0x003f:
            r8 = r3
        L_0x0040:
            if (r8 == 0) goto L_0x00ba
            r8 = 2131428242(0x7f0b0392, float:1.8478123E38)
            if (r7 != r8) goto L_0x0049
            r7 = r2
            goto L_0x004a
        L_0x0049:
            r7 = r3
        L_0x004a:
            int r5 = r5.mRotation
            int r5 = getBoundPositionFromRotation(r6, r5)
            r6 = 85
            r8 = 53
            r1 = 83
            r4 = 51
            if (r5 == 0) goto L_0x007b
            if (r5 == r2) goto L_0x0076
            r2 = 2
            if (r5 == r2) goto L_0x0071
            r2 = 3
            if (r5 != r2) goto L_0x0065
            if (r7 == 0) goto L_0x0074
            goto L_0x007f
        L_0x0065:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Incorrect position: "
            java.lang.String r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0.m0m(r7, r5)
            r6.<init>(r5)
            throw r6
        L_0x0071:
            if (r7 == 0) goto L_0x0074
            goto L_0x0079
        L_0x0074:
            r5 = r6
            goto L_0x0080
        L_0x0076:
            if (r7 == 0) goto L_0x0079
            goto L_0x007d
        L_0x0079:
            r5 = r8
            goto L_0x0080
        L_0x007b:
            if (r7 == 0) goto L_0x007f
        L_0x007d:
            r5 = r4
            goto L_0x0080
        L_0x007f:
            r5 = r1
        L_0x0080:
            android.view.ViewGroup$LayoutParams r7 = r0.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r7 = (android.widget.FrameLayout.LayoutParams) r7
            r7.gravity = r5
            r7 = 0
            r0.setRotation(r7)
            r7 = 1065353216(0x3f800000, float:1.0)
            r0.setScaleX(r7)
            r0.setScaleY(r7)
            if (r5 == r4) goto L_0x00b7
            r7 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r5 == r8) goto L_0x00b4
            if (r5 == r1) goto L_0x00b0
            if (r5 != r6) goto L_0x00a4
            r5 = 1127481344(0x43340000, float:180.0)
            r0.setRotation(r5)
            goto L_0x00b7
        L_0x00a4:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Unsupported gravity: "
            java.lang.String r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0.m0m(r7, r5)
            r6.<init>(r5)
            throw r6
        L_0x00b0:
            r0.setScaleY(r7)
            goto L_0x00b7
        L_0x00b4:
            r0.setScaleX(r7)
        L_0x00b7:
            r0.setVisibility(r3)
        L_0x00ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorations.updateRoundedCornerView(int, int, android.view.DisplayCutout):void");
    }

    public final void updateView(int i, DisplayCutout displayCutout) {
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr != null && overlayWindowArr[i] != null && this.mHwcScreenDecorationSupport == null) {
            updateRoundedCornerView(i, C1777R.C1779id.left, displayCutout);
            updateRoundedCornerView(i, C1777R.C1779id.right, displayCutout);
            updateRoundedCornerSize(this.mRoundedDefault, this.mRoundedDefaultTop, this.mRoundedDefaultBottom);
            updateRoundedCornerImageView();
            DisplayCutoutView[] displayCutoutViewArr = this.mCutoutViews;
            if (displayCutoutViewArr != null && displayCutoutViewArr[i] != null) {
                DisplayCutoutView displayCutoutView = displayCutoutViewArr[i];
                int i2 = this.mRotation;
                Objects.requireNonNull(displayCutoutView);
                displayCutoutView.mRotation = i2;
                displayCutoutView.updateCutout();
            }
        }
    }

    /* renamed from: -$$Nest$mupdateColorInversion  reason: not valid java name */
    public static void m166$$Nest$mupdateColorInversion(ScreenDecorations screenDecorations, int i) {
        int i2;
        Objects.requireNonNull(screenDecorations);
        if (i != 0) {
            i2 = -1;
        } else {
            i2 = -16777216;
        }
        screenDecorations.mTintColor = i2;
        if (DEBUG_COLOR) {
            screenDecorations.mTintColor = -65536;
        }
        if (screenDecorations.mOverlays != null && screenDecorations.mHwcScreenDecorationSupport == null) {
            ColorStateList valueOf = ColorStateList.valueOf(screenDecorations.mTintColor);
            for (int i3 = 0; i3 < 4; i3++) {
                OverlayWindow[] overlayWindowArr = screenDecorations.mOverlays;
                if (overlayWindowArr[i3] != null) {
                    OverlayWindow overlayWindow = overlayWindowArr[i3];
                    Objects.requireNonNull(overlayWindow);
                    ViewGroup viewGroup = overlayWindow.rootView;
                    int childCount = viewGroup.getChildCount();
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = viewGroup.getChildAt(i4);
                        if (!(childAt.getId() == C1777R.C1779id.privacy_dot_top_left_container || childAt.getId() == C1777R.C1779id.privacy_dot_top_right_container || childAt.getId() == C1777R.C1779id.privacy_dot_bottom_left_container || childAt.getId() == C1777R.C1779id.privacy_dot_bottom_right_container)) {
                            if (childAt instanceof ImageView) {
                                ((ImageView) childAt).setImageTintList(valueOf);
                            } else if (childAt instanceof DisplayCutoutView) {
                                DisplayCutoutView displayCutoutView = (DisplayCutoutView) childAt;
                                displayCutoutView.paint.setColor(screenDecorations.mTintColor);
                                displayCutoutView.invalidate();
                            }
                        }
                    }
                }
            }
        }
    }

    public ScreenDecorations(Context context, Executor executor, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, TunerService tunerService, UserTracker userTracker, PrivacyDotViewController privacyDotViewController, ThreadFactory threadFactory, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory) {
        super(context);
        this.mMainExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mTunerService = tunerService;
        this.mUserTracker = userTracker;
        this.mDotViewController = privacyDotViewController;
        this.mThreadFactory = threadFactory;
        this.mDotFactory = privacyDotDecorProviderFactory;
        C06392 r1 = this.mPrivacyDotShowingListener;
        Objects.requireNonNull(privacyDotViewController);
        privacyDotViewController.showingListener = r1;
    }

    public static boolean isRoundedCornerMultipleRadius(Context context, String str) {
        boolean z;
        Resources resources = context.getResources();
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray obtainTypedArray = resources.obtainTypedArray(C1777R.array.config_roundedCornerMultipleRadiusArray);
        if (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= obtainTypedArray.length()) {
            z = resources.getBoolean(C1777R.bool.config_roundedCornerMultipleRadius);
        } else {
            z = obtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        }
        obtainTypedArray.recycle();
        return z;
    }

    public WindowManager.LayoutParams getWindowLayoutParams(int i) {
        int i2;
        String str;
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        int boundPositionFromRotation = getBoundPositionFromRotation(i, this.mRotation);
        int i3 = -2;
        int i4 = 3;
        if (boundPositionFromRotation == 1 || boundPositionFromRotation == 3) {
            i2 = -1;
        } else {
            i2 = -2;
        }
        windowLayoutBaseParams.width = i2;
        int boundPositionFromRotation2 = getBoundPositionFromRotation(i, this.mRotation);
        if (!(boundPositionFromRotation2 == 1 || boundPositionFromRotation2 == 3)) {
            i3 = -1;
        }
        windowLayoutBaseParams.height = i3;
        if (i == 0) {
            str = "ScreenDecorOverlayLeft";
        } else if (i == 1) {
            str = "ScreenDecorOverlay";
        } else if (i == 2) {
            str = "ScreenDecorOverlayRight";
        } else if (i == 3) {
            str = "ScreenDecorOverlayBottom";
        } else {
            throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("unknown bound position: ", i));
        }
        windowLayoutBaseParams.setTitle(str);
        int boundPositionFromRotation3 = getBoundPositionFromRotation(i, this.mRotation);
        if (boundPositionFromRotation3 != 0) {
            if (boundPositionFromRotation3 == 1) {
                i4 = 48;
            } else if (boundPositionFromRotation3 == 2) {
                i4 = 5;
            } else if (boundPositionFromRotation3 == 3) {
                i4 = 80;
            } else {
                throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("unknown bound position: ", i));
            }
        }
        windowLayoutBaseParams.gravity = i4;
        return windowLayoutBaseParams;
    }

    public void setSize(View view, Point point) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        view.setLayoutParams(layoutParams);
    }
}
