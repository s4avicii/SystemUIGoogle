package androidx.appcompat.app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArraySet;
import java.lang.ref.WeakReference;

public class AppCompatDialog extends Dialog implements AppCompatCallback {
    public AppCompatDelegateImpl mDelegate;
    public final C00381 mKeyDispatcher;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AppCompatDialog(android.content.Context r5, int r6) {
        /*
            r4 = this;
            r0 = 1
            r1 = 2130968948(0x7f040174, float:1.7546564E38)
            if (r6 != 0) goto L_0x0015
            android.util.TypedValue r2 = new android.util.TypedValue
            r2.<init>()
            android.content.res.Resources$Theme r3 = r5.getTheme()
            r3.resolveAttribute(r1, r2, r0)
            int r2 = r2.resourceId
            goto L_0x0016
        L_0x0015:
            r2 = r6
        L_0x0016:
            r4.<init>(r5, r2)
            androidx.appcompat.app.AppCompatDialog$1 r2 = new androidx.appcompat.app.AppCompatDialog$1
            r2.<init>()
            r4.mKeyDispatcher = r2
            androidx.appcompat.app.AppCompatDelegate r4 = r4.getDelegate()
            if (r6 != 0) goto L_0x0034
            android.util.TypedValue r6 = new android.util.TypedValue
            r6.<init>()
            android.content.res.Resources$Theme r5 = r5.getTheme()
            r5.resolveAttribute(r1, r6, r0)
            int r6 = r6.resourceId
        L_0x0034:
            r5 = r4
            androidx.appcompat.app.AppCompatDelegateImpl r5 = (androidx.appcompat.app.AppCompatDelegateImpl) r5
            java.util.Objects.requireNonNull(r5)
            r5.mThemeResId = r6
            r4.onCreate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDialog.<init>(android.content.Context, int):void");
    }

    public final void onSupportActionModeFinished() {
    }

    public final void onSupportActionModeStarted() {
    }

    public final void onWindowStartingSupportActionMode() {
    }

    public final void setContentView(int i) {
        getDelegate().setContentView(i);
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        getDelegate().setTitle(charSequence);
    }

    public final AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            ArraySet<WeakReference<AppCompatDelegate>> arraySet = AppCompatDelegate.sActivityDelegates;
            this.mDelegate = new AppCompatDelegateImpl(getContext(), getWindow(), this, this);
        }
        return this.mDelegate;
    }

    public final void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getDelegate().addContentView(view, layoutParams);
    }

    public final void dismiss() {
        super.dismiss();
        getDelegate().onDestroy();
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        getWindow().getDecorView();
        C00381 r1 = this.mKeyDispatcher;
        if (r1 == null) {
            return false;
        }
        return r1.superDispatchKeyEvent(keyEvent);
    }

    public final <T extends View> T findViewById(int i) {
        return getDelegate().findViewById(i);
    }

    public final void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    public void onCreate(Bundle bundle) {
        getDelegate().installViewFactory();
        super.onCreate(bundle);
        getDelegate().onCreate();
    }

    public final void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getDelegate().setContentView(view, layoutParams);
    }

    public void setTitle(int i) {
        super.setTitle(i);
        getDelegate().setTitle(getContext().getString(i));
    }

    public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }
}
