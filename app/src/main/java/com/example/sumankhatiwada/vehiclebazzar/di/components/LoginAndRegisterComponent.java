package com.example.sumankhatiwada.vehiclebazzar.di.components;

import com.example.sumankhatiwada.vehiclebazzar.di.modules.LoginAndRegisterModule;
import com.example.sumankhatiwada.vehiclebazzar.di.scope.PerActivity;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.RegisterView;
import com.example.sumankhatiwada.vehiclebazzar.ui.activities.LoginActivity;
import com.example.sumankhatiwada.vehiclebazzar.ui.activities.RegisterActivity;

import dagger.Component;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

@PerActivity
@Component(modules = LoginAndRegisterModule.class, dependencies = ApplicationComponent.class)
public interface LoginAndRegisterComponent {
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);

}
