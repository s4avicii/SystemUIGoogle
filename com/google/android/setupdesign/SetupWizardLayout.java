package com.google.android.setupdesign;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupdesign.template.ProgressBarMixin;
import java.util.Objects;

public class SetupWizardLayout extends TemplateLayout {

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean isProgressBarShown = false;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            boolean z = false;
            this.isProgressBarShown = parcel.readInt() != 0 ? true : z;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isProgressBarShown ? 1 : 0);
        }
    }

    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = C1777R.C1779id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = C1777R.layout.sud_template;
        }
        return inflateTemplate(layoutInflater, 2132017796, i);
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            Log.w("SetupWizardLayout", "Ignoring restore instance state " + parcelable);
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        ((ProgressBarMixin) getMixin(ProgressBarMixin.class)).setShown(savedState.isProgressBarShown);
    }

    /* JADX WARNING: type inference failed for: r9v9, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SetupWizardLayout(android.content.Context r9, android.util.AttributeSet r10) {
        /*
            r8 = this;
            r8.<init>(r9, r10)
            boolean r9 = r8.isInEditMode()
            if (r9 == 0) goto L_0x000b
            goto L_0x019c
        L_0x000b:
            java.lang.Class<com.google.android.setupcompat.template.SystemNavBarMixin> r9 = com.google.android.setupcompat.template.SystemNavBarMixin.class
            com.google.android.setupcompat.template.SystemNavBarMixin r0 = new com.google.android.setupcompat.template.SystemNavBarMixin
            r1 = 0
            r0.<init>(r8, r1)
            r8.registerMixin(r9, r0)
            java.lang.Class<com.google.android.setupdesign.template.HeaderMixin> r9 = com.google.android.setupdesign.template.HeaderMixin.class
            com.google.android.setupdesign.template.HeaderMixin r0 = new com.google.android.setupdesign.template.HeaderMixin
            r2 = 2130969880(0x7f040518, float:1.7548454E38)
            r0.<init>(r8, r10, r2)
            r8.registerMixin(r9, r0)
            java.lang.Class<com.google.android.setupdesign.template.DescriptionMixin> r9 = com.google.android.setupdesign.template.DescriptionMixin.class
            com.google.android.setupdesign.template.DescriptionMixin r0 = new com.google.android.setupdesign.template.DescriptionMixin
            r0.<init>(r8, r10, r2)
            r8.registerMixin(r9, r0)
            java.lang.Class<com.google.android.setupdesign.template.ProgressBarMixin> r9 = com.google.android.setupdesign.template.ProgressBarMixin.class
            com.google.android.setupdesign.template.ProgressBarMixin r0 = new com.google.android.setupdesign.template.ProgressBarMixin
            r3 = 0
            r0.<init>(r8, r1, r3)
            r8.registerMixin(r9, r0)
            java.lang.Class<com.google.android.setupdesign.template.NavigationBarMixin> r9 = com.google.android.setupdesign.template.NavigationBarMixin.class
            com.google.android.setupdesign.template.NavigationBarMixin r0 = new com.google.android.setupdesign.template.NavigationBarMixin
            r0.<init>()
            r8.registerMixin(r9, r0)
            com.google.android.setupdesign.template.RequireScrollMixin r9 = new com.google.android.setupdesign.template.RequireScrollMixin
            r9.<init>()
            java.lang.Class<com.google.android.setupdesign.template.RequireScrollMixin> r0 = com.google.android.setupdesign.template.RequireScrollMixin.class
            r8.registerMixin(r0, r9)
            r9 = 2131428955(0x7f0b065b, float:1.847957E38)
            android.view.View r9 = r8.findManagedViewById(r9)
            boolean r0 = r9 instanceof android.widget.ScrollView
            if (r0 == 0) goto L_0x005a
            r1 = r9
            android.widget.ScrollView r1 = (android.widget.ScrollView) r1
        L_0x005a:
            if (r1 == 0) goto L_0x0079
            boolean r9 = r1 instanceof com.google.android.setupdesign.view.BottomScrollView
            if (r9 == 0) goto L_0x0063
            com.google.android.setupdesign.view.BottomScrollView r1 = (com.google.android.setupdesign.view.BottomScrollView) r1
            goto L_0x0079
        L_0x0063:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "Cannot set non-BottomScrollView. Found="
            r9.append(r0)
            r9.append(r1)
            java.lang.String r9 = r9.toString()
            java.lang.String r0 = "ScrollViewDelegate"
            android.util.Log.w(r0, r9)
        L_0x0079:
            android.content.Context r9 = r8.getContext()
            int[] r0 = com.google.android.setupdesign.R$styleable.SudSetupWizardLayout
            android.content.res.TypedArray r9 = r9.obtainStyledAttributes(r10, r0, r2, r3)
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r3)
            r0 = 1
            r1 = 2131428974(0x7f0b066e, float:1.8479608E38)
            if (r10 == 0) goto L_0x0097
            android.view.View r2 = r8.findManagedViewById(r1)
            if (r2 == 0) goto L_0x00b2
            r2.setBackgroundDrawable(r10)
            goto L_0x00b2
        L_0x0097:
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r0)
            if (r10 == 0) goto L_0x00b2
            boolean r2 = r10 instanceof android.graphics.drawable.BitmapDrawable
            if (r2 == 0) goto L_0x00a9
            r2 = r10
            android.graphics.drawable.BitmapDrawable r2 = (android.graphics.drawable.BitmapDrawable) r2
            android.graphics.Shader$TileMode r4 = android.graphics.Shader.TileMode.REPEAT
            r2.setTileModeXY(r4, r4)
        L_0x00a9:
            android.view.View r2 = r8.findManagedViewById(r1)
            if (r2 == 0) goto L_0x00b2
            r2.setBackgroundDrawable(r10)
        L_0x00b2:
            r10 = 3
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r10)
            r2 = 2
            if (r10 == 0) goto L_0x00d6
            android.view.View r3 = r8.findManagedViewById(r1)
            boolean r4 = r3 instanceof com.google.android.setupdesign.view.Illustration
            if (r4 == 0) goto L_0x013f
            com.google.android.setupdesign.view.Illustration r3 = (com.google.android.setupdesign.view.Illustration) r3
            java.util.Objects.requireNonNull(r3)
            android.graphics.drawable.Drawable r4 = r3.illustration
            if (r10 != r4) goto L_0x00cd
            goto L_0x013f
        L_0x00cd:
            r3.illustration = r10
            r3.invalidate()
            r3.requestLayout()
            goto L_0x013f
        L_0x00d6:
            r10 = 6
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r10)
            r4 = 5
            android.graphics.drawable.Drawable r4 = r9.getDrawable(r4)
            if (r10 == 0) goto L_0x013f
            if (r4 == 0) goto L_0x013f
            android.view.View r5 = r8.findManagedViewById(r1)
            boolean r6 = r5 instanceof com.google.android.setupdesign.view.Illustration
            if (r6 == 0) goto L_0x013f
            com.google.android.setupdesign.view.Illustration r5 = (com.google.android.setupdesign.view.Illustration) r5
            android.content.Context r6 = r8.getContext()
            android.content.res.Resources r6 = r6.getResources()
            r7 = 2131034225(0x7f050071, float:1.7678962E38)
            boolean r6 = r6.getBoolean(r7)
            if (r6 == 0) goto L_0x012c
            boolean r6 = r4 instanceof android.graphics.drawable.BitmapDrawable
            if (r6 == 0) goto L_0x0110
            r6 = r4
            android.graphics.drawable.BitmapDrawable r6 = (android.graphics.drawable.BitmapDrawable) r6
            android.graphics.Shader$TileMode r7 = android.graphics.Shader.TileMode.REPEAT
            r6.setTileModeX(r7)
            r7 = 48
            r6.setGravity(r7)
        L_0x0110:
            boolean r6 = r10 instanceof android.graphics.drawable.BitmapDrawable
            if (r6 == 0) goto L_0x011c
            r6 = r10
            android.graphics.drawable.BitmapDrawable r6 = (android.graphics.drawable.BitmapDrawable) r6
            r7 = 51
            r6.setGravity(r7)
        L_0x011c:
            android.graphics.drawable.LayerDrawable r6 = new android.graphics.drawable.LayerDrawable
            android.graphics.drawable.Drawable[] r7 = new android.graphics.drawable.Drawable[r2]
            r7[r3] = r4
            r7[r0] = r10
            r6.<init>(r7)
            r6.setAutoMirrored(r0)
            r10 = r6
            goto L_0x012f
        L_0x012c:
            r10.setAutoMirrored(r0)
        L_0x012f:
            java.util.Objects.requireNonNull(r5)
            android.graphics.drawable.Drawable r3 = r5.illustration
            if (r10 != r3) goto L_0x0137
            goto L_0x013f
        L_0x0137:
            r5.illustration = r10
            r5.invalidate()
            r5.requestLayout()
        L_0x013f:
            r10 = -1
            int r2 = r9.getDimensionPixelSize(r2, r10)
            if (r2 != r10) goto L_0x0151
            android.content.res.Resources r10 = r8.getResources()
            r2 = 2131167113(0x7f070789, float:1.794849E38)
            int r2 = r10.getDimensionPixelSize(r2)
        L_0x0151:
            android.view.View r10 = r8.findManagedViewById(r1)
            if (r10 == 0) goto L_0x0166
            int r3 = r10.getPaddingLeft()
            int r4 = r10.getPaddingRight()
            int r5 = r10.getPaddingBottom()
            r10.setPadding(r3, r2, r4, r5)
        L_0x0166:
            r10 = 4
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r10 = r9.getFloat(r10, r2)
            int r2 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0184
            android.util.TypedValue r10 = new android.util.TypedValue
            r10.<init>()
            android.content.res.Resources r2 = r8.getResources()
            r3 = 2131167165(0x7f0707bd, float:1.7948596E38)
            r2.getValue(r3, r10, r0)
            float r10 = r10.getFloat()
        L_0x0184:
            android.view.View r8 = r8.findManagedViewById(r1)
            boolean r0 = r8 instanceof com.google.android.setupdesign.view.Illustration
            if (r0 == 0) goto L_0x0199
            com.google.android.setupdesign.view.Illustration r8 = (com.google.android.setupdesign.view.Illustration) r8
            java.util.Objects.requireNonNull(r8)
            r8.aspectRatio = r10
            r8.invalidate()
            r8.requestLayout()
        L_0x0199:
            r9.recycle()
        L_0x019c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.SetupWizardLayout.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public final Parcelable onSaveInstanceState() {
        int i;
        boolean z;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        Objects.requireNonNull(progressBarMixin);
        TemplateLayout templateLayout = progressBarMixin.templateLayout;
        if (progressBarMixin.useBottomProgressBar) {
            i = C1777R.C1779id.sud_glif_progress_bar;
        } else {
            i = C1777R.C1779id.sud_layout_progress;
        }
        View findManagedViewById = templateLayout.findManagedViewById(i);
        if (findManagedViewById == null || findManagedViewById.getVisibility() != 0) {
            z = false;
        } else {
            z = true;
        }
        savedState.isProgressBarShown = z;
        return savedState;
    }
}
