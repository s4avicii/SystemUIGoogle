package com.android.systemui.volume;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.media.AudioSystem;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.text.InputFilter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import com.android.systemui.Prefs;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda2;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda1;
import com.android.systemui.util.AlphaTintDrawableWrapper;
import com.android.systemui.util.RoundedCornerProgressDrawable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class VolumeDialogImpl implements VolumeDialog, ConfigurationController.ConfigurationListener, ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final String TAG = Util.logTag(VolumeDialogImpl.class);
    public final Accessibility mAccessibility;
    public final AccessibilityManagerWrapper mAccessibilityMgr;
    public int mActiveStream;
    public final ActivityManager mActivityManager;
    public final ActivityStarter mActivityStarter;
    public final ValueAnimator mAnimateUpBackgroundToMatchDrawer = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
    public boolean mAutomute;
    public final boolean mChangeVolumeRowTintWhenInactive;
    public boolean mConfigChanged;
    public ConfigurableTexts mConfigurableTexts;
    public final ConfigurationController mConfigurationController;
    public final ContextThemeWrapper mContext;
    public final VolumeDialogController mController;
    public final C17406 mControllerCallbackH;
    public VolumeDialogImpl$$ExternalSyntheticLambda13 mCrossWindowBlurEnabledListener;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public CustomDialog mDialog;
    public int mDialogCornerRadius;
    public final int mDialogHideAnimationDurationMs;
    public ViewGroup mDialogRowsView;
    public BackgroundBlurDrawable mDialogRowsViewBackground;
    public ViewGroup mDialogRowsViewContainer;
    public final int mDialogShowAnimationDurationMs;
    public ViewGroup mDialogView;
    public int mDialogWidth;
    public final SparseBooleanArray mDynamic;
    public final C1741H mHandler = new C1741H();
    public boolean mHasSeenODICaptionsTooltip;
    public boolean mHovering;
    public boolean mIsAnimatingDismiss;
    public boolean mIsRingerDrawerOpen;
    public final KeyguardManager mKeyguard;
    public final MediaOutputDialogFactory mMediaOutputDialogFactory;
    public CaptionsToggleImageButton mODICaptionsIcon;
    public View mODICaptionsTooltipView;
    public ViewStub mODICaptionsTooltipViewStub;
    public ViewGroup mODICaptionsView;
    public int mPrevActiveStream;
    public ViewGroup mRinger;
    public View mRingerAndDrawerContainer;
    public Drawable mRingerAndDrawerContainerBackground;
    public int mRingerCount;
    public float mRingerDrawerClosedAmount;
    public ViewGroup mRingerDrawerContainer;
    public ImageView mRingerDrawerIconAnimatingDeselected;
    public ImageView mRingerDrawerIconAnimatingSelected;
    public final ValueAnimator mRingerDrawerIconColorAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
    public int mRingerDrawerItemSize;
    public ViewGroup mRingerDrawerMute;
    public ImageView mRingerDrawerMuteIcon;
    public ViewGroup mRingerDrawerNewSelectionBg;
    public ViewGroup mRingerDrawerNormal;
    public ImageView mRingerDrawerNormalIcon;
    public ViewGroup mRingerDrawerVibrate;
    public ImageView mRingerDrawerVibrateIcon;
    public ImageButton mRingerIcon;
    public int mRingerRowsPadding;
    public final ArrayList mRows;
    public SafetyWarningDialog mSafetyWarning;
    public final Object mSafetyWarningLock;
    public ViewGroup mSelectedRingerContainer;
    public ImageView mSelectedRingerIcon;
    public ImageButton mSettingsIcon;
    public View mSettingsView;
    public boolean mShowA11yStream;
    public boolean mShowActiveStreamOnly;
    public final boolean mShowLowMediaVolumeIcon;
    public boolean mShowVibrate;
    public boolean mShowing;
    public boolean mSilentMode;
    public VolumeDialogController.State mState;
    public View mTopContainer;
    public final Region mTouchableRegion = new Region();
    public final boolean mUseBackgroundBlur;
    public Window mWindow;
    public FrameLayout mZenIcon;

    public final class Accessibility extends View.AccessibilityDelegate {
        public Accessibility() {
        }

        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            VolumeDialogImpl.this.rescheduleTimeoutH();
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            List<CharSequence> text = accessibilityEvent.getText();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            Objects.requireNonNull(volumeDialogImpl);
            text.add(volumeDialogImpl.mContext.getString(C1777R.string.volume_dialog_title, new Object[]{volumeDialogImpl.getStreamLabelH(volumeDialogImpl.getActiveRow().f88ss)}));
            return true;
        }
    }

    public final class CustomDialog extends Dialog {
        public final void onStart() {
            setCanceledOnTouchOutside(true);
            super.onStart();
        }

        public CustomDialog(ContextThemeWrapper contextThemeWrapper) {
            super(contextThemeWrapper, C1777R.style.volume_dialog_theme);
        }

        public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
            VolumeDialogImpl.this.rescheduleTimeoutH();
            return super.dispatchTouchEvent(motionEvent);
        }

        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (!VolumeDialogImpl.this.mShowing || motionEvent.getAction() != 4) {
                return false;
            }
            VolumeDialogImpl.this.dismissH(1);
            return true;
        }

        public final void onStop() {
            super.onStop();
            VolumeDialogImpl.this.mHandler.sendEmptyMessage(4);
        }
    }

    /* renamed from: com.android.systemui.volume.VolumeDialogImpl$H */
    public final class C1741H extends Handler {
        public C1741H() {
            super(Looper.getMainLooper());
        }

        public final void handleMessage(Message message) {
            boolean z;
            switch (message.what) {
                case 1:
                    VolumeDialogImpl.m287$$Nest$mshowH(VolumeDialogImpl.this, message.arg1);
                    return;
                case 2:
                    VolumeDialogImpl.this.dismissH(message.arg1);
                    return;
                case 3:
                    VolumeDialogImpl.this.recheckH((VolumeRow) message.obj);
                    return;
                case 4:
                    VolumeDialogImpl.this.recheckH((VolumeRow) null);
                    return;
                case 5:
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    int i = message.arg1;
                    if (message.arg2 != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Objects.requireNonNull(volumeDialogImpl);
                    Iterator it = volumeDialogImpl.mRows.iterator();
                    while (it.hasNext()) {
                        VolumeRow volumeRow = (VolumeRow) it.next();
                        if (volumeRow.stream == i) {
                            volumeRow.important = z;
                            return;
                        }
                    }
                    return;
                case FalsingManager.VERSION:
                    VolumeDialogImpl.this.rescheduleTimeoutH();
                    return;
                case 7:
                    VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                    volumeDialogImpl2.onStateChangedH(volumeDialogImpl2.mState);
                    return;
                default:
                    return;
            }
        }
    }

    public class RingerDrawerItemClickListener implements View.OnClickListener {
        public final int mClickedRingerMode;

        public RingerDrawerItemClickListener(int i) {
            this.mClickedRingerMode = i;
        }

        public final void onClick(View view) {
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if (volumeDialogImpl.mIsRingerDrawerOpen) {
                volumeDialogImpl.setRingerMode(this.mClickedRingerMode);
                VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                volumeDialogImpl2.mRingerDrawerIconAnimatingSelected = volumeDialogImpl2.getDrawerIconViewForMode(this.mClickedRingerMode);
                VolumeDialogImpl volumeDialogImpl3 = VolumeDialogImpl.this;
                volumeDialogImpl3.mRingerDrawerIconAnimatingDeselected = volumeDialogImpl3.getDrawerIconViewForMode(volumeDialogImpl3.mState.ringerModeInternal);
                VolumeDialogImpl.this.mRingerDrawerIconColorAnimator.start();
                VolumeDialogImpl.this.mSelectedRingerContainer.setVisibility(4);
                VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.setAlpha(1.0f);
                VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().setInterpolator(Interpolators.ACCELERATE_DECELERATE).setDuration(175).withEndAction(new StatusBar$$ExternalSyntheticLambda18(this, 12));
                if (!VolumeDialogImpl.this.isLandscape()) {
                    VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().translationY(VolumeDialogImpl.this.getTranslationInDrawerForRingerMode(this.mClickedRingerMode)).start();
                } else {
                    VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().translationX(VolumeDialogImpl.this.getTranslationInDrawerForRingerMode(this.mClickedRingerMode)).start();
                }
            }
        }
    }

    public static class VolumeRow {
        public ObjectAnimator anim;
        public int animTargetProgress;
        public boolean defaultStream;
        public FrameLayout dndIcon;
        public TextView header;
        public ImageButton icon;
        public int iconMuteRes;
        public int iconRes;
        public int iconState;
        public boolean important;
        public int lastAudibleLevel = 1;
        public TextView number;
        public int requestedLevel = -1;
        public SeekBar slider;
        public AlphaTintDrawableWrapper sliderProgressIcon;
        public Drawable sliderProgressSolid;

        /* renamed from: ss */
        public VolumeDialogController.StreamState f88ss;
        public int stream;
        public boolean tracking;
        public long userAttempt;
        public View view;
    }

    public final class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final VolumeRow mRow;

        public VolumeSeekBarChangeListener(VolumeRow volumeRow) {
            this.mRow = volumeRow;
        }

        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            int i2;
            if (this.mRow.f88ss != null) {
                if (C1716D.BUG) {
                    String str = VolumeDialogImpl.TAG;
                    Log.d(str, AudioSystem.streamToString(this.mRow.stream) + " onProgressChanged " + i + " fromUser=" + z);
                }
                if (z) {
                    int i3 = this.mRow.f88ss.levelMin;
                    if (i3 > 0 && i < (i2 = i3 * 100)) {
                        seekBar.setProgress(i2);
                        i = i2;
                    }
                    int impliedLevel = VolumeDialogImpl.getImpliedLevel(seekBar, i);
                    VolumeRow volumeRow = this.mRow;
                    VolumeDialogController.StreamState streamState = volumeRow.f88ss;
                    if (streamState.level != impliedLevel || (streamState.muted && impliedLevel > 0)) {
                        volumeRow.userAttempt = SystemClock.uptimeMillis();
                        VolumeRow volumeRow2 = this.mRow;
                        if (volumeRow2.requestedLevel != impliedLevel) {
                            VolumeDialogImpl.this.mController.setActiveStream(volumeRow2.stream);
                            VolumeDialogImpl.this.mController.setStreamVolume(this.mRow.stream, impliedLevel);
                            VolumeRow volumeRow3 = this.mRow;
                            volumeRow3.requestedLevel = impliedLevel;
                            Events.writeEvent(9, Integer.valueOf(volumeRow3.stream), Integer.valueOf(impliedLevel));
                        }
                    }
                }
            }
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
            if (C1716D.BUG) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("onStartTrackingTouch "), this.mRow.stream, VolumeDialogImpl.TAG);
            }
            VolumeDialogImpl.this.mController.setActiveStream(this.mRow.stream);
            this.mRow.tracking = true;
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
            if (C1716D.BUG) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("onStopTrackingTouch "), this.mRow.stream, VolumeDialogImpl.TAG);
            }
            VolumeRow volumeRow = this.mRow;
            volumeRow.tracking = false;
            volumeRow.userAttempt = SystemClock.uptimeMillis();
            int impliedLevel = VolumeDialogImpl.getImpliedLevel(seekBar, seekBar.getProgress());
            Events.writeEvent(16, Integer.valueOf(this.mRow.stream), Integer.valueOf(impliedLevel));
            VolumeRow volumeRow2 = this.mRow;
            if (volumeRow2.f88ss.level != impliedLevel) {
                C1741H h = VolumeDialogImpl.this.mHandler;
                h.sendMessageDelayed(h.obtainMessage(3, volumeRow2), 1000);
            }
        }
    }

    public final ImageView getDrawerIconViewForMode(int i) {
        if (i == 1) {
            return this.mRingerDrawerVibrateIcon;
        }
        if (i == 0) {
            return this.mRingerDrawerMuteIcon;
        }
        return this.mRingerDrawerNormalIcon;
    }

    public final float getTranslationInDrawerForRingerMode(int i) {
        int i2;
        if (i == 1) {
            i2 = (-this.mRingerDrawerItemSize) * 2;
        } else if (i != 0) {
            return 0.0f;
        } else {
            i2 = -this.mRingerDrawerItemSize;
        }
        return (float) i2;
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        this.mTouchableRegion.setEmpty();
        for (int i = 0; i < this.mDialogView.getChildCount(); i++) {
            unionViewBoundstoTouchableRegion(this.mDialogView.getChildAt(i));
        }
        View view = this.mODICaptionsTooltipView;
        if (view != null && view.getVisibility() == 0) {
            unionViewBoundstoTouchableRegion(this.mODICaptionsTooltipView);
        }
        internalInsetsInfo.touchableRegion.set(this.mTouchableRegion);
    }

    public final void setRingerMode(int i) {
        VibrationEffect vibrationEffect;
        Events.writeEvent(18, Integer.valueOf(i));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Secure.putInt(contentResolver, "manual_ringer_toggle_count", Settings.Secure.getInt(contentResolver, "manual_ringer_toggle_count", 0) + 1);
        updateRingerH();
        String str = null;
        if (i == 0) {
            vibrationEffect = VibrationEffect.get(0);
        } else if (i != 2) {
            vibrationEffect = VibrationEffect.get(1);
        } else {
            this.mController.scheduleTouchFeedback();
            vibrationEffect = null;
        }
        if (vibrationEffect != null) {
            this.mController.vibrate(vibrationEffect);
        }
        this.mController.setRingerMode(i, false);
        ContextThemeWrapper contextThemeWrapper = this.mContext;
        int i2 = contextThemeWrapper.getSharedPreferences(contextThemeWrapper.getPackageName(), 0).getInt("RingerGuidanceCount", 0);
        if (i2 <= 12) {
            if (i == 0) {
                str = this.mContext.getString(17041672);
            } else if (i != 2) {
                str = this.mContext.getString(17041673);
            } else {
                VolumeDialogController.StreamState streamState = this.mState.states.get(2);
                if (streamState != null) {
                    str = this.mContext.getString(C1777R.string.volume_dialog_ringer_guidance_ring, new Object[]{NumberFormat.getPercentInstance().format(((double) ((long) streamState.level)) / ((double) ((long) streamState.levelMax)))});
                }
            }
            Toast.makeText(this.mContext, str, 0).show();
            ContextThemeWrapper contextThemeWrapper2 = this.mContext;
            contextThemeWrapper2.getSharedPreferences(contextThemeWrapper2.getPackageName(), 0).edit().putInt("RingerGuidanceCount", i2 + 1).apply();
        }
    }

    public final void unionViewBoundstoTouchableRegion(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        float f = (float) iArr[0];
        float f2 = (float) iArr[1];
        if (view == this.mTopContainer && !this.mIsRingerDrawerOpen) {
            if (!isLandscape()) {
                f2 += (float) getRingerDrawerOpenExtraSize();
            } else {
                f += (float) getRingerDrawerOpenExtraSize();
            }
        }
        this.mTouchableRegion.op((int) f, (int) f2, view.getWidth() + iArr[0], view.getHeight() + iArr[1], Region.Op.UNION);
    }

    public final void addAccessibilityDescription(ImageButton imageButton, int i, final String str) {
        int i2;
        ContextThemeWrapper contextThemeWrapper = this.mContext;
        if (i == 0) {
            i2 = C1777R.string.volume_ringer_status_silent;
        } else if (i != 1) {
            i2 = C1777R.string.volume_ringer_status_normal;
        } else {
            i2 = C1777R.string.volume_ringer_status_vibrate;
        }
        imageButton.setContentDescription(contextThemeWrapper.getString(i2));
        imageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, str));
            }
        });
    }

    public final void addRow$1(int i, int i2, int i3, boolean z, boolean z2) {
        if (C1716D.BUG) {
            String str = TAG;
            Slog.d(str, "Adding row for stream " + i);
        }
        VolumeRow volumeRow = new VolumeRow();
        initRow(volumeRow, i, i2, i3, z, z2);
        this.mDialogRowsView.addView(volumeRow.view);
        this.mRows.add(volumeRow);
    }

    public final void checkODICaptionsTooltip(boolean z) {
        boolean z2 = this.mHasSeenODICaptionsTooltip;
        if (!z2 && !z && this.mODICaptionsTooltipViewStub != null) {
            this.mController.getCaptionsComponentState(true);
        } else if (z2 && z && this.mODICaptionsTooltipView != null) {
            hideCaptionsTooltip();
        }
    }

    public final void destroy() {
        this.mController.removeCallback(this.mControllerCallbackH);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        this.mConfigurationController.removeCallback(this);
    }

    public final void dismissH(int i) {
        boolean z;
        if (C1716D.BUG) {
            String str = TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mDialog.dismiss() reason: ");
            m.append(Events.DISMISS_REASONS[i]);
            m.append(" from: ");
            m.append(Debug.getCaller());
            Log.d(str, m.toString());
        }
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(1);
        if (!this.mIsAnimatingDismiss) {
            this.mIsAnimatingDismiss = true;
            this.mDialogView.animate().cancel();
            if (this.mShowing) {
                this.mShowing = false;
                Events.writeEvent(1, Integer.valueOf(i));
            }
            this.mDialogView.setTranslationX(0.0f);
            this.mDialogView.setAlpha(1.0f);
            ViewPropertyAnimator withEndAction = this.mDialogView.animate().alpha(0.0f).setDuration((long) this.mDialogHideAnimationDurationMs).setInterpolator(new SystemUIInterpolators$LogAccelerateInterpolator()).withEndAction(new TaskView$$ExternalSyntheticLambda3(this, 6));
            if (this.mContext.getDisplay().getRotation() != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                withEndAction.translationX(((float) this.mDialogView.getWidth()) / 2.0f);
            }
            withEndAction.start();
            checkODICaptionsTooltip(true);
            this.mController.notifyVisible(false);
            synchronized (this.mSafetyWarningLock) {
                if (this.mSafetyWarning != null) {
                    if (C1716D.BUG) {
                        Log.d(TAG, "SafetyWarning dismissed");
                    }
                    this.mSafetyWarning.dismiss();
                }
            }
        }
    }

    public final VolumeRow getActiveRow() {
        Iterator it = this.mRows.iterator();
        while (it.hasNext()) {
            VolumeRow volumeRow = (VolumeRow) it.next();
            if (volumeRow.stream == this.mActiveStream) {
                return volumeRow;
            }
        }
        Iterator it2 = this.mRows.iterator();
        while (it2.hasNext()) {
            VolumeRow volumeRow2 = (VolumeRow) it2.next();
            if (volumeRow2.stream == 3) {
                return volumeRow2;
            }
        }
        return (VolumeRow) this.mRows.get(0);
    }

    public final int getRingerDrawerOpenExtraSize() {
        return (this.mRingerCount - 1) * this.mRingerDrawerItemSize;
    }

    public final String getStreamLabelH(VolumeDialogController.StreamState streamState) {
        if (streamState == null) {
            return "";
        }
        String str = streamState.remoteLabel;
        if (str != null) {
            return str;
        }
        try {
            return this.mContext.getResources().getString(streamState.name);
        } catch (Resources.NotFoundException unused) {
            String str2 = TAG;
            Slog.e(str2, "Can't find translation for stream " + streamState);
            return "";
        }
    }

    public final void hideCaptionsTooltip() {
        View view = this.mODICaptionsTooltipView;
        if (view != null && view.getVisibility() == 0) {
            this.mODICaptionsTooltipView.animate().cancel();
            this.mODICaptionsTooltipView.setAlpha(1.0f);
            this.mODICaptionsTooltipView.animate().alpha(0.0f).setStartDelay(0).setDuration((long) this.mDialogHideAnimationDurationMs).withEndAction(new AccessPoint$$ExternalSyntheticLambda1(this, 9)).start();
        }
    }

    public final void hideRingerDrawer() {
        if (this.mRingerDrawerContainer != null && this.mIsRingerDrawerOpen) {
            getDrawerIconViewForMode(this.mState.ringerModeInternal).setVisibility(4);
            this.mRingerDrawerContainer.animate().alpha(0.0f).setDuration(250).setStartDelay(0).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda11(this, 0));
            if (!isLandscape()) {
                this.mRingerDrawerContainer.animate().translationY((float) (this.mRingerDrawerItemSize * 2)).start();
            } else {
                this.mRingerDrawerContainer.animate().translationX((float) (this.mRingerDrawerItemSize * 2)).start();
            }
            this.mAnimateUpBackgroundToMatchDrawer.setDuration(250);
            this.mAnimateUpBackgroundToMatchDrawer.setInterpolator(Interpolators.FAST_OUT_SLOW_IN_REVERSE);
            this.mAnimateUpBackgroundToMatchDrawer.reverse();
            this.mSelectedRingerContainer.animate().translationX(0.0f).translationY(0.0f).start();
            this.mSelectedRingerContainer.setContentDescription(this.mContext.getString(C1777R.string.volume_ringer_change));
            this.mIsRingerDrawerOpen = false;
        }
    }

    public final void initDialog() {
        this.mDialog = new CustomDialog(this.mContext);
        initDimens();
        this.mConfigurableTexts = new ConfigurableTexts(this.mContext);
        this.mHovering = false;
        this.mShowing = false;
        Window window = this.mDialog.getWindow();
        this.mWindow = window;
        window.requestFeature(1);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(65538);
        this.mWindow.addFlags(17563688);
        this.mWindow.addPrivateFlags(536870912);
        this.mWindow.setType(2020);
        this.mWindow.setWindowAnimations(16973828);
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.format = -3;
        Class<VolumeDialogImpl> cls = VolumeDialogImpl.class;
        attributes.setTitle("VolumeDialogImpl");
        attributes.windowAnimations = -1;
        attributes.gravity = this.mContext.getResources().getInteger(C1777R.integer.volume_dialog_gravity);
        this.mWindow.setAttributes(attributes);
        this.mWindow.setLayout(-2, -2);
        this.mDialog.setContentView(C1777R.layout.volume_dialog);
        ViewGroup viewGroup = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_dialog);
        this.mDialogView = viewGroup;
        viewGroup.setAlpha(0.0f);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.setOnShowListener(new VolumeDialogImpl$$ExternalSyntheticLambda3(this));
        this.mDialog.setOnDismissListener(new VolumeDialogImpl$$ExternalSyntheticLambda2(this));
        this.mDialogView.setOnHoverListener(new VolumeDialogImpl$$ExternalSyntheticLambda8(this));
        this.mDialogRowsView = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_dialog_rows);
        if (this.mUseBackgroundBlur) {
            this.mDialogView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public final void onViewAttachedToWindow(View view) {
                    VolumeDialogImpl.this.mWindow.getWindowManager().addCrossWindowBlurEnabledListener(VolumeDialogImpl.this.mCrossWindowBlurEnabledListener);
                    VolumeDialogImpl.this.mDialogRowsViewBackground = view.getViewRootImpl().createBackgroundBlurDrawable();
                    Resources resources = VolumeDialogImpl.this.mContext.getResources();
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    volumeDialogImpl.mDialogRowsViewBackground.setCornerRadius((float) volumeDialogImpl.mContext.getResources().getDimensionPixelSize(Utils.getThemeAttr(VolumeDialogImpl.this.mContext, 16844145)));
                    VolumeDialogImpl.this.mDialogRowsViewBackground.setBlurRadius(resources.getDimensionPixelSize(C1777R.dimen.volume_dialog_background_blur_radius));
                    VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                    volumeDialogImpl2.mDialogRowsView.setBackground(volumeDialogImpl2.mDialogRowsViewBackground);
                }

                public final void onViewDetachedFromWindow(View view) {
                    VolumeDialogImpl.this.mWindow.getWindowManager().removeCrossWindowBlurEnabledListener(VolumeDialogImpl.this.mCrossWindowBlurEnabledListener);
                }
            });
        }
        this.mDialogRowsViewContainer = (ViewGroup) this.mDialogView.findViewById(C1777R.C1779id.volume_dialog_rows_container);
        this.mTopContainer = this.mDialogView.findViewById(C1777R.C1779id.volume_dialog_top_container);
        View findViewById = this.mDialogView.findViewById(C1777R.C1779id.volume_ringer_and_drawer_container);
        this.mRingerAndDrawerContainer = findViewById;
        if (findViewById != null) {
            if (isLandscape()) {
                View view = this.mRingerAndDrawerContainer;
                view.setPadding(view.getPaddingLeft(), this.mRingerAndDrawerContainer.getPaddingTop(), this.mRingerAndDrawerContainer.getPaddingRight(), this.mRingerRowsPadding);
                this.mRingerAndDrawerContainer.setBackgroundDrawable(this.mContext.getDrawable(C1777R.C1778drawable.volume_background_top_rounded));
            }
            this.mRingerAndDrawerContainer.post(new CreateUserActivity$$ExternalSyntheticLambda1(this, 6));
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.ringer);
        this.mRinger = viewGroup2;
        if (viewGroup2 != null) {
            this.mRingerIcon = (ImageButton) viewGroup2.findViewById(C1777R.C1779id.ringer_icon);
            this.mZenIcon = (FrameLayout) this.mRinger.findViewById(C1777R.C1779id.dnd_icon);
        }
        this.mSelectedRingerIcon = (ImageView) this.mDialog.findViewById(C1777R.C1779id.volume_new_ringer_active_icon);
        this.mSelectedRingerContainer = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_new_ringer_active_icon_container);
        this.mRingerDrawerMute = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_mute);
        this.mRingerDrawerNormal = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_normal);
        this.mRingerDrawerVibrate = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_vibrate);
        this.mRingerDrawerMuteIcon = (ImageView) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_mute_icon);
        this.mRingerDrawerVibrateIcon = (ImageView) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_vibrate_icon);
        this.mRingerDrawerNormalIcon = (ImageView) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_normal_icon);
        this.mRingerDrawerNewSelectionBg = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_selection_background);
        ViewGroup viewGroup3 = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.volume_drawer_container);
        this.mRingerDrawerContainer = viewGroup3;
        if (viewGroup3 != null) {
            if (!this.mShowVibrate) {
                this.mRingerDrawerVibrate.setVisibility(8);
            }
            if (!isLandscape()) {
                ViewGroup viewGroup4 = this.mDialogView;
                viewGroup4.setPadding(viewGroup4.getPaddingLeft(), this.mDialogView.getPaddingTop(), this.mDialogView.getPaddingRight(), getRingerDrawerOpenExtraSize() + this.mDialogView.getPaddingBottom());
            } else {
                ViewGroup viewGroup5 = this.mDialogView;
                viewGroup5.setPadding(getRingerDrawerOpenExtraSize() + viewGroup5.getPaddingLeft(), this.mDialogView.getPaddingTop(), this.mDialogView.getPaddingRight(), this.mDialogView.getPaddingBottom());
            }
            ((LinearLayout) this.mRingerDrawerContainer.findViewById(C1777R.C1779id.volume_drawer_options)).setOrientation(isLandscape() ^ true ? 1 : 0);
            this.mSelectedRingerContainer.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda6(this));
            this.mRingerDrawerVibrate.setOnClickListener(new RingerDrawerItemClickListener(1));
            this.mRingerDrawerMute.setOnClickListener(new RingerDrawerItemClickListener(0));
            this.mRingerDrawerNormal.setOnClickListener(new RingerDrawerItemClickListener(2));
            this.mRingerDrawerIconColorAnimator.addUpdateListener(new VolumeDialogImpl$$ExternalSyntheticLambda1(this, Utils.getColorAttrDefaultColor(this.mContext, 16844002), Utils.getColorAttrDefaultColor(this.mContext, 16843829)));
            this.mRingerDrawerIconColorAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    VolumeDialogImpl.this.mRingerDrawerIconAnimatingDeselected.clearColorFilter();
                    VolumeDialogImpl.this.mRingerDrawerIconAnimatingSelected.clearColorFilter();
                }
            });
            this.mRingerDrawerIconColorAnimator.setDuration(175);
            this.mAnimateUpBackgroundToMatchDrawer.addUpdateListener(new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 0));
        }
        ViewGroup viewGroup6 = (ViewGroup) this.mDialog.findViewById(C1777R.C1779id.odi_captions);
        this.mODICaptionsView = viewGroup6;
        if (viewGroup6 != null) {
            this.mODICaptionsIcon = (CaptionsToggleImageButton) viewGroup6.findViewById(C1777R.C1779id.odi_captions_icon);
        }
        ViewStub viewStub = (ViewStub) this.mDialog.findViewById(C1777R.C1779id.odi_captions_tooltip_stub);
        this.mODICaptionsTooltipViewStub = viewStub;
        if (this.mHasSeenODICaptionsTooltip && viewStub != null) {
            this.mDialogView.removeView(viewStub);
            this.mODICaptionsTooltipViewStub = null;
        }
        this.mSettingsView = this.mDialog.findViewById(C1777R.C1779id.settings_container);
        this.mSettingsIcon = (ImageButton) this.mDialog.findViewById(C1777R.C1779id.settings);
        if (this.mRows.isEmpty()) {
            if (!AudioSystem.isSingleVolume(this.mContext)) {
                addRow$1(10, C1777R.C1778drawable.ic_volume_accessibility, C1777R.C1778drawable.ic_volume_accessibility, true, false);
            }
            addRow$1(3, C1777R.C1778drawable.ic_volume_media, C1777R.C1778drawable.ic_volume_media_mute, true, true);
            if (!AudioSystem.isSingleVolume(this.mContext)) {
                addRow$1(2, C1777R.C1778drawable.ic_volume_ringer, C1777R.C1778drawable.ic_volume_ringer_mute, true, false);
                addRow$1(4, C1777R.C1778drawable.ic_alarm, C1777R.C1778drawable.ic_volume_alarm_mute, true, false);
                addRow$1(0, 17302810, 17302810, false, false);
                addRow$1(6, C1777R.C1778drawable.ic_volume_bt_sco, C1777R.C1778drawable.ic_volume_bt_sco, false, false);
                addRow$1(1, C1777R.C1778drawable.ic_volume_system, C1777R.C1778drawable.ic_volume_system_mute, false, false);
            }
        } else {
            int size = this.mRows.size();
            for (int i = 0; i < size; i++) {
                VolumeRow volumeRow = (VolumeRow) this.mRows.get(i);
                initRow(volumeRow, volumeRow.stream, volumeRow.iconRes, volumeRow.iconMuteRes, volumeRow.important, volumeRow.defaultStream);
                this.mDialogRowsView.addView(volumeRow.view);
                updateVolumeRowH(volumeRow);
            }
        }
        updateRowsH(getActiveRow());
        ImageButton imageButton = this.mRingerIcon;
        if (imageButton != null) {
            imageButton.setAccessibilityLiveRegion(1);
            this.mRingerIcon.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda5(this));
        }
        updateRingerH();
        initSettingsH();
        CaptionsToggleImageButton captionsToggleImageButton = this.mODICaptionsIcon;
        if (captionsToggleImageButton != null) {
            VolumeDialogImpl$$ExternalSyntheticLambda9 volumeDialogImpl$$ExternalSyntheticLambda9 = new VolumeDialogImpl$$ExternalSyntheticLambda9(this);
            C1741H h = this.mHandler;
            captionsToggleImageButton.mConfirmedTapListener = volumeDialogImpl$$ExternalSyntheticLambda9;
            if (captionsToggleImageButton.mGestureDetector == null) {
                captionsToggleImageButton.mGestureDetector = new GestureDetector(captionsToggleImageButton.getContext(), captionsToggleImageButton.mGestureListener, h);
            }
        }
        this.mController.getCaptionsComponentState(false);
    }

    public final void initDimens() {
        int i;
        this.mDialogWidth = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.volume_dialog_panel_width);
        this.mDialogCornerRadius = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.volume_dialog_panel_width_half);
        this.mRingerDrawerItemSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.volume_ringer_drawer_item_size);
        this.mRingerRowsPadding = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.volume_dialog_ringer_rows_padding);
        boolean hasVibrator = this.mController.hasVibrator();
        this.mShowVibrate = hasVibrator;
        if (hasVibrator) {
            i = 3;
        } else {
            i = 2;
        }
        this.mRingerCount = i;
    }

    @SuppressLint({"InflateParams"})
    public final void initRow(VolumeRow volumeRow, int i, int i2, int i3, boolean z, boolean z2) {
        volumeRow.stream = i;
        volumeRow.iconRes = i2;
        volumeRow.iconMuteRes = i3;
        volumeRow.important = z;
        volumeRow.defaultStream = z2;
        AlphaTintDrawableWrapper alphaTintDrawableWrapper = null;
        View inflate = this.mDialog.getLayoutInflater().inflate(C1777R.layout.volume_dialog_row, (ViewGroup) null);
        volumeRow.view = inflate;
        inflate.setId(volumeRow.stream);
        volumeRow.view.setTag(volumeRow);
        TextView textView = (TextView) volumeRow.view.findViewById(C1777R.C1779id.volume_row_header);
        volumeRow.header = textView;
        textView.setId(volumeRow.stream * 20);
        if (i == 10) {
            volumeRow.header.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        }
        volumeRow.dndIcon = (FrameLayout) volumeRow.view.findViewById(C1777R.C1779id.dnd_icon);
        SeekBar seekBar = (SeekBar) volumeRow.view.findViewById(C1777R.C1779id.volume_row_slider);
        volumeRow.slider = seekBar;
        seekBar.setOnSeekBarChangeListener(new VolumeSeekBarChangeListener(volumeRow));
        volumeRow.number = (TextView) volumeRow.view.findViewById(C1777R.C1779id.volume_number);
        volumeRow.anim = null;
        LayerDrawable layerDrawable = (LayerDrawable) this.mContext.getDrawable(C1777R.C1778drawable.volume_row_seekbar);
        LayerDrawable layerDrawable2 = (LayerDrawable) ((RoundedCornerProgressDrawable) layerDrawable.findDrawableByLayerId(16908301)).getDrawable();
        volumeRow.sliderProgressSolid = layerDrawable2.findDrawableByLayerId(C1777R.C1779id.volume_seekbar_progress_solid);
        Drawable findDrawableByLayerId = layerDrawable2.findDrawableByLayerId(C1777R.C1779id.volume_seekbar_progress_icon);
        if (findDrawableByLayerId != null) {
            alphaTintDrawableWrapper = (AlphaTintDrawableWrapper) ((RotateDrawable) findDrawableByLayerId).getDrawable();
        }
        volumeRow.sliderProgressIcon = alphaTintDrawableWrapper;
        volumeRow.slider.setProgressDrawable(layerDrawable);
        volumeRow.icon = (ImageButton) volumeRow.view.findViewById(C1777R.C1779id.volume_row_icon);
        Resources.Theme theme = this.mContext.getTheme();
        ImageButton imageButton = volumeRow.icon;
        if (imageButton != null) {
            imageButton.setImageResource(i2);
        }
        AlphaTintDrawableWrapper alphaTintDrawableWrapper2 = volumeRow.sliderProgressIcon;
        if (alphaTintDrawableWrapper2 != null) {
            alphaTintDrawableWrapper2.setDrawable(volumeRow.view.getResources().getDrawable(i2, theme));
        }
        ImageButton imageButton2 = volumeRow.icon;
        if (imageButton2 == null) {
            return;
        }
        if (volumeRow.stream != 10) {
            imageButton2.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda7(this, volumeRow, i));
        } else {
            imageButton2.setImportantForAccessibility(2);
        }
    }

    public final void initSettingsH() {
        int i;
        View view = this.mSettingsView;
        if (view != null) {
            if (!this.mDeviceProvisionedController.isCurrentUserSetup() || this.mActivityManager.getLockTaskModeState() != 0) {
                i = 8;
            } else {
                i = 0;
            }
            view.setVisibility(i);
        }
        ImageButton imageButton = this.mSettingsIcon;
        if (imageButton != null) {
            imageButton.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda4(this, 0));
        }
    }

    public final boolean isLandscape() {
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            return true;
        }
        return false;
    }

    public final boolean isRtl() {
        if (this.mContext.getResources().getConfiguration().getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    public final void onStateChangedH(VolumeDialogController.State state) {
        VolumeRow volumeRow;
        int i;
        int i2;
        if (C1716D.BUG) {
            String str = TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onStateChangedH() state: ");
            m.append(state.toString());
            Log.d(str, m.toString());
        }
        VolumeDialogController.State state2 = this.mState;
        if (!(state2 == null || state == null || (i = state2.ringerModeInternal) == -1 || i == (i2 = state.ringerModeInternal) || i2 != 1)) {
            this.mController.vibrate(VibrationEffect.get(5));
        }
        this.mState = state;
        this.mDynamic.clear();
        for (int i3 = 0; i3 < state.states.size(); i3++) {
            int keyAt = state.states.keyAt(i3);
            if (state.states.valueAt(i3).dynamic) {
                this.mDynamic.put(keyAt, true);
                Iterator it = this.mRows.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        volumeRow = null;
                        break;
                    }
                    volumeRow = (VolumeRow) it.next();
                    if (volumeRow.stream == keyAt) {
                        break;
                    }
                }
                if (volumeRow == null) {
                    addRow$1(keyAt, C1777R.C1778drawable.ic_volume_remote, C1777R.C1778drawable.ic_volume_remote_mute, true, false);
                }
            }
        }
        int i4 = this.mActiveStream;
        int i5 = state.activeStream;
        if (i4 != i5) {
            this.mPrevActiveStream = i4;
            this.mActiveStream = i5;
            updateRowsH(getActiveRow());
            if (this.mShowing) {
                rescheduleTimeoutH();
            }
        }
        Iterator it2 = this.mRows.iterator();
        while (it2.hasNext()) {
            updateVolumeRowH((VolumeRow) it2.next());
        }
        updateRingerH();
        this.mWindow.setTitle(this.mContext.getString(C1777R.string.volume_dialog_title, new Object[]{getStreamLabelH(getActiveRow().f88ss)}));
    }

    public final void onUiModeChanged() {
        this.mContext.getTheme().applyStyle(this.mContext.getThemeResId(), true);
    }

    public final void recheckH(VolumeRow volumeRow) {
        if (volumeRow == null) {
            if (C1716D.BUG) {
                Log.d(TAG, "recheckH ALL");
            }
            trimObsoleteH();
            Iterator it = this.mRows.iterator();
            while (it.hasNext()) {
                updateVolumeRowH((VolumeRow) it.next());
            }
            return;
        }
        if (C1716D.BUG) {
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("recheckH "), volumeRow.stream, TAG);
        }
        updateVolumeRowH(volumeRow);
    }

    public final void rescheduleTimeoutH() {
        int i;
        this.mHandler.removeMessages(2);
        if (this.mHovering) {
            AccessibilityManagerWrapper accessibilityManagerWrapper = this.mAccessibilityMgr;
            Objects.requireNonNull(accessibilityManagerWrapper);
            i = accessibilityManagerWrapper.mAccessibilityManager.getRecommendedTimeoutMillis(16000, 4);
        } else if (this.mSafetyWarning != null) {
            AccessibilityManagerWrapper accessibilityManagerWrapper2 = this.mAccessibilityMgr;
            Objects.requireNonNull(accessibilityManagerWrapper2);
            i = accessibilityManagerWrapper2.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 6);
        } else if (this.mHasSeenODICaptionsTooltip || this.mODICaptionsTooltipView == null) {
            AccessibilityManagerWrapper accessibilityManagerWrapper3 = this.mAccessibilityMgr;
            Objects.requireNonNull(accessibilityManagerWrapper3);
            i = accessibilityManagerWrapper3.mAccessibilityManager.getRecommendedTimeoutMillis(3000, 4);
        } else {
            AccessibilityManagerWrapper accessibilityManagerWrapper4 = this.mAccessibilityMgr;
            Objects.requireNonNull(accessibilityManagerWrapper4);
            i = accessibilityManagerWrapper4.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 6);
        }
        C1741H h = this.mHandler;
        h.sendMessageDelayed(h.obtainMessage(2, 3, 0), (long) i);
        if (C1716D.BUG) {
            String str = TAG;
            StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("rescheduleTimeout ", i, " ");
            m.append(Debug.getCaller());
            Log.d(str, m.toString());
        }
        this.mController.userActivity();
    }

    public final void trimObsoleteH() {
        if (C1716D.BUG) {
            Log.d(TAG, "trimObsoleteH");
        }
        int size = this.mRows.size();
        while (true) {
            size--;
            if (size >= 0) {
                VolumeRow volumeRow = (VolumeRow) this.mRows.get(size);
                VolumeDialogController.StreamState streamState = volumeRow.f88ss;
                if (streamState != null && streamState.dynamic && !this.mDynamic.get(volumeRow.stream)) {
                    this.mRows.remove(size);
                    this.mDialogRowsView.removeView(volumeRow.view);
                    ConfigurableTexts configurableTexts = this.mConfigurableTexts;
                    TextView textView = volumeRow.header;
                    Objects.requireNonNull(configurableTexts);
                    configurableTexts.mTexts.remove(textView);
                    configurableTexts.mTextLabels.remove(textView);
                }
            } else {
                return;
            }
        }
    }

    public final void updateBackgroundForDrawerClosedAmount() {
        Drawable drawable = this.mRingerAndDrawerContainerBackground;
        if (drawable != null) {
            Rect copyBounds = drawable.copyBounds();
            if (!isLandscape()) {
                copyBounds.top = (int) (this.mRingerDrawerClosedAmount * ((float) getRingerDrawerOpenExtraSize()));
            } else {
                copyBounds.left = (int) (this.mRingerDrawerClosedAmount * ((float) getRingerDrawerOpenExtraSize()));
            }
            this.mRingerAndDrawerContainerBackground.setBounds(copyBounds);
        }
    }

    public final void updateCaptionsIcon() {
        String str;
        int i;
        boolean areCaptionsEnabled = this.mController.areCaptionsEnabled();
        CaptionsToggleImageButton captionsToggleImageButton = this.mODICaptionsIcon;
        Objects.requireNonNull(captionsToggleImageButton);
        if (captionsToggleImageButton.mCaptionsEnabled != areCaptionsEnabled) {
            C1741H h = this.mHandler;
            CaptionsToggleImageButton captionsToggleImageButton2 = this.mODICaptionsIcon;
            Objects.requireNonNull(captionsToggleImageButton2);
            captionsToggleImageButton2.mCaptionsEnabled = areCaptionsEnabled;
            AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK;
            if (areCaptionsEnabled) {
                str = captionsToggleImageButton2.getContext().getString(C1777R.string.volume_odi_captions_hint_disable);
            } else {
                str = captionsToggleImageButton2.getContext().getString(C1777R.string.volume_odi_captions_hint_enable);
            }
            ViewCompat.replaceAccessibilityAction(captionsToggleImageButton2, accessibilityActionCompat, str, new CaptionsToggleImageButton$$ExternalSyntheticLambda0(captionsToggleImageButton2));
            if (captionsToggleImageButton2.mCaptionsEnabled) {
                i = C1777R.C1778drawable.ic_volume_odi_captions;
            } else {
                i = C1777R.C1778drawable.ic_volume_odi_captions_disabled;
            }
            h.post(captionsToggleImageButton2.setImageResourceAsync(i));
        }
        boolean isCaptionStreamOptedOut = this.mController.isCaptionStreamOptedOut();
        CaptionsToggleImageButton captionsToggleImageButton3 = this.mODICaptionsIcon;
        Objects.requireNonNull(captionsToggleImageButton3);
        if (captionsToggleImageButton3.mOptedOut != isCaptionStreamOptedOut) {
            this.mHandler.post(new VolumeDialogImpl$$ExternalSyntheticLambda12(this, isCaptionStreamOptedOut));
        }
    }

    public final void updateRingerH() {
        VolumeDialogController.State state;
        VolumeDialogController.StreamState streamState;
        boolean z;
        int i;
        if (this.mRinger != null && (state = this.mState) != null && (streamState = state.states.get(2)) != null) {
            VolumeDialogController.State state2 = this.mState;
            int i2 = state2.zenMode;
            boolean z2 = false;
            if (i2 == 3 || i2 == 2 || (i2 == 1 && state2.disallowRinger)) {
                z = true;
            } else {
                z = false;
            }
            boolean z3 = !z;
            ImageButton imageButton = this.mRingerIcon;
            if (imageButton != null) {
                imageButton.setEnabled(z3);
            }
            FrameLayout frameLayout = this.mZenIcon;
            if (frameLayout != null) {
                if (z3) {
                    i = 8;
                } else {
                    i = 0;
                }
                frameLayout.setVisibility(i);
            }
            int i3 = this.mState.ringerModeInternal;
            if (i3 == 0) {
                this.mRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer_mute);
                this.mSelectedRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer_mute);
                this.mRingerIcon.setTag(2);
                addAccessibilityDescription(this.mRingerIcon, 0, this.mContext.getString(C1777R.string.volume_ringer_hint_unmute));
            } else if (i3 != 1) {
                if ((this.mAutomute && streamState.level == 0) || streamState.muted) {
                    z2 = true;
                }
                if (z || !z2) {
                    this.mRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer);
                    this.mSelectedRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer);
                    if (this.mController.hasVibrator()) {
                        addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(C1777R.string.volume_ringer_hint_vibrate));
                    } else {
                        addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(C1777R.string.volume_ringer_hint_mute));
                    }
                    this.mRingerIcon.setTag(1);
                    return;
                }
                this.mRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer_mute);
                this.mSelectedRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer_mute);
                addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(C1777R.string.volume_ringer_hint_unmute));
                this.mRingerIcon.setTag(2);
            } else {
                this.mRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer_vibrate);
                this.mSelectedRingerIcon.setImageResource(C1777R.C1778drawable.ic_volume_ringer_vibrate);
                addAccessibilityDescription(this.mRingerIcon, 1, this.mContext.getString(C1777R.string.volume_ringer_hint_mute));
                this.mRingerIcon.setTag(3);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
        if (r8 == r12.mPrevActiveStream) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x006a, code lost:
        if (r12.mDynamic.get(r9) != false) goto L_0x006c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0025 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateRowsH(com.android.systemui.volume.VolumeDialogImpl.VolumeRow r13) {
        /*
            r12 = this;
            boolean r0 = com.android.systemui.volume.C1716D.BUG
            if (r0 == 0) goto L_0x000c
            java.lang.String r0 = TAG
            java.lang.String r1 = "updateRowsH"
            android.util.Log.d(r0, r1)
        L_0x000c:
            boolean r0 = r12.mShowing
            if (r0 != 0) goto L_0x0013
            r12.trimObsoleteH()
        L_0x0013:
            boolean r0 = r12.isRtl()
            r1 = -1
            r2 = 32767(0x7fff, float:4.5916E-41)
            if (r0 != 0) goto L_0x001e
            r0 = r1
            goto L_0x001f
        L_0x001e:
            r0 = r2
        L_0x001f:
            java.util.ArrayList r3 = r12.mRows
            java.util.Iterator r3 = r3.iterator()
        L_0x0025:
            boolean r4 = r3.hasNext()
            r5 = 0
            if (r4 == 0) goto L_0x00e3
            java.lang.Object r4 = r3.next()
            com.android.systemui.volume.VolumeDialogImpl$VolumeRow r4 = (com.android.systemui.volume.VolumeDialogImpl.VolumeRow) r4
            r6 = 1
            if (r4 != r13) goto L_0x0037
            r7 = r6
            goto L_0x0038
        L_0x0037:
            r7 = r5
        L_0x0038:
            int r8 = r4.stream
            int r9 = r13.stream
            if (r8 != r9) goto L_0x0040
            r10 = r6
            goto L_0x0041
        L_0x0040:
            r10 = r5
        L_0x0041:
            if (r10 == 0) goto L_0x0044
            goto L_0x006c
        L_0x0044:
            boolean r10 = r12.mShowActiveStreamOnly
            if (r10 != 0) goto L_0x006e
            r10 = 10
            if (r8 != r10) goto L_0x004f
            boolean r8 = r12.mShowA11yStream
            goto L_0x006f
        L_0x004f:
            if (r9 != r10) goto L_0x0056
            int r11 = r12.mPrevActiveStream
            if (r8 != r11) goto L_0x0056
            goto L_0x006c
        L_0x0056:
            boolean r8 = r4.defaultStream
            if (r8 == 0) goto L_0x006e
            r8 = 2
            if (r9 == r8) goto L_0x006c
            r8 = 4
            if (r9 == r8) goto L_0x006c
            if (r9 == 0) goto L_0x006c
            if (r9 == r10) goto L_0x006c
            android.util.SparseBooleanArray r8 = r12.mDynamic
            boolean r8 = r8.get(r9)
            if (r8 == 0) goto L_0x006e
        L_0x006c:
            r8 = r6
            goto L_0x006f
        L_0x006e:
            r8 = r5
        L_0x006f:
            android.view.View r9 = r4.view
            if (r9 == 0) goto L_0x0086
            int r10 = r9.getVisibility()
            if (r10 != 0) goto L_0x007a
            goto L_0x007b
        L_0x007a:
            r6 = r5
        L_0x007b:
            if (r6 != r8) goto L_0x007e
            goto L_0x0086
        L_0x007e:
            if (r8 == 0) goto L_0x0081
            goto L_0x0083
        L_0x0081:
            r5 = 8
        L_0x0083:
            r9.setVisibility(r5)
        L_0x0086:
            if (r8 == 0) goto L_0x00d6
            android.graphics.drawable.Drawable r5 = r12.mRingerAndDrawerContainerBackground
            if (r5 == 0) goto L_0x00d6
            boolean r5 = r12.isRtl()
            if (r5 != 0) goto L_0x009f
            android.view.ViewGroup r5 = r12.mDialogRowsView
            android.view.View r6 = r4.view
            int r5 = r5.indexOfChild(r6)
            int r0 = java.lang.Math.max(r0, r5)
            goto L_0x00ab
        L_0x009f:
            android.view.ViewGroup r5 = r12.mDialogRowsView
            android.view.View r6 = r4.view
            int r5 = r5.indexOfChild(r6)
            int r0 = java.lang.Math.min(r0, r5)
        L_0x00ab:
            android.view.View r5 = r4.view
            android.view.ViewGroup$LayoutParams r5 = r5.getLayoutParams()
            boolean r6 = r5 instanceof android.widget.LinearLayout.LayoutParams
            if (r6 == 0) goto L_0x00c8
            android.widget.LinearLayout$LayoutParams r5 = (android.widget.LinearLayout.LayoutParams) r5
            boolean r6 = r12.isRtl()
            if (r6 != 0) goto L_0x00c3
            int r6 = r12.mRingerRowsPadding
            r5.setMarginEnd(r6)
            goto L_0x00c8
        L_0x00c3:
            int r6 = r12.mRingerRowsPadding
            r5.setMarginStart(r6)
        L_0x00c8:
            android.view.View r5 = r4.view
            android.view.ContextThemeWrapper r6 = r12.mContext
            r8 = 2131232760(0x7f0807f8, float:1.8081638E38)
            android.graphics.drawable.Drawable r6 = r6.getDrawable(r8)
            r5.setBackgroundDrawable(r6)
        L_0x00d6:
            android.view.View r5 = r4.view
            boolean r5 = r5.isShown()
            if (r5 == 0) goto L_0x0025
            r12.updateVolumeRowTintH(r4, r7)
            goto L_0x0025
        L_0x00e3:
            if (r0 <= r1) goto L_0x0100
            if (r0 >= r2) goto L_0x0100
            android.view.ViewGroup r13 = r12.mDialogRowsView
            android.view.View r13 = r13.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r0 = r13.getLayoutParams()
            boolean r1 = r0 instanceof android.widget.LinearLayout.LayoutParams
            if (r1 == 0) goto L_0x0100
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            r0.setMarginStart(r5)
            r0.setMarginEnd(r5)
            r13.setBackgroundColor(r5)
        L_0x0100:
            r12.updateBackgroundForDrawerClosedAmount()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogImpl.updateRowsH(com.android.systemui.volume.VolumeDialogImpl$VolumeRow):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:195:0x02ba  */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x02c4  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x02e6  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x02f0  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x03ae  */
    /* JADX WARNING: Removed duplicated region for block: B:254:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateVolumeRowH(com.android.systemui.volume.VolumeDialogImpl.VolumeRow r15) {
        /*
            r14 = this;
            boolean r0 = com.android.systemui.volume.C1716D.BUG
            if (r0 == 0) goto L_0x0019
            java.lang.String r0 = TAG
            java.lang.String r1 = "updateVolumeRowH s="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r2 = r15.stream
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
        L_0x0019:
            com.android.systemui.plugins.VolumeDialogController$State r0 = r14.mState
            if (r0 != 0) goto L_0x001e
            return
        L_0x001e:
            android.util.SparseArray<com.android.systemui.plugins.VolumeDialogController$StreamState> r0 = r0.states
            int r1 = r15.stream
            java.lang.Object r0 = r0.get(r1)
            com.android.systemui.plugins.VolumeDialogController$StreamState r0 = (com.android.systemui.plugins.VolumeDialogController.StreamState) r0
            if (r0 != 0) goto L_0x002b
            return
        L_0x002b:
            r15.f88ss = r0
            int r1 = r0.level
            if (r1 <= 0) goto L_0x0033
            r15.lastAudibleLevel = r1
        L_0x0033:
            int r2 = r15.requestedLevel
            if (r1 != r2) goto L_0x003a
            r1 = -1
            r15.requestedLevel = r1
        L_0x003a:
            int r1 = r15.stream
            r2 = 10
            r3 = 1
            if (r1 != r2) goto L_0x0043
            r2 = r3
            goto L_0x0044
        L_0x0043:
            r2 = 0
        L_0x0044:
            r4 = 2
            if (r1 != r4) goto L_0x0049
            r5 = r3
            goto L_0x004a
        L_0x0049:
            r5 = 0
        L_0x004a:
            if (r1 != r3) goto L_0x004e
            r6 = r3
            goto L_0x004f
        L_0x004e:
            r6 = 0
        L_0x004f:
            r7 = 4
            if (r1 != r7) goto L_0x0054
            r7 = r3
            goto L_0x0055
        L_0x0054:
            r7 = 0
        L_0x0055:
            r8 = 3
            if (r1 != r8) goto L_0x005a
            r1 = r3
            goto L_0x005b
        L_0x005a:
            r1 = 0
        L_0x005b:
            if (r5 == 0) goto L_0x0065
            com.android.systemui.plugins.VolumeDialogController$State r9 = r14.mState
            int r9 = r9.ringerModeInternal
            if (r9 != r3) goto L_0x0065
            r9 = r3
            goto L_0x0066
        L_0x0065:
            r9 = 0
        L_0x0066:
            if (r5 == 0) goto L_0x0070
            com.android.systemui.plugins.VolumeDialogController$State r10 = r14.mState
            int r10 = r10.ringerModeInternal
            if (r10 != 0) goto L_0x0070
            r10 = r3
            goto L_0x0071
        L_0x0070:
            r10 = 0
        L_0x0071:
            com.android.systemui.plugins.VolumeDialogController$State r11 = r14.mState
            int r12 = r11.zenMode
            if (r12 != r3) goto L_0x0079
            r13 = r3
            goto L_0x007a
        L_0x0079:
            r13 = 0
        L_0x007a:
            if (r12 != r8) goto L_0x007e
            r8 = r3
            goto L_0x007f
        L_0x007e:
            r8 = 0
        L_0x007f:
            if (r12 != r4) goto L_0x0083
            r12 = r3
            goto L_0x0084
        L_0x0083:
            r12 = 0
        L_0x0084:
            if (r8 == 0) goto L_0x008b
            if (r5 != 0) goto L_0x00b0
            if (r6 == 0) goto L_0x00b2
            goto L_0x00b0
        L_0x008b:
            if (r12 == 0) goto L_0x0096
            if (r5 != 0) goto L_0x00b0
            if (r6 != 0) goto L_0x00b0
            if (r7 != 0) goto L_0x00b0
            if (r1 == 0) goto L_0x00b2
            goto L_0x00b0
        L_0x0096:
            if (r13 == 0) goto L_0x00b2
            if (r7 == 0) goto L_0x009e
            boolean r7 = r11.disallowAlarms
            if (r7 != 0) goto L_0x00b0
        L_0x009e:
            if (r1 == 0) goto L_0x00a4
            boolean r1 = r11.disallowMedia
            if (r1 != 0) goto L_0x00b0
        L_0x00a4:
            if (r5 == 0) goto L_0x00aa
            boolean r1 = r11.disallowRinger
            if (r1 != 0) goto L_0x00b0
        L_0x00aa:
            if (r6 == 0) goto L_0x00b2
            boolean r1 = r11.disallowSystem
            if (r1 == 0) goto L_0x00b2
        L_0x00b0:
            r1 = r3
            goto L_0x00b3
        L_0x00b2:
            r1 = 0
        L_0x00b3:
            int r6 = r0.levelMax
            int r6 = r6 * 100
            android.widget.SeekBar r7 = r15.slider
            int r7 = r7.getMax()
            if (r6 == r7) goto L_0x00c4
            android.widget.SeekBar r7 = r15.slider
            r7.setMax(r6)
        L_0x00c4:
            int r6 = r0.levelMin
            int r6 = r6 * 100
            android.widget.SeekBar r7 = r15.slider
            int r7 = r7.getMin()
            if (r6 == r7) goto L_0x00d5
            android.widget.SeekBar r7 = r15.slider
            r7.setMin(r6)
        L_0x00d5:
            android.widget.TextView r6 = r15.header
            java.lang.String r7 = r14.getStreamLabelH(r0)
            java.lang.CharSequence r8 = r6.getText()
            r11 = 0
            if (r8 == 0) goto L_0x00e8
            int r12 = r8.length()
            if (r12 != 0) goto L_0x00e9
        L_0x00e8:
            r8 = r11
        L_0x00e9:
            if (r7 == 0) goto L_0x00f3
            int r12 = r7.length()
            if (r12 != 0) goto L_0x00f2
            goto L_0x00f3
        L_0x00f2:
            r11 = r7
        L_0x00f3:
            boolean r8 = java.util.Objects.equals(r8, r11)
            if (r8 == 0) goto L_0x00fa
            goto L_0x00fd
        L_0x00fa:
            r6.setText(r7)
        L_0x00fd:
            android.widget.SeekBar r6 = r15.slider
            android.widget.TextView r7 = r15.header
            java.lang.CharSequence r7 = r7.getText()
            r6.setContentDescription(r7)
            com.android.systemui.volume.ConfigurableTexts r6 = r14.mConfigurableTexts
            android.widget.TextView r7 = r15.header
            int r8 = r0.name
            if (r7 != 0) goto L_0x0114
            java.util.Objects.requireNonNull(r6)
            goto L_0x015b
        L_0x0114:
            android.util.ArrayMap<android.widget.TextView, java.lang.Integer> r11 = r6.mTexts
            boolean r11 = r11.containsKey(r7)
            if (r11 == 0) goto L_0x0128
            android.util.ArrayMap<android.widget.TextView, java.lang.Integer> r6 = r6.mTexts
            java.lang.Object r6 = r6.get(r7)
            java.lang.Integer r6 = (java.lang.Integer) r6
            r6.intValue()
            goto L_0x015b
        L_0x0128:
            android.content.Context r11 = r6.mContext
            android.content.res.Resources r11 = r11.getResources()
            android.content.res.Configuration r12 = r11.getConfiguration()
            float r12 = r12.fontScale
            android.util.DisplayMetrics r11 = r11.getDisplayMetrics()
            float r11 = r11.density
            float r13 = r7.getTextSize()
            float r13 = r13 / r12
            float r13 = r13 / r11
            int r11 = (int) r13
            android.util.ArrayMap<android.widget.TextView, java.lang.Integer> r12 = r6.mTexts
            java.lang.Integer r13 = java.lang.Integer.valueOf(r11)
            r12.put(r7, r13)
            com.android.systemui.volume.ConfigurableTexts$1 r12 = new com.android.systemui.volume.ConfigurableTexts$1
            r12.<init>(r7, r11)
            r7.addOnAttachStateChangeListener(r12)
            android.util.ArrayMap<android.widget.TextView, java.lang.Integer> r6 = r6.mTextLabels
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r6.put(r7, r8)
        L_0x015b:
            boolean r6 = r14.mAutomute
            if (r6 != 0) goto L_0x0163
            boolean r7 = r0.muteSupported
            if (r7 == 0) goto L_0x0167
        L_0x0163:
            if (r1 != 0) goto L_0x0167
            r7 = r3
            goto L_0x0168
        L_0x0167:
            r7 = 0
        L_0x0168:
            r8 = 2131232317(0x7f08063d, float:1.808074E38)
            r11 = 2131232315(0x7f08063b, float:1.8080736E38)
            r12 = 2131232316(0x7f08063c, float:1.8080738E38)
            r13 = 2131232326(0x7f080646, float:1.8080758E38)
            if (r9 == 0) goto L_0x0178
            r3 = r13
            goto L_0x01c1
        L_0x0178:
            if (r10 != 0) goto L_0x01bf
            if (r1 == 0) goto L_0x017d
            goto L_0x01bf
        L_0x017d:
            boolean r10 = r0.routedToBluetooth
            if (r10 == 0) goto L_0x0193
            if (r6 == 0) goto L_0x0187
            int r4 = r0.level
            if (r4 == 0) goto L_0x018d
        L_0x0187:
            boolean r4 = r0.muted
            if (r4 == 0) goto L_0x018c
            goto L_0x018d
        L_0x018c:
            r3 = 0
        L_0x018d:
            if (r3 == 0) goto L_0x0191
            r3 = r12
            goto L_0x01c1
        L_0x0191:
            r3 = r11
            goto L_0x01c1
        L_0x0193:
            if (r6 == 0) goto L_0x0199
            int r6 = r0.level
            if (r6 == 0) goto L_0x019f
        L_0x0199:
            boolean r6 = r0.muted
            if (r6 == 0) goto L_0x019e
            goto L_0x019f
        L_0x019e:
            r3 = 0
        L_0x019f:
            if (r3 == 0) goto L_0x01ac
            boolean r3 = r0.muted
            if (r3 == 0) goto L_0x01a9
            r3 = 2131232319(0x7f08063f, float:1.8080744E38)
            goto L_0x01c1
        L_0x01a9:
            int r3 = r15.iconMuteRes
            goto L_0x01c1
        L_0x01ac:
            boolean r3 = r14.mShowLowMediaVolumeIcon
            if (r3 == 0) goto L_0x01bc
            int r3 = r0.level
            int r3 = r3 * r4
            int r4 = r0.levelMax
            int r6 = r0.levelMin
            int r4 = r4 + r6
            if (r3 >= r4) goto L_0x01bc
            r3 = r8
            goto L_0x01c1
        L_0x01bc:
            int r3 = r15.iconRes
            goto L_0x01c1
        L_0x01bf:
            int r3 = r15.iconMuteRes
        L_0x01c1:
            android.view.ContextThemeWrapper r4 = r14.mContext
            android.content.res.Resources$Theme r4 = r4.getTheme()
            android.widget.ImageButton r6 = r15.icon
            if (r6 == 0) goto L_0x01ce
            r6.setImageResource(r3)
        L_0x01ce:
            com.android.systemui.util.AlphaTintDrawableWrapper r6 = r15.sliderProgressIcon
            if (r6 == 0) goto L_0x01df
            android.view.View r10 = r15.view
            android.content.res.Resources r10 = r10.getResources()
            android.graphics.drawable.Drawable r4 = r10.getDrawable(r3, r4)
            r6.setDrawable(r4)
        L_0x01df:
            if (r3 != r13) goto L_0x01e3
            r3 = 3
            goto L_0x01f8
        L_0x01e3:
            if (r3 == r12) goto L_0x01f7
            int r4 = r15.iconMuteRes
            if (r3 != r4) goto L_0x01ea
            goto L_0x01f7
        L_0x01ea:
            if (r3 == r11) goto L_0x01f5
            int r4 = r15.iconRes
            if (r3 == r4) goto L_0x01f5
            if (r3 != r8) goto L_0x01f3
            goto L_0x01f5
        L_0x01f3:
            r3 = 0
            goto L_0x01f8
        L_0x01f5:
            r3 = 1
            goto L_0x01f8
        L_0x01f7:
            r3 = 2
        L_0x01f8:
            r15.iconState = r3
            android.widget.ImageButton r3 = r15.icon
            if (r3 == 0) goto L_0x02b7
            if (r7 == 0) goto L_0x02ae
            r4 = 2131953516(0x7f13076c, float:1.9543505E38)
            r6 = 2131953515(0x7f13076b, float:1.9543503E38)
            r7 = 2131953517(0x7f13076d, float:1.9543507E38)
            if (r5 == 0) goto L_0x0268
            if (r9 == 0) goto L_0x0222
            android.view.ContextThemeWrapper r2 = r14.mContext
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r6 = 0
            r4[r6] = r0
            java.lang.String r0 = r2.getString(r7, r4)
            r3.setContentDescription(r0)
            goto L_0x02b7
        L_0x0222:
            com.android.systemui.plugins.VolumeDialogController r2 = r14.mController
            boolean r2 = r2.hasVibrator()
            if (r2 == 0) goto L_0x024c
            android.widget.ImageButton r2 = r15.icon
            android.view.ContextThemeWrapper r3 = r14.mContext
            boolean r4 = r14.mShowA11yStream
            if (r4 == 0) goto L_0x0236
            r4 = 2131953519(0x7f13076f, float:1.9543511E38)
            goto L_0x0239
        L_0x0236:
            r4 = 2131953518(0x7f13076e, float:1.954351E38)
        L_0x0239:
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r7 = 0
            r6[r7] = r0
            java.lang.String r0 = r3.getString(r4, r6)
            r2.setContentDescription(r0)
            goto L_0x02b7
        L_0x024c:
            android.widget.ImageButton r2 = r15.icon
            android.view.ContextThemeWrapper r3 = r14.mContext
            boolean r7 = r14.mShowA11yStream
            if (r7 == 0) goto L_0x0255
            goto L_0x0256
        L_0x0255:
            r4 = r6
        L_0x0256:
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r7 = 0
            r6[r7] = r0
            java.lang.String r0 = r3.getString(r4, r6)
            r2.setContentDescription(r0)
            goto L_0x02b7
        L_0x0268:
            if (r2 == 0) goto L_0x0272
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r3.setContentDescription(r0)
            goto L_0x02b7
        L_0x0272:
            boolean r2 = r0.muted
            if (r2 != 0) goto L_0x0299
            boolean r2 = r14.mAutomute
            if (r2 == 0) goto L_0x027f
            int r2 = r0.level
            if (r2 != 0) goto L_0x027f
            goto L_0x0299
        L_0x027f:
            android.view.ContextThemeWrapper r2 = r14.mContext
            boolean r7 = r14.mShowA11yStream
            if (r7 == 0) goto L_0x0286
            goto L_0x0287
        L_0x0286:
            r4 = r6
        L_0x0287:
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r7 = 0
            r6[r7] = r0
            java.lang.String r0 = r2.getString(r4, r6)
            r3.setContentDescription(r0)
            goto L_0x02b8
        L_0x0299:
            r2 = 1
            r4 = 0
            android.view.ContextThemeWrapper r6 = r14.mContext
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r2[r4] = r0
            java.lang.String r0 = r6.getString(r7, r2)
            r3.setContentDescription(r0)
            r7 = r4
            goto L_0x02b8
        L_0x02ae:
            r7 = 0
            java.lang.String r0 = r14.getStreamLabelH(r0)
            r3.setContentDescription(r0)
            goto L_0x02b8
        L_0x02b7:
            r7 = 0
        L_0x02b8:
            if (r1 == 0) goto L_0x02bc
            r15.tracking = r7
        L_0x02bc:
            r0 = r1 ^ 1
            r2 = r0 ^ 1
            android.widget.FrameLayout r3 = r15.dndIcon
            if (r2 == 0) goto L_0x02c6
            r2 = 0
            goto L_0x02c8
        L_0x02c6:
            r2 = 8
        L_0x02c8:
            r3.setVisibility(r2)
            com.android.systemui.plugins.VolumeDialogController$StreamState r2 = r15.f88ss
            boolean r3 = r2.muted
            if (r3 == 0) goto L_0x02d7
            if (r5 != 0) goto L_0x02d7
            if (r1 != 0) goto L_0x02d7
            r1 = 0
            goto L_0x02d9
        L_0x02d7:
            int r1 = r2.level
        L_0x02d9:
            android.widget.SeekBar r2 = r15.slider
            r2.setEnabled(r0)
            int r0 = r15.stream
            int r2 = r14.mActiveStream
            if (r0 != r2) goto L_0x02e6
            r0 = 1
            goto L_0x02e7
        L_0x02e6:
            r0 = 0
        L_0x02e7:
            r14.updateVolumeRowTintH(r15, r0)
            boolean r0 = r15.tracking
            if (r0 == 0) goto L_0x02f0
            goto L_0x03aa
        L_0x02f0:
            android.widget.SeekBar r0 = r15.slider
            int r0 = r0.getProgress()
            android.widget.SeekBar r2 = r15.slider
            int r2 = getImpliedLevel(r2, r0)
            android.view.View r3 = r15.view
            int r3 = r3.getVisibility()
            if (r3 != 0) goto L_0x0306
            r3 = 1
            goto L_0x0307
        L_0x0306:
            r3 = 0
        L_0x0307:
            long r4 = android.os.SystemClock.uptimeMillis()
            long r6 = r15.userAttempt
            long r4 = r4 - r6
            r6 = 1000(0x3e8, double:4.94E-321)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0316
            r4 = 1
            goto L_0x0317
        L_0x0316:
            r4 = 0
        L_0x0317:
            com.android.systemui.volume.VolumeDialogImpl$H r5 = r14.mHandler
            r8 = 3
            r5.removeMessages(r8, r15)
            boolean r5 = r14.mShowing
            if (r5 == 0) goto L_0x033f
            if (r3 == 0) goto L_0x033f
            if (r4 == 0) goto L_0x033f
            boolean r0 = com.android.systemui.volume.C1716D.BUG
            if (r0 == 0) goto L_0x0330
            java.lang.String r0 = TAG
            java.lang.String r2 = "inGracePeriod"
            android.util.Log.d(r0, r2)
        L_0x0330:
            com.android.systemui.volume.VolumeDialogImpl$H r14 = r14.mHandler
            r0 = 3
            android.os.Message r0 = r14.obtainMessage(r0, r15)
            long r2 = r15.userAttempt
            long r2 = r2 + r6
            r14.sendMessageAtTime(r0, r2)
            goto L_0x03aa
        L_0x033f:
            if (r1 != r2) goto L_0x0346
            if (r5 == 0) goto L_0x0346
            if (r3 == 0) goto L_0x0346
            goto L_0x03aa
        L_0x0346:
            int r14 = r1 * 100
            if (r0 == r14) goto L_0x03aa
            if (r5 == 0) goto L_0x039d
            if (r3 == 0) goto L_0x039d
            android.animation.ObjectAnimator r2 = r15.anim
            if (r2 == 0) goto L_0x035d
            boolean r2 = r2.isRunning()
            if (r2 == 0) goto L_0x035d
            int r2 = r15.animTargetProgress
            if (r2 != r14) goto L_0x035d
            goto L_0x03aa
        L_0x035d:
            android.animation.ObjectAnimator r2 = r15.anim
            if (r2 != 0) goto L_0x037d
            android.widget.SeekBar r2 = r15.slider
            r3 = 2
            int[] r3 = new int[r3]
            r4 = 0
            r3[r4] = r0
            r0 = 1
            r3[r0] = r14
            java.lang.String r0 = "progress"
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofInt(r2, r0, r3)
            r15.anim = r0
            android.view.animation.DecelerateInterpolator r2 = new android.view.animation.DecelerateInterpolator
            r2.<init>()
            r0.setInterpolator(r2)
            goto L_0x038e
        L_0x037d:
            r2.cancel()
            android.animation.ObjectAnimator r2 = r15.anim
            r3 = 2
            int[] r3 = new int[r3]
            r4 = 0
            r3[r4] = r0
            r0 = 1
            r3[r0] = r14
            r2.setIntValues(r3)
        L_0x038e:
            r15.animTargetProgress = r14
            android.animation.ObjectAnimator r14 = r15.anim
            r2 = 80
            r14.setDuration(r2)
            android.animation.ObjectAnimator r14 = r15.anim
            r14.start()
            goto L_0x03aa
        L_0x039d:
            android.animation.ObjectAnimator r0 = r15.anim
            if (r0 == 0) goto L_0x03a4
            r0.cancel()
        L_0x03a4:
            android.widget.SeekBar r0 = r15.slider
            r2 = 1
            r0.setProgress(r14, r2)
        L_0x03aa:
            android.widget.TextView r14 = r15.number
            if (r14 == 0) goto L_0x03b5
            java.lang.String r15 = java.lang.Integer.toString(r1)
            r14.setText(r15)
        L_0x03b5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogImpl.updateVolumeRowH(com.android.systemui.volume.VolumeDialogImpl$VolumeRow):void");
    }

    public final void updateVolumeRowTintH(VolumeRow volumeRow, boolean z) {
        boolean z2;
        ColorStateList colorStateList;
        int i;
        if (z) {
            volumeRow.slider.requestFocus();
        }
        if (!z || !volumeRow.slider.isEnabled()) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2 || this.mChangeVolumeRowTintWhenInactive) {
            if (z2) {
                colorStateList = Utils.getColorAttr(this.mContext, 16843829);
            } else {
                colorStateList = Utils.getColorAttr(this.mContext, 17956902);
            }
            if (z2) {
                i = Color.alpha(colorStateList.getDefaultColor());
            } else {
                TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16844115});
                float f = obtainStyledAttributes.getFloat(0, 0.0f);
                obtainStyledAttributes.recycle();
                i = (int) (f * 255.0f);
            }
            ColorStateList colorAttr = Utils.getColorAttr(this.mContext, 16844002);
            ColorStateList colorAttr2 = Utils.getColorAttr(this.mContext, 17957103);
            volumeRow.sliderProgressSolid.setTintList(colorStateList);
            AlphaTintDrawableWrapper alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
            if (alphaTintDrawableWrapper != null) {
                alphaTintDrawableWrapper.setTintList(colorAttr);
            }
            ImageButton imageButton = volumeRow.icon;
            if (imageButton != null) {
                imageButton.setImageTintList(colorAttr2);
                volumeRow.icon.setImageAlpha(i);
            }
            TextView textView = volumeRow.number;
            if (textView != null) {
                textView.setTextColor(colorStateList);
                volumeRow.number.setAlpha((float) i);
            }
        }
    }

    /* renamed from: -$$Nest$mshowH  reason: not valid java name */
    public static void m287$$Nest$mshowH(VolumeDialogImpl volumeDialogImpl, int i) {
        Objects.requireNonNull(volumeDialogImpl);
        if (C1716D.BUG) {
            ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("showH r="), Events.SHOW_REASONS[i], TAG);
        }
        volumeDialogImpl.mHandler.removeMessages(1);
        volumeDialogImpl.mHandler.removeMessages(2);
        volumeDialogImpl.rescheduleTimeoutH();
        if (volumeDialogImpl.mConfigChanged) {
            volumeDialogImpl.initDialog();
            ConfigurableTexts configurableTexts = volumeDialogImpl.mConfigurableTexts;
            Objects.requireNonNull(configurableTexts);
            if (!configurableTexts.mTexts.isEmpty()) {
                configurableTexts.mTexts.keyAt(0).post(configurableTexts.mUpdateAll);
            }
            volumeDialogImpl.mConfigChanged = false;
        }
        volumeDialogImpl.initSettingsH();
        volumeDialogImpl.mShowing = true;
        volumeDialogImpl.mIsAnimatingDismiss = false;
        volumeDialogImpl.mDialog.show();
        Events.writeEvent(0, Integer.valueOf(i), Boolean.valueOf(volumeDialogImpl.mKeyguard.isKeyguardLocked()));
        volumeDialogImpl.mController.notifyVisible(true);
        volumeDialogImpl.mController.getCaptionsComponentState(false);
        volumeDialogImpl.checkODICaptionsTooltip(false);
        volumeDialogImpl.updateBackgroundForDrawerClosedAmount();
    }

    public VolumeDialogImpl(Context context, VolumeDialogController volumeDialogController, AccessibilityManagerWrapper accessibilityManagerWrapper, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, MediaOutputDialogFactory mediaOutputDialogFactory, ActivityStarter activityStarter) {
        boolean z = false;
        this.mIsRingerDrawerOpen = false;
        this.mRingerDrawerClosedAmount = 1.0f;
        this.mRows = new ArrayList();
        this.mDynamic = new SparseBooleanArray();
        this.mSafetyWarningLock = new Object();
        this.mAccessibility = new Accessibility();
        this.mAutomute = true;
        this.mSilentMode = true;
        this.mHovering = false;
        this.mConfigChanged = false;
        this.mIsAnimatingDismiss = false;
        this.mODICaptionsTooltipView = null;
        this.mControllerCallbackH = new VolumeDialogController.Callbacks() {
            public final void onAccessibilityModeChanged(Boolean bool) {
                boolean z;
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                if (bool == null) {
                    z = false;
                } else {
                    z = bool.booleanValue();
                }
                volumeDialogImpl.mShowA11yStream = z;
                VolumeRow activeRow = VolumeDialogImpl.this.getActiveRow();
                VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                if (volumeDialogImpl2.mShowA11yStream || 10 != activeRow.stream) {
                    volumeDialogImpl2.updateRowsH(activeRow);
                } else {
                    volumeDialogImpl2.dismissH(7);
                }
            }

            public final void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
                ViewStub viewStub;
                int i;
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                boolean booleanValue = bool.booleanValue();
                boolean booleanValue2 = bool2.booleanValue();
                Objects.requireNonNull(volumeDialogImpl);
                ViewGroup viewGroup = volumeDialogImpl.mODICaptionsView;
                if (viewGroup != null) {
                    if (booleanValue) {
                        i = 0;
                    } else {
                        i = 8;
                    }
                    viewGroup.setVisibility(i);
                }
                if (booleanValue) {
                    volumeDialogImpl.updateCaptionsIcon();
                    if (booleanValue2) {
                        if (!volumeDialogImpl.mHasSeenODICaptionsTooltip && (viewStub = volumeDialogImpl.mODICaptionsTooltipViewStub) != null) {
                            View inflate = viewStub.inflate();
                            volumeDialogImpl.mODICaptionsTooltipView = inflate;
                            inflate.findViewById(C1777R.C1779id.dismiss).setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda2(volumeDialogImpl, 2));
                            volumeDialogImpl.mODICaptionsTooltipViewStub = null;
                            volumeDialogImpl.rescheduleTimeoutH();
                        }
                        View view = volumeDialogImpl.mODICaptionsTooltipView;
                        if (view != null) {
                            view.setAlpha(0.0f);
                            volumeDialogImpl.mHandler.post(new VolumeDialogImpl$$ExternalSyntheticLambda10(volumeDialogImpl, 0));
                        }
                    }
                }
            }

            public final void onConfigurationChanged() {
                VolumeDialogImpl.this.mDialog.dismiss();
                VolumeDialogImpl.this.mConfigChanged = true;
            }

            public final void onDismissRequested(int i) {
                VolumeDialogImpl.this.dismissH(i);
            }

            public final void onLayoutDirectionChanged(int i) {
                VolumeDialogImpl.this.mDialogView.setLayoutDirection(i);
            }

            public final void onScreenOff() {
                VolumeDialogImpl.this.dismissH(4);
            }

            public final void onShowRequested(int i) {
                VolumeDialogImpl.m287$$Nest$mshowH(VolumeDialogImpl.this, i);
            }

            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
                r3.recheckH((com.android.systemui.volume.VolumeDialogImpl.VolumeRow) null);
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onShowSafetyWarning(int r4) {
                /*
                    r3 = this;
                    com.android.systemui.volume.VolumeDialogImpl r3 = com.android.systemui.volume.VolumeDialogImpl.this
                    java.util.Objects.requireNonNull(r3)
                    r4 = r4 & 1025(0x401, float:1.436E-42)
                    if (r4 != 0) goto L_0x000d
                    boolean r4 = r3.mShowing
                    if (r4 == 0) goto L_0x002d
                L_0x000d:
                    java.lang.Object r4 = r3.mSafetyWarningLock
                    monitor-enter(r4)
                    com.android.systemui.volume.SafetyWarningDialog r0 = r3.mSafetyWarning     // Catch:{ all -> 0x0031 }
                    if (r0 == 0) goto L_0x0016
                    monitor-exit(r4)     // Catch:{ all -> 0x0031 }
                    goto L_0x0030
                L_0x0016:
                    com.android.systemui.volume.VolumeDialogImpl$4 r0 = new com.android.systemui.volume.VolumeDialogImpl$4     // Catch:{ all -> 0x0031 }
                    android.view.ContextThemeWrapper r1 = r3.mContext     // Catch:{ all -> 0x0031 }
                    com.android.systemui.plugins.VolumeDialogController r2 = r3.mController     // Catch:{ all -> 0x0031 }
                    android.media.AudioManager r2 = r2.getAudioManager()     // Catch:{ all -> 0x0031 }
                    r0.<init>(r1, r2)     // Catch:{ all -> 0x0031 }
                    r3.mSafetyWarning = r0     // Catch:{ all -> 0x0031 }
                    r0.show()     // Catch:{ all -> 0x0031 }
                    monitor-exit(r4)     // Catch:{ all -> 0x0031 }
                    r4 = 0
                    r3.recheckH(r4)
                L_0x002d:
                    r3.rescheduleTimeoutH()
                L_0x0030:
                    return
                L_0x0031:
                    r3 = move-exception
                    monitor-exit(r4)     // Catch:{ all -> 0x0031 }
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogImpl.C17406.onShowSafetyWarning(int):void");
            }

            public final void onShowSilentHint() {
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                if (volumeDialogImpl.mSilentMode) {
                    volumeDialogImpl.mController.setRingerMode(2, false);
                }
            }

            public final void onShowVibrateHint() {
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                if (volumeDialogImpl.mSilentMode) {
                    volumeDialogImpl.mController.setRingerMode(0, false);
                }
            }

            public final void onStateChanged(VolumeDialogController.State state) {
                VolumeDialogImpl.this.onStateChangedH(state);
            }
        };
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, C1777R.style.volume_dialog_theme);
        this.mContext = contextThemeWrapper;
        this.mController = volumeDialogController;
        this.mKeyguard = (KeyguardManager) contextThemeWrapper.getSystemService("keyguard");
        this.mActivityManager = (ActivityManager) contextThemeWrapper.getSystemService("activity");
        this.mAccessibilityMgr = accessibilityManagerWrapper;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mConfigurationController = configurationController;
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
        this.mActivityStarter = activityStarter;
        this.mShowActiveStreamOnly = (contextThemeWrapper.getPackageManager().hasSystemFeature("android.software.leanback") || contextThemeWrapper.getPackageManager().hasSystemFeature("android.hardware.type.television")) ? true : z;
        this.mHasSeenODICaptionsTooltip = Prefs.getBoolean(context, "HasSeenODICaptionsTooltip");
        this.mShowLowMediaVolumeIcon = contextThemeWrapper.getResources().getBoolean(C1777R.bool.config_showLowMediaVolumeIcon);
        this.mChangeVolumeRowTintWhenInactive = contextThemeWrapper.getResources().getBoolean(C1777R.bool.config_changeVolumeRowTintWhenInactive);
        this.mDialogShowAnimationDurationMs = contextThemeWrapper.getResources().getInteger(C1777R.integer.config_dialogShowAnimationDurationMs);
        this.mDialogHideAnimationDurationMs = contextThemeWrapper.getResources().getInteger(C1777R.integer.config_dialogHideAnimationDurationMs);
        boolean z2 = contextThemeWrapper.getResources().getBoolean(C1777R.bool.config_volumeDialogUseBackgroundBlur);
        this.mUseBackgroundBlur = z2;
        if (z2) {
            this.mCrossWindowBlurEnabledListener = new VolumeDialogImpl$$ExternalSyntheticLambda13(this, contextThemeWrapper.getColor(C1777R.color.volume_dialog_background_color_above_blur), contextThemeWrapper.getColor(C1777R.color.volume_dialog_background_color));
        }
        initDimens();
    }

    public static int getImpliedLevel(SeekBar seekBar, int i) {
        int max = seekBar.getMax();
        int i2 = max / 100;
        int i3 = i2 - 1;
        if (i == 0) {
            return 0;
        }
        if (i == max) {
            return i2;
        }
        return ((int) ((((float) i) / ((float) max)) * ((float) i3))) + 1;
    }

    public final void init(int i, VolumeDialog.Callback callback) {
        initDialog();
        Accessibility accessibility = this.mAccessibility;
        Objects.requireNonNull(accessibility);
        VolumeDialogImpl.this.mDialogView.setAccessibilityDelegate(accessibility);
        this.mController.addCallback(this.mControllerCallbackH, this.mHandler);
        this.mController.getState();
        this.mConfigurationController.addCallback(this);
    }
}
