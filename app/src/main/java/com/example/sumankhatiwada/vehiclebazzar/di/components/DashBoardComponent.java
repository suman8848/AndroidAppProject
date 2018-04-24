package com.example.sumankhatiwada.vehiclebazzar.di.components;

import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.LoginAndRegisterModule;
import com.example.sumankhatiwada.vehiclebazzar.di.scope.PerActivity;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.ProfileFragmentPresenter;
import com.example.sumankhatiwada.vehiclebazzar.ui.activities.DashBoardActivity;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.HomeFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.ProfileFragment;

import dagger.Component;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

@PerActivity
@Component(modules = DashBoardModule.class, dependencies = ApplicationComponent.class)
public interface DashBoardComponent {
    void inject(DashBoardActivity dashBoardActivity);
    void inject(ProfileFragment profileFragment);
    void inject(HomeFragment homeFragment);
}
