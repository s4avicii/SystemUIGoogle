package com.android.systemui.media;

import android.app.PendingIntent;
import android.app.WallpaperColors;
import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.text.Layout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.MediaViewController;
import com.android.systemui.media.SeekBarViewModel;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class MediaControlPanel {
    public static final int[] ACTION_IDS = {C1777R.C1779id.action0, C1777R.C1779id.action1, C1777R.C1779id.action2, C1777R.C1779id.action3, C1777R.C1779id.action4};
    public static final Intent SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    public final ActivityStarter mActivityStarter;
    public int mAlbumArtSize;
    public int mBackgroundColor;
    public final Executor mBackgroundExecutor;
    public Context mContext;
    public MediaController mController;
    public int mDevicePadding;
    public final FalsingManager mFalsingManager;
    public int mInstanceId = -1;
    public boolean mIsImpressed = false;
    public String mKey;
    public MediaCarouselController mMediaCarouselController;
    public Lazy<MediaDataManager> mMediaDataManagerLazy;
    public final MediaFlags mMediaFlags;
    public final MediaOutputDialogFactory mMediaOutputDialogFactory;
    public MediaViewController mMediaViewController;
    public MediaViewHolder mMediaViewHolder;
    public RecommendationViewHolder mRecommendationViewHolder;
    public SeekBarObserver mSeekBarObserver;
    public final SeekBarViewModel mSeekBarViewModel;
    public int mSmartspaceMediaItemsCount;
    public SystemClock mSystemClock;
    public MediaSession.Token mToken;
    public int mUid = -1;

    public static void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z) {
        int i2;
        float f;
        if (z) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        Objects.requireNonNull(constraintSet);
        constraintSet.get(i).propertySet.visibility = i2;
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        constraintSet.get(i).propertySet.alpha = f;
    }

    public final void attachPlayer(MediaViewHolder mediaViewHolder, MediaViewController.TYPE type) {
        boolean z;
        this.mMediaViewHolder = mediaViewHolder;
        TransitionLayout transitionLayout = mediaViewHolder.player;
        if (type == MediaViewController.TYPE.PLAYER_SESSION) {
            z = true;
        } else {
            z = false;
        }
        this.mSeekBarObserver = new SeekBarObserver(mediaViewHolder, z);
        SeekBarViewModel seekBarViewModel = this.mSeekBarViewModel;
        Objects.requireNonNull(seekBarViewModel);
        seekBarViewModel._progress.observeForever(this.mSeekBarObserver);
        SeekBarViewModel seekBarViewModel2 = this.mSeekBarViewModel;
        SeekBar seekBar = mediaViewHolder.seekBar;
        Objects.requireNonNull(seekBarViewModel2);
        seekBar.setOnSeekBarChangeListener(new SeekBarViewModel.SeekBarChangeListener(seekBarViewModel2));
        seekBar.setOnTouchListener(new SeekBarViewModel.SeekBarTouchListener(seekBarViewModel2, seekBar));
        this.mMediaViewController.attach(transitionLayout, type);
        mediaViewHolder.player.setOnLongClickListener(new MediaControlPanel$$ExternalSyntheticLambda11(this));
        mediaViewHolder.cancel.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda3(this, 0));
        mediaViewHolder.settings.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda0(this, 0));
    }

    public final void bindPlayer(MediaData mediaData, String str) {
        int i;
        boolean z;
        float f;
        boolean z2;
        ColorScheme colorScheme;
        Drawable drawable;
        int i2;
        int[] iArr;
        boolean z3;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        Rect rect;
        MediaData mediaData2 = mediaData;
        String str2 = str;
        if (this.mMediaViewHolder != null) {
            this.mKey = str2;
            Objects.requireNonNull(mediaData);
            MediaSession.Token token = mediaData2.token;
            try {
                this.mUid = this.mContext.getPackageManager().getApplicationInfo(mediaData2.packageName, 0).uid;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("MediaControlPanel", "Unable to look up package name", e);
            }
            if (this.mInstanceId == -1) {
                this.mInstanceId = Math.abs(Math.floorMod(this.mUid + ((int) this.mSystemClock.currentTimeMillis()), 8192));
            }
            this.mBackgroundColor = mediaData2.backgroundColor;
            MediaSession.Token token2 = this.mToken;
            if (token2 == null || !token2.equals(token)) {
                this.mToken = token;
            }
            if (this.mToken != null) {
                this.mController = new MediaController(this.mContext, this.mToken);
            } else {
                this.mController = null;
            }
            PendingIntent pendingIntent = mediaData2.clickIntent;
            if (pendingIntent != null) {
                MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder);
                mediaViewHolder.player.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda8(this, pendingIntent));
            }
            MediaViewHolder mediaViewHolder2 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder2);
            boolean z4 = true;
            mediaViewHolder2.player.setContentDescription(this.mContext.getString(C1777R.string.controls_media_playing_item_description, new Object[]{mediaData2.song, mediaData2.artist, mediaData2.app}));
            MediaViewHolder mediaViewHolder3 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder3);
            mediaViewHolder3.titleText.setText(mediaData2.song);
            MediaViewHolder mediaViewHolder4 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder4);
            mediaViewHolder4.artistText.setText(mediaData2.artist);
            this.mBackgroundExecutor.execute(new KeyguardPINView$$ExternalSyntheticLambda0(this, this.mController, 1));
            boolean z5 = mediaData2.isClearable;
            MediaViewHolder mediaViewHolder5 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder5);
            TextView textView = mediaViewHolder5.longPressText;
            if (z5) {
                i = C1777R.string.controls_media_close_session;
            } else {
                i = C1777R.string.controls_media_active_session;
            }
            textView.setText(i);
            MediaViewHolder mediaViewHolder6 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder6);
            ViewGroup viewGroup = mediaViewHolder6.seamless;
            viewGroup.setVisibility(0);
            MediaViewHolder mediaViewHolder7 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder7);
            ImageView imageView = mediaViewHolder7.seamlessIcon;
            MediaViewHolder mediaViewHolder8 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder8);
            TextView textView2 = mediaViewHolder8.seamlessText;
            MediaDeviceData mediaDeviceData = mediaData2.device;
            if ((mediaDeviceData == null || mediaDeviceData.enabled) && !mediaData2.resumption) {
                z = false;
            } else {
                z = true;
            }
            float f2 = 0.38f;
            if (z) {
                f = 0.38f;
            } else {
                f = 1.0f;
            }
            MediaViewHolder mediaViewHolder9 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder9);
            mediaViewHolder9.seamlessButton.setAlpha(f);
            viewGroup.setEnabled(!z);
            CharSequence string = this.mContext.getString(C1777R.string.media_seamless_other_device);
            if (mediaDeviceData != null) {
                Drawable drawable5 = mediaDeviceData.icon;
                if (drawable5 instanceof AdaptiveIcon) {
                    AdaptiveIcon adaptiveIcon = (AdaptiveIcon) drawable5;
                    adaptiveIcon.setBackgroundColor(this.mBackgroundColor);
                    imageView.setImageDrawable(adaptiveIcon);
                } else {
                    imageView.setImageDrawable(drawable5);
                }
                string = mediaDeviceData.name;
            } else {
                imageView.setImageResource(C1777R.C1778drawable.ic_media_home_devices);
            }
            textView2.setText(string);
            viewGroup.setContentDescription(string);
            viewGroup.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda9(this, mediaDeviceData, mediaData2));
            MediaViewHolder mediaViewHolder10 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder10);
            TextView textView3 = mediaViewHolder10.dismissText;
            if (z5) {
                f2 = 1.0f;
            }
            textView3.setAlpha(f2);
            MediaViewHolder mediaViewHolder11 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder11);
            mediaViewHolder11.dismiss.setEnabled(z5);
            MediaViewHolder mediaViewHolder12 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder12);
            mediaViewHolder12.dismiss.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda6(this, str2, mediaData2, 0));
            this.mMediaViewController.refreshState();
            MediaViewHolder mediaViewHolder13 = this.mMediaViewHolder;
            if (mediaViewHolder13 instanceof PlayerViewHolder) {
                MediaViewController mediaViewController = this.mMediaViewController;
                Objects.requireNonNull(mediaViewController);
                ConstraintSet constraintSet = mediaViewController.expandedLayout;
                MediaViewController mediaViewController2 = this.mMediaViewController;
                Objects.requireNonNull(mediaViewController2);
                ConstraintSet constraintSet2 = mediaViewController2.collapsedLayout;
                MediaViewHolder mediaViewHolder14 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder14);
                ImageView imageView2 = mediaViewHolder14.albumView;
                Icon icon = mediaData2.artwork;
                if (icon == null) {
                    z4 = false;
                }
                if (z4) {
                    if (icon == null) {
                        drawable4 = null;
                    } else {
                        Drawable loadDrawable = icon.loadDrawable(this.mContext);
                        float intrinsicHeight = ((float) loadDrawable.getIntrinsicHeight()) / ((float) loadDrawable.getIntrinsicWidth());
                        if (intrinsicHeight > 1.0f) {
                            int i3 = this.mAlbumArtSize;
                            rect = new Rect(0, 0, i3, (int) (((float) i3) * intrinsicHeight));
                        } else {
                            int i4 = this.mAlbumArtSize;
                            rect = new Rect(0, 0, (int) (((float) i4) / intrinsicHeight), i4);
                        }
                        if (rect.width() > this.mAlbumArtSize || rect.height() > this.mAlbumArtSize) {
                            rect.offset((int) (-(((float) (rect.width() - this.mAlbumArtSize)) / 2.0f)), (int) (-(((float) (rect.height() - this.mAlbumArtSize)) / 2.0f)));
                        }
                        loadDrawable.setBounds(rect);
                        drawable4 = loadDrawable;
                    }
                    imageView2.setPadding(0, 0, 0, 0);
                    imageView2.setImageDrawable(drawable4);
                } else {
                    MediaDeviceData mediaDeviceData2 = mediaData2.device;
                    if (mediaDeviceData2 == null || (drawable3 = mediaDeviceData2.icon) == null) {
                        drawable2 = this.mContext.getDrawable(C1777R.C1778drawable.ic_headphone);
                    } else {
                        drawable2 = drawable3.getConstantState().newDrawable().mutate();
                    }
                    drawable2.setTintList(ColorStateList.valueOf(this.mBackgroundColor));
                    int i5 = this.mDevicePadding;
                    imageView2.setPadding(i5, i5, i5, i5);
                    imageView2.setImageDrawable(drawable2);
                }
                MediaViewHolder mediaViewHolder15 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder15);
                ImageView imageView3 = mediaViewHolder15.appIcon;
                imageView3.clearColorFilter();
                Icon icon2 = mediaData2.appIcon;
                if (icon2 == null || mediaData2.resumption) {
                    ColorMatrix colorMatrix = new ColorMatrix();
                    colorMatrix.setSaturation(0.0f);
                    imageView3.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    try {
                        imageView3.setImageDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData2.packageName));
                    } catch (PackageManager.NameNotFoundException e2) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot find icon for package ");
                        m.append(mediaData2.packageName);
                        Log.w("MediaControlPanel", m.toString(), e2);
                        imageView3.setImageResource(C1777R.C1778drawable.ic_music_note);
                    }
                } else {
                    imageView3.setImageIcon(icon2);
                    imageView3.setColorFilter(this.mContext.getColor(C1777R.color.material_dynamic_secondary10));
                }
                List<MediaAction> list = mediaData2.actions;
                List<Integer> list2 = mediaData2.actionsToShowInCompact;
                MediaFlags mediaFlags = this.mMediaFlags;
                Objects.requireNonNull(mediaFlags);
                ArrayList arrayList = list;
                ArrayList arrayList2 = list2;
                if (mediaFlags.featureFlags.isEnabled(Flags.MEDIA_SESSION_ACTIONS)) {
                    MediaButton mediaButton = mediaData2.semanticActions;
                    arrayList = list;
                    arrayList2 = list2;
                    if (mediaButton != null) {
                        ArrayList arrayList3 = new ArrayList();
                        arrayList3.add(mediaButton.startCustom);
                        arrayList3.add(mediaButton.prevOrCustom);
                        arrayList3.add(mediaButton.playOrPause);
                        arrayList3.add(mediaButton.nextOrCustom);
                        arrayList3.add(mediaButton.endCustom);
                        ArrayList arrayList4 = new ArrayList();
                        arrayList4.add(1);
                        arrayList4.add(2);
                        arrayList4.add(3);
                        arrayList = arrayList3;
                        arrayList2 = arrayList4;
                    }
                }
                int i6 = 0;
                while (i6 < arrayList.size()) {
                    int[] iArr2 = ACTION_IDS;
                    if (i6 >= iArr2.length) {
                        break;
                    }
                    int i7 = iArr2[i6];
                    boolean contains = arrayList2.contains(Integer.valueOf(i6));
                    ImageButton action = this.mMediaViewHolder.getAction(i7);
                    MediaAction mediaAction = arrayList.get(i6);
                    if (mediaAction != null) {
                        action.setImageIcon(mediaAction.icon);
                        action.setContentDescription(mediaAction.contentDescription);
                        Runnable runnable = mediaAction.action;
                        if (runnable == null) {
                            action.setEnabled(false);
                            z3 = true;
                        } else {
                            action.setEnabled(true);
                            action.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda10(this, runnable));
                            z3 = true;
                        }
                        setVisibleAndAlpha(constraintSet2, i7, contains);
                        setVisibleAndAlpha(constraintSet, i7, z3);
                    } else {
                        action.setImageIcon((Icon) null);
                        action.setContentDescription((CharSequence) null);
                        action.setEnabled(false);
                        setVisibleAndAlpha(constraintSet2, i7, contains);
                        Objects.requireNonNull(constraintSet);
                        constraintSet.get(i7).propertySet.visibility = 4;
                        constraintSet.get(i7).propertySet.alpha = 0.0f;
                    }
                    i6++;
                }
                while (true) {
                    iArr = ACTION_IDS;
                    if (i6 >= iArr.length) {
                        break;
                    }
                    setVisibleAndAlpha(constraintSet2, iArr[i6], false);
                    setVisibleAndAlpha(constraintSet, iArr[i6], false);
                    i6++;
                }
                if (arrayList.size() == 0) {
                    int i8 = iArr[0];
                    Objects.requireNonNull(constraintSet);
                    constraintSet.get(i8).propertySet.visibility = 4;
                }
            } else if (mediaViewHolder13 instanceof PlayerSessionViewHolder) {
                int i9 = this.mBackgroundColor;
                int defaultColor = Utils.getColorAttr(this.mContext, 16842806).getDefaultColor();
                int defaultColor2 = Utils.getColorAttr(this.mContext, 16842806).getDefaultColor();
                int defaultColor3 = Utils.getColorAttr(this.mContext, 16842809).getDefaultColor();
                int defaultColor4 = Utils.getColorAttr(this.mContext, 16842808).getDefaultColor();
                int defaultColor5 = Utils.getColorAttr(this.mContext, 16843282).getDefaultColor();
                MediaViewHolder mediaViewHolder16 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder16);
                ImageView imageView4 = mediaViewHolder16.appIcon;
                imageView4.clearColorFilter();
                try {
                    imageView4.setImageDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData2.packageName));
                } catch (PackageManager.NameNotFoundException e3) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot find icon for package ");
                    m2.append(mediaData2.packageName);
                    Log.w("MediaControlPanel", m2.toString(), e3);
                    Icon icon3 = mediaData2.appIcon;
                    if (icon3 != null) {
                        imageView4.setImageIcon(icon3);
                    } else {
                        imageView4.setImageResource(C1777R.C1778drawable.ic_music_note);
                    }
                    imageView4.setColorFilter(this.mContext.getColor(C1777R.color.material_dynamic_secondary10));
                }
                MediaViewHolder mediaViewHolder17 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder17);
                ImageView imageView5 = mediaViewHolder17.albumView;
                Icon icon4 = mediaData2.artwork;
                if (icon4 != null) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    colorScheme = new ColorScheme(WallpaperColors.fromBitmap(icon4.getBitmap()));
                    MediaViewHolder mediaViewHolder18 = this.mMediaViewHolder;
                    Objects.requireNonNull(mediaViewHolder18);
                    int width = mediaViewHolder18.player.getWidth();
                    MediaViewHolder mediaViewHolder19 = this.mMediaViewHolder;
                    Objects.requireNonNull(mediaViewHolder19);
                    int height = mediaViewHolder19.player.getHeight();
                    Icon icon5 = mediaData2.artwork;
                    if (icon5 == null) {
                        i2 = 0;
                        drawable = null;
                    } else {
                        Drawable loadDrawable2 = icon5.loadDrawable(this.mContext);
                        Rect rect2 = new Rect(0, 0, width, height);
                        if (rect2.width() > width || rect2.height() > height) {
                            rect2.offset((int) (-(((float) (rect2.width() - width)) / 2.0f)), (int) (-(((float) (rect2.height() - height)) / 2.0f)));
                        }
                        loadDrawable2.setBounds(rect2);
                        i2 = 0;
                        drawable = loadDrawable2;
                    }
                    imageView5.setPadding(i2, i2, i2, i2);
                    imageView5.setImageDrawable(drawable);
                    imageView5.setClipToOutline(true);
                } else {
                    try {
                        colorScheme = new ColorScheme(WallpaperColors.fromDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData2.packageName)));
                    } catch (PackageManager.NameNotFoundException e4) {
                        StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot find icon for package ");
                        m3.append(mediaData2.packageName);
                        Log.w("MediaControlPanel", m3.toString(), e4);
                        colorScheme = null;
                    }
                }
                if (colorScheme != null) {
                    i9 = ((Integer) colorScheme.accent2.get(9)).intValue();
                    defaultColor = ((Integer) colorScheme.accent1.get(2)).intValue();
                    defaultColor2 = ((Integer) colorScheme.neutral1.get(1)).intValue();
                    defaultColor3 = ((Integer) colorScheme.neutral1.get(10)).intValue();
                    defaultColor4 = ((Integer) colorScheme.neutral2.get(3)).intValue();
                    defaultColor5 = ((Integer) colorScheme.neutral2.get(5)).intValue();
                }
                ColorStateList valueOf = ColorStateList.valueOf(i9);
                ColorStateList valueOf2 = ColorStateList.valueOf(defaultColor);
                ColorStateList valueOf3 = ColorStateList.valueOf(defaultColor2);
                imageView5.setForegroundTintList(ColorStateList.valueOf(i9));
                imageView5.setBackgroundTintList(ColorStateList.valueOf(i9));
                MediaViewHolder mediaViewHolder20 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder20);
                mediaViewHolder20.player.setBackgroundTintList(valueOf);
                MediaViewHolder mediaViewHolder21 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder21);
                mediaViewHolder21.titleText.setTextColor(defaultColor2);
                MediaViewHolder mediaViewHolder22 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder22);
                mediaViewHolder22.artistText.setTextColor(defaultColor4);
                MediaViewHolder mediaViewHolder23 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder23);
                SeekBar seekBar = mediaViewHolder23.seekBar;
                seekBar.getThumb().setTintList(valueOf3);
                seekBar.setProgressTintList(valueOf3);
                seekBar.setProgressBackgroundTintList(ColorStateList.valueOf(defaultColor5));
                MediaViewHolder mediaViewHolder24 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder24);
                mediaViewHolder24.seamlessButton.setBackgroundTintList(valueOf2);
                MediaViewHolder mediaViewHolder25 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder25);
                mediaViewHolder25.seamlessIcon.setImageTintList(valueOf);
                MediaViewHolder mediaViewHolder26 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder26);
                mediaViewHolder26.seamlessText.setTextColor(i9);
                MediaButton mediaButton2 = mediaData2.semanticActions;
                if (mediaButton2 != null) {
                    PlayerSessionViewHolder playerSessionViewHolder = (PlayerSessionViewHolder) this.mMediaViewHolder;
                    Objects.requireNonNull(playerSessionViewHolder);
                    playerSessionViewHolder.actionPlayPause.setBackgroundTintList(valueOf2);
                    setSemanticButton(playerSessionViewHolder.actionPlayPause, mediaButton2.playOrPause, ColorStateList.valueOf(defaultColor3));
                    setSemanticButton(playerSessionViewHolder.actionNext, mediaButton2.nextOrCustom, valueOf3);
                    setSemanticButton(playerSessionViewHolder.actionPrev, mediaButton2.prevOrCustom, valueOf3);
                    setSemanticButton(playerSessionViewHolder.actionStart, mediaButton2.startCustom, valueOf3);
                    setSemanticButton(playerSessionViewHolder.actionEnd, mediaButton2.endCustom, valueOf3);
                } else {
                    Log.w("MediaControlPanel", "Using semantic player, but did not get buttons");
                }
                MediaViewHolder mediaViewHolder27 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder27);
                mediaViewHolder27.longPressText.setTextColor(valueOf3);
                MediaViewHolder mediaViewHolder28 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder28);
                mediaViewHolder28.settingsText.setTextColor(valueOf3);
                MediaViewHolder mediaViewHolder29 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder29);
                mediaViewHolder29.settingsText.setBackgroundTintList(valueOf2);
                MediaViewHolder mediaViewHolder30 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder30);
                mediaViewHolder30.cancelText.setTextColor(valueOf3);
                MediaViewHolder mediaViewHolder31 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder31);
                mediaViewHolder31.cancelText.setBackgroundTintList(valueOf2);
                MediaViewHolder mediaViewHolder32 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder32);
                mediaViewHolder32.dismissText.setTextColor(valueOf3);
                MediaViewHolder mediaViewHolder33 = this.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder33);
                mediaViewHolder33.dismissText.setBackgroundTintList(valueOf2);
            }
        }
    }

    public final void closeGuts(boolean z) {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(false);
        } else {
            RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                recommendationViewHolder.longPressText.getHandler().postDelayed(new RecommendationViewHolder$marquee$1(recommendationViewHolder, false), 500);
            }
        }
        MediaViewController mediaViewController = this.mMediaViewController;
        Objects.requireNonNull(mediaViewController);
        if (mediaViewController.isGutsVisible) {
            mediaViewController.isGutsVisible = false;
            if (!z) {
                mediaViewController.animateNextStateChange = true;
                mediaViewController.animationDuration = 500;
                mediaViewController.animationDelay = 0;
            }
            mediaViewController.setCurrentState(mediaViewController.currentStartLocation, mediaViewController.currentEndLocation, mediaViewController.currentTransitionProgress, z);
        }
    }

    public final int getSurfaceForSmartspaceLogging() {
        MediaViewController mediaViewController = this.mMediaViewController;
        Objects.requireNonNull(mediaViewController);
        int i = mediaViewController.currentEndLocation;
        if (i == 1 || i == 0) {
            return 4;
        }
        if (i == 2) {
            return 2;
        }
        return 0;
    }

    public final void logSmartspaceCardReported(int i, boolean z, int i2, int i3) {
        MediaCarouselController mediaCarouselController = this.mMediaCarouselController;
        int i4 = this.mInstanceId;
        int i5 = this.mUid;
        int[] iArr = {getSurfaceForSmartspaceLogging()};
        Objects.requireNonNull(mediaCarouselController);
        MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController, i, i4, i5, z, iArr, i2, i3, 0, 0, 384);
    }

    public final void openGuts() {
        Layout layout;
        boolean z;
        MediaViewController mediaViewController = this.mMediaViewController;
        Objects.requireNonNull(mediaViewController);
        ConstraintSet constraintSet = mediaViewController.expandedLayout;
        MediaViewController mediaViewController2 = this.mMediaViewController;
        Objects.requireNonNull(mediaViewController2);
        ConstraintSet constraintSet2 = mediaViewController2.collapsedLayout;
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(true);
            MediaViewHolder mediaViewHolder2 = this.mMediaViewHolder;
            Objects.requireNonNull(mediaViewHolder2);
            layout = mediaViewHolder2.settingsText.getLayout();
        } else {
            RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                recommendationViewHolder.longPressText.getHandler().postDelayed(new RecommendationViewHolder$marquee$1(recommendationViewHolder, true), 500);
                RecommendationViewHolder recommendationViewHolder2 = this.mRecommendationViewHolder;
                Objects.requireNonNull(recommendationViewHolder2);
                layout = recommendationViewHolder2.settingsText.getLayout();
            } else {
                layout = null;
            }
        }
        if (layout == null || layout.getEllipsisCount(0) <= 0) {
            z = false;
        } else {
            z = true;
        }
        MediaViewController mediaViewController3 = this.mMediaViewController;
        Objects.requireNonNull(mediaViewController3);
        mediaViewController3.shouldHideGutsSettings = z;
        if (z) {
            Objects.requireNonNull(constraintSet);
            constraintSet.get(C1777R.C1779id.settings).layout.widthMax = 0;
            Objects.requireNonNull(constraintSet2);
            constraintSet2.get(C1777R.C1779id.settings).layout.widthMax = 0;
        }
        MediaViewController mediaViewController4 = this.mMediaViewController;
        Objects.requireNonNull(mediaViewController4);
        if (!mediaViewController4.isGutsVisible) {
            mediaViewController4.isGutsVisible = true;
            mediaViewController4.animateNextStateChange = true;
            mediaViewController4.animationDuration = 500;
            mediaViewController4.animationDelay = 0;
            mediaViewController4.setCurrentState(mediaViewController4.currentStartLocation, mediaViewController4.currentEndLocation, mediaViewController4.currentTransitionProgress, false);
        }
    }

    public final void setSmartspaceRecItemOnClickListener(ViewGroup viewGroup, SmartspaceAction smartspaceAction, int i) {
        if (viewGroup == null || smartspaceAction == null || smartspaceAction.getIntent() == null || smartspaceAction.getIntent().getExtras() == null) {
            Log.e("MediaControlPanel", "No tap action can be set up");
        } else {
            viewGroup.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda7(this, i, smartspaceAction, viewGroup));
        }
    }

    public MediaControlPanel(Context context, Executor executor, ActivityStarter activityStarter, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy<MediaDataManager> lazy, MediaOutputDialogFactory mediaOutputDialogFactory, MediaCarouselController mediaCarouselController, FalsingManager falsingManager, MediaFlags mediaFlags, SystemClock systemClock) {
        this.mContext = context;
        this.mBackgroundExecutor = executor;
        this.mActivityStarter = activityStarter;
        this.mSeekBarViewModel = seekBarViewModel;
        this.mMediaViewController = mediaViewController;
        this.mMediaDataManagerLazy = lazy;
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
        this.mMediaCarouselController = mediaCarouselController;
        this.mFalsingManager = falsingManager;
        this.mMediaFlags = mediaFlags;
        this.mSystemClock = systemClock;
        this.mAlbumArtSize = context.getResources().getDimensionPixelSize(C1777R.dimen.qs_media_album_size);
        this.mDevicePadding = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.qs_media_album_device_padding);
        MediaControlPanel$$ExternalSyntheticLambda14 mediaControlPanel$$ExternalSyntheticLambda14 = new MediaControlPanel$$ExternalSyntheticLambda14(this);
        Objects.requireNonNull(seekBarViewModel);
        seekBarViewModel.logSmartspaceClick = mediaControlPanel$$ExternalSyntheticLambda14;
    }

    public static C08781 buildLaunchAnimatorController(final TransitionLayout transitionLayout) {
        if (transitionLayout.getParent() instanceof ViewGroup) {
            return new GhostedViewLaunchAnimatorController(transitionLayout) {
                public final float getCurrentTopCornerRadius() {
                    return ((IlluminationDrawable) transitionLayout.getBackground()).getCornerRadius();
                }

                public final float getCurrentBottomCornerRadius() {
                    return getCurrentTopCornerRadius();
                }

                public final void onLaunchAnimationEnd(boolean z) {
                    super.onLaunchAnimationEnd(z);
                    ((IlluminationDrawable) transitionLayout.getBackground()).setCornerRadiusOverride((Float) null);
                }

                public final void setBackgroundCornerRadius(Drawable drawable, float f, float f2) {
                    ((IlluminationDrawable) drawable).setCornerRadiusOverride(Float.valueOf(Math.min(f, f2)));
                }
            };
        }
        Log.wtf("MediaControlPanel", "Skipping player animation as it is not attached to a ViewGroup", new Exception());
        return null;
    }

    public final void setSemanticButton(ImageButton imageButton, MediaAction mediaAction, ColorStateList colorStateList) {
        imageButton.setImageTintList(colorStateList);
        if (mediaAction != null) {
            imageButton.setImageIcon(mediaAction.icon);
            imageButton.setContentDescription(mediaAction.contentDescription);
            Runnable runnable = mediaAction.action;
            if (runnable == null) {
                imageButton.setEnabled(false);
                return;
            }
            imageButton.setEnabled(true);
            imageButton.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda5(this, runnable, 0));
            return;
        }
        imageButton.setImageIcon((Icon) null);
        imageButton.setContentDescription((CharSequence) null);
        imageButton.setEnabled(false);
    }
}
