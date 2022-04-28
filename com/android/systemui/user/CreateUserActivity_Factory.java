package com.android.systemui.user;

import android.app.IActivityManager;
import com.android.settingslib.users.EditUserInfoController;
import com.android.systemui.user.UserModule_ProvideEditUserInfoControllerFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CreateUserActivity_Factory implements Factory<CreateUserActivity> {
    public final Provider<IActivityManager> activityManagerProvider;
    public final Provider<EditUserInfoController> editUserInfoControllerProvider;
    public final Provider<UserCreator> userCreatorProvider;

    public CreateUserActivity_Factory(UserCreator_Factory userCreator_Factory, Provider provider) {
        UserModule_ProvideEditUserInfoControllerFactory userModule_ProvideEditUserInfoControllerFactory = UserModule_ProvideEditUserInfoControllerFactory.InstanceHolder.INSTANCE;
        this.userCreatorProvider = userCreator_Factory;
        this.editUserInfoControllerProvider = userModule_ProvideEditUserInfoControllerFactory;
        this.activityManagerProvider = provider;
    }

    public final Object get() {
        return new CreateUserActivity(this.userCreatorProvider.get(), this.editUserInfoControllerProvider.get(), this.activityManagerProvider.get());
    }
}
