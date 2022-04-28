package com.android.systemui.clipboardoverlay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.policy.PhoneWindow;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.OverlayActionChip;
import com.android.systemui.screenshot.SwipeDismissHandler;
import com.android.systemui.screenshot.TimeoutHandler;
import com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class ClipboardOverlayController {
    public final AccessibilityManager mAccessibilityManager;
    public final ArrayList<OverlayActionChip> mActionChips = new ArrayList<>();
    public final LinearLayout mActionContainer;
    public final View mActionContainerBackground;
    public boolean mBlockAttach = false;
    public C07241 mCloseDialogsReceiver;
    public final FrameLayout mContainer;
    public final Context mContext;
    public final View mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public final OverlayActionChip mEditChip;
    public final ImageView mImagePreview;
    public C07263 mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public Runnable mOnSessionCompleteListener;
    public final View mPreviewBorder;
    public C07252 mScreenshotReceiver;
    public final TextClassifier mTextClassifier;
    public final TextView mTextPreview;
    public final TimeoutHandler mTimeoutHandler;
    public final DraggableConstraintLayout mView;
    public final PhoneWindow mWindow;
    public final WindowManager mWindowManager;

    public final void animateOut() {
        DraggableConstraintLayout draggableConstraintLayout = this.mView;
        Objects.requireNonNull(draggableConstraintLayout);
        SwipeDismissHandler swipeDismissHandler = draggableConstraintLayout.mSwipeDismissHandler;
        Objects.requireNonNull(swipeDismissHandler);
        swipeDismissHandler.dismiss(1.0f);
    }

    public final void resetActionChips() {
        Iterator<OverlayActionChip> it = this.mActionChips.iterator();
        while (it.hasNext()) {
            this.mActionContainer.removeView(it.next());
        }
        this.mActionChips.clear();
    }

    public final void showTextPreview(CharSequence charSequence) {
        this.mTextPreview.setVisibility(0);
        this.mImagePreview.setVisibility(8);
        this.mTextPreview.setText(charSequence);
        this.mEditChip.setVisibility(8);
    }

    public ClipboardOverlayController(Context context, TimeoutHandler timeoutHandler) {
        View peekDecorView;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        Context createWindowContext = context.createDisplayContext(displayManager.getDisplay(0)).createWindowContext(2036, (Bundle) null);
        this.mContext = createWindowContext;
        this.mAccessibilityManager = AccessibilityManager.getInstance(createWindowContext);
        TextClassificationManager textClassificationManager = (TextClassificationManager) context.getSystemService(TextClassificationManager.class);
        Objects.requireNonNull(textClassificationManager);
        this.mTextClassifier = textClassificationManager.getTextClassifier();
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        createWindowContext.getDisplay().getRealMetrics(displayMetrics);
        this.mTimeoutHandler = timeoutHandler;
        timeoutHandler.mDefaultTimeout = 6000;
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        floatingWindowParams.setTitle("ClipboardOverlay");
        PhoneWindow phoneWindow = new PhoneWindow(createWindowContext);
        phoneWindow.requestFeature(1);
        phoneWindow.requestFeature(13);
        phoneWindow.setBackgroundDrawableResource(17170445);
        this.mWindow = phoneWindow;
        phoneWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
        int i = floatingWindowParams.flags;
        int i2 = i | 8;
        floatingWindowParams.flags = i2;
        if (!(i2 == i || (peekDecorView = phoneWindow.peekDecorView()) == null || !peekDecorView.isAttachedToWindow())) {
            windowManager.updateViewLayout(peekDecorView, floatingWindowParams);
        }
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(createWindowContext).inflate(C1777R.layout.clipboard_overlay, (ViewGroup) null);
        this.mContainer = frameLayout;
        DraggableConstraintLayout draggableConstraintLayout = (DraggableConstraintLayout) frameLayout.findViewById(C1777R.C1779id.clipboard_ui);
        Objects.requireNonNull(draggableConstraintLayout);
        this.mView = draggableConstraintLayout;
        View findViewById = draggableConstraintLayout.findViewById(C1777R.C1779id.actions_container_background);
        Objects.requireNonNull(findViewById);
        this.mActionContainerBackground = findViewById;
        LinearLayout linearLayout = (LinearLayout) draggableConstraintLayout.findViewById(C1777R.C1779id.actions);
        Objects.requireNonNull(linearLayout);
        this.mActionContainer = linearLayout;
        ImageView imageView = (ImageView) draggableConstraintLayout.findViewById(C1777R.C1779id.image_preview);
        Objects.requireNonNull(imageView);
        this.mImagePreview = imageView;
        TextView textView = (TextView) draggableConstraintLayout.findViewById(C1777R.C1779id.text_preview);
        Objects.requireNonNull(textView);
        this.mTextPreview = textView;
        View findViewById2 = draggableConstraintLayout.findViewById(C1777R.C1779id.preview_border);
        Objects.requireNonNull(findViewById2);
        this.mPreviewBorder = findViewById2;
        OverlayActionChip overlayActionChip = (OverlayActionChip) draggableConstraintLayout.findViewById(C1777R.C1779id.edit_chip);
        Objects.requireNonNull(overlayActionChip);
        this.mEditChip = overlayActionChip;
        OverlayActionChip overlayActionChip2 = (OverlayActionChip) draggableConstraintLayout.findViewById(C1777R.C1779id.remote_copy_chip);
        Objects.requireNonNull(overlayActionChip2);
        View findViewById3 = draggableConstraintLayout.findViewById(C1777R.C1779id.dismiss_button);
        Objects.requireNonNull(findViewById3);
        this.mDismissButton = findViewById3;
        draggableConstraintLayout.mOnDismiss = new WMShell$7$$ExternalSyntheticLambda2(this, 1);
        draggableConstraintLayout.mOnInteraction = new WMShell$8$$ExternalSyntheticLambda0(timeoutHandler, 1);
        findViewById3.setOnClickListener(new ClipboardOverlayController$$ExternalSyntheticLambda2(this));
        overlayActionChip.setIcon(Icon.createWithResource(createWindowContext, C1777R.C1778drawable.ic_screenshot_edit), true);
        overlayActionChip2.setIcon(Icon.createWithResource(createWindowContext, C1777R.C1778drawable.ic_baseline_devices_24), true);
        PackageManager packageManager = createWindowContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.REMOTE_COPY");
        intent.addFlags(268468224);
        if (packageManager.resolveActivity(intent, 0) != null) {
            overlayActionChip2.setOnClickListener(new RemoteInputView$$ExternalSyntheticLambda0(this, 1));
            overlayActionChip2.setAlpha(1.0f);
        } else {
            overlayActionChip2.setVisibility(8);
        }
        View decorView = phoneWindow.getDecorView();
        if (!decorView.isAttachedToWindow() && !this.mBlockAttach) {
            this.mBlockAttach = true;
            windowManager.addView(decorView, floatingWindowParams);
            decorView.requestApplyInsets();
            draggableConstraintLayout.requestApplyInsets();
            decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                public final void onWindowDetached() {
                }

                public final void onWindowAttached() {
                    ClipboardOverlayController.this.mBlockAttach = false;
                }
            });
        }
        final OneHandedController$$ExternalSyntheticLambda1 oneHandedController$$ExternalSyntheticLambda1 = new OneHandedController$$ExternalSyntheticLambda1(this, 2);
        final View decorView2 = phoneWindow.getDecorView();
        if (decorView2.isAttachedToWindow()) {
            oneHandedController$$ExternalSyntheticLambda1.run();
        } else {
            decorView2.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                public final void onWindowDetached() {
                }

                public final void onWindowAttached() {
                    ClipboardOverlayController.this.mBlockAttach = false;
                    decorView2.getViewTreeObserver().removeOnWindowAttachListener(this);
                    oneHandedController$$ExternalSyntheticLambda1.run();
                }
            });
        }
        timeoutHandler.mOnTimeout = new BaseWifiTracker$$ExternalSyntheticLambda0(this, 2);
        C07241 r12 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mCloseDialogsReceiver = r12;
        createWindowContext.registerReceiver(r12, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        C07252 r122 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("com.android.systemui.SCREENSHOT".equals(intent.getAction())) {
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mScreenshotReceiver = r122;
        createWindowContext.registerReceiver(r122, new IntentFilter("com.android.systemui.SCREENSHOT"), "com.android.systemui.permission.SELF", (Handler) null);
        this.mInputMonitor = ((InputManager) createWindowContext.getSystemService(InputManager.class)).monitorGestureInput("clipboard overlay", 0);
        this.mInputEventReceiver = new InputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.getMainLooper()) {
            public final void onInputEvent(InputEvent inputEvent) {
                if (inputEvent instanceof MotionEvent) {
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    if (motionEvent.getActionMasked() == 0) {
                        Region region = new Region();
                        Rect rect = new Rect();
                        ClipboardOverlayController.this.mPreviewBorder.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(ClipboardOverlayController.this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(ClipboardOverlayController.this.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        ClipboardOverlayController.this.mActionContainerBackground.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(ClipboardOverlayController.this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(ClipboardOverlayController.this.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        ClipboardOverlayController.this.mDismissButton.getBoundsOnScreen(rect);
                        region.op(rect, Region.Op.UNION);
                        if (!region.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                            ClipboardOverlayController.this.animateOut();
                        }
                    }
                }
                finishInputEvent(inputEvent, true);
            }
        };
        createWindowContext.sendBroadcast(new Intent("com.android.systemui.COPY"), "com.android.systemui.permission.SELF");
    }
}
