package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.view.View;
import androidx.fragment.R$id;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class FooterView extends StackScrollerDecorView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FooterViewButton mClearAllButton;
    public FooterViewButton mManageButton;
    public String mManageNotificationHistoryText;
    public String mManageNotificationText;
    public boolean mShowHistory;

    public class FooterViewState extends ExpandableViewState {
        public boolean hideContent;

        public final void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof FooterView) {
                ((FooterView) view).setContentVisible(!this.hideContent);
            }
        }

        public final void copyFrom(ExpandableViewState expandableViewState) {
            super.copyFrom(expandableViewState);
            if (expandableViewState instanceof FooterViewState) {
                this.hideContent = ((FooterViewState) expandableViewState).hideContent;
            }
        }
    }

    public final ExpandableViewState createExpandableViewState() {
        return new FooterViewState();
    }

    public final void updateColors() {
        Resources.Theme theme = this.mContext.getTheme();
        int color = getResources().getColor(C1777R.color.notif_pill_text, theme);
        this.mClearAllButton.setBackground(theme.getDrawable(C1777R.C1778drawable.notif_footer_btn_background));
        this.mClearAllButton.setTextColor(color);
        this.mManageButton.setBackground(theme.getDrawable(C1777R.C1778drawable.notif_footer_btn_background));
        this.mManageButton.setTextColor(color);
    }

    public final void updateText() {
        if (this.mShowHistory) {
            this.mManageButton.setText(this.mManageNotificationHistoryText);
            this.mManageButton.setContentDescription(this.mManageNotificationHistoryText);
            return;
        }
        this.mManageButton.setText(this.mManageNotificationText);
        this.mManageButton.setContentDescription(this.mManageNotificationText);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = R$id.asIndenting(printWriter);
        super.dump(fileDescriptor, asIndenting, strArr);
        R$id.withIncreasedIndent(asIndenting, new PipController$PipImpl$$ExternalSyntheticLambda0(this, asIndenting, 1));
    }

    public final View findContentView() {
        return findViewById(C1777R.C1779id.content);
    }

    public final View findSecondaryView() {
        return findViewById(C1777R.C1779id.dismiss_text);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateColors();
        this.mClearAllButton.setText(C1777R.string.clear_all_notifications_text);
        this.mClearAllButton.setContentDescription(this.mContext.getString(C1777R.string.accessibility_clear_all));
        this.mManageNotificationText = getContext().getString(C1777R.string.manage_notifications_text);
        this.mManageNotificationHistoryText = getContext().getString(C1777R.string.manage_notifications_history_text);
        updateText();
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mClearAllButton = (FooterViewButton) findSecondaryView();
        this.mManageButton = (FooterViewButton) findViewById(C1777R.C1779id.manage_text);
        this.mManageNotificationText = getContext().getString(C1777R.string.manage_notifications_text);
        this.mManageNotificationHistoryText = getContext().getString(C1777R.string.manage_notifications_history_text);
        updateText();
    }

    public FooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
