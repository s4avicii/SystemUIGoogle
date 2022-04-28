package com.android.systemui.p006qs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.external.CustomTile;
import com.android.systemui.p006qs.external.CustomTileStatePersister;
import com.android.systemui.p006qs.external.TileLifecycleManager;
import com.android.systemui.p006qs.external.TileRequestDialogEventLogger;
import com.android.systemui.p006qs.external.TileServiceRequestController;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.p005qs.QSFactory;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.settings.SecureSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSTileHost */
public final class QSTileHost implements QSHost, TunerService.Tunable, PluginListener<QSFactory>, Dumpable {
    public static final boolean DEBUG = Log.isLoggable("QSTileHost", 3);
    public AutoTileManager mAutoTiles;
    public final ArrayList mCallbacks = new ArrayList();
    public final Context mContext;
    public int mCurrentUser;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public final StatusBarIconController mIconController;
    public final InstanceIdSequence mInstanceIdSequence;
    public final PluginManager mPluginManager;
    public final QSLogger mQSLogger;
    public final ArrayList<QSFactory> mQsFactories;
    public SecureSettings mSecureSettings;
    public final Optional<StatusBar> mStatusBarOptional;
    public TileLifecycleManager.Factory mTileLifeCycleManagerFactory;
    public final TileServiceRequestController mTileServiceRequestController;
    public final ArrayList<String> mTileSpecs = new ArrayList<>();
    public final LinkedHashMap<String, QSTile> mTiles = new LinkedHashMap<>();
    public final TunerService mTunerService;
    public final UiEventLogger mUiEventLogger;
    public Context mUserContext;
    public UserTracker mUserTracker;

    public QSTileHost(Context context, StatusBarIconController statusBarIconController, QSFactory qSFactory, Handler handler, PluginManager pluginManager, TunerService tunerService, Provider provider, DumpManager dumpManager, Optional optional, QSLogger qSLogger, UiEventLogger uiEventLogger, UserTracker userTracker, SecureSettings secureSettings, CustomTileStatePersister customTileStatePersister, TileServiceRequestController.Builder builder, TileLifecycleManager.Factory factory) {
        Context context2 = context;
        PluginManager pluginManager2 = pluginManager;
        TunerService tunerService2 = tunerService;
        TileServiceRequestController.Builder builder2 = builder;
        ArrayList<QSFactory> arrayList = new ArrayList<>();
        this.mQsFactories = arrayList;
        this.mIconController = statusBarIconController;
        this.mContext = context2;
        this.mUserContext = context2;
        this.mTunerService = tunerService2;
        this.mPluginManager = pluginManager2;
        this.mQSLogger = qSLogger;
        this.mUiEventLogger = uiEventLogger;
        Objects.requireNonNull(builder);
        this.mTileServiceRequestController = new TileServiceRequestController(this, builder2.commandQueue, builder2.commandRegistry, new TileRequestDialogEventLogger(new UiEventLoggerImpl(), new InstanceIdSequence(1048576)));
        this.mTileLifeCycleManagerFactory = factory;
        this.mInstanceIdSequence = new InstanceIdSequence(1048576);
        this.mStatusBarOptional = optional;
        QSFactory qSFactory2 = qSFactory;
        arrayList.add(qSFactory);
        pluginManager2.addPluginListener(this, QSFactory.class, true);
        dumpManager.registerDumpable("QSTileHost", this);
        this.mUserTracker = userTracker;
        this.mSecureSettings = secureSettings;
        this.mCustomTileStatePersister = customTileStatePersister;
        Handler handler2 = handler;
        handler.post(new QSTileHost$$ExternalSyntheticLambda0(this, tunerService2, provider));
    }

    public final void addTile(String str, int i) {
        if (str.equals("work")) {
            Log.wtfStack("QSTileHost", "Adding work tile");
        }
        changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda7(str, i));
    }

    public final QSTile createTile(String str) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTile createTile = this.mQsFactories.get(i).createTile(str);
            if (createTile != null) {
                return createTile;
            }
        }
        return null;
    }

    public final void warn() {
    }

    public static ArrayList getDefaultSpecs(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(context.getResources().getString(C1777R.string.quick_settings_tiles_default).split(",")));
        if (Build.IS_DEBUGGABLE) {
            arrayList.add("dbg:mem");
        }
        return arrayList;
    }

    public final void changeTileSpecs(Predicate<List<String>> predicate) {
        ArrayList loadTileSpecs = loadTileSpecs(this.mContext, this.mSecureSettings.getStringForUser("sysui_qs_tiles", this.mCurrentUser));
        if (predicate.test(loadTileSpecs)) {
            saveTilesToSettings(loadTileSpecs);
        }
    }

    public final void changeTiles(List list, ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(list);
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayList2.get(i);
            if (str.startsWith("custom(") && !arrayList.contains(str)) {
                ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str);
                TileLifecycleManager create = this.mTileLifeCycleManagerFactory.create(new Intent().setComponent(componentFromSpec), new UserHandle(this.mCurrentUser));
                create.onStopListening();
                create.onTileRemoved();
                CustomTileStatePersister customTileStatePersister = this.mCustomTileStatePersister;
                Objects.requireNonNull(customTileStatePersister);
                customTileStatePersister.sharedPreferences.edit().remove(componentFromSpec.flattenToString() + ':' + this.mCurrentUser).apply();
                this.mContext.getSharedPreferences("tiles_prefs", 0).edit().putBoolean(componentFromSpec.flattenToString(), false).commit();
                create.mUnbindImmediate = true;
                create.setBindService(true);
            }
        }
        if (DEBUG) {
            Log.d("QSTileHost", "saveCurrentTiles " + arrayList);
        }
        saveTilesToSettings(arrayList);
    }

    public final void collapsePanels() {
        this.mStatusBarOptional.ifPresent(QSTileHost$$ExternalSyntheticLambda5.INSTANCE);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("QSTileHost:");
        this.mTiles.values().stream().filter(QSTileHost$$ExternalSyntheticLambda10.INSTANCE).forEach(new QSTileHost$$ExternalSyntheticLambda2(fileDescriptor, printWriter, strArr));
    }

    public final InstanceId getNewInstanceId() {
        return this.mInstanceIdSequence.newInstanceId();
    }

    public final int indexOf(String str) {
        return this.mTileSpecs.indexOf(str);
    }

    public final void onPluginConnected(Plugin plugin, Context context) {
        this.mQsFactories.add(0, (QSFactory) plugin);
        String value = this.mTunerService.getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    public final void onPluginDisconnected(Plugin plugin) {
        this.mQsFactories.remove((QSFactory) plugin);
        String value = this.mTunerService.getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    public final void openPanels() {
        this.mStatusBarOptional.ifPresent(QSTileHost$$ExternalSyntheticLambda4.INSTANCE);
    }

    public final void removeTile(String str) {
        changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda6(str));
    }

    public final void removeTiles(ArrayList arrayList) {
        changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda8(arrayList));
    }

    public final void unmarkTileAsAutoAdded(String str) {
        String str2;
        AutoTileManager autoTileManager = this.mAutoTiles;
        if (autoTileManager != null) {
            AutoAddTracker autoAddTracker = autoTileManager.mAutoTracker;
            Objects.requireNonNull(autoAddTracker);
            synchronized (autoAddTracker.autoAdded) {
                if (autoAddTracker.autoAdded.remove(str)) {
                    str2 = TextUtils.join(",", autoAddTracker.autoAdded);
                } else {
                    str2 = null;
                }
            }
            if (str2 != null) {
                autoAddTracker.secureSettings.putStringForUser$1("qs_auto_tiles", str2, autoAddTracker.userId);
            }
        }
    }

    public static ArrayList loadTileSpecs(Context context, String str) {
        Resources resources = context.getResources();
        if (TextUtils.isEmpty(str)) {
            str = resources.getString(C1777R.string.quick_settings_tiles);
            if (DEBUG) {
                DialogFragment$$ExternalSyntheticOutline0.m17m("Loaded tile specs from config: ", str, "QSTileHost");
            }
        } else if (DEBUG) {
            DialogFragment$$ExternalSyntheticOutline0.m17m("Loaded tile specs from setting: ", str, "QSTileHost");
        }
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        boolean z = false;
        for (String trim : str.split(",")) {
            String trim2 = trim.trim();
            if (!trim2.isEmpty()) {
                if (trim2.equals("default")) {
                    if (!z) {
                        Iterator it = getDefaultSpecs(context).iterator();
                        while (it.hasNext()) {
                            String str2 = (String) it.next();
                            if (!arraySet.contains(str2)) {
                                arrayList.add(str2);
                                arraySet.add(str2);
                            }
                        }
                        z = true;
                    }
                } else if (!arraySet.contains(trim2)) {
                    arrayList.add(trim2);
                    arraySet.add(trim2);
                }
            }
        }
        if (arrayList.contains("internet")) {
            arrayList.remove("wifi");
            arrayList.remove("cell");
        } else if (arrayList.contains("wifi")) {
            arrayList.set(arrayList.indexOf("wifi"), "internet");
            arrayList.remove("cell");
        } else if (arrayList.contains("cell")) {
            arrayList.set(arrayList.indexOf("cell"), "internet");
        }
        return arrayList;
    }

    public final void addTile(ComponentName componentName, boolean z) {
        String spec = CustomTile.toSpec(componentName);
        if (!this.mTileSpecs.contains(spec)) {
            ArrayList arrayList = new ArrayList(this.mTileSpecs);
            if (z) {
                arrayList.add(spec);
            } else {
                arrayList.add(0, spec);
            }
            changeTiles(this.mTileSpecs, arrayList);
        }
    }

    public final void onTuningChanged(String str, String str2) {
        boolean z;
        if ("sysui_qs_tiles".equals(str)) {
            Log.d("QSTileHost", "Recreating tiles");
            if (str2 == null && UserManager.isDeviceInDemoMode(this.mContext)) {
                str2 = this.mContext.getResources().getString(C1777R.string.quick_settings_tiles_retail_mode);
            }
            ArrayList loadTileSpecs = loadTileSpecs(this.mContext, str2);
            int userId = this.mUserTracker.getUserId();
            if (userId != this.mCurrentUser) {
                this.mUserContext = this.mUserTracker.getUserContext();
                AutoTileManager autoTileManager = this.mAutoTiles;
                if (autoTileManager != null) {
                    autoTileManager.changeUser(UserHandle.of(userId));
                }
            }
            if (!loadTileSpecs.equals(this.mTileSpecs) || userId != this.mCurrentUser) {
                this.mTiles.entrySet().stream().filter(new QSTileHost$$ExternalSyntheticLambda9(loadTileSpecs)).forEach(new QSTileHost$$ExternalSyntheticLambda1(this, 0));
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                Iterator it = loadTileSpecs.iterator();
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    QSTile qSTile = this.mTiles.get(str3);
                    if (qSTile == null || (z && ((CustomTile) qSTile).mUser != userId)) {
                        if (qSTile != null) {
                            qSTile.destroy();
                            Log.d("QSTileHost", "Destroying tile for wrong user: " + str3);
                            this.mQSLogger.logTileDestroyed(str3, "Tile for wrong user");
                        }
                        DialogFragment$$ExternalSyntheticOutline0.m17m("Creating tile: ", str3, "QSTileHost");
                        try {
                            QSTile createTile = createTile(str3);
                            if (createTile != null) {
                                createTile.setTileSpec(str3);
                                if (createTile.isAvailable()) {
                                    linkedHashMap.put(str3, createTile);
                                    this.mQSLogger.logTileAdded(str3);
                                } else {
                                    createTile.destroy();
                                    Log.d("QSTileHost", "Destroying not available tile: " + str3);
                                    this.mQSLogger.logTileDestroyed(str3, "Tile not available");
                                }
                            }
                        } catch (Throwable th) {
                            Log.w("QSTileHost", "Error creating tile for spec: " + str3, th);
                        }
                    } else if (qSTile.isAvailable()) {
                        if (DEBUG) {
                            Log.d("QSTileHost", "Adding " + qSTile);
                        }
                        qSTile.removeCallbacks();
                        if (!((z = qSTile instanceof CustomTile)) && this.mCurrentUser != userId) {
                            qSTile.userSwitch(userId);
                        }
                        linkedHashMap.put(str3, qSTile);
                        this.mQSLogger.logTileAdded(str3);
                    } else {
                        qSTile.destroy();
                        Log.d("QSTileHost", "Destroying not available tile: " + str3);
                        this.mQSLogger.logTileDestroyed(str3, "Tile not available");
                    }
                }
                this.mCurrentUser = userId;
                ArrayList arrayList = new ArrayList(this.mTileSpecs);
                this.mTileSpecs.clear();
                this.mTileSpecs.addAll(loadTileSpecs);
                this.mTiles.clear();
                this.mTiles.putAll(linkedHashMap);
                if (!linkedHashMap.isEmpty() || loadTileSpecs.isEmpty()) {
                    for (int i = 0; i < this.mCallbacks.size(); i++) {
                        ((QSHost.Callback) this.mCallbacks.get(i)).onTilesChanged();
                    }
                    return;
                }
                Log.d("QSTileHost", "No valid tiles on tuning changed. Setting to default.");
                changeTiles(arrayList, loadTileSpecs(this.mContext, ""));
            }
        }
    }

    public final void saveTilesToSettings(ArrayList arrayList) {
        if (arrayList.contains("work")) {
            Log.wtfStack("QSTileHost", "Saving work tile");
        }
        this.mSecureSettings.putStringForUser$1("sysui_qs_tiles", TextUtils.join(",", arrayList), this.mCurrentUser);
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final UiEventLogger getUiEventLogger() {
        return this.mUiEventLogger;
    }

    public final Context getUserContext() {
        return this.mUserContext;
    }

    public final int getUserId() {
        return this.mCurrentUser;
    }
}
