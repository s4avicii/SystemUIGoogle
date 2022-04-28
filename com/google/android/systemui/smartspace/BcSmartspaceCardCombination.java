package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.p012wm.shell.C1777R;
import java.util.List;

public class BcSmartspaceCardCombination extends BcSmartspaceCardSecondary {
    public ConstraintLayout mFirstSubCard;
    public ConstraintLayout mSecondSubCard;

    public BcSmartspaceCardCombination(Context context) {
        super(context);
    }

    public BcSmartspaceCardCombination(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean fillSubCard(ConstraintLayout constraintLayout, SmartspaceTarget smartspaceTarget, SmartspaceAction smartspaceAction, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        boolean z;
        CharSequence charSequence;
        TextView textView = (TextView) constraintLayout.findViewById(C1777R.C1779id.sub_card_text);
        ImageView imageView = (ImageView) constraintLayout.findViewById(C1777R.C1779id.sub_card_icon);
        if (textView == null) {
            Log.w("BcSmartspaceCardCombination", "No sub-card text field to update");
            return false;
        } else if (imageView == null) {
            Log.w("BcSmartspaceCardCombination", "No sub-card image field to update");
            return false;
        } else {
            BcSmartSpaceUtil.setOnClickListener(constraintLayout, smartspaceTarget, smartspaceAction, "BcSmartspaceCardCombination", cardPagerAdapter$$ExternalSyntheticLambda0, bcSmartspaceCardLoggingInfo);
            Drawable iconDrawable = BcSmartSpaceUtil.getIconDrawable(smartspaceAction.getIcon(), getContext());
            boolean z2 = true;
            if (iconDrawable == null) {
                imageView.setVisibility(8);
                z = false;
            } else {
                imageView.setImageDrawable(iconDrawable);
                imageView.setVisibility(0);
                z = true;
            }
            CharSequence title = smartspaceAction.getTitle();
            if (TextUtils.isEmpty(title)) {
                textView.setVisibility(8);
                z2 = z;
            } else {
                textView.setText(title);
                textView.setVisibility(0);
            }
            if (z2) {
                charSequence = smartspaceAction.getContentDescription();
            } else {
                charSequence = null;
            }
            constraintLayout.setContentDescription(charSequence);
            if (z2) {
                constraintLayout.setVisibility(0);
            } else {
                constraintLayout.setVisibility(8);
            }
            return z2;
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mFirstSubCard = (ConstraintLayout) findViewById(C1777R.C1779id.first_sub_card);
        this.mSecondSubCard = (ConstraintLayout) findViewById(C1777R.C1779id.second_sub_card);
    }

    public boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceAction smartspaceAction;
        boolean z;
        boolean z2;
        boolean z3;
        List actionChips = smartspaceTarget.getActionChips();
        if (actionChips == null || actionChips.size() < 1 || (smartspaceAction = (SmartspaceAction) actionChips.get(0)) == null) {
            return false;
        }
        ConstraintLayout constraintLayout = this.mFirstSubCard;
        if (constraintLayout == null || !fillSubCard(constraintLayout, smartspaceTarget, smartspaceAction, cardPagerAdapter$$ExternalSyntheticLambda0, bcSmartspaceCardLoggingInfo)) {
            z = false;
        } else {
            z = true;
        }
        if (actionChips.size() <= 1 || actionChips.get(1) == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            z3 = fillSubCard(this.mSecondSubCard, smartspaceTarget, (SmartspaceAction) actionChips.get(1), cardPagerAdapter$$ExternalSyntheticLambda0, bcSmartspaceCardLoggingInfo);
        } else {
            z3 = true;
        }
        if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (!z2 || !z3) {
                layoutParams.weight = 1.0f;
            } else {
                layoutParams.weight = 3.0f;
            }
            setLayoutParams(layoutParams);
        }
        if (!z || !z3) {
            return false;
        }
        return true;
    }
}
