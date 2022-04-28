package androidx.core.widget;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.Objects;

public final class TextViewCompat {

    public static class OreoCallback implements ActionMode.Callback {
        public final ActionMode.Callback mCallback;
        public boolean mCanUseMenuBuilderReferences;
        public boolean mInitializedMenuBuilderReferences;
        public Class<?> mMenuBuilderClass;
        public Method mMenuBuilderRemoveItemAtMethod;
        public final TextView mTextView;

        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mCallback.onActionItemClicked(actionMode, menuItem);
        }

        public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mCallback.onCreateActionMode(actionMode, menu);
        }

        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mCallback.onDestroyActionMode(actionMode);
        }

        /* JADX WARNING: Removed duplicated region for block: B:41:0x00d3  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00a1 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onPrepareActionMode(android.view.ActionMode r13, android.view.Menu r14) {
            /*
                r12 = this;
                android.widget.TextView r0 = r12.mTextView
                android.content.Context r0 = r0.getContext()
                android.content.pm.PackageManager r1 = r0.getPackageManager()
                boolean r2 = r12.mInitializedMenuBuilderReferences
                r3 = 1
                java.lang.String r4 = "removeItemAt"
                r5 = 0
                if (r2 != 0) goto L_0x0033
                r12.mInitializedMenuBuilderReferences = r3
                java.lang.String r2 = "com.android.internal.view.menu.MenuBuilder"
                java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                r12.mMenuBuilderClass = r2     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                java.lang.Class[] r6 = new java.lang.Class[r3]     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                r6[r5] = r7     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                java.lang.reflect.Method r2 = r2.getDeclaredMethod(r4, r6)     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                r12.mMenuBuilderRemoveItemAtMethod = r2     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                r12.mCanUseMenuBuilderReferences = r3     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002c }
                goto L_0x0033
            L_0x002c:
                r2 = 0
                r12.mMenuBuilderClass = r2
                r12.mMenuBuilderRemoveItemAtMethod = r2
                r12.mCanUseMenuBuilderReferences = r5
            L_0x0033:
                boolean r2 = r12.mCanUseMenuBuilderReferences     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                if (r2 == 0) goto L_0x0042
                java.lang.Class<?> r2 = r12.mMenuBuilderClass     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                boolean r2 = r2.isInstance(r14)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                if (r2 == 0) goto L_0x0042
                java.lang.reflect.Method r2 = r12.mMenuBuilderRemoveItemAtMethod     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                goto L_0x0050
            L_0x0042:
                java.lang.Class r2 = r14.getClass()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                java.lang.Class[] r6 = new java.lang.Class[r3]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                r6[r5] = r7     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                java.lang.reflect.Method r2 = r2.getDeclaredMethod(r4, r6)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
            L_0x0050:
                int r4 = r14.size()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                int r4 = r4 - r3
            L_0x0055:
                java.lang.String r6 = "android.intent.action.PROCESS_TEXT"
                if (r4 < 0) goto L_0x007f
                android.view.MenuItem r7 = r14.getItem(r4)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                android.content.Intent r8 = r7.getIntent()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                if (r8 == 0) goto L_0x007c
                android.content.Intent r7 = r7.getIntent()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                java.lang.String r7 = r7.getAction()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                boolean r6 = r6.equals(r7)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                if (r6 == 0) goto L_0x007c
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r4)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                r6[r5] = r7     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
                r2.invoke(r14, r6)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x012b }
            L_0x007c:
                int r4 = r4 + -1
                goto L_0x0055
            L_0x007f:
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                boolean r4 = r0 instanceof android.app.Activity
                java.lang.String r7 = "text/plain"
                if (r4 != 0) goto L_0x008c
                goto L_0x00d7
            L_0x008c:
                android.content.Intent r4 = new android.content.Intent
                r4.<init>()
                android.content.Intent r4 = r4.setAction(r6)
                android.content.Intent r4 = r4.setType(r7)
                java.util.List r4 = r1.queryIntentActivities(r4, r5)
                java.util.Iterator r4 = r4.iterator()
            L_0x00a1:
                boolean r8 = r4.hasNext()
                if (r8 == 0) goto L_0x00d7
                java.lang.Object r8 = r4.next()
                android.content.pm.ResolveInfo r8 = (android.content.pm.ResolveInfo) r8
                java.lang.String r9 = r0.getPackageName()
                android.content.pm.ActivityInfo r10 = r8.activityInfo
                java.lang.String r10 = r10.packageName
                boolean r9 = r9.equals(r10)
                if (r9 == 0) goto L_0x00bc
                goto L_0x00d0
            L_0x00bc:
                android.content.pm.ActivityInfo r9 = r8.activityInfo
                boolean r10 = r9.exported
                if (r10 != 0) goto L_0x00c3
                goto L_0x00ce
            L_0x00c3:
                java.lang.String r9 = r9.permission
                if (r9 == 0) goto L_0x00d0
                int r9 = r0.checkSelfPermission(r9)
                if (r9 != 0) goto L_0x00ce
                goto L_0x00d0
            L_0x00ce:
                r9 = r5
                goto L_0x00d1
            L_0x00d0:
                r9 = r3
            L_0x00d1:
                if (r9 == 0) goto L_0x00a1
                r2.add(r8)
                goto L_0x00a1
            L_0x00d7:
                r0 = r5
            L_0x00d8:
                int r4 = r2.size()
                if (r0 >= r4) goto L_0x012b
                java.lang.Object r4 = r2.get(r0)
                android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
                int r8 = r0 + 100
                java.lang.CharSequence r9 = r4.loadLabel(r1)
                android.view.MenuItem r8 = r14.add(r5, r5, r8, r9)
                android.widget.TextView r9 = r12.mTextView
                android.content.Intent r10 = new android.content.Intent
                r10.<init>()
                android.content.Intent r10 = r10.setAction(r6)
                android.content.Intent r10 = r10.setType(r7)
                boolean r11 = r9 instanceof android.text.Editable
                if (r11 == 0) goto L_0x010f
                boolean r11 = r9.onCheckIsTextEditor()
                if (r11 == 0) goto L_0x010f
                boolean r9 = r9.isEnabled()
                if (r9 == 0) goto L_0x010f
                r9 = r3
                goto L_0x0110
            L_0x010f:
                r9 = r5
            L_0x0110:
                r9 = r9 ^ r3
                java.lang.String r11 = "android.intent.extra.PROCESS_TEXT_READONLY"
                android.content.Intent r9 = r10.putExtra(r11, r9)
                android.content.pm.ActivityInfo r4 = r4.activityInfo
                java.lang.String r10 = r4.packageName
                java.lang.String r4 = r4.name
                android.content.Intent r4 = r9.setClassName(r10, r4)
                android.view.MenuItem r4 = r8.setIntent(r4)
                r4.setShowAsAction(r3)
                int r0 = r0 + 1
                goto L_0x00d8
            L_0x012b:
                android.view.ActionMode$Callback r12 = r12.mCallback
                boolean r12 = r12.onPrepareActionMode(r13, r14)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.TextViewCompat.OreoCallback.onPrepareActionMode(android.view.ActionMode, android.view.Menu):boolean");
        }
    }

    public static ActionMode.Callback unwrapCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        if (!(callback instanceof OreoCallback)) {
            return callback;
        }
        OreoCallback oreoCallback = (OreoCallback) callback;
        Objects.requireNonNull(oreoCallback);
        return oreoCallback.mCallback;
    }
}
