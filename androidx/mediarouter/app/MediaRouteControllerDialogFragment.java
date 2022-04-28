package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouteSelector;

public class MediaRouteControllerDialogFragment extends DialogFragment {
    public AppCompatDialog mDialog;
    public MediaRouteSelector mSelector;
    public boolean mUseDynamicGroup = false;

    public final void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
        AppCompatDialog appCompatDialog = this.mDialog;
        if (appCompatDialog == null) {
            return;
        }
        if (this.mUseDynamicGroup) {
            ((MediaRouteDynamicControllerDialog) appCompatDialog).updateLayout();
        } else {
            ((MediaRouteControllerDialog) appCompatDialog).updateLayout();
        }
    }

    public final Dialog onCreateDialog() {
        if (this.mUseDynamicGroup) {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = new MediaRouteDynamicControllerDialog(getContext());
            this.mDialog = mediaRouteDynamicControllerDialog;
            mediaRouteDynamicControllerDialog.setRouteSelector(this.mSelector);
        } else {
            this.mDialog = new MediaRouteControllerDialog(getContext());
        }
        return this.mDialog;
    }

    public MediaRouteControllerDialogFragment() {
        this.mCancelable = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(true);
        }
    }

    public final void onStop() {
        super.onStop();
        AppCompatDialog appCompatDialog = this.mDialog;
        if (appCompatDialog != null && !this.mUseDynamicGroup) {
            ((MediaRouteControllerDialog) appCompatDialog).clearGroupListAnimation(false);
        }
    }
}
