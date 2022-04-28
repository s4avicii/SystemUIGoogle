package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

public final class PreferenceGroupAdapter extends RecyclerView.Adapter<PreferenceViewHolder> implements Preference.OnPreferenceChangeInternalListener {
    public Handler mHandler;
    public PreferenceGroup mPreferenceGroup;
    public ArrayList mPreferenceResourceDescriptors;
    public ArrayList mPreferences;
    public C03021 mSyncRunnable = new Runnable() {
        public final void run() {
            PreferenceGroupAdapter.this.updatePreferences();
        }
    };
    public ArrayList mVisiblePreferences;

    public static class PreferenceResourceDescriptor {
        public String mClassName;
        public int mLayoutResId;
        public int mWidgetLayoutResId;

        public final boolean equals(Object obj) {
            if (!(obj instanceof PreferenceResourceDescriptor)) {
                return false;
            }
            PreferenceResourceDescriptor preferenceResourceDescriptor = (PreferenceResourceDescriptor) obj;
            if (this.mLayoutResId == preferenceResourceDescriptor.mLayoutResId && this.mWidgetLayoutResId == preferenceResourceDescriptor.mWidgetLayoutResId && TextUtils.equals(this.mClassName, preferenceResourceDescriptor.mClassName)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this.mClassName.hashCode() + ((((527 + this.mLayoutResId) * 31) + this.mWidgetLayoutResId) * 31);
        }

        public PreferenceResourceDescriptor(Preference preference) {
            this.mClassName = preference.getClass().getName();
            this.mLayoutResId = preference.mLayoutResId;
            this.mWidgetLayoutResId = preference.mWidgetLayoutResId;
        }
    }

    public final ArrayList createVisiblePreferencesList(final PreferenceGroup preferenceGroup) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        int i = 0;
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            Preference preference = preferenceGroup.getPreference(i2);
            Objects.requireNonNull(preference);
            if (preference.mVisible) {
                if (!isGroupExpandable(preferenceGroup) || i < preferenceGroup.mInitialExpandedChildrenCount) {
                    arrayList.add(preference);
                } else {
                    arrayList2.add(preference);
                }
                if (!(preference instanceof PreferenceGroup)) {
                    i++;
                } else {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                    if (!(!(preferenceGroup2 instanceof PreferenceScreen))) {
                        continue;
                    } else if (!isGroupExpandable(preferenceGroup) || !isGroupExpandable(preferenceGroup2)) {
                        Iterator it = createVisiblePreferencesList(preferenceGroup2).iterator();
                        while (it.hasNext()) {
                            Preference preference2 = (Preference) it.next();
                            if (!isGroupExpandable(preferenceGroup) || i < preferenceGroup.mInitialExpandedChildrenCount) {
                                arrayList.add(preference2);
                            } else {
                                arrayList2.add(preference2);
                            }
                            i++;
                        }
                    } else {
                        throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                    }
                }
            }
        }
        if (isGroupExpandable(preferenceGroup) && i > preferenceGroup.mInitialExpandedChildrenCount) {
            ExpandButton expandButton = new ExpandButton(preferenceGroup.mContext, arrayList2, preferenceGroup.mId);
            expandButton.mOnClickListener = new Preference.OnPreferenceClickListener() {
                public final void onPreferenceClick(Preference preference) {
                    preferenceGroup.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
                    PreferenceGroupAdapter preferenceGroupAdapter = PreferenceGroupAdapter.this;
                    Objects.requireNonNull(preferenceGroupAdapter);
                    preferenceGroupAdapter.mHandler.removeCallbacks(preferenceGroupAdapter.mSyncRunnable);
                    preferenceGroupAdapter.mHandler.post(preferenceGroupAdapter.mSyncRunnable);
                    Objects.requireNonNull(preferenceGroup);
                }
            };
            arrayList.add(expandButton);
        }
        return arrayList;
    }

    public final Preference getItem(int i) {
        if (i < 0 || i >= getItemCount()) {
            return null;
        }
        return (Preference) this.mVisiblePreferences.get(i);
    }

    public final int getItemCount() {
        return this.mVisiblePreferences.size();
    }

    public final long getItemId(int i) {
        if (!this.mHasStableIds) {
            return -1;
        }
        return getItem(i).getId();
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        PreferenceViewHolder preferenceViewHolder = (PreferenceViewHolder) viewHolder;
        Preference item = getItem(i);
        Drawable background = preferenceViewHolder.itemView.getBackground();
        Drawable drawable = preferenceViewHolder.mBackground;
        if (background != drawable) {
            View view = preferenceViewHolder.itemView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(view, drawable);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(16908310);
        if (!(textView == null || preferenceViewHolder.mTitleTextColors == null || textView.getTextColors().equals(preferenceViewHolder.mTitleTextColors))) {
            textView.setTextColor(preferenceViewHolder.mTitleTextColors);
        }
        item.onBindViewHolder(preferenceViewHolder);
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = (PreferenceResourceDescriptor) this.mPreferenceResourceDescriptors.get(i);
        LayoutInflater from = LayoutInflater.from(recyclerView.getContext());
        TypedArray obtainStyledAttributes = recyclerView.getContext().obtainStyledAttributes((AttributeSet) null, R$styleable.BackgroundStyle);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable == null) {
            drawable = AppCompatResources.getDrawable(recyclerView.getContext(), 17301602);
        }
        obtainStyledAttributes.recycle();
        View inflate = from.inflate(preferenceResourceDescriptor.mLayoutResId, recyclerView, false);
        if (inflate.getBackground() == null) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(inflate, drawable);
        }
        ViewGroup viewGroup = (ViewGroup) inflate.findViewById(16908312);
        if (viewGroup != null) {
            int i2 = preferenceResourceDescriptor.mWidgetLayoutResId;
            if (i2 != 0) {
                from.inflate(i2, viewGroup);
            } else {
                viewGroup.setVisibility(8);
            }
        }
        return new PreferenceViewHolder(inflate);
    }

    public final void updatePreferences() {
        Iterator it = this.mPreferences.iterator();
        while (it.hasNext()) {
            Preference preference = (Preference) it.next();
            Objects.requireNonNull(preference);
            preference.mListener = null;
        }
        ArrayList arrayList = new ArrayList(this.mPreferences.size());
        this.mPreferences = arrayList;
        flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
        this.mVisiblePreferences = createVisiblePreferencesList(this.mPreferenceGroup);
        Objects.requireNonNull(this.mPreferenceGroup);
        notifyDataSetChanged();
        Iterator it2 = this.mPreferences.iterator();
        while (it2.hasNext()) {
            Objects.requireNonNull((Preference) it2.next());
        }
    }

    public PreferenceGroupAdapter(PreferenceScreen preferenceScreen) {
        this.mPreferenceGroup = preferenceScreen;
        this.mHandler = new Handler();
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        Objects.requireNonNull(preferenceGroup);
        preferenceGroup.mListener = this;
        this.mPreferences = new ArrayList();
        this.mVisiblePreferences = new ArrayList();
        this.mPreferenceResourceDescriptors = new ArrayList();
        PreferenceGroup preferenceGroup2 = this.mPreferenceGroup;
        if (preferenceGroup2 instanceof PreferenceScreen) {
            PreferenceScreen preferenceScreen2 = (PreferenceScreen) preferenceGroup2;
            Objects.requireNonNull(preferenceScreen2);
            setHasStableIds(preferenceScreen2.mShouldUseGeneratedIds);
        } else {
            setHasStableIds(true);
        }
        updatePreferences();
    }

    public static boolean isGroupExpandable(PreferenceGroup preferenceGroup) {
        Objects.requireNonNull(preferenceGroup);
        if (preferenceGroup.mInitialExpandedChildrenCount != Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }

    public final void flattenPreferenceGroup(ArrayList arrayList, PreferenceGroup preferenceGroup) {
        Objects.requireNonNull(preferenceGroup);
        synchronized (preferenceGroup) {
            Collections.sort(preferenceGroup.mPreferences);
        }
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            arrayList.add(preference);
            PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(preference);
            if (!this.mPreferenceResourceDescriptors.contains(preferenceResourceDescriptor)) {
                this.mPreferenceResourceDescriptors.add(preferenceResourceDescriptor);
            }
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                if (!(preferenceGroup2 instanceof PreferenceScreen)) {
                    flattenPreferenceGroup(arrayList, preferenceGroup2);
                }
            }
            preference.mListener = this;
        }
    }

    public final int getItemViewType(int i) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(getItem(i));
        int indexOf = this.mPreferenceResourceDescriptors.indexOf(preferenceResourceDescriptor);
        if (indexOf != -1) {
            return indexOf;
        }
        int size = this.mPreferenceResourceDescriptors.size();
        this.mPreferenceResourceDescriptors.add(preferenceResourceDescriptor);
        return size;
    }
}
