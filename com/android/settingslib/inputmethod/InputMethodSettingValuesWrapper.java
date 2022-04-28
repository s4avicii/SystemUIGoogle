package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.annotations.GuardedBy;
import java.util.ArrayList;

public final class InputMethodSettingValuesWrapper {
    @GuardedBy({"sInstanceMapLock"})
    public static SparseArray<InputMethodSettingValuesWrapper> sInstanceMap = new SparseArray<>();
    public static final Object sInstanceMapLock = new Object();

    public InputMethodSettingValuesWrapper(Context context) {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = context.getContentResolver();
        arrayList.clear();
        arrayList.addAll(((InputMethodManager) context.getSystemService(InputMethodManager.class)).getInputMethodListAsUser(contentResolver.getUserId(), 1));
    }
}
