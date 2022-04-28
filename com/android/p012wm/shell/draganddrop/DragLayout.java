package com.android.p012wm.shell.draganddrop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.draganddrop.DragLayout */
public final class DragLayout extends LinearLayout {
    public DragAndDropPolicy.Target mCurrentTarget = null;
    public int mDisplayMargin;
    public int mDividerSize;
    public DropZoneView mDropZoneView1;
    public DropZoneView mDropZoneView2;
    public boolean mHasDropped;
    public final IconProvider mIconProvider;
    public Insets mInsets = Insets.NONE;
    public boolean mIsShowing;
    public final DragAndDropPolicy mPolicy;
    public final SplitScreenController mSplitScreenController;
    public final StatusBarManager mStatusBarManager;

    public static int getResizingBackgroundColor(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int backgroundColor = runningTaskInfo.taskDescription.getBackgroundColor();
        if (backgroundColor == -1) {
            backgroundColor = -1;
        }
        return Color.valueOf(backgroundColor).toArgb();
    }

    public final void animateSplitContainers(boolean z, final Runnable runnable) {
        int i;
        ObjectAnimator objectAnimator;
        StatusBarManager statusBarManager = this.mStatusBarManager;
        if (z) {
            i = 9830400;
        } else {
            i = 0;
        }
        statusBarManager.disable(i);
        this.mDropZoneView1.setShowingMargin(z);
        this.mDropZoneView2.setShowingMargin(z);
        DropZoneView dropZoneView = this.mDropZoneView1;
        Objects.requireNonNull(dropZoneView);
        ObjectAnimator objectAnimator2 = dropZoneView.mMarginAnimator;
        if (objectAnimator2 == null || !objectAnimator2.isRunning()) {
            ObjectAnimator objectAnimator3 = dropZoneView.mBackgroundAnimator;
            if (objectAnimator3 == null || !objectAnimator3.isRunning()) {
                objectAnimator = null;
            } else {
                objectAnimator = dropZoneView.mBackgroundAnimator;
            }
        } else {
            objectAnimator = dropZoneView.mMarginAnimator;
        }
        if (runnable == null) {
            return;
        }
        if (objectAnimator != null) {
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x010f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void recomputeDropTargets() {
        /*
            r12 = this;
            boolean r0 = r12.mIsShowing
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.android.wm.shell.draganddrop.DragAndDropPolicy r0 = r12.mPolicy
            android.graphics.Insets r1 = r12.mInsets
            java.util.Objects.requireNonNull(r0)
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r2 = r0.mTargets
            r2.clear()
            com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession r2 = r0.mSession
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x001b
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r0 = r0.mTargets
            goto L_0x011b
        L_0x001b:
            com.android.wm.shell.common.DisplayLayout r2 = r2.displayLayout
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mWidth
            com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession r5 = r0.mSession
            com.android.wm.shell.common.DisplayLayout r5 = r5.displayLayout
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mHeight
            int r6 = r1.left
            int r2 = r2 - r6
            int r7 = r1.right
            int r2 = r2 - r7
            int r7 = r1.top
            int r5 = r5 - r7
            int r1 = r1.bottom
            int r5 = r5 - r1
            android.graphics.Rect r1 = new android.graphics.Rect
            int r2 = r2 + r6
            int r5 = r5 + r7
            r1.<init>(r6, r7, r2, r5)
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>(r1)
            android.graphics.Rect r5 = new android.graphics.Rect
            r5.<init>(r1)
            com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession r6 = r0.mSession
            com.android.wm.shell.common.DisplayLayout r6 = r6.displayLayout
            boolean r6 = r6.isLandscape()
            com.android.wm.shell.splitscreen.SplitScreenController r7 = r0.mSplitScreen
            if (r7 == 0) goto L_0x005c
            boolean r7 = r7.isSplitScreenVisible()
            if (r7 == 0) goto L_0x005c
            r7 = r4
            goto L_0x005d
        L_0x005c:
            r7 = r3
        L_0x005d:
            android.content.Context r8 = r0.mContext
            android.content.res.Resources r8 = r8.getResources()
            r9 = 2131167037(0x7f07073d, float:1.7948336E38)
            int r8 = r8.getDimensionPixelSize(r9)
            float r8 = (float) r8
            if (r7 != 0) goto L_0x007a
            com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession r9 = r0.mSession
            int r10 = r9.runningTaskActType
            if (r10 != r4) goto L_0x0078
            int r9 = r9.runningTaskWinMode
            if (r9 != r4) goto L_0x0078
            goto L_0x007a
        L_0x0078:
            r9 = r3
            goto L_0x007b
        L_0x007a:
            r9 = r4
        L_0x007b:
            if (r9 == 0) goto L_0x010f
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            android.graphics.Rect r5 = new android.graphics.Rect
            r5.<init>()
            com.android.wm.shell.splitscreen.SplitScreenController r9 = r0.mSplitScreen
            r9.getStageBounds(r2, r5)
            r2.intersect(r1)
            r5.intersect(r1)
            r9 = 1073741824(0x40000000, float:2.0)
            r10 = 2
            if (r6 == 0) goto L_0x00d3
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            android.graphics.Rect r11 = new android.graphics.Rect
            r11.<init>()
            if (r7 == 0) goto L_0x00b4
            int r7 = r2.right
            float r7 = (float) r7
            float r8 = r8 / r9
            float r8 = r8 + r7
            r6.set(r1)
            int r7 = (int) r8
            r6.right = r7
            r11.set(r1)
            r11.left = r7
            goto L_0x00bd
        L_0x00b4:
            android.graphics.Rect[] r7 = new android.graphics.Rect[r10]
            r7[r3] = r6
            r7[r4] = r11
            r1.splitVertically(r7)
        L_0x00bd:
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r1 = r0.mTargets
            com.android.wm.shell.draganddrop.DragAndDropPolicy$Target r7 = new com.android.wm.shell.draganddrop.DragAndDropPolicy$Target
            r7.<init>(r4, r6, r2)
            r1.add(r7)
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r1 = r0.mTargets
            com.android.wm.shell.draganddrop.DragAndDropPolicy$Target r2 = new com.android.wm.shell.draganddrop.DragAndDropPolicy$Target
            r6 = 3
            r2.<init>(r6, r11, r5)
            r1.add(r2)
            goto L_0x0119
        L_0x00d3:
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            android.graphics.Rect r11 = new android.graphics.Rect
            r11.<init>()
            if (r7 == 0) goto L_0x00f0
            int r7 = r2.bottom
            float r7 = (float) r7
            float r8 = r8 / r9
            float r8 = r8 + r7
            r6.set(r1)
            int r7 = (int) r8
            r6.bottom = r7
            r11.set(r1)
            r11.top = r7
            goto L_0x00f9
        L_0x00f0:
            android.graphics.Rect[] r7 = new android.graphics.Rect[r10]
            r7[r3] = r6
            r7[r4] = r11
            r1.splitHorizontally(r7)
        L_0x00f9:
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r1 = r0.mTargets
            com.android.wm.shell.draganddrop.DragAndDropPolicy$Target r7 = new com.android.wm.shell.draganddrop.DragAndDropPolicy$Target
            r7.<init>(r10, r6, r2)
            r1.add(r7)
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r1 = r0.mTargets
            com.android.wm.shell.draganddrop.DragAndDropPolicy$Target r2 = new com.android.wm.shell.draganddrop.DragAndDropPolicy$Target
            r6 = 4
            r2.<init>(r6, r11, r5)
            r1.add(r2)
            goto L_0x0119
        L_0x010f:
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r1 = r0.mTargets
            com.android.wm.shell.draganddrop.DragAndDropPolicy$Target r6 = new com.android.wm.shell.draganddrop.DragAndDropPolicy$Target
            r6.<init>(r3, r5, r2)
            r1.add(r6)
        L_0x0119:
            java.util.ArrayList<com.android.wm.shell.draganddrop.DragAndDropPolicy$Target> r0 = r0.mTargets
        L_0x011b:
            r1 = r3
        L_0x011c:
            int r2 = r0.size()
            if (r1 >= r2) goto L_0x0147
            java.lang.Object r2 = r0.get(r1)
            com.android.wm.shell.draganddrop.DragAndDropPolicy$Target r2 = (com.android.p012wm.shell.draganddrop.DragAndDropPolicy.Target) r2
            boolean r5 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled
            if (r5 == 0) goto L_0x013d
            java.lang.String r5 = java.lang.String.valueOf(r2)
            com.android.wm.shell.protolog.ShellProtoLogGroup r6 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP
            r7 = -710770147(0xffffffffd5a2821d, float:-2.23349645E13)
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r4]
            r9[r3] = r5
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r6, r7, r3, r8, r9)
        L_0x013d:
            android.graphics.Rect r2 = r2.drawRegion
            int r5 = r12.mDisplayMargin
            r2.inset(r5, r5)
            int r1 = r1 + 1
            goto L_0x011c
        L_0x0147:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.draganddrop.DragLayout.recomputeDropTargets():void");
    }

    public final void updateContainerMargins(int i) {
        int i2 = this.mDisplayMargin;
        float f = ((float) i2) / 2.0f;
        if (i == 2) {
            this.mDropZoneView1.setContainerMargin((float) i2, (float) i2, f, (float) i2);
            DropZoneView dropZoneView = this.mDropZoneView2;
            int i3 = this.mDisplayMargin;
            dropZoneView.setContainerMargin(f, (float) i3, (float) i3, (float) i3);
        } else if (i == 1) {
            this.mDropZoneView1.setContainerMargin((float) i2, (float) i2, (float) i2, f);
            DropZoneView dropZoneView2 = this.mDropZoneView2;
            int i4 = this.mDisplayMargin;
            dropZoneView2.setContainerMargin((float) i4, f, (float) i4, (float) i4);
        }
    }

    @SuppressLint({"WrongConstant"})
    public DragLayout(Context context, SplitScreenController splitScreenController, IconProvider iconProvider) {
        super(context);
        this.mSplitScreenController = splitScreenController;
        this.mIconProvider = iconProvider;
        this.mPolicy = new DragAndDropPolicy(context, ActivityTaskManager.getInstance(), splitScreenController, new DragAndDropPolicy.DefaultStarter(context));
        this.mStatusBarManager = (StatusBarManager) context.getSystemService(StatusBarManager.class);
        this.mDisplayMargin = context.getResources().getDimensionPixelSize(C1777R.dimen.drop_layout_display_margin);
        this.mDividerSize = context.getResources().getDimensionPixelSize(C1777R.dimen.split_divider_bar_width);
        this.mDropZoneView1 = new DropZoneView(context, (AttributeSet) null);
        this.mDropZoneView2 = new DropZoneView(context, (AttributeSet) null);
        addView(this.mDropZoneView1, new LinearLayout.LayoutParams(-1, -1));
        addView(this.mDropZoneView2, new LinearLayout.LayoutParams(-1, -1));
        ((LinearLayout.LayoutParams) this.mDropZoneView1.getLayoutParams()).weight = 1.0f;
        ((LinearLayout.LayoutParams) this.mDropZoneView2.getLayoutParams()).weight = 1.0f;
        updateContainerMargins(getResources().getConfiguration().orientation);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mInsets = windowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        recomputeDropTargets();
        int i = getResources().getConfiguration().orientation;
        if (i == 2) {
            this.mDropZoneView1.setBottomInset((float) this.mInsets.bottom);
            this.mDropZoneView2.setBottomInset((float) this.mInsets.bottom);
        } else if (i == 1) {
            this.mDropZoneView1.setBottomInset(0.0f);
            this.mDropZoneView2.setBottomInset((float) this.mInsets.bottom);
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void updateDropZoneSizes(Rect rect, Rect rect2) {
        float f;
        int i;
        int i2;
        int i3;
        boolean z = true;
        if (getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        int i4 = this.mDividerSize / 2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDropZoneView1.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mDropZoneView2.getLayoutParams();
        int i5 = -1;
        if (z) {
            layoutParams.width = -1;
            layoutParams2.width = -1;
            if (rect != null) {
                i3 = rect.height() + i4;
            } else {
                i3 = -1;
            }
            layoutParams.height = i3;
            if (rect2 != null) {
                i5 = rect2.height() + i4;
            }
            layoutParams2.height = i5;
        } else {
            if (rect != null) {
                i = rect.width() + i4;
            } else {
                i = -1;
            }
            layoutParams.width = i;
            if (rect2 != null) {
                i2 = rect2.width() + i4;
            } else {
                i2 = -1;
            }
            layoutParams2.width = i2;
            layoutParams.height = -1;
            layoutParams2.height = -1;
        }
        float f2 = 0.0f;
        if (rect != null) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        layoutParams.weight = f;
        if (rect2 == null) {
            f2 = 1.0f;
        }
        layoutParams2.weight = f2;
        this.mDropZoneView1.setLayoutParams(layoutParams);
        this.mDropZoneView2.setLayoutParams(layoutParams2);
    }
}
