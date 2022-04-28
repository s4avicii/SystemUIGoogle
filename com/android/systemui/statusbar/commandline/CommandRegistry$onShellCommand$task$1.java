package com.android.systemui.statusbar.commandline;

import androidx.exifinterface.media.C0155xe8491b12;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.collections.ArrayAsCollection;
import kotlin.collections.EmptyList;

/* compiled from: CommandRegistry.kt */
public final class CommandRegistry$onShellCommand$task$1<V> implements Callable {
    public final /* synthetic */ String[] $args;
    public final /* synthetic */ Command $command;
    public final /* synthetic */ PrintWriter $pw;

    public CommandRegistry$onShellCommand$task$1(Command command, PrintWriter printWriter, String[] strArr) {
        this.$command = command;
        this.$pw = printWriter;
        this.$args = strArr;
    }

    public final Object call() {
        boolean z;
        List list;
        Command command = this.$command;
        PrintWriter printWriter = this.$pw;
        String[] strArr = this.$args;
        int length = strArr.length - 1;
        if (length < 0) {
            length = 0;
        }
        if (length >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (length == 0) {
                list = EmptyList.INSTANCE;
            } else {
                int length2 = strArr.length;
                if (length >= length2) {
                    int length3 = strArr.length;
                    if (length3 == 0) {
                        list = EmptyList.INSTANCE;
                    } else if (length3 != 1) {
                        list = new ArrayList(new ArrayAsCollection(strArr, false));
                    } else {
                        list = Collections.singletonList(strArr[0]);
                    }
                } else if (length == 1) {
                    list = Collections.singletonList(strArr[length2 - 1]);
                } else {
                    ArrayList arrayList = new ArrayList(length);
                    for (int i = length2 - length; i < length2; i++) {
                        arrayList.add(strArr[i]);
                    }
                    list = arrayList;
                }
            }
            command.execute(printWriter, list);
            return Unit.INSTANCE;
        }
        throw new IllegalArgumentException(C0155xe8491b12.m16m("Requested element count ", length, " is less than zero.").toString());
    }
}
