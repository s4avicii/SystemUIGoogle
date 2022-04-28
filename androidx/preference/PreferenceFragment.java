package androidx.preference;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

@Deprecated
public abstract class PreferenceFragment extends Fragment implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceManager.OnDisplayPreferenceDialogListener, PreferenceManager.OnNavigateToScreenListener, DialogPreference.TargetFragment {
    public final DividerDecoration mDividerDecoration = new DividerDecoration();
    public final C02981 mHandler = new Handler() {
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                PreferenceFragment preferenceFragment = PreferenceFragment.this;
                Objects.requireNonNull(preferenceFragment);
                PreferenceScreen preferenceScreen = preferenceFragment.getPreferenceScreen();
                if (preferenceScreen != null) {
                    preferenceFragment.mList.setAdapter(new PreferenceGroupAdapter(preferenceScreen));
                    preferenceScreen.onAttached();
                }
            }
        }
    };
    public boolean mHavePrefs;
    public boolean mInitDone;
    public int mLayoutResId = C1777R.layout.preference_list_fragment;
    public RecyclerView mList;
    public PreferenceManager mPreferenceManager;
    public final C02992 mRequestFocus = new Runnable() {
        public final void run() {
            RecyclerView recyclerView = PreferenceFragment.this.mList;
            recyclerView.focusableViewAvailable(recyclerView);
        }
    };
    public ContextThemeWrapper mStyledContext;

    public class DividerDecoration extends RecyclerView.ItemDecoration {
        public boolean mAllowDividerAfterLastItem = true;
        public Drawable mDivider;
        public int mDividerHeight;

        public DividerDecoration() {
        }

        public final void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
            if (this.mDivider != null) {
                int childCount = recyclerView.getChildCount();
                int width = recyclerView.getWidth();
                for (int i = 0; i < childCount; i++) {
                    View childAt = recyclerView.getChildAt(i);
                    if (shouldDrawDividerBelow(childAt, recyclerView)) {
                        int height = childAt.getHeight() + ((int) childAt.getY());
                        this.mDivider.setBounds(0, height, width, this.mDividerHeight + height);
                        this.mDivider.draw(canvas);
                    }
                }
            }
        }

        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (shouldDrawDividerBelow(view, recyclerView)) {
                rect.bottom = this.mDividerHeight;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:7:0x0018 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0019  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean shouldDrawDividerBelow(android.view.View r5, androidx.recyclerview.widget.RecyclerView r6) {
            /*
                r4 = this;
                androidx.recyclerview.widget.RecyclerView$ViewHolder r0 = r6.getChildViewHolder(r5)
                boolean r1 = r0 instanceof androidx.preference.PreferenceViewHolder
                r2 = 0
                r3 = 1
                if (r1 == 0) goto L_0x0015
                androidx.preference.PreferenceViewHolder r0 = (androidx.preference.PreferenceViewHolder) r0
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mDividerAllowedBelow
                if (r0 == 0) goto L_0x0015
                r0 = r3
                goto L_0x0016
            L_0x0015:
                r0 = r2
            L_0x0016:
                if (r0 != 0) goto L_0x0019
                return r2
            L_0x0019:
                boolean r4 = r4.mAllowDividerAfterLastItem
                int r5 = r6.indexOfChild(r5)
                int r0 = r6.getChildCount()
                int r0 = r0 - r3
                if (r5 >= r0) goto L_0x003e
                int r5 = r5 + r3
                android.view.View r4 = r6.getChildAt(r5)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r4 = r6.getChildViewHolder(r4)
                boolean r5 = r4 instanceof androidx.preference.PreferenceViewHolder
                if (r5 == 0) goto L_0x003d
                androidx.preference.PreferenceViewHolder r4 = (androidx.preference.PreferenceViewHolder) r4
                java.util.Objects.requireNonNull(r4)
                boolean r4 = r4.mDividerAllowedAbove
                if (r4 == 0) goto L_0x003d
                r2 = r3
            L_0x003d:
                r4 = r2
            L_0x003e:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceFragment.DividerDecoration.shouldDrawDividerBelow(android.view.View, androidx.recyclerview.widget.RecyclerView):boolean");
        }
    }

    public interface OnPreferenceDisplayDialogCallback {
        boolean onPreferenceDisplayDialog();
    }

    public interface OnPreferenceStartFragmentCallback {
        boolean onPreferenceStartFragment(Preference preference);
    }

    public interface OnPreferenceStartScreenCallback {
        void onPreferenceStartScreen(PreferenceFragment preferenceFragment, PreferenceScreen preferenceScreen);
    }

    @Deprecated
    public abstract void onCreatePreferences(String str);

    /* JADX INFO: finally extract failed */
    @Deprecated
    public final void addPreferencesFromResource(int i) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            ContextThemeWrapper contextThemeWrapper = this.mStyledContext;
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            preferenceManager.mNoCommit = true;
            PreferenceInflater preferenceInflater = new PreferenceInflater(contextThemeWrapper, preferenceManager);
            XmlResourceParser xml = contextThemeWrapper.getResources().getXml(i);
            try {
                PreferenceGroup inflate = preferenceInflater.inflate(xml, preferenceScreen);
                xml.close();
                PreferenceScreen preferenceScreen2 = (PreferenceScreen) inflate;
                preferenceScreen2.onAttachedToHierarchy(preferenceManager);
                SharedPreferences.Editor editor = preferenceManager.mEditor;
                if (editor != null) {
                    editor.apply();
                }
                preferenceManager.mNoCommit = false;
                setPreferenceScreen(preferenceScreen2);
            } catch (Throwable th) {
                xml.close();
                throw th;
            }
        } else {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
    }

    @Deprecated
    public final <T extends Preference> T findPreference(CharSequence charSequence) {
        PreferenceScreen preferenceScreen;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null || (preferenceScreen = preferenceManager.mPreferenceScreen) == null) {
            return null;
        }
        return preferenceScreen.findPreference(charSequence);
    }

    @Deprecated
    public final PreferenceScreen getPreferenceScreen() {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        return preferenceManager.mPreferenceScreen;
    }

    /* JADX WARNING: type inference failed for: r9v15, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View onCreateView(android.view.LayoutInflater r9, android.view.ViewGroup r10, android.os.Bundle r11) {
        /*
            r8 = this;
            android.view.ContextThemeWrapper r11 = r8.mStyledContext
            int[] r0 = androidx.preference.R$styleable.PreferenceFragment
            r1 = 2130969623(0x7f040417, float:1.7547933E38)
            r2 = 16844038(0x1010506, float:2.3697162E-38)
            int r1 = androidx.core.content.res.TypedArrayUtils.getAttr(r11, r1, r2)
            r2 = 0
            r3 = 0
            android.content.res.TypedArray r11 = r11.obtainStyledAttributes(r2, r0, r1, r3)
            int r0 = r8.mLayoutResId
            int r0 = r11.getResourceId(r3, r0)
            r8.mLayoutResId = r0
            r0 = 1
            android.graphics.drawable.Drawable r1 = r11.getDrawable(r0)
            r2 = 2
            r4 = -1
            int r2 = r11.getDimensionPixelSize(r2, r4)
            r5 = 3
            boolean r5 = r11.getBoolean(r5, r0)
            r11.recycle()
            android.view.ContextThemeWrapper r11 = r8.mStyledContext
            android.view.LayoutInflater r9 = r9.cloneInContext(r11)
            int r11 = r8.mLayoutResId
            android.view.View r10 = r9.inflate(r11, r10, r3)
            r11 = 16908351(0x102003f, float:2.3877406E-38)
            android.view.View r11 = r10.findViewById(r11)
            boolean r6 = r11 instanceof android.view.ViewGroup
            if (r6 == 0) goto L_0x00fb
            android.view.ViewGroup r11 = (android.view.ViewGroup) r11
            android.view.ContextThemeWrapper r6 = r8.mStyledContext
            android.content.pm.PackageManager r6 = r6.getPackageManager()
            java.lang.String r7 = "android.hardware.type.automotive"
            boolean r6 = r6.hasSystemFeature(r7)
            if (r6 == 0) goto L_0x0062
            r6 = 2131428677(0x7f0b0545, float:1.8479005E38)
            android.view.View r6 = r11.findViewById(r6)
            androidx.recyclerview.widget.RecyclerView r6 = (androidx.recyclerview.widget.RecyclerView) r6
            if (r6 == 0) goto L_0x0062
            goto L_0x0081
        L_0x0062:
            r6 = 2131624400(0x7f0e01d0, float:1.8875979E38)
            android.view.View r9 = r9.inflate(r6, r11, r3)
            r6 = r9
            androidx.recyclerview.widget.RecyclerView r6 = (androidx.recyclerview.widget.RecyclerView) r6
            androidx.recyclerview.widget.LinearLayoutManager r9 = new androidx.recyclerview.widget.LinearLayoutManager
            r8.getActivity()
            r9.<init>(r0)
            r6.setLayoutManager(r9)
            androidx.preference.PreferenceRecyclerViewAccessibilityDelegate r9 = new androidx.preference.PreferenceRecyclerViewAccessibilityDelegate
            r9.<init>(r6)
            r6.mAccessibilityDelegate = r9
            androidx.core.view.ViewCompat.setAccessibilityDelegate(r6, r9)
        L_0x0081:
            r8.mList = r6
            androidx.preference.PreferenceFragment$DividerDecoration r9 = r8.mDividerDecoration
            r6.addItemDecoration(r9)
            androidx.preference.PreferenceFragment$DividerDecoration r9 = r8.mDividerDecoration
            if (r1 == 0) goto L_0x0096
            java.util.Objects.requireNonNull(r9)
            int r0 = r1.getIntrinsicHeight()
            r9.mDividerHeight = r0
            goto L_0x0098
        L_0x0096:
            r9.mDividerHeight = r3
        L_0x0098:
            r9.mDivider = r1
            androidx.preference.PreferenceFragment r9 = androidx.preference.PreferenceFragment.this
            androidx.recyclerview.widget.RecyclerView r9 = r9.mList
            java.util.Objects.requireNonNull(r9)
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ItemDecoration> r0 = r9.mItemDecorations
            int r0 = r0.size()
            java.lang.String r1 = "Cannot invalidate item decorations during a scroll or layout"
            if (r0 != 0) goto L_0x00ac
            goto L_0x00b9
        L_0x00ac:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r9.mLayout
            if (r0 == 0) goto L_0x00b3
            r0.assertNotInLayoutOrScroll(r1)
        L_0x00b3:
            r9.markItemDecorInsetsDirty()
            r9.requestLayout()
        L_0x00b9:
            if (r2 == r4) goto L_0x00df
            androidx.preference.PreferenceFragment$DividerDecoration r9 = r8.mDividerDecoration
            java.util.Objects.requireNonNull(r9)
            r9.mDividerHeight = r2
            androidx.preference.PreferenceFragment r9 = androidx.preference.PreferenceFragment.this
            androidx.recyclerview.widget.RecyclerView r9 = r9.mList
            java.util.Objects.requireNonNull(r9)
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ItemDecoration> r0 = r9.mItemDecorations
            int r0 = r0.size()
            if (r0 != 0) goto L_0x00d2
            goto L_0x00df
        L_0x00d2:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r9.mLayout
            if (r0 == 0) goto L_0x00d9
            r0.assertNotInLayoutOrScroll(r1)
        L_0x00d9:
            r9.markItemDecorInsetsDirty()
            r9.requestLayout()
        L_0x00df:
            androidx.preference.PreferenceFragment$DividerDecoration r9 = r8.mDividerDecoration
            java.util.Objects.requireNonNull(r9)
            r9.mAllowDividerAfterLastItem = r5
            androidx.recyclerview.widget.RecyclerView r9 = r8.mList
            android.view.ViewParent r9 = r9.getParent()
            if (r9 != 0) goto L_0x00f3
            androidx.recyclerview.widget.RecyclerView r9 = r8.mList
            r11.addView(r9)
        L_0x00f3:
            androidx.preference.PreferenceFragment$1 r9 = r8.mHandler
            androidx.preference.PreferenceFragment$2 r8 = r8.mRequestFocus
            r9.post(r8)
            return r10
        L_0x00fb:
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.String r9 = "Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceFragment.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle):android.view.View");
    }

    public final void onDestroyView() {
        PreferenceScreen preferenceScreen;
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mHandler.removeMessages(1);
        if (this.mHavePrefs && (preferenceScreen = getPreferenceScreen()) != null) {
            preferenceScreen.onDetached();
        }
        this.mList = null;
        super.onDestroyView();
    }

    @Deprecated
    public final void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        boolean z;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        PreferenceScreen preferenceScreen2 = preferenceManager.mPreferenceScreen;
        if (preferenceScreen != preferenceScreen2) {
            if (preferenceScreen2 != null) {
                preferenceScreen2.onDetached();
            }
            preferenceManager.mPreferenceScreen = preferenceScreen;
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.mHavePrefs = true;
            if (this.mInitDone && !this.mHandler.hasMessages(1)) {
                this.mHandler.obtainMessage(1).sendToTarget();
            }
        }
    }

    public void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(C1777R.attr.preferenceTheme, typedValue, true);
        int i = typedValue.resourceId;
        if (i == 0) {
            i = C1777R.style.PreferenceThemeOverlay;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), i);
        this.mStyledContext = contextThemeWrapper;
        PreferenceManager preferenceManager = new PreferenceManager(contextThemeWrapper);
        this.mPreferenceManager = preferenceManager;
        preferenceManager.mOnNavigateToScreenListener = this;
        if (getArguments() != null) {
            str = getArguments().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
        } else {
            str = null;
        }
        onCreatePreferences(str);
    }

    @Deprecated
    public void onDisplayPreferenceDialog(Preference preference) {
        boolean z;
        DialogFragment dialogFragment;
        if (getActivity() instanceof OnPreferenceDisplayDialogCallback) {
            z = ((OnPreferenceDisplayDialogCallback) getActivity()).onPreferenceDisplayDialog();
        } else {
            z = false;
        }
        if (!z && getFragmentManager().findFragmentByTag("androidx.preference.PreferenceFragment.DIALOG") == null) {
            if (preference instanceof EditTextPreference) {
                Objects.requireNonNull(preference);
                String str = preference.mKey;
                dialogFragment = new EditTextPreferenceDialogFragment();
                Bundle bundle = new Bundle(1);
                bundle.putString("key", str);
                dialogFragment.setArguments(bundle);
            } else if (preference instanceof ListPreference) {
                Objects.requireNonNull(preference);
                String str2 = preference.mKey;
                dialogFragment = new ListPreferenceDialogFragment();
                Bundle bundle2 = new Bundle(1);
                bundle2.putString("key", str2);
                dialogFragment.setArguments(bundle2);
            } else if (preference instanceof MultiSelectListPreference) {
                Objects.requireNonNull(preference);
                String str3 = preference.mKey;
                dialogFragment = new MultiSelectListPreferenceDialogFragment();
                Bundle bundle3 = new Bundle(1);
                bundle3.putString("key", str3);
                dialogFragment.setArguments(bundle3);
            } else {
                throw new IllegalArgumentException("Tried to display dialog for unknown preference type. Did you forget to override onDisplayPreferenceDialog()?");
            }
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getFragmentManager(), "androidx.preference.PreferenceFragment.DIALOG");
        }
    }

    @Deprecated
    public boolean onPreferenceTreeClick(Preference preference) {
        Objects.requireNonNull(preference);
        if (preference.mFragment == null || !(getActivity() instanceof OnPreferenceStartFragmentCallback)) {
            return false;
        }
        return ((OnPreferenceStartFragmentCallback) getActivity()).onPreferenceStartFragment(preference);
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.dispatchSaveInstanceState(bundle2);
            bundle.putBundle("android:preferences", bundle2);
        }
    }

    public final void onStart() {
        super.onStart();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        preferenceManager.mOnPreferenceTreeClickListener = this;
        PreferenceManager preferenceManager2 = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager2);
        preferenceManager2.mOnDisplayPreferenceDialogListener = this;
    }

    public final void onStop() {
        super.onStop();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        preferenceManager.mOnPreferenceTreeClickListener = null;
        PreferenceManager preferenceManager2 = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager2);
        preferenceManager2.mOnDisplayPreferenceDialogListener = null;
    }

    public final void onViewCreated(View view, Bundle bundle) {
        PreferenceScreen preferenceScreen;
        Bundle bundle2;
        PreferenceScreen preferenceScreen2;
        super.onViewCreated(view, bundle);
        if (!(bundle == null || (bundle2 = bundle.getBundle("android:preferences")) == null || (preferenceScreen2 = getPreferenceScreen()) == null)) {
            preferenceScreen2.dispatchRestoreInstanceState(bundle2);
        }
        if (this.mHavePrefs && (preferenceScreen = getPreferenceScreen()) != null) {
            this.mList.setAdapter(new PreferenceGroupAdapter(preferenceScreen));
            preferenceScreen.onAttached();
        }
        this.mInitDone = true;
    }
}
