package com.android.systemui.tuner;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;

public class PowerNotificationControlsFragment extends Fragment {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final boolean isEnabled() {
        if (Settings.Secure.getInt(getContext().getContentResolver(), "show_importance_slider", 0) == 1) {
            return true;
        }
        return false;
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1777R.layout.power_notification_controls_settings, viewGroup, false);
    }

    public final void onPause() {
        super.onPause();
        MetricsLogger.visibility(getContext(), 392, false);
    }

    public final void onResume() {
        super.onResume();
        MetricsLogger.visibility(getContext(), 392, true);
    }

    public final void onViewCreated(View view, Bundle bundle) {
        String str;
        super.onViewCreated(view, bundle);
        View findViewById = view.findViewById(C1777R.C1779id.switch_bar);
        final Switch switchR = (Switch) findViewById.findViewById(16908352);
        final TextView textView = (TextView) findViewById.findViewById(C1777R.C1779id.switch_text);
        switchR.setChecked(isEnabled());
        if (isEnabled()) {
            str = getString(C1777R.string.switch_bar_on);
        } else {
            str = getString(C1777R.string.switch_bar_off);
        }
        textView.setText(str);
        switchR.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String str;
                PowerNotificationControlsFragment powerNotificationControlsFragment = PowerNotificationControlsFragment.this;
                int i = PowerNotificationControlsFragment.$r8$clinit;
                boolean z = !powerNotificationControlsFragment.isEnabled();
                MetricsLogger.action(PowerNotificationControlsFragment.this.getContext(), 393, z);
                Settings.Secure.putInt(PowerNotificationControlsFragment.this.getContext().getContentResolver(), "show_importance_slider", z ? 1 : 0);
                switchR.setChecked(z);
                TextView textView = textView;
                if (z) {
                    str = PowerNotificationControlsFragment.this.getString(C1777R.string.switch_bar_on);
                } else {
                    str = PowerNotificationControlsFragment.this.getString(C1777R.string.switch_bar_off);
                }
                textView.setText(str);
            }
        });
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }
}
