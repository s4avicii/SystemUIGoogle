package androidx.preference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import com.android.p012wm.shell.C1777R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Preference implements Comparable<Preference> {
    public boolean mAllowDividerAbove;
    public boolean mAllowDividerBelow;
    public boolean mBaseMethodCalled;
    public final C02961 mClickListener;
    public Context mContext;
    public boolean mCopyingEnabled;
    public Object mDefaultValue;
    public String mDependencyKey;
    public boolean mDependencyMet;
    public ArrayList mDependents;
    public boolean mEnabled;
    public Bundle mExtras;
    public String mFragment;
    public boolean mHasId;
    public boolean mHasSingleLineTitleAttr;
    public Drawable mIcon;
    public int mIconResId;
    public boolean mIconSpaceReserved;
    public long mId;
    public Intent mIntent;
    public String mKey;
    public int mLayoutResId;
    public OnPreferenceChangeInternalListener mListener;
    public OnPreferenceChangeListener mOnChangeListener;
    public OnPreferenceClickListener mOnClickListener;
    public OnPreferenceCopyListener mOnCopyListener;
    public int mOrder;
    public boolean mParentDependencyMet;
    public PreferenceGroup mParentGroup;
    public boolean mPersistent;
    public PreferenceManager mPreferenceManager;
    public boolean mRequiresKey;
    public boolean mSelectable;
    public boolean mShouldDisableView;
    public boolean mSingleLineTitle;
    public CharSequence mSummary;
    public SummaryProvider mSummaryProvider;
    public CharSequence mTitle;
    public boolean mVisible;
    public int mWidgetLayoutResId;

    public static class BaseSavedState extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator<BaseSavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new BaseSavedState[i];
            }
        };

        public BaseSavedState(Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(AbsSavedState absSavedState) {
            super(absSavedState);
        }
    }

    public interface OnPreferenceChangeInternalListener {
    }

    public interface OnPreferenceChangeListener {
        boolean onPreferenceChange(Preference preference, Serializable serializable);
    }

    public interface OnPreferenceClickListener {
        void onPreferenceClick(Preference preference);
    }

    public static class OnPreferenceCopyListener implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public final Preference mPreference;

        public final void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            CharSequence summary = this.mPreference.getSummary();
            Preference preference = this.mPreference;
            Objects.requireNonNull(preference);
            if (preference.mCopyingEnabled && !TextUtils.isEmpty(summary)) {
                contextMenu.setHeaderTitle(summary);
                contextMenu.add(0, 0, 0, C1777R.string.copy).setOnMenuItemClickListener(this);
            }
        }

        public final boolean onMenuItemClick(MenuItem menuItem) {
            Preference preference = this.mPreference;
            Objects.requireNonNull(preference);
            CharSequence summary = this.mPreference.getSummary();
            ((ClipboardManager) preference.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Preference", summary));
            Preference preference2 = this.mPreference;
            Objects.requireNonNull(preference2);
            Context context = preference2.mContext;
            Preference preference3 = this.mPreference;
            Objects.requireNonNull(preference3);
            Toast.makeText(context, preference3.mContext.getString(C1777R.string.preference_copied, new Object[]{summary}), 0).show();
            return true;
        }

        public OnPreferenceCopyListener(Preference preference) {
            this.mPreference = preference;
        }
    }

    public interface SummaryProvider<T extends Preference> {
        CharSequence provideSummary(T t);
    }

    public Preference(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mOrder = Integer.MAX_VALUE;
        this.mEnabled = true;
        this.mSelectable = true;
        this.mPersistent = true;
        this.mDependencyMet = true;
        this.mParentDependencyMet = true;
        this.mVisible = true;
        this.mAllowDividerAbove = true;
        this.mAllowDividerBelow = true;
        this.mSingleLineTitle = true;
        this.mShouldDisableView = true;
        this.mLayoutResId = C1777R.layout.preference;
        this.mClickListener = new View.OnClickListener() {
            public final void onClick(View view) {
                Preference.this.performClick(view);
            }
        };
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        this.mIconResId = obtainStyledAttributes.getResourceId(23, obtainStyledAttributes.getResourceId(0, 0));
        this.mKey = TypedArrayUtils.getString(obtainStyledAttributes, 26, 6);
        CharSequence text = obtainStyledAttributes.getText(34);
        this.mTitle = text == null ? obtainStyledAttributes.getText(4) : text;
        CharSequence text2 = obtainStyledAttributes.getText(33);
        this.mSummary = text2 == null ? obtainStyledAttributes.getText(7) : text2;
        this.mOrder = obtainStyledAttributes.getInt(28, obtainStyledAttributes.getInt(8, Integer.MAX_VALUE));
        this.mFragment = TypedArrayUtils.getString(obtainStyledAttributes, 22, 13);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(27, obtainStyledAttributes.getResourceId(3, C1777R.layout.preference));
        this.mWidgetLayoutResId = obtainStyledAttributes.getResourceId(35, obtainStyledAttributes.getResourceId(9, 0));
        this.mEnabled = obtainStyledAttributes.getBoolean(21, obtainStyledAttributes.getBoolean(2, true));
        this.mSelectable = obtainStyledAttributes.getBoolean(30, obtainStyledAttributes.getBoolean(5, true));
        this.mPersistent = obtainStyledAttributes.getBoolean(29, obtainStyledAttributes.getBoolean(1, true));
        this.mDependencyKey = TypedArrayUtils.getString(obtainStyledAttributes, 19, 10);
        this.mAllowDividerAbove = obtainStyledAttributes.getBoolean(16, obtainStyledAttributes.getBoolean(16, this.mSelectable));
        this.mAllowDividerBelow = obtainStyledAttributes.getBoolean(17, obtainStyledAttributes.getBoolean(17, this.mSelectable));
        if (obtainStyledAttributes.hasValue(18)) {
            this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, 18);
        } else if (obtainStyledAttributes.hasValue(11)) {
            this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, 11);
        }
        this.mShouldDisableView = obtainStyledAttributes.getBoolean(31, obtainStyledAttributes.getBoolean(12, true));
        boolean hasValue = obtainStyledAttributes.hasValue(32);
        this.mHasSingleLineTitleAttr = hasValue;
        if (hasValue) {
            this.mSingleLineTitle = obtainStyledAttributes.getBoolean(32, obtainStyledAttributes.getBoolean(14, true));
        }
        this.mIconSpaceReserved = obtainStyledAttributes.getBoolean(24, obtainStyledAttributes.getBoolean(15, false));
        this.mVisible = obtainStyledAttributes.getBoolean(25, obtainStyledAttributes.getBoolean(25, true));
        this.mCopyingEnabled = obtainStyledAttributes.getBoolean(20, obtainStyledAttributes.getBoolean(20, false));
        obtainStyledAttributes.recycle();
    }

    public void onClick() {
    }

    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return null;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        this.mBaseMethodCalled = true;
        if (parcelable != AbsSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    public Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return AbsSavedState.EMPTY_STATE;
    }

    public void onSetInitialValue(Object obj) {
    }

    public void performClick(View view) {
        performClick();
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.equals(charSequence, this.mTitle)) {
            this.mTitle = charSequence;
            notifyChanged();
        }
    }

    public final boolean callChangeListener(Serializable serializable) {
        OnPreferenceChangeListener onPreferenceChangeListener = this.mOnChangeListener;
        if (onPreferenceChangeListener == null || onPreferenceChangeListener.onPreferenceChange(this, serializable)) {
            return true;
        }
        return false;
    }

    public final int compareTo(Object obj) {
        Preference preference = (Preference) obj;
        int i = this.mOrder;
        int i2 = preference.mOrder;
        if (i != i2) {
            return i - i2;
        }
        CharSequence charSequence = this.mTitle;
        CharSequence charSequence2 = preference.mTitle;
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (charSequence2 == null) {
            return -1;
        }
        return charSequence.toString().compareToIgnoreCase(preference.mTitle.toString());
    }

    public void dispatchRestoreInstanceState(Bundle bundle) {
        Parcelable parcelable;
        if ((!TextUtils.isEmpty(this.mKey)) && (parcelable = bundle.getParcelable(this.mKey)) != null) {
            this.mBaseMethodCalled = false;
            onRestoreInstanceState(parcelable);
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    public void dispatchSaveInstanceState(Bundle bundle) {
        if (!TextUtils.isEmpty(this.mKey)) {
            this.mBaseMethodCalled = false;
            Parcelable onSaveInstanceState = onSaveInstanceState();
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            } else if (onSaveInstanceState != null) {
                bundle.putParcelable(this.mKey, onSaveInstanceState);
            }
        }
    }

    public final Drawable getIcon() {
        int i;
        if (this.mIcon == null && (i = this.mIconResId) != 0) {
            this.mIcon = AppCompatResources.getDrawable(this.mContext, i);
        }
        return this.mIcon;
    }

    public CharSequence getSummary() {
        SummaryProvider summaryProvider = this.mSummaryProvider;
        if (summaryProvider != null) {
            return summaryProvider.provideSummary(this);
        }
        return this.mSummary;
    }

    public boolean isEnabled() {
        if (!this.mEnabled || !this.mDependencyMet || !this.mParentDependencyMet) {
            return false;
        }
        return true;
    }

    public void notifyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) onPreferenceChangeInternalListener;
            int indexOf = preferenceGroupAdapter.mVisiblePreferences.indexOf(this);
            if (indexOf != -1) {
                preferenceGroupAdapter.mObservable.notifyItemRangeChanged(indexOf, 1, this);
            }
        }
    }

    public void notifyDependencyChange(boolean z) {
        ArrayList arrayList = this.mDependents;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Preference preference = (Preference) arrayList.get(i);
                Objects.requireNonNull(preference);
                if (preference.mDependencyMet == z) {
                    preference.mDependencyMet = !z;
                    preference.notifyDependencyChange(preference.shouldDisableDependents());
                    preference.notifyChanged();
                }
            }
        }
    }

    public void onAttached() {
        PreferenceScreen preferenceScreen;
        if (!TextUtils.isEmpty(this.mDependencyKey)) {
            String str = this.mDependencyKey;
            PreferenceManager preferenceManager = this.mPreferenceManager;
            Preference preference = null;
            if (!(preferenceManager == null || (preferenceScreen = preferenceManager.mPreferenceScreen) == null)) {
                preference = preferenceScreen.findPreference(str);
            }
            if (preference != null) {
                if (preference.mDependents == null) {
                    preference.mDependents = new ArrayList();
                }
                preference.mDependents.add(this);
                boolean shouldDisableDependents = preference.shouldDisableDependents();
                if (this.mDependencyMet == shouldDisableDependents) {
                    this.mDependencyMet = !shouldDisableDependents;
                    notifyDependencyChange(shouldDisableDependents());
                    notifyChanged();
                    return;
                }
                return;
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Dependency \"");
            m.append(this.mDependencyKey);
            m.append("\" not found for preference \"");
            m.append(this.mKey);
            m.append("\" (title: \"");
            m.append(this.mTitle);
            m.append("\"");
            throw new IllegalStateException(m.toString());
        }
    }

    public void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        SharedPreferences sharedPreferences;
        long j;
        this.mPreferenceManager = preferenceManager;
        if (!this.mHasId) {
            Objects.requireNonNull(preferenceManager);
            synchronized (preferenceManager) {
                j = preferenceManager.mNextId;
                preferenceManager.mNextId = 1 + j;
            }
            this.mId = j;
        }
        if (shouldPersist()) {
            PreferenceManager preferenceManager2 = this.mPreferenceManager;
            if (preferenceManager2 != null) {
                sharedPreferences = preferenceManager2.getSharedPreferences();
            } else {
                sharedPreferences = null;
            }
            if (sharedPreferences.contains(this.mKey)) {
                onSetInitialValue((Object) null);
                return;
            }
        }
        Object obj = this.mDefaultValue;
        if (obj != null) {
            onSetInitialValue(obj);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBindViewHolder(androidx.preference.PreferenceViewHolder r9) {
        /*
            r8 = this;
            android.view.View r0 = r9.itemView
            androidx.preference.Preference$1 r1 = r8.mClickListener
            r0.setOnClickListener(r1)
            r1 = 0
            r0.setId(r1)
            r2 = 16908304(0x1020010, float:2.3877274E-38)
            android.view.View r2 = r9.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r3 = 0
            r4 = 8
            if (r2 == 0) goto L_0x0035
            java.lang.CharSequence r5 = r8.getSummary()
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0032
            r2.setText(r5)
            r2.setVisibility(r1)
            int r2 = r2.getCurrentTextColor()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x0036
        L_0x0032:
            r2.setVisibility(r4)
        L_0x0035:
            r2 = r3
        L_0x0036:
            r5 = 16908310(0x1020016, float:2.387729E-38)
            android.view.View r5 = r9.findViewById(r5)
            android.widget.TextView r5 = (android.widget.TextView) r5
            if (r5 == 0) goto L_0x006f
            java.lang.CharSequence r6 = r8.mTitle
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x006c
            r5.setText(r6)
            r5.setVisibility(r1)
            boolean r6 = r8.mHasSingleLineTitleAttr
            if (r6 == 0) goto L_0x0058
            boolean r6 = r8.mSingleLineTitle
            r5.setSingleLine(r6)
        L_0x0058:
            boolean r6 = r8.mSelectable
            if (r6 != 0) goto L_0x006f
            boolean r6 = r8.isEnabled()
            if (r6 == 0) goto L_0x006f
            if (r2 == 0) goto L_0x006f
            int r2 = r2.intValue()
            r5.setTextColor(r2)
            goto L_0x006f
        L_0x006c:
            r5.setVisibility(r4)
        L_0x006f:
            r2 = 16908294(0x1020006, float:2.3877246E-38)
            android.view.View r2 = r9.findViewById(r2)
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            r5 = 4
            if (r2 == 0) goto L_0x00a8
            int r6 = r8.mIconResId
            if (r6 != 0) goto L_0x0083
            android.graphics.drawable.Drawable r7 = r8.mIcon
            if (r7 == 0) goto L_0x0096
        L_0x0083:
            android.graphics.drawable.Drawable r7 = r8.mIcon
            if (r7 != 0) goto L_0x008f
            android.content.Context r7 = r8.mContext
            android.graphics.drawable.Drawable r6 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r7, r6)
            r8.mIcon = r6
        L_0x008f:
            android.graphics.drawable.Drawable r6 = r8.mIcon
            if (r6 == 0) goto L_0x0096
            r2.setImageDrawable(r6)
        L_0x0096:
            android.graphics.drawable.Drawable r6 = r8.mIcon
            if (r6 == 0) goto L_0x009e
            r2.setVisibility(r1)
            goto L_0x00a8
        L_0x009e:
            boolean r6 = r8.mIconSpaceReserved
            if (r6 == 0) goto L_0x00a4
            r6 = r5
            goto L_0x00a5
        L_0x00a4:
            r6 = r4
        L_0x00a5:
            r2.setVisibility(r6)
        L_0x00a8:
            r2 = 2131428104(0x7f0b0308, float:1.8477843E38)
            android.view.View r2 = r9.findViewById(r2)
            if (r2 != 0) goto L_0x00b8
            r2 = 16908350(0x102003e, float:2.3877403E-38)
            android.view.View r2 = r9.findViewById(r2)
        L_0x00b8:
            if (r2 == 0) goto L_0x00ca
            android.graphics.drawable.Drawable r6 = r8.mIcon
            if (r6 == 0) goto L_0x00c2
            r2.setVisibility(r1)
            goto L_0x00ca
        L_0x00c2:
            boolean r1 = r8.mIconSpaceReserved
            if (r1 == 0) goto L_0x00c7
            r4 = r5
        L_0x00c7:
            r2.setVisibility(r4)
        L_0x00ca:
            boolean r1 = r8.mShouldDisableView
            if (r1 == 0) goto L_0x00d6
            boolean r1 = r8.isEnabled()
            setEnabledStateOnViews(r0, r1)
            goto L_0x00da
        L_0x00d6:
            r1 = 1
            setEnabledStateOnViews(r0, r1)
        L_0x00da:
            boolean r1 = r8.mSelectable
            r0.setFocusable(r1)
            r0.setClickable(r1)
            boolean r2 = r8.mAllowDividerAbove
            r9.mDividerAllowedAbove = r2
            boolean r2 = r8.mAllowDividerBelow
            r9.mDividerAllowedBelow = r2
            boolean r9 = r8.mCopyingEnabled
            if (r9 == 0) goto L_0x00f9
            androidx.preference.Preference$OnPreferenceCopyListener r2 = r8.mOnCopyListener
            if (r2 != 0) goto L_0x00f9
            androidx.preference.Preference$OnPreferenceCopyListener r2 = new androidx.preference.Preference$OnPreferenceCopyListener
            r2.<init>(r8)
            r8.mOnCopyListener = r2
        L_0x00f9:
            if (r9 == 0) goto L_0x00fe
            androidx.preference.Preference$OnPreferenceCopyListener r8 = r8.mOnCopyListener
            goto L_0x00ff
        L_0x00fe:
            r8 = r3
        L_0x00ff:
            r0.setOnCreateContextMenuListener(r8)
            r0.setLongClickable(r9)
            if (r9 == 0) goto L_0x010e
            if (r1 != 0) goto L_0x010e
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r8 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r0, r3)
        L_0x010e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.Preference.onBindViewHolder(androidx.preference.PreferenceViewHolder):void");
    }

    public void performClick() {
        Intent intent;
        PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener;
        if (isEnabled() && this.mSelectable) {
            onClick();
            OnPreferenceClickListener onPreferenceClickListener = this.mOnClickListener;
            if (onPreferenceClickListener != null) {
                onPreferenceClickListener.onPreferenceClick(this);
                return;
            }
            PreferenceManager preferenceManager = this.mPreferenceManager;
            if ((preferenceManager == null || (onPreferenceTreeClickListener = preferenceManager.mOnPreferenceTreeClickListener) == null || !onPreferenceTreeClickListener.onPreferenceTreeClick(this)) && (intent = this.mIntent) != null) {
                this.mContext.startActivity(intent);
            }
        }
    }

    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void setIcon(Drawable drawable) {
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            this.mIconResId = 0;
            notifyChanged();
        }
    }

    public final void setKey(String str) {
        this.mKey = str;
        if (this.mRequiresKey && !(!TextUtils.isEmpty(str))) {
            if (!TextUtils.isEmpty(this.mKey)) {
                this.mRequiresKey = true;
                return;
            }
            throw new IllegalStateException("Preference does not have a key assigned.");
        }
    }

    public void setSummary(CharSequence charSequence) {
        if (this.mSummaryProvider != null) {
            throw new IllegalStateException("Preference already has a SummaryProvider set.");
        } else if (!TextUtils.equals(this.mSummary, charSequence)) {
            this.mSummary = charSequence;
            notifyChanged();
        }
    }

    public void setSummary$1() {
        setSummary(this.mContext.getString(C1777R.string.lockscreen_none));
    }

    public final void setVisible(boolean z) {
        if (this.mVisible != z) {
            this.mVisible = z;
            OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
            if (onPreferenceChangeInternalListener != null) {
                PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) onPreferenceChangeInternalListener;
                preferenceGroupAdapter.mHandler.removeCallbacks(preferenceGroupAdapter.mSyncRunnable);
                preferenceGroupAdapter.mHandler.post(preferenceGroupAdapter.mSyncRunnable);
            }
        }
    }

    public final boolean shouldPersist() {
        if (this.mPreferenceManager == null || !this.mPersistent || !(!TextUtils.isEmpty(this.mKey))) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        CharSequence charSequence = this.mTitle;
        if (!TextUtils.isEmpty(charSequence)) {
            sb.append(charSequence);
            sb.append(' ');
        }
        CharSequence summary = getSummary();
        if (!TextUtils.isEmpty(summary)) {
            sb.append(summary);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public final void tryCommit(SharedPreferences.Editor editor) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        if (!preferenceManager.mNoCommit) {
            editor.apply();
        }
    }

    public final void unregisterDependency() {
        ArrayList arrayList;
        PreferenceScreen preferenceScreen;
        String str = this.mDependencyKey;
        if (str != null) {
            PreferenceManager preferenceManager = this.mPreferenceManager;
            Preference preference = null;
            if (!(preferenceManager == null || (preferenceScreen = preferenceManager.mPreferenceScreen) == null)) {
                preference = preferenceScreen.findPreference(str);
            }
            if (preference != null && (arrayList = preference.mDependents) != null) {
                arrayList.remove(this);
            }
        }
    }

    public static void setEnabledStateOnViews(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            while (true) {
                childCount--;
                if (childCount >= 0) {
                    setEnabledStateOnViews(viewGroup.getChildAt(childCount), z);
                } else {
                    return;
                }
            }
        }
    }

    public final String getPersistedString(String str) {
        if (!shouldPersist()) {
            return str;
        }
        return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, str);
    }

    public boolean persistBoolean(boolean z) {
        if (!shouldPersist()) {
            return false;
        }
        boolean z2 = !z;
        if (shouldPersist()) {
            z2 = this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, z2);
        }
        if (z == z2) {
            return true;
        }
        SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
        editor.putBoolean(this.mKey, z);
        tryCommit(editor);
        return true;
    }

    public boolean persistString(String str) {
        if (!shouldPersist()) {
            return false;
        }
        if (TextUtils.equals(str, getPersistedString((String) null))) {
            return true;
        }
        SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
        editor.putString(this.mKey, str);
        tryCommit(editor);
        return true;
    }

    public boolean shouldDisableDependents() {
        return !isEnabled();
    }

    public final void setTitle(int i) {
        setTitle((CharSequence) this.mContext.getString(i));
    }

    public Preference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, C1777R.attr.preferenceStyle, 16842894));
    }

    public Preference(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: package-private */
    public long getId() {
        return this.mId;
    }

    public void onDetached() {
        unregisterDependency();
    }
}
