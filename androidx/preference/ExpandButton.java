package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class ExpandButton extends Preference {
    public long mId;

    public ExpandButton(Context context, ArrayList arrayList, long j) {
        super(context);
        this.mLayoutResId = C1777R.layout.expand_button;
        setIcon(AppCompatResources.getDrawable(this.mContext, C1777R.C1778drawable.ic_arrow_down_24dp));
        this.mIconResId = C1777R.C1778drawable.ic_arrow_down_24dp;
        setTitle((int) C1777R.string.expand_button_title);
        if (999 != this.mOrder) {
            this.mOrder = 999;
            Preference.OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
            if (onPreferenceChangeInternalListener != null) {
                PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) onPreferenceChangeInternalListener;
                preferenceGroupAdapter.mHandler.removeCallbacks(preferenceGroupAdapter.mSyncRunnable);
                preferenceGroupAdapter.mHandler.post(preferenceGroupAdapter.mSyncRunnable);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        CharSequence charSequence = null;
        while (it.hasNext()) {
            Preference preference = (Preference) it.next();
            Objects.requireNonNull(preference);
            CharSequence charSequence2 = preference.mTitle;
            boolean z = preference instanceof PreferenceGroup;
            if (z && !TextUtils.isEmpty(charSequence2)) {
                arrayList2.add((PreferenceGroup) preference);
            }
            if (arrayList2.contains(preference.mParentGroup)) {
                if (z) {
                    arrayList2.add((PreferenceGroup) preference);
                }
            } else if (!TextUtils.isEmpty(charSequence2)) {
                if (charSequence == null) {
                    charSequence = charSequence2;
                } else {
                    charSequence = this.mContext.getString(C1777R.string.summary_collapsed_preference_list, new Object[]{charSequence, charSequence2});
                }
            }
        }
        setSummary(charSequence);
        this.mId = j + 1000000;
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
    }

    public final long getId() {
        return this.mId;
    }
}
