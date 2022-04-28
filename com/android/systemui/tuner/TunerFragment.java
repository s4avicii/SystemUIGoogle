package com.android.systemui.tuner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;

public class TunerFragment extends PreferenceFragment {
    public static final String[] DEBUG_ONLY = {"nav_bar", "lockscreen", "picture_in_picture"};
    public final TunerService mTunerService;

    public static class TunerWarningFragment extends DialogFragment {
        public final Dialog onCreateDialog(Bundle bundle) {
            return new AlertDialog.Builder(getContext()).setTitle(C1777R.string.tuner_warning_title).setMessage(C1777R.string.tuner_warning).setPositiveButton(C1777R.string.got_it, new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Settings.Secure.putInt(TunerWarningFragment.this.getContext().getContentResolver(), "seen_tuner_warning", 1);
                }
            }).show();
        }
    }

    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 2, 0, C1777R.string.remove_from_settings);
    }

    @SuppressLint({"ValidFragment"})
    public TunerFragment(TunerService tunerService) {
        this.mTunerService = tunerService;
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public final void onCreatePreferences(String str) {
        addPreferencesFromResource(C1777R.xml.tuner_prefs);
        if (!getContext().getSharedPreferences("plugin_prefs", 0).getBoolean("plugins", false)) {
            getPreferenceScreen().removePreference(findPreference("plugins"));
        }
        if (!new AmbientDisplayConfiguration(getContext()).alwaysOnAvailable()) {
            getPreferenceScreen().removePreference(findPreference("doze"));
        }
        if (!Build.IS_DEBUGGABLE) {
            int i = 0;
            while (true) {
                String[] strArr = DEBUG_ONLY;
                if (i >= 3) {
                    break;
                }
                Preference findPreference = findPreference(strArr[i]);
                if (findPreference != null) {
                    getPreferenceScreen().removePreference(findPreference);
                }
                i++;
            }
        }
        if (Settings.Secure.getInt(getContext().getContentResolver(), "seen_tuner_warning", 0) == 0 && getFragmentManager().findFragmentByTag("tuner_warning") == null) {
            new TunerWarningFragment().show(getFragmentManager(), "tuner_warning");
        }
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 2) {
            this.mTunerService.showResetRequest(new CarrierTextManager$$ExternalSyntheticLambda0(this, 7));
            return true;
        } else if (itemId != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            getActivity().finish();
            return true;
        }
    }

    public final void onPause() {
        super.onPause();
        MetricsLogger.visibility(getContext(), 227, false);
    }

    public final void onResume() {
        super.onResume();
        getActivity().setTitle(C1777R.string.system_ui_tuner);
        MetricsLogger.visibility(getContext(), 227, true);
    }
}
