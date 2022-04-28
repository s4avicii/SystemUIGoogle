package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import androidx.core.content.ContextCompat;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.widget.SliceView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class SliceActionView extends FrameLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final int[] CHECKED_STATE_SET = {16842912};
    public View mActionView;
    public EventInfo mEventInfo;
    public int mIconSize;
    public int mImageSize;
    public SliceActionLoadingListener mLoadingListener;
    public SliceView.OnSliceActionListener mObserver;
    public ProgressBar mProgressView;
    public SliceActionImpl mSliceAction;
    public int mTextActionPadding = 0;

    public static class ImageToggle extends ImageView implements Checkable, View.OnClickListener {
        public boolean mIsChecked;
        public View.OnClickListener mListener;

        public final int[] onCreateDrawableState(int i) {
            int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
            if (this.mIsChecked) {
                View.mergeDrawableStates(onCreateDrawableState, SliceActionView.CHECKED_STATE_SET);
            }
            return onCreateDrawableState;
        }

        public final void setChecked(boolean z) {
            if (this.mIsChecked != z) {
                this.mIsChecked = z;
                refreshDrawableState();
                View.OnClickListener onClickListener = this.mListener;
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                }
            }
        }

        public final void toggle() {
            setChecked(!this.mIsChecked);
        }

        public ImageToggle(Context context) {
            super(context);
            super.setOnClickListener(this);
        }

        public final void onClick(View view) {
            toggle();
        }

        public final void setOnClickListener(View.OnClickListener onClickListener) {
            this.mListener = onClickListener;
        }

        public final boolean isChecked() {
            return this.mIsChecked;
        }
    }

    public interface SliceActionLoadingListener {
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mSliceAction != null && this.mActionView != null) {
            sendActionInternal();
        }
    }

    public final void onClick(View view) {
        if (this.mSliceAction != null && this.mActionView != null) {
            sendActionInternal();
        }
    }

    public final void sendActionInternal() {
        int i;
        int i2;
        SliceActionImpl sliceActionImpl = this.mSliceAction;
        if (sliceActionImpl != null && sliceActionImpl.mActionItem != null) {
            Intent intent = null;
            try {
                if (sliceActionImpl.isToggle()) {
                    boolean isChecked = ((Checkable) this.mActionView).isChecked();
                    intent = new Intent().addFlags(268435456).putExtra("android.app.slice.extra.TOGGLE_STATE", isChecked);
                    EventInfo eventInfo = this.mEventInfo;
                    if (eventInfo != null) {
                        if (isChecked) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        eventInfo.state = i2;
                    }
                }
                SliceActionImpl sliceActionImpl2 = this.mSliceAction;
                Objects.requireNonNull(sliceActionImpl2);
                if (sliceActionImpl2.mActionItem.fireActionInternal(getContext(), intent)) {
                    setLoading();
                    SliceActionLoadingListener sliceActionLoadingListener = this.mLoadingListener;
                    if (sliceActionLoadingListener != null) {
                        EventInfo eventInfo2 = this.mEventInfo;
                        if (eventInfo2 != null) {
                            i = eventInfo2.rowIndex;
                        } else {
                            i = -1;
                        }
                        SliceActionImpl sliceActionImpl3 = this.mSliceAction;
                        Objects.requireNonNull(sliceActionImpl3);
                        ((SliceAdapter) sliceActionLoadingListener).onSliceActionLoading(sliceActionImpl3.mSliceItem, i);
                    }
                }
                SliceView.OnSliceActionListener onSliceActionListener = this.mObserver;
                if (onSliceActionListener != null && this.mEventInfo != null) {
                    Objects.requireNonNull(this.mSliceAction);
                    onSliceActionListener.onSliceAction();
                }
            } catch (PendingIntent.CanceledException e) {
                View view = this.mActionView;
                if (view instanceof Checkable) {
                    view.setSelected(!((Checkable) view).isChecked());
                }
                Log.e("SliceActionView", "PendingIntent for slice cannot be sent", e);
            }
        }
    }

    public final void setAction(SliceActionImpl sliceActionImpl, EventInfo eventInfo, SliceView.OnSliceActionListener onSliceActionListener, int i, SliceActionLoadingListener sliceActionLoadingListener) {
        int i2;
        View view = this.mActionView;
        if (view != null) {
            removeView(view);
            this.mActionView = null;
        }
        ProgressBar progressBar = this.mProgressView;
        if (progressBar != null) {
            removeView(progressBar);
            this.mProgressView = null;
        }
        this.mSliceAction = sliceActionImpl;
        this.mEventInfo = eventInfo;
        this.mObserver = onSliceActionListener;
        this.mActionView = null;
        this.mLoadingListener = sliceActionLoadingListener;
        int i3 = 0;
        if (sliceActionImpl.isDefaultToggle()) {
            Switch switchR = (Switch) LayoutInflater.from(getContext()).inflate(C1777R.layout.abc_slice_switch, this, false);
            switchR.setChecked(sliceActionImpl.mIsChecked);
            switchR.setOnCheckedChangeListener(this);
            switchR.setMinimumHeight(this.mImageSize);
            switchR.setMinimumWidth(this.mImageSize);
            addView(switchR);
            if (i != -1) {
                int colorAttr = SliceViewUtil.getColorAttr(getContext(), 16842800);
                int[] iArr = CHECKED_STATE_SET;
                int[] iArr2 = FrameLayout.EMPTY_STATE_SET;
                ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{i, colorAttr});
                Drawable trackDrawable = switchR.getTrackDrawable();
                trackDrawable.setTintList(colorStateList);
                switchR.setTrackDrawable(trackDrawable);
                int colorAttr2 = SliceViewUtil.getColorAttr(getContext(), C1777R.attr.colorSwitchThumbNormal);
                if (colorAttr2 == 0) {
                    Context context = getContext();
                    Object obj = ContextCompat.sLock;
                    colorAttr2 = context.getColor(C1777R.color.switch_thumb_normal_material_light);
                }
                ColorStateList colorStateList2 = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{i, colorAttr2});
                Drawable thumbDrawable = switchR.getThumbDrawable();
                thumbDrawable.setTintList(colorStateList2);
                switchR.setThumbDrawable(thumbDrawable);
            }
            this.mActionView = switchR;
        } else if (sliceActionImpl.mImageMode == 6) {
            Button button = new Button(getContext());
            this.mActionView = button;
            button.setText(sliceActionImpl.mTitle);
            addView(this.mActionView);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mActionView.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            this.mActionView.setLayoutParams(layoutParams);
            int i4 = this.mTextActionPadding;
            this.mActionView.setPadding(i4, i4, i4, i4);
            this.mActionView.setOnClickListener(this);
        } else if (sliceActionImpl.mIcon != null) {
            if (sliceActionImpl.isToggle()) {
                ImageToggle imageToggle = new ImageToggle(getContext());
                imageToggle.setChecked(sliceActionImpl.mIsChecked);
                this.mActionView = imageToggle;
            } else {
                this.mActionView = new ImageView(getContext());
            }
            addView(this.mActionView);
            SliceActionImpl sliceActionImpl2 = this.mSliceAction;
            Objects.requireNonNull(sliceActionImpl2);
            Drawable loadDrawable = sliceActionImpl2.mIcon.loadDrawable(getContext());
            ((ImageView) this.mActionView).setImageDrawable(loadDrawable);
            if (i != -1) {
                SliceActionImpl sliceActionImpl3 = this.mSliceAction;
                Objects.requireNonNull(sliceActionImpl3);
                if (sliceActionImpl3.mImageMode == 0 && loadDrawable != null) {
                    loadDrawable.setTint(i);
                }
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mActionView.getLayoutParams();
            int i5 = this.mImageSize;
            layoutParams2.width = i5;
            layoutParams2.height = i5;
            this.mActionView.setLayoutParams(layoutParams2);
            if (sliceActionImpl.mImageMode == 0) {
                int i6 = this.mImageSize;
                if (i6 == -1) {
                    i2 = this.mIconSize;
                } else {
                    i2 = i6 - this.mIconSize;
                }
                i3 = i2 / 2;
            }
            this.mActionView.setPadding(i3, i3, i3, i3);
            this.mActionView.setBackground(SliceViewUtil.getDrawable(getContext(), 16843868));
            this.mActionView.setOnClickListener(this);
        }
        View view2 = this.mActionView;
        if (view2 != null) {
            CharSequence charSequence = sliceActionImpl.mContentDescription;
            if (charSequence == null) {
                charSequence = sliceActionImpl.mTitle;
            }
            view2.setContentDescription(charSequence);
        }
    }

    public final void setLoading() {
        if (this.mProgressView == null) {
            ProgressBar progressBar = (ProgressBar) LayoutInflater.from(getContext()).inflate(C1777R.layout.abc_slice_progress_view, this, false);
            this.mProgressView = progressBar;
            addView(progressBar);
        }
        Context context = getContext();
        ProgressBar progressBar2 = this.mProgressView;
        int colorAttr = SliceViewUtil.getColorAttr(context, C1777R.attr.colorControlHighlight);
        Drawable indeterminateDrawable = progressBar2.getIndeterminateDrawable();
        if (!(indeterminateDrawable == null || colorAttr == 0)) {
            indeterminateDrawable.setColorFilter(colorAttr, PorterDuff.Mode.MULTIPLY);
            progressBar2.setProgressDrawable(indeterminateDrawable);
        }
        this.mActionView.setVisibility(8);
        this.mProgressView.setVisibility(0);
    }

    public SliceActionView(Context context, RowStyle rowStyle) {
        super(context);
        Resources resources = getContext().getResources();
        this.mIconSize = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_icon_size);
        this.mImageSize = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_small_image_size);
        if (rowStyle != null) {
            this.mIconSize = rowStyle.mIconSize;
            this.mImageSize = rowStyle.mImageSize;
            this.mTextActionPadding = rowStyle.mTextActionPadding;
        }
    }
}
