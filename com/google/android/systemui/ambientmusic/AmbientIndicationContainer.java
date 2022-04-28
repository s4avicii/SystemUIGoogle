package com.google.android.systemui.ambientmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.media.MediaMetadata;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.Dependency;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Objects;

public class AmbientIndicationContainer extends AutoReinflateContainer implements DozeReceiver, StatusBarStateController.StateListener, NotificationMediaManager.MediaListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Drawable mAmbientIconOverride;
    public int mAmbientIndicationIconSize;
    public Drawable mAmbientMusicAnimation;
    public Drawable mAmbientMusicNoteIcon;
    public int mAmbientMusicNoteIconIconSize;
    public CharSequence mAmbientMusicText;
    public boolean mAmbientSkipUnlock;
    public int mBottomMarginPx;
    public boolean mDozing;
    public PendingIntent mFavoritingIntent;
    public final Handler mHandler;
    public final Rect mIconBounds = new Rect();
    public String mIconDescription;
    public int mIconOverride = -1;
    public ImageView mIconView;
    public int mIndicationTextMode;
    public int mMediaPlaybackState;
    public PendingIntent mOpenIntent;
    public Drawable mReverseChargingAnimation;
    public CharSequence mReverseChargingMessage;
    public StatusBar mStatusBar;
    public int mStatusBarState;
    public int mTextColor;
    public ValueAnimator mTextColorAnimator;
    public TextView mTextView;
    public final WakeLock mWakeLock;
    public CharSequence mWirelessChargingMessage;

    @VisibleForTesting
    public WakeLock createWakeLock(Context context, Handler handler) {
        return new DelayedWakeLock(handler, WakeLock.createPartial(context, "AmbientIndication"));
    }

    public final void onDozingChanged(boolean z) {
        this.mDozing = z;
        if (this.mStatusBarState == 1) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setEnabled(!z);
            updateColors();
        }
    }

    public final void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        if (this.mMediaPlaybackState != i) {
            this.mMediaPlaybackState = i;
            if (NotificationMediaManager.isPlayingState(i)) {
                setAmbientMusic((CharSequence) null, (PendingIntent) null, (PendingIntent) null, 0, false, (String) null);
            }
        }
    }

    public final void onStateChanged(int i) {
        this.mStatusBarState = i;
        if (i == 1) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
    }

    public final void onTextClick(View view) {
        if (this.mOpenIntent != null) {
            this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), view, "AMBIENT_MUSIC_CLICK");
            if (this.mAmbientSkipUnlock) {
                sendBroadcastWithoutDismissingKeyguard(this.mOpenIntent);
            } else {
                this.mStatusBar.startPendingIntentDismissingKeyguard(this.mOpenIntent);
            }
        }
    }

    public final void setAmbientMusic(CharSequence charSequence, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i, boolean z, String str) {
        Drawable drawable;
        if (!Objects.equals(this.mAmbientMusicText, charSequence) || !Objects.equals(this.mOpenIntent, pendingIntent) || !Objects.equals(this.mFavoritingIntent, pendingIntent2) || this.mIconOverride != i || !Objects.equals(this.mIconDescription, str) || this.mAmbientSkipUnlock != z) {
            this.mAmbientMusicText = charSequence;
            this.mOpenIntent = pendingIntent;
            this.mFavoritingIntent = pendingIntent2;
            this.mAmbientSkipUnlock = z;
            this.mIconOverride = i;
            this.mIconDescription = str;
            Context context = this.mContext;
            switch (i) {
                case 1:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_music_search);
                    break;
                case 3:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_music_not_found);
                    break;
                case 4:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_cloud_off);
                    break;
                case 5:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_favorite);
                    break;
                case FalsingManager.VERSION:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_favorite_border);
                    break;
                case 7:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_error);
                    break;
                case 8:
                    drawable = context.getDrawable(C1777R.C1778drawable.ic_favorite_note);
                    break;
                default:
                    drawable = null;
                    break;
            }
            this.mAmbientIconOverride = drawable;
            updatePill();
        }
    }

    public final void updateColors() {
        int i;
        ValueAnimator valueAnimator = this.mTextColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTextColorAnimator.cancel();
        }
        int defaultColor = this.mTextView.getTextColors().getDefaultColor();
        if (this.mDozing) {
            i = -1;
        } else {
            i = this.mTextColor;
        }
        if (defaultColor == i) {
            this.mTextView.setTextColor(i);
            this.mIconView.setImageTintList(ColorStateList.valueOf(i));
            return;
        }
        ValueAnimator ofArgb = ValueAnimator.ofArgb(new int[]{defaultColor, i});
        this.mTextColorAnimator = ofArgb;
        ofArgb.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mTextColorAnimator.setDuration(500);
        this.mTextColorAnimator.addUpdateListener(new AuthBiometricView$$ExternalSyntheticLambda0(this, 1));
        this.mTextColorAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                AmbientIndicationContainer.this.mTextColorAnimator = null;
            }
        });
        this.mTextColorAnimator.start();
    }

    public final void updatePill() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        CharSequence charSequence;
        Drawable drawable;
        int i;
        int i2;
        Drawable drawable2;
        TextView textView = this.mTextView;
        if (textView != null) {
            int i3 = this.mIndicationTextMode;
            boolean z5 = true;
            this.mIndicationTextMode = 1;
            CharSequence charSequence2 = this.mAmbientMusicText;
            int i4 = 0;
            if (textView.getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            CharSequence charSequence3 = this.mAmbientMusicText;
            if (charSequence3 == null || charSequence3.length() != 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            TextView textView2 = this.mTextView;
            if (this.mOpenIntent != null) {
                z3 = true;
            } else {
                z3 = false;
            }
            textView2.setClickable(z3);
            ImageView imageView = this.mIconView;
            if (this.mFavoritingIntent == null && this.mOpenIntent == null) {
                z4 = false;
            } else {
                z4 = true;
            }
            imageView.setClickable(z4);
            if (TextUtils.isEmpty(this.mIconDescription)) {
                charSequence = charSequence2;
            } else {
                charSequence = this.mIconDescription;
            }
            Drawable drawable3 = null;
            if (!TextUtils.isEmpty(this.mReverseChargingMessage)) {
                this.mIndicationTextMode = 2;
                charSequence2 = this.mReverseChargingMessage;
                if (this.mReverseChargingAnimation == null) {
                    this.mReverseChargingAnimation = this.mContext.getDrawable(C1777R.anim.reverse_charging_animation);
                }
                Drawable drawable4 = this.mReverseChargingAnimation;
                this.mTextView.setClickable(false);
                this.mIconView.setClickable(false);
                charSequence = null;
                drawable3 = drawable4;
                z2 = false;
            } else if (!TextUtils.isEmpty(this.mWirelessChargingMessage)) {
                this.mIndicationTextMode = 3;
                charSequence2 = this.mWirelessChargingMessage;
                this.mTextView.setClickable(false);
                this.mIconView.setClickable(false);
                z2 = false;
                charSequence = null;
            } else if ((!TextUtils.isEmpty(charSequence2) || z2) && (drawable3 = this.mAmbientIconOverride) == null) {
                if (z) {
                    if (this.mAmbientMusicNoteIcon == null) {
                        this.mAmbientMusicNoteIcon = this.mContext.getDrawable(C1777R.C1778drawable.ic_music_note);
                    }
                    drawable2 = this.mAmbientMusicNoteIcon;
                } else {
                    if (this.mAmbientMusicAnimation == null) {
                        this.mAmbientMusicAnimation = this.mContext.getDrawable(C1777R.anim.audioanim_animation);
                    }
                    drawable2 = this.mAmbientMusicAnimation;
                }
                drawable3 = drawable2;
            }
            this.mTextView.setText(charSequence2);
            this.mTextView.setContentDescription(charSequence2);
            this.mIconView.setContentDescription(charSequence);
            if (drawable3 != null) {
                this.mIconBounds.set(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
                Rect rect = this.mIconBounds;
                if (drawable3 == this.mAmbientMusicNoteIcon) {
                    i = this.mAmbientMusicNoteIconIconSize;
                } else {
                    i = this.mAmbientIndicationIconSize;
                }
                MathUtils.fitRect(rect, i);
                drawable = new DrawableWrapper(drawable3) {
                    public final int getIntrinsicHeight() {
                        return AmbientIndicationContainer.this.mIconBounds.height();
                    }

                    public final int getIntrinsicWidth() {
                        return AmbientIndicationContainer.this.mIconBounds.width();
                    }
                };
                if (!TextUtils.isEmpty(charSequence2)) {
                    i2 = (int) (getResources().getDisplayMetrics().density * 24.0f);
                } else {
                    i2 = 0;
                }
                TextView textView3 = this.mTextView;
                textView3.setPaddingRelative(textView3.getPaddingStart(), this.mTextView.getPaddingTop(), i2, this.mTextView.getPaddingBottom());
            } else {
                TextView textView4 = this.mTextView;
                textView4.setPaddingRelative(textView4.getPaddingStart(), this.mTextView.getPaddingTop(), 0, this.mTextView.getPaddingBottom());
                drawable = drawable3;
            }
            this.mIconView.setImageDrawable(drawable);
            if (TextUtils.isEmpty(charSequence2) && !z2) {
                z5 = false;
            }
            if (!z5) {
                i4 = 8;
            }
            this.mTextView.setVisibility(i4);
            if (drawable3 == null) {
                this.mIconView.setVisibility(8);
            } else {
                this.mIconView.setVisibility(i4);
            }
            if (!z5) {
                this.mTextView.animate().cancel();
                if (drawable3 != null && (drawable3 instanceof AnimatedVectorDrawable)) {
                    ((AnimatedVectorDrawable) drawable3).reset();
                }
                this.mHandler.post(this.mWakeLock.wrap(AmbientIndicationContainer$$ExternalSyntheticLambda2.INSTANCE));
            } else if (!z) {
                this.mWakeLock.acquire("AmbientIndication");
                if (drawable3 != null && (drawable3 instanceof AnimatedVectorDrawable)) {
                    ((AnimatedVectorDrawable) drawable3).start();
                }
                TextView textView5 = this.mTextView;
                textView5.setTranslationY((float) (textView5.getHeight() / 2));
                this.mTextView.setAlpha(0.0f);
                this.mTextView.animate().alpha(1.0f).translationY(0.0f).setStartDelay(150).setDuration(100).setListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        AmbientIndicationContainer.this.mWakeLock.release("AmbientIndication");
                        AmbientIndicationContainer.this.mTextView.animate().setListener((Animator.AnimatorListener) null);
                    }
                }).setInterpolator(Interpolators.DECELERATE_QUINT).start();
            } else if (i3 == this.mIndicationTextMode) {
                this.mHandler.post(this.mWakeLock.wrap(AmbientIndicationContainer$$ExternalSyntheticLambda2.INSTANCE));
            } else if (drawable3 != null && (drawable3 instanceof AnimatedVectorDrawable)) {
                this.mWakeLock.acquire("AmbientIndication");
                ((AnimatedVectorDrawable) drawable3).start();
                this.mWakeLock.release("AmbientIndication");
            }
            updateBottomSpacing();
        }
    }

    public AmbientIndicationContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mWakeLock = createWakeLock(this.mContext, handler);
    }

    public static void sendBroadcastWithoutDismissingKeyguard(PendingIntent pendingIntent) {
        if (!pendingIntent.isActivity()) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.w("AmbientIndication", "Sending intent failed: " + e);
            }
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).addCallback(this);
        ((NotificationMediaManager) Dependency.get(NotificationMediaManager.class)).addCallback(this);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).removeCallback(this);
        NotificationMediaManager notificationMediaManager = (NotificationMediaManager) Dependency.get(NotificationMediaManager.class);
        Objects.requireNonNull(notificationMediaManager);
        notificationMediaManager.mMediaListeners.remove(this);
        this.mMediaPlaybackState = 0;
    }

    public final void updateBottomSpacing() {
        boolean z;
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.ambient_indication_margin_bottom);
        if (this.mBottomMarginPx != dimensionPixelSize) {
            this.mBottomMarginPx = dimensionPixelSize;
            ((FrameLayout.LayoutParams) getLayoutParams()).bottomMargin = this.mBottomMarginPx;
        }
        int i = 0;
        if (this.mTextView.getVisibility() == 0) {
            z = true;
        } else {
            z = false;
        }
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        NotificationPanelViewController notificationPanelViewController = statusBar.mNotificationPanelViewController;
        int top = getTop();
        Objects.requireNonNull(notificationPanelViewController);
        if (z) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            i = notificationStackScrollLayoutController.mView.getBottom() - top;
        }
        if (notificationPanelViewController.mAmbientIndicationBottomPadding != i) {
            notificationPanelViewController.mAmbientIndicationBottomPadding = i;
            notificationPanelViewController.updateMaxDisplayedNotifications(true);
        }
    }

    public final void dozeTimeTick() {
        updatePill();
    }
}
