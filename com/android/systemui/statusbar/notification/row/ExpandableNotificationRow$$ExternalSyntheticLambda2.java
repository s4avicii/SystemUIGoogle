package com.android.systemui.statusbar.notification.row;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.IndentingPrintWriter;
import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ExpandableNotificationRow f$0;
    public final /* synthetic */ IndentingPrintWriter f$1;
    public final /* synthetic */ FileDescriptor f$2;
    public final /* synthetic */ String[] f$3;

    public /* synthetic */ ExpandableNotificationRow$$ExternalSyntheticLambda2(ExpandableNotificationRow expandableNotificationRow, IndentingPrintWriter indentingPrintWriter, FileDescriptor fileDescriptor, String[] strArr) {
        this.f$0 = expandableNotificationRow;
        this.f$1 = indentingPrintWriter;
        this.f$2 = fileDescriptor;
        this.f$3 = strArr;
    }

    public final void run() {
        boolean z;
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        IndentingPrintWriter indentingPrintWriter = this.f$1;
        FileDescriptor fileDescriptor = this.f$2;
        String[] strArr = this.f$3;
        ExpandableNotificationRow.C13092 r3 = ExpandableNotificationRow.TRANSLATE_CONTENT;
        Objects.requireNonNull(expandableNotificationRow);
        indentingPrintWriter.print("visibility: " + expandableNotificationRow.getVisibility());
        indentingPrintWriter.print(", alpha: " + expandableNotificationRow.getAlpha());
        indentingPrintWriter.print(", translation: " + expandableNotificationRow.getTranslation());
        indentingPrintWriter.print(", removed: " + expandableNotificationRow.mRemoved);
        indentingPrintWriter.print(", expandAnimationRunning: " + expandableNotificationRow.mExpandAnimationRunning);
        NotificationContentView showingLayout = expandableNotificationRow.getShowingLayout();
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(", privateShowing: ");
        if (showingLayout == expandableNotificationRow.mPrivateLayout) {
            z = true;
        } else {
            z = false;
        }
        m.append(z);
        indentingPrintWriter.print(m.toString());
        indentingPrintWriter.println();
        Objects.requireNonNull(showingLayout);
        indentingPrintWriter.print("contentView visibility: " + showingLayout.getVisibility());
        indentingPrintWriter.print(", alpha: " + showingLayout.getAlpha());
        indentingPrintWriter.print(", clipBounds: " + showingLayout.getClipBounds());
        indentingPrintWriter.print(", contentHeight: " + showingLayout.mContentHeight);
        indentingPrintWriter.print(", visibleType: " + showingLayout.mVisibleType);
        View viewForVisibleType = showingLayout.getViewForVisibleType(showingLayout.mVisibleType);
        indentingPrintWriter.print(", visibleView ");
        if (viewForVisibleType != null) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" visibility: ");
            m2.append(viewForVisibleType.getVisibility());
            indentingPrintWriter.print(m2.toString());
            indentingPrintWriter.print(", alpha: " + viewForVisibleType.getAlpha());
            indentingPrintWriter.print(", clipBounds: " + viewForVisibleType.getClipBounds());
        } else {
            indentingPrintWriter.print("null");
        }
        indentingPrintWriter.println();
        ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
        if (expandableViewState != null) {
            expandableViewState.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.println();
        } else {
            indentingPrintWriter.println("no viewState!!!");
        }
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            indentingPrintWriter.println();
            indentingPrintWriter.print("ChildrenContainer");
            indentingPrintWriter.print(" visibility: " + expandableNotificationRow.mChildrenContainer.getVisibility());
            indentingPrintWriter.print(", alpha: " + expandableNotificationRow.mChildrenContainer.getAlpha());
            indentingPrintWriter.print(", translationY: " + expandableNotificationRow.mChildrenContainer.getTranslationY());
            indentingPrintWriter.println();
            ArrayList<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
            StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Children: ");
            m3.append(attachedChildren.size());
            indentingPrintWriter.println(m3.toString());
            indentingPrintWriter.print("{");
            indentingPrintWriter.increaseIndent();
            for (ExpandableNotificationRow dump : attachedChildren) {
                indentingPrintWriter.println();
                dump.dump(fileDescriptor, indentingPrintWriter, strArr);
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("}");
            return;
        }
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        if (notificationContentView != null) {
            if (notificationContentView.mHeadsUpSmartReplyView != null) {
                indentingPrintWriter.println("HeadsUp SmartReplyView:");
                indentingPrintWriter.increaseIndent();
                notificationContentView.mHeadsUpSmartReplyView.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
            if (notificationContentView.mExpandedSmartReplyView != null) {
                indentingPrintWriter.println("Expanded SmartReplyView:");
                indentingPrintWriter.increaseIndent();
                notificationContentView.mExpandedSmartReplyView.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
        }
    }
}
