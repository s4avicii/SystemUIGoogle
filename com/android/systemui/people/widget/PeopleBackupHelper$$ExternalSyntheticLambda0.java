package com.android.systemui.people.widget;

import android.content.ContentProvider;
import android.content.SharedPreferences;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.text.TextUtils;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleBackupHelper$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ PeopleBackupHelper f$0;
    public final /* synthetic */ SharedPreferences.Editor f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ PeopleBackupHelper$$ExternalSyntheticLambda0(PeopleBackupHelper peopleBackupHelper, SharedPreferences.Editor editor, ArrayList arrayList) {
        this.f$0 = peopleBackupHelper;
        this.f$1 = editor;
        this.f$2 = arrayList;
    }

    public final void accept(Object obj) {
        PeopleBackupHelper peopleBackupHelper = this.f$0;
        SharedPreferences.Editor editor = this.f$1;
        List list = this.f$2;
        Map.Entry entry = (Map.Entry) obj;
        Objects.requireNonNull(peopleBackupHelper);
        String str = (String) entry.getKey();
        if (!TextUtils.isEmpty(str)) {
            int ordinal = PeopleBackupHelper.getEntryType(entry).ordinal();
            if (ordinal == 1) {
                String valueOf = String.valueOf(entry.getValue());
                if (list.contains(str)) {
                    Uri parse = Uri.parse(valueOf);
                    if (ContentProvider.uriHasUserId(parse)) {
                        int userIdFromUri = ContentProvider.getUserIdFromUri(parse);
                        editor.putInt("add_user_id_to_uri_" + str, userIdFromUri);
                        parse = ContentProvider.getUriWithoutUserId(parse);
                    }
                    editor.putString(str, parse.toString());
                }
            } else if (ordinal == 2) {
                Set set = (Set) entry.getValue();
                PeopleTileKey fromString = PeopleTileKey.fromString(str);
                Objects.requireNonNull(fromString);
                if (fromString.mUserId == peopleBackupHelper.mUserHandle.getIdentifier()) {
                    Set set2 = (Set) set.stream().filter(new PeopleBackupHelper$$ExternalSyntheticLambda1(list, 0)).collect(Collectors.toSet());
                    if (!set2.isEmpty()) {
                        fromString.mUserId = -1;
                        editor.putStringSet(fromString.toString(), set2);
                    }
                }
            } else if (ordinal != 3) {
                MotionLayout$$ExternalSyntheticOutline0.m9m("Key not identified, skipping: ", str, "PeopleBackupHelper");
            } else {
                Set set3 = (Set) entry.getValue();
                Uri parse2 = Uri.parse(String.valueOf(str));
                if (ContentProvider.uriHasUserId(parse2)) {
                    int userIdFromUri2 = ContentProvider.getUserIdFromUri(parse2);
                    if (userIdFromUri2 == peopleBackupHelper.mUserHandle.getIdentifier()) {
                        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(parse2);
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("add_user_id_to_uri_");
                        m.append(uriWithoutUserId.toString());
                        editor.putInt(m.toString(), userIdFromUri2);
                        editor.putStringSet(uriWithoutUserId.toString(), set3);
                    }
                } else if (peopleBackupHelper.mUserHandle.isSystem()) {
                    editor.putStringSet(parse2.toString(), set3);
                }
            }
        }
    }
}
