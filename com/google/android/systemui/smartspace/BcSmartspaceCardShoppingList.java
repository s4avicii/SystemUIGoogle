package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Locale;

public class BcSmartspaceCardShoppingList extends BcSmartspaceCardSecondary {
    public static final int[] LIST_ITEM_TEXT_VIEW_IDS = {C1777R.C1779id.list_item_1, C1777R.C1779id.list_item_2, C1777R.C1779id.list_item_3};
    public ImageView mCardPromptIconView;
    public TextView mCardPromptView;
    public TextView mEmptyListMessageView;
    public ImageView mListIconView;

    public BcSmartspaceCardShoppingList(Context context) {
        super(context);
    }

    public BcSmartspaceCardShoppingList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mCardPromptView = (TextView) findViewById(C1777R.C1779id.card_prompt);
        this.mEmptyListMessageView = (TextView) findViewById(C1777R.C1779id.empty_list_message);
        this.mCardPromptIconView = (ImageView) findViewById(C1777R.C1779id.card_prompt_icon);
        this.mListIconView = (ImageView) findViewById(C1777R.C1779id.list_icon);
    }

    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        Bundle bundle;
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bitmap bitmap = null;
        if (baseAction == null) {
            bundle = null;
        } else {
            bundle = baseAction.getExtras();
        }
        if (bundle != null) {
            this.mEmptyListMessageView.setVisibility(8);
            this.mListIconView.setVisibility(8);
            this.mCardPromptIconView.setVisibility(8);
            this.mCardPromptView.setVisibility(8);
            for (int i = 0; i < 3; i++) {
                TextView textView = (TextView) findViewById(LIST_ITEM_TEXT_VIEW_IDS[i]);
                if (textView != null) {
                    textView.setVisibility(8);
                }
            }
            if (bundle.containsKey("appIcon")) {
                bitmap = (Bitmap) bundle.get("appIcon");
            } else if (bundle.containsKey("imageBitmap")) {
                bitmap = (Bitmap) bundle.get("imageBitmap");
            }
            this.mCardPromptIconView.setImageBitmap(bitmap);
            this.mListIconView.setImageBitmap(bitmap);
            if (bundle.containsKey("cardPrompt")) {
                String string = bundle.getString("cardPrompt");
                TextView textView2 = this.mCardPromptView;
                if (textView2 == null) {
                    Log.w("BcSmartspaceCardShoppingList", "No card prompt view to update");
                } else {
                    textView2.setText(string);
                }
                this.mCardPromptView.setVisibility(0);
                if (bitmap != null) {
                    this.mCardPromptIconView.setVisibility(0);
                }
                return true;
            } else if (bundle.containsKey("emptyListString")) {
                String string2 = bundle.getString("emptyListString");
                TextView textView3 = this.mEmptyListMessageView;
                if (textView3 == null) {
                    Log.w("BcSmartspaceCardShoppingList", "No empty list message view to update");
                } else {
                    textView3.setText(string2);
                }
                this.mEmptyListMessageView.setVisibility(0);
                this.mListIconView.setVisibility(0);
                return true;
            } else if (bundle.containsKey("listItems")) {
                String[] stringArray = bundle.getStringArray("listItems");
                if (stringArray.length == 0) {
                    return false;
                }
                this.mListIconView.setVisibility(0);
                bundle.getInt("listSize", -1);
                int i2 = 0;
                while (true) {
                    if (i2 >= 3) {
                        break;
                    }
                    TextView textView4 = (TextView) findViewById(LIST_ITEM_TEXT_VIEW_IDS[i2]);
                    if (textView4 == null) {
                        Log.w("BcSmartspaceCardShoppingList", String.format(Locale.US, "Missing list item view to update at row: %d", new Object[]{Integer.valueOf(i2 + 1)}));
                        break;
                    }
                    if (i2 < stringArray.length) {
                        textView4.setVisibility(0);
                        textView4.setText(stringArray[i2]);
                    } else {
                        textView4.setVisibility(8);
                        textView4.setText("");
                    }
                    i2++;
                }
                return true;
            }
        }
        return false;
    }
}
