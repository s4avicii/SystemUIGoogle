package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.AlphaOptimizedImageView;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.ArrayList;
import java.util.Objects;

public final class NotificationMenuRow implements NotificationMenuRowPlugin, View.OnClickListener, ExpandableNotificationRow.LayoutListener {
    public float mAlpha = 0.0f;
    public boolean mAnimating;
    public CheckForDrag mCheckForDrag;
    public Context mContext;
    public boolean mDismissing;
    public ValueAnimator mFadeAnimator;
    public NotificationMenuItem mFeedbackItem;
    public Handler mHandler;
    public int mHorizSpaceForIcon = -1;
    public int[] mIconLocation = new int[2];
    public int mIconPadding = -1;
    public boolean mIconsPlaced;
    public NotificationMenuItem mInfoItem;
    public boolean mIsUserTouching;
    public ArrayList<NotificationMenuRowPlugin.MenuItem> mLeftMenuItems;
    public FrameLayout mMenuContainer;
    public boolean mMenuFadedIn;
    public final ArrayMap mMenuItemsByView = new ArrayMap();
    public NotificationMenuRowPlugin.OnMenuEventListener mMenuListener;
    public boolean mMenuSnapped;
    public boolean mMenuSnappedOnLeft;
    public boolean mOnLeft;
    public ExpandableNotificationRow mParent;
    public int[] mParentLocation = new int[2];
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    public ArrayList<NotificationMenuRowPlugin.MenuItem> mRightMenuItems;
    public boolean mShouldShowMenu;
    public boolean mSnapping;
    public boolean mSnappingToDismiss;
    public NotificationMenuItem mSnoozeItem;
    public float mTranslation;
    public int mVertSpaceForIcons = -1;

    public final class CheckForDrag implements Runnable {
        public CheckForDrag() {
        }

        public final void run() {
            float abs = Math.abs(NotificationMenuRow.this.mTranslation);
            float spaceForMenu = (float) NotificationMenuRow.this.getSpaceForMenu();
            float width = ((float) NotificationMenuRow.this.mParent.getWidth()) * 0.4f;
            if ((!NotificationMenuRow.this.isMenuVisible() || NotificationMenuRow.this.isMenuLocationChange()) && ((double) abs) >= ((double) spaceForMenu) * 0.4d && abs < width) {
                NotificationMenuRow.this.fadeInMenu(width);
            }
        }
    }

    public static class NotificationMenuItem implements NotificationMenuRowPlugin.MenuItem {
        public String mContentDescription;
        public NotificationGuts.GutsContent mGutsContent;
        public AlphaOptimizedImageView mMenuView;

        public final View getGutsView() {
            return this.mGutsContent.getContentView();
        }

        public NotificationMenuItem(Context context, String str, NotificationGuts.GutsContent gutsContent, int i) {
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.notification_menu_icon_padding);
            int color = resources.getColor(C1777R.color.notification_gear_color);
            if (i >= 0) {
                AlphaOptimizedImageView alphaOptimizedImageView = new AlphaOptimizedImageView(context);
                alphaOptimizedImageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                alphaOptimizedImageView.setImageDrawable(context.getResources().getDrawable(i));
                alphaOptimizedImageView.setColorFilter(color);
                alphaOptimizedImageView.setAlpha(1.0f);
                this.mMenuView = alphaOptimizedImageView;
            }
            this.mContentDescription = str;
            this.mGutsContent = gutsContent;
        }

        public final String getContentDescription() {
            return this.mContentDescription;
        }

        public final View getMenuView() {
            return this.mMenuView;
        }
    }

    @VisibleForTesting
    public void beginDrag() {
        this.mSnapping = false;
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mHandler.removeCallbacks(this.mCheckForDrag);
        this.mCheckForDrag = null;
        this.mIsUserTouching = true;
    }

    public final NotificationMenuRowPlugin.MenuItem menuItemToExposeOnSnap() {
        return null;
    }

    public final void onSnapOpen() {
        ExpandableNotificationRow expandableNotificationRow;
        this.mMenuSnapped = true;
        this.mMenuSnappedOnLeft = isMenuOnLeft();
        if (this.mAlpha == 0.0f && (expandableNotificationRow = this.mParent) != null) {
            fadeInMenu((float) expandableNotificationRow.getWidth());
        }
        NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener = this.mMenuListener;
        if (onMenuEventListener != null) {
            onMenuEventListener.onMenuShown(getParent());
        }
    }

    public final void onTouchEnd() {
        this.mIsUserTouching = false;
    }

    public final void onTouchMove(float f) {
        CheckForDrag checkForDrag;
        boolean z = false;
        this.mSnapping = false;
        if (!isTowardsMenu(f) && isMenuLocationChange()) {
            this.mMenuSnapped = false;
            if (!this.mHandler.hasCallbacks(this.mCheckForDrag)) {
                this.mCheckForDrag = null;
            } else {
                setMenuAlpha(0.0f);
                setMenuLocation();
            }
        }
        if (this.mShouldShowMenu && !NotificationStackScrollLayout.isPinnedHeadsUp(getParent()) && !this.mParent.areGutsExposed() && !this.mParent.showingPulsing() && ((checkForDrag = this.mCheckForDrag) == null || !this.mHandler.hasCallbacks(checkForDrag))) {
            CheckForDrag checkForDrag2 = new CheckForDrag();
            this.mCheckForDrag = checkForDrag2;
            this.mHandler.postDelayed(checkForDrag2, 60);
        }
        if (canBeDismissed()) {
            float dismissThreshold = getDismissThreshold();
            if (f < (-dismissThreshold) || f > dismissThreshold) {
                z = true;
            }
            if (this.mSnappingToDismiss != z) {
                this.mMenuContainer.performHapticFeedback(4);
            }
            this.mSnappingToDismiss = z;
        }
    }

    public final void resetMenu() {
        resetState(true);
    }

    public final void resetState(boolean z) {
        setMenuAlpha(0.0f);
        this.mIconsPlaced = false;
        this.mMenuFadedIn = false;
        this.mAnimating = false;
        this.mSnapping = false;
        this.mDismissing = false;
        this.mMenuSnapped = false;
        setMenuLocation();
        NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener = this.mMenuListener;
        if (onMenuEventListener != null && z) {
            onMenuEventListener.onMenuReset(this.mParent);
        }
    }

    public final void setAppName(String str) {
        if (str != null) {
            setAppName(str, this.mLeftMenuItems);
            setAppName(str, this.mRightMenuItems);
        }
    }

    public final void setMenuItems(ArrayList<NotificationMenuRowPlugin.MenuItem> arrayList) {
    }

    public final boolean shouldShowGutsOnSnapOpen() {
        return false;
    }

    @VisibleForTesting
    public void cancelDrag() {
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mHandler.removeCallbacks(this.mCheckForDrag);
    }

    public final void createMenu(ViewGroup viewGroup, StatusBarNotification statusBarNotification) {
        this.mParent = (ExpandableNotificationRow) viewGroup;
        createMenuViews(true);
    }

    public final void createMenuViews(boolean z) {
        boolean z2;
        Resources resources = this.mContext.getResources();
        this.mHorizSpaceForIcon = resources.getDimensionPixelSize(C1777R.dimen.notification_menu_icon_size);
        this.mVertSpaceForIcons = resources.getDimensionPixelSize(C1777R.dimen.notification_min_height);
        this.mLeftMenuItems.clear();
        this.mRightMenuItems.clear();
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "show_notification_snooze", 0) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            this.mSnoozeItem = createSnoozeItem(this.mContext);
        }
        Context context = this.mContext;
        this.mFeedbackItem = new NotificationMenuItem(context, (String) null, (FeedbackInfo) LayoutInflater.from(context).inflate(C1777R.layout.feedback_info, (ViewGroup) null, false), -1);
        ExpandableNotificationRow expandableNotificationRow = this.mParent;
        Objects.requireNonNull(expandableNotificationRow);
        int peopleNotificationType = this.mPeopleNotificationIdentifier.getPeopleNotificationType(expandableNotificationRow.mEntry);
        if (peopleNotificationType == 1) {
            this.mInfoItem = createPartialConversationItem(this.mContext);
        } else if (peopleNotificationType >= 2) {
            this.mInfoItem = createConversationItem(this.mContext);
        } else {
            this.mInfoItem = createInfoItem(this.mContext);
        }
        if (z2) {
            this.mRightMenuItems.add(this.mSnoozeItem);
        }
        this.mRightMenuItems.add(this.mInfoItem);
        this.mRightMenuItems.add(this.mFeedbackItem);
        this.mLeftMenuItems.addAll(this.mRightMenuItems);
        populateMenuViews();
        if (z) {
            resetState(false);
            return;
        }
        this.mIconsPlaced = false;
        setMenuLocation();
        if (!this.mIsUserTouching) {
            onSnapOpen();
        }
    }

    public final void fadeInMenu(final float f) {
        final boolean z;
        if (!this.mDismissing && !this.mAnimating) {
            if (isMenuLocationChange()) {
                setMenuAlpha(0.0f);
            }
            final float f2 = this.mTranslation;
            if (f2 > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            setMenuLocation();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mAlpha, 1.0f});
            this.mFadeAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    boolean z;
                    float abs = Math.abs(f2);
                    boolean z2 = z;
                    if ((!z2 || f2 > f) && (z2 || abs > f)) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        NotificationMenuRow notificationMenuRow = NotificationMenuRow.this;
                        if (!notificationMenuRow.mMenuFadedIn) {
                            notificationMenuRow.setMenuAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    }
                }
            });
            this.mFadeAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationCancel(Animator animator) {
                    NotificationMenuRow.this.setMenuAlpha(0.0f);
                }

                public final void onAnimationEnd(Animator animator) {
                    NotificationMenuRow notificationMenuRow = NotificationMenuRow.this;
                    boolean z = false;
                    notificationMenuRow.mAnimating = false;
                    if (notificationMenuRow.mAlpha == 1.0f) {
                        z = true;
                    }
                    notificationMenuRow.mMenuFadedIn = z;
                }

                public final void onAnimationStart(Animator animator) {
                    NotificationMenuRow.this.mAnimating = true;
                }
            });
            this.mFadeAnimator.setInterpolator(Interpolators.ALPHA_IN);
            this.mFadeAnimator.setDuration(200);
            this.mFadeAnimator.start();
        }
    }

    @VisibleForTesting
    public float getMaximumSwipeDistance() {
        return ((float) this.mHorizSpaceForIcon) * 0.2f;
    }

    public final ArrayList<NotificationMenuRowPlugin.MenuItem> getMenuItems(Context context) {
        if (this.mOnLeft) {
            return this.mLeftMenuItems;
        }
        return this.mRightMenuItems;
    }

    public final Point getRevealAnimationOrigin() {
        NotificationMenuItem notificationMenuItem = this.mInfoItem;
        Objects.requireNonNull(notificationMenuItem);
        AlphaOptimizedImageView alphaOptimizedImageView = notificationMenuItem.mMenuView;
        int width = (alphaOptimizedImageView.getWidth() / 2) + alphaOptimizedImageView.getPaddingLeft() + alphaOptimizedImageView.getLeft();
        int top = alphaOptimizedImageView.getTop();
        int height = (alphaOptimizedImageView.getHeight() / 2) + alphaOptimizedImageView.getPaddingTop() + top;
        if (isMenuOnLeft()) {
            return new Point(width, height);
        }
        return new Point(this.mParent.getRight() - width, height);
    }

    @VisibleForTesting
    public int getSpaceForMenu() {
        return this.mMenuContainer.getChildCount() * this.mHorizSpaceForIcon;
    }

    public final boolean isMenuLocationChange() {
        boolean z;
        boolean z2;
        float f = this.mTranslation;
        int i = this.mIconPadding;
        if (f > ((float) i)) {
            z = true;
        } else {
            z = false;
        }
        if (f < ((float) (-i))) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((!isMenuOnLeft() || !z2) && (isMenuOnLeft() || !z)) {
            return false;
        }
        return true;
    }

    public final boolean isMenuVisible() {
        if (this.mAlpha > 0.0f) {
            return true;
        }
        return false;
    }

    public final void onClick(View view) {
        if (this.mMenuListener != null) {
            view.getLocationOnScreen(this.mIconLocation);
            this.mParent.getLocationOnScreen(this.mParentLocation);
            int[] iArr = this.mIconLocation;
            int i = iArr[0];
            int[] iArr2 = this.mParentLocation;
            int i2 = (i - iArr2[0]) + (this.mHorizSpaceForIcon / 2);
            int height = (iArr[1] - iArr2[1]) + (view.getHeight() / 2);
            if (this.mMenuItemsByView.containsKey(view)) {
                this.mMenuListener.onMenuClicked(this.mParent, i2, height, (NotificationMenuRowPlugin.MenuItem) this.mMenuItemsByView.get(view));
            }
        }
    }

    public final void onConfigurationChanged() {
        ExpandableNotificationRow expandableNotificationRow = this.mParent;
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mLayoutListener = this;
    }

    public final void onNotificationUpdated(StatusBarNotification statusBarNotification) {
        if (this.mMenuContainer != null) {
            createMenuViews(!isMenuVisible());
        }
    }

    public final void onParentHeightUpdate() {
        float f;
        if (this.mParent == null) {
            return;
        }
        if ((!this.mLeftMenuItems.isEmpty() || !this.mRightMenuItems.isEmpty()) && this.mMenuContainer != null) {
            ExpandableNotificationRow expandableNotificationRow = this.mParent;
            Objects.requireNonNull(expandableNotificationRow);
            int i = expandableNotificationRow.mActualHeight;
            int i2 = this.mVertSpaceForIcons;
            if (i < i2) {
                f = (float) ((i / 2) - (this.mHorizSpaceForIcon / 2));
            } else {
                f = (float) ((i2 - this.mHorizSpaceForIcon) / 2);
            }
            this.mMenuContainer.setTranslationY(f);
        }
    }

    public final void onParentTranslationUpdate(float f) {
        this.mTranslation = f;
        if (!this.mAnimating && this.mMenuFadedIn) {
            float width = ((float) this.mParent.getWidth()) * 0.3f;
            float abs = Math.abs(f);
            float f2 = 0.0f;
            if (abs != 0.0f) {
                if (abs <= width) {
                    f2 = 1.0f;
                } else {
                    f2 = 1.0f - ((abs - width) / (((float) this.mParent.getWidth()) - width));
                }
            }
            setMenuAlpha(f2);
        }
    }

    public final void populateMenuViews() {
        ArrayList<NotificationMenuRowPlugin.MenuItem> arrayList;
        FrameLayout frameLayout = this.mMenuContainer;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            this.mMenuItemsByView.clear();
        } else {
            this.mMenuContainer = new FrameLayout(this.mContext);
        }
        boolean z = true;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "show_new_notif_dismiss", 1) != 1) {
            z = false;
        }
        if (!z) {
            if (this.mOnLeft) {
                arrayList = this.mLeftMenuItems;
            } else {
                arrayList = this.mRightMenuItems;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                NotificationMenuRowPlugin.MenuItem menuItem = arrayList.get(i);
                FrameLayout frameLayout2 = this.mMenuContainer;
                View menuView = menuItem.getMenuView();
                if (menuView != null) {
                    menuView.setAlpha(this.mAlpha);
                    frameLayout2.addView(menuView);
                    menuView.setOnClickListener(this);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) menuView.getLayoutParams();
                    int i2 = this.mHorizSpaceForIcon;
                    layoutParams.width = i2;
                    layoutParams.height = i2;
                    menuView.setLayoutParams(layoutParams);
                }
                this.mMenuItemsByView.put(menuView, menuItem);
            }
        }
    }

    @VisibleForTesting
    public void setMenuAlpha(float f) {
        this.mAlpha = f;
        FrameLayout frameLayout = this.mMenuContainer;
        if (frameLayout != null) {
            if (f == 0.0f) {
                this.mMenuFadedIn = false;
                frameLayout.setVisibility(4);
            } else {
                frameLayout.setVisibility(0);
            }
            int childCount = this.mMenuContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mMenuContainer.getChildAt(i).setAlpha(this.mAlpha);
            }
        }
    }

    public final void setMenuLocation() {
        boolean z;
        FrameLayout frameLayout;
        int i = 0;
        if (this.mTranslation > 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if ((!this.mIconsPlaced || z != isMenuOnLeft()) && !isSnapping() && (frameLayout = this.mMenuContainer) != null && frameLayout.isAttachedToWindow()) {
            boolean z2 = this.mOnLeft;
            this.mOnLeft = z;
            if (z2 != z) {
                populateMenuViews();
            }
            int childCount = this.mMenuContainer.getChildCount();
            while (i < childCount) {
                View childAt = this.mMenuContainer.getChildAt(i);
                float f = (float) (this.mHorizSpaceForIcon * i);
                i++;
                float width = (float) (this.mParent.getWidth() - (this.mHorizSpaceForIcon * i));
                if (!z) {
                    f = width;
                }
                childAt.setX(f);
            }
            this.mIconsPlaced = true;
        }
    }

    public NotificationMenuRow(Context context, PeopleNotificationIdentifier peopleNotificationIdentifier) {
        this.mContext = context;
        this.mShouldShowMenu = context.getResources().getBoolean(C1777R.bool.config_showNotificationGear);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mLeftMenuItems = new ArrayList<>();
        this.mRightMenuItems = new ArrayList<>();
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
    }

    public static NotificationMenuItem createConversationItem(Context context) {
        return new NotificationMenuItem(context, context.getResources().getString(C1777R.string.notification_menu_gear_description), (NotificationConversationInfo) LayoutInflater.from(context).inflate(C1777R.layout.notification_conversation_info, (ViewGroup) null, false), C1777R.C1778drawable.ic_settings);
    }

    public static NotificationMenuItem createInfoItem(Context context) {
        return new NotificationMenuItem(context, context.getResources().getString(C1777R.string.notification_menu_gear_description), (NotificationInfo) LayoutInflater.from(context).inflate(C1777R.layout.notification_info, (ViewGroup) null, false), C1777R.C1778drawable.ic_settings);
    }

    public static NotificationMenuItem createPartialConversationItem(Context context) {
        return new NotificationMenuItem(context, context.getResources().getString(C1777R.string.notification_menu_gear_description), (PartialConversationInfo) LayoutInflater.from(context).inflate(C1777R.layout.partial_conversation_info, (ViewGroup) null, false), C1777R.C1778drawable.ic_settings);
    }

    public static NotificationMenuItem createSnoozeItem(Context context) {
        return new NotificationMenuItem(context, context.getResources().getString(C1777R.string.notification_menu_snooze_description), (NotificationSnooze) LayoutInflater.from(context).inflate(C1777R.layout.notification_snooze, (ViewGroup) null, false), C1777R.C1778drawable.ic_snooze);
    }

    public final boolean canBeDismissed() {
        return getParent().canViewBeDismissed();
    }

    @VisibleForTesting
    public float getDismissThreshold() {
        return ((float) getParent().getWidth()) * 0.6f;
    }

    public final int getMenuSnapTarget() {
        boolean isMenuOnLeft = isMenuOnLeft();
        int spaceForMenu = getSpaceForMenu();
        if (isMenuOnLeft) {
            return spaceForMenu;
        }
        return -spaceForMenu;
    }

    @VisibleForTesting
    public float getMinimumSwipeDistance() {
        float f;
        if (getParent().canViewBeDismissed()) {
            f = 0.25f;
        } else {
            f = 0.15f;
        }
        return ((float) this.mHorizSpaceForIcon) * f;
    }

    @VisibleForTesting
    public float getSnapBackThreshold() {
        return ((float) getSpaceForMenu()) - getMaximumSwipeDistance();
    }

    public final boolean isSnappedAndOnSameSide() {
        if (!isMenuSnapped() || !isMenuVisible() || isMenuSnappedOnLeft() != isMenuOnLeft()) {
            return false;
        }
        return true;
    }

    public final boolean isSwipedEnoughToShowMenu() {
        float minimumSwipeDistance = getMinimumSwipeDistance();
        float translation = getTranslation();
        if (!isMenuVisible() || (!isMenuOnLeft() ? translation >= (-minimumSwipeDistance) : translation <= minimumSwipeDistance)) {
            return false;
        }
        return true;
    }

    public final boolean isTowardsMenu(float f) {
        if (!isMenuVisible() || ((!isMenuOnLeft() || f > 0.0f) && (isMenuOnLeft() || f < 0.0f))) {
            return false;
        }
        return true;
    }

    public final boolean isWithinSnapMenuThreshold() {
        float translation = getTranslation();
        float snapBackThreshold = getSnapBackThreshold();
        float dismissThreshold = getDismissThreshold();
        if (isMenuOnLeft()) {
            if (translation > snapBackThreshold && translation < dismissThreshold) {
                return true;
            }
        } else if (translation < (-snapBackThreshold) && translation > (-dismissThreshold)) {
            return true;
        }
        return false;
    }

    public final void onDismiss() {
        cancelDrag();
        this.mMenuSnapped = false;
        this.mDismissing = true;
    }

    public final void onSnapClosed() {
        cancelDrag();
        this.mMenuSnapped = false;
        this.mSnapping = true;
    }

    public final void onTouchStart() {
        beginDrag();
        this.mSnappingToDismiss = false;
    }

    public final void setAppName(String str, ArrayList<NotificationMenuRowPlugin.MenuItem> arrayList) {
        Resources resources = this.mContext.getResources();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            NotificationMenuRowPlugin.MenuItem menuItem = arrayList.get(i);
            String format = String.format(resources.getString(C1777R.string.notification_menu_accessibility), new Object[]{str, menuItem.getContentDescription()});
            View menuView = menuItem.getMenuView();
            if (menuView != null) {
                menuView.setContentDescription(format);
            }
        }
    }

    public final boolean shouldSnapBack() {
        float translation = getTranslation();
        float snapBackThreshold = getSnapBackThreshold();
        if (isMenuOnLeft()) {
            if (translation < snapBackThreshold) {
                return true;
            }
        } else if (translation > (-snapBackThreshold)) {
            return true;
        }
        return false;
    }

    public final NotificationMenuRowPlugin.MenuItem getFeedbackMenuItem(Context context) {
        return this.mFeedbackItem;
    }

    public final NotificationMenuRowPlugin.MenuItem getLongpressMenuItem(Context context) {
        return this.mInfoItem;
    }

    public final NotificationMenuRowPlugin.MenuItem getSnoozeMenuItem(Context context) {
        return this.mSnoozeItem;
    }

    public final void setMenuClickListener(NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener) {
        this.mMenuListener = onMenuEventListener;
    }

    public final View getMenuView() {
        return this.mMenuContainer;
    }

    @VisibleForTesting
    public ExpandableNotificationRow getParent() {
        return this.mParent;
    }

    @VisibleForTesting
    public float getTranslation() {
        return this.mTranslation;
    }

    @VisibleForTesting
    public boolean isDismissing() {
        return this.mDismissing;
    }

    @VisibleForTesting
    public boolean isMenuOnLeft() {
        return this.mOnLeft;
    }

    @VisibleForTesting
    public boolean isMenuSnapped() {
        return this.mMenuSnapped;
    }

    @VisibleForTesting
    public boolean isMenuSnappedOnLeft() {
        return this.mMenuSnappedOnLeft;
    }

    @VisibleForTesting
    public boolean isSnapping() {
        return this.mSnapping;
    }

    @VisibleForTesting
    public boolean isSnappingToDismiss() {
        return this.mSnappingToDismiss;
    }

    @VisibleForTesting
    public boolean isUserTouching() {
        return this.mIsUserTouching;
    }

    public final boolean shouldShowMenu() {
        return this.mShouldShowMenu;
    }
}
