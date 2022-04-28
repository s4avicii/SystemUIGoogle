package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouteSelector;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;

public class MediaRouteChooserDialogFragment extends DialogFragment {
    public AppCompatDialog mDialog;
    public MediaRouteSelector mSelector;
    public boolean mUseDynamicGroup = false;

    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        this.mCalled = true;
        AppCompatDialog appCompatDialog = this.mDialog;
        if (appCompatDialog != null) {
            int i2 = -2;
            if (this.mUseDynamicGroup) {
                MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = (MediaRouteDynamicChooserDialog) appCompatDialog;
                Context context = mediaRouteDynamicChooserDialog.mContext;
                if (!context.getResources().getBoolean(C1777R.bool.is_tablet)) {
                    i = -1;
                } else {
                    i = MediaRouteDialogHelper.getDialogWidth(context);
                }
                if (!mediaRouteDynamicChooserDialog.mContext.getResources().getBoolean(C1777R.bool.is_tablet)) {
                    i2 = -1;
                }
                mediaRouteDynamicChooserDialog.getWindow().setLayout(i, i2);
                return;
            }
            MediaRouteChooserDialog mediaRouteChooserDialog = (MediaRouteChooserDialog) appCompatDialog;
            mediaRouteChooserDialog.getWindow().setLayout(MediaRouteDialogHelper.getDialogWidth(mediaRouteChooserDialog.getContext()), -2);
        }
    }

    public final void ensureRouteSelector() {
        if (this.mSelector == null) {
            Bundle bundle = this.mArguments;
            if (bundle != null) {
                Bundle bundle2 = bundle.getBundle("selector");
                MediaRouteSelector mediaRouteSelector = MediaRouteSelector.EMPTY;
                MediaRouteSelector mediaRouteSelector2 = null;
                if (bundle2 != null) {
                    mediaRouteSelector2 = new MediaRouteSelector(bundle2, (ArrayList) null);
                }
                this.mSelector = mediaRouteSelector2;
            }
            if (this.mSelector == null) {
                this.mSelector = MediaRouteSelector.EMPTY;
            }
        }
    }

    public final Dialog onCreateDialog() {
        if (this.mUseDynamicGroup) {
            MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = new MediaRouteDynamicChooserDialog(getContext());
            this.mDialog = mediaRouteDynamicChooserDialog;
            ensureRouteSelector();
            mediaRouteDynamicChooserDialog.setRouteSelector(this.mSelector);
        } else {
            MediaRouteChooserDialog mediaRouteChooserDialog = new MediaRouteChooserDialog(getContext());
            this.mDialog = mediaRouteChooserDialog;
            ensureRouteSelector();
            mediaRouteChooserDialog.setRouteSelector(this.mSelector);
        }
        return this.mDialog;
    }

    public MediaRouteChooserDialogFragment() {
        this.mCancelable = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(true);
        }
    }
}
