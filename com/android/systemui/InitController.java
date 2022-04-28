package com.android.systemui;

import java.util.ArrayList;

public final class InitController {
    public final ArrayList<Runnable> mTasks = new ArrayList<>();
    public boolean mTasksExecuted = false;
}
