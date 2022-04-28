package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.WeakHashMap;

public class ActivityChooserView extends ViewGroup {
    public final View mActivityChooserContent;
    public final ActivityChooserViewAdapter mAdapter;
    public final Callbacks mCallbacks;
    public final FrameLayout mDefaultActivityButton;
    public final FrameLayout mExpandActivityOverflowButton;
    public int mInitialActivityCount = 4;
    public boolean mIsAttachedToWindow;
    public ListPopupWindow mListPopupWindow;
    public final C00571 mModelDataSetObserver = new DataSetObserver() {
        public final void onChanged() {
            super.onChanged();
            ActivityChooserView.this.mAdapter.notifyDataSetChanged();
        }

        public final void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
        }
    };
    public final C00582 mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public final void onGlobalLayout() {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            Objects.requireNonNull(activityChooserView);
            if (!activityChooserView.getListPopupWindow().isShowing()) {
                return;
            }
            if (!ActivityChooserView.this.isShown()) {
                ActivityChooserView.this.getListPopupWindow().dismiss();
                return;
            }
            ActivityChooserView.this.getListPopupWindow().show();
            Objects.requireNonNull(ActivityChooserView.this);
        }
    };

    public class ActivityChooserViewAdapter extends BaseAdapter {
        public int mMaxActivityCount = 4;
        public boolean mShowDefaultActivity;
        public boolean mShowFooterView;

        public final long getItemId(int i) {
            return (long) i;
        }

        public final int getViewTypeCount() {
            return 3;
        }

        public ActivityChooserViewAdapter() {
        }

        public final int getItemViewType(int i) {
            if (!this.mShowFooterView) {
                return 0;
            }
            throw null;
        }

        public final Object getItem(int i) {
            getItemViewType(i);
            if (!this.mShowDefaultActivity) {
                throw null;
            }
            throw null;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            getItemViewType(i);
            if (view == null || view.getId() != C1777R.C1779id.list_item) {
                view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C1777R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
            }
            ActivityChooserView.this.getContext().getPackageManager();
            ImageView imageView = (ImageView) view.findViewById(C1777R.C1779id.icon);
            getItem(i);
            throw null;
        }

        public final int getCount() {
            throw null;
        }
    }

    public class Callbacks implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        public Callbacks() {
        }

        public final void onClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.mDefaultActivityButton) {
                activityChooserView.dismissPopup();
                Objects.requireNonNull(ActivityChooserView.this.mAdapter);
                throw null;
            } else if (view == activityChooserView.mExpandActivityOverflowButton) {
                Objects.requireNonNull(activityChooserView.mAdapter);
                throw new IllegalStateException("No data model. Did you call #setDataModel?");
            } else {
                throw new IllegalArgumentException();
            }
        }

        public final void onDismiss() {
            Objects.requireNonNull(ActivityChooserView.this);
            Objects.requireNonNull(ActivityChooserView.this);
        }

        public final boolean onLongClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.mDefaultActivityButton) {
                Objects.requireNonNull(activityChooserView.mAdapter);
                throw null;
            }
            throw new IllegalArgumentException();
        }

        public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ((ActivityChooserViewAdapter) adapterView.getAdapter()).getItemViewType(i);
            ActivityChooserView.this.dismissPopup();
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            Objects.requireNonNull(activityChooserView);
            Objects.requireNonNull(activityChooserView.mAdapter);
            Objects.requireNonNull(ActivityChooserView.this.mAdapter);
            throw null;
        }
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        int[] iArr = R$styleable.ActivityChooserView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        this.mInitialActivityCount = obtainStyledAttributes.getInt(1, 4);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(C1777R.layout.abc_activity_chooser_view, this, true);
        Callbacks callbacks = new Callbacks();
        this.mCallbacks = callbacks;
        View findViewById = findViewById(C1777R.C1779id.activity_chooser_view_content);
        this.mActivityChooserContent = findViewById;
        findViewById.getBackground();
        FrameLayout frameLayout = (FrameLayout) findViewById(C1777R.C1779id.default_activity_button);
        this.mDefaultActivityButton = frameLayout;
        frameLayout.setOnClickListener(callbacks);
        frameLayout.setOnLongClickListener(callbacks);
        ImageView imageView = (ImageView) frameLayout.findViewById(C1777R.C1779id.image);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(C1777R.C1779id.expand_activities_button);
        frameLayout2.setOnClickListener(callbacks);
        frameLayout2.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setCanOpenPopup(true);
            }
        });
        frameLayout2.setOnTouchListener(new ForwardingListener(frameLayout2) {
            public final ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            public final boolean onForwardingStarted() {
                ActivityChooserView activityChooserView = ActivityChooserView.this;
                Objects.requireNonNull(activityChooserView);
                if (activityChooserView.getListPopupWindow().isShowing() || !activityChooserView.mIsAttachedToWindow) {
                    return true;
                }
                Objects.requireNonNull(activityChooserView.mAdapter);
                throw new IllegalStateException("No data model. Did you call #setDataModel?");
            }

            public final boolean onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = frameLayout2;
        ((ImageView) frameLayout2.findViewById(C1777R.C1779id.image)).setImageDrawable(drawable);
        ActivityChooserViewAdapter activityChooserViewAdapter = new ActivityChooserViewAdapter();
        this.mAdapter = activityChooserViewAdapter;
        activityChooserViewAdapter.registerDataSetObserver(new DataSetObserver() {
            public final void onChanged() {
                super.onChanged();
                ActivityChooserView activityChooserView = ActivityChooserView.this;
                Objects.requireNonNull(activityChooserView);
                Objects.requireNonNull(activityChooserView.mAdapter);
                throw null;
            }
        });
        Resources resources = context.getResources();
        Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C1777R.dimen.abc_config_prefDialogWidth));
    }

    public static class InnerLayout extends LinearLayout {
        public static final int[] TINT_ATTRS = {16842964};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            Drawable drawable;
            int resourceId;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, TINT_ATTRS);
            if (!obtainStyledAttributes.hasValue(0) || (resourceId = obtainStyledAttributes.getResourceId(0, 0)) == 0) {
                drawable = obtainStyledAttributes.getDrawable(0);
            } else {
                drawable = AppCompatResources.getDrawable(context, resourceId);
            }
            setBackgroundDrawable(drawable);
            obtainStyledAttributes.recycle();
        }
    }

    public final ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
            this.mListPopupWindow = listPopupWindow;
            listPopupWindow.setAdapter(this.mAdapter);
            ListPopupWindow listPopupWindow2 = this.mListPopupWindow;
            Objects.requireNonNull(listPopupWindow2);
            listPopupWindow2.mDropDownAnchorView = this;
            ListPopupWindow listPopupWindow3 = this.mListPopupWindow;
            Objects.requireNonNull(listPopupWindow3);
            listPopupWindow3.mModal = true;
            listPopupWindow3.mPopup.setFocusable(true);
            ListPopupWindow listPopupWindow4 = this.mListPopupWindow;
            Callbacks callbacks = this.mCallbacks;
            Objects.requireNonNull(listPopupWindow4);
            listPopupWindow4.mItemClickListener = callbacks;
            ListPopupWindow listPopupWindow5 = this.mListPopupWindow;
            Callbacks callbacks2 = this.mCallbacks;
            Objects.requireNonNull(listPopupWindow5);
            listPopupWindow5.mPopup.setOnDismissListener(callbacks2);
        }
        return this.mListPopupWindow;
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mActivityChooserContent.layout(0, 0, i3 - i, i4 - i2);
        if (!getListPopupWindow().isShowing()) {
            dismissPopup();
        }
    }

    public final void onMeasure(int i, int i2) {
        View view = this.mActivityChooserContent;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824);
        }
        measureChild(view, i, i2);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public final void dismissPopup() {
        if (getListPopupWindow().isShowing()) {
            getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
            }
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Objects.requireNonNull(this.mAdapter);
        this.mIsAttachedToWindow = true;
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Objects.requireNonNull(this.mAdapter);
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (getListPopupWindow().isShowing()) {
            dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }
}
