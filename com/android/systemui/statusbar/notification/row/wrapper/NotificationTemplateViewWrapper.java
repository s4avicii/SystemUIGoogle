package com.android.systemui.statusbar.notification.row.wrapper;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Pools;
import android.view.NotificationHeaderView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.leanback.R$raw;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda20;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda0;
import com.android.systemui.Dependency;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.util.wakelock.WakeLock$$ExternalSyntheticLambda0;
import com.google.android.systemui.dreamliner.DockObserver$$ExternalSyntheticLambda0;
import java.util.Objects;

public class NotificationTemplateViewWrapper extends NotificationHeaderViewWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public NotificationActionListLayout mActions;
    public View mActionsContainer;
    public final boolean mAllowHideHeader;
    public boolean mCanHideHeader;
    public ArraySet<PendingIntent> mCancelledPendingIntents = new ArraySet<>();
    public final int mFullHeaderTranslation;
    public float mHeaderTranslation;
    public ImageView mLeftIcon;
    public ProgressBar mProgressBar;
    public View mRemoteInputHistory;
    public ImageView mRightIcon;
    public View mSmartReplyContainer;
    public TextView mText;
    public TextView mTitle;
    public UiOffloadThread mUiOffloadThread;

    public final int getExtraMeasureHeight() {
        int i;
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        if (notificationActionListLayout != null) {
            i = notificationActionListLayout.getExtraMeasureHeight();
        } else {
            i = 0;
        }
        View view = this.mRemoteInputHistory;
        if (!(view == null || view.getVisibility() == 8)) {
            i += this.mRow.getContext().getResources().getDimensionPixelSize(C1777R.dimen.remote_input_history_extra_height);
        }
        return i + 0;
    }

    public final int getHeaderTranslation(boolean z) {
        if (!z || !this.mCanHideHeader) {
            return (int) this.mHeaderTranslation;
        }
        return this.mFullHeaderTranslation;
    }

    public final void setContentHeight(int i, int i2) {
        if (this.mActionsContainer != null) {
            this.mActionsContainer.setTranslationY((float) ((Math.max(i, i2) - this.mView.getHeight()) - getHeaderTranslation(false)));
        }
    }

    public final void setHeaderVisibleAmount(float f) {
        float f2;
        NotificationHeaderView notificationHeaderView;
        if (!this.mCanHideHeader || (notificationHeaderView = this.mNotificationHeader) == null) {
            f2 = 0.0f;
        } else {
            notificationHeaderView.setAlpha(f);
            f2 = (1.0f - f) * ((float) this.mFullHeaderTranslation);
        }
        this.mHeaderTranslation = f2;
        this.mView.setTranslationY(f2);
    }

    public boolean shouldClipToRounding(boolean z) {
        View view;
        if (this instanceof NotificationCustomViewWrapper) {
            return true;
        }
        if (!z || (view = this.mActionsContainer) == null || view.getVisibility() == 8) {
            return false;
        }
        return true;
    }

    public NotificationTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(view, expandableNotificationRow);
        this.mAllowHideHeader = context.getResources().getBoolean(C1777R.bool.heads_up_notification_hides_header);
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        C13391 r4 = new ViewTransformationHelper.CustomTransformation() {
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = transformableView.getCurrentState(1);
                R$raw.fadeIn(transformState.mTransformedView, f, true);
                if (currentState != null) {
                    transformState.transformViewFrom(currentState, 16, this, f);
                    currentState.recycle();
                }
                return true;
            }

            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = transformableView.getCurrentState(1);
                R$raw.fadeOut(transformState.mTransformedView, f, true);
                if (currentState != null) {
                    transformState.transformViewTo(currentState, 16, this, f);
                    currentState.recycle();
                }
                return true;
            }

            public final boolean customTransformTarget(TransformState transformState, TransformState transformState2) {
                int[] laidOutLocationOnScreen = transformState2.getLaidOutLocationOnScreen();
                int[] laidOutLocationOnScreen2 = transformState.getLaidOutLocationOnScreen();
                transformState.mTransformationEndY = ((float) ((transformState2.mTransformedView.getHeight() + laidOutLocationOnScreen[1]) - laidOutLocationOnScreen2[1])) * 0.33f;
                return true;
            }

            public final boolean initTransformation(TransformState transformState, TransformState transformState2) {
                int[] laidOutLocationOnScreen = transformState2.getLaidOutLocationOnScreen();
                int[] laidOutLocationOnScreen2 = transformState.getLaidOutLocationOnScreen();
                transformState.setTransformationStartY(((float) ((transformState2.mTransformedView.getHeight() + laidOutLocationOnScreen[1]) - laidOutLocationOnScreen2[1])) * 0.33f);
                return true;
            }
        };
        Objects.requireNonNull(viewTransformationHelper);
        viewTransformationHelper.mCustomTransformations.put(2, r4);
        this.mFullHeaderTranslation = context.getResources().getDimensionPixelSize(17105387) - context.getResources().getDimensionPixelSize(17105390);
    }

    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        ImageView imageView;
        Bitmap bitmap;
        Icon icon;
        Bitmap bitmap2;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        ImageView imageView2 = (ImageView) this.mView.findViewById(16909420);
        this.mRightIcon = imageView2;
        if (imageView2 != null) {
            Pools.SimplePool<ImageTransformState> simplePool = ImageTransformState.sInstancePool;
            Notification notification = statusBarNotification.getNotification();
            if ((!notification.extras.getBoolean("android.showBigPictureWhenCollapsed") || !notification.isStyle(Notification.BigPictureStyle.class) || (icon = Notification.BigPictureStyle.getPictureIcon(notification.extras)) == null) && (icon = notification.getLargeIcon()) == null && (bitmap2 = notification.largeIcon) != null) {
                icon = Icon.createWithBitmap(bitmap2);
            }
            imageView2.setTag(C1777R.C1779id.image_icon_tag, icon);
            ImageView imageView3 = this.mRightIcon;
            Pools.SimplePool<TransformState> simplePool2 = TransformState.sInstancePool;
            imageView3.setTag(C1777R.C1779id.align_transform_end_tag, Boolean.TRUE);
        }
        ImageView imageView4 = (ImageView) this.mView.findViewById(16909170);
        this.mLeftIcon = imageView4;
        if (imageView4 != null) {
            Pools.SimplePool<ImageTransformState> simplePool3 = ImageTransformState.sInstancePool;
            Notification notification2 = statusBarNotification.getNotification();
            Icon largeIcon = notification2.getLargeIcon();
            if (largeIcon == null && (bitmap = notification2.largeIcon) != null) {
                largeIcon = Icon.createWithBitmap(bitmap);
            }
            imageView4.setTag(C1777R.C1779id.image_icon_tag, largeIcon);
        }
        this.mTitle = (TextView) this.mView.findViewById(16908310);
        this.mText = (TextView) this.mView.findViewById(16909566);
        View findViewById = this.mView.findViewById(16908301);
        if (findViewById instanceof ProgressBar) {
            this.mProgressBar = (ProgressBar) findViewById;
        } else {
            this.mProgressBar = null;
        }
        this.mSmartReplyContainer = this.mView.findViewById(16909500);
        this.mActionsContainer = this.mView.findViewById(16908742);
        this.mActions = this.mView.findViewById(16908741);
        this.mRemoteInputHistory = this.mView.findViewById(16909276);
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        boolean z = false;
        if (notificationActionListLayout != null) {
            int childCount = notificationActionListLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                Button button = (Button) this.mActions.getChildAt(i);
                ExecutorUtils$$ExternalSyntheticLambda0 executorUtils$$ExternalSyntheticLambda0 = new ExecutorUtils$$ExternalSyntheticLambda0(this, button, 3);
                final PendingIntent pendingIntent = (PendingIntent) button.getTag(16909325);
                if (pendingIntent != null) {
                    if (this.mCancelledPendingIntents.contains(pendingIntent)) {
                        executorUtils$$ExternalSyntheticLambda0.run();
                    } else {
                        final NotificationTemplateViewWrapper$$ExternalSyntheticLambda0 notificationTemplateViewWrapper$$ExternalSyntheticLambda0 = new NotificationTemplateViewWrapper$$ExternalSyntheticLambda0(this, pendingIntent, executorUtils$$ExternalSyntheticLambda0);
                        if (this.mUiOffloadThread == null) {
                            this.mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
                        }
                        if (button.isAttachedToWindow()) {
                            UiOffloadThread uiOffloadThread = this.mUiOffloadThread;
                            DockObserver$$ExternalSyntheticLambda0 dockObserver$$ExternalSyntheticLambda0 = new DockObserver$$ExternalSyntheticLambda0(pendingIntent, notificationTemplateViewWrapper$$ExternalSyntheticLambda0, 1);
                            Objects.requireNonNull(uiOffloadThread);
                            uiOffloadThread.mExecutorService.submit(dockObserver$$ExternalSyntheticLambda0);
                        }
                        button.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                            public final void onViewAttachedToWindow(View view) {
                                UiOffloadThread uiOffloadThread = NotificationTemplateViewWrapper.this.mUiOffloadThread;
                                BubbleStackView$$ExternalSyntheticLambda20 bubbleStackView$$ExternalSyntheticLambda20 = new BubbleStackView$$ExternalSyntheticLambda20(pendingIntent, notificationTemplateViewWrapper$$ExternalSyntheticLambda0, 2);
                                Objects.requireNonNull(uiOffloadThread);
                                uiOffloadThread.mExecutorService.submit(bubbleStackView$$ExternalSyntheticLambda20);
                            }

                            public final void onViewDetachedFromWindow(View view) {
                                UiOffloadThread uiOffloadThread = NotificationTemplateViewWrapper.this.mUiOffloadThread;
                                WakeLock$$ExternalSyntheticLambda0 wakeLock$$ExternalSyntheticLambda0 = new WakeLock$$ExternalSyntheticLambda0(pendingIntent, notificationTemplateViewWrapper$$ExternalSyntheticLambda0, 1);
                                Objects.requireNonNull(uiOffloadThread);
                                uiOffloadThread.mExecutorService.submit(wakeLock$$ExternalSyntheticLambda0);
                            }
                        });
                    }
                }
            }
        }
        super.onContentUpdated(expandableNotificationRow);
        if (this.mAllowHideHeader && this.mNotificationHeader != null && ((imageView = this.mRightIcon) == null || imageView.getVisibility() != 0)) {
            z = true;
        }
        this.mCanHideHeader = z;
        float f = expandableNotificationRow.mHeaderVisibleAmount;
        if (f != 1.0f) {
            setHeaderVisibleAmount(f);
        }
    }

    public void updateTransformedTypes() {
        super.updateTransformedTypes();
        TextView textView = this.mTitle;
        if (textView != null) {
            this.mTransformationHelper.addTransformedView(1, textView);
        }
        TextView textView2 = this.mText;
        if (textView2 != null) {
            this.mTransformationHelper.addTransformedView(2, textView2);
        }
        ImageView imageView = this.mRightIcon;
        if (imageView != null) {
            this.mTransformationHelper.addTransformedView(3, imageView);
        }
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            this.mTransformationHelper.addTransformedView(4, progressBar);
        }
        addViewsTransformingToSimilar(this.mLeftIcon);
        addTransformedViews(this.mSmartReplyContainer);
    }
}
