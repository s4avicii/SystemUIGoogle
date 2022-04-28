package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import java.util.WeakHashMap;

public class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
    public Drawable mBackground;
    public CheckBox mCheckBox;
    public LinearLayout mContent;
    public boolean mForceShowIcon;
    public ImageView mGroupDivider;
    public boolean mHasListDivider;
    public ImageView mIconView;
    public LayoutInflater mInflater;
    public MenuItemImpl mItemData;
    public boolean mPreserveIconSpacing;
    public RadioButton mRadioButton;
    public TextView mShortcutView;
    public Drawable mSubMenuArrow;
    public ImageView mSubMenuArrowView;
    public int mTextAppearance;
    public Context mTextAppearanceContext;
    public TextView mTitleView;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MenuView, i);
        this.mBackground = obtainStyledAttributes.getDrawable(5);
        this.mTextAppearance = obtainStyledAttributes.getResourceId(1, -1);
        this.mPreserveIconSpacing = obtainStyledAttributes.getBoolean(7, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = obtainStyledAttributes.getDrawable(8);
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes((AttributeSet) null, new int[]{16843049}, C1777R.attr.dropDownListViewStyle, 0);
        this.mHasListDivider = obtainStyledAttributes2.hasValue(0);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes2.recycle();
    }

    public final void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null && imageView.getVisibility() == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams();
            rect.top = this.mGroupDivider.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top;
        }
    }

    public final LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        return this.mInflater;
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x01e1  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x022b  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0231  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0111  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void initialize(androidx.appcompat.view.menu.MenuItemImpl r13) {
        /*
            r12 = this;
            r12.mItemData = r13
            boolean r0 = r13.isVisible()
            r1 = 0
            r2 = 8
            if (r0 == 0) goto L_0x000d
            r0 = r1
            goto L_0x000e
        L_0x000d:
            r0 = r2
        L_0x000e:
            r12.setVisibility(r0)
            java.lang.CharSequence r0 = r13.mTitle
            if (r0 == 0) goto L_0x0028
            android.widget.TextView r3 = r12.mTitleView
            r3.setText(r0)
            android.widget.TextView r0 = r12.mTitleView
            int r0 = r0.getVisibility()
            if (r0 == 0) goto L_0x0035
            android.widget.TextView r0 = r12.mTitleView
            r0.setVisibility(r1)
            goto L_0x0035
        L_0x0028:
            android.widget.TextView r0 = r12.mTitleView
            int r0 = r0.getVisibility()
            if (r0 == r2) goto L_0x0035
            android.widget.TextView r0 = r12.mTitleView
            r0.setVisibility(r2)
        L_0x0035:
            boolean r0 = r13.isCheckable()
            r3 = 4
            r4 = 1
            if (r0 != 0) goto L_0x0047
            android.widget.RadioButton r5 = r12.mRadioButton
            if (r5 != 0) goto L_0x0047
            android.widget.CheckBox r5 = r12.mCheckBox
            if (r5 != 0) goto L_0x0047
            goto L_0x00ca
        L_0x0047:
            androidx.appcompat.view.menu.MenuItemImpl r5 = r12.mItemData
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mFlags
            r5 = r5 & r3
            if (r5 == 0) goto L_0x0053
            r5 = r4
            goto L_0x0054
        L_0x0053:
            r5 = r1
        L_0x0054:
            r6 = -1
            if (r5 == 0) goto L_0x007a
            android.widget.RadioButton r5 = r12.mRadioButton
            if (r5 != 0) goto L_0x0075
            android.view.LayoutInflater r5 = r12.getInflater()
            r7 = 2131623953(0x7f0e0011, float:1.8875072E38)
            android.view.View r5 = r5.inflate(r7, r12, r1)
            android.widget.RadioButton r5 = (android.widget.RadioButton) r5
            r12.mRadioButton = r5
            android.widget.LinearLayout r7 = r12.mContent
            if (r7 == 0) goto L_0x0072
            r7.addView(r5, r6)
            goto L_0x0075
        L_0x0072:
            r12.addView(r5, r6)
        L_0x0075:
            android.widget.RadioButton r5 = r12.mRadioButton
            android.widget.CheckBox r6 = r12.mCheckBox
            goto L_0x009c
        L_0x007a:
            android.widget.CheckBox r5 = r12.mCheckBox
            if (r5 != 0) goto L_0x0098
            android.view.LayoutInflater r5 = r12.getInflater()
            r7 = 2131623950(0x7f0e000e, float:1.8875066E38)
            android.view.View r5 = r5.inflate(r7, r12, r1)
            android.widget.CheckBox r5 = (android.widget.CheckBox) r5
            r12.mCheckBox = r5
            android.widget.LinearLayout r7 = r12.mContent
            if (r7 == 0) goto L_0x0095
            r7.addView(r5, r6)
            goto L_0x0098
        L_0x0095:
            r12.addView(r5, r6)
        L_0x0098:
            android.widget.CheckBox r5 = r12.mCheckBox
            android.widget.RadioButton r6 = r12.mRadioButton
        L_0x009c:
            if (r0 == 0) goto L_0x00bc
            androidx.appcompat.view.menu.MenuItemImpl r0 = r12.mItemData
            boolean r0 = r0.isChecked()
            r5.setChecked(r0)
            int r0 = r5.getVisibility()
            if (r0 == 0) goto L_0x00b0
            r5.setVisibility(r1)
        L_0x00b0:
            if (r6 == 0) goto L_0x00ca
            int r0 = r6.getVisibility()
            if (r0 == r2) goto L_0x00ca
            r6.setVisibility(r2)
            goto L_0x00ca
        L_0x00bc:
            android.widget.CheckBox r0 = r12.mCheckBox
            if (r0 == 0) goto L_0x00c3
            r0.setVisibility(r2)
        L_0x00c3:
            android.widget.RadioButton r0 = r12.mRadioButton
            if (r0 == 0) goto L_0x00ca
            r0.setVisibility(r2)
        L_0x00ca:
            androidx.appcompat.view.menu.MenuBuilder r0 = r13.mMenu
            boolean r0 = r0.isShortcutsVisible()
            if (r0 == 0) goto L_0x00e3
            androidx.appcompat.view.menu.MenuBuilder r0 = r13.mMenu
            boolean r0 = r0.isQwertyMode()
            if (r0 == 0) goto L_0x00dd
            char r0 = r13.mShortcutAlphabeticChar
            goto L_0x00df
        L_0x00dd:
            char r0 = r13.mShortcutNumericChar
        L_0x00df:
            if (r0 == 0) goto L_0x00e3
            r0 = r4
            goto L_0x00e4
        L_0x00e3:
            r0 = r1
        L_0x00e4:
            androidx.appcompat.view.menu.MenuBuilder r5 = r13.mMenu
            r5.isQwertyMode()
            if (r0 == 0) goto L_0x010e
            androidx.appcompat.view.menu.MenuItemImpl r0 = r12.mItemData
            java.util.Objects.requireNonNull(r0)
            androidx.appcompat.view.menu.MenuBuilder r5 = r0.mMenu
            boolean r5 = r5.isShortcutsVisible()
            if (r5 == 0) goto L_0x0109
            androidx.appcompat.view.menu.MenuBuilder r5 = r0.mMenu
            boolean r5 = r5.isQwertyMode()
            if (r5 == 0) goto L_0x0103
            char r0 = r0.mShortcutAlphabeticChar
            goto L_0x0105
        L_0x0103:
            char r0 = r0.mShortcutNumericChar
        L_0x0105:
            if (r0 == 0) goto L_0x0109
            r0 = r4
            goto L_0x010a
        L_0x0109:
            r0 = r1
        L_0x010a:
            if (r0 == 0) goto L_0x010e
            r0 = r1
            goto L_0x010f
        L_0x010e:
            r0 = r2
        L_0x010f:
            if (r0 != 0) goto L_0x01d9
            android.widget.TextView r5 = r12.mShortcutView
            androidx.appcompat.view.menu.MenuItemImpl r6 = r12.mItemData
            java.util.Objects.requireNonNull(r6)
            androidx.appcompat.view.menu.MenuBuilder r7 = r6.mMenu
            boolean r7 = r7.isQwertyMode()
            if (r7 == 0) goto L_0x0123
            char r7 = r6.mShortcutAlphabeticChar
            goto L_0x0125
        L_0x0123:
            char r7 = r6.mShortcutNumericChar
        L_0x0125:
            if (r7 != 0) goto L_0x012b
            java.lang.String r3 = ""
            goto L_0x01d6
        L_0x012b:
            androidx.appcompat.view.menu.MenuBuilder r8 = r6.mMenu
            java.util.Objects.requireNonNull(r8)
            android.content.Context r8 = r8.mContext
            android.content.res.Resources r8 = r8.getResources()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            androidx.appcompat.view.menu.MenuBuilder r10 = r6.mMenu
            java.util.Objects.requireNonNull(r10)
            android.content.Context r10 = r10.mContext
            android.view.ViewConfiguration r10 = android.view.ViewConfiguration.get(r10)
            boolean r10 = r10.hasPermanentMenuKey()
            if (r10 == 0) goto L_0x0156
            r10 = 2131951635(0x7f130013, float:1.953969E38)
            java.lang.String r10 = r8.getString(r10)
            r9.append(r10)
        L_0x0156:
            androidx.appcompat.view.menu.MenuBuilder r10 = r6.mMenu
            boolean r10 = r10.isQwertyMode()
            if (r10 == 0) goto L_0x0161
            int r6 = r6.mShortcutAlphabeticModifiers
            goto L_0x0163
        L_0x0161:
            int r6 = r6.mShortcutNumericModifiers
        L_0x0163:
            r10 = 65536(0x10000, float:9.18355E-41)
            r11 = 2131951631(0x7f13000f, float:1.9539682E38)
            java.lang.String r11 = r8.getString(r11)
            androidx.appcompat.view.menu.MenuItemImpl.appendModifier(r9, r6, r10, r11)
            r10 = 4096(0x1000, float:5.74E-42)
            r11 = 2131951627(0x7f13000b, float:1.9539674E38)
            java.lang.String r11 = r8.getString(r11)
            androidx.appcompat.view.menu.MenuItemImpl.appendModifier(r9, r6, r10, r11)
            r10 = 2
            r11 = 2131951626(0x7f13000a, float:1.9539672E38)
            java.lang.String r11 = r8.getString(r11)
            androidx.appcompat.view.menu.MenuItemImpl.appendModifier(r9, r6, r10, r11)
            r10 = 2131951632(0x7f130010, float:1.9539684E38)
            java.lang.String r10 = r8.getString(r10)
            androidx.appcompat.view.menu.MenuItemImpl.appendModifier(r9, r6, r4, r10)
            r4 = 2131951634(0x7f130012, float:1.9539688E38)
            java.lang.String r4 = r8.getString(r4)
            androidx.appcompat.view.menu.MenuItemImpl.appendModifier(r9, r6, r3, r4)
            r3 = 2131951630(0x7f13000e, float:1.953968E38)
            java.lang.String r3 = r8.getString(r3)
            androidx.appcompat.view.menu.MenuItemImpl.appendModifier(r9, r6, r2, r3)
            if (r7 == r2) goto L_0x01c8
            r3 = 10
            if (r7 == r3) goto L_0x01bd
            r3 = 32
            if (r7 == r3) goto L_0x01b2
            r9.append(r7)
            goto L_0x01d2
        L_0x01b2:
            r3 = 2131951633(0x7f130011, float:1.9539686E38)
            java.lang.String r3 = r8.getString(r3)
            r9.append(r3)
            goto L_0x01d2
        L_0x01bd:
            r3 = 2131951629(0x7f13000d, float:1.9539678E38)
            java.lang.String r3 = r8.getString(r3)
            r9.append(r3)
            goto L_0x01d2
        L_0x01c8:
            r3 = 2131951628(0x7f13000c, float:1.9539676E38)
            java.lang.String r3 = r8.getString(r3)
            r9.append(r3)
        L_0x01d2:
            java.lang.String r3 = r9.toString()
        L_0x01d6:
            r5.setText(r3)
        L_0x01d9:
            android.widget.TextView r3 = r12.mShortcutView
            int r3 = r3.getVisibility()
            if (r3 == r0) goto L_0x01e6
            android.widget.TextView r3 = r12.mShortcutView
            r3.setVisibility(r0)
        L_0x01e6:
            android.graphics.drawable.Drawable r0 = r13.getIcon()
            androidx.appcompat.view.menu.MenuItemImpl r3 = r12.mItemData
            java.util.Objects.requireNonNull(r3)
            androidx.appcompat.view.menu.MenuBuilder r3 = r3.mMenu
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r12.mForceShowIcon
            if (r3 != 0) goto L_0x01fd
            boolean r4 = r12.mPreserveIconSpacing
            if (r4 != 0) goto L_0x01fd
            goto L_0x0247
        L_0x01fd:
            android.widget.ImageView r4 = r12.mIconView
            if (r4 != 0) goto L_0x0208
            if (r0 != 0) goto L_0x0208
            boolean r5 = r12.mPreserveIconSpacing
            if (r5 != 0) goto L_0x0208
            goto L_0x0247
        L_0x0208:
            if (r4 != 0) goto L_0x0224
            android.view.LayoutInflater r4 = r12.getInflater()
            r5 = 2131623951(0x7f0e000f, float:1.8875068E38)
            android.view.View r4 = r4.inflate(r5, r12, r1)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            r12.mIconView = r4
            android.widget.LinearLayout r5 = r12.mContent
            if (r5 == 0) goto L_0x0221
            r5.addView(r4, r1)
            goto L_0x0224
        L_0x0221:
            r12.addView(r4, r1)
        L_0x0224:
            if (r0 != 0) goto L_0x0231
            boolean r4 = r12.mPreserveIconSpacing
            if (r4 == 0) goto L_0x022b
            goto L_0x0231
        L_0x022b:
            android.widget.ImageView r0 = r12.mIconView
            r0.setVisibility(r2)
            goto L_0x0247
        L_0x0231:
            android.widget.ImageView r4 = r12.mIconView
            if (r3 == 0) goto L_0x0236
            goto L_0x0237
        L_0x0236:
            r0 = 0
        L_0x0237:
            r4.setImageDrawable(r0)
            android.widget.ImageView r0 = r12.mIconView
            int r0 = r0.getVisibility()
            if (r0 == 0) goto L_0x0247
            android.widget.ImageView r0 = r12.mIconView
            r0.setVisibility(r1)
        L_0x0247:
            boolean r0 = r13.isEnabled()
            r12.setEnabled(r0)
            boolean r0 = r13.hasSubMenu()
            android.widget.ImageView r3 = r12.mSubMenuArrowView
            if (r3 == 0) goto L_0x025d
            if (r0 == 0) goto L_0x0259
            goto L_0x025a
        L_0x0259:
            r1 = r2
        L_0x025a:
            r3.setVisibility(r1)
        L_0x025d:
            java.lang.CharSequence r13 = r13.mContentDescription
            r12.setContentDescription(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.ListMenuItemView.initialize(androidx.appcompat.view.menu.MenuItemImpl):void");
    }

    public final void onMeasure(int i, int i2) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            int i3 = layoutParams.height;
            if (i3 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = i3;
            }
        }
        super.onMeasure(i, i2);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        Drawable drawable = this.mBackground;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, drawable);
        TextView textView = (TextView) findViewById(C1777R.C1779id.title);
        this.mTitleView = textView;
        int i = this.mTextAppearance;
        if (i != -1) {
            textView.setTextAppearance(this.mTextAppearanceContext, i);
        }
        this.mShortcutView = (TextView) findViewById(C1777R.C1779id.shortcut);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.submenuarrow);
        this.mSubMenuArrowView = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.mSubMenuArrow);
        }
        this.mGroupDivider = (ImageView) findViewById(C1777R.C1779id.group_divider);
        this.mContent = (LinearLayout) findViewById(C1777R.C1779id.content);
    }

    public final MenuItemImpl getItemData() {
        return this.mItemData;
    }
}
