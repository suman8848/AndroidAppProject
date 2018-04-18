package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.content.Intent;
import android.os.Bundle;
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

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public class DashBoardActivity extends BaseActivity implements DashBoardView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

    }
    protected void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.logout){
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
