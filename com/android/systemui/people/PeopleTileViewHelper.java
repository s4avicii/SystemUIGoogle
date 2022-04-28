package com.android.systemui.people;

import android.app.people.ConversationStatus;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.Pair;
import android.util.SizeF;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import com.android.internal.annotations.VisibleForTesting;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.people.PeopleStoryIconFactory;
import com.android.systemui.people.widget.PeopleTileKey;
import com.android.systemui.plugins.FalsingManager;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import kotlinx.atomicfu.AtomicFU;

public final class PeopleTileViewHelper {
    public static final Pattern ANY_DOUBLE_MARK_PATTERN = Pattern.compile("[!?][!?]+");
    public static final Pattern DOUBLE_EXCLAMATION_PATTERN = Pattern.compile("[!][!]+");
    public static final Pattern DOUBLE_QUESTION_PATTERN = Pattern.compile("[?][?]+");
    public static final Pattern EMOJI_PATTERN = Pattern.compile("\\p{RI}\\p{RI}|(\\p{Emoji}(\\p{EMod}|\\x{FE0F}\\x{20E3}?|[\\x{E0020}-\\x{E007E}]+\\x{E007F})|[\\p{Emoji}&&\\p{So}])(\\x{200D}\\p{Emoji}(\\p{EMod}|\\x{FE0F}\\x{20E3}?|[\\x{E0020}-\\x{E007E}]+\\x{E007F})?)*");
    public static final Pattern MIXED_MARK_PATTERN = Pattern.compile("![?].*|.*[?]!");
    public int mAppWidgetId;
    public Context mContext;
    public float mDensity;
    public int mHeight;
    public NumberFormat mIntegerFormat;
    public boolean mIsLeftToRight;
    public PeopleTileKey mKey;
    public int mLayoutSize;
    public Locale mLocale;
    public int mMediumVerticalPadding;
    public PeopleSpaceTile mTile;
    public int mWidth;

    public static Bitmap getPersonIconBitmap(Context context, PeopleSpaceTile peopleSpaceTile, int i, boolean z) {
        Drawable drawable;
        Context context2 = context;
        Icon userIcon = peopleSpaceTile.getUserIcon();
        if (userIcon == null) {
            Drawable mutate = context.getDrawable(C1777R.C1778drawable.ic_avatar_with_badge).mutate();
            mutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
            return PeopleSpaceUtils.convertDrawableToBitmap(mutate);
        }
        PackageManager packageManager = context.getPackageManager();
        IconDrawableFactory.newInstance(context, false);
        PeopleStoryIconFactory peopleStoryIconFactory = new PeopleStoryIconFactory(context, packageManager, i);
        RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(context.getResources(), userIcon.getBitmap());
        String packageName = peopleSpaceTile.getPackageName();
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        int identifier = peopleSpaceTile.getUserHandle().getIdentifier();
        boolean isImportantConversation = peopleSpaceTile.isImportantConversation();
        try {
            drawable = Utils.getBadgedIcon(peopleStoryIconFactory.mContext, peopleStoryIconFactory.mPackageManager.getApplicationInfoAsUser(packageName, 128, identifier));
        } catch (PackageManager.NameNotFoundException unused) {
            drawable = peopleStoryIconFactory.mPackageManager.getDefaultActivityIcon();
        }
        PeopleStoryIconFactory.PeopleStoryIconDrawable peopleStoryIconDrawable = new PeopleStoryIconFactory.PeopleStoryIconDrawable(roundedBitmapDrawable21, drawable, peopleStoryIconFactory.mIconBitmapSize, peopleStoryIconFactory.mImportantConversationColor, isImportantConversation, peopleStoryIconFactory.mIconSize, peopleStoryIconFactory.mDensity, peopleStoryIconFactory.mAccentColor, z);
        if (isDndBlockingTileData(peopleSpaceTile)) {
            peopleStoryIconDrawable.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
        }
        return PeopleSpaceUtils.convertDrawableToBitmap(peopleStoryIconDrawable);
    }

    public static boolean isDndBlockingTileData(PeopleSpaceTile peopleSpaceTile) {
        if (peopleSpaceTile == null) {
            return false;
        }
        int notificationPolicyState = peopleSpaceTile.getNotificationPolicyState();
        if ((notificationPolicyState & 1) != 0) {
            return false;
        }
        if ((notificationPolicyState & 4) != 0 && peopleSpaceTile.isImportantConversation()) {
            return false;
        }
        if ((notificationPolicyState & 8) != 0 && peopleSpaceTile.getContactAffinity() == 1.0f) {
            return false;
        }
        if ((notificationPolicyState & 16) == 0 || (peopleSpaceTile.getContactAffinity() != 0.5f && peopleSpaceTile.getContactAffinity() != 1.0f)) {
            return !peopleSpaceTile.canBypassDnd();
        }
        return false;
    }

    public static final class RemoteViewsAndSizes {
        public final int mAvatarSize;
        public final RemoteViews mRemoteViews;

        public RemoteViewsAndSizes(RemoteViews remoteViews, int i) {
            this.mRemoteViews = remoteViews;
            this.mAvatarSize = i;
        }
    }

    public final RemoteViewsAndSizes createDndRemoteViews() {
        int i;
        int i2;
        int i3;
        StaticLayout staticLayout;
        int i4;
        String packageName = this.mContext.getPackageName();
        int i5 = this.mLayoutSize;
        if (i5 == 1) {
            i = C1777R.layout.people_tile_with_suppression_detail_content_horizontal;
        } else if (i5 != 2) {
            i = getLayoutSmallByHeight();
        } else {
            i = C1777R.layout.people_tile_with_suppression_detail_content_vertical;
        }
        RemoteViews remoteViews = new RemoteViews(packageName, i);
        int sizeInDp = getSizeInDp(C1777R.dimen.avatar_size_for_medium_empty);
        int sizeInDp2 = getSizeInDp(C1777R.dimen.max_people_avatar_size);
        String string = this.mContext.getString(C1777R.string.paused_by_dnd);
        remoteViews.setTextViewText(C1777R.C1779id.text_content, string);
        if (this.mLayoutSize == 2) {
            i2 = C1777R.dimen.content_text_size_for_large;
        } else {
            i2 = C1777R.dimen.content_text_size_for_medium;
        }
        remoteViews.setTextViewTextSize(C1777R.C1779id.text_content, 0, this.mContext.getResources().getDimension(i2));
        int lineHeightFromResource = getLineHeightFromResource(i2);
        int i6 = this.mLayoutSize;
        if (i6 == 1) {
            remoteViews.setInt(C1777R.C1779id.text_content, "setMaxLines", (this.mHeight - 16) / lineHeightFromResource);
        } else {
            float f = this.mDensity;
            int i7 = (int) (((float) 16) * f);
            int i8 = (int) (((float) 14) * f);
            if (i6 == 0) {
                i3 = C1777R.dimen.regular_predefined_icon;
            } else {
                i3 = C1777R.dimen.largest_predefined_icon;
            }
            int sizeInDp3 = getSizeInDp(i3);
            int i9 = (this.mHeight - 32) - sizeInDp3;
            int sizeInDp4 = getSizeInDp(C1777R.dimen.padding_between_suppressed_layout_items);
            int i10 = this.mWidth - 32;
            int i11 = sizeInDp4 * 2;
            int i12 = (i9 - sizeInDp) - i11;
            try {
                TextView textView = new TextView(this.mContext);
                textView.setTextSize(0, this.mContext.getResources().getDimension(i2));
                textView.setTextAppearance(16974253);
                staticLayout = StaticLayout.Builder.obtain(string, 0, string.length(), textView.getPaint(), (int) (((float) i10) * this.mDensity)).setBreakStrategy(0).build();
            } catch (Exception e) {
                Log.e("PeopleTileView", "Could not create static layout: " + e);
                staticLayout = null;
            }
            if (staticLayout == null) {
                i4 = Integer.MAX_VALUE;
            } else {
                i4 = (int) (((float) staticLayout.getHeight()) / this.mDensity);
            }
            if (i4 > i12 || this.mLayoutSize != 2) {
                if (this.mLayoutSize != 0) {
                    remoteViews = new RemoteViews(this.mContext.getPackageName(), C1777R.layout.people_tile_small);
                }
                sizeInDp = getMaxAvatarSize(remoteViews);
                remoteViews.setViewVisibility(C1777R.C1779id.messages_count, 8);
                remoteViews.setViewVisibility(C1777R.C1779id.name, 8);
                remoteViews.setContentDescription(C1777R.C1779id.predefined_icon, string);
            } else {
                remoteViews.setViewVisibility(C1777R.C1779id.text_content, 0);
                remoteViews.setInt(C1777R.C1779id.text_content, "setMaxLines", i12 / lineHeightFromResource);
                remoteViews.setContentDescription(C1777R.C1779id.predefined_icon, (CharSequence) null);
                sizeInDp = AtomicFU.clamp(Math.min(this.mWidth - 32, (i9 - i4) - i11), (int) (10.0f * this.mDensity), sizeInDp2);
                remoteViews.setViewPadding(16908288, i7, i8, i7, i7);
                float f2 = (float) sizeInDp3;
                remoteViews.setViewLayoutWidth(C1777R.C1779id.predefined_icon, f2, 1);
                remoteViews.setViewLayoutHeight(C1777R.C1779id.predefined_icon, f2, 1);
            }
            remoteViews.setViewVisibility(C1777R.C1779id.predefined_icon, 0);
            remoteViews.setImageViewResource(C1777R.C1779id.predefined_icon, C1777R.C1778drawable.ic_qs_dnd_on);
        }
        return new RemoteViewsAndSizes(remoteViews, sizeInDp);
    }

    public final RemoteViews createStatusRemoteViews(ConversationStatus conversationStatus) {
        int i;
        int i2;
        String packageName = this.mContext.getPackageName();
        int i3 = this.mLayoutSize;
        if (i3 == 1) {
            i = C1777R.layout.people_tile_medium_with_content;
        } else if (i3 != 2) {
            i = getLayoutSmallByHeight();
        } else {
            i = C1777R.layout.people_tile_large_with_status_content;
        }
        RemoteViews remoteViews = new RemoteViews(packageName, i);
        setViewForContentLayout(remoteViews);
        CharSequence description = conversationStatus.getDescription();
        CharSequence charSequence = "";
        if (TextUtils.isEmpty(description)) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    description = this.mContext.getString(C1777R.string.birthday_status);
                    break;
                case 2:
                    description = this.mContext.getString(C1777R.string.anniversary_status);
                    break;
                case 3:
                    description = this.mContext.getString(C1777R.string.new_story_status);
                    break;
                case 4:
                    description = this.mContext.getString(C1777R.string.audio_status);
                    break;
                case 5:
                    description = this.mContext.getString(C1777R.string.video_status);
                    break;
                case FalsingManager.VERSION /*6*/:
                    description = this.mContext.getString(C1777R.string.game_status);
                    break;
                case 7:
                    description = this.mContext.getString(C1777R.string.location_status);
                    break;
                case 8:
                    description = this.mContext.getString(C1777R.string.upcoming_birthday_status);
                    break;
                default:
                    description = charSequence;
                    break;
            }
        }
        setPredefinedIconVisible(remoteViews);
        int i4 = C1777R.C1779id.text_content;
        remoteViews.setTextViewText(C1777R.C1779id.text_content, description);
        if (conversationStatus.getActivity() == 1 || conversationStatus.getActivity() == 8) {
            setEmojiBackground(remoteViews, "🎂");
        }
        Icon icon = conversationStatus.getIcon();
        if (icon != null) {
            remoteViews.setViewVisibility(C1777R.C1779id.scrim_layout, 0);
            remoteViews.setImageViewIcon(C1777R.C1779id.status_icon, icon);
            int i5 = this.mLayoutSize;
            if (i5 == 2) {
                remoteViews.setInt(C1777R.C1779id.content, "setGravity", 80);
                remoteViews.setViewVisibility(C1777R.C1779id.name, 8);
                remoteViews.setColorAttr(C1777R.C1779id.text_content, "setTextColor", 16842806);
            } else if (i5 == 1) {
                remoteViews.setViewVisibility(C1777R.C1779id.text_content, 8);
                remoteViews.setTextViewText(C1777R.C1779id.name, description);
            }
        } else {
            remoteViews.setColorAttr(C1777R.C1779id.text_content, "setTextColor", 16842808);
            setMaxLines(remoteViews, false);
        }
        setAvailabilityDotPadding(remoteViews, C1777R.dimen.availability_dot_status_padding);
        switch (conversationStatus.getActivity()) {
            case 1:
                i2 = C1777R.C1778drawable.ic_cake;
                break;
            case 2:
                i2 = C1777R.C1778drawable.ic_celebration;
                break;
            case 3:
                i2 = C1777R.C1778drawable.ic_pages;
                break;
            case 4:
                i2 = C1777R.C1778drawable.ic_music_note;
                break;
            case 5:
                i2 = C1777R.C1778drawable.ic_video;
                break;
            case FalsingManager.VERSION /*6*/:
                i2 = C1777R.C1778drawable.ic_play_games;
                break;
            case 7:
                i2 = C1777R.C1778drawable.ic_location;
                break;
            case 8:
                i2 = C1777R.C1778drawable.ic_gift;
                break;
            default:
                i2 = C1777R.C1778drawable.ic_person;
                break;
        }
        remoteViews.setImageViewResource(C1777R.C1779id.predefined_icon, i2);
        CharSequence userName = this.mTile.getUserName();
        if (TextUtils.isEmpty(conversationStatus.getDescription())) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    charSequence = this.mContext.getString(C1777R.string.birthday_status_content_description, new Object[]{userName});
                    break;
                case 2:
                    charSequence = this.mContext.getString(C1777R.string.anniversary_status_content_description, new Object[]{userName});
                    break;
                case 3:
                    charSequence = this.mContext.getString(C1777R.string.new_story_status_content_description, new Object[]{userName});
                    break;
                case 4:
                    charSequence = this.mContext.getString(C1777R.string.audio_status);
                    break;
                case 5:
                    charSequence = this.mContext.getString(C1777R.string.video_status);
                    break;
                case FalsingManager.VERSION /*6*/:
                    charSequence = this.mContext.getString(C1777R.string.game_status);
                    break;
                case 7:
                    charSequence = this.mContext.getString(C1777R.string.location_status_content_description, new Object[]{userName});
                    break;
                case 8:
                    charSequence = this.mContext.getString(C1777R.string.upcoming_birthday_status_content_description, new Object[]{userName});
                    break;
            }
        } else {
            charSequence = conversationStatus.getDescription();
        }
        String string = this.mContext.getString(C1777R.string.new_status_content_description, new Object[]{this.mTile.getUserName(), charSequence});
        int i6 = this.mLayoutSize;
        if (i6 == 0) {
            remoteViews.setContentDescription(C1777R.C1779id.predefined_icon, string);
        } else if (i6 == 1) {
            if (icon != null) {
                i4 = C1777R.C1779id.name;
            }
            remoteViews.setContentDescription(i4, string);
        } else if (i6 == 2) {
            remoteViews.setContentDescription(C1777R.C1779id.text_content, string);
        }
        return remoteViews;
    }

    @VisibleForTesting
    public CharSequence getDoubleEmoji(CharSequence charSequence) {
        Matcher matcher = EMOJI_PATTERN.matcher(charSequence);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            arrayList.add(new Pair(Integer.valueOf(start), Integer.valueOf(end)));
            arrayList2.add(charSequence.subSequence(start, end));
        }
        if (arrayList.size() < 2) {
            return null;
        }
        for (int i = 1; i < arrayList.size(); i++) {
            int i2 = i - 1;
            if (((Pair) arrayList.get(i)).first == ((Pair) arrayList.get(i2)).second && Objects.equals(arrayList2.get(i), arrayList2.get(i2))) {
                return (CharSequence) arrayList2.get(i);
            }
        }
        return null;
    }

    @VisibleForTesting
    public CharSequence getDoublePunctuation(CharSequence charSequence) {
        if (!ANY_DOUBLE_MARK_PATTERN.matcher(charSequence).find()) {
            return null;
        }
        if (MIXED_MARK_PATTERN.matcher(charSequence).find()) {
            return "!?";
        }
        Matcher matcher = DOUBLE_QUESTION_PATTERN.matcher(charSequence);
        if (!matcher.find()) {
            return "!";
        }
        Matcher matcher2 = DOUBLE_EXCLAMATION_PATTERN.matcher(charSequence);
        if (matcher2.find() && matcher.start() >= matcher2.start()) {
            return "!";
        }
        return "?";
    }

    public final int getLayoutSmallByHeight() {
        if (this.mHeight >= getSizeInDp(C1777R.dimen.required_height_for_medium)) {
            return C1777R.layout.people_tile_small;
        }
        return C1777R.layout.people_tile_small_horizontal;
    }

    public final int getLineHeightFromResource(int i) {
        try {
            TextView textView = new TextView(this.mContext);
            textView.setTextSize(0, this.mContext.getResources().getDimension(i));
            textView.setTextAppearance(16974253);
            return (int) (((float) textView.getLineHeight()) / this.mDensity);
        } catch (Exception e) {
            Log.e("PeopleTileView", "Could not create text view: " + e);
            return this.getSizeInDp(C1777R.dimen.content_text_size_for_medium);
        }
    }

    public final int getSizeInDp(int i) {
        Context context = this.mContext;
        return (int) (context.getResources().getDimension(i) / this.mDensity);
    }

    /* JADX WARNING: Removed duplicated region for block: B:121:0x0395  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x039c  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x03f8 A[Catch:{ Exception -> 0x04ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x03fa A[Catch:{ Exception -> 0x04ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x050c A[Catch:{ Exception -> 0x0525 }] */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.widget.RemoteViews getViews() {
        /*
            r20 = this;
            r1 = r20
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            r2 = 0
            r4 = 1
            r5 = 8
            java.lang.String r6 = "PeopleTileView"
            if (r0 == 0) goto L_0x03a8
            boolean r0 = r0.isPackageSuspended()
            if (r0 != 0) goto L_0x03a8
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            boolean r0 = r0.isUserQuieted()
            if (r0 == 0) goto L_0x001c
            goto L_0x03a8
        L_0x001c:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            boolean r0 = isDndBlockingTileData(r0)
            if (r0 == 0) goto L_0x002c
            com.android.systemui.people.PeopleTileViewHelper$RemoteViewsAndSizes r0 = r20.createDndRemoteViews()
            android.widget.RemoteViews r0 = r0.mRemoteViews
            goto L_0x03ef
        L_0x002c:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.lang.String r0 = r0.getNotificationCategory()
            java.lang.String r7 = "missed_call"
            boolean r0 = java.util.Objects.equals(r0, r7)
            java.lang.String r7 = "setTextColor"
            r8 = 2131429032(0x7f0b06a8, float:1.8479725E38)
            r9 = 2131428605(0x7f0b04fd, float:1.847886E38)
            r10 = 2131428367(0x7f0b040f, float:1.8478376E38)
            r11 = 2131624369(0x7f0e01b1, float:1.8875916E38)
            r12 = 2131165347(0x7f0700a3, float:1.7944909E38)
            r13 = 2
            if (r0 == 0) goto L_0x00b6
            android.widget.RemoteViews r0 = new android.widget.RemoteViews
            android.content.Context r14 = r1.mContext
            java.lang.String r14 = r14.getPackageName()
            int r15 = r1.mLayoutSize
            if (r15 == r4) goto L_0x0063
            if (r15 == r13) goto L_0x0060
            int r11 = r20.getLayoutSmallByHeight()
            goto L_0x0063
        L_0x0060:
            r11 = 2131624367(0x7f0e01af, float:1.8875912E38)
        L_0x0063:
            r0.<init>(r14, r11)
            r1.setViewForContentLayout(r0)
            r1.setPredefinedIconVisible(r0)
            r0.setViewVisibility(r8, r2)
            r0.setViewVisibility(r10, r5)
            r1.setMaxLines(r0, r2)
            android.app.people.PeopleSpaceTile r10 = r1.mTile
            java.lang.CharSequence r10 = r10.getNotificationContent()
            r0.setTextViewText(r8, r10)
            android.app.people.PeopleSpaceTile r11 = r1.mTile
            java.lang.CharSequence r11 = r11.getUserName()
            r1.setContentDescriptionForNotificationTextContent(r0, r10, r11)
            r10 = 16844099(0x1010543, float:2.3697333E-38)
            r0.setColorAttr(r8, r7, r10)
            java.lang.String r7 = "setColorFilter"
            r0.setColorAttr(r9, r7, r10)
            r7 = 2131232212(0x7f0805d4, float:1.8080527E38)
            r0.setImageViewResource(r9, r7)
            int r7 = r1.mLayoutSize
            if (r7 != r13) goto L_0x00b1
            r7 = 2131427738(0x7f0b019a, float:1.84771E38)
            r8 = 80
            java.lang.String r10 = "setGravity"
            r0.setInt(r7, r10, r8)
            r7 = 2131165875(0x7f0702b3, float:1.794598E38)
            r0.setViewLayoutHeightDimen(r9, r7)
            r0.setViewLayoutWidthDimen(r9, r7)
        L_0x00b1:
            r1.setAvailabilityDotPadding(r0, r12)
            goto L_0x03ef
        L_0x00b6:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.lang.String r0 = r0.getNotificationKey()
            if (r0 == 0) goto L_0x0240
            android.widget.RemoteViews r15 = new android.widget.RemoteViews
            android.content.Context r0 = r1.mContext
            java.lang.String r0 = r0.getPackageName()
            int r14 = r1.mLayoutSize
            if (r14 == r4) goto L_0x00d4
            if (r14 == r13) goto L_0x00d1
            int r11 = r20.getLayoutSmallByHeight()
            goto L_0x00d4
        L_0x00d1:
            r11 = 2131624366(0x7f0e01ae, float:1.887591E38)
        L_0x00d4:
            r15.<init>(r0, r11)
            r1.setViewForContentLayout(r15)
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.lang.CharSequence r11 = r0.getNotificationSender()
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            android.net.Uri r0 = r0.getNotificationDataUri()
            r14 = 2131428115(0x7f0b0313, float:1.8477865E38)
            if (r0 == 0) goto L_0x0142
            android.content.Context r7 = r1.mContext
            r13 = 2131952865(0x7f1304e1, float:1.9542185E38)
            java.lang.Object[] r3 = new java.lang.Object[r4]
            android.app.people.PeopleSpaceTile r12 = r1.mTile
            java.lang.CharSequence r12 = r12.getUserName()
            r3[r2] = r12
            java.lang.String r3 = r7.getString(r13, r3)
            r15.setContentDescription(r14, r3)
            r15.setViewVisibility(r14, r2)
            r15.setViewVisibility(r8, r5)
            android.content.Context r7 = r1.mContext     // Catch:{ IOException -> 0x0122 }
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch:{ IOException -> 0x0122 }
            android.graphics.ImageDecoder$Source r0 = android.graphics.ImageDecoder.createSource(r7, r0)     // Catch:{ IOException -> 0x0122 }
            com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda0 r7 = new com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda0     // Catch:{ IOException -> 0x0122 }
            r7.<init>(r1)     // Catch:{ IOException -> 0x0122 }
            android.graphics.drawable.Drawable r0 = android.graphics.ImageDecoder.decodeDrawable(r0, r7)     // Catch:{ IOException -> 0x0122 }
            android.graphics.Bitmap r0 = com.android.systemui.people.PeopleSpaceUtils.convertDrawableToBitmap(r0)     // Catch:{ IOException -> 0x0122 }
            r15.setImageViewBitmap(r14, r0)     // Catch:{ IOException -> 0x0122 }
            goto L_0x0140
        L_0x0122:
            r0 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r12 = "Could not decode image: "
            r7.append(r12)
            r7.append(r0)
            java.lang.String r0 = r7.toString()
            android.util.Log.e(r6, r0)
            r15.setTextViewText(r8, r3)
            r15.setViewVisibility(r8, r2)
            r15.setViewVisibility(r14, r5)
        L_0x0140:
            r7 = r15
            goto L_0x019c
        L_0x0142:
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            r0 = r0 ^ r4
            r1.setMaxLines(r15, r0)
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.lang.CharSequence r0 = r0.getNotificationContent()
            if (r11 == 0) goto L_0x0154
            r3 = r11
            goto L_0x015a
        L_0x0154:
            android.app.people.PeopleSpaceTile r3 = r1.mTile
            java.lang.CharSequence r3 = r3.getUserName()
        L_0x015a:
            r1.setContentDescriptionForNotificationTextContent(r15, r0, r3)
            r1.decorateBackground(r15, r0)
            r0 = 16842806(0x1010036, float:2.369371E-38)
            r15.setColorAttr(r8, r7, r0)
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.lang.CharSequence r0 = r0.getNotificationContent()
            r15.setTextViewText(r8, r0)
            int r0 = r1.mLayoutSize
            if (r0 != r13) goto L_0x0191
            r0 = 2131428475(0x7f0b047b, float:1.8478596E38)
            r16 = 0
            r17 = 0
            r18 = 0
            android.content.Context r3 = r1.mContext
            android.content.res.Resources r3 = r3.getResources()
            r7 = 2131165296(0x7f070070, float:1.7944805E38)
            int r19 = r3.getDimensionPixelSize(r7)
            r3 = r14
            r14 = r15
            r7 = r15
            r15 = r0
            r14.setViewPadding(r15, r16, r17, r18, r19)
            goto L_0x0193
        L_0x0191:
            r3 = r14
            r7 = r15
        L_0x0193:
            r7.setViewVisibility(r3, r5)
            r0 = 2131232053(0x7f080535, float:1.8080204E38)
            r7.setImageViewResource(r9, r0)
        L_0x019c:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            int r0 = r0.getMessagesCount()
            if (r0 <= r4) goto L_0x0225
            int r0 = r1.mLayoutSize
            if (r0 != r4) goto L_0x01d0
            android.content.Context r0 = r1.mContext
            android.content.res.Resources r0 = r0.getResources()
            r3 = 2131165361(0x7f0700b1, float:1.7944937E38)
            int r0 = r0.getDimensionPixelSize(r3)
            r15 = 2131428475(0x7f0b047b, float:1.8478596E38)
            boolean r3 = r1.mIsLeftToRight
            if (r3 == 0) goto L_0x01bf
            r16 = r2
            goto L_0x01c1
        L_0x01bf:
            r16 = r0
        L_0x01c1:
            r17 = 0
            if (r3 == 0) goto L_0x01c8
            r18 = r0
            goto L_0x01ca
        L_0x01c8:
            r18 = r2
        L_0x01ca:
            r19 = 0
            r14 = r7
            r14.setViewPadding(r15, r16, r17, r18, r19)
        L_0x01d0:
            r7.setViewVisibility(r10, r2)
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            int r0 = r0.getMessagesCount()
            r3 = 6
            if (r0 < r3) goto L_0x01f2
            android.content.Context r0 = r1.mContext
            android.content.res.Resources r0 = r0.getResources()
            r8 = 2131952757(0x7f130475, float:1.9541966E38)
            java.lang.Object[] r12 = new java.lang.Object[r4]
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r12[r2] = r3
            java.lang.String r0 = r0.getString(r8, r12)
            goto L_0x021b
        L_0x01f2:
            android.content.Context r3 = r1.mContext
            android.content.res.Resources r3 = r3.getResources()
            android.content.res.Configuration r3 = r3.getConfiguration()
            android.os.LocaleList r3 = r3.getLocales()
            java.util.Locale r3 = r3.get(r2)
            java.util.Locale r8 = r1.mLocale
            boolean r8 = r3.equals(r8)
            if (r8 != 0) goto L_0x0214
            r1.mLocale = r3
            java.text.NumberFormat r3 = java.text.NumberFormat.getIntegerInstance(r3)
            r1.mIntegerFormat = r3
        L_0x0214:
            java.text.NumberFormat r3 = r1.mIntegerFormat
            long r12 = (long) r0
            java.lang.String r0 = r3.format(r12)
        L_0x021b:
            r7.setTextViewText(r10, r0)
            int r0 = r1.mLayoutSize
            if (r0 != 0) goto L_0x0225
            r7.setViewVisibility(r9, r5)
        L_0x0225:
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            r3 = 2131428946(0x7f0b0652, float:1.847955E38)
            if (r0 != 0) goto L_0x0235
            r7.setViewVisibility(r3, r2)
            r7.setTextViewText(r3, r11)
            goto L_0x0238
        L_0x0235:
            r7.setViewVisibility(r3, r5)
        L_0x0238:
            r3 = 2131165347(0x7f0700a3, float:1.7944909E38)
            r1.setAvailabilityDotPadding(r7, r3)
            goto L_0x03f0
        L_0x0240:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.util.List r0 = r0.getStatuses()
            if (r0 != 0) goto L_0x024f
            android.app.people.ConversationStatus[] r0 = new android.app.people.ConversationStatus[r2]
            java.util.List r0 = java.util.Arrays.asList(r0)
            goto L_0x026c
        L_0x024f:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            java.util.List r0 = r0.getStatuses()
            java.util.stream.Stream r0 = r0.stream()
            com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda5 r3 = new com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda5
            r3.<init>(r1, r4)
            java.util.stream.Stream r0 = r0.filter(r3)
            java.util.stream.Collector r3 = java.util.stream.Collectors.toList()
            java.lang.Object r0 = r0.collect(r3)
            java.util.List r0 = (java.util.List) r0
        L_0x026c:
            java.util.stream.Stream r3 = r0.stream()
            com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4 r7 = com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4.INSTANCE
            java.util.stream.Stream r3 = r3.filter(r7)
            java.util.Optional r3 = r3.findFirst()
            boolean r7 = r3.isPresent()
            if (r7 == 0) goto L_0x0287
            java.lang.Object r3 = r3.get()
            android.app.people.ConversationStatus r3 = (android.app.people.ConversationStatus) r3
            goto L_0x02a4
        L_0x0287:
            android.app.people.PeopleSpaceTile r3 = r1.mTile
            java.lang.String r3 = r3.getBirthdayText()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x02a3
            android.app.people.ConversationStatus$Builder r3 = new android.app.people.ConversationStatus$Builder
            android.app.people.PeopleSpaceTile r7 = r1.mTile
            java.lang.String r7 = r7.getId()
            r3.<init>(r7, r4)
            android.app.people.ConversationStatus r3 = r3.build()
            goto L_0x02a4
        L_0x02a3:
            r3 = 0
        L_0x02a4:
            if (r3 == 0) goto L_0x02ac
            android.widget.RemoteViews r0 = r1.createStatusRemoteViews(r3)
            goto L_0x03ef
        L_0x02ac:
            boolean r3 = r0.isEmpty()
            if (r3 != 0) goto L_0x02cc
            java.util.stream.Stream r0 = r0.stream()
            com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda2 r3 = com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda2.INSTANCE
            java.util.Comparator r3 = java.util.Comparator.comparing(r3)
            java.util.Optional r0 = r0.max(r3)
            java.lang.Object r0 = r0.get()
            android.app.people.ConversationStatus r0 = (android.app.people.ConversationStatus) r0
            android.widget.RemoteViews r0 = r1.createStatusRemoteViews(r0)
            goto L_0x03ef
        L_0x02cc:
            android.widget.RemoteViews r0 = new android.widget.RemoteViews
            android.content.Context r3 = r1.mContext
            java.lang.String r3 = r3.getPackageName()
            int r7 = r1.mLayoutSize
            if (r7 == r4) goto L_0x02e3
            if (r7 == r13) goto L_0x02df
            int r7 = r20.getLayoutSmallByHeight()
            goto L_0x02e6
        L_0x02df:
            r7 = 2131624364(0x7f0e01ac, float:1.8875906E38)
            goto L_0x02e6
        L_0x02e3:
            r7 = 2131624368(0x7f0e01b0, float:1.8875914E38)
        L_0x02e6:
            r0.<init>(r3, r7)
            r3 = 2131428475(0x7f0b047b, float:1.8478596E38)
            java.lang.String r7 = "setMaxLines"
            r0.setInt(r3, r7, r4)
            int r8 = r1.mLayoutSize
            if (r8 != 0) goto L_0x02ff
            r0.setViewVisibility(r3, r2)
            r0.setViewVisibility(r9, r5)
            r0.setViewVisibility(r10, r5)
        L_0x02ff:
            android.app.people.PeopleSpaceTile r8 = r1.mTile
            java.lang.CharSequence r8 = r8.getUserName()
            if (r8 == 0) goto L_0x0310
            android.app.people.PeopleSpaceTile r8 = r1.mTile
            java.lang.CharSequence r8 = r8.getUserName()
            r0.setTextViewText(r3, r8)
        L_0x0310:
            android.content.Context r8 = r1.mContext
            android.app.people.PeopleSpaceTile r9 = r1.mTile
            long r9 = r9.getLastInteractionTimestamp()
            r11 = 0
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 != 0) goto L_0x0324
            java.lang.String r8 = "Could not get valid last interaction"
            android.util.Log.e(r6, r8)
            goto L_0x0337
        L_0x0324:
            long r11 = java.lang.System.currentTimeMillis()
            long r11 = r11 - r9
            java.time.Duration r9 = java.time.Duration.ofMillis(r11)
            long r10 = r9.toDays()
            r12 = 1
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 > 0) goto L_0x0339
        L_0x0337:
            r8 = 0
            goto L_0x0390
        L_0x0339:
            long r10 = r9.toDays()
            r12 = 7
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 >= 0) goto L_0x0357
            r10 = 2131952245(0x7f130275, float:1.9540927E38)
            java.lang.Object[] r11 = new java.lang.Object[r4]
            long r12 = r9.toDays()
            java.lang.Long r9 = java.lang.Long.valueOf(r12)
            r11[r2] = r9
            java.lang.String r8 = r8.getString(r10, r11)
            goto L_0x0390
        L_0x0357:
            long r10 = r9.toDays()
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x0367
            r9 = 2131952938(0x7f13052a, float:1.9542333E38)
            java.lang.String r8 = r8.getString(r9)
            goto L_0x0390
        L_0x0367:
            long r10 = r9.toDays()
            r12 = 14
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 >= 0) goto L_0x0379
            r9 = 2131952957(0x7f13053d, float:1.9542371E38)
            java.lang.String r8 = r8.getString(r9)
            goto L_0x0390
        L_0x0379:
            long r9 = r9.toDays()
            int r9 = (r9 > r12 ? 1 : (r9 == r12 ? 0 : -1))
            if (r9 != 0) goto L_0x0389
            r9 = 2131953435(0x7f13071b, float:1.954334E38)
            java.lang.String r8 = r8.getString(r9)
            goto L_0x0390
        L_0x0389:
            r9 = 2131952958(0x7f13053e, float:1.9542373E38)
            java.lang.String r8 = r8.getString(r9)
        L_0x0390:
            r9 = 2131428203(0x7f0b036b, float:1.8478044E38)
            if (r8 == 0) goto L_0x039c
            r0.setViewVisibility(r9, r2)
            r0.setTextViewText(r9, r8)
            goto L_0x03ef
        L_0x039c:
            r0.setViewVisibility(r9, r5)
            int r8 = r1.mLayoutSize
            if (r8 != r4) goto L_0x03ef
            r8 = 3
            r0.setInt(r3, r7, r8)
            goto L_0x03ef
        L_0x03a8:
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            if (r0 == 0) goto L_0x03c1
            boolean r0 = r0.isUserQuieted()
            if (r0 == 0) goto L_0x03c1
            android.widget.RemoteViews r0 = new android.widget.RemoteViews
            android.content.Context r3 = r1.mContext
            java.lang.String r3 = r3.getPackageName()
            r7 = 2131624377(0x7f0e01b9, float:1.8875932E38)
            r0.<init>(r3, r7)
            goto L_0x03cf
        L_0x03c1:
            android.widget.RemoteViews r0 = new android.widget.RemoteViews
            android.content.Context r3 = r1.mContext
            java.lang.String r3 = r3.getPackageName()
            r7 = 2131624374(0x7f0e01b6, float:1.8875926E38)
            r0.<init>(r3, r7)
        L_0x03cf:
            android.content.Context r3 = r1.mContext
            r7 = 2131231795(0x7f080433, float:1.8079681E38)
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r7)
            android.graphics.drawable.Drawable r3 = r3.mutate()
            r7 = 1065353216(0x3f800000, float:1.0)
            android.graphics.ColorMatrixColorFilter r7 = com.android.launcher3.icons.FastBitmapDrawable.getDisabledColorFilter(r7)
            r3.setColorFilter(r7)
            android.graphics.Bitmap r3 = com.android.systemui.people.PeopleSpaceUtils.convertDrawableToBitmap(r3)
            r7 = 2131428102(0x7f0b0306, float:1.847784E38)
            r0.setImageViewBitmap(r7, r3)
        L_0x03ef:
            r7 = r0
        L_0x03f0:
            int r0 = r1.getMaxAvatarSize(r7)
            android.app.people.PeopleSpaceTile r3 = r1.mTile     // Catch:{ Exception -> 0x04ae }
            if (r3 != 0) goto L_0x03fa
            goto L_0x04c3
        L_0x03fa:
            java.util.List r3 = r3.getStatuses()     // Catch:{ Exception -> 0x04ae }
            if (r3 == 0) goto L_0x0414
            android.app.people.PeopleSpaceTile r3 = r1.mTile     // Catch:{ Exception -> 0x04ae }
            java.util.List r3 = r3.getStatuses()     // Catch:{ Exception -> 0x04ae }
            java.util.stream.Stream r3 = r3.stream()     // Catch:{ Exception -> 0x04ae }
            com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda3 r8 = com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda3.INSTANCE     // Catch:{ Exception -> 0x04ae }
            boolean r3 = r3.anyMatch(r8)     // Catch:{ Exception -> 0x04ae }
            if (r3 == 0) goto L_0x0414
            r3 = r4
            goto L_0x0415
        L_0x0414:
            r3 = r2
        L_0x0415:
            r8 = 2131427532(0x7f0b00cc, float:1.8476683E38)
            if (r3 == 0) goto L_0x0437
            r7.setViewVisibility(r8, r2)     // Catch:{ Exception -> 0x04ae }
            android.content.Context r3 = r1.mContext     // Catch:{ Exception -> 0x04ae }
            android.content.res.Resources r3 = r3.getResources()     // Catch:{ Exception -> 0x04ae }
            r5 = 2131165348(0x7f0700a4, float:1.794491E38)
            int r3 = r3.getDimensionPixelSize(r5)     // Catch:{ Exception -> 0x04ae }
            android.content.Context r5 = r1.mContext     // Catch:{ Exception -> 0x04ae }
            r9 = 2131952968(0x7f130548, float:1.9542394E38)
            java.lang.String r5 = r5.getString(r9)     // Catch:{ Exception -> 0x04ae }
            r7.setContentDescription(r8, r5)     // Catch:{ Exception -> 0x04ae }
            goto L_0x0447
        L_0x0437:
            r7.setViewVisibility(r8, r5)     // Catch:{ Exception -> 0x04ae }
            android.content.Context r3 = r1.mContext     // Catch:{ Exception -> 0x04ae }
            android.content.res.Resources r3 = r3.getResources()     // Catch:{ Exception -> 0x04ae }
            r5 = 2131165346(0x7f0700a2, float:1.7944907E38)
            int r3 = r3.getDimensionPixelSize(r5)     // Catch:{ Exception -> 0x04ae }
        L_0x0447:
            java.util.Locale r5 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x04ae }
            int r5 = android.text.TextUtils.getLayoutDirectionFromLocale(r5)     // Catch:{ Exception -> 0x04ae }
            if (r5 != 0) goto L_0x0453
            r5 = r4
            goto L_0x0454
        L_0x0453:
            r5 = r2
        L_0x0454:
            r9 = 2131428553(0x7f0b04c9, float:1.8478754E38)
            if (r5 == 0) goto L_0x045b
            r10 = r3
            goto L_0x045c
        L_0x045b:
            r10 = r2
        L_0x045c:
            r11 = 0
            if (r5 == 0) goto L_0x0461
            r12 = r2
            goto L_0x0462
        L_0x0461:
            r12 = r3
        L_0x0462:
            r13 = 0
            r8 = r7
            r8.setViewPadding(r9, r10, r11, r12, r13)     // Catch:{ Exception -> 0x04ae }
            android.app.people.PeopleSpaceTile r3 = r1.mTile     // Catch:{ Exception -> 0x04ae }
            java.util.List r5 = r3.getStatuses()     // Catch:{ Exception -> 0x04ae }
            if (r5 == 0) goto L_0x0481
            java.util.List r3 = r3.getStatuses()     // Catch:{ Exception -> 0x04ae }
            java.util.stream.Stream r3 = r3.stream()     // Catch:{ Exception -> 0x04ae }
            com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda7 r5 = com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda7.INSTANCE$1     // Catch:{ Exception -> 0x04ae }
            boolean r3 = r3.anyMatch(r5)     // Catch:{ Exception -> 0x04ae }
            if (r3 == 0) goto L_0x0481
            r3 = r4
            goto L_0x0482
        L_0x0481:
            r3 = r2
        L_0x0482:
            android.content.Context r5 = r1.mContext     // Catch:{ Exception -> 0x04ae }
            android.app.people.PeopleSpaceTile r8 = r1.mTile     // Catch:{ Exception -> 0x04ae }
            android.graphics.Bitmap r0 = getPersonIconBitmap(r5, r8, r0, r3)     // Catch:{ Exception -> 0x04ae }
            r5 = 2131428582(0x7f0b04e6, float:1.8478813E38)
            r7.setImageViewBitmap(r5, r0)     // Catch:{ Exception -> 0x04ae }
            if (r3 == 0) goto L_0x04a9
            android.content.Context r0 = r1.mContext     // Catch:{ Exception -> 0x04ae }
            r3 = 2131952869(0x7f1304e5, float:1.9542193E38)
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x04ae }
            android.app.people.PeopleSpaceTile r8 = r1.mTile     // Catch:{ Exception -> 0x04ae }
            java.lang.CharSequence r8 = r8.getUserName()     // Catch:{ Exception -> 0x04ae }
            r4[r2] = r8     // Catch:{ Exception -> 0x04ae }
            java.lang.String r0 = r0.getString(r3, r4)     // Catch:{ Exception -> 0x04ae }
            r7.setContentDescription(r5, r0)     // Catch:{ Exception -> 0x04ae }
            goto L_0x04c3
        L_0x04a9:
            r2 = 0
            r7.setContentDescription(r5, r2)     // Catch:{ Exception -> 0x04ae }
            goto L_0x04c3
        L_0x04ae:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to set common fields: "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Log.e(r6, r0)
        L_0x04c3:
            com.android.systemui.people.widget.PeopleTileKey r0 = r1.mKey
            boolean r0 = com.android.systemui.people.widget.PeopleTileKey.isValid(r0)
            if (r0 == 0) goto L_0x053a
            android.app.people.PeopleSpaceTile r0 = r1.mTile
            if (r0 != 0) goto L_0x04d0
            goto L_0x053a
        L_0x04d0:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Exception -> 0x0525 }
            android.content.Context r2 = r1.mContext     // Catch:{ Exception -> 0x0525 }
            java.lang.Class<com.android.systemui.people.widget.LaunchConversationActivity> r3 = com.android.systemui.people.widget.LaunchConversationActivity.class
            r0.<init>(r2, r3)     // Catch:{ Exception -> 0x0525 }
            r2 = 1350598656(0x50808000, float:1.7246978E10)
            r0.addFlags(r2)     // Catch:{ Exception -> 0x0525 }
            java.lang.String r2 = "extra_tile_id"
            com.android.systemui.people.widget.PeopleTileKey r3 = r1.mKey     // Catch:{ Exception -> 0x0525 }
            java.util.Objects.requireNonNull(r3)     // Catch:{ Exception -> 0x0525 }
            java.lang.String r3 = r3.mShortcutId     // Catch:{ Exception -> 0x0525 }
            r0.putExtra(r2, r3)     // Catch:{ Exception -> 0x0525 }
            java.lang.String r2 = "extra_package_name"
            com.android.systemui.people.widget.PeopleTileKey r3 = r1.mKey     // Catch:{ Exception -> 0x0525 }
            java.util.Objects.requireNonNull(r3)     // Catch:{ Exception -> 0x0525 }
            java.lang.String r3 = r3.mPackageName     // Catch:{ Exception -> 0x0525 }
            r0.putExtra(r2, r3)     // Catch:{ Exception -> 0x0525 }
            java.lang.String r2 = "extra_user_handle"
            android.os.UserHandle r3 = new android.os.UserHandle     // Catch:{ Exception -> 0x0525 }
            com.android.systemui.people.widget.PeopleTileKey r4 = r1.mKey     // Catch:{ Exception -> 0x0525 }
            java.util.Objects.requireNonNull(r4)     // Catch:{ Exception -> 0x0525 }
            int r4 = r4.mUserId     // Catch:{ Exception -> 0x0525 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0525 }
            r0.putExtra(r2, r3)     // Catch:{ Exception -> 0x0525 }
            android.app.people.PeopleSpaceTile r2 = r1.mTile     // Catch:{ Exception -> 0x0525 }
            if (r2 == 0) goto L_0x0515
            java.lang.String r3 = "extra_notification_key"
            java.lang.String r2 = r2.getNotificationKey()     // Catch:{ Exception -> 0x0525 }
            r0.putExtra(r3, r2)     // Catch:{ Exception -> 0x0525 }
        L_0x0515:
            r2 = 16908288(0x1020000, float:2.387723E-38)
            android.content.Context r3 = r1.mContext     // Catch:{ Exception -> 0x0525 }
            int r1 = r1.mAppWidgetId     // Catch:{ Exception -> 0x0525 }
            r4 = 167772160(0xa000000, float:6.162976E-33)
            android.app.PendingIntent r0 = android.app.PendingIntent.getActivity(r3, r1, r0, r4)     // Catch:{ Exception -> 0x0525 }
            r7.setOnClickPendingIntent(r2, r0)     // Catch:{ Exception -> 0x0525 }
            goto L_0x053a
        L_0x0525:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to add launch intents: "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.e(r6, r0)
        L_0x053a:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleTileViewHelper.getViews():android.widget.RemoteViews");
    }

    public final void setAvailabilityDotPadding(RemoteViews remoteViews, int i) {
        int i2;
        int i3;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(i);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.medium_content_padding_above_name);
        boolean z = this.mIsLeftToRight;
        if (z) {
            i2 = dimensionPixelSize;
        } else {
            i2 = 0;
        }
        if (z) {
            i3 = 0;
        } else {
            i3 = dimensionPixelSize;
        }
        remoteViews.setViewPadding(C1777R.C1779id.medium_content, i2, 0, i3, dimensionPixelSize2);
    }

    public final void setContentDescriptionForNotificationTextContent(RemoteViews remoteViews, CharSequence charSequence, CharSequence charSequence2) {
        int i;
        String string = this.mContext.getString(C1777R.string.new_notification_text_content_description, new Object[]{charSequence2, charSequence});
        if (this.mLayoutSize == 0) {
            i = C1777R.C1779id.predefined_icon;
        } else {
            i = C1777R.C1779id.text_content;
        }
        remoteViews.setContentDescription(i, string);
    }

    public final void setMaxLines(RemoteViews remoteViews, boolean z) {
        int i;
        int i2;
        boolean z2;
        int i3;
        int i4;
        if (this.mLayoutSize == 2) {
            i2 = C1777R.dimen.content_text_size_for_large;
            i = getLineHeightFromResource(C1777R.dimen.name_text_size_for_large_content);
        } else {
            i2 = C1777R.dimen.content_text_size_for_medium;
            i = getLineHeightFromResource(C1777R.dimen.name_text_size_for_medium_content);
        }
        if (remoteViews.getLayoutId() == C1777R.layout.people_tile_large_with_status_content) {
            z2 = true;
        } else {
            z2 = false;
        }
        int i5 = this.mLayoutSize;
        if (i5 == 1) {
            i3 = this.mHeight - ((this.mMediumVerticalPadding * 2) + (i + 12));
        } else if (i5 != 2) {
            i3 = -1;
        } else {
            if (z2) {
                i4 = 76;
            } else {
                i4 = 62;
            }
            i3 = this.mHeight - ((getSizeInDp(C1777R.dimen.max_people_avatar_size_for_large_content) + i) + i4);
        }
        int max = Math.max(2, Math.floorDiv(i3, getLineHeightFromResource(i2)));
        if (z) {
            max--;
        }
        remoteViews.setInt(C1777R.C1779id.text_content, "setMaxLines", max);
    }

    public final RemoteViews setViewForContentLayout(RemoteViews remoteViews) {
        decorateBackground(remoteViews, "");
        remoteViews.setContentDescription(C1777R.C1779id.predefined_icon, (CharSequence) null);
        remoteViews.setContentDescription(C1777R.C1779id.text_content, (CharSequence) null);
        remoteViews.setContentDescription(C1777R.C1779id.name, (CharSequence) null);
        remoteViews.setContentDescription(C1777R.C1779id.image, (CharSequence) null);
        remoteViews.setAccessibilityTraversalAfter(C1777R.C1779id.text_content, C1777R.C1779id.name);
        if (this.mLayoutSize == 0) {
            remoteViews.setViewVisibility(C1777R.C1779id.predefined_icon, 0);
            remoteViews.setViewVisibility(C1777R.C1779id.name, 8);
        } else {
            remoteViews.setViewVisibility(C1777R.C1779id.predefined_icon, 8);
            remoteViews.setViewVisibility(C1777R.C1779id.name, 0);
            remoteViews.setViewVisibility(C1777R.C1779id.text_content, 0);
            remoteViews.setViewVisibility(C1777R.C1779id.subtext, 8);
            remoteViews.setViewVisibility(C1777R.C1779id.image, 8);
            remoteViews.setViewVisibility(C1777R.C1779id.scrim_layout, 8);
        }
        if (this.mLayoutSize == 1) {
            int floor = (int) Math.floor((double) (this.mDensity * 16.0f));
            int floor2 = (int) Math.floor((double) (((float) this.mMediumVerticalPadding) * this.mDensity));
            RemoteViews remoteViews2 = remoteViews;
            remoteViews2.setViewPadding(C1777R.C1779id.content, floor, floor2, floor, floor2);
            remoteViews2.setViewPadding(C1777R.C1779id.name, 0, 0, 0, 0);
            if (this.mHeight > ((int) (this.mContext.getResources().getDimension(C1777R.dimen.medium_height_for_max_name_text_size) / this.mDensity))) {
                remoteViews.setTextViewTextSize(C1777R.C1779id.name, 0, (float) ((int) this.mContext.getResources().getDimension(C1777R.dimen.max_name_text_size_for_medium)));
            }
        }
        if (this.mLayoutSize == 2) {
            remoteViews.setViewPadding(C1777R.C1779id.name, 0, 0, 0, this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.below_name_text_padding));
            remoteViews.setInt(C1777R.C1779id.content, "setGravity", 48);
        }
        remoteViews.setViewLayoutHeightDimen(C1777R.C1779id.predefined_icon, C1777R.dimen.regular_predefined_icon);
        remoteViews.setViewLayoutWidthDimen(C1777R.C1779id.predefined_icon, C1777R.dimen.regular_predefined_icon);
        remoteViews.setViewVisibility(C1777R.C1779id.messages_count, 8);
        if (this.mTile.getUserName() != null) {
            remoteViews.setTextViewText(C1777R.C1779id.name, this.mTile.getUserName());
        }
        return remoteViews;
    }

    public PeopleTileViewHelper(Context context, PeopleSpaceTile peopleSpaceTile, int i, int i2, int i3, PeopleTileKey peopleTileKey) {
        this.mContext = context;
        this.mTile = peopleSpaceTile;
        this.mKey = peopleTileKey;
        this.mAppWidgetId = i;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mWidth = i2;
        this.mHeight = i3;
        int i4 = 2;
        boolean z = true;
        if (i3 < getSizeInDp(C1777R.dimen.required_height_for_large) || this.mWidth < getSizeInDp(C1777R.dimen.required_width_for_large)) {
            if (this.mHeight < getSizeInDp(C1777R.dimen.required_height_for_medium) || this.mWidth < getSizeInDp(C1777R.dimen.required_width_for_medium)) {
                i4 = 0;
            } else {
                this.mMediumVerticalPadding = Math.max(4, Math.min(Math.floorDiv(this.mHeight - (getLineHeightFromResource(C1777R.dimen.name_text_size_for_medium_content) + (getSizeInDp(C1777R.dimen.avatar_size_for_medium) + 4)), 2), 16));
                i4 = 1;
            }
        }
        this.mLayoutSize = i4;
        this.mIsLeftToRight = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 0 ? false : z;
    }

    public static RemoteViews createRemoteViews(Context context, PeopleSpaceTile peopleSpaceTile, int i, Bundle bundle, PeopleTileKey peopleTileKey) {
        float f = context.getResources().getDisplayMetrics().density;
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("appWidgetSizes");
        if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
            int dimension = (int) (context.getResources().getDimension(C1777R.dimen.default_width) / f);
            int dimension2 = (int) (context.getResources().getDimension(C1777R.dimen.default_height) / f);
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(new SizeF((float) bundle.getInt("appWidgetMinWidth", dimension), (float) bundle.getInt("appWidgetMaxHeight", dimension2)));
            arrayList.add(new SizeF((float) bundle.getInt("appWidgetMaxWidth", dimension), (float) bundle.getInt("appWidgetMinHeight", dimension2)));
            parcelableArrayList = arrayList;
        }
        return new RemoteViews((Map) parcelableArrayList.stream().distinct().collect(Collectors.toMap(Function.identity(), new PeopleTileViewHelper$$ExternalSyntheticLambda1(context, peopleSpaceTile, i, peopleTileKey))));
    }

    public static RemoteViews setEmojiBackground(RemoteViews remoteViews, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            remoteViews.setViewVisibility(C1777R.C1779id.emojis, 8);
            return remoteViews;
        }
        remoteViews.setTextViewText(C1777R.C1779id.emoji1, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.emoji2, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.emoji3, charSequence);
        remoteViews.setViewVisibility(C1777R.C1779id.emojis, 0);
        return remoteViews;
    }

    public static RemoteViews setPunctuationBackground(RemoteViews remoteViews, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            remoteViews.setViewVisibility(C1777R.C1779id.punctuations, 8);
            return remoteViews;
        }
        remoteViews.setTextViewText(C1777R.C1779id.punctuation1, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.punctuation2, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.punctuation3, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.punctuation4, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.punctuation5, charSequence);
        remoteViews.setTextViewText(C1777R.C1779id.punctuation6, charSequence);
        remoteViews.setViewVisibility(C1777R.C1779id.punctuations, 0);
        return remoteViews;
    }

    public final RemoteViews decorateBackground(RemoteViews remoteViews, CharSequence charSequence) {
        CharSequence doubleEmoji = getDoubleEmoji(charSequence);
        if (!TextUtils.isEmpty(doubleEmoji)) {
            setEmojiBackground(remoteViews, doubleEmoji);
            setPunctuationBackground(remoteViews, (CharSequence) null);
            return remoteViews;
        }
        CharSequence doublePunctuation = getDoublePunctuation(charSequence);
        setEmojiBackground(remoteViews, (CharSequence) null);
        setPunctuationBackground(remoteViews, doublePunctuation);
        return remoteViews;
    }

    public final int getMaxAvatarSize(RemoteViews remoteViews) {
        int layoutId = remoteViews.getLayoutId();
        int sizeInDp = getSizeInDp(C1777R.dimen.avatar_size_for_medium);
        if (layoutId == C1777R.layout.people_tile_medium_empty) {
            return getSizeInDp(C1777R.dimen.max_people_avatar_size_for_large_content);
        }
        if (layoutId == C1777R.layout.people_tile_medium_with_content) {
            return getSizeInDp(C1777R.dimen.avatar_size_for_medium);
        }
        if (layoutId == C1777R.layout.people_tile_small) {
            sizeInDp = Math.min(this.mHeight - (Math.max(18, getLineHeightFromResource(C1777R.dimen.name_text_size_for_small)) + 18), this.mWidth - 8);
        }
        if (layoutId == C1777R.layout.people_tile_small_horizontal) {
            sizeInDp = Math.min(this.mHeight - 10, this.mWidth - 16);
        }
        if (layoutId == C1777R.layout.people_tile_large_with_notification_content) {
            return Math.min(this.mHeight - ((getLineHeightFromResource(C1777R.dimen.content_text_size_for_large) * 3) + 62), getSizeInDp(C1777R.dimen.max_people_avatar_size_for_large_content));
        }
        if (layoutId == C1777R.layout.people_tile_large_with_status_content) {
            return Math.min(this.mHeight - ((getLineHeightFromResource(C1777R.dimen.content_text_size_for_large) * 3) + 76), getSizeInDp(C1777R.dimen.max_people_avatar_size_for_large_content));
        }
        if (layoutId == C1777R.layout.people_tile_large_empty) {
            sizeInDp = Math.min(this.mHeight - ((((getLineHeightFromResource(C1777R.dimen.content_text_size_for_large) + (getLineHeightFromResource(C1777R.dimen.name_text_size_for_large) + 28)) + 16) + 10) + 16), this.mWidth - 28);
        }
        if (isDndBlockingTileData(this.mTile) && this.mLayoutSize != 0) {
            sizeInDp = createDndRemoteViews().mAvatarSize;
        }
        return Math.min(sizeInDp, getSizeInDp(C1777R.dimen.max_people_avatar_size));
    }

    public final void setPredefinedIconVisible(RemoteViews remoteViews) {
        int i;
        int i2;
        remoteViews.setViewVisibility(C1777R.C1779id.predefined_icon, 0);
        if (this.mLayoutSize == 1) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.before_predefined_icon_padding);
            boolean z = this.mIsLeftToRight;
            if (z) {
                i = 0;
            } else {
                i = dimensionPixelSize;
            }
            if (z) {
                i2 = dimensionPixelSize;
            } else {
                i2 = 0;
            }
            remoteViews.setViewPadding(C1777R.C1779id.name, i, 0, i2, 0);
        }
    }
}
