package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.leanback.R$raw;
import com.android.internal.util.ArrayUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$styleable;
import com.android.systemui.statusbar.phone.UserAvatarView;

/* renamed from: com.android.systemui.qs.tiles.UserDetailItemView */
public class UserDetailItemView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActivatedStyle;
    public UserAvatarView mAvatar;
    public TextView mName;
    public int mRegularStyle;
    public View mRestrictedPadlock;

    public UserDetailItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public int getFontSizeDimen() {
        return C1777R.dimen.qs_tile_text_size;
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public UserDetailItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UserDetailItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void drawableStateChanged() {
        int i;
        super.drawableStateChanged();
        boolean contains = ArrayUtils.contains(getDrawableState(), 16843518);
        TextView textView = this.mName;
        if (contains) {
            i = this.mActivatedStyle;
        } else {
            i = this.mRegularStyle;
        }
        textView.setTextAppearance(i);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        R$raw.updateFontSize(this.mName, getFontSizeDimen());
    }

    public void onFinishInflate() {
        int i;
        this.mAvatar = (UserAvatarView) findViewById(C1777R.C1779id.user_picture);
        TextView textView = (TextView) findViewById(C1777R.C1779id.user_name);
        this.mName = textView;
        if (this.mRegularStyle == 0) {
            this.mRegularStyle = textView.getExplicitStyle();
        }
        if (this.mActivatedStyle == 0) {
            this.mActivatedStyle = this.mName.getExplicitStyle();
        }
        boolean contains = ArrayUtils.contains(getDrawableState(), 16843518);
        TextView textView2 = this.mName;
        if (contains) {
            i = this.mActivatedStyle;
        } else {
            i = this.mRegularStyle;
        }
        textView2.setTextAppearance(i);
        this.mRestrictedPadlock = findViewById(C1777R.C1779id.restricted_padlock);
    }

    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mName.setEnabled(z);
        this.mAvatar.setEnabled(z);
    }

    public UserDetailItemView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.UserDetailItemView, i, i2);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = obtainStyledAttributes.getIndex(i3);
            if (index == 1) {
                this.mRegularStyle = obtainStyledAttributes.getResourceId(index, 0);
            } else if (index == 0) {
                this.mActivatedStyle = obtainStyledAttributes.getResourceId(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }
}
