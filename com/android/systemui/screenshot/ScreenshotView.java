package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Looper;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.InputMonitor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.leanback.R$color;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda5;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda0;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda6;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.SwipeDismissHandler;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import com.google.android.setupcompat.util.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ScreenshotView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public HorizontalScrollView mActionsContainer;
    public ImageView mActionsContainerBackground;
    public LinearLayout mActionsView;
    public ImageView mBackgroundProtection;
    public ScreenshotViewCallback mCallbacks;
    public boolean mDirectionLTR;
    public FrameLayout mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public OverlayActionChip mEditChip;
    public final Interpolator mFastOutSlowIn;
    public final float mFixedSize;
    public InputChannelCompat$InputEventReceiver mInputEventReceiver;
    public Logger mInputMonitor;
    public int mNavMode;
    public boolean mOrientationPortrait;
    public String mPackageName;
    public PendingInteraction mPendingInteraction;
    public boolean mPendingSharedTransition;
    public OverlayActionChip mQuickShareChip;
    public final Resources mResources;
    public ImageView mScreenshotFlash;
    public ImageView mScreenshotPreview;
    public View mScreenshotPreviewBorder;
    public ScreenshotSelectorView mScreenshotSelectorView;
    public View mScreenshotStatic;
    public OverlayActionChip mScrollChip;
    public ImageView mScrollablePreview;
    public ImageView mScrollingScrim;
    public OverlayActionChip mShareChip;
    public boolean mShowScrollablePreview;
    public final ArrayList<OverlayActionChip> mSmartChips;
    public final GestureDetector mSwipeDetector;
    public SwipeDismissHandler mSwipeDismissHandler;
    public UiEventLogger mUiEventLogger;

    public enum PendingInteraction {
        PREVIEW,
        EDIT,
        SHARE,
        QUICK_SHARE
    }

    public interface ScreenshotViewCallback {
    }

    public ScreenshotView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(getTouchRegion(true));
    }

    public final void startSharedTransition(ScreenshotController.SavedImageData.ActionTransition actionTransition) {
        try {
            this.mPendingSharedTransition = true;
            actionTransition.action.actionIntent.send();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda4(this));
            ofFloat.setDuration(600);
            ofFloat.start();
        } catch (PendingIntent.CanceledException e) {
            this.mPendingSharedTransition = false;
            ScreenDecorations$$ExternalSyntheticLambda2 screenDecorations$$ExternalSyntheticLambda2 = actionTransition.onCancelRunnable;
            if (screenDecorations$$ExternalSyntheticLambda2 != null) {
                screenDecorations$$ExternalSyntheticLambda2.run();
            }
            Log.e("Screenshot", "Intent cancelled", e);
        }
    }

    public ScreenshotView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void addQuickShareChip(Notification.Action action) {
        if (this.mPendingInteraction == null) {
            OverlayActionChip overlayActionChip = (OverlayActionChip) LayoutInflater.from(this.mContext).inflate(C1777R.layout.overlay_action_chip, this.mActionsView, false);
            this.mQuickShareChip = overlayActionChip;
            overlayActionChip.setText(action.title);
            this.mQuickShareChip.setIcon(action.getIcon(), false);
            this.mQuickShareChip.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda9(this, 0));
            this.mQuickShareChip.setAlpha(1.0f);
            this.mActionsView.addView(this.mQuickShareChip);
            this.mSmartChips.add(this.mQuickShareChip);
        }
    }

    public final Region getSwipeRegion() {
        Region region = new Region();
        Rect rect = new Rect();
        this.mScreenshotPreview.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mActionsContainerBackground.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mDismissButton.getBoundsOnScreen(rect);
        region.op(rect, Region.Op.UNION);
        return region;
    }

    public final void reset() {
        boolean z;
        SwipeDismissHandler swipeDismissHandler = this.mSwipeDismissHandler;
        Objects.requireNonNull(swipeDismissHandler);
        ValueAnimator valueAnimator = swipeDismissHandler.mDismissAnimation;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            swipeDismissHandler.mDismissAnimation.cancel();
        }
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        this.mScreenshotPreview.setImageDrawable((Drawable) null);
        this.mScreenshotPreview.setVisibility(4);
        this.mScreenshotPreviewBorder.setAlpha(0.0f);
        this.mPendingSharedTransition = false;
        this.mActionsContainerBackground.setVisibility(8);
        this.mActionsContainer.setVisibility(8);
        this.mBackgroundProtection.setAlpha(0.0f);
        this.mDismissButton.setVisibility(8);
        this.mScrollingScrim.setVisibility(8);
        this.mScrollablePreview.setVisibility(8);
        this.mScreenshotStatic.setTranslationX(0.0f);
        this.mScreenshotPreview.setContentDescription(this.mContext.getResources().getString(C1777R.string.screenshot_preview_description));
        this.mScreenshotPreview.setOnClickListener((View.OnClickListener) null);
        this.mShareChip.setOnClickListener((View.OnClickListener) null);
        this.mScrollingScrim.setVisibility(8);
        this.mEditChip.setOnClickListener((View.OnClickListener) null);
        this.mShareChip.setIsPending(false);
        this.mEditChip.setIsPending(false);
        this.mPendingInteraction = null;
        Iterator<OverlayActionChip> it = this.mSmartChips.iterator();
        while (it.hasNext()) {
            this.mActionsView.removeView(it.next());
        }
        this.mSmartChips.clear();
        this.mQuickShareChip = null;
        setAlpha(1.0f);
        ScreenshotSelectorView screenshotSelectorView = this.mScreenshotSelectorView;
        Objects.requireNonNull(screenshotSelectorView);
        if (screenshotSelectorView.mSelectionRect != null) {
            screenshotSelectorView.mStartPoint = null;
            screenshotSelectorView.mSelectionRect = null;
        }
    }

    public final void restoreNonScrollingUi() {
        this.mScrollChip.setVisibility(8);
        this.mScrollablePreview.setVisibility(8);
        this.mScrollingScrim.setVisibility(8);
        if (this.mAccessibilityManager.isEnabled()) {
            this.mDismissButton.setVisibility(0);
        }
        this.mActionsContainer.setVisibility(0);
        this.mBackgroundProtection.setVisibility(0);
        this.mActionsContainerBackground.setVisibility(0);
        this.mScreenshotPreviewBorder.setVisibility(0);
        this.mScreenshotPreview.setVisibility(0);
        ScreenshotController.C10753 r2 = (ScreenshotController.C10753) this.mCallbacks;
        Objects.requireNonNull(r2);
        ScreenshotController.this.mScreenshotHandler.resetTimeout();
    }

    public final void setChipIntents(ScreenshotController.SavedImageData savedImageData) {
        this.mShareChip.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda12(this, savedImageData, 0));
        this.mEditChip.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda11(this, savedImageData, 0));
        this.mScreenshotPreview.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda14(this, savedImageData));
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            overlayActionChip.setOnClickListener(new OverlayActionChip$$ExternalSyntheticLambda0(savedImageData.quickShareAction.actionIntent, new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 3)));
        }
        PendingInteraction pendingInteraction = this.mPendingInteraction;
        if (pendingInteraction != null) {
            int ordinal = pendingInteraction.ordinal();
            if (ordinal == 0) {
                this.mScreenshotPreview.callOnClick();
            } else if (ordinal == 1) {
                this.mEditChip.callOnClick();
            } else if (ordinal == 2) {
                this.mShareChip.callOnClick();
            } else if (ordinal == 3) {
                this.mQuickShareChip.callOnClick();
            }
        } else {
            LayoutInflater from = LayoutInflater.from(this.mContext);
            Iterator it = savedImageData.smartActions.iterator();
            while (it.hasNext()) {
                Notification.Action action = (Notification.Action) it.next();
                OverlayActionChip overlayActionChip2 = (OverlayActionChip) from.inflate(C1777R.layout.overlay_action_chip, this.mActionsView, false);
                overlayActionChip2.setText(action.title);
                overlayActionChip2.setIcon(action.getIcon(), false);
                overlayActionChip2.setOnClickListener(new OverlayActionChip$$ExternalSyntheticLambda0(action.actionIntent, new SuggestController$$ExternalSyntheticLambda1(this, 3)));
                overlayActionChip2.setAlpha(1.0f);
                this.mActionsView.addView(overlayActionChip2);
                this.mSmartChips.add(overlayActionChip2);
            }
        }
    }

    public final void showScrollChip(String str, BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda5) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_IMPRESSION, 0, str);
        this.mScrollChip.setVisibility(0);
        this.mScrollChip.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda6(this, str, bubbleController$$ExternalSyntheticLambda5, 1));
    }

    public final void stopInputListening() {
        Logger logger = this.mInputMonitor;
        if (logger != null) {
            ((InputMonitor) logger.prefix).dispose();
            this.mInputMonitor = null;
        }
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.mInputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.mReceiver.dispose();
            this.mInputEventReceiver = null;
        }
    }

    public final void updateInsets(WindowInsets windowInsets) {
        boolean z = true;
        if (this.mContext.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        this.mOrientationPortrait = z;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mScreenshotStatic.getLayoutParams();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        if (displayCutout == null) {
            layoutParams.setMargins(0, 0, 0, insets.bottom);
        } else {
            Insets waterfallInsets = displayCutout.getWaterfallInsets();
            if (this.mOrientationPortrait) {
                layoutParams.setMargins(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom)));
            } else {
                layoutParams.setMargins(Math.max(displayCutout.getSafeInsetLeft(), waterfallInsets.left), waterfallInsets.top, Math.max(displayCutout.getSafeInsetRight(), waterfallInsets.right), Math.max(insets.bottom, waterfallInsets.bottom));
            }
        }
        this.mScreenshotStatic.setLayoutParams(layoutParams);
        this.mScreenshotStatic.requestLayout();
    }

    public final void updateOrientation(WindowInsets windowInsets) {
        boolean z = true;
        if (this.mContext.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        this.mOrientationPortrait = z;
        updateInsets(windowInsets);
        ViewGroup.LayoutParams layoutParams = this.mScreenshotPreview.getLayoutParams();
        if (this.mOrientationPortrait) {
            layoutParams.width = (int) this.mFixedSize;
            layoutParams.height = -2;
            this.mScreenshotPreview.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            layoutParams.width = -2;
            layoutParams.height = (int) this.mFixedSize;
            this.mScreenshotPreview.setScaleType(ImageView.ScaleType.FIT_END);
        }
        this.mScreenshotPreview.setLayoutParams(layoutParams);
    }

    public static /* synthetic */ void $r8$lambda$b5uTZBQem2CWFC7azy4AO9g0ukw(ScreenshotView screenshotView) {
        Objects.requireNonNull(screenshotView);
        screenshotView.mShareChip.setIsPending(true);
        screenshotView.mEditChip.setIsPending(false);
        OverlayActionChip overlayActionChip = screenshotView.mQuickShareChip;
        if (overlayActionChip != null) {
            overlayActionChip.setIsPending(false);
        }
        screenshotView.mPendingInteraction = PendingInteraction.SHARE;
    }

    public ScreenshotView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final ValueAnimator createScreenshotActionsShadeAnimation() {
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException unused) {
        }
        ArrayList arrayList = new ArrayList();
        this.mShareChip.setContentDescription(this.mContext.getString(C1777R.string.screenshot_share_description));
        this.mShareChip.setIcon(Icon.createWithResource(this.mContext, C1777R.C1778drawable.ic_screenshot_share), true);
        this.mShareChip.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda0(this, 1));
        arrayList.add(this.mShareChip);
        this.mEditChip.setContentDescription(this.mContext.getString(C1777R.string.screenshot_edit_description));
        this.mEditChip.setIcon(Icon.createWithResource(this.mContext, C1777R.C1778drawable.ic_screenshot_edit), true);
        this.mEditChip.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda13(this));
        arrayList.add(this.mEditChip);
        this.mScreenshotPreview.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda10(this, 0));
        this.mScrollChip.setText(this.mContext.getString(C1777R.string.screenshot_scroll_label));
        this.mScrollChip.setIcon(Icon.createWithResource(this.mContext, C1777R.C1778drawable.ic_screenshot_scroll), true);
        arrayList.add(this.mScrollChip);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mActionsView.getChildAt(0).getLayoutParams();
        layoutParams.setMarginEnd(0);
        this.mActionsView.getChildAt(0).setLayoutParams(layoutParams);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.setDuration(400);
        this.mActionsContainer.setAlpha(0.0f);
        this.mActionsContainerBackground.setAlpha(0.0f);
        this.mActionsContainer.setVisibility(0);
        this.mActionsContainerBackground.setVisibility(0);
        ofFloat.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda8(this, arrayList));
        return ofFloat;
    }

    public final Region getTouchRegion(boolean z) {
        Region swipeRegion = getSwipeRegion();
        if (z && this.mScrollingScrim.getVisibility() == 0) {
            Rect rect = new Rect();
            this.mScrollingScrim.getBoundsOnScreen(rect);
            swipeRegion.op(rect, Region.Op.UNION);
        }
        if (R$color.isGesturalMode(this.mNavMode)) {
            Insets insets = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
            Rect rect2 = new Rect(0, 0, insets.left, this.mDisplayMetrics.heightPixels);
            swipeRegion.op(rect2, Region.Op.UNION);
            DisplayMetrics displayMetrics = this.mDisplayMetrics;
            int i = displayMetrics.widthPixels;
            rect2.set(i - insets.right, 0, i, displayMetrics.heightPixels);
            swipeRegion.op(rect2, Region.Op.UNION);
        }
        return swipeRegion;
    }

    public final void onFinishInflate() {
        boolean z;
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.screenshot_scrolling_scrim);
        Objects.requireNonNull(imageView);
        ImageView imageView2 = imageView;
        this.mScrollingScrim = imageView;
        View findViewById = findViewById(C1777R.C1779id.screenshot_static);
        Objects.requireNonNull(findViewById);
        View view = findViewById;
        this.mScreenshotStatic = findViewById;
        ImageView imageView3 = (ImageView) findViewById(C1777R.C1779id.screenshot_preview);
        Objects.requireNonNull(imageView3);
        ImageView imageView4 = imageView3;
        this.mScreenshotPreview = imageView3;
        View findViewById2 = findViewById(C1777R.C1779id.screenshot_preview_border);
        Objects.requireNonNull(findViewById2);
        View view2 = findViewById2;
        this.mScreenshotPreviewBorder = findViewById2;
        this.mScreenshotPreview.setClipToOutline(true);
        ImageView imageView5 = (ImageView) findViewById(C1777R.C1779id.screenshot_actions_container_background);
        Objects.requireNonNull(imageView5);
        ImageView imageView6 = imageView5;
        this.mActionsContainerBackground = imageView5;
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(C1777R.C1779id.screenshot_actions_container);
        Objects.requireNonNull(horizontalScrollView);
        HorizontalScrollView horizontalScrollView2 = horizontalScrollView;
        this.mActionsContainer = horizontalScrollView;
        LinearLayout linearLayout = (LinearLayout) findViewById(C1777R.C1779id.screenshot_actions);
        Objects.requireNonNull(linearLayout);
        LinearLayout linearLayout2 = linearLayout;
        this.mActionsView = linearLayout;
        ImageView imageView7 = (ImageView) findViewById(C1777R.C1779id.screenshot_actions_background);
        Objects.requireNonNull(imageView7);
        ImageView imageView8 = imageView7;
        this.mBackgroundProtection = imageView7;
        FrameLayout frameLayout = (FrameLayout) findViewById(C1777R.C1779id.screenshot_dismiss_button);
        Objects.requireNonNull(frameLayout);
        FrameLayout frameLayout2 = frameLayout;
        this.mDismissButton = frameLayout;
        ImageView imageView9 = (ImageView) findViewById(C1777R.C1779id.screenshot_scrollable_preview);
        Objects.requireNonNull(imageView9);
        ImageView imageView10 = imageView9;
        this.mScrollablePreview = imageView9;
        ImageView imageView11 = (ImageView) findViewById(C1777R.C1779id.screenshot_flash);
        Objects.requireNonNull(imageView11);
        ImageView imageView12 = imageView11;
        this.mScreenshotFlash = imageView11;
        ScreenshotSelectorView screenshotSelectorView = (ScreenshotSelectorView) findViewById(C1777R.C1779id.screenshot_selector);
        Objects.requireNonNull(screenshotSelectorView);
        ScreenshotSelectorView screenshotSelectorView2 = screenshotSelectorView;
        this.mScreenshotSelectorView = screenshotSelectorView;
        OverlayActionChip overlayActionChip = (OverlayActionChip) this.mActionsContainer.findViewById(C1777R.C1779id.screenshot_share_chip);
        Objects.requireNonNull(overlayActionChip);
        OverlayActionChip overlayActionChip2 = overlayActionChip;
        this.mShareChip = overlayActionChip;
        OverlayActionChip overlayActionChip3 = (OverlayActionChip) this.mActionsContainer.findViewById(C1777R.C1779id.screenshot_edit_chip);
        Objects.requireNonNull(overlayActionChip3);
        OverlayActionChip overlayActionChip4 = overlayActionChip3;
        this.mEditChip = overlayActionChip3;
        OverlayActionChip overlayActionChip5 = (OverlayActionChip) this.mActionsContainer.findViewById(C1777R.C1779id.screenshot_scroll_chip);
        Objects.requireNonNull(overlayActionChip5);
        OverlayActionChip overlayActionChip6 = overlayActionChip5;
        this.mScrollChip = overlayActionChip5;
        int dpToPx = (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 12.0f);
        this.mScreenshotPreview.setTouchDelegate(new TouchDelegate(new Rect(dpToPx, dpToPx, dpToPx, dpToPx), this.mScreenshotPreview));
        this.mActionsContainerBackground.setTouchDelegate(new TouchDelegate(new Rect(dpToPx, dpToPx, dpToPx, dpToPx), this.mActionsContainerBackground));
        setFocusable(true);
        this.mScreenshotSelectorView.setFocusable(true);
        this.mScreenshotSelectorView.setFocusableInTouchMode(true);
        boolean z2 = false;
        this.mActionsContainer.setScrollX(0);
        this.mNavMode = getResources().getInteger(17694878);
        if (getResources().getConfiguration().orientation == 1) {
            z = true;
        } else {
            z = false;
        }
        this.mOrientationPortrait = z;
        if (getResources().getConfiguration().getLayoutDirection() == 0) {
            z2 = true;
        }
        this.mDirectionLTR = z2;
        setFocusableInTouchMode(true);
        requestFocus();
        this.mSwipeDismissHandler = new SwipeDismissHandler(this.mContext, this.mScreenshotStatic, new SwipeDismissHandler.SwipeDismissCallbacks() {
            public final void onDismiss() {
                ScreenshotView screenshotView = ScreenshotView.this;
                screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SWIPE_DISMISSED, 0, screenshotView.mPackageName);
                ScreenshotController.C10753 r4 = (ScreenshotController.C10753) ScreenshotView.this.mCallbacks;
                Objects.requireNonNull(r4);
                ScreenshotController.this.finishDismiss();
            }

            public final void onInteraction() {
                ScreenshotController.C10753 r0 = (ScreenshotController.C10753) ScreenshotView.this.mCallbacks;
                Objects.requireNonNull(r0);
                ScreenshotController.this.mScreenshotHandler.resetTimeout();
            }
        });
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!getSwipeRegion().contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
            return false;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mSwipeDismissHandler.onTouch(this, motionEvent);
        }
        return this.mSwipeDetector.onTouchEvent(motionEvent);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        new AccelerateInterpolator();
        this.mPackageName = "";
        this.mSmartChips = new ArrayList<>();
        Resources resources = this.mContext.getResources();
        this.mResources = resources;
        this.mFixedSize = (float) resources.getDimensionPixelSize(C1777R.dimen.overlay_x_scale);
        this.mFastOutSlowIn = AnimationUtils.loadInterpolator(this.mContext, 17563661);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mAccessibilityManager = AccessibilityManager.getInstance(this.mContext);
        GestureDetector gestureDetector = new GestureDetector(this.mContext, new GestureDetector.SimpleOnGestureListener() {
            public final Rect mActionsRect = new Rect();

            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                ScreenshotView.this.mActionsContainer.getBoundsOnScreen(this.mActionsRect);
                if (!this.mActionsRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY()) || !ScreenshotView.this.mActionsContainer.canScrollHorizontally((int) f)) {
                    return true;
                }
                return false;
            }
        });
        this.mSwipeDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public final void onViewAttachedToWindow(View view) {
                ScreenshotView screenshotView = ScreenshotView.this;
                int i = ScreenshotView.$r8$clinit;
                Objects.requireNonNull(screenshotView);
                screenshotView.stopInputListening();
                Logger logger = new Logger("Screenshot", 0);
                screenshotView.mInputMonitor = logger;
                screenshotView.mInputEventReceiver = logger.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new ScreenshotView$$ExternalSyntheticLambda15(screenshotView));
            }

            public final void onViewDetachedFromWindow(View view) {
                ScreenshotView.this.stopInputListening();
            }
        });
    }

    static {
        Class<ScreenshotView> cls = ScreenshotView.class;
    }
}
