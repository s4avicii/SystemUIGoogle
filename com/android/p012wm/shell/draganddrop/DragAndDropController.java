package com.android.p012wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda21;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.draganddrop.DragAndDropEventLogger;
import com.android.p012wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.draganddrop.DragAndDropController */
public final class DragAndDropController implements DisplayController.OnDisplaysChangedListener, View.OnDragListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DisplayController mDisplayController;
    public final SparseArray<PerDisplay> mDisplayDropTargets = new SparseArray<>();
    public final IconProvider mIconProvider;
    public DragAndDropImpl mImpl;
    public final DragAndDropEventLogger mLogger;
    public ShellExecutor mMainExecutor;
    public SplitScreenController mSplitScreen;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropController$DragAndDropImpl */
    public class DragAndDropImpl implements DragAndDrop {
        public DragAndDropImpl() {
        }

        public final void onConfigChanged(Configuration configuration) {
            DragAndDropController.this.mMainExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda21(this, configuration, 4));
        }

        public final void onThemeChanged() {
            DragAndDropController.this.mMainExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda17(this, 9));
        }
    }

    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropController$PerDisplay */
    public static class PerDisplay {
        public int activeDragCount;
        public final int displayId;
        public final DragLayout dragLayout;
        public boolean isHandlingDrag;
        public final FrameLayout rootView;

        /* renamed from: wm */
        public final WindowManager f124wm;

        public PerDisplay(int i, WindowManager windowManager, FrameLayout frameLayout, DragLayout dragLayout2) {
            this.displayId = i;
            this.f124wm = windowManager;
            this.rootView = frameLayout;
            this.dragLayout = dragLayout2;
        }
    }

    public static void setDropTargetWindowVisibility(PerDisplay perDisplay, int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1184615936, 5, (String) null, Long.valueOf((long) perDisplay.displayId), Long.valueOf((long) i));
        }
        perDisplay.rootView.setVisibility(i);
        if (i == 0) {
            perDisplay.rootView.requestApplyInsets();
        }
    }

    public final void onDisplayAdded(int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1006733970, 1, (String) null, Long.valueOf((long) i));
        }
        if (i == 0) {
            Context createWindowContext = this.mDisplayController.getDisplayContext(i).createWindowContext(2038, (Bundle) null);
            WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 16777224, -3);
            layoutParams.privateFlags |= -2147483568;
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.setFitInsetsTypes(0);
            layoutParams.setTitle("ShellDropTarget");
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(createWindowContext).inflate(C1777R.layout.global_drop_target, (ViewGroup) null);
            frameLayout.setOnDragListener(this);
            frameLayout.setVisibility(4);
            DragLayout dragLayout = new DragLayout(createWindowContext, this.mSplitScreen, this.mIconProvider);
            frameLayout.addView(dragLayout, new FrameLayout.LayoutParams(-1, -1));
            try {
                windowManager.addView(frameLayout, layoutParams);
                this.mDisplayDropTargets.put(i, new PerDisplay(i, windowManager, frameLayout, dragLayout));
            } catch (WindowManager.InvalidDisplayException unused) {
                Slog.w("DragAndDropController", "Unable to add view for display id: " + i);
            }
        }
    }

    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 2057038970, 1, (String) null, Long.valueOf((long) i));
        }
        PerDisplay perDisplay = this.mDisplayDropTargets.get(i);
        if (perDisplay != null) {
            perDisplay.rootView.requestApplyInsets();
        }
    }

    public final void onDisplayRemoved(int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1382704050, 1, (String) null, Long.valueOf((long) i));
        }
        PerDisplay perDisplay = this.mDisplayDropTargets.get(i);
        if (perDisplay != null) {
            perDisplay.f124wm.removeViewImmediate(perDisplay.rootView);
            this.mDisplayDropTargets.remove(i);
        }
    }

    public final boolean onDrag(View view, DragEvent dragEvent) {
        DragAndDropEventLogger.DragAndDropUiEventEnum dragAndDropUiEventEnum;
        boolean z;
        DragAndDropPolicy.Target target;
        Runnable runnable;
        boolean z2;
        boolean z3;
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1862198614, 680, (String) null, String.valueOf(DragEvent.actionToString(dragEvent.getAction())), Double.valueOf((double) dragEvent.getX()), Double.valueOf((double) dragEvent.getY()), Double.valueOf((double) dragEvent.getOffsetX()), Double.valueOf((double) dragEvent.getOffsetY()));
        }
        int displayId = view.getDisplay().getDisplayId();
        PerDisplay perDisplay = this.mDisplayDropTargets.get(displayId);
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (perDisplay == null) {
            return false;
        }
        if (dragEvent.getAction() == 1) {
            if (dragEvent.getClipData().getItemCount() <= 0 || (!clipDescription.hasMimeType("application/vnd.android.activity") && !clipDescription.hasMimeType("application/vnd.android.shortcut") && !clipDescription.hasMimeType("application/vnd.android.task"))) {
                z3 = false;
            } else {
                z3 = true;
            }
            perDisplay.isHandlingDrag = z3;
            if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
                long itemCount = (long) dragEvent.getClipData().getItemCount();
                String str = "";
                for (int i = 0; i < clipDescription.getMimeTypeCount(); i++) {
                    if (i > 0) {
                        str = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, ", ");
                    }
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(str);
                    m.append(clipDescription.getMimeType(i));
                    str = m.toString();
                }
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 375908576, 7, (String) null, Boolean.valueOf(z3), Long.valueOf(itemCount), String.valueOf(str));
            }
        }
        if (!perDisplay.isHandlingDrag) {
            return false;
        }
        switch (dragEvent.getAction()) {
            case 1:
                if (perDisplay.activeDragCount == 0) {
                    DragAndDropEventLogger dragAndDropEventLogger = this.mLogger;
                    Objects.requireNonNull(dragAndDropEventLogger);
                    ClipDescription clipDescription2 = dragEvent.getClipDescription();
                    ClipData.Item itemAt = dragEvent.getClipData().getItemAt(0);
                    InstanceId parcelableExtra = itemAt.getIntent().getParcelableExtra("android.intent.extra.LOGGING_INSTANCE_ID");
                    dragAndDropEventLogger.mInstanceId = parcelableExtra;
                    if (parcelableExtra == null) {
                        dragAndDropEventLogger.mInstanceId = dragAndDropEventLogger.mIdSequence.newInstanceId();
                    }
                    dragAndDropEventLogger.mActivityInfo = itemAt.getActivityInfo();
                    if (clipDescription2.hasMimeType("application/vnd.android.activity")) {
                        dragAndDropUiEventEnum = DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_ACTIVITY;
                    } else if (clipDescription2.hasMimeType("application/vnd.android.shortcut")) {
                        dragAndDropUiEventEnum = DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_SHORTCUT;
                    } else if (clipDescription2.hasMimeType("application/vnd.android.task")) {
                        dragAndDropUiEventEnum = DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_TASK;
                    } else {
                        throw new IllegalArgumentException("Not an app drag");
                    }
                    dragAndDropEventLogger.log(dragAndDropUiEventEnum, dragAndDropEventLogger.mActivityInfo);
                    InstanceId instanceId = dragAndDropEventLogger.mInstanceId;
                    perDisplay.activeDragCount++;
                    DragLayout dragLayout = perDisplay.dragLayout;
                    DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(displayId);
                    ClipData clipData = dragEvent.getClipData();
                    Objects.requireNonNull(dragLayout);
                    DragAndDropPolicy dragAndDropPolicy = dragLayout.mPolicy;
                    Objects.requireNonNull(dragAndDropPolicy);
                    dragAndDropPolicy.mLoggerSessionId = instanceId;
                    ActivityTaskManager activityTaskManager = dragAndDropPolicy.mActivityTaskManager;
                    DragAndDropPolicy.DragSession dragSession = new DragAndDropPolicy.DragSession(activityTaskManager, displayLayout, clipData);
                    dragAndDropPolicy.mSession = dragSession;
                    List tasks = activityTaskManager.getTasks(1, false);
                    if (!tasks.isEmpty()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) tasks.get(0);
                        dragSession.runningTaskInfo = runningTaskInfo;
                        dragSession.runningTaskWinMode = runningTaskInfo.getWindowingMode();
                        dragSession.runningTaskActType = runningTaskInfo.getActivityType();
                    }
                    ActivityInfo activityInfo = clipData.getItemAt(0).getActivityInfo();
                    if (activityInfo != null) {
                        ActivityInfo.isResizeableMode(activityInfo.resizeMode);
                    }
                    dragSession.dragData = clipData.getItemAt(0).getIntent();
                    dragLayout.mHasDropped = false;
                    dragLayout.mCurrentTarget = null;
                    SplitScreenController splitScreenController = dragLayout.mSplitScreenController;
                    if (splitScreenController == null || !splitScreenController.isSplitScreenVisible()) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (!z) {
                        DragAndDropPolicy dragAndDropPolicy2 = dragLayout.mPolicy;
                        Objects.requireNonNull(dragAndDropPolicy2);
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = dragAndDropPolicy2.mSession.runningTaskInfo;
                        if (runningTaskInfo2 != null) {
                            Drawable icon = dragLayout.mIconProvider.getIcon(runningTaskInfo2.topActivityInfo);
                            int resizingBackgroundColor = DragLayout.getResizingBackgroundColor(runningTaskInfo2);
                            dragLayout.mDropZoneView1.setAppInfo(resizingBackgroundColor, icon);
                            dragLayout.mDropZoneView2.setAppInfo(resizingBackgroundColor, icon);
                            dragLayout.updateDropZoneSizes((Rect) null, (Rect) null);
                        }
                    } else {
                        ActivityManager.RunningTaskInfo taskInfo = dragLayout.mSplitScreenController.getTaskInfo(0);
                        ActivityManager.RunningTaskInfo taskInfo2 = dragLayout.mSplitScreenController.getTaskInfo(1);
                        if (!(taskInfo == null || taskInfo2 == null)) {
                            Drawable icon2 = dragLayout.mIconProvider.getIcon(taskInfo.topActivityInfo);
                            int resizingBackgroundColor2 = DragLayout.getResizingBackgroundColor(taskInfo);
                            Drawable icon3 = dragLayout.mIconProvider.getIcon(taskInfo2.topActivityInfo);
                            int resizingBackgroundColor3 = DragLayout.getResizingBackgroundColor(taskInfo2);
                            dragLayout.mDropZoneView1.setAppInfo(resizingBackgroundColor2, icon2);
                            dragLayout.mDropZoneView2.setAppInfo(resizingBackgroundColor3, icon3);
                        }
                        Rect rect = new Rect();
                        Rect rect2 = new Rect();
                        dragLayout.mSplitScreenController.getStageBounds(rect, rect2);
                        dragLayout.updateDropZoneSizes(rect, rect2);
                    }
                    setDropTargetWindowVisibility(perDisplay, 0);
                    break;
                } else {
                    Slog.w("DragAndDropController", "Unexpected drag start during an active drag");
                    return false;
                }
                break;
            case 2:
                DragLayout dragLayout2 = perDisplay.dragLayout;
                Objects.requireNonNull(dragLayout2);
                if (!dragLayout2.mHasDropped) {
                    DragAndDropPolicy dragAndDropPolicy3 = dragLayout2.mPolicy;
                    int x = (int) dragEvent.getX();
                    int y = (int) dragEvent.getY();
                    Objects.requireNonNull(dragAndDropPolicy3);
                    int size = dragAndDropPolicy3.mTargets.size();
                    while (true) {
                        size--;
                        if (size >= 0) {
                            target = dragAndDropPolicy3.mTargets.get(size);
                            if (target.hitRegion.contains(x, y)) {
                            }
                        } else {
                            target = null;
                        }
                    }
                    if (dragLayout2.mCurrentTarget != target) {
                        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
                            Object[] objArr = {String.valueOf(target)};
                            runnable = null;
                            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1481772149, 0, (String) null, objArr);
                        } else {
                            runnable = null;
                        }
                        if (target == null) {
                            dragLayout2.animateSplitContainers(false, runnable);
                        } else if (dragLayout2.mCurrentTarget == null) {
                            dragLayout2.animateSplitContainers(true, runnable);
                            int i2 = target.type;
                            if (i2 == 1 || i2 == 2) {
                                dragLayout2.mDropZoneView1.setShowingHighlight(true);
                                dragLayout2.mDropZoneView2.setShowingHighlight(false);
                            } else if (i2 == 3 || i2 == 4) {
                                dragLayout2.mDropZoneView1.setShowingHighlight(false);
                                dragLayout2.mDropZoneView2.setShowingHighlight(true);
                            }
                        } else {
                            dragLayout2.mDropZoneView1.animateSwitch();
                            dragLayout2.mDropZoneView2.animateSwitch();
                        }
                        dragLayout2.mCurrentTarget = target;
                        break;
                    }
                }
                break;
            case 3:
                SurfaceControl dragSurface = dragEvent.getDragSurface();
                perDisplay.activeDragCount--;
                DragLayout dragLayout3 = perDisplay.dragLayout;
                DragAndDropController$$ExternalSyntheticLambda0 dragAndDropController$$ExternalSyntheticLambda0 = new DragAndDropController$$ExternalSyntheticLambda0(this, perDisplay, dragSurface, 0);
                Objects.requireNonNull(dragLayout3);
                DragAndDropPolicy.Target target2 = dragLayout3.mCurrentTarget;
                if (target2 != null) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                dragLayout3.mHasDropped = true;
                dragLayout3.mPolicy.handleDrop(target2, dragEvent.getClipData());
                dragLayout3.mIsShowing = false;
                dragLayout3.animateSplitContainers(false, dragAndDropController$$ExternalSyntheticLambda0);
                dragLayout3.mCurrentTarget = null;
                return z2;
            case 4:
                DragLayout dragLayout4 = perDisplay.dragLayout;
                Objects.requireNonNull(dragLayout4);
                if (dragLayout4.mHasDropped) {
                    DragAndDropEventLogger dragAndDropEventLogger2 = this.mLogger;
                    Objects.requireNonNull(dragAndDropEventLogger2);
                    dragAndDropEventLogger2.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_DROPPED, dragAndDropEventLogger2.mActivityInfo);
                } else {
                    perDisplay.activeDragCount--;
                    DragLayout dragLayout5 = perDisplay.dragLayout;
                    NavBarTuner$$ExternalSyntheticLambda6 navBarTuner$$ExternalSyntheticLambda6 = new NavBarTuner$$ExternalSyntheticLambda6(this, perDisplay, 6);
                    Objects.requireNonNull(dragLayout5);
                    dragLayout5.mIsShowing = false;
                    dragLayout5.animateSplitContainers(false, navBarTuner$$ExternalSyntheticLambda6);
                    dragLayout5.mCurrentTarget = null;
                }
                DragAndDropEventLogger dragAndDropEventLogger3 = this.mLogger;
                Objects.requireNonNull(dragAndDropEventLogger3);
                dragAndDropEventLogger3.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_END, dragAndDropEventLogger3.mActivityInfo);
                break;
            case 5:
                DragLayout dragLayout6 = perDisplay.dragLayout;
                Objects.requireNonNull(dragLayout6);
                dragLayout6.mIsShowing = true;
                dragLayout6.recomputeDropTargets();
                break;
            case FalsingManager.VERSION:
                DragLayout dragLayout7 = perDisplay.dragLayout;
                Objects.requireNonNull(dragLayout7);
                dragLayout7.mIsShowing = false;
                dragLayout7.animateSplitContainers(false, (Runnable) null);
                dragLayout7.mCurrentTarget = null;
                break;
        }
        return true;
    }

    public DragAndDropController(DisplayController displayController, UiEventLogger uiEventLogger, IconProvider iconProvider, ShellExecutor shellExecutor) {
        this.mDisplayController = displayController;
        this.mLogger = new DragAndDropEventLogger(uiEventLogger);
        this.mIconProvider = iconProvider;
        this.mMainExecutor = shellExecutor;
        this.mImpl = new DragAndDropImpl();
    }

    static {
        Class<DragAndDropController> cls = DragAndDropController.class;
    }
}
