package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sumankhatiwada.vehiclebazzar.MainActivity;
import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerDashBoardComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerLoginAndRegisterComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.LoginAndRegisterModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.DashBoardPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.AboutUsFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.HomeFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.NotificationFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.ProfileFragement;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public class DashBoardActivity extends BaseActivity implements DashBoardView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Inject
    DashBoardPresenter dashBoardPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_dashboard;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setToolbar();
        setDesiredFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
//                    case R.id.action_home:
//                        showToast(DashBoardActivity.this, "Home");
//                        setDesiredFragment(new HomeFragment());
//                        break;

                    case R.id.action_profile:
                        showToast(DashBoardActivity.this, "Profile");
                        setDesiredFragment(new ProfileFragement());
                        break;

                    case R.id.action_aboutUs:
                        showToast(DashBoardActivity.this, "AboutUs");
                        setDesiredFragment(new AboutUsFragment());
                        break;

                    case R.id.action_Notification:
                        showToast(DashBoardActivity.this, "Notification");
                        setDesiredFragment(new NotificationFragment());
                        break;

                }
                return true;
            }
        });

    }

    private void setDesiredFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_out, R.anim.fade_in)
                .add(R.id.frame_dashboard_container, fragment, "fragment").commit();
        // set the toolbar title
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_container, fragment).commit();
        String title = "";
        if (fragment instanceof HomeFragment) {
            title = getString(R.string.home);
        } else if (fragment instanceof ProfileFragement) {
            title = getString(R.string.profile);
        } else if (fragment instanceof AboutUsFragment) {
            title = getString(R.string.aboutus);
        } else if (fragment instanceof NotificationFragment) {
            title = getString(R.string.notification);
        }

        getSupportActionBar().setTitle(title);
    }

    protected void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.logout) {
            dashBoardPresenter.clearAllPreferences();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void resolveDaggerDependency() {
        super.resolveDaggerDependency();
        DaggerDashBoardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashBoardModule(new DashBoardModule(this))
                .build().inject(this);
    }

    @Override
    public void onLogoutSuccess() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
