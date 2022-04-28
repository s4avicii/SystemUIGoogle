package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.IntentButtonProvider;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.statusbar.phone.ExpandableIndicator;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Iterator;

public class LockscreenFragment extends PreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList<TunerService.Tunable> mTunables = new ArrayList<>();
    public TunerService mTunerService;

    public static class Adapter extends RecyclerView.Adapter<Holder> {
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            Holder holder = (Holder) viewHolder;
            throw null;
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            return new Holder(LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.tuner_shortcut_item, recyclerView, false));
        }

        public final int getItemCount() {
            throw null;
        }
    }

    public static class App extends Item {
    }

    public static class LockButtonFactory implements ExtensionController.TunerFactory<IntentButtonProvider.IntentButton> {
        public final Context mContext;
        public final String mKey;

        public final IntentButtonProvider.IntentButton create(ArrayMap arrayMap) {
            ActivityInfo activityInfo;
            String str = (String) arrayMap.get(this.mKey);
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if (str.contains("::")) {
                ShortcutParser.Shortcut shortcutInfo = LockscreenFragment.getShortcutInfo(this.mContext, str);
                if (shortcutInfo != null) {
                    return new ShortcutButton(this.mContext, shortcutInfo);
                }
                return null;
            } else if (!str.contains("/")) {
                return null;
            } else {
                Context context = this.mContext;
                int i = LockscreenFragment.$r8$clinit;
                try {
                    activityInfo = context.getPackageManager().getActivityInfo(ComponentName.unflattenFromString(str), 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    activityInfo = null;
                }
                if (activityInfo != null) {
                    return new ActivityButton(this.mContext, activityInfo);
                }
                return null;
            }
        }

        public LockButtonFactory(Context context, String str) {
            this.mContext = context;
            this.mKey = str;
        }
    }

    public static class ShortcutButton implements IntentButtonProvider.IntentButton {
        public final IntentButtonProvider.IntentButton.IconState mIconState;
        public final ShortcutParser.Shortcut mShortcut;

        public final Intent getIntent() {
            return this.mShortcut.intent;
        }

        public ShortcutButton(Context context, ShortcutParser.Shortcut shortcut) {
            this.mShortcut = shortcut;
            IntentButtonProvider.IntentButton.IconState iconState = new IntentButtonProvider.IntentButton.IconState();
            this.mIconState = iconState;
            iconState.isVisible = true;
            iconState.drawable = shortcut.icon.loadDrawable(context).mutate();
            iconState.contentDescription = shortcut.label;
            Drawable drawable = iconState.drawable;
            iconState.drawable = new ScalingDrawableWrapper(drawable, ((float) ((int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics()))) / ((float) drawable.getIntrinsicWidth()));
            iconState.tint = false;
        }

        public final IntentButtonProvider.IntentButton.IconState getIcon() {
            return this.mIconState;
        }
    }

    public static class StaticShortcut extends Item {
    }

    public static class ActivityButton implements IntentButtonProvider.IntentButton {
        public final IntentButtonProvider.IntentButton.IconState mIconState;
        public final Intent mIntent;

        public ActivityButton(Context context, ActivityInfo activityInfo) {
            this.mIntent = new Intent().setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
            IntentButtonProvider.IntentButton.IconState iconState = new IntentButtonProvider.IntentButton.IconState();
            this.mIconState = iconState;
            iconState.isVisible = true;
            iconState.drawable = activityInfo.loadIcon(context.getPackageManager()).mutate();
            iconState.contentDescription = activityInfo.loadLabel(context.getPackageManager());
            Drawable drawable = iconState.drawable;
            iconState.drawable = new ScalingDrawableWrapper(drawable, ((float) ((int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics()))) / ((float) drawable.getIntrinsicWidth()));
            iconState.tint = false;
        }

        public final IntentButtonProvider.IntentButton.IconState getIcon() {
            return this.mIconState;
        }

        public final Intent getIntent() {
            return this.mIntent;
        }
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public Holder(View view) {
            super(view);
            ImageView imageView = (ImageView) view.findViewById(16908294);
            TextView textView = (TextView) view.findViewById(16908310);
            ExpandableIndicator expandableIndicator = (ExpandableIndicator) view.findViewById(C1777R.C1779id.expand);
        }
    }

    public static ShortcutParser.Shortcut getShortcutInfo(Context context, String str) {
        String[] split = str.split("::");
        try {
            Iterator it = new ShortcutParser(context, new ComponentName(split[0], split[1])).getShortcuts().iterator();
            while (it.hasNext()) {
                ShortcutParser.Shortcut shortcut = (ShortcutParser.Shortcut) it.next();
                if (shortcut.f82id.equals(split[2])) {
                    return shortcut;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return null;
    }

    public final void onCreatePreferences(String str) {
        this.mTunerService = (TunerService) Dependency.get(TunerService.class);
        new Handler();
        addPreferencesFromResource(C1777R.xml.lockscreen_settings);
        setupGroup("sysui_keyguard_left", "sysui_keyguard_left_unlock");
        setupGroup("sysui_keyguard_right", "sysui_keyguard_right_unlock");
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(new LockscreenFragment$$ExternalSyntheticLambda1(this, 0));
    }

    public final void setupGroup(String str, String str2) {
        LockscreenFragment$$ExternalSyntheticLambda0 lockscreenFragment$$ExternalSyntheticLambda0 = new LockscreenFragment$$ExternalSyntheticLambda0(this, (SwitchPreference) findPreference(str2), findPreference(str));
        this.mTunables.add(lockscreenFragment$$ExternalSyntheticLambda0);
        this.mTunerService.addTunable(lockscreenFragment$$ExternalSyntheticLambda0, str);
    }

    public static abstract class Item {
        private Item() {
        }
    }
}
