package com.android.systemui.media.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import androidx.recyclerview.R$dimen;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.settingslib.media.InfoMediaDevice;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class MediaOutputAdapter extends MediaOutputBaseAdapter {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputAdapter", 3);

    public class MediaDeviceViewHolder extends MediaOutputBaseAdapter.MediaDeviceBaseViewHolder {
        public static final /* synthetic */ int $r8$clinit = 0;

        public static void setCheckBoxColor(CheckBox checkBox, int i) {
            checkBox.setButtonTintList(new ColorStateList(new int[][]{new int[]{16842912}, new int[0]}, new int[]{i, i}));
        }

        /* JADX WARNING: Removed duplicated region for block: B:37:0x0261  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x0267  */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x028c  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onBind(com.android.settingslib.media.MediaDevice r8, boolean r9, boolean r10, int r11) {
            /*
                r7 = this;
                super.onBind(r8, r9, r10, r11)
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                boolean r10 = com.android.systemui.media.dialog.MediaOutputAdapter.DEBUG
                java.util.Objects.requireNonNull(r9)
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r10 = r8.getId()
                com.android.systemui.media.dialog.MediaOutputController r9 = r9.mController
                java.util.Objects.requireNonNull(r9)
                com.android.settingslib.media.LocalMediaManager r9 = r9.mLocalMediaManager
                java.util.Objects.requireNonNull(r9)
                com.android.settingslib.media.MediaDevice r9 = r9.mCurrentConnectedDevice
                java.lang.String r9 = r9.getId()
                boolean r9 = android.text.TextUtils.equals(r10, r9)
                if (r9 == 0) goto L_0x002e
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r10)
            L_0x002e:
                android.widget.CheckBox r10 = r7.mCheckBox
                r0 = 8
                r10.setVisibility(r0)
                android.widget.ImageView r10 = r7.mStatusIcon
                r10.setVisibility(r0)
                android.widget.LinearLayout r10 = r7.mContainerLayout
                r1 = 0
                r10.setOnClickListener(r1)
                android.widget.TextView r10 = r7.mTitleText
                com.android.systemui.media.dialog.MediaOutputAdapter r2 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r2 = r2.mController
                java.util.Objects.requireNonNull(r2)
                int r2 = r2.mColorInactiveItem
                r10.setTextColor(r2)
                android.widget.SeekBar r10 = r7.mSeekBar
                android.graphics.drawable.Drawable r10 = r10.getProgressDrawable()
                android.graphics.PorterDuffColorFilter r2 = new android.graphics.PorterDuffColorFilter
                com.android.systemui.media.dialog.MediaOutputAdapter r3 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r3 = r3.mController
                java.util.Objects.requireNonNull(r3)
                int r3 = r3.mColorSeekbarProgress
                android.graphics.PorterDuff$Mode r4 = android.graphics.PorterDuff.Mode.SRC_IN
                r2.<init>(r3, r4)
                r10.setColorFilter(r2)
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                int r2 = r10.mCurrentActivePosition
                r3 = -1
                if (r2 != r11) goto L_0x0070
                r10.mCurrentActivePosition = r3
            L_0x0070:
                int r10 = r8.mType
                r2 = 4
                r4 = 1065353216(0x3f800000, float:1.0)
                if (r10 != r2) goto L_0x008a
                boolean r10 = r8.isConnected()
                if (r10 != 0) goto L_0x008a
                android.widget.TextView r10 = r7.mTitleText
                r2 = 1056964608(0x3f000000, float:0.5)
                r10.setAlpha(r2)
                android.widget.ImageView r10 = r7.mTitleIcon
                r10.setAlpha(r2)
                goto L_0x0094
            L_0x008a:
                android.widget.TextView r10 = r7.mTitleText
                r10.setAlpha(r4)
                android.widget.ImageView r10 = r7.mTitleIcon
                r10.setAlpha(r4)
            L_0x0094:
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                boolean r10 = r10.isTransferring()
                r2 = 1
                r5 = 0
                if (r10 == 0) goto L_0x00e3
                int r9 = r8.mState
                if (r9 != r2) goto L_0x00d5
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r9 = r9.mController
                boolean r9 = r9.hasAdjustVolumeUserRestriction()
                if (r9 != 0) goto L_0x00d5
                android.widget.ProgressBar r9 = r7.mProgressBar
                android.graphics.drawable.Drawable r9 = r9.getIndeterminateDrawable()
                android.graphics.PorterDuffColorFilter r10 = new android.graphics.PorterDuffColorFilter
                com.android.systemui.media.dialog.MediaOutputAdapter r11 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r11 = r11.mController
                java.util.Objects.requireNonNull(r11)
                int r11 = r11.mColorInactiveItem
                android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.SRC_IN
                r10.<init>(r11, r0)
                r9.setColorFilter(r10)
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r8 = r8.getName()
                r7.setSingleLineLayout(r8, r5, r2, r5)
                goto L_0x03aa
            L_0x00d5:
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r8 = r8.getName()
                r7.setSingleLineLayout(r8, r5, r5, r5)
                goto L_0x03aa
            L_0x00e3:
                int r10 = r8.mState
                r6 = 3
                if (r10 != r6) goto L_0x0196
                android.widget.TextView r9 = r7.mTitleText
                r9.setAlpha(r4)
                android.widget.ImageView r9 = r7.mTitleIcon
                r9.setAlpha(r4)
                android.widget.ImageView r9 = r7.mStatusIcon
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                android.content.Context r10 = r10.mContext
                r11 = 2131232446(0x7f0806be, float:1.8081001E38)
                android.graphics.drawable.Drawable r10 = r10.getDrawable(r11)
                r9.setImageDrawable(r10)
                android.widget.ImageView r9 = r7.mStatusIcon
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.Objects.requireNonNull(r10)
                int r10 = r10.mColorInactiveItem
                r9.setColorFilter(r10)
                android.widget.TextView r9 = r7.mTitleText
                r9.setVisibility(r0)
                android.widget.RelativeLayout r9 = r7.mTwoLineLayout
                r9.setVisibility(r5)
                android.widget.ImageView r9 = r7.mStatusIcon
                r9.setVisibility(r5)
                android.widget.SeekBar r9 = r7.mSeekBar
                r9.setAlpha(r4)
                android.widget.SeekBar r9 = r7.mSeekBar
                r9.setVisibility(r0)
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r9 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                android.content.Context r9 = r9.mContext
                r10 = 2131232443(0x7f0806bb, float:1.8080995E38)
                android.graphics.drawable.Drawable r9 = r9.getDrawable(r10)
                android.graphics.drawable.Drawable r9 = r9.mutate()
                android.graphics.PorterDuffColorFilter r10 = new android.graphics.PorterDuffColorFilter
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r11 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r11 = r11.mController
                java.util.Objects.requireNonNull(r11)
                int r11 = r11.mColorItemBackground
                android.graphics.PorterDuff$Mode r1 = android.graphics.PorterDuff.Mode.SRC_IN
                r10.<init>(r11, r1)
                r9.setColorFilter(r10)
                android.widget.FrameLayout r10 = r7.mItemLayout
                r10.setBackground(r9)
                android.widget.ProgressBar r9 = r7.mProgressBar
                r9.setVisibility(r0)
                android.widget.TextView r9 = r7.mSubTitleText
                r9.setVisibility(r5)
                android.widget.TextView r9 = r7.mTwoLineTitleText
                r10 = 0
                r9.setTranslationY(r10)
                android.widget.TextView r9 = r7.mTwoLineTitleText
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r10 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                java.util.Objects.requireNonNull(r10)
                java.lang.String r10 = r8.getName()
                r9.setText(r10)
                android.widget.TextView r9 = r7.mTwoLineTitleText
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r10 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                android.content.Context r10 = r10.mContext
                r11 = 17039968(0x1040260, float:2.4246275E-38)
                java.lang.String r10 = r10.getString(r11)
                android.graphics.Typeface r10 = android.graphics.Typeface.create(r10, r5)
                r9.setTypeface(r10)
                android.widget.TextView r9 = r7.mSubTitleText
                r10 = 2131952735(0x7f13045f, float:1.9541921E38)
                r9.setText(r10)
                android.widget.LinearLayout r9 = r7.mContainerLayout
                com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda0 r10 = new com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda0
                r10.<init>(r7, r8)
                r9.setOnClickListener(r10)
                goto L_0x03aa
            L_0x0196:
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.ArrayList r10 = r10.getSelectedMediaDevice()
                int r10 = r10.size()
                if (r10 <= r2) goto L_0x029d
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.ArrayList r10 = r10.getSelectedMediaDevice()
                boolean r10 = isDeviceIncluded(r10, r8)
                if (r10 == 0) goto L_0x029d
                android.widget.TextView r9 = r7.mTitleText
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.Objects.requireNonNull(r10)
                int r10 = r10.mColorActiveItem
                r9.setTextColor(r10)
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r9 = r8.getName()
                r7.setSingleLineLayout(r9, r2, r5, r5)
                android.widget.CheckBox r9 = r7.mCheckBox
                r9.setOnCheckedChangeListener(r1)
                android.widget.CheckBox r9 = r7.mCheckBox
                r9.setVisibility(r5)
                android.widget.CheckBox r9 = r7.mCheckBox
                r9.setChecked(r2)
                android.widget.CheckBox r9 = r7.mCheckBox
                com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda1 r10 = new com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda1
                r10.<init>(r7, r8)
                r9.setOnCheckedChangeListener(r10)
                android.widget.CheckBox r8 = r7.mCheckBox
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r9 = r9.mController
                java.util.Objects.requireNonNull(r9)
                int r9 = r9.mColorActiveItem
                setCheckBoxColor(r8, r9)
                android.widget.SeekBar r8 = r7.mSeekBar
                r8.setEnabled(r5)
                android.widget.SeekBar r8 = r7.mSeekBar
                com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda0 r9 = com.android.systemui.media.dialog.C0900x9ea0900.INSTANCE
                r8.setOnTouchListener(r9)
                android.widget.SeekBar r8 = r7.mSeekBar
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r9 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r9 = r9.mController
                java.util.Objects.requireNonNull(r9)
                com.android.settingslib.media.LocalMediaManager r9 = r9.mLocalMediaManager
                java.util.Objects.requireNonNull(r9)
                com.android.settingslib.media.InfoMediaManager r9 = r9.mInfoMediaManager
                java.util.Objects.requireNonNull(r9)
                java.lang.String r10 = r9.mPackageName
                boolean r10 = android.text.TextUtils.isEmpty(r10)
                java.lang.String r11 = "InfoMediaManager"
                if (r10 == 0) goto L_0x0222
                java.lang.String r9 = "getSessionVolumeMax() package name is null or empty!"
                android.util.Log.w(r11, r9)
                goto L_0x023f
            L_0x0222:
                android.media.RoutingSessionInfo r10 = r9.getRoutingSessionInfo()
                if (r10 == 0) goto L_0x022d
                int r9 = r10.getVolumeMax()
                goto L_0x0240
            L_0x022d:
                java.lang.String r10 = "getSessionVolumeMax() can't found corresponding RoutingSession with : "
                java.lang.StringBuilder r10 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
                java.lang.String r9 = r9.mPackageName
                r10.append(r9)
                java.lang.String r9 = r10.toString()
                android.util.Log.w(r11, r9)
            L_0x023f:
                r9 = r3
            L_0x0240:
                r8.setMax(r9)
                android.widget.SeekBar r8 = r7.mSeekBar
                r8.setMin(r5)
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r8 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r8 = r8.mController
                java.util.Objects.requireNonNull(r8)
                com.android.settingslib.media.LocalMediaManager r8 = r8.mLocalMediaManager
                java.util.Objects.requireNonNull(r8)
                com.android.settingslib.media.InfoMediaManager r8 = r8.mInfoMediaManager
                java.util.Objects.requireNonNull(r8)
                java.lang.String r9 = r8.mPackageName
                boolean r9 = android.text.TextUtils.isEmpty(r9)
                if (r9 == 0) goto L_0x0267
                java.lang.String r8 = "getSessionVolume() package name is null or empty!"
                android.util.Log.w(r11, r8)
                goto L_0x0284
            L_0x0267:
                android.media.RoutingSessionInfo r9 = r8.getRoutingSessionInfo()
                if (r9 == 0) goto L_0x0272
                int r3 = r9.getVolume()
                goto L_0x0284
            L_0x0272:
                java.lang.String r9 = "getSessionVolume() can't found corresponding RoutingSession with : "
                java.lang.StringBuilder r9 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r9)
                java.lang.String r8 = r8.mPackageName
                r9.append(r8)
                java.lang.String r8 = r9.toString()
                android.util.Log.w(r11, r8)
            L_0x0284:
                android.widget.SeekBar r8 = r7.mSeekBar
                int r8 = r8.getProgress()
                if (r8 == r3) goto L_0x0291
                android.widget.SeekBar r8 = r7.mSeekBar
                r8.setProgress(r3)
            L_0x0291:
                android.widget.SeekBar r8 = r7.mSeekBar
                com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$2 r9 = new com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$2
                r9.<init>()
                r8.setOnSeekBarChangeListener(r9)
                goto L_0x03aa
            L_0x029d:
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                boolean r10 = r10.hasAdjustVolumeUserRestriction()
                if (r10 != 0) goto L_0x0347
                if (r9 == 0) goto L_0x0347
                android.widget.ImageView r9 = r7.mStatusIcon
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                android.content.Context r10 = r10.mContext
                r0 = 2131232445(0x7f0806bd, float:1.8081E38)
                android.graphics.drawable.Drawable r10 = r10.getDrawable(r0)
                r9.setImageDrawable(r10)
                android.widget.ImageView r9 = r7.mStatusIcon
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.Objects.requireNonNull(r10)
                int r10 = r10.mColorActiveItem
                r9.setColorFilter(r10)
                android.widget.TextView r9 = r7.mTitleText
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.Objects.requireNonNull(r10)
                int r10 = r10.mColorActiveItem
                r9.setTextColor(r10)
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r9 = r8.getName()
                r7.setSingleLineLayout(r9, r2, r5, r2)
                com.android.systemui.media.dialog.MediaOutputBaseAdapter r9 = com.android.systemui.media.dialog.MediaOutputBaseAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r9 = r9.mController
                java.util.Objects.requireNonNull(r9)
                boolean r10 = com.android.systemui.media.dialog.MediaOutputController.isActiveRemoteDevice(r8)
                if (r10 == 0) goto L_0x02f4
                boolean r9 = r9.mVolumeAdjustmentForRemoteGroupSessions
                if (r9 == 0) goto L_0x02f3
                goto L_0x02f4
            L_0x02f3:
                r2 = r5
            L_0x02f4:
                if (r2 != 0) goto L_0x0302
                android.widget.SeekBar r9 = r7.mSeekBar
                r9.setEnabled(r5)
                android.widget.SeekBar r9 = r7.mSeekBar
                com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda0 r10 = com.android.systemui.media.dialog.C0900x9ea0900.INSTANCE
                r9.setOnTouchListener(r10)
            L_0x0302:
                android.widget.SeekBar r9 = r7.mSeekBar
                android.media.MediaRoute2Info r10 = r8.mRouteInfo
                java.lang.String r0 = "MediaDevice"
                if (r10 != 0) goto L_0x0311
                java.lang.String r10 = "Unable to get max volume. RouteInfo is empty"
                android.util.Log.w(r0, r10)
                r10 = r5
                goto L_0x0315
            L_0x0311:
                int r10 = r10.getVolumeMax()
            L_0x0315:
                r9.setMax(r10)
                android.widget.SeekBar r9 = r7.mSeekBar
                r9.setMin(r5)
                android.media.MediaRoute2Info r9 = r8.mRouteInfo
                if (r9 != 0) goto L_0x0327
                java.lang.String r9 = "Unable to get current volume. RouteInfo is empty"
                android.util.Log.w(r0, r9)
                goto L_0x032b
            L_0x0327:
                int r5 = r9.getVolume()
            L_0x032b:
                android.widget.SeekBar r9 = r7.mSeekBar
                int r9 = r9.getProgress()
                if (r9 == r5) goto L_0x0338
                android.widget.SeekBar r9 = r7.mSeekBar
                r9.setProgress(r5)
            L_0x0338:
                android.widget.SeekBar r9 = r7.mSeekBar
                com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$1 r10 = new com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$1
                r10.<init>(r8)
                r9.setOnSeekBarChangeListener(r10)
                com.android.systemui.media.dialog.MediaOutputAdapter r7 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                r7.mCurrentActivePosition = r11
                goto L_0x03aa
            L_0x0347:
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r9 = r9.mController
                java.util.ArrayList r9 = r9.getSelectableMediaDevice()
                boolean r9 = isDeviceIncluded(r9, r8)
                if (r9 == 0) goto L_0x0393
                android.widget.CheckBox r9 = r7.mCheckBox
                r9.setOnCheckedChangeListener(r1)
                android.widget.CheckBox r9 = r7.mCheckBox
                r9.setVisibility(r5)
                android.widget.CheckBox r9 = r7.mCheckBox
                r9.setChecked(r5)
                android.widget.CheckBox r9 = r7.mCheckBox
                com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda2 r10 = new com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda2
                r10.<init>(r7, r8)
                r9.setOnCheckedChangeListener(r10)
                android.widget.CheckBox r9 = r7.mCheckBox
                com.android.systemui.media.dialog.MediaOutputAdapter r10 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                com.android.systemui.media.dialog.MediaOutputController r10 = r10.mController
                java.util.Objects.requireNonNull(r10)
                int r10 = r10.mColorInactiveItem
                setCheckBoxColor(r9, r10)
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r9 = r8.getName()
                r7.setSingleLineLayout(r9, r5, r5, r5)
                android.widget.LinearLayout r9 = r7.mContainerLayout
                com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda12 r10 = new com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda12
                r10.<init>(r7, r8, r2)
                r9.setOnClickListener(r10)
                goto L_0x03aa
            L_0x0393:
                com.android.systemui.media.dialog.MediaOutputAdapter r9 = com.android.systemui.media.dialog.MediaOutputAdapter.this
                java.util.Objects.requireNonNull(r9)
                java.lang.String r9 = r8.getName()
                r7.setSingleLineLayout(r9, r5, r5, r5)
                android.widget.LinearLayout r9 = r7.mContainerLayout
                com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda11 r10 = new com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda11
                r11 = 2
                r10.<init>(r7, r8, r11)
                r9.setOnClickListener(r10)
            L_0x03aa:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.dialog.MediaOutputAdapter.MediaDeviceViewHolder.onBind(com.android.settingslib.media.MediaDevice, boolean, boolean, int):void");
        }

        public MediaDeviceViewHolder(View view) {
            super(view);
        }

        public final void onCheckBoxClicked(boolean z, MediaDevice mediaDevice) {
            if (z && isDeviceIncluded(MediaOutputAdapter.this.mController.getSelectableMediaDevice(), mediaDevice)) {
                MediaOutputController mediaOutputController = MediaOutputAdapter.this.mController;
                Objects.requireNonNull(mediaOutputController);
                LocalMediaManager localMediaManager = mediaOutputController.mLocalMediaManager;
                Objects.requireNonNull(localMediaManager);
                InfoMediaManager infoMediaManager = localMediaManager.mInfoMediaManager;
                Objects.requireNonNull(infoMediaManager);
                if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
                    Log.w("InfoMediaManager", "addDeviceToPlayMedia() package name is null or empty!");
                    return;
                }
                RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
                if (routingSessionInfo == null || !routingSessionInfo.getSelectableRoutes().contains(mediaDevice.mRouteInfo.getId())) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("addDeviceToPlayMedia() Ignoring selecting a non-selectable device : ");
                    m.append(mediaDevice.getName());
                    Log.w("InfoMediaManager", m.toString());
                    return;
                }
                infoMediaManager.mRouterManager.selectRoute(routingSessionInfo, mediaDevice.mRouteInfo);
            } else if (!z) {
                MediaOutputController mediaOutputController2 = MediaOutputAdapter.this.mController;
                Objects.requireNonNull(mediaOutputController2);
                LocalMediaManager localMediaManager2 = mediaOutputController2.mLocalMediaManager;
                Objects.requireNonNull(localMediaManager2);
                InfoMediaManager infoMediaManager2 = localMediaManager2.mInfoMediaManager;
                Objects.requireNonNull(infoMediaManager2);
                ArrayList arrayList = new ArrayList();
                if (TextUtils.isEmpty(infoMediaManager2.mPackageName)) {
                    Log.d("InfoMediaManager", "getDeselectableMediaDevice() package name is null or empty!");
                } else {
                    RoutingSessionInfo routingSessionInfo2 = infoMediaManager2.getRoutingSessionInfo();
                    if (routingSessionInfo2 != null) {
                        for (MediaRoute2Info mediaRoute2Info : infoMediaManager2.mRouterManager.getDeselectableRoutes(routingSessionInfo2)) {
                            arrayList.add(new InfoMediaDevice(infoMediaManager2.mContext, infoMediaManager2.mRouterManager, mediaRoute2Info, infoMediaManager2.mPackageName));
                            StringBuilder sb = new StringBuilder();
                            sb.append(mediaRoute2Info.getName());
                            sb.append(" is deselectable for ");
                            ExifInterface$$ExternalSyntheticOutline2.m15m(sb, infoMediaManager2.mPackageName, "InfoMediaManager");
                        }
                    } else {
                        ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("getDeselectableMediaDevice() cannot found deselectable MediaDevice from : "), infoMediaManager2.mPackageName, "InfoMediaManager");
                    }
                }
                if (isDeviceIncluded(arrayList, mediaDevice)) {
                    MediaOutputController mediaOutputController3 = MediaOutputAdapter.this.mController;
                    Objects.requireNonNull(mediaOutputController3);
                    LocalMediaManager localMediaManager3 = mediaOutputController3.mLocalMediaManager;
                    Objects.requireNonNull(localMediaManager3);
                    InfoMediaManager infoMediaManager3 = localMediaManager3.mInfoMediaManager;
                    Objects.requireNonNull(infoMediaManager3);
                    if (TextUtils.isEmpty(infoMediaManager3.mPackageName)) {
                        Log.w("InfoMediaManager", "removeDeviceFromMedia() package name is null or empty!");
                        return;
                    }
                    RoutingSessionInfo routingSessionInfo3 = infoMediaManager3.getRoutingSessionInfo();
                    if (routingSessionInfo3 == null || !routingSessionInfo3.getSelectedRoutes().contains(mediaDevice.mRouteInfo.getId())) {
                        StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("removeDeviceFromMedia() Ignoring deselecting a non-deselectable device : ");
                        m2.append(mediaDevice.getName());
                        Log.w("InfoMediaManager", m2.toString());
                        return;
                    }
                    infoMediaManager3.mRouterManager.deselectRoute(routingSessionInfo3, mediaDevice.mRouteInfo);
                }
            }
        }

        public final void onItemClick(MediaDevice mediaDevice) {
            if (!MediaOutputAdapter.this.mController.isTransferring()) {
                MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
                Objects.requireNonNull(mediaOutputAdapter);
                String id = mediaDevice.getId();
                MediaOutputController mediaOutputController = mediaOutputAdapter.mController;
                Objects.requireNonNull(mediaOutputController);
                LocalMediaManager localMediaManager = mediaOutputController.mLocalMediaManager;
                Objects.requireNonNull(localMediaManager);
                if (TextUtils.equals(id, localMediaManager.mCurrentConnectedDevice.getId())) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("This device is already connected! : ");
                    m.append(mediaDevice.getName());
                    Log.d("MediaOutputAdapter", m.toString());
                    return;
                }
                MediaOutputAdapter mediaOutputAdapter2 = MediaOutputAdapter.this;
                mediaOutputAdapter2.mCurrentActivePosition = -1;
                MediaOutputController mediaOutputController2 = mediaOutputAdapter2.mController;
                Objects.requireNonNull(mediaOutputController2);
                MediaOutputMetricLogger mediaOutputMetricLogger = mediaOutputController2.mMetricLogger;
                LocalMediaManager localMediaManager2 = mediaOutputController2.mLocalMediaManager;
                Objects.requireNonNull(localMediaManager2);
                MediaDevice mediaDevice2 = localMediaManager2.mCurrentConnectedDevice;
                Objects.requireNonNull(mediaOutputMetricLogger);
                mediaOutputMetricLogger.mSourceDevice = mediaDevice2;
                mediaOutputMetricLogger.mTargetDevice = mediaDevice;
                if (MediaOutputMetricLogger.DEBUG) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("updateOutputEndPoints - source:");
                    m2.append(mediaOutputMetricLogger.mSourceDevice.toString());
                    m2.append(" target:");
                    m2.append(mediaOutputMetricLogger.mTargetDevice.toString());
                    Log.d("MediaOutputMetricLogger", m2.toString());
                }
                R$dimen.postOnBackgroundThread(new ClockManager$$ExternalSyntheticLambda1(mediaOutputController2, mediaDevice, 1));
                mediaDevice.mState = 1;
                Objects.requireNonNull(MediaOutputAdapter.this);
                MediaOutputAdapter.this.notifyDataSetChanged();
            }
        }

        public static boolean isDeviceIncluded(ArrayList arrayList, MediaDevice mediaDevice) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(((MediaDevice) it.next()).getId(), mediaDevice.getId())) {
                    return true;
                }
            }
            return false;
        }

        public final void onBind() {
            TextView textView = this.mTitleText;
            MediaOutputController mediaOutputController = MediaOutputAdapter.this.mController;
            Objects.requireNonNull(mediaOutputController);
            textView.setTextColor(mediaOutputController.mColorInactiveItem);
            this.mCheckBox.setVisibility(8);
            setSingleLineLayout(MediaOutputAdapter.this.mContext.getText(C1777R.string.media_output_dialog_pairing_new), false, false, false);
            Drawable drawable = MediaOutputAdapter.this.mContext.getDrawable(C1777R.C1778drawable.ic_add);
            drawable.setColorFilter(new PorterDuffColorFilter(Utils.getColorAttrDefaultColor(MediaOutputAdapter.this.mContext, 16843829), PorterDuff.Mode.SRC_IN));
            this.mTitleIcon.setImageDrawable(drawable);
            LinearLayout linearLayout = this.mContainerLayout;
            MediaOutputController mediaOutputController2 = MediaOutputAdapter.this.mController;
            Objects.requireNonNull(mediaOutputController2);
            linearLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda4(mediaOutputController2, 1));
        }
    }

    public final int getItemCount() {
        if (this.mController.isZeroMode()) {
            MediaOutputController mediaOutputController = this.mController;
            Objects.requireNonNull(mediaOutputController);
            return mediaOutputController.mMediaDevices.size() + 1;
        }
        MediaOutputController mediaOutputController2 = this.mController;
        Objects.requireNonNull(mediaOutputController2);
        return mediaOutputController2.mMediaDevices.size();
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = (MediaOutputBaseAdapter.MediaDeviceBaseViewHolder) viewHolder;
        MediaOutputController mediaOutputController = this.mController;
        Objects.requireNonNull(mediaOutputController);
        int size = mediaOutputController.mMediaDevices.size();
        if (i == size && this.mController.isZeroMode()) {
            mediaDeviceBaseViewHolder.onBind();
        } else if (i < size) {
            MediaOutputController mediaOutputController2 = this.mController;
            Objects.requireNonNull(mediaOutputController2);
            MediaDevice mediaDevice = mediaOutputController2.mMediaDevices.get(i);
            boolean z2 = false;
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            if (i == size - 1) {
                z2 = true;
            }
            mediaDeviceBaseViewHolder.onBind(mediaDevice, z, z2, i);
        } else if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("Incorrect position: ", i, "MediaOutputAdapter");
        }
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        Context context = recyclerView.getContext();
        this.mContext = context;
        context.getResources().getDimensionPixelSize(C1777R.dimen.media_output_dialog_list_margin);
        this.mHolderView = LayoutInflater.from(this.mContext).inflate(C1777R.layout.media_output_list_item, recyclerView, false);
        return new MediaDeviceViewHolder(this.mHolderView);
    }

    public MediaOutputAdapter(MediaOutputController mediaOutputController) {
        super(mediaOutputController);
    }
}
