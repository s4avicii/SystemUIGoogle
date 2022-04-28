package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.volume.VolumeDialogComponent$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class NearestTouchFrame extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mAttachedChildren;
    public final NearestTouchFrame$$ExternalSyntheticLambda0 mChildRegionComparator;
    public final ArrayList mClickableChildren;
    public final boolean mIsActive;
    public boolean mIsVertical;
    public final int[] mOffset;
    public final int[] mTmpInt;
    public final HashMap mTouchableRegions;
    public View mTouchingChild;

    public NearestTouchFrame(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, context.getResources().getConfiguration());
    }

    public NearestTouchFrame(Context context, AttributeSet attributeSet, Configuration configuration) {
        super(context, attributeSet);
        this.mClickableChildren = new ArrayList();
        this.mAttachedChildren = new ArrayList();
        this.mTmpInt = new int[2];
        this.mOffset = new int[2];
        this.mTouchableRegions = new HashMap();
        this.mChildRegionComparator = new NearestTouchFrame$$ExternalSyntheticLambda0(this);
        this.mIsActive = configuration.smallestScreenWidthDp < 600;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{C1777R.attr.isVertical});
        this.mIsVertical = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsActive) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0) {
                this.mTouchingChild = (View) this.mClickableChildren.stream().filter(NearestTouchFrame$$ExternalSyntheticLambda2.INSTANCE).filter(new NearestTouchFrame$$ExternalSyntheticLambda1(this, x, y)).findFirst().orElse((Object) null);
            }
            View view = this.mTouchingChild;
            if (view != null) {
                motionEvent.offsetLocation((float) ((view.getWidth() / 2) - x), (float) ((this.mTouchingChild.getHeight() / 2) - y));
                if (this.mTouchingChild.getVisibility() != 0 || !this.mTouchingChild.dispatchTouchEvent(motionEvent)) {
                    return false;
                }
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void addClickableChildren(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.isClickable()) {
                this.mClickableChildren.add(childAt);
            } else if (childAt instanceof ViewGroup) {
                addClickableChildren((ViewGroup) childAt);
            }
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        super.onLayout(z, i, i2, i3, i4);
        this.mClickableChildren.clear();
        this.mAttachedChildren.clear();
        this.mTouchableRegions.clear();
        addClickableChildren(this);
        getLocationInWindow(this.mOffset);
        if (getWidth() != 0 && getHeight() != 0) {
            this.mClickableChildren.sort(this.mChildRegionComparator);
            Stream filter = this.mClickableChildren.stream().filter(SavedNetworkTracker$$ExternalSyntheticLambda3.INSTANCE$1);
            ArrayList arrayList = this.mAttachedChildren;
            Objects.requireNonNull(arrayList);
            filter.forEachOrdered(new VolumeDialogComponent$$ExternalSyntheticLambda0(arrayList, 1));
            for (int i7 = 0; i7 < this.mAttachedChildren.size(); i7++) {
                View view = (View) this.mAttachedChildren.get(i7);
                if (view.isAttachedToWindow()) {
                    view.getLocationInWindow(this.mTmpInt);
                    int[] iArr = this.mTmpInt;
                    int i8 = iArr[0];
                    int[] iArr2 = this.mOffset;
                    int i9 = i8 - iArr2[0];
                    int i10 = iArr[1] - iArr2[1];
                    Rect rect = new Rect(i9, i10, view.getWidth() + i9, view.getHeight() + i10);
                    if (i7 == 0) {
                        if (this.mIsVertical) {
                            rect.top = 0;
                        } else {
                            rect.left = 0;
                        }
                        this.mTouchableRegions.put(view, rect);
                    } else {
                        Rect rect2 = (Rect) this.mTouchableRegions.get((View) this.mAttachedChildren.get(i7 - 1));
                        if (this.mIsVertical) {
                            int i11 = rect.top;
                            int i12 = rect2.bottom;
                            int i13 = i11 - i12;
                            int i14 = i13 / 2;
                            rect.top = i11 - i14;
                            if (i13 % 2 == 0) {
                                i6 = 1;
                            } else {
                                i6 = 0;
                            }
                            rect2.bottom = (i14 - i6) + i12;
                        } else {
                            int i15 = rect.left;
                            int i16 = rect2.right;
                            int i17 = i15 - i16;
                            int i18 = i17 / 2;
                            rect.left = i15 - i18;
                            if (i17 % 2 == 0) {
                                i5 = 1;
                            } else {
                                i5 = 0;
                            }
                            rect2.right = (i18 - i5) + i16;
                        }
                        if (i7 == this.mClickableChildren.size() - 1) {
                            if (this.mIsVertical) {
                                rect.bottom = getHeight();
                            } else {
                                rect.right = getWidth();
                            }
                        }
                        this.mTouchableRegions.put(view, rect);
                    }
                }
            }
        }
    }

    public void setIsVertical(boolean z) {
        this.mIsVertical = z;
    }
}
