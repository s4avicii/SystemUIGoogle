package com.android.systemui.user;

import com.android.settingslib.users.EditUserInfoController;
import dagger.internal.Factory;

public final class UserModule_ProvideEditUserInfoControllerFactory implements Factory<EditUserInfoController> {

    public static final class InstanceHolder {
        public static final UserModule_ProvideEditUserInfoControllerFactory INSTANCE = new UserModule_ProvideEditUserInfoControllerFactory();
    }

    public final Object get() {
        return new EditUserInfoController();
    }
}
