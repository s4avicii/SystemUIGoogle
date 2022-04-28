package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import java.util.Objects;
import java.util.WeakHashMap;

public final class AppCompatBackgroundHelper {
    public int mBackgroundResId = -1;
    public TintInfo mBackgroundTint;
    public final AppCompatDrawableManager mDrawableManager;
    public TintInfo mInternalBackgroundTint;
    public TintInfo mTmpInfo;
    public final View mView;

    public final void onSetBackgroundDrawable() {
        this.mBackgroundResId = -1;
        setInternalBackgroundTint((ColorStateList) null);
        applySupportBackgroundTint();
    }

    public final void applySupportBackgroundTint() {
        boolean z;
        Drawable background = this.mView.getBackground();
        if (background != null) {
            boolean z2 = true;
            if (this.mInternalBackgroundTint != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                if (this.mTmpInfo == null) {
                    this.mTmpInfo = new TintInfo();
                }
                TintInfo tintInfo = this.mTmpInfo;
                Objects.requireNonNull(tintInfo);
                tintInfo.mTintList = null;
                tintInfo.mHasTintList = false;
                tintInfo.mTintMode = null;
                tintInfo.mHasTintMode = false;
                View view = this.mView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ColorStateList backgroundTintList = ViewCompat.Api21Impl.getBackgroundTintList(view);
                if (backgroundTintList != null) {
                    tintInfo.mHasTintList = true;
                    tintInfo.mTintList = backgroundTintList;
                }
                PorterDuff.Mode backgroundTintMode = ViewCompat.Api21Impl.getBackgroundTintMode(this.mView);
                if (backgroundTintMode != null) {
                    tintInfo.mHasTintMode = true;
                    tintInfo.mTintMode = backgroundTintMode;
                }
                if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                    AppCompatDrawableManager.tintDrawable(background, tintInfo, this.mView.getDrawableState());
                } else {
                    z2 = false;
                }
                if (z2) {
                    return;
                }
            }
            TintInfo tintInfo2 = this.mBackgroundTint;
            if (tintInfo2 != null) {
                AppCompatDrawableManager.tintDrawable(background, tintInfo2, this.mView.getDrawableState());
                return;
            }
            TintInfo tintInfo3 = this.mInternalBackgroundTint;
            if (tintInfo3 != null) {
                AppCompatDrawableManager.tintDrawable(background, tintInfo3, this.mView.getDrawableState());
            }
        }
    }

    public final ColorStateList getSupportBackgroundTintList() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public final PorterDuff.Mode getSupportBackgroundTintMode() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0072, code lost:
        r0.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0075, code lost:
        throw r8;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void loadFromAttributes(android.util.AttributeSet r9, int r10) {
        /*
            r8 = this;
            android.view.View r0 = r8.mView
            android.content.Context r0 = r0.getContext()
            int[] r3 = androidx.appcompat.R$styleable.ViewBackgroundHelper
            androidx.appcompat.widget.TintTypedArray r0 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r0, r9, r3, r10)
            android.view.View r1 = r8.mView
            android.content.Context r2 = r1.getContext()
            android.content.res.TypedArray r5 = r0.mWrapped
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            r7 = 0
            r4 = r9
            r6 = r10
            androidx.core.view.ViewCompat.Api29Impl.saveAttributeDataForStyleable(r1, r2, r3, r4, r5, r6, r7)
            r9 = 0
            boolean r10 = r0.hasValue(r9)     // Catch:{ all -> 0x0071 }
            r1 = -1
            if (r10 == 0) goto L_0x0048
            int r9 = r0.getResourceId(r9, r1)     // Catch:{ all -> 0x0071 }
            r8.mBackgroundResId = r9     // Catch:{ all -> 0x0071 }
            androidx.appcompat.widget.AppCompatDrawableManager r9 = r8.mDrawableManager     // Catch:{ all -> 0x0071 }
            android.view.View r10 = r8.mView     // Catch:{ all -> 0x0071 }
            android.content.Context r10 = r10.getContext()     // Catch:{ all -> 0x0071 }
            int r2 = r8.mBackgroundResId     // Catch:{ all -> 0x0071 }
            java.util.Objects.requireNonNull(r9)     // Catch:{ all -> 0x0071 }
            monitor-enter(r9)     // Catch:{ all -> 0x0071 }
            androidx.appcompat.widget.ResourceManagerInternal r3 = r9.mResourceManager     // Catch:{ all -> 0x0045 }
            android.content.res.ColorStateList r10 = r3.getTintList(r10, r2)     // Catch:{ all -> 0x0045 }
            monitor-exit(r9)     // Catch:{ all -> 0x0071 }
            if (r10 == 0) goto L_0x0048
            r8.setInternalBackgroundTint(r10)     // Catch:{ all -> 0x0071 }
            goto L_0x0048
        L_0x0045:
            r8 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0071 }
            throw r8     // Catch:{ all -> 0x0071 }
        L_0x0048:
            r9 = 1
            boolean r10 = r0.hasValue(r9)     // Catch:{ all -> 0x0071 }
            if (r10 == 0) goto L_0x0058
            android.view.View r10 = r8.mView     // Catch:{ all -> 0x0071 }
            android.content.res.ColorStateList r9 = r0.getColorStateList(r9)     // Catch:{ all -> 0x0071 }
            androidx.core.view.ViewCompat.Api21Impl.setBackgroundTintList(r10, r9)     // Catch:{ all -> 0x0071 }
        L_0x0058:
            r9 = 2
            boolean r10 = r0.hasValue(r9)     // Catch:{ all -> 0x0071 }
            if (r10 == 0) goto L_0x006d
            android.view.View r8 = r8.mView     // Catch:{ all -> 0x0071 }
            int r9 = r0.getInt(r9, r1)     // Catch:{ all -> 0x0071 }
            r10 = 0
            android.graphics.PorterDuff$Mode r9 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r9, r10)     // Catch:{ all -> 0x0071 }
            androidx.core.view.ViewCompat.Api21Impl.setBackgroundTintMode(r8, r9)     // Catch:{ all -> 0x0071 }
        L_0x006d:
            r0.recycle()
            return
        L_0x0071:
            r8 = move-exception
            r0.recycle()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatBackgroundHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    public final void onSetBackgroundResource(int i) {
        ColorStateList colorStateList;
        this.mBackgroundResId = i;
        AppCompatDrawableManager appCompatDrawableManager = this.mDrawableManager;
        if (appCompatDrawableManager != null) {
            Context context = this.mView.getContext();
            synchronized (appCompatDrawableManager) {
                colorStateList = appCompatDrawableManager.mResourceManager.getTintList(context, i);
            }
        } else {
            colorStateList = null;
        }
        setInternalBackgroundTint(colorStateList);
        applySupportBackgroundTint();
    }

    public final void setInternalBackgroundTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            TintInfo tintInfo = this.mInternalBackgroundTint;
            tintInfo.mTintList = colorStateList;
            tintInfo.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        applySupportBackgroundTint();
    }

    public final void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        applySupportBackgroundTint();
    }

    public final void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = true;
        applySupportBackgroundTint();
    }

    public AppCompatBackgroundHelper(View view) {
        this.mView = view;
        this.mDrawableManager = AppCompatDrawableManager.get();
    }
}
