package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.p012wm.shell.C1777R;
import java.util.List;

public class BcSmartspaceCardCombinationAtStore extends BcSmartspaceCardCombination {
    public BcSmartspaceCardCombinationAtStore(Context context) {
        super(context);
    }

    public BcSmartspaceCardCombinationAtStore(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceAction smartspaceAction;
        boolean z;
        boolean z2;
        List actionChips = smartspaceTarget.getActionChips();
        if (actionChips == null || actionChips.isEmpty() || (smartspaceAction = (SmartspaceAction) actionChips.get(0)) == null) {
            return false;
        }
        ConstraintLayout constraintLayout = this.mFirstSubCard;
        if (!(constraintLayout instanceof BcSmartspaceCardShoppingList) || !((BcSmartspaceCardShoppingList) constraintLayout).setSmartspaceActions(smartspaceTarget, cardPagerAdapter$$ExternalSyntheticLambda0, bcSmartspaceCardLoggingInfo)) {
            z = false;
        } else {
            z = true;
        }
        ConstraintLayout constraintLayout2 = this.mSecondSubCard;
        if (constraintLayout2 == null || !fillSubCard(constraintLayout2, smartspaceTarget, smartspaceAction, cardPagerAdapter$$ExternalSyntheticLambda0, bcSmartspaceCardLoggingInfo)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z) {
            this.mFirstSubCard.setBackgroundResource(C1777R.C1778drawable.bg_smartspace_combination_sub_card);
        }
        if (!z || !z2) {
            return false;
        }
        return true;
    }
}
