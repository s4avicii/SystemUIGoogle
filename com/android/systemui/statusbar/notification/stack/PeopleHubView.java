package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: PeopleHubView.kt */
public final class PeopleHubView extends StackScrollerDecorView implements SwipeableView {
    public ViewGroup contents;

    /* compiled from: PeopleHubView.kt */
    public final class PersonDataListenerImpl {
    }

    public final NotificationMenuRowPlugin createMenu() {
        return null;
    }

    public final View findSecondaryView() {
        return null;
    }

    public final boolean hasFinishedInitialization() {
        return true;
    }

    public final void resetTranslation() {
        setTranslationX(0.0f);
    }

    public final void setTranslation(float f) {
    }

    public final View findContentView() {
        ViewGroup viewGroup = this.contents;
        if (viewGroup == null) {
            return null;
        }
        return viewGroup;
    }

    public final void onFinishInflate() {
        IntRange intRange;
        this.contents = (ViewGroup) requireViewById(C1777R.C1779id.people_list);
        TextView textView = (TextView) requireViewById(C1777R.C1779id.header_label);
        ViewGroup viewGroup = this.contents;
        if (viewGroup == null) {
            viewGroup = null;
        }
        int childCount = viewGroup.getChildCount();
        if (childCount <= Integer.MIN_VALUE) {
            intRange = IntRange.EMPTY;
        } else {
            intRange = new IntRange(0, childCount - 1);
        }
        SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(intRange), new PeopleHubView$onFinishInflate$1(this)));
        super.onFinishInflate();
        setVisible(true, false);
    }

    public PeopleHubView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
