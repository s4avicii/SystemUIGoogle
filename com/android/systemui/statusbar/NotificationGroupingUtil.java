package com.android.systemui.statusbar;

import android.app.Notification;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public final class NotificationGroupingUtil {
    public static final AppNameApplicator APP_NAME_APPLICATOR = new AppNameApplicator();
    public static final AppNameComparator APP_NAME_COMPARATOR = new AppNameComparator();
    public static final BadgeComparator BADGE_COMPARATOR = new BadgeComparator();
    public static final C11574 GREY_APPLICATOR = new ResultApplicator() {
        public final void apply(View view, View view2, boolean z, boolean z2) {
            CachingIconView findViewById = view2.findViewById(16908294);
            if (findViewById != null) {
                findViewById.setGrayedOut(z);
            }
        }
    };
    public static final C11563 GREY_COMPARATOR = new IconComparator() {
        public final boolean compare(View view, View view2, Notification notification, Notification notification2) {
            boolean z;
            if (notification.getSmallIcon().sameAs(notification2.getSmallIcon())) {
                if (notification.color == notification2.color) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    return false;
                }
            }
            return true;
        }
    };
    public static final C11541 ICON_EXTRACTOR = new DataExtractor() {
    };
    public static final C11552 ICON_VISIBILITY_COMPARATOR = new IconComparator() {
        public final boolean compare(View view, View view2, Notification notification, Notification notification2) {
            boolean z;
            if (notification.getSmallIcon().sameAs(notification2.getSmallIcon())) {
                if (notification.color == notification2.color) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return true;
                }
            }
            return false;
        }
    };
    public static final LeftIconApplicator LEFT_ICON_APPLICATOR = new LeftIconApplicator();
    public static final TextViewComparator TEXT_VIEW_COMPARATOR = new TextViewComparator();
    public static final VisibilityApplicator VISIBILITY_APPLICATOR = new VisibilityApplicator();
    public final HashSet<Integer> mDividers;
    public final ArrayList<Processor> mProcessors;
    public final ExpandableNotificationRow mRow;

    public static class AppNameApplicator extends VisibilityApplicator {
        public final void apply(View view, View view2, boolean z, boolean z2) {
            if (z2 && (view instanceof ConversationLayout)) {
                z = ((ConversationLayout) view).shouldHideAppName();
            }
            super.apply(view, view2, z, z2);
        }
    }

    public static class BadgeComparator implements ViewComparator {
        public final boolean isEmpty(View view) {
            if (!(view instanceof ImageView) || ((ImageView) view).getDrawable() != null) {
                return false;
            }
            return true;
        }

        public final boolean compare(View view, View view2, Notification notification, Notification notification2) {
            if (view.getVisibility() != 8) {
                return true;
            }
            return false;
        }
    }

    public interface DataExtractor {
    }

    public static abstract class IconComparator implements ViewComparator {
        public final boolean isEmpty(View view) {
            return false;
        }
    }

    public static class Processor {
        public final ResultApplicator mApplicator;
        public boolean mApply;
        public final ViewComparator mComparator;
        public final DataExtractor mExtractor;
        public final int mId;
        public Notification mParentData;
        public final ExpandableNotificationRow mParentRow;
        public View mParentView;

        public final void apply(ExpandableNotificationRow expandableNotificationRow, boolean z) {
            boolean z2;
            if (!this.mApply || z) {
                z2 = false;
            } else {
                z2 = true;
            }
            Objects.requireNonNull(expandableNotificationRow);
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                applyToView(z2, z, expandableNotificationRow.getNotificationViewWrapper().getNotificationHeader());
                return;
            }
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            Objects.requireNonNull(notificationContentView);
            applyToView(z2, z, notificationContentView.mContractedChild);
            NotificationContentView notificationContentView2 = expandableNotificationRow.mPrivateLayout;
            Objects.requireNonNull(notificationContentView2);
            applyToView(z2, z, notificationContentView2.mHeadsUpChild);
            NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
            Objects.requireNonNull(notificationContentView3);
            applyToView(z2, z, notificationContentView3.mExpandedChild);
        }

        public final void applyToView(boolean z, boolean z2, View view) {
            View findViewById;
            if (view != null && (findViewById = view.findViewById(this.mId)) != null && !this.mComparator.isEmpty(findViewById)) {
                this.mApplicator.apply(view, findViewById, z, z2);
            }
        }

        public Processor(ExpandableNotificationRow expandableNotificationRow, int i, C11541 r3, ViewComparator viewComparator, ResultApplicator resultApplicator) {
            this.mId = i;
            this.mExtractor = r3;
            this.mApplicator = resultApplicator;
            this.mComparator = viewComparator;
            this.mParentRow = expandableNotificationRow;
        }
    }

    public interface ResultApplicator {
        void apply(View view, View view2, boolean z, boolean z2);
    }

    public static class TextViewComparator implements ViewComparator {
        public boolean compare(View view, View view2, Notification notification, Notification notification2) {
            Object obj;
            TextView textView = (TextView) view;
            if (textView == null) {
                obj = "";
            } else {
                obj = textView.getText();
            }
            return Objects.equals(obj, ((TextView) view2).getText());
        }

        public final boolean isEmpty(View view) {
            if (view == null || TextUtils.isEmpty(((TextView) view).getText())) {
                return true;
            }
            return false;
        }
    }

    public interface ViewComparator {
        boolean compare(View view, View view2, Notification notification, Notification notification2);

        boolean isEmpty(View view);
    }

    public static class VisibilityApplicator implements ResultApplicator {
        public void apply(View view, View view2, boolean z, boolean z2) {
            int i;
            if (z) {
                i = 8;
            } else {
                i = 0;
            }
            view2.setVisibility(i);
        }
    }

    public static class AppNameComparator extends TextViewComparator {
        public final boolean compare(View view, View view2, Notification notification, Notification notification2) {
            if (isEmpty(view2)) {
                return true;
            }
            return super.compare(view, view2, notification, notification2);
        }
    }

    public static class LeftIconApplicator implements ResultApplicator {
        public static final int[] MARGIN_ADJUSTED_VIEWS = {16909566, 16908820, 16908310, 16909275, 16909272};

        /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0053  */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0055  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x005b A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:58:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void apply(android.view.View r5, android.view.View r6, boolean r7, boolean r8) {
            /*
                r4 = this;
                r4 = 16909170(0x1020372, float:2.38797E-38)
                android.view.View r4 = r6.findViewById(r4)
                android.widget.ImageView r4 = (android.widget.ImageView) r4
                if (r4 != 0) goto L_0x000c
                return
            L_0x000c:
                r5 = 16909420(0x102046c, float:2.3880402E-38)
                android.view.View r5 = r6.findViewById(r5)
                android.widget.ImageView r5 = (android.widget.ImageView) r5
                r8 = 0
                r0 = 1
                if (r5 == 0) goto L_0x002c
                java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
                r2 = 16909559(0x10204f7, float:2.388079E-38)
                java.lang.Object r2 = r5.getTag(r2)
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x002c
                r1 = r0
                goto L_0x002d
            L_0x002c:
                r1 = r8
            L_0x002d:
                java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
                r3 = 16909565(0x10204fd, float:2.3880808E-38)
                java.lang.Object r3 = r4.getTag(r3)
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x004f
                r2 = 0
                if (r5 != 0) goto L_0x0043
                r3 = r2
                goto L_0x0047
            L_0x0043:
                android.graphics.drawable.Drawable r3 = r5.getDrawable()
            L_0x0047:
                if (r7 == 0) goto L_0x004c
                if (r1 != 0) goto L_0x004c
                r2 = r3
            L_0x004c:
                r4.setImageDrawable(r2)
            L_0x004f:
                r2 = 8
                if (r7 == 0) goto L_0x0055
                r3 = r8
                goto L_0x0056
            L_0x0055:
                r3 = r2
            L_0x0056:
                r4.setVisibility(r3)
                if (r5 == 0) goto L_0x00c5
                if (r1 != 0) goto L_0x005f
                if (r7 != 0) goto L_0x0066
            L_0x005f:
                android.graphics.drawable.Drawable r4 = r5.getDrawable()
                if (r4 == 0) goto L_0x0066
                goto L_0x0067
            L_0x0066:
                r0 = r8
            L_0x0067:
                if (r0 == 0) goto L_0x006a
                r2 = r8
            L_0x006a:
                r5.setVisibility(r2)
                int[] r4 = MARGIN_ADJUSTED_VIEWS
                r5 = 5
            L_0x0070:
                if (r8 >= r5) goto L_0x00c5
                r7 = r4[r8]
                android.view.View r7 = r6.findViewById(r7)
                if (r7 != 0) goto L_0x007b
                goto L_0x00c2
            L_0x007b:
                boolean r1 = r7 instanceof com.android.internal.widget.ImageFloatingTextView
                if (r1 == 0) goto L_0x0085
                com.android.internal.widget.ImageFloatingTextView r7 = (com.android.internal.widget.ImageFloatingTextView) r7
                r7.setHasImage(r0)
                goto L_0x00c2
            L_0x0085:
                if (r0 == 0) goto L_0x008b
                r1 = 16909562(0x10204fa, float:2.38808E-38)
                goto L_0x008e
            L_0x008b:
                r1 = 16909561(0x10204f9, float:2.3880797E-38)
            L_0x008e:
                java.lang.Object r1 = r7.getTag(r1)
                java.lang.Integer r1 = (java.lang.Integer) r1
                if (r1 != 0) goto L_0x0097
                goto L_0x00c2
            L_0x0097:
                android.content.res.Resources r2 = r7.getResources()
                android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
                int r1 = r1.intValue()
                int r1 = android.util.TypedValue.complexToDimensionPixelOffset(r1, r2)
                boolean r2 = r7 instanceof android.view.NotificationHeaderView
                if (r2 == 0) goto L_0x00b1
                android.view.NotificationHeaderView r7 = (android.view.NotificationHeaderView) r7
                r7.setTopLineExtraMarginEnd(r1)
                goto L_0x00c2
            L_0x00b1:
                android.view.ViewGroup$LayoutParams r2 = r7.getLayoutParams()
                boolean r3 = r2 instanceof android.view.ViewGroup.MarginLayoutParams
                if (r3 == 0) goto L_0x00c2
                r3 = r2
                android.view.ViewGroup$MarginLayoutParams r3 = (android.view.ViewGroup.MarginLayoutParams) r3
                r3.setMarginEnd(r1)
                r7.setLayoutParams(r2)
            L_0x00c2:
                int r8 = r8 + 1
                goto L_0x0070
            L_0x00c5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.apply(android.view.View, android.view.View, boolean, boolean):void");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void sanitizeTopLine(android.view.ViewGroup r10, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11) {
        /*
            r9 = this;
            if (r10 != 0) goto L_0x0003
            return
        L_0x0003:
            int r0 = r10.getChildCount()
            r1 = 16909597(0x102051d, float:2.3880898E-38)
            android.view.View r1 = r10.findViewById(r1)
            r2 = 0
            r3 = r2
        L_0x0010:
            r4 = 8
            r5 = 1
            if (r3 >= r0) goto L_0x003a
            android.view.View r6 = r10.getChildAt(r3)
            boolean r7 = r6 instanceof android.widget.TextView
            if (r7 == 0) goto L_0x0037
            int r7 = r6.getVisibility()
            if (r7 == r4) goto L_0x0037
            java.util.HashSet<java.lang.Integer> r7 = r9.mDividers
            int r8 = r6.getId()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            boolean r7 = r7.contains(r8)
            if (r7 != 0) goto L_0x0037
            if (r6 == r1) goto L_0x0037
            r3 = r5
            goto L_0x003b
        L_0x0037:
            int r3 = r3 + 1
            goto L_0x0010
        L_0x003a:
            r3 = r2
        L_0x003b:
            if (r3 == 0) goto L_0x0054
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = r11.mEntry
            java.util.Objects.requireNonNull(r11)
            android.service.notification.StatusBarNotification r11 = r11.mSbn
            android.app.Notification r11 = r11.getNotification()
            boolean r11 = r11.showsTime()
            if (r11 == 0) goto L_0x0052
            goto L_0x0054
        L_0x0052:
            r11 = r4
            goto L_0x0055
        L_0x0054:
            r11 = r2
        L_0x0055:
            r1.setVisibility(r11)
            r11 = 0
            r1 = r2
        L_0x005a:
            if (r1 >= r0) goto L_0x00b2
            android.view.View r3 = r10.getChildAt(r1)
            java.util.HashSet<java.lang.Integer> r6 = r9.mDividers
            int r7 = r3.getId()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            boolean r6 = r6.contains(r7)
            if (r6 == 0) goto L_0x00a5
        L_0x0070:
            int r1 = r1 + 1
            if (r1 >= r0) goto L_0x0099
            android.view.View r6 = r10.getChildAt(r1)
            java.util.HashSet<java.lang.Integer> r7 = r9.mDividers
            int r8 = r6.getId()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            boolean r7 = r7.contains(r8)
            if (r7 == 0) goto L_0x008b
            int r1 = r1 + -1
            goto L_0x0099
        L_0x008b:
            int r7 = r6.getVisibility()
            if (r7 == r4) goto L_0x0070
            boolean r7 = r6 instanceof android.widget.TextView
            if (r7 == 0) goto L_0x0070
            if (r11 == 0) goto L_0x009a
            r11 = r5
            goto L_0x009b
        L_0x0099:
            r6 = r11
        L_0x009a:
            r11 = r2
        L_0x009b:
            if (r11 == 0) goto L_0x009f
            r11 = r2
            goto L_0x00a0
        L_0x009f:
            r11 = r4
        L_0x00a0:
            r3.setVisibility(r11)
            r11 = r6
            goto L_0x00b0
        L_0x00a5:
            int r6 = r3.getVisibility()
            if (r6 == r4) goto L_0x00b0
            boolean r6 = r3 instanceof android.widget.TextView
            if (r6 == 0) goto L_0x00b0
            r11 = r3
        L_0x00b0:
            int r1 = r1 + r5
            goto L_0x005a
        L_0x00b2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.sanitizeTopLine(android.view.ViewGroup, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow):void");
    }

    public NotificationGroupingUtil(ExpandableNotificationRow expandableNotificationRow) {
        ArrayList<Processor> arrayList = new ArrayList<>();
        this.mProcessors = arrayList;
        HashSet<Integer> hashSet = new HashSet<>();
        this.mDividers = hashSet;
        this.mRow = expandableNotificationRow;
        C11541 r9 = ICON_EXTRACTOR;
        C11552 r10 = ICON_VISIBILITY_COMPARATOR;
        VisibilityApplicator visibilityApplicator = VISIBILITY_APPLICATOR;
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
        C11541 r6 = r9;
        arrayList.add(new Processor(expandableNotificationRow2, 16908294, r6, r10, visibilityApplicator));
        arrayList.add(new Processor(expandableNotificationRow2, 16909536, r6, GREY_COMPARATOR, GREY_APPLICATOR));
        arrayList.add(new Processor(expandableNotificationRow2, 16909536, r6, r10, LEFT_ICON_APPLICATOR));
        arrayList.add(new Processor(expandableNotificationRow2, 16909365, (C11541) null, BADGE_COMPARATOR, visibilityApplicator));
        arrayList.add(new Processor(expandableNotificationRow2, 16908782, (C11541) null, APP_NAME_COMPARATOR, APP_NAME_APPLICATOR));
        arrayList.add(new Processor(expandableNotificationRow2, 16909071, (C11541) null, TEXT_VIEW_COMPARATOR, visibilityApplicator));
        hashSet.add(16909072);
        hashSet.add(16909074);
        hashSet.add(16909601);
    }

    public final void sanitizeTopLineViews(ExpandableNotificationRow expandableNotificationRow) {
        Objects.requireNonNull(expandableNotificationRow);
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            sanitizeTopLine(expandableNotificationRow.getNotificationViewWrapper().getNotificationHeader(), expandableNotificationRow);
            return;
        }
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        Objects.requireNonNull(notificationContentView);
        View view = notificationContentView.mContractedChild;
        if (view != null) {
            sanitizeTopLine((ViewGroup) view.findViewById(16909284), expandableNotificationRow);
        }
        View view2 = notificationContentView.mHeadsUpChild;
        if (view2 != null) {
            sanitizeTopLine((ViewGroup) view2.findViewById(16909284), expandableNotificationRow);
        }
        View view3 = notificationContentView.mExpandedChild;
        if (view3 != null) {
            sanitizeTopLine((ViewGroup) view3.findViewById(16909284), expandableNotificationRow);
        }
    }
}
